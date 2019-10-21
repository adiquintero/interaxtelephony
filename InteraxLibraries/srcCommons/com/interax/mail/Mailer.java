package com.interax.mail;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Esta clase helper que envia un correo electronico envolviendo la funcionalidad de java mail.
 * @author jestevez - (updated) amarin
 */
public class Mailer {
	/* Objeto Session de Java Mail */
	private Session session;
	/* Direccion de correo del remitente */
	private String from;
	/* Destinos del correo */
	private ArrayList<Object> toList = new ArrayList<Object>();
	/* Con copias */
	private ArrayList<Object> ccList = new ArrayList<Object>();
	/* Con copias oculta */
	private ArrayList<Object> bccList = new ArrayList<Object>();
	/* Asunto del correo */
	private String subject;
	/* Cuerpo del mensaje */
	private String body;
	/* Host SMTP */
	private String mailHost;
	
	private String serverType;
	/* Debug */
	private boolean debug;
	/* Tipo de contenido */
	private int bodyType;
	/* Archivos Adjuntos */
	private ArrayList<File> attach = new ArrayList<File>();
	/**
	 * Tipo del contenido del Mensaje en texto sencillo
	 */
	public final static int TEXT = 1;
	/**
	 * Tipo del contenido del Mensaje en HTML
	 */
	public final static int HTML = 2;
	
	/**
	 * Constructor sin argumentos
	 */
	public Mailer() {
		super();
		session = null;
		from = new String("");
		toList = new ArrayList<Object>();
		ccList = new ArrayList<Object>();
		bccList = new ArrayList<Object>();
		attach = new ArrayList<File>();
		subject = new String("");
		body = new String("");
		mailHost = new String("");
		debug = false;
		bodyType = Mailer.TEXT;
		serverType = new String("");
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	/**
	 * Metodo utilizado para obtener la lista de con copias ocultas
	 * @return ArrayList
	 */
	public ArrayList<Object> getBccList() {
		return bccList;
	}
	/**
	 * Metodo utilizado para establecer la lista de con copias ocultas
	 * @param bccList
	 */
	public void setBccList(ArrayList<Object> bccList) {
		this.bccList = bccList;
	}
	/**
	 * Metodo para establecer un correo de copia oculta
	 * @param bcc
	 */
	public void addBcc(String bcc) {
		bccList.add(bcc);
	}

	/**
	 * Metodo utilizado para obtener el cuerpo del mensaje
	 * @return String
	 */
	public String getBody() {
		return body;
	}
	/**
	 * Metodo para establecer el cuerpo de mensaje
	 * @param String
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * Metodo utilizado para obtener la lista de con copias
	 * @return ArrayList
	 */
	public ArrayList<Object> getCcList() {
		return ccList;
	}
	/**
	 * Metodo utilizado para establecer la lista de con copias
	 * @param ccList
	 */
	public void setCcList(ArrayList<Object> ccList) {
		this.ccList = ccList;
	}
	/**
	 * Metodo para establecer un correo con copia
	 * @param cc
	 */
	public void addCc(String cc) {
		ccList.add(cc);
	}
	/**
	 * Metodo utilizado para obtener el estado de depuraci&oacute;n
	 * @return debug
	 */
	public boolean isDebug() {
		return debug;
	}
	/**
	 * Metodo utilizado para establecer el estado de depuraci&oacute;n
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	/**
	 * Metodo utilizado para obtener el origen del correo
	 * @return
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * Metodo para establecer el origen del correo electronico
	 * @param from
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * Metodo utilizado para obtener el nombre del servidor de correo
	 * @return
	 */
	public String getMailHost() {
		return mailHost;
	}
	/**
	 * Metodo para establecer el nombre del servidor de correo
	 * @param mailHost
	 */
	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}
	/**
	 * Metodo para obtener el objeto Session de JavaMail
	 * @return
	 */
	public Session getSession() {
		return session;
	}
	/**
	 * Metodo para establecer el objeto Session de JavaMail
	 * @param session
	 */
	public void setSession(Session session) {
		this.session = session;
	}
	/**
	 * Metodo para obtener el asunto del correo
	 * @return
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * Metodo para establecer el asunto del correo 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * Metodo utilizado para obtener la lista de origenes del correo
	 * @return
	 */
	public ArrayList<Object> getToList() {
		return toList;
	}
	/**
	 * Metodo utilizado para establecer la lista de origenes del correo
	 * @param toList
	 */
	public void setToList(ArrayList<Object> toList) {
		this.toList = toList;
	}
	/**
	 * Metodo utilizado para establecer un origen al correo
	 * @param to
	 */
	public void addTo(String to) {
		toList.add(to);
	}
	/**
	 * Limpia todos los destinos de la lista
	 */
	public void removeAllTo(){
		toList.removeAll(toList);
	}
	
	/**
	 * Metodo utilizado para obtener el Tipo de Contenido del Correo electronico
	 * @return
	 */
	public int getBodyType() {
		return bodyType;
	}
	/**
	 * Metodo utilizado para establecer el Tipo de contenido del Correo electronico
	 * @param bodyType
	 * Usted debe especificar una de las siguientes opciones para bodyType
	 * <ul>
	 * <li><code>Mailer.HTML:</code> "Contenido del correo en HTML"</li>
	 * <li><code>Mailer.TEXT:</code> "Contenido del correo en TXT"</li>
	 * </ul>
	 */
	public void setBodyType(int bodyType) {
		this.bodyType = bodyType;
	}
	/**
	 * Metodo utilizado para establecer la lista de archivos adjuntos
	 * @return
	 */
	public ArrayList<File> getAttach() {
		return attach;
	}
	/**
	 * Metodo para establecer la lista de archivos adjuntos
	 * @param attach
	 */
	public void setAttach(ArrayList<File> attach) {
		this.attach = attach;
	}
	/**
	 * Metodo utilizado para agregar un archivo adjunto
	 * @param file
	 */
	public void addAttach(File file) {
		if(file != null)
			attach.add(file);
	}
	/**
	 * Metodo para enviar correo
	 * @param mailHost IP o Direcci&oacute;n electronica del servidor de correo.
	 * @param to Destino del correo
	 * @param from Origen del correo
	 * @param subject Asunto del correo
	 * @param body Contenido del Correo
	 * @param cc Con Copia
	 * @param bcc Con Copia Oculta
	 * @param bodyType Formato del correo
	 * Usted debe especificar una de las siguientes opciones para bodyType
	 * <ul>
	 * <li><code>Mailer.HTML:</code> "Contenido del correo en HTML"</li>
	 * <li><code>Mailer.TEXT:</code> "Contenido del correo en TXT"</li>
	 * </ul>
	 * @param file Archivo adjunto
	 * @param debug Verbose
	 * @throws MessagingException
	 */
	public void doSend(String mailHost, String to, String from, 
			String subject, String body, String cc, String bcc, int bodyType,
			File file, boolean debug) throws MessagingException{
		Mailer mailer = new Mailer();
		mailer.setMailHost(mailHost);
		mailer.addTo(to);
		mailer.setSubject(subject);
		mailer.setBody(body);
		mailer.setFrom(from);
		mailer.addBcc(bcc);
		mailer.addCc(cc);
		mailer.setBodyType(bodyType);
		mailer.setDebug(debug);
		if(file != null)
			mailer.addAttach(file);
		mailer.doSend();
	}
	/**
	 * Metodo para enviar correo
	 * @throws MessagingException
	 */
	public synchronized void doSend() throws MessagingException{
		Properties properties = new Properties();
		properties.put(serverType, mailHost);
		if(session == null){
			session = Session.getDefaultInstance(properties, null);
		
		}
		session.setDebug(debug);
		// Creo el objeto message, lo cree final para ejecutarlo en un hilo
		final Message message = new MimeMessage(session);
		InternetAddress[] addresses;
		
		// Lista de destinos
		addresses = new InternetAddress[toList.size()];
		for (int i = 0; i < addresses.length; i++) {
			addresses[i] = new InternetAddress((String) toList.get(i));
		}
		message.setRecipients(Message.RecipientType.TO, addresses);

		// Direcciones de copias
		addresses = new InternetAddress[ccList.size()];
		for (int i = 0; i < addresses.length; i++) {
			addresses[i] = new InternetAddress((String) ccList.get(i));
		}
		message.setRecipients(Message.RecipientType.CC, addresses);
		
		// Direcciones de copia oculta
		addresses = new InternetAddress[bccList.size()];
		for (int i = 0; i < addresses.length; i++) {
			addresses[i] = new InternetAddress((String) bccList.get(i));
		}
		message.setRecipients(Message.RecipientType.BCC, addresses);
		
		// Origen del mensaje
		message.setFrom(new InternetAddress(from));
		
		// Asunto del mensaje
		message.setSubject(subject);
		
		// Verifico si hay archivos adjuntos
		if(attach.size() > 0){
			
			BodyPart bodyPart = new MimeBodyPart();
			// Texto del mensaje
			bodyPart.setContent(body, "text/html");
			
			MimeMultipart multiParte = new MimeMultipart();
			BodyPart bodyPart2 = null;//= new MimeBodyPart();
			// Cargo los adjuntos
			multiParte.addBodyPart(bodyPart);
			
			for (Iterator<File> iter = attach.iterator(); iter.hasNext();) {
				bodyPart2 = new MimeBodyPart();
				File file = iter.next();
				bodyPart2.setDataHandler(new DataHandler(new FileDataSource(file)));
				// Opcional. De esta forma transmitimos al receptor el nombre original del archivo
				bodyPart2.setFileName(file.getName());
				multiParte.addBodyPart(bodyPart2);
			}
			// Junto ambas partes
			//multiParte.addBodyPart(bodyPart);
				
			message.setContent(multiParte);
		}
		else{
			if(bodyType == Mailer.TEXT){
				// Mensaje de Texto
				message.setText(body);
			}
			else if(bodyType == Mailer.HTML){
				// Mensaje en HTML
				message.setContent(body, "text/html; charset=utf-8");
			}
			
		}
		
		//new Thread(){
		//	public void run(){
				try {
					Transport.send(message);
				} catch (MessagingException e) {
					e.printStackTrace();
					throw e;
				}
		//	}
		//}.start();
		
	}
	
}

