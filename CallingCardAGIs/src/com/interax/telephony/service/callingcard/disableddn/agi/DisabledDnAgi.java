package com.interax.telephony.service.callingcard.disableddn.agi;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;

import com.interax.telephony.cdr.InteraxTelephonyCdrFormater;
import com.interax.telephony.server.agi.InteraxTelephonyAgiServer;
import com.interax.telephony.service.callingcard.base.agi.CallingCardBaseAgi;
import com.interax.telephony.service.data.ServiceFamily;
import com.interax.telephony.service.remote.callingcard.CallingCardPinEJB;
import com.interax.telephony.util.InteraxTelephonyGenericEjbCaller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisabledDnAgi extends CallingCardBaseAgi {
	
    private Thread agiThread;
    private AgiRequest agiRequest;
    private AgiChannel agiChannel;
    private String channelId;
    private InteraxTelephonyGenericEjbCaller genericEjbCaller;
    private CallingCardPinEJB callingCardPinEJB;

    private DisabledDnAgiLogger logger;

    private DisabledDnAgiStep step;
    private int playCustomAudioTries;

    private static final int NULL_TIMEOUT = 0;
    private static final int NULL_MAX_DIGITS = 0;
    private static final int MAX_DIGITS = 1;
    private static final int MAX_CUSTOM_AUDIO_LISTENING_TRIES = 3;

    private static ServiceFamily serviceFamily = ServiceFamily.CALLING_CARD;
    private String ani = null;
    private String alternativeDn;

    private String cdrId = null;
    private boolean cdrForked = false;

    private DisabledDnAgiConfig config = null;

    private String configPath = null;

    // Interruption Mode Flags
    boolean interrupted = false;
    char interruptedData = 0x0;

    @Override
    public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {

        this.agiThread = Thread.currentThread();
        this.agiThread.setName("DisabledDnAgiThread-" + agiThread.getId());

        this.interrupted = false;
        this.interruptedData = 0x0;
        this.playCustomAudioTries = 0;

        this.cdrId = getVariable("CDR(uniqueId)");
        this.setVariable("CDR(accountcode)", InteraxTelephonyCdrFormater.formatCdrAccountCode(serviceFamily, null));
        this.setVariable("CDR(userfield)", InteraxTelephonyCdrFormater.formatCdrUserField(null, null, null, null));
        this.ani = this.getVariable("CALLERID(num)");

        this.agiRequest = arg0;
        this.agiChannel = arg1;
        this.configPath = InteraxTelephonyAgiServer.getConfig().CONFIG_PATH + InteraxTelephonyAgiServer.getConfig().AGIS_CONFIG_PATH + "/disableddn/";
        this.logger = new DisabledDnAgiLogger(this, this.configPath);
        this.step = DisabledDnAgiStep.INIT;
        this.channelId = agiChannel.getVariable("CHANNEL");

        // Inicialización del AGI
        this.logger.info("DisabledDnAgi started");
        this.step = DisabledDnAgiStep.ANSWER;

        // Máquina de estados
        while(this.step != DisabledDnAgiStep.STOP){

            switch(step) {
                case ANSWER:
                    pd_answer();
                break;

                case INIT_RESOURCES:
                    pd_initResources();
                break;

                case GET_ALTERNATIVE_DN:
                    pd_getAlternativeDn();
                break;

                case PLAY_CUSTOM_AUDIO:
                    pd_playCustom_Audio();
                break;

                case GET_CALL_TRANSFERENCE_INPUT:
                    pd_get_call_transference_input();
                break;

                case TRANSFER:
                    pd_transfer();
                break;

                case HANGUP:
                    pd_hangup();
                break;
            }

        }

        // IVR CDR... There can be only one!
        if(cdrForked) this.setVariable("CDR(amaflags)", "IGNORE");
        // Finalización del AGI
        this.logger.info("PromiscuousDnAgi stopped");
    }

    // FUNCIONES DE LA MÁQUINA DE ESTADOS

    private void pd_answer() {

        try {
            this.logger.info("Trying to answer the channel");
            answer();
            this.logger.info("Channel answered successfully");
            this.logger.info("Waiting one second, to ensure that playback beggins from the top.");
            this.exec("Wait","1");
            this.step = DisabledDnAgiStep.INIT_RESOURCES;
        } catch(AgiException ae){
            this.logger.error("An error ocurred while trying to answer the channel", ae);
            this.step = DisabledDnAgiStep.HANGUP;
        }

    }

    private void pd_initResources() {

        try {
            this.logger.info("Trying to initialize resources");
            this.setVariable("CHANNEL(language)", "es");
            this.genericEjbCaller = new InteraxTelephonyGenericEjbCaller(logger);
            this.logger.info("Resources initialized successfully");
            this.logger.info("Getting configName for request.");
            String configName = this.getChannel().getVariable("CALLING_CARD_CONFIG_NAME");
            this.logger.info("Looking for configuration " + configName + " in path: " + this.configPath);
            this.config = DisabledDnAgiConfigLoader.getConfig(configName, this.configPath);
            this._streamFile(DisabledDnAgiAudio.WELCOME);
            this.step = DisabledDnAgiStep.GET_ALTERNATIVE_DN;
            /*List<String> filesToPlay = playNumber(15);
            for(int i=0;i < filesToPlay.size();i++) {
                try {
                    streamFile("digits/es/"+filesToPlay.get(i));
                } catch (AgiException ex) {

                }
            }
            this.step = DisabledDnAgiStep.HANGUP;*/
        } catch(AgiException ae){
            this.logger.error("An error ocurred while trying to initialize resources", ae);
            this.step = DisabledDnAgiStep.HANGUP;
        }

    }

    private void pd_getAlternativeDn() {

        try {
            this._streamFile(DisabledDnAgiAudio.UNAVAILABLE);
            this.alternativeDn = this.getAlternativeAccessNumbers(ani);
            this.step = DisabledDnAgiStep.PLAY_CUSTOM_AUDIO;
        } catch (AgiException ae) {
            this.logger.error("An error ocurred while trying to get DNs", ae);
            this.step = DisabledDnAgiStep.HANGUP;
        }
               
    }

    private void pd_playCustom_Audio() {
        
        try {
            this._streamFile(DisabledDnAgiAudio.PLEASE_DIAL_FOLLOWING_NUMBER);
            sayDigits(this.alternativeDn);
            this.playCustomAudioTries++;
            String option = this._getData(DisabledDnAgiAudio.CALL_TRANSFERENCE_OPTIONS_EXTENDED,10000);
            if(this.playCustomAudioTries < MAX_CUSTOM_AUDIO_LISTENING_TRIES && option.equals("1")) this.step = DisabledDnAgiStep.PLAY_CUSTOM_AUDIO;
            else if(option.equals("2")) this.step = DisabledDnAgiStep.TRANSFER;
            else this.step = DisabledDnAgiStep.GET_CALL_TRANSFERENCE_INPUT;
        } catch (AgiException ae) {
            this.logger.error("An error ocurred while trying to play custom audio", ae);
            this.step = DisabledDnAgiStep.HANGUP;
        }

    }

    private void pd_get_call_transference_input() {

        try {
            String option = this._getData(DisabledDnAgiAudio.CALL_TRANSFERENCE_OPTIONS,10000);
            if(option.equals("1")) this.step = DisabledDnAgiStep.TRANSFER;
            else this.step = DisabledDnAgiStep.HANGUP;
        } catch (AgiException ae) {
            this.logger.error("An error ocurred while trying to get call transference input", ae);
            this.step = DisabledDnAgiStep.HANGUP;
        }
        
    }

    private void pd_transfer() {

       try{
            this.logger.info("Trying to transfer the call");
            this._streamFile(DisabledDnAgiAudio.CALL_TRANSFERENCE);
            String currentContext = this.getVariable("MACRO_CONTEXT");
            //String dialOptions = "Local/*811@" + currentContext + "/n";
            //Transferencia a ATC
            String dialOptions = "Local/106@it_test/n";
            exec("Dial", dialOptions);
            this.logger.info("Call transfered successfully");
            this.step = DisabledDnAgiStep.HANGUP;
        } catch(AgiException ae){
            this.logger.error("An error ocurred while trying to transfer the call", ae);
            this.step = DisabledDnAgiStep.HANGUP;
        }
        
    }

    private void pd_hangup() {

        try {
            this.logger.info("Trying to hangup the channel");
            hangup();
            this.logger.info("Channel hanged up successfully");
            this.step = DisabledDnAgiStep.STOP;
        } catch(AgiException ae){
            this.logger.error("An error ocurred while trying to hangup the channel", ae);
            this.step = DisabledDnAgiStep.STOP;
        }
        
    }

    // GETTERS

    public String getChannelId() {
        return this.channelId;
    }

    public String getCdrId() {
        return this.cdrId;
    }

    public DisabledDnAgiStep getStep() {
        return this.step;
    }

    // INTERRUPTION METHODS

    public String _getData(String file) throws AgiException {

        return this._getData(file, NULL_TIMEOUT);

    }

    public String _getData(String file, int timeout) throws AgiException {

        return this._getData(file, timeout, MAX_DIGITS);
        
    }

    public String _getData(String file, int timeout, int maxDigits) throws AgiException {

        String data = null;
        StringBuffer bufferData = new StringBuffer();

        if (this.interrupted) {
            bufferData.append(this.interruptedData);
            if(maxDigits != NULL_MAX_DIGITS){
                 maxDigits--;
            }
            this.interrupted = false;
            file = DisabledDnAgiAudio.SILENCE_0;
        }
        if (maxDigits > 0 || maxDigits == NULL_MAX_DIGITS) {
            if(maxDigits == NULL_MAX_DIGITS){
                if(timeout == NULL_TIMEOUT){
                    data = this.getChannel().getData(file);
                } else{
                    data = this.getChannel().getData(file, timeout);
                }
            } else {
                data = this.getChannel().getData(file, timeout, maxDigits);
            }
            if(data != null && !data.contains("timeout")){
                bufferData.append(data);
            }
        }

        if(bufferData.length()>0)  return bufferData.toString();
        else return data;
        
    }
	
    public void _streamFile(String file) throws AgiException {

        this._streamFile(file, "0123456789#*");

    }
	
    public void _streamFile(String file, String escapeDigits) throws AgiException {

    	if(this.interrupted) return;
    	   
    	char dtmf = getChannel().streamFile(file, escapeDigits);
    	if(dtmf != 0x00){
            this.interrupted = true;
            this.interruptedData = dtmf;
    	}

    }

    public char _sayDigits(String digits) throws AgiException {

        return _sayDigits(digits,"0123456789#*");

    }

    public char _sayDigits(String digits, String escapeDigits) throws AgiException {

        return getChannel().sayDigits(digits, escapeDigits);

    }
    
    public void _sayNumber(String number) throws AgiException {

         _sayNumber(number,"0123456789#*");

    }

    public void _sayNumber(String number, String escapeDigits) throws AgiException {

    	if(this.interrupted) return;
    	
    	char dtmf = getChannel().sayNumber(number, escapeDigits);
    	if(dtmf != 0x00){
            this.interrupted = true;
            this.interruptedData = dtmf;
    	}

    }
    
    public char _getOption(String file, String escapeDigits) throws AgiException {

        return _getOption(file, escapeDigits,NULL_TIMEOUT);

    }

    
    public char _getOption(String file, String escapeDigits, int timeout) throws AgiException {

    	char dtmf = 0x00;
    	if(this.interrupted){
            this.interrupted = false;
            return this.interruptedData;
    	}
    	if(timeout == NULL_TIMEOUT){
             dtmf = getChannel().getOption(file, escapeDigits);
    	} else {
             dtmf = getChannel().getOption(file, escapeDigits, timeout);
    	}
    
    	if(dtmf != 0x00){
            this.interrupted = true;
            this.interruptedData = dtmf;
    	}

        return dtmf;

    }

    //MISCELLANEOUS

    private String getAlternativeAccessNumbers(String ani) {

        callingCardPinEJB = (CallingCardPinEJB)this.genericEjbCaller.getGenericController("ejb/CallingCardPinEJB", this.config.PIN_EJB_SERVER, this.config.PIN_EJB_PORT);
        String dn = "";
        try {
            dn = callingCardPinEJB.getAccessNumberByAni(null,ani);
        } catch(Exception e) {
            this.logger.error("An error ocurred while trying to get alternative access numbers", e);
        }

        return dn;

    }

    private List<String> playNumber(int number) {

        List<String> filesToPlay = new ArrayList<String>();
        HashMap<String,Integer> nHash = new HashMap<String,Integer>(); 
        int cociente, residuo = 0;
        int divisor = 1000;

        while(divisor > 1) {
            cociente = number / divisor;
            residuo = number % divisor;
            if(cociente > 0) {
                switch(divisor) {
                    case 1000:
                        nHash.put("m",cociente);
                    break;

                    case 100:
                        nHash.put("c",cociente);
                    break;

                    case 10:
                        nHash.put("d",number);
                    break;
                }
            } else nHash.put("d",number);
            number = residuo;
            divisor /= 10;
        }

        Integer cifra = null;
        //Miles
        if(nHash.get("m") != null && nHash.get("m") > 0) {
            cifra = nHash.get("m");
            if(cifra > 1 && cifra <= 30) filesToPlay.add(cifra.toString());
            else if(cifra > 30) {
                filesToPlay = playNumber(cifra);
            }
            filesToPlay.add("thousand");
        }
        //Centenas
        if(nHash.get("c") != null && nHash.get("c") > 0) {
            cifra = nHash.get("c");
            if(cifra > 1) {
                filesToPlay.add((cifra*100)+"");
            } else {
                if(nHash.get("d") != null && nHash.get("d") > 1) filesToPlay.add("100-and");
                else filesToPlay.add("hundred");
            }
        }
        //Decenas y unidades
        if(nHash.get("d") != null && nHash.get("d") > 0) {
            cifra = nHash.get("d");
            if(cifra <= 30) filesToPlay.add(cifra.toString());
            else {
                cociente = cifra / 10;
                residuo = cifra % 10;
                filesToPlay.add((cociente*10)+"");
                if(residuo > 0) {
                    filesToPlay.add("and");
                    filesToPlay.add(residuo+"");
                }
                
            }
        }

        return filesToPlay;
        /*for(int i=0;i < filesToPlay.size();i++) {
            try {
                streamFile("digits/es/"+filesToPlay.get(i));
            } catch (AgiException ex) {

            }
        }*/

    }

}
