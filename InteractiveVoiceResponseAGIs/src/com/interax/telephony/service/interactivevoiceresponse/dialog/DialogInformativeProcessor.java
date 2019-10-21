/**
 * Title:        DialogInformativeProcessor
 * Description:
 * @author Carlos García 
 * @version 1.0
 * First Created: 24/04/2008
 * Last Changes: 06/05/2008   
 **/

/*
 *	Clase implementada para procesar los dialogos informativos,  
 *	que son los más básicos de los dialogos.
 *	Solo tienen la capacidad de reproducir una frase en un momento dado. 
 *  La función processDialog retorna el identificador del siguiente 
 *  dialogo a reproducir.
 **/

package com.interax.telephony.service.interactivevoiceresponse.dialog;

//import java.util.Hashtable;

import org.asteriskjava.fastagi.AgiException;

import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseAction;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseDialog;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseOptionAnswer;
import java.util.HashMap;

public class DialogInformativeProcessor extends DialogProcessor{

	public HashMap<String,Object> processDialog(InteractiveVoiceResponseDialog dialog) throws AgiException, Exception{
		HashMap<String,Object>action = new HashMap<String, Object>();
		this.logger.info("DialogInformativeProcessor");
//		for(int i=0; i< dialog.getDialogPhraseCount() ; i++){
			
//			ScriptAnswer answer = new ScriptAnswer();
//			answer.setDialogId(dialog.getId());
			String phrase = dialog.getPhrase();
			playPhrase(phrase);				

			InteractiveVoiceResponseOptionAnswer answer = new InteractiveVoiceResponseOptionAnswer();
			answer.setDialogId(dialog.getId());
			getActualOptionAnswers().add(answer);
			

			
//		}
			action.put(InteractiveVoiceResponseAction.class.getSimpleName(), dialog.getAction());
			if(dialog.getActionData() != null){
				action.put(String.class.getSimpleName(), dialog.getActionData());
			}
			return action;
			

		
	}

}
