
package com.interax.telephony.service.callingcard.agi;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;

import com.interax.telephony.cdr.InteraxTelephonyCdrFormater;
import com.interax.telephony.server.agi.InteraxTelephonyAgiServer;
import com.interax.telephony.service.callingcard.base.agi.CallingCardBaseAgi;
import com.interax.telephony.service.callingcard.data.CallingCardAccessType;
import com.interax.telephony.service.callingcard.data.CallingCardAuthenticateData;
import com.interax.telephony.service.callingcard.data.CallingCardCancelData;
import com.interax.telephony.service.callingcard.data.CallingCardCommitData;
import com.interax.telephony.service.callingcard.data.CallingCardFavoriteDestinationCombination;
import com.interax.telephony.service.callingcard.data.CallingCardPin;
import com.interax.telephony.service.callingcard.data.CallingCardPinQuota;
import com.interax.telephony.service.callingcard.data.CallingCardPinStatus;
import com.interax.telephony.service.callingcard.data.CallingCardRequestData;
import com.interax.telephony.service.callingcard.data.CallingCardReservation;
import com.interax.telephony.service.callingcard.data.CallingCardServiceType;
import com.interax.telephony.service.callingcard.security.PinSecretEncrypter;
import com.interax.telephony.service.data.CdrCallDetailRecord;
import com.interax.telephony.service.data.ServiceDialStatus;
import com.interax.telephony.service.data.ServiceFamily;
import com.interax.telephony.service.data.ServiceReservation;
import com.interax.telephony.service.exception.InteraxTelephonyException;
import com.interax.telephony.service.exception.NoOpenReservationException;
import com.interax.telephony.service.exception.ReservationNotFoundException;
import com.interax.telephony.service.exception.callingcard.CallingCardCreditLimitExceededException;
import com.interax.telephony.service.exception.callingcard.CallingCardException;
import com.interax.telephony.service.exception.callingcard.CallingCardGeneralException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidAccessTypeException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidCountryCodeException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidDnByLocationException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidDnException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidPinException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidServiceTypeException;
import com.interax.telephony.service.exception.callingcard.CallingCardMaxConcurrentCallException;
import com.interax.telephony.service.exception.callingcard.CallingCardNotEnoughBalanceException;
import com.interax.telephony.service.exception.callingcard.CallingCardNotMinBalanceException;
import com.interax.telephony.service.exception.callingcard.CallingCardOverdueInvoiceException;
import com.interax.telephony.service.exception.callingcard.CallingCardRateNotFoundException;
import com.interax.telephony.service.exception.callingcard.CallingCardRestrictedCallException;
import com.interax.telephony.service.exception.callingcard.CallingCardUnauthorizedCallException;
import com.interax.telephony.service.remote.RaterEJB;
import com.interax.telephony.service.remote.callingcard.CallingCardPinEJB;
import com.interax.telephony.service.security.Encoder;
import com.interax.telephony.util.GeneralUtils;
import com.interax.telephony.util.InteraxTelephonyGenericEjbCaller;
import com.interax.utils.FileUtils;

public class CallingCardAgi extends CallingCardBaseAgi{

    private Thread agiThread;
    private AgiRequest agiRequest;
    private AgiChannel agiChannel;
    private String channelId;

    private CallingCardAgiLogger logger;

    private CallingCardAgiStep step;
    private String language;
    private int languageAttempt;
    private int authenticationAttempt;
    private int askPasswordAttempt;
    private int specialMenuAttempt;
    private int changePasswordAttempt;
    private int callAttempt;

    private static final int NULL_TIMEOUT = 0;
    private static final int NULL_MAX_DIGITS = 0;

    private static ServiceFamily serviceFamily = ServiceFamily.CALLING_CARD;
    private CallingCardServiceType serviceType;
    private CallingCardAccessType accessType;
    private CallingCardAuthenticateData currentAuthenticateData = null;
    private String ani = null;
    private String dnid = null;
    private String dni = null;
        private int playCustomAudioTries;
        private final String ATC_NUMBER = "18009543211";

    //TEST_MODE:1
    private boolean agiTestMode = false;
    private boolean raterTestMode = false;
    private boolean raterTestPinFree = false;
    private int raterTestAvailablesSeconds = 90;
    private boolean raterTestInternCall = false;

    private String cdrId = null;
    private boolean cdrForked = false;
    private int cdrCallAttempt;

    private String secret = null;
    private CallingCardPin pin = null;
    private boolean pinHasUnlimitedQuota;
    private CallingCardRequestData currentRequestData = null;
    private CallingCardReservation currentReservation = null;

    private CallingCardAgiConfig config = null;

    private InteraxTelephonyGenericEjbCaller genericEjbCaller;
    private String configPath = null;
    private RaterEJB raterEJB;
    private CallingCardPinEJB callingCardPinEJB;

    // Interruption Mode Flags
    boolean interrupted = false;
    char interruptedData = 0x0;

    private String currentUserField = "";
    private String currentAccountCode = "" ;
        private String alternativeDn;

    private FileUtils fileManager;
    private File callFile;
    Boolean isMobile = false;

    private String commitPersistenceFolder;
    private String cancelPersistenceFolder;
    private String crashPersistenceFolder;
    private String onlineCallPersistenceFolder;
    private CallingCardFavoriteDestinationCombination ccFdc;

    public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {

        this.agiThread = Thread.currentThread();
        this.agiThread.setName("CallingCardAgiThread-" + agiThread.getId());

        this.interrupted = false;
        this.interruptedData = 0x0;

        this.cdrId = getVariable("CDR(uniqueId)");
        this.setVariable("CDR(accountcode)", InteraxTelephonyCdrFormater.formatCdrAccountCode(serviceFamily, null));
        this.setVariable("CDR(userfield)", InteraxTelephonyCdrFormater.formatCdrUserField(null, null, null, null));

        this.agiRequest = arg0;
        this.agiChannel = arg1;
        this.configPath = InteraxTelephonyAgiServer.getConfig().CONFIG_PATH + InteraxTelephonyAgiServer.getConfig().AGIS_CONFIG_PATH + "/callingcard/";
        this.logger = new CallingCardAgiLogger(this, this.configPath);
        this.step = CallingCardAgiStep.INIT;
        this.channelId = agiChannel.getVariable("CHANNEL");

        String persistenceFolder = InteraxTelephonyAgiServer.getConfig().PERSISTENCE_PATH + InteraxTelephonyAgiServer.getConfig().AGIS_CONFIG_PATH + "/" + serviceFamily.name().toLowerCase().replace("_", "") + "/" + CallingCardAgi.class.getSimpleName();
        this.commitPersistenceFolder = persistenceFolder + "/commit/";
        this.cancelPersistenceFolder = persistenceFolder + "/cancel/";
        this.crashPersistenceFolder = persistenceFolder + "/crash/";
        this.onlineCallPersistenceFolder = persistenceFolder + "/onlineCall/";
        // TEST_MODE:2
        String agiMode = getVariable("AGI_MODE");
        if(agiMode!=null && agiMode.equalsIgnoreCase("test")){
            this.agiTestMode = true;
            this.logger.info("AGI_MODE TEST ACTIVATED.");
        }

        if(this.getVariable("IS_MOBILE") != null){
            isMobile = Boolean.parseBoolean(this.getVariable("IS_MOBILE"));
        };

        //RATER_TEST_MODE:2
        String raterMode = getVariable("RATER_MODE");
        if(raterMode!=null && raterMode.equalsIgnoreCase("test")){
            this.raterTestMode = true;
            this.logger.info("RATER MODE TEST ACTIVATED.");
            String raterSeconds = getVariable("RATER_SECONDS");
            this.logger.info("RATER TEST AVAILABLE SECONDS:" + raterSeconds);
            if(raterSeconds!= null){
                try{
                    this.raterTestAvailablesSeconds = Integer.parseInt(raterSeconds);
                }
                catch(Exception e){
                    this.logger.error("Error parsing raterTestAvailablesMinutes.");
                    this.logger.debug("Detail: " + e.getMessage());
                }
            }
            String raterInternCall = getVariable("RATER_INTERN_CALL");
            if(raterInternCall!=null && raterInternCall.equalsIgnoreCase("true")){
                this.raterTestInternCall = true;
                this.logger.info("RATER TEST INTERN CALL ACTIVATED.");
            }

        }

        this.languageAttempt = 0;
        this.authenticationAttempt = 0;
        this.askPasswordAttempt = 0;
        this.specialMenuAttempt = 0;
        this.changePasswordAttempt = 0;
        this.callAttempt = 1;
        this.cdrCallAttempt = 0;

        // Inicialización del AGI
        this.logger.info("CallingCardAgi started");
        this.step = CallingCardAgiStep.ANSWER;

        // Máquina de estados
        while(this.step != CallingCardAgiStep.STOP){

            switch(step){
                case ANSWER:
                    cc_answer();
                break;

                case INIT_RESOURCES:
                    cc_initResources();
                break;

                case PROCESS_CALL_DATA:
                    cc_processCallData();
                break;

                case ASK_LANGUAGE:
                    cc_askLanguage();
                break;

                case AUTHENTICATE:
                    cc_authenticate();
                break;

                case ASK_PASSWORD:
                    cc_askPassword();
                break;

                                case PLAY_ALTERNATIVE_DN_AUDIO:
                                    cc_play_alternative_dn_audio();
                                break;

                case AUTHORIZE:
                    cc_authorize();
                break;

                case SPECIAL_MENU:
                    cc_specialMenu();
                break;

                case CHANGE_PASSWORD:
                    cc_changePassword();
                break;

                case VALIDATE_DESTINY:
                    cc_validateDestiny();
                break;

                case MAKE_CALL:
                    cc_makeCall();
                break;

                case HANGUP:
                    cc_hangup();
                break;
            }
        }

     // IVR CDR... There can be only one!
     if(cdrForked)
         this.setVariable("CDR(amaflags)", "IGNORE");

     // Finalización del AGI
     this.logger.info("CallingCardAgi stopped");
    }



    // FUNCIONES DE LA MÁQUINA DE ESTADOS

    private void cc_answer(){
        try{
            this.logger.info("Trying to answer the channel");
            answer();
            this.logger.info("Channel answered successfully");

            this.logger.info("Waiting one second, to ensure that sounds are playedback form the begining.");
            this.exec("Wait","1");

            this.step = CallingCardAgiStep.INIT_RESOURCES;
        }
        catch(AgiException ae){
            this.logger.error("An error ocurred while trying to answer the channel", ae);
            this.step = CallingCardAgiStep.HANGUP;
        }
    }

    private void cc_initResources(){
//        try{
            this.logger.info("Trying to initialize resources");

            this.genericEjbCaller = new InteraxTelephonyGenericEjbCaller(logger);

            this.logger.info("Resources initialized successfully");
            this.step = CallingCardAgiStep.PROCESS_CALL_DATA;
//        }
//        catch(AgiException ae){
//            this.logger.error("An error ocurred while trying to initialize resources", ae);
//            this.step = CallingCardAgiStep.STOP;
//        }
    }

    private void cc_processCallData(){
        try{
            this.logger.info("Trying to process call data");

            //TEST_MODE:3
            if(this.agiTestMode){
                this.ani = generateTestAni();
                this.logger.info("ANI: " + this.ani);
                this.setVariable("CDR(src)", this.ani);
                this.dnid = generateTestDnid();
                this.logger.info("DNID: " + this.dnid);
            }
            else{
                this.ani = this.getVariable("CALLERID(num)");
                this.logger.info("ANI: " + this.ani);
                this.setVariable("CDR(src)", this.ani);
                this.dnid = this.getVariable("CALLERID(dnid)");
                this.logger.info("DNID: " + this.dnid);
            }

            this.setVariable("CDR(dst)", this.dnid);

            this.logger.info("Getting configName for request.");
            String configName = this.getChannel().getVariable("CALLING_CARD_CONFIG_NAME");
            this.logger.info("Looking for configuration " + configName + " in path: " + this.configPath);

            this.config = CallingCardAgiConfigLoader.getConfig(configName, this.configPath);
            CallingCardAgiStep nextStep = CallingCardAgiStep.HANGUP;

            if(this.config==null){
                this.logger.info("No config file associated to this configName.");
                this.step = CallingCardAgiStep.HANGUP;
                return;
            }
            else{

                this.accessType = this.config.ACCESS_TYPE;

                this.logger.info("SERVICE TYPES: " + this.accessType.name());
                this.logger.info("ACCESS TYPE: " + this.accessType.name());
                this.logger.info("ENTERPRISE ID: " + this.config.ENTERPRISE_ID);
                this.logger.info("ENTERPRISE NAME: " + this.config.ENTERPRISE_NAME);
                this.logger.info("CDR ID:" + this.cdrId);
                this.logger.info("CHANNEL ID:" + this.channelId);
                this.currentAccountCode = InteraxTelephonyCdrFormater.formatCdrAccountCode(serviceFamily, this.config.ENTERPRISE_ID);
 this.setVariable("CDR(accountcode)",this.currentAccountCode);
                this.setVariable("CDR(userfield)", InteraxTelephonyCdrFormater.formatCdrUserField(this.accessType, null, null, null));

                String defaultLanguage = this.config.LANGUAGES[0];
                this.logger.info("Using default language: " + defaultLanguage);
//                this.setVariable("CHANNEL(language)", defaultLanguage.toLowerCase());

                switch(this.accessType){
                    case IVR:
                    case IVR_MOBIL:
                        nextStep = CallingCardAgiStep.ASK_LANGUAGE;
                    break;

                    case IVR_PIN_FREE:
                        nextStep = CallingCardAgiStep.AUTHENTICATE;
                    break;

                    case IVR_DIALER:
                        this.logger.info("Sending tone to dialer..");
                        //TEST_MODE:4
                        if(this.agiTestMode)
                            this.dni = generateTestDni();
                        else
                            this.dni = this._getData(CallingCardAgiAudio.WELCOME,10000);

                        if(this.dni.contains("timeout")){
                            this.logger.info("No DNI sent from the dialer after 10 seconds.");
                            nextStep = CallingCardAgiStep.HANGUP;
                        }
                        else{
                            this.logger.info("DNI: " + this.dni);
                            nextStep = CallingCardAgiStep.AUTHENTICATE;
                        }
                    break;

                    case WEB_PHONE:
                        String webphoneData = getVariable("WEBPHONE_DATA");
                        System.out.println(webphoneData.length());
 System.out.println((PinSecretEncrypter.ENCRYPTED_SECRET_LENGTH + 1));
                        if(webphoneData.length() < (PinSecretEncrypter.ENCRYPTED_SECRET_LENGTH + 1)){
                            this.logger.info("Invalid webphone request: Wrong data length");
                            this.logger.info("Request data: " + webphoneData);
                            this.step = CallingCardAgiStep.HANGUP;
                            return;
                        }

                        String ipAddress = this.agiRequest.getRemoteAddress().getHostAddress();
                        this.logger.info("IP ADDRESS: " + ipAddress);
                        String encryptedSecret = webphoneData.substring(0, PinSecretEncrypter.ENCRYPTED_SECRET_LENGTH);
                        this.secret = PinSecretEncrypter.decryptPinSecret(encryptedSecret, ipAddress);

                        if(this.secret==null){
                            this.logger.info("Invalid webphone request: Wrong data encoding");
                            this.logger.info("Request data: " + webphoneData);
                            this.step = CallingCardAgiStep.HANGUP;
                            return;
                        }

                        this.logger.info("SECRET: " + this.secret);
                        this.dni = webphoneData.substring(PinSecretEncrypter.ENCRYPTED_SECRET_LENGTH);
                        this.logger.info("DNI: " + this.dni);
                        nextStep = CallingCardAgiStep.AUTHENTICATE;
                    break;
                }
            }

            this.setVariable(serviceFamily.name() + "_ENTERPRISE_ID", this.config.ENTERPRISE_ID + "");
            this.setVariable(serviceFamily.name() + "_SERVICE_FAMILY", serviceFamily.name());
            this.setVariable(serviceFamily.name() + "_ACCESS_TYPE", this.accessType.name());
            this.setVariable(serviceFamily.name() + "_DNID", this.dnid);
            this.setVariable(serviceFamily.name() + "_ANI", this.ani);

            this.logger.info("Call data processed successfully");
            this.step = nextStep;
        }
        catch(AgiException ae){
                    this.logger.error("An error ocurred while trying to process call data", ae);
                    this.step = CallingCardAgiStep.HANGUP;
        }
    }

    private void cc_askLanguage(){
        try{
            this.logger.info("Trying to ask language");

            if(this.config.LANGUAGES.length<2){
                this.language = this.config.LANGUAGES[0].toLowerCase();
//                setVariable("CHANNEL(language)", this.language);
                this.logger.info("Only one language available. Using: " + this.language);


                if(this.accessType == CallingCardAccessType.IVR_MOBIL)
                {
 this._streamFile(CallingCardAgiAudio.CC_PROCESSING_CALL);
                }
                else
                {
//                    this._streamFile(CallingCardAgiAudio.WELCOME);
                }

                this.step = CallingCardAgiStep.AUTHENTICATE;
                return;
            }

            this.logger.info("Multiple languages available. Asking the user for one.");
 while(++this.languageAttempt<=this.config.LANGUAGE_MAX_ATTEMPTS){

                String validKeys = "1234567890*#";
                char languageKey = 0;
                int availableLanguagesCount = this.config.LANGUAGES.length;

                for (int i=0; i<availableLanguagesCount; i++) {
                    String language = this.config.LANGUAGES[i];

                    setVariable("CHANNEL(language)", language.toLowerCase());
                    if(this.languageAttempt==1){
                        languageKey = this._getOption(CallingCardAgiAudio.WELCOME,validKeys,0);
                    }
                    if(languageKey==0) languageKey = this._getOption(CallingCardAgiAudio.ENTER_LANGUAGE,validKeys,0);
                    if(languageKey==0) languageKey = this._getOption("digits/" + (i+1),validKeys,0);
                    if(languageKey!=0) break;
                }
                if(languageKey==0) languageKey = this._getOption(CallingCardAgiAudio.SILENCE_1,validKeys,this.config.LANGUAGE_DIGIT_TIMEOUT*1000);

                if(languageKey == 0){
                    this.logger.info("No input detected after " + this.config.LANGUAGE_DIGIT_TIMEOUT + " seconds");
                }
                else{
                    this.logger.info("User input: " + languageKey);

                    int languageIndex = languageKey - '0';
                    if(languageIndex>0 && languageIndex <= availableLanguagesCount){
                        this.language = this.config.LANGUAGES[languageIndex-1].toLowerCase();
                        setVariable("CHANNEL(language)", this.language);
                        this.logger.info("Language selected: " + this.language);
                        this.step = CallingCardAgiStep.AUTHENTICATE;
                        return;
                    }
                    else{
                         this.logger.info("No language found for the pressed key");
                    }
                }
            }

            this.logger.info("User didn't selected a valid language after " + this.config.LANGUAGE_MAX_ATTEMPTS + " attempts");
            this.step = CallingCardAgiStep.HANGUP;
        }
        catch(AgiException ae){
            this.logger.error("An error ocurred while trying to authenticate user", ae);
            this.step = CallingCardAgiStep.HANGUP;
        }
    }

    private void cc_authenticate(){
        try{
            this.logger.info("Trying to authenticate user");
            CallingCardPin pin = null;

            this.currentAuthenticateData = new CallingCardAuthenticateData();
 this.currentAuthenticateData.setAccessType(this.accessType);
            this.currentAuthenticateData.setDnid(this.dnid);
 this.currentAuthenticateData.setEnterpriseId(this.config.ENTERPRISE_ID);

            switch (this.accessType) {

                case IVR:
                case IVR_MOBIL:
                      this.currentAuthenticateData.setAni(this.ani);
 while(++this.authenticationAttempt<=this.config.AUTHENTICATION_MAX_ATTEMPTS) {

                       if(this.accessType == CallingCardAccessType.IVR)
                        {
                        this.secret = this._getData(CallingCardAgiAudio.ENTER_SECRET,this.config.AUTHENTICATION_DIGIT_TIMEOUT*1000,this.config.MAX_PIN_SECRET_LENGTH);
                        }
                        else
                        {
                            //PARA QUE NO REPRODUZCA EL AUDIO
                            this.secret = this._getData(CallingCardAgiAudio.SILENCE_1,this.config.AUTHENTICATION_DIGIT_TIMEOUT*1000,this.config.MAX_PIN_SECRET_LENGTH);
                        }


                        if(secret == null){
                            this.logger.info("Null input detected");
 this._streamFile(CallingCardAgiAudio.NO_INPUT);
                        }
                        else if(secret.equals("-1")){
                            this.step = CallingCardAgiStep.HANGUP;
                            return;
                        }
                        else if(secret.contains("timeout")){
                            this.logger.info("No input detected after " + this.config.AUTHENTICATION_DIGIT_TIMEOUT + " seconds");
 this._streamFile(CallingCardAgiAudio.NO_INPUT);
                        }
                        else{
                            this.logger.info("User input: " + this.secret);
 this.currentAuthenticateData.setSecret(this.secret);
                            try {
                                                            pin = this.searchPin(this.currentAuthenticateData);
                            } catch (CallingCardInvalidDnByLocationException e) {
this.logger.error("Invalid Dn by location",e);
this.step = CallingCardAgiStep.PLAY_ALTERNATIVE_DN_AUDIO;
                                                            return;
                                                        } catch (CallingCardInvalidDnException e) {
this.logger.error("Invalid Dn",e);
this.step = CallingCardAgiStep.PLAY_ALTERNATIVE_DN_AUDIO;
                                                            return;
                                                        } catch (CallingCardNotMinBalanceException e) {
                                this.logger.error("This pin can't be used. Reason: The min balance is not enough to make any call.");
 this._streamFile(CallingCardAgiAudio.CALL_MIN_BALANCE);
                                this.step = CallingCardAgiStep.HANGUP;
                                return;
                            } catch (CallingCardGeneralException e) {
                                this.logger.error("This pin can't be used. Reason: General Remote Exception.");
                                this.logger.debug("Detail: " + e.getMessage());
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                                this.step = CallingCardAgiStep.HANGUP;
                                return;
                            } catch (Exception e) {
                                this.logger.error("This pin can't be used. Reason: General Local Exception.");
                                this.logger.debug("Detail: " + e.getMessage());
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                                this.step = CallingCardAgiStep.HANGUP;
                                return;
                            }
                            if(pin != null){
                                proccessPinData(pin, false);
                                return;
                            }
                            else{
                                this.logger.info("No pin found for this secret");
 this._streamFile(CallingCardAgiAudio.INVALID_SECRET);
                            }
                        }
                    }


                    this.logger.info("User couldn't be authenticated after " + this.config.AUTHENTICATION_MAX_ATTEMPTS + " attempts");
                    this.step = CallingCardAgiStep.HANGUP;

                break;

                case IVR_PIN_FREE:
                    this.logger.info("Trying to find a pin for this ani.");
                    this.currentAuthenticateData.setAni(this.ani);
                    try{
                        pin = this.searchPin(this.currentAuthenticateData);
                        if(pin != null){
                            try{
                                ccFdc = this.getDniByAccessNumber(ani, this.currentAuthenticateData.getDnid());

                                if(ccFdc == null && this.getVariable("P-CALLED-PARTY-ID") != null){
                                    ccFdc = new CallingCardFavoriteDestinationCombination();
                                    ccFdc.setDni(new Long(this.getVariable("P-CALLED-PARTY-ID")));
                                }
                            }
                            catch(NumberFormatException e)
                            {
                                this.logger.error("Invalid P-CALLED-PARTY-ID destination reason: " + e.getMessage());
                            }
                            catch(CallingCardGeneralException e)
                            {
                                this.logger.error("Invalid favorite destination reason: " + e.getMessage());
                            }
                            catch(Exception e)
                            {
                                this.logger.error("Unknow exception getting favorite destination: " + e.getMessage());
                            }
                        }

                    } catch (CallingCardInvalidDnByLocationException e) {
this.logger.error("Invalid Dn by location",e);
                                            this.step = CallingCardAgiStep.PLAY_ALTERNATIVE_DN_AUDIO;
                                            return;
                                        } catch (CallingCardInvalidDnException e) {
this.logger.error("Invalid Dn",e);
                                            this.step = CallingCardAgiStep.PLAY_ALTERNATIVE_DN_AUDIO;
                                            return;
                                        } catch (CallingCardNotMinBalanceException e) {
                        this.logger.error("This pin can't be used. Reason: The min balance is not enough to make any call.");
 this._streamFile(CallingCardAgiAudio.CALL_MIN_BALANCE);
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    } catch (CallingCardGeneralException e) {
                        this.logger.error("This pin can't be used. Reason: General Remote Exception.");
                        this.logger.debug("Detail: " + e.getMessage());
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    } catch (Exception e) {
                        this.logger.error("This pin can't be used. Reason: General Local Exception.");
                        this.logger.debug("Detail: " + e.getMessage());
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    }
                    if(pin!=null){
                        proccessPinData(pin, true);
                        return;
                    }
                    else{
                        this.accessType = CallingCardAccessType.IVR;
                        this.setVariable(serviceFamily.name() + "_ACCESS_TYPE", this.accessType.name());
                        this.setVariable("CDR(userfield)", InteraxTelephonyCdrFormater.formatCdrUserField(this.accessType, null, null, null));
                        this.step = CallingCardAgiStep.ASK_LANGUAGE;

                        this.logger.info("No pin found for this ani. Trying manual authentication");
                    }
                break;

                case IVR_DIALER:

                    this.logger.info("Trying to find a pin for this ani.");
                    this.currentAuthenticateData.setAni(this.ani);
                    try{
                        pin = this.searchPin(this.currentAuthenticateData);
                    } catch (CallingCardInvalidDnByLocationException e) {
this.logger.error("Invalid Dn by location",e);
                                            this.step = CallingCardAgiStep.PLAY_ALTERNATIVE_DN_AUDIO;
                                            return;
                                        } catch (CallingCardInvalidDnException e) {
this.logger.error("Invalid Dn",e);
                                            this.step = CallingCardAgiStep.PLAY_ALTERNATIVE_DN_AUDIO;
                                            return;
                                        } catch (CallingCardNotMinBalanceException e) {
                        this.logger.error("This pin can't be used. Reason: The min balance is not enough to make any call.");
 this._streamFile(CallingCardAgiAudio.CALL_MIN_BALANCE);
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    } catch (CallingCardGeneralException e) {
                        this.logger.error("This pin can't be used. Reason: General Remote Exception.");
                        this.logger.debug("Detail: " + e.getMessage());
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    } catch (Exception e) {
                        this.logger.error("This pin can't be used. Reason: General Local Exception.");
                        this.logger.debug("Detail: " + e.getMessage());
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    }
                    if(pin!=null){
                        proccessPinData(pin, true);
                        return;
                    }
                    else{
                        this.logger.info("No pin found for this ani. Invalid dialer call");
                        //TODO: Poner en variable de arch. de configuración.
                        int seconds = 5;
                        this.logger.info("Sending congestion tone for " + seconds + " seconds.");
                        exec("Congestion","5");
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    }


                case WEB_PHONE:

                    this.logger.info("Trying to find a pin for this secret.");
 this.currentAuthenticateData.setSecret(this.secret);
                    try{
                                            pin = this.searchPin(this.currentAuthenticateData);
                    } catch (CallingCardInvalidDnByLocationException e) {
this.logger.error("Invalid Dn by location",e);
                                            this.step = CallingCardAgiStep.PLAY_ALTERNATIVE_DN_AUDIO;
                                            return;
                                        } catch (CallingCardInvalidDnException e) {
this.logger.error("Invalid Dn",e);
                                            this.step = CallingCardAgiStep.PLAY_ALTERNATIVE_DN_AUDIO;
                                            return;
                                        } catch (CallingCardNotMinBalanceException e) {
                        this.logger.error("This pin can't be used. Reason: The min balance is not enough to make any call.");
 this._streamFile(CallingCardAgiAudio.CALL_MIN_BALANCE);
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    } catch (CallingCardGeneralException e) {
                        this.logger.error("This pin can't be used. Reason: General Remote Exception.");
                        this.logger.debug("Detail: " + e.getMessage());
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    } catch (Exception e) {
                        this.logger.error("This pin can't be used. Reason: General Local Exception.");
                        this.logger.debug("Detail: " + e.getMessage());
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    }
                    if(pin!=null){
                        proccessPinData(pin, true);
                        return;
                    }
                    else{
                        this.logger.info("No pin found for this secret. Invalid webphone call");
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    }
            }

        }
        catch(AgiException ae){
            this.logger.error("An error ocurred while trying to authenticate user", ae);
            this.step = CallingCardAgiStep.HANGUP;
        }
    }

        private void cc_play_alternative_dn_audio() {
            try {
                //this._streamFile(CallingCardAgiAudio.UNAVAILABLE);
//this._streamFile(CallingCardAgiAudio.PLEASE_DIAL_FOLLOWING_NUMBER);
                //FIXME: Extraer el número de ATC desde BD o un properties
                this.alternativeDn = getAlternativeDn();
this._streamFile(CallingCardAgiAudio.RESTRICTED_DN_1);
                sayDigits(this.alternativeDn);
this._streamFile(CallingCardAgiAudio.RESTRICTED_DN_2);
                sayDigits(this.alternativeDn);
                this.exec("Wait","1");
this._streamFile(CallingCardAgiAudio.RESTRICTED_DN_3);
                sayDigits(ATC_NUMBER);
                this.exec("Wait","1");
this._streamFile(CallingCardAgiAudio.RESTRICTED_DN_4);
                this.step = CallingCardAgiStep.HANGUP;
            } catch (AgiException ae) {
                this.logger.error("An error ocurred while trying to play custom audio", ae);
                this.step = CallingCardAgiStep.HANGUP;
            }
        }

    private void cc_askPassword(){
        try{
            this.logger.info("Trying to ask pin password");

            this.askPasswordAttempt = 0;
 while(++this.askPasswordAttempt<=this.config.ASK_PASSWORD_MAX_ATTEMPTS){

                String password = this._getData(CallingCardAgiAudio.ENTER_PASSWORD,this.config.ASK_PASSWORD_DIGIT_TIMEOUT*1000,this.config.MAX_PIN_PASSWORD_LENGTH);

                if(password == null){
                    this.logger.info("Null input detected");
                    this._streamFile(CallingCardAgiAudio.NO_INPUT);
                    this.step = CallingCardAgiStep.AUTHENTICATE;
                }
                else if(password.equals("-1")){
                    this.step = CallingCardAgiStep.HANGUP;
                    return;
                }
                else if(password.contains("timeout")){
                    this.logger.info("No input detected after " + this.config.ASK_PASSWORD_DIGIT_TIMEOUT + " seconds");
                    this._streamFile(CallingCardAgiAudio.NO_INPUT);
                    this.step = CallingCardAgiStep.AUTHENTICATE;
                }
                else{
                    this.logger.info("User input: " + password);
                    String md5 = Encoder.MD5(password);
                    if(md5.equals(this.pin.getPassword())){
                        this.logger.info("Password matches. User authenticated sucessfully");
                        this.logger.info("Pin balance: " + pin.getCurrentBalance());
                        this.step = CallingCardAgiStep.AUTHORIZE;
                        return;
                    }
                    else{
                        this.logger.info("Invalid password. User authentication failed");
 this._streamFile(CallingCardAgiAudio.INVALID_PASSWORD);
                        this.step = CallingCardAgiStep.AUTHENTICATE;
                        return;
                    }
                }
            }
        }
        catch(Exception e){
            this.logger.error("An error ocurred while trying to ask pin password", e);
            this.logger.debug("Detail: " + e.getMessage());
            this.step = CallingCardAgiStep.HANGUP;
        }
    }

    private void cc_authorize(){
        try{
            this.logger.info("Trying to authorize caller");

            CallingCardPinStatus pinStatus = this.pin.getStatus();
            switch(pinStatus){

                case PIN_DISABLED:
                case LOT_DISABLED:
                    this.logger.info("Caller not authorized. Reason: " + pinStatus.name());
 this._streamFile(CallingCardAgiAudio.DISABLED_PIN);
                    this.step = CallingCardAgiStep.HANGUP;
                    break;

                case PIN_EXPIRED:
                case LOT_EXPIRED:
                    this.logger.info("Caller not authorized. Reason: " + pinStatus.name());
 this._streamFile(CallingCardAgiAudio.EXPIRED_PIN);
                    this.step = CallingCardAgiStep.HANGUP;
                    break;

                case ENABLED:
                    this.logger.info("Caller authorized successfully");

                    switch(this.accessType){
                        case IVR:
                        case IVR_PIN_FREE:
                           

                                boolean sayBalanceAudios = true;
                                List<CallingCardPinQuota> callingCardQuotas = this.pin.getCallingCardPinQuotas();
                                Float currentBalance = this.pin.getCurrentBalance();

                                if((callingCardQuotas != null && callingCardQuotas.size() > 0) && currentBalance == 0){
                                    sayBalanceAudios = false;
                                }
                            if(!isMobile)
                            {
                                if(sayBalanceAudios){
 this._streamFile(CallingCardAgiAudio.BALANCE_IS);
                                    int units = currentBalance.intValue();
                                    this._sayNumber("" + units, "cc/digits");
 this._streamFile(CallingCardAgiAudio.BALANCE_UNITS + this.pin.getCurrency().toLowerCase());
 this._streamFile(CallingCardAgiAudio.BALANCE_AND);
                                    String cents = "" + ((currentBalance * 100) % 100);
                                    this._sayNumber(cents);
 this._streamFile(CallingCardAgiAudio.BALANCE_CENTS + this.pin.getCurrency().toLowerCase());
                                }
                            }
                           
                        break;

                        case IVR_DIALER:
                        case IVR_MOBIL:
                        case WEB_PHONE:
                        break;
                    }

                    switch(this.serviceType){
                        case CALLING_CARD:
                        case CALLING_CARD_PLATINUM:
                            this.step = CallingCardAgiStep.VALIDATE_DESTINY;
                        break;

                        case BUSINESS_KIT:
                            this.step = CallingCardAgiStep.SPECIAL_MENU;
                            break;
                    }

                    break;
            }
        }
        catch(Exception e){
            this.logger.error("An error ocurred while trying to authorize caller", e);
            this.logger.debug("Detail: " + e.getMessage());
            this.step = CallingCardAgiStep.HANGUP;
        }
    }

    private void cc_specialMenu(){
        try{
            this.logger.info("Trying to ask for an option");

 while(++this.specialMenuAttempt<=this.config.SPECIAL_MENU_MAX_ATTEMPTS){

                String validKeys = "1234567890*#";
                char specialMenuKey = this._getOption(CallingCardAgiAudio.SPECIAL_MENU_OPTIONS,validKeys,this.config.SPECIAL_MENU_DIGIT_TIMEOUT*1000);;

                if(specialMenuKey == 0){
                    this.logger.info("No input detected after " + this.config.SPECIAL_MENU_DIGIT_TIMEOUT + " seconds");
                }
                else{
                    this.logger.info("User input: " + specialMenuKey);

                    int optionIndex = specialMenuKey - '0';
                    switch(optionIndex){

                        case 1:
                            this.logger.info("Option 1 selected: Validate destiny");
                            this.step = CallingCardAgiStep.VALIDATE_DESTINY;
                            return;

                        case 2:
                            this.logger.info("Option 2 selected: Change password");
                            this.step = CallingCardAgiStep.CHANGE_PASSWORD;
                            return;

                        default:
 this._streamFile(CallingCardAgiAudio.INVALID_OPTION);
                            this.logger.info("No option found for the pressed key");
                    }
                }
            }

            this.logger.info("User didn't selected a valid option after " + this.config.SPECIAL_MENU_DIGIT_TIMEOUT + " attempts");
            this.step = CallingCardAgiStep.HANGUP;
        }
        catch(Exception e){
            this.logger.error("An error ocurred while trying to ask for an option", e);
            this.logger.debug("Detail: " + e.getMessage());
            this.step = CallingCardAgiStep.HANGUP;
        }
    }

    private void cc_changePassword(){
        try{
            this.logger.info("Trying to change pin password");

            this.changePasswordAttempt = 0;
 while(++this.changePasswordAttempt<=this.config.CHANGE_PASSWORD_MAX_ATTEMPTS){

                this.logger.info("Asking new password");
                String newPassword = this._getData(CallingCardAgiAudio.ENTER_NEW_PASSWORD,this.config.CHANGE_PASSWORD_DIGIT_TIMEOUT*1000,this.config.MAX_PIN_PASSWORD_LENGTH);

                if(newPassword == null){
                    this.logger.info("Null input detected");
                    this._streamFile(CallingCardAgiAudio.NO_INPUT);
                }
                else if(newPassword.equals("-1")){
                    this.step = CallingCardAgiStep.HANGUP;
                    return;
                }
                else if(newPassword.contains("timeout")){
                    this.logger.info("No input detected after " + this.config.CHANGE_PASSWORD_DIGIT_TIMEOUT + " seconds");
                    this._streamFile(CallingCardAgiAudio.NO_INPUT);
                }
                else{
                    this.logger.info("User input: " + newPassword);

                    this.logger.info("Asking new password confirmation");
                    String newPasswordConfirmation = this._getData(CallingCardAgiAudio.CONFIRM_NEW_PASSWORD,this.config.CHANGE_PASSWORD_DIGIT_TIMEOUT*1000,this.config.MAX_PIN_PASSWORD_LENGTH);

                    if(newPasswordConfirmation == null){
                        this.logger.info("Null input detected");
 this._streamFile(CallingCardAgiAudio.NO_INPUT);
                    }
                    else if(newPasswordConfirmation.equals("-1")){
                        this.step = CallingCardAgiStep.HANGUP;
                        return;
                    }
                    else if(newPasswordConfirmation.contains("timeout")){
                        this.logger.info("No input detected after " + this.config.CHANGE_PASSWORD_DIGIT_TIMEOUT + " seconds");
 this._streamFile(CallingCardAgiAudio.NO_INPUT);
                    }
                    else{
                        this.logger.info("User input: " + newPasswordConfirmation);

 if(newPassword.equals(newPasswordConfirmation)){
                            this.logger.info("New password and confirmation matches. Trying to change pin password");

                            String md5 = Encoder.MD5(newPassword);
 if(this.updatePinPassword(this.pin.getSerial(), md5)){

                                this.pin.setPassword(md5);
 this._streamFile(CallingCardAgiAudio.PASSWORD_CHANGE_SUCCESS);
                                this.logger.info("Pin password changed successfully");
                                this.step = CallingCardAgiStep.SPECIAL_MENU;
                                return;
                            }
                            else{
                                this.logger.info("Pin password couldn't be changed");
 this._streamFile(CallingCardAgiAudio.PASSWORD_CHANGE_ERROR);
                                this.step = CallingCardAgiStep.SPECIAL_MENU;
                                return;
                            }

                        }
                        else{
                            this.logger.info("New password and confirmation doesn't match.");
 this._streamFile(CallingCardAgiAudio.NEW_PASSWORD_MISMATCH);
                        }
                    }
                }
            }

            this.logger.info("Pin password couldn't be changed after " + this.config.CHANGE_PASSWORD_MAX_ATTEMPTS + " attempts");
            this.step = CallingCardAgiStep.HANGUP;

        }
        catch(Exception e){
            this.logger.error("An error ocurred while trying to change pin password", e);
            this.logger.debug("Detail: " + e.getMessage());
            this.step = CallingCardAgiStep.HANGUP;
        }
    }

    private void cc_validateDestiny(){

        try{

            this.logger.info("Trying to validate destiny");


 while(this.callAttempt++<=this.config.VALIDATE_DESTINY_MAX_ATTEMPTS){

                switch(this.accessType){

                    case IVR:
                    case IVR_MOBIL:
                    case IVR_PIN_FREE:

                        String destiny = null;
                        if(ccFdc != null){
                            destiny = "" +  ccFdc.getDni();
                        }else{

                            if(this.accessType == CallingCardAccessType.IVR_MOBIL || isMobile == true)
                            {
                      
                                destiny = this._getData(CallingCardAgiAudio.SILENCE_1,this.config.VALIDATE_DESTINY_DIGIT_TIMEOUT  * 1000);
                            }
                            else
                            {
                               destiny = this._getData(CallingCardAgiAudio.ENTER_DESTINY,this.config.VALIDATE_DESTINY_DIGIT_TIMEOUT*1000);
                            }

                        }

                        if(destiny == null){
                            this.logger.info("Null input detected");
 this._streamFile(CallingCardAgiAudio.NO_INPUT);
                            continue;
                        }
                        else if(destiny.equals("-1")){
                            this.step = CallingCardAgiStep.HANGUP;
                            return;
                        }
                        else if(destiny.contains("timeout")){
                            this.logger.info("No input detected after " + this.config.VALIDATE_DESTINY_DIGIT_TIMEOUT + " seconds");
                            if(!isMobile) this._streamFile(CallingCardAgiAudio.NO_INPUT);
                            continue;
                        }
                        else{
                            this.logger.info("User input: " + destiny);
                            this.dni = destiny;
                        }

                    break;

                    case IVR_DIALER:
                    case WEB_PHONE:
                        //this.config.VALIDATE_DESTINY_MAX_ATTEMPTS = 1;
                    break;
                }

                this.logger.info("Trying to match dialed number to a service prefix.");
                CallingCardAgiInputPrefix callingCardAgiprefix = this.config.findInputPrefix(this.dni);
                if(callingCardAgiprefix==null){
                    this.logger.info("Prefix not found. Invalid destiny");
 this._streamFile(CallingCardAgiAudio.CALL_INVALID_DESTINY);
                    continue;
                }
                else{
                    this.logger.info("Prefix found: " + callingCardAgiprefix.getPrefix() + "-->" + callingCardAgiprefix.getPadding());
                    int length = callingCardAgiprefix.getPrefix().length();
 if(InteraxTelephonyAgiServer.getConfig().DEFAULT_INPUT_PREFIX.equals(callingCardAgiprefix.getPrefix())){
                        length = 0;
                    }
                    this.dni = callingCardAgiprefix.getPadding() + this.dni.substring(length);
                    this.logger.info("Transformed DNI is: " + this.dni);
                }

                this.currentRequestData = new CallingCardRequestData();
                this.currentRequestData.setAni(this.ani);
                this.currentRequestData.setCdrId(this.cdrId);
                this.currentRequestData.setDni(this.dni);
                this.currentRequestData.setDnid(this.dnid);
 this.currentRequestData.setPinSerial(this.pin.getSerial());
 this.currentRequestData.setServiceType(this.serviceType);



//                FIXME quitar este cable luego de agregar en el CallingCardRater el AccessType Correspondiente
                if(this.accessType == CallingCardAccessType.IVR_MOBIL)
                {
 this.currentRequestData.setAccessType(CallingCardAccessType.IVR);
                }
                else
                {
 this.currentRequestData.setAccessType(this.accessType);
                }
 this.currentRequestData.setServiceFamily(serviceFamily);
                this.logger.debug("Current request data detail: ", this.currentRequestData);

                try
                 {
                    this.logger.info("Trying to create a reservation for the call");
                    this.currentReservation = this.createReservation(this.currentRequestData);

                    this.logger.info("Reservation for the call created successfully");
                    this.logger.debug("Reservation detail: ", this.currentReservation);

                    Integer availableSeconds = this.currentReservation.getAvailableSeconds();

                    if(availableSeconds == ServiceReservation.UNLIMITED_CALL_TIME){
                        this.setVariable(serviceFamily.name() + "_UNLIMITED_CALL", "TRUE");
                        //TODO si se quiere permitir mas de una llamada a destinos no limitados
                        //this.pinHasUnlimitedQuota = true;
                    }
                    else{

                        if(!isMobile)
                        {
                            this.setVariable(serviceFamily.name() + "_UNLIMITED_CALL", "FALSE");
                            //TODO si se quiere permitir mas de una llamada a destinos no limitados
                            //this.pinHasUnlimitedQuota = false;

                            if(availableSeconds < ServiceReservation.BASE_TIME_IN_SECONDS)
 this.setVariable(serviceFamily.name() + "_UNMONITORED_CALL", "TRUE");

                            boolean sayAvailableTime = true;

                            switch(this.serviceType){
                                case CALLING_CARD_PLATINUM:
                                    sayAvailableTime = false;
                                break;
                            }

                            if(sayAvailableTime){
                                Integer minutes = availableSeconds / ServiceReservation.BASE_TIME_IN_SECONDS;
                                Integer seconds = availableSeconds % ServiceReservation.BASE_TIME_IN_SECONDS;
 this._streamFile(CallingCardAgiAudio.CALL_TIME_IS);
                                boolean saySeconds = seconds > 0;

                                if(minutes > 0){
                                    this._sayNumber("" + minutes);
                                    if(minutes==1)
 this._streamFile(CallingCardAgiAudio.MINUTE);
                                    else
 this._streamFile(CallingCardAgiAudio.MINUTES);

                                    if(saySeconds)
 this._streamFile(CallingCardAgiAudio.AND);
                                }

                                if(saySeconds){
                                    this._sayNumber("" + seconds);
                                    if(seconds==1)
 this._streamFile(CallingCardAgiAudio.SECOND);
                                    else
 this._streamFile(CallingCardAgiAudio.SECONDS);
                                }



                            }
                        }//Si no es movil
                    }


                    this.logger.info("Destiny validated successfully");
                    this.step = CallingCardAgiStep.MAKE_CALL;
                    return;
                 }

                catch(CallingCardInvalidPinException pnfe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: Pin not found");
 this._streamFile(CallingCardAgiAudio.INVALID_SECRET);
                }
                catch(CallingCardNotEnoughBalanceException nebe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: No enough balance");
 this._streamFile(CallingCardAgiAudio.CALL_NO_ENOUGH_BALANCE);
                }
                catch(CallingCardRestrictedCallException rnfe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: Restricted call");
 this._streamFile(CallingCardAgiAudio.CALL_RESTRICTED);
                }
                catch(CallingCardCreditLimitExceededException rnfe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: Credit limit exceeded");
 this._streamFile(CallingCardAgiAudio.CREDIT_LIMIT_EXCEEDED);
                }
                catch(CallingCardRateNotFoundException rnfe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: Rate not found");
 this._streamFile(CallingCardAgiAudio.CALL_RATE_NOT_FOUND);
                }
                catch(CallingCardInvalidServiceTypeException pnfe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: Invalid service type");
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                    this.step = CallingCardAgiStep.HANGUP;
                    return;
                }
                catch(CallingCardInvalidAccessTypeException pnfe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: Invalid access type");
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                    this.step = CallingCardAgiStep.HANGUP;
                    return;
                }
                catch(CallingCardInvalidCountryCodeException dnfe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: Destination not found");
 this._streamFile(CallingCardAgiAudio.CALL_INVALID_DESTINY);
                }
                catch(CallingCardInvalidDnException dnfe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: Invalid access number");
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                    this.step = CallingCardAgiStep.HANGUP;
                    return;
                }
                catch(CallingCardOverdueInvoiceException pnfe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: Max overdued invoices limit reached.");
this._streamFile(CallingCardAgiAudio.OVERDUE_INVOICE_LIMIT_EXCEEDED );
                }
                catch(CallingCardUnauthorizedCallException pnfe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: Requested call not authorized.");
this._streamFile(CallingCardAgiAudio.CALL_UNATHORIZED);
                }
                catch(CallingCardMaxConcurrentCallException pnfe){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: Max concurrent call limit reached.");
this._streamFile(CallingCardAgiAudio.MAX_CONCURRENT_CALL_LIMIT_EXCEEDED);
                }
                catch(CallingCardGeneralException ge){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: General Remote Exception");
                    this.logger.debug("Detail: " + ge.getMessage());
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                    this.step = CallingCardAgiStep.HANGUP;
                    return;
                }
                catch(Exception e){
                    this.logger.info("Reservation for the call couldn't be created successfully. Reason: General Local Exception");
                    this.logger.debug("Detail: " + e.getMessage());
 this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                    this.step = CallingCardAgiStep.HANGUP;
                    return;
                }

                ccFdc = null; //REINICIO EL NUMERO FAVORITO
            }

            this.logger.info("Destiny couldn't be validated after " + this.config.VALIDATE_DESTINY_MAX_ATTEMPTS + " attempts");
            this.step = CallingCardAgiStep.HANGUP;
        }
        catch(AgiException ae){
            this.logger.error("An error ocurred while trying to validate destiny", ae);
            this.step = CallingCardAgiStep.HANGUP;
        }
    }

    private void cc_makeCall(){
        try{
            this.logger.info("Trying to call");

            this.setVariable(serviceFamily.name() + "_DNI", this.dni);
            this.setVariable(serviceFamily.name() + "_PIN_SERIAL", "" + this.pin.getSerial());
            this.setVariable(serviceFamily.name() + "_RESERVATION", "" + this.currentReservation.getId());
            String nextCdrId = this.cdrId + "-" + (++this.cdrCallAttempt);
            this.setVariable(serviceFamily.name() + "_CHILDREN_CDR_ID", nextCdrId);
            this.setVariable("LIMIT_WARNING_FILE", "beep");

            String asteriskId = this.getVariable("ASTERISK_SERVER");
            currentUserField = InteraxTelephonyCdrFormater.formatCdrUserField(this.accessType, this.serviceType, this.pin.getSerial(), "" + this.cdrCallAttempt);

            callFile = new File(this.onlineCallPersistenceFolder + nextCdrId);
            callFile.getParentFile().mkdirs();
            Calendar startDate = Calendar.getInstance();
            CdrCallDetailRecord cdr = new CdrCallDetailRecord();

            cdr.setAccountcode(this.currentAccountCode);
            cdr.setAmaflags(3); //DOCUMENTATION
            cdr.setAnswerDate(0); //AMI
            cdr.setAsteriskId(asteriskId);
            cdr.setBillsec(0);
            cdr.setChannel(this.getVariable("CDR(channel)"));
            cdr.setClid(this.getVariable("CDR(clid)"));
            cdr.setDcontext(this.getVariable("CDR(dcontext)"));
            cdr.setDisposition("UNKNOWN");
            cdr.setDst(this.dni);
            cdr.setDstchannel(null); //AMI
            cdr.setDuration(0);
            cdr.setHangupCause(0);
            cdr.setId(0);
            cdr.setLastapp("ResetCdr");
            cdr.setLastdata("w");
            cdr.setSrc(this.ani);
            cdr.setStartdate(startDate.getTime().getTime());
            cdr.setUniqueid(nextCdrId);
            cdr.setUserfield(currentUserField);

            byte fileDataCrash[] = (GeneralUtils.toText(cdr)).getBytes();

            fileManager = new FileUtils();
            this.logger.info("Trying to create onlinecall persistence file: " + callFile.getName());
            try{
                fileManager.upload(callFile,fileDataCrash, false);
                this.logger.info("Onlinecall persistence file created successfully");
            }
            catch(Exception ex){
                this.logger.error("An error ocurred while trying to create onlinecall persistence file");
            }

            String dialOptions = "";
            //TEST_MODE:5
            if(this.agiTestMode){
                //dialOptions += this.config.DIAL_TEST_PREFIX + this.dni + "@" + this.config.DIAL_TEST_GATEWAY;
                dialOptions += this.createDialOption(serviceFamily.name(), this.serviceType.name(), accessType.name(), this.config.ENTERPRISE_ID, this.dni);
            }
            else if(this.currentReservation.isInternCall()){
                dialOptions = "Local/" + this.dni +
                "@" + InteraxTelephonyAgiServer.getConfig().DID_INCOMING_CONTEXT;
            }
            else{
            //    dialOptions += this.config.DIAL_PREFIX + this.dni + "@" + this.config.DIAL_GATEWAY;
                dialOptions += this.createDialOption(serviceFamily.name(), this.serviceType.name(), accessType.name(), this.config.ENTERPRISE_ID, this.dni);
            }

             dialOptions += "|" + this.config.DIAL_TIMEOUT;
            if(this.currentReservation.getAvailableSeconds() != ServiceReservation.UNLIMITED_CALL_TIME){
                this.logger.info("Call limited to: " + this.currentReservation.getAvailableSeconds() + " seconds by Rater");
                dialOptions += "|L(" + this.currentReservation.getAvailableSeconds() * 1000 + ":30000:30000)";
            }else{
                if(this.config.UNLIMITED_CALLS_MAX_MINUTES > 0){
                    int limitSeconds = (int) ((this.config.UNLIMITED_CALLS_MAX_MINUTES + (this.config.UNLIMITED_CALLS_RANDOM_MINUTES * Math.random())) * 60);
                    this.logger.info("Call limited to: " + limitSeconds + " seconds by Agi");
                    dialOptions += "|L(" + limitSeconds * 1000 + ":30000:30000)";
                }else{
                    // UNLIMITED
                    this.logger.info("Unlimited call");
                }
            }

            this.logger.info("Dial options: " + dialOptions);

            exec("ResetCdr","");
            String lastCdrUniqueId = "";
            String lastCdrAnswer = "";
            String lastCdrStart = "";
            String lastCdrEnd = "";
            String lastCdrDuration = "";
            String lastCdrBillsec = "";
            String dialStatusStr = "";
            int lastCdrHangupCause = 0;

            try{

                if(this.config.IS_MASK_ANI){
                    if(this.dni.startsWith("58")){
                        this.setVariable("CALLERID(num)", generateTestAni());
                    }
                }
                exec("Dial", dialOptions);
                this.setVariable("CDR(src)", this.ani);
                this.setVariable("CDR(dst)", this.dni);
                this.setVariable("CDR(uniqueid)", nextCdrId);
                this.setVariable("CDR(userfield)", currentUserField);

                exec("ResetCdr","w");


                lastCdrUniqueId = getVariable("LAST_CDR_UNIQUEID");
                lastCdrAnswer = getVariable("LAST_CDR_ANSWER");
                lastCdrStart = getVariable("LAST_CDR_START");
                lastCdrEnd = getVariable("LAST_CDR_END");
                lastCdrDuration = getVariable("LAST_CDR_DURATION");
                lastCdrBillsec = getVariable("LAST_CDR_BILLSEC");
                dialStatusStr = getVariable("DIALSTATUS").replace(" ", "");

                try{
                    lastCdrHangupCause = Integer.parseInt(getVariable("LAST_CDR_HANGUP_CAUSE"));
                }
                catch(Exception e){
                     lastCdrHangupCause = -1;
                }


            }
            catch(AgiException agiException){

                this.logger.warn("!Asterisk is down");
                String stringObject = new String(fileManager.download(callFile.getParent(),"/" + callFile.getName()));
                CdrCallDetailRecord cdrFromFile = (CdrCallDetailRecord) GeneralUtils.fromText(stringObject);

                Long stopDate = Calendar.getInstance().getTimeInMillis();
                Long duration =  Long.valueOf(Math.round((stopDate - cdrFromFile.getStartdate())/1000));
                lastCdrDuration = "" + duration;
                lastCdrEnd = GeneralUtils.dateCalendar2String(GeneralUtils.dateLong2Calendar(Long.valueOf(cdrFromFile.getStopDate())));
                lastCdrUniqueId = nextCdrId;
                lastCdrStart = GeneralUtils.dateCalendar2String(GeneralUtils.dateLong2Calendar(Long.valueOf(cdrFromFile.getStartdate())));

                if(cdrFromFile.getAnswerDate() > 0){
                     Long billSeconds = Long.valueOf(Math.round((stopDate - cdrFromFile.getAnswerDate())/1000));
                     lastCdrAnswer = GeneralUtils.dateCalendar2String(GeneralUtils.dateLong2Calendar(Long.valueOf(cdrFromFile.getAnswerDate())));
                     lastCdrBillsec = "" +billSeconds;
                     lastCdrHangupCause = 16;
                     dialStatusStr = "ANSWER";

                     cdrFromFile.setBillsec(billSeconds);
                     cdrFromFile.setDisposition("ANSWERED");
                }else{
                     lastCdrAnswer = "NULL";
                     lastCdrBillsec = "0";
                     lastCdrHangupCause = 0;
                     dialStatusStr = "UNKNOWN";
                }

               cdrFromFile.setDuration(duration);
               cdrFromFile.setHangupCause(lastCdrHangupCause);
               cdrFromFile.setStopDate(stopDate);

               fileManager.upload(callFile, GeneralUtils.toText(cdrFromFile).getBytes(), false);

               this.logger.info("Calculing rater data");
               this.logger.info("uniqueId:" + lastCdrUniqueId);
               this.logger.info("lastCdrStart:" + lastCdrStart);
               this.logger.info("lastCdrAnswer:" + lastCdrAnswer);
               this.logger.info("lastCdrEnd:" + lastCdrEnd);
               this.logger.info("lastCdrDuration:" + lastCdrDuration);
               this.logger.info("lastCdrBillsec:" + lastCdrBillsec);

               File crashFile = new File(crashPersistenceFolder + callFile.getName());
               crashFile.getParentFile().mkdirs();
               this.logger.info("Moving file from: " + callFile.getAbsolutePath() + " to :" + crashFile.getAbsolutePath());
               if(callFile.renameTo(crashFile))
                     this.logger.info("File moved successfully");
               else
                    this.logger.info("Error moving file");
            }

            this.logger.info("Trying to delete onlinecall persistence file: " + callFile.getName());
            try{
                callFile.delete();
                this.logger.info("Onlinecall persistence file deleted successfully");
            }
            catch(Exception e){
                this.logger.error("An error ocurred while trying to delete onlinecall persistence file");
            }

            ServiceDialStatus dialStatus = ServiceDialStatus.valueOf(dialStatusStr);

        try{
            switch(dialStatus){
                case ANSWER:
                    this.logger.info("Call completed successfully");
                break;

                case BUSY:
                    this.logger.info("Call couldn't be completed: Destiny is busy");
                    this._streamFile(CallingCardAgiAudio.CALL_BUSY);
                break;

                case NOANSWER:
                    this.logger.info("Call couldn't be completed: Destiny doesn't answer");
 this._streamFile(CallingCardAgiAudio.CALL_NOANSWER);
                break;

                case CHANUNAVAIL:
                    this.logger.info("Call couldn't be completed: Destiny is not available");
 this._streamFile(CallingCardAgiAudio.CALL_INVALID_DESTINY);
                break;

                case CANCEL:
                    this.logger.info("Call couldn't be completed: Canceled by the user");
                break;

                case UNKNOWN:
                    this.logger.info("Crash");
                break;

                default:
                    this.logger.info("Call couldn't be completed: Tech problem");
 //this._streamFile(CallingCardAgiAudio.CALL_TECH_PROBLEM);
                    this._streamFile(CallingCardAgiAudio.REORDER);
                break;
            }

        }catch(Exception e){
            this.logger.info("A exception when try to play a file on channel");
        }


            if(dialStatus == ServiceDialStatus.ANSWER){
                CallingCardCommitData callingCardCommitData = new CallingCardCommitData();
                try{
                    this.logger.info("Trying to collect commit data");

                    Calendar startTime = GeneralUtils.dateString2Calendar(lastCdrStart);
                    Calendar answerTime = GeneralUtils.dateString2Calendar(lastCdrAnswer);
                    Calendar endTime = GeneralUtils.dateString2Calendar(lastCdrEnd);
                    Integer callDuration = Integer.parseInt(lastCdrDuration);
                    Integer billSeconds = Integer.parseInt(lastCdrBillsec);

                    callingCardCommitData.setStartTime(startTime);
                    callingCardCommitData.setAnswerTime(answerTime);
                    callingCardCommitData.setStopTime(endTime);
 callingCardCommitData.setCallDuration(callDuration);
 callingCardCommitData.setBillSeconds(billSeconds);
                    callingCardCommitData.setDialStatus(dialStatus);
                    callingCardCommitData.setCdrId(lastCdrUniqueId);
 callingCardCommitData.setReservationId(this.currentReservation.getId());
 callingCardCommitData.setHangupCause(lastCdrHangupCause);
 callingCardCommitData.setServiceFamily(serviceFamily);
 callingCardCommitData.setServiceType(this.serviceType);
 callingCardCommitData.setAccessType(this.accessType);
                    //TODO Elminar el archivo con la info del CDR y start time

                    this.logger.info("Commit data collected successfully");
                    this.logger.debug("Commit data detail: ", callingCardCommitData);

                    this.logger.info("Trying to commit reservation " + this.currentReservation.getId());

                    if (this.commitReservation(this.currentReservation.getId(), callingCardCommitData))
                        this.logger.info("Reservation " + this.currentReservation.getId() + " commited successfully.");
                    else
                        this.logger.info("Reservation " + this.currentReservation.getId() + " couldn't be commited.");

                    this.callAttempt = 1;

                }catch(Exception e){

                    this.logger.info("An error ocurred while trying to commit reservation");
                    this.logger.debug("Detail: " + e.getMessage());

                    File newFileCallingCardCommitData = new File(this.commitPersistenceFolder + callingCardCommitData.getCdrId());
 newFileCallingCardCommitData.getParentFile().mkdirs();

                    FileUtils fileManager = new FileUtils();
                    byte fileData[] = GeneralUtils.toText(callingCardCommitData).getBytes();

                    this.logger.info("Trying to create commit persistence file: " + newFileCallingCardCommitData.getName());
                    try{
 fileManager.upload(newFileCallingCardCommitData, fileData, false);
                        this.logger.info("Commit persistence file deleted successfully");
                    }
                    catch(Exception ex){
                        this.logger.error("An error ocurred while trying to delete commit persistence file");
                    }
                }
            }

            else{
                CallingCardCancelData callingCardCancelData = new CallingCardCancelData();
                try{
                    this.logger.info("Trying to collect cancel data");

                    boolean dirty = (dialStatus == ServiceDialStatus.UNKNOWN);

                    Calendar startTime = GeneralUtils.dateString2Calendar(lastCdrStart);
                    Calendar endTime = GeneralUtils.dateString2Calendar(lastCdrEnd);
                    Integer callDuration = Integer.parseInt(lastCdrDuration);

                    callingCardCancelData.setStartTime(startTime);
                    callingCardCancelData.setStopTime(endTime);
 callingCardCancelData.setCallDuration(callDuration);
                    callingCardCancelData.setDialStatus(dialStatus);
                    callingCardCancelData.setCdrId(lastCdrUniqueId);
 callingCardCancelData.setHangupCause(lastCdrHangupCause);
 callingCardCancelData.setServiceFamily(serviceFamily);
 callingCardCancelData.setServiceType(this.serviceType);
 callingCardCancelData.setReservationId(this.currentReservation.getId());
 callingCardCancelData.setAccessType(this.accessType);
                    callingCardCancelData.setDirty(dirty);

                    this.logger.info("Cancel data collected successfully");
                    this.logger.debug("Cancel data detail: ", callingCardCancelData);

                    this.logger.info("Trying to cancel reservation");
                    if (this.cancelReservation(this.currentReservation.getId(), callingCardCancelData)){
                        this.logger.info("Reservation " + this.currentReservation.getId() + " canceled successfully.");
                    }else{
                        this.logger.info("Reservation " + this.currentReservation.getId() + " couldn't be canceled.");
                    }

                }
                catch(Exception e){
                    this.logger.info("An error ocurred while trying to cancel reservation");
                    this.logger.debug("Detail: " + e.getMessage());

                    File newFileCallingCardCancelData = new File(this.cancelPersistenceFolder + callingCardCancelData.getCdrId());
 newFileCallingCardCancelData.getParentFile().mkdirs();

                    FileUtils fileManager = new FileUtils();
                    byte fileData[] = GeneralUtils.toText(callingCardCancelData).getBytes();

                    this.logger.info("Trying to create cancel persistence file: " + newFileCallingCardCancelData.getName());
                    try{
 fileManager.upload(newFileCallingCardCancelData, fileData, false);
                        this.logger.info("Cancel persistence file deleted successfully");
                    }
                    catch(Exception ex){
                        this.logger.error("An error ocurred while trying to delete cancel persistence file");
                    }

                }
            }

            switch(this.accessType){
                case IVR:
                case IVR_PIN_FREE:
                    if(dialStatus == ServiceDialStatus.ANSWER && this.pinHasUnlimitedQuota){
                        this.step = CallingCardAgiStep.HANGUP;
                    }else{
                        this.step = CallingCardAgiStep.VALIDATE_DESTINY;
                    }
                break;
                case IVR_MOBIL:
                case IVR_DIALER:
                case WEB_PHONE:
                    this.step = CallingCardAgiStep.HANGUP;
                break;
            }
        }
        catch(AgiException ae){
            this.logger.error("An error ocurred while trying to call", ae);
            this.step = CallingCardAgiStep.HANGUP;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRandowAni() {
        // TODO Auto-generated method stub
        return null;
    }



    private void cc_hangup(){
        try{
            this.logger.info("Trying to hangup the channel");
            hangup();
            this.logger.info("Channel hanged up successfully");

            this.step = CallingCardAgiStep.STOP;
        }
        catch(AgiException ae){
            this.logger.error("An error ocurred while trying to hangup the channel", ae);
            this.step = CallingCardAgiStep.STOP;
        }
    }

    // FUNCIONES AUXILIARES

    private void proccessPinData(CallingCardPin pin, boolean updateLanguage) throws AgiException{

        this.pin = pin;
        this.serviceType = this.pin.getServiceType();
        this.pinHasUnlimitedQuota = false;

        List<CallingCardPinQuota> pinQuotas = this.pin.getCallingCardPinQuotas();
        if(pinQuotas != null){
            for(CallingCardPinQuota callingCardPinQuota : pinQuotas){
                if(callingCardPinQuota.getInitQuantity() == ServiceReservation.UNLIMITED_CALL_TIME || callingCardPinQuota.getCurrentQuantity() == ServiceReservation.UNLIMITED_CALL_TIME){
                    this.pinHasUnlimitedQuota = true;
                    break;
                }
            }
        }

        this.setVariable(serviceFamily.name() + "_SERVICE_TYPE", this.serviceType.name());

        this.logger.info("Pin found. Service Type: " + this.serviceType.name() + ". Serial: " + this.pin.getSerial());
        this.setVariable("CDR(userfield)", InteraxTelephonyCdrFormater.formatCdrUserField(this.accessType, this.serviceType, this.pin.getSerial(), null));
        exec("ForkCdr","R");
        cdrForked = true;

        if(!this.config.SERVICE_TYPES.contains(this.serviceType)){
            this.logger.info("Invalid service type for this dnid. Aborting call");
            this.step = CallingCardAgiStep.HANGUP;
        }
        else{
            if(updateLanguage){
                String defaultLanguage = this.config.LANGUAGES[0];
                String pinLanguage = this.pin.getLanguage();
                String newLanguage = null;

                this.logger.info("Validating Pin language: " + pinLanguage);
                int availableLanguagesCount = this.config.LANGUAGES.length;
                for (int i=0; i<availableLanguagesCount; i++) {
 if(this.config.LANGUAGES[i].equalsIgnoreCase(pinLanguage)){
                        newLanguage = this.config.LANGUAGES[i];
                        break;
                    }
                }

                if(newLanguage!=null){
                    this.logger.info("Pin language found. ");
                    this.language = newLanguage;
                }
                else{
                    this.logger.info("Pin language not found. Using default language. " + defaultLanguage);
                    this.language = defaultLanguage;
                }

                this.logger.info("Using language: " + this.language);
//                this.setVariable("CHANNEL(language)", this.language.toLowerCase());
            }

                        //if(checkDn()) {
                            switch(this.serviceType){
                case BUSINESS_KIT:
                    this.logger.info("Pin requires password");
                    this.step = CallingCardAgiStep.ASK_PASSWORD;
                    return;

                case CALLING_CARD:
                case CALLING_CARD_PLATINUM:
                    this.logger.info("User authenticated sucessfully");
                    this.logger.info("Pin balance: " + pin.getCurrentBalance());
                    this.step = CallingCardAgiStep.AUTHORIZE;
                    return;
                            }
                        /*} else {
                            this.step = CallingCardAgiStep.PLAY_ALTERNATIVE_DN_AUDIO;
                        }*/

        }
    }


    // FUNCIONES REMOTAS

        private String getAlternativeDn() {

            callingCardPinEJB = (CallingCardPinEJB)this.genericEjbCaller.getGenericController("ejb/CallingCardPinEJB", this.config.PIN_EJB_SERVER, this.config.PIN_EJB_PORT);
            String dn = "";
            try {
                dn = callingCardPinEJB.getAccessNumberByAni(null,ani);
            } catch(Exception e) {
                this.logger.error("An error ocurred while trying to get alternative access numbers", e);
            }

            return dn;
        }

    private CallingCardPin searchPin(CallingCardAuthenticateData callingCardAuthenticateData) throws CallingCardException, AgiException {

        //RATER_TEST_MODE:3
        if(this.raterTestMode){
            CallingCardPin pin = null;
            pin = new CallingCardPin();
            String raterService = this.getVariable("RATER_SERVICE");
            if(raterService!=null && !"null".equalsIgnoreCase(raterService)){
                this.logger.info("RATER SERVICE IS:" + raterService);
 pin.setServiceType(CallingCardServiceType.valueOf(raterService));
            }else{
                this.logger.info("RATER SERVICE DEFAULT IS: CALLING_CARD");
 pin.setServiceType(CallingCardServiceType.CALLING_CARD);
 //pin.setServiceType(CallingCardServiceType.BUSINESS_KIT);
            }
            pin.setSerial(123456783L);
            pin.setPassword("c26820b8a4c1b3c2aa868d6d57e14a79"); // 1357
            pin.setStatus(CallingCardPinStatus.ENABLED);
            pin.setCurrentBalance(1000.54F);
            pin.setCurrency("VEB");
            pin.setLanguage("ES");
            switch(callingCardAuthenticateData.getAccessType()){
                case IVR:
                    return pin;

                case IVR_DIALER:
 pin.setServiceType(CallingCardServiceType.CALLING_CARD_PLATINUM);
                    return pin;

                case IVR_PIN_FREE:
                    if(this.raterTestPinFree){
                        return pin;
                    }
                    else
                        return null;
            }
        }
        callingCardPinEJB = (CallingCardPinEJB)this.genericEjbCaller.getGenericController("ejb/CallingCardPinEJB", this.config.PIN_EJB_SERVER, this.config.PIN_EJB_PORT);
        return (CallingCardPin)callingCardPinEJB.searchPin(callingCardAuthenticateData);

    }

    private CallingCardFavoriteDestinationCombination getDniByAccessNumber(String ani, String accessNumber) throws CallingCardException
    {

        CallingCardPinEJB pinEJB = (CallingCardPinEJB) this.genericEjbCaller.getGenericController("ejb/CallingCardPinEJB", this.config.PIN_EJB_SERVER, this.config.PIN_EJB_PORT);
        CallingCardFavoriteDestinationCombination ccFdc = pinEJB.getFavoriteDestinationByAni(ani, accessNumber);
        return ccFdc;
    }


    private boolean updatePinPassword(Long serial, String md5){

        //RATER_TEST_MODE:4
        if(this.raterTestMode){
            return true;
        }

        callingCardPinEJB = (CallingCardPinEJB)this.genericEjbCaller.getGenericController("ejb/CallingCardPinEJB", this.config.PIN_EJB_SERVER, this.config.PIN_EJB_PORT);
        try {
            return callingCardPinEJB.updatePinPassword(serial, md5);
        } catch (CallingCardInvalidPinException e) {
            this.logger.error("Invalid pin, couldn't update the pin password.");
            return false;
        } catch (CallingCardException e) {
            this.logger.info("Tech problem, couldn't update the pin password.");
            return false;
        }
    }

    private CallingCardReservation createReservation(CallingCardRequestData callingCardRequestData) throws Exception {

        //RATER_TEST_MODE:5
        if(this.raterTestMode){
            CallingCardReservation callingCardReservation = new CallingCardReservation();
            callingCardReservation.setId(1000000L);
            if(this.raterTestAvailablesSeconds != -1)
 callingCardReservation.setAvailableSeconds(this.raterTestAvailablesSeconds);
            else
 callingCardReservation.setAvailableSeconds(ServiceReservation.BASE_TIME_IN_SECONDS);

 callingCardReservation.setInternCall(this.raterTestInternCall);
            return callingCardReservation;
        }

        raterEJB = (RaterEJB)this.genericEjbCaller.getGenericController("ejb/RaterEJB", this.config.RATER_EJB_SERVER, this.config.RATER_EJB_PORT);
        return (CallingCardReservation)raterEJB.createReservation(callingCardRequestData);
    }

    private boolean cancelReservation(Long reservationId, CallingCardCancelData callingCardCancelData){

        //RATER_TEST_MODE:6
        if(this.raterTestMode){
            return true;
        }

        raterEJB = (RaterEJB)this.genericEjbCaller.getGenericController("ejb/RaterEJB", this.config.RATER_EJB_SERVER, this.config.RATER_EJB_PORT);
        try {
            return raterEJB.cancelReservation(reservationId, callingCardCancelData);
        } catch (NoOpenReservationException e) {
            this.logger.info("NoOpenReservationException, couldn't cancelReservation " + reservationId);
            return false;
        } catch (ReservationNotFoundException e) {
            this.logger.info("ReservationNotFoundException, couldn't cancelReservation " + reservationId);
            return false;
        } catch (InteraxTelephonyException e) {
            this.logger.info("InteraxTelephonyException, couldn't cancelReservation " + reservationId);
            return false;
        }

    }

    private boolean    commitReservation(Long reservationId, CallingCardCommitData callingCardCommitData){

        //RATER_TEST_MODE:7
        if(this.raterTestMode){
            return true;
        }

        raterEJB = (RaterEJB)this.genericEjbCaller.getGenericController("ejb/RaterEJB", this.config.RATER_EJB_SERVER, this.config.RATER_EJB_PORT);
        try {
            return raterEJB.commitReservation(reservationId, callingCardCommitData);
        } catch (NoOpenReservationException e) {
            this.logger.info("NoOpenReservationException, couldn't commitReservation " + reservationId);
            return false;
        } catch (ReservationNotFoundException e) {
            this.logger.info("ReservationNotFoundException, couldn't commitReservation " + reservationId);
            return false;
        } catch (InteraxTelephonyException e) {
            this.logger.info("InteraxTelephonyException, couldn't commitReservation " + reservationId);
            return false;
        }
    }

    // GETTERS

    public String getChannelId() {
        return this.channelId;
    }

    public String getCdrId() {
        return this.cdrId;
    }

    public CallingCardAgiStep getStep() {
        return this.step;
    }

    // HELPERS FOR TEST

    private String generateTestAni(){
        String[] anis = this.config.MASK_ANI_LIST.split(",");
        int index = new Random().nextInt(anis.length);
//        String[] anis = {"582122250170","582122250155","582122250171","582122250172","582122250173","582122250174","582122250175","582122250176","582122250177","582122250178","582122250179","582122250180","582122250181","582122250182","582122250183","582122250170","17862061194","17861466858","17865886259","17861620871","17862176686","17869144204","17862980374","17868238870","17863956224","17868117241","17863644637","17869805359","17865442485","17868950036","17867806129","17869606112","17868073044","17862155265","17868723439","17861824263","17861648274","17868104907","17861578147","17869763778","17869828515","17862736470","17860009323","17869528223","17860683349","17862390206","17869260868","17861155053","17867395750","17869968476","17869003167","17865604108","17863144279","17864975065","17861529514","17869933375","17865323123","17865964648","17866187997","17865965892","17868291167","17866750607","17868701730","13057896638","17862223672","13053247701","1256111"};
        return anis[index];
    }

    private String generateTestDni(){
        int index = new Random().nextInt(10);
        String[] dni = {"19737741150","12025963525","12034133238","12126019940","13474225352","15186593069","15858151152","16463487843","17169869089","17183039001"};
        return dni[index];
    }

    private String generateTestDnid(){
        int index = new Random().nextInt(10);
        String[] dnid = {"19085661061","19737741147","12025963523","17032895756","12034133233","12126019904","13474225301","15186593070","15858151153","16463487844"};
        return dnid[index];
    }



    // INTERRUPTION METHODS

    public String _getData(String file) throws AgiException {
        return this._getData(file, NULL_TIMEOUT);
    }

    public String _getData(String file, int timeout) throws AgiException {
        return this._getData(file, timeout, NULL_MAX_DIGITS);
    }

    public String _getData(String file, int timeout, int maxDigits)
            throws AgiException {
        String data = null;
        StringBuffer bufferData = new StringBuffer();

        if (this.interrupted) {
            bufferData.append(this.interruptedData);
            if(maxDigits != NULL_MAX_DIGITS){
                maxDigits--;
            }
            this.interrupted = false;
            file = CallingCardAgiAudio.SILENCE_0;
        }
        if (maxDigits > 0 || maxDigits == NULL_MAX_DIGITS) {
            if(maxDigits == NULL_MAX_DIGITS){
                if(timeout == NULL_TIMEOUT){
                    data = this.getChannel().getData(file);
                }else{
                    data = this.getChannel().getData(file, timeout);
                }
            }else{
                data = this.getChannel().getData(file, timeout, maxDigits);
            }
            if(data != null && !data.contains("timeout")){
                bufferData.append(data);
            }
        }

        if(bufferData.length()>0)
            return bufferData.toString();
        else
            return data;
    }

    public void _streamFile(String file) throws AgiException {
        this._streamFile(file, "0123456789#*");
    }

    public void _streamFile(String file, String escapeDigits) throws AgiException{
        if(this.interrupted)
            return;

        char dtmf = getChannel().streamFile(file, escapeDigits);
        if(dtmf != 0x00){
            this.interrupted = true;
            this.interruptedData = dtmf;
        }
    }

    public char _sayDigits(String digits) throws AgiException
    {
        return _sayDigits(digits,"0123456789#*");
    }

    public char _sayDigits(String digits, String escapeDigits) throws AgiException
    {
        return getChannel().sayDigits(digits, escapeDigits);
    }

    public void _sayNumber(String number) throws AgiException
    {
         _sayNumber(number,"0123456789#*");
    }

    public void _sayNumber(String number, String escapeDigits) throws AgiException
    {
        if(this.interrupted)
            return;

        char dtmf = getChannel().sayNumber(number, escapeDigits);
        if(dtmf != 0x00){
            this.interrupted = true;
            this.interruptedData = dtmf;
        }
    }

    public char _getOption(String file, String escapeDigits) throws AgiException
    {
        return _getOption(file, escapeDigits,NULL_TIMEOUT);
    }


    public char _getOption(String file, String escapeDigits, int timeout) throws AgiException
    {
        char dtmf = 0x00;
        if(this.interrupted){
            this.interrupted = false;
            return this.interruptedData;
        }
        if(timeout == NULL_TIMEOUT){
             dtmf = getChannel().getOption(file, escapeDigits);
        }else{
             dtmf = getChannel().getOption(file, escapeDigits, timeout);
        }

        if(dtmf != 0x00){

            this.interrupted = false;
        //    this.interruptedData = dtmf;
        }
        return dtmf;
    }




}


