/**
 * Title:        DialogClosedProcessor
 * Description:
 * @author Carlos García 
 * @version 1.0
 * First Created: 24/04/2008
 * Last Changes: 06/05/2008   
 **/


/*
 *	Clase implementada para procesar los dialogos cerrados, donde 
 *	se le pedirá al usuario que introduzca una opción de un solo 
 *	dígito y se realiza el procesamiento a través de funciones AGI
 *  según el caso, así como también la recolección de estadísticas. 
 *  La función processDialog retorna el identificador del siguiente 
 *  dialogo a reproducir.
 **/

package com.interax.telephony.service.interactivevoiceresponse.dialog;

//import java.util.Hashtable;

import org.asteriskjava.fastagi.AgiException;

import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseAction;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseDialog;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseOption;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseOptionAnswer;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseScript;
import java.util.HashMap;

public class DialogClosedProcessor extends DialogProcessor{



	public HashMap<String,Object> processDialog(InteractiveVoiceResponseDialog dialog) throws AgiException, Exception{

		HashMap<String,Object> action = new HashMap<String, Object>();
		this.logger.info("En DialogClosedProcessor");
		InteractiveVoiceResponseOption optionAnswer = null;
		char option;
		int attempt = 0;
		
		while(optionAnswer==null && attempt++ < MAX_ATTEMPTS)
		{
			option = 0;
			InteractiveVoiceResponseScript script = getActualScript();
			InteractiveVoiceResponseOptionAnswer answer = new InteractiveVoiceResponseOptionAnswer();			
			answer.setDialogId(dialog.getId());

			option = playPhraseWithEscape(dialog.getPhrase(), "Closed");

			if(option==0)
				option = (Character)findAnswer("Closed",  TIMEOUT);

			if(option==0){
				manageTimeoutOption(script,"Closed");
				answer.setOptionId(null);
				answer.setValue(null);
				getActualOptionAnswers().add(answer);

				this.logger.info("option: " + option);
			}
			else{
				try{
					optionAnswer = dialog.getOptionByAnswer(String.valueOf(option));
				}
				catch(Exception e){
					optionAnswer = null;
				}

				
				if(optionAnswer==null){
					manageIncorrectOption(script, "Closed");
					answer.setOptionId(null);
					answer.setValue(null);
					getActualOptionAnswers().add(answer);
				}
				else
				{
					answer.setOptionId(optionAnswer.getId());
					answer.setValue(String.valueOf(option));
					getActualOptionAnswers().add(answer);
					InteractiveVoiceResponseAction callAction = optionAnswer.getAction();

					if( callAction != InteractiveVoiceResponseAction.NONE){
						action.put(InteractiveVoiceResponseAction.class.getSimpleName(), callAction);
						if(optionAnswer.getActionData() != null){
							action.put(String.class.getSimpleName(), optionAnswer.getActionData());
						}
						return action;
					}
					else{
						action.put(InteractiveVoiceResponseAction.class.getSimpleName(), dialog.getAction());
						if(dialog.getActionData() != null){
							action.put(String.class.getSimpleName(), dialog.getActionData());
						}
						return action;
					}

				}		

			}
		}
		throw new Exception("Forced hangup");
	}

}
