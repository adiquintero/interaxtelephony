/**
 * Title:        DialogOpenProcessor
 * Description:
 * @author Carlos García 
 * @version 1.0
 * First Created: 24/04/2008
 * Last Changes: 06/05/2008   
 **/

/*
 *	Clase implementada para procesar los dialogos abiertos, donde 
 *	se le pedirá al usuario que introduzca una serie de dígitos
 *	seguidos de la tecla # que indican al sistema un rango de elección.
 *	Se realiza el procesamiento a través de funciones AGI
 *  según el caso, así como también la recolección de estadísticas. 
 *  La función processDialog retorna el identificador del siguiente 
 *  dialogo a reproducir.
 *  
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


public class DialogOpenProcessor extends DialogProcessor{

	public HashMap<String,Object>  processDialog(InteractiveVoiceResponseDialog dialog) throws AgiException, Exception{
		HashMap<String,Object> action = new HashMap<String, Object>();
		this.logger.info("In OpenDialogProcessor");
		InteractiveVoiceResponseScript script = getActualScript();
		InteractiveVoiceResponseOption optionAnswer = null;
		String option = null;
		char firstChar;
		//		String option;
		int attempt = 0;

		while(optionAnswer==null && attempt++ < MAX_ATTEMPTS)
		{
			firstChar = 0;
			InteractiveVoiceResponseOptionAnswer answer = new InteractiveVoiceResponseOptionAnswer();
			answer.setDialogId(dialog.getId());

			//TODO: AL FINAL getActualOptionAnswers().add(answer);

			firstChar = playPhraseWithEscape(dialog.getPhrase(), "Open");		

			if(firstChar!=0)
				option = firstChar + (String) findAnswer("Open", TIMEOUT);
			else
				option = (String) findAnswer("Open", TIMEOUT);

			if (option==null || option.contains("timeout")){
				getActualOptionAnswers().add(answer);
				manageTimeoutOption(script,"Closed");	
			}else{

				try{
					optionAnswer = dialog.getOptionByAnswer(option);
				}
				catch(Exception e){
					optionAnswer = null;
				}

				if(optionAnswer==null){
					this.logger.info("Incorrect option");
					answer.setValue(option);
					getActualOptionAnswers().add(answer);
					manageIncorrectOption(script, "Closed");
				}
				else{
					answer.setOptionId(optionAnswer.getId());
					answer.setValue(option);
					getActualOptionAnswers().add(answer);
					InteractiveVoiceResponseAction callAction = optionAnswer.getAction();

					if( callAction != InteractiveVoiceResponseAction.NONE){
						action.put(InteractiveVoiceResponseAction.class.getSimpleName(), callAction);
						if(optionAnswer.getActionData() != null){
							action.put(String.class.getSimpleName(), optionAnswer.getActionData());
						}

					}
					else{
						action.put(InteractiveVoiceResponseAction.class.getSimpleName(), dialog.getAction());
						if(dialog.getActionData() != null){
							action.put(String.class.getSimpleName(), dialog.getActionData());
						}
					}
					return action;
				}

			}
		}
		throw new Exception("Forced hangup");
	}			
}


