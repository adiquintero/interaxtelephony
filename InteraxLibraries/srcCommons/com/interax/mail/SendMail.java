package com.interax.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
//import java.util.Hashtable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

public class SendMail {
	
	
	/**
	 * @param from
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param message
	 */
	@SuppressWarnings({ "unchecked" })
	public static void sendMail(String mailHost, String serverType, String from, String to, String cc, String bcc, String subject, String message, Throwable t){
		message = appendStackTrace(message, t);
		sendMail(mailHost, serverType, from, to, cc, bcc, subject, message, (List)null);		
	}
	
	/**
	 * @param from
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param message
	 */
	@SuppressWarnings("unchecked")
	public static void sendMail(String mailHost, String serverType, String from, String to, String cc, String bcc, String subject, String message){
		sendMail(mailHost, serverType, from, to, cc, bcc, subject, message, (List)null);		
	}
	
	/**
	 * @param from
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param message
	 */
	public static void sendMail(String mailHost, String serverType, String from, String to, String cc, String bcc, String subject, String message, File file){
		
		List<File> listFiles = new ArrayList<File>();
		listFiles.add(file);
		sendMail(mailHost, serverType, from, to, cc, bcc, subject, message, listFiles);		
		
	}
	
	/**
	 * @param from
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param message
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public static void sendMail(String mailHost, String serverType, String from, String to, String cc, String bcc, String subject, String message, List <File> listFiles){
		Mailer mailer;
		try {
			mailer = new Mailer();
			
			mailer.setBody(message);
			mailer.setDebug(false);
			
			mailer.setMailHost(mailHost);	// mail.123.com.ve
			mailer.setServerType(serverType);
			
			mailer.setFrom(from);
			
			String[] splittedTo = to.split(",");
			int toQuantity = splittedTo.length;
			for (int i = 0; i < toQuantity; i++) {
				mailer.addTo(splittedTo[i]);
			}
			
			if (cc != null && cc.length()>0){
				String[] splittedCc = cc.split(",");
				int ccQuantity = splittedCc.length;
				for (int i = 0; i < ccQuantity; i++) {
					mailer.addCc(splittedCc[i]);
				}
			}
			
			if (bcc != null && bcc.length()>0)
				mailer.addBcc(bcc);
			
			if (bcc != null && bcc.length()>0){
				String[] splittedBcc = bcc.split(",");
				int bccQuantity = splittedBcc.length;
				for (int i = 0; i < bccQuantity; i++) {
					mailer.addBcc(splittedBcc[i]);
				}
			}
			
			mailer.setSubject(subject);
			mailer.setBodyType(mailer.HTML);
			
			if( listFiles!= null && listFiles.size() > 0)
				mailer.setAttach((ArrayList)listFiles);
			
			try {
				mailer.doSend();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			mailer.removeAllTo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param from
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param message
	 */
	public static void sendMailHtml(String mailHost, String serverType, String from, String to, String cc, String bcc, String subject, HashMap<String, String> parameters, File htmlFile){
		Mailer mailer;
		try {
			
			mailer = new Mailer();
			StringBuffer output = new StringBuffer();
			BufferedReader inFormsetTemplate = new BufferedReader(new FileReader(htmlFile));

			String lineFormSet;
			while (( lineFormSet = inFormsetTemplate.readLine() ) != null) {

                                Set<String> dictionaryKeys = parameters.keySet();
                                Iterator<String> iterator = dictionaryKeys.iterator();
				while (iterator.hasNext()) {
					String dictionaryKey = (String)iterator.next();
					lineFormSet = lineFormSet.replace("${"+dictionaryKey+"}", parameters.get(dictionaryKey));
				}
				output.append(lineFormSet);
				output.append("\n");
			}		            
			output.append("\n");


			mailer.setBody(output.toString());
			mailer.setDebug(false);

			mailer.setMailHost(mailHost);
			mailer.setServerType(serverType);

			mailer.setFrom(from);
			mailer.addTo(to);
			if (cc != null && cc.length()>0)
				mailer.addCc(cc);
			if (bcc != null && bcc.length()>0)
				mailer.addBcc(bcc);
			mailer.setSubject(subject);
			mailer.setBodyType(Mailer.HTML);
			try {
				mailer.doSend();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			mailer.removeAllTo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static String appendStackTrace(String s, Throwable t) {

		String lineSeparator = " <br/><br/> ";
		StringBuffer sb = new StringBuffer();
		
		sb.append(lineSeparator);
		sb.append(s);
		sb.append(lineSeparator);
		sb.append(lineSeparator);
		if (t != null) {
			try {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				t.printStackTrace(pw);
				pw.close();
				sb.append(sw.toString());
				sb.append(lineSeparator);
			} catch (Exception e) {
				sb.append("Couldn't append the exception!!"+e.getMessage());
			}
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		
		String mailHost ="mail.123.com.ve";
		String serverType ="mail.smtp.host";
		
		System.out.println("SENDING EMAIL");
		SendMail.sendMail(mailHost, serverType, "amarin@interaxmedia.com","jmorales@interaxmedia.com", null, null,"[ContactManagerReporter] Reporte de Contactoszzzz",
				"Mensaje \nEn la otra linea y asi:\n 2316418641318 168461 1649684	8484984615616			231864616168546811\n");
		System.out.println("EMAIL SENDED");
		
//		
//		System.out.println("SENDING EMAIL");
//		Hashtable<String, String> parameters = new Hashtable<String, String>();
//		parameters.put("name", "Amador Marin");
//		//parameters.put("server", "Amador Marin");
//		parameters.put("messageBody", "Su pago no se ha podido procesar correctamente, por favor pongase en contacto con el personal de soporte en support@inmerx.com");
//
//		SendMail.sendMailHtml(
//			"uno23@interaxmedia.com","amarin@interaxmedia.com", "vrobles@interaxmedia.com", null, "Confirmación de Pago",
//			parameters,
//			new File("/home/amador/workspaces/inmerx/CoreWEBAdmin/WebContent/emailTemplates/email_inmerx01.html"));
//		
//		System.out.println("EMAIL SENDED");
		
	}
}

