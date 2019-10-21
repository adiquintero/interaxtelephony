/**
 * Title:        DialogProcessor
 * Description:
 * @author Carlos García 
 * @version 1.0
 * First Created: 25/04/2008
 * Last Changes: 06/05/2008   
 **/

package com.interax.telephony.service.interactivevoiceresponse.dialog;

//import java.util.Hashtable;
import java.util.List;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

import com.interax.telephony.service.interactivevoiceresponse.agi.incoming.InteractiveVoiceResponseIncomingAgiLogger;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseDialog;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseOptionAnswer;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseScript;
import java.util.HashMap;

/*
 *	Clase padre implementada para definir las acciones comunes de los 
 *	dialogos, tales como reproducir una frase, sintetizar una frase, 
 *	tomar las opciones del usuario y la creación de la jerarquía 
 *	de carpetas y luego eliminación de las innecesarias.
 *	También es usada para la definición de constantes generales que 
 *	serán usadas durante el procesamiento de un dialogo.  
 *  
 **/

public abstract class DialogProcessor extends BaseAgiScript {
	
	protected static final int MAX_ATTEMPTS = 3; //TODO: Cambiar esto del dialogo y del guión.
	
	public static final String DIGITS_CLOSED = "0123456789*#";
	public static final String DIGITS_OPEN = "0123456789";
	public static final String ASTERISK_AUDIOS_PATH = "/usr/share/interaxtelephony/default/server/asterisk/var/lib/sounds/";
	public static final int REPEAT = 1; //FIXME Para la siguiente versión va en el dialogo
	public static final int TIMEOUT = 6;
	private String audioFolder = "";
	public InteractiveVoiceResponseIncomingAgiLogger logger ;
	

	abstract public HashMap<String,Object> processDialog(InteractiveVoiceResponseDialog dialog)throws AgiException, Exception;
	
	protected InteractiveVoiceResponseScript actualScript;
	protected List<InteractiveVoiceResponseOptionAnswer> optionAnswers;


	public void playPhrase(String file) throws AgiException{

		
		if (file.contains(".") && (file.indexOf('.') >= file.length() - 5))
			file = file.substring(0, file.lastIndexOf('.'));
		this.logger.info("Playing file : "+ file);
		    streamFile(this.audioFolder + file);
	} 
	
	public Object findAnswer(String type, int timeout) throws AgiException{
		Object result = null;
	//	System.out.println("findAnswer" + type);
		this.logger.info("FindingAnswer : "+ type);
		if(type.equals("Closed"))
			result = waitForDigit(timeout*1000);
		else if(type.equals("Open"))
			result = getData(ASTERISK_AUDIOS_PATH + "silence/1",timeout *1000);
		
		return result;
		
	}

	public char playPhraseWithEscape(String file, String type) throws AgiException{
		char result = 0;
		this.logger.info("Playing file : "+ file + "type : " + type);
		if (file.contains(".") && (file.indexOf('.') >= file.length() - 5))
			file = file.substring(0, file.lastIndexOf('.'));
		if(type.equalsIgnoreCase("Closed"))
			result = streamFile(this.audioFolder + file, DIGITS_CLOSED);
		else if(type.equalsIgnoreCase("Open"))
			result = streamFile(this.audioFolder + file, DIGITS_OPEN);
		return result;
	}
	
	public void manageTimeoutOption(InteractiveVoiceResponseScript script, String type) throws AgiException{
		this.logger.info("Timeout");
		this.playPhrase(script.getTimeoutPhrase());
	}
	
	public void manageIncorrectOption(InteractiveVoiceResponseScript script,String type) throws AgiException{
		this.playPhrase(script.getIncorrectPhrase());
	}


    public void playSilence(int amount) throws AgiException{
		this.logger.info("PlaySilence");
    	for (int i = 0; i < amount; i++) {
    		streamFile(ASTERISK_AUDIOS_PATH + "silence/1");
		}
    	
    }
        


	public InteractiveVoiceResponseScript getActualScript() {
		return actualScript;
	}

	public void setActualScript(InteractiveVoiceResponseScript  actualScript) {
		this.actualScript = actualScript;
	}
	
	public List<InteractiveVoiceResponseOptionAnswer> getActualOptionAnswers() {
		return optionAnswers;
	}

	public void setActualOptionAnswers(List<InteractiveVoiceResponseOptionAnswer>  optionAnswers) {
		this.optionAnswers = optionAnswers;
	}

    public void service(AgiRequest request, AgiChannel channel)
    throws AgiException{}

	public String getAudioFolder() {
		return audioFolder;
	}

	public void setAudioFolder(String audioFolder) {
		this.audioFolder = audioFolder;
	}
	public void setLogger(InteractiveVoiceResponseIncomingAgiLogger logger){
		this.logger = logger;
	}
}
