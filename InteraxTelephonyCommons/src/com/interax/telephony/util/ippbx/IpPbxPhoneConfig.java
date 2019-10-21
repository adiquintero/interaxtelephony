package com.interax.telephony.util.ippbx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
//import java.util.Vector;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.interax.telephony.service.data.RtIaxPeer;
import com.interax.telephony.service.data.RtSipPeer;
import com.interax.telephony.service.ippbx.data.IpEquipment;
import com.interax.telephony.service.ippbx.data.IpEquipmentManufacturer;
import com.interax.telephony.service.ippbx.data.IpEquipmentModel;
import com.interax.telephony.service.ippbx.data.IpExtension;
import com.interax.telephony.service.ippbx.data.IpExtensionHasEquipment;
import com.interax.telephony.service.ippbx.data.IpExtensionProtocol;
import java.util.List;

public class IpPbxPhoneConfig {

	private IpEquipment ipEquipment;
	private IpEquipmentManufacturer ipEquipmentManufacturer;
	private IpEquipmentModel ipEquipmentModel;
	private IpExtension ipExtension;
	private IpExtensionHasEquipment ipExtensionHasEquipment;
	private List<RtIaxPeer> rtIaxPeers;
	private List<RtSipPeer> rtSipPeers;
	private String registrationServer;
	
	private static final String SIP_PORT = "5060";
	
	public IpPbxPhoneConfig(IpEquipmentManufacturer ipEquipmentManufacturer, IpEquipmentModel ipEquipmentModel, IpEquipment ipEquipment, IpExtension ipExtension, IpExtensionHasEquipment ipExtensionHasEquipment, List<RtIaxPeer> rtIaxPeers, List<RtSipPeer> rtSipPeers, String registrationServer){
		this.ipEquipment = ipEquipment;
		this.rtIaxPeers = rtIaxPeers;
		this.rtSipPeers = rtSipPeers;
		this.ipEquipmentManufacturer = ipEquipmentManufacturer;
		this.ipEquipmentModel =ipEquipmentModel;
		this.ipExtension = ipExtension;
		this.ipExtensionHasEquipment = ipExtensionHasEquipment;
		this.registrationServer = registrationServer;
	}

	 
	public void generateXml(String fileName){
		this.generateXml(new File(fileName));
	}

	public void generateXml(File file) {
		try {
			OutputStream outputStream = new FileOutputStream(file);
		    this.generateXml(outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void generateXml(StringBuffer outputStringBuffer){
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		try {
			outputStream.flush();
			outputStringBuffer.append(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	
	//public String 
	
	private void generateXml(OutputStream outputStream){
		try {
			//ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			StreamResult streamResult = new StreamResult(outputStream);
			SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	
			AttributesImpl atts = new AttributesImpl();
			TransformerHandler hd = tf.newTransformerHandler();
			Transformer serializer = hd.getTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");
			hd.setResult(streamResult);
			hd.startDocument();

			atts.clear();
			hd.startElement("","","phone_config",atts);
				hd.startElement("","","general",atts);
					
					/*String fileName = null;
					switch (ipEquipmentManufacturer.getInternalName()) {
						case POLYCOM:
							fileName = this.ipEquipment.getMacAddress().toLowerCase();
						break;
						case GRANDSTREAM:
							fileName = this.ipEquipment.getMacAddress().toLowerCase();
						break;
						case ATCOM:
							fileName = this.ipEquipment.getMacAddress().toLowerCase();
						break;
						default:
							fileName = this.ipEquipment.getMacAddress();
						break;
					}*/
					//FIXME NAME Y VERSION DEBEN SALIR DE LA CONFIG
					//atts.addAttribute("","","name","CDATA",fileName);
					atts.addAttribute("","","name","CDATA",this.ipEquipment.getMacAddress());
					atts.addAttribute("","","version","CDATA",this.ipExtensionHasEquipment.getFileVersion());
					String defaultProtocol = null;
					
					if (this.ipExtensionHasEquipment.getProtocol() == IpExtensionProtocol.IAX)
						defaultProtocol = "4";
					else
						defaultProtocol = "2";

					atts.addAttribute("","","default_protocol","CDATA",defaultProtocol);
					atts.addAttribute("","","encryption_key","CDATA",this.ipExtensionHasEquipment.getEncryptionKey());

					hd.startElement("","","file",atts);
					hd.endElement("","","file");
					
					atts.clear();
					atts.addAttribute("","","name","CDATA",this.ipExtension.getCallerId());

					hd.startElement("","","display",atts);
					hd.endElement("","","display");
				hd.endElement("","","general");
				
				atts.clear();
				hd.startElement("","","voip",atts);
				
				switch (this.ipExtensionHasEquipment.getProtocol()){
					
					case SIP:
					
						atts.clear();
						hd.startElement("","","sip",atts);
							
							atts.clear();
							hd.startElement("","","accounts",atts);
								int rtSipPeersSize = rtSipPeers.size();
								for (int i = 0; i < rtSipPeersSize; i++) {
									RtSipPeer rtSipPeersElement = rtSipPeers.get(i);
									atts.clear();
									atts.addAttribute("","","number","CDATA",""+(i+1));
									atts.addAttribute("","","server_ip","CDATA",this.registrationServer);
									atts.addAttribute("","","server_port","CDATA",IpPbxPhoneConfig.SIP_PORT);
									atts.addAttribute("","","username","CDATA",rtSipPeersElement.getUsername());
									atts.addAttribute("","","password","CDATA",rtSipPeersElement.getSecret());
									hd.startElement("","","account",atts);
									hd.endElement("","","account");
								}
							hd.endElement("","","accounts");
						
						hd.endElement("","","sip");
					
					break;
					
					case IAX:
						atts.clear();
						hd.startElement("","","iax",atts);
							atts.clear();
							hd.startElement("","","accounts",atts);
								int rtIaxPeersSize = rtIaxPeers.size();
								for (int i = 0; i < rtIaxPeersSize; i++) {
									RtIaxPeer rtIaxPeersElement = rtIaxPeers.get(i);
									atts.clear();
									atts.addAttribute("","","number","CDATA",""+(i+1));
									atts.addAttribute("","","server_ip","CDATA",this.registrationServer);
									atts.addAttribute("","","server_port","CDATA","4569");
									atts.addAttribute("","","username","CDATA",rtIaxPeersElement.getUsername());
									atts.addAttribute("","","password","CDATA",rtIaxPeersElement.getSecret());
									hd.startElement("","","account",atts);
									hd.endElement("","","account");
								}
							hd.endElement("","","accounts");
						hd.endElement("","","iax");
					
					break;
				}
				
					atts.clear();
					hd.startElement("","","dsp",atts);
						atts.clear();
						atts.addAttribute("","","number","CDATA","1");
						atts.addAttribute("","","name","CDATA","g729");
						hd.startElement("","","codecs",atts);
						hd.endElement("","","codecs");
					hd.endElement("","","dsp");
				hd.endElement("","","voip");
					atts.clear();
					atts.addAttribute("","","protocol","CDATA","TFTP");
					atts.addAttribute("","","server_ip","CDATA",this.registrationServer.replace("pbx", "tftp"));
					atts.addAttribute("","","server_port","CDATA","69");
					atts.addAttribute("","","time","CDATA","3600");
					atts.addAttribute("","","update_mode","CDATA","Update Interval");
					atts.addAttribute("","","update_interval","CDATA","1");
					hd.startElement("","","auto_provisioning",atts);	
					hd.endElement("","","auto_provisioning");
				
					atts.clear();
					atts.addAttribute("","","mac_address","CDATA", ipEquipment.getMacAddress());
					atts.addAttribute("","","serial_number","CDATA", ipEquipment.getSerialNumber());
					atts.addAttribute("","","product_number","CDATA",ipEquipmentModel.getProductNumber());
					atts.addAttribute("","","manufacturer","CDATA", ipEquipmentManufacturer.getInternalName().name());
					hd.startElement("","","device",atts);
					hd.endElement("","","device");	
			hd.endElement("","","phone_config");
			hd.endDocument();
			//return byteArrayOutputStream;
			
			
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return null;

	}
	
//	
//	public static void main(String args[]){
//		
//		IpEquipment ipEquipment = new IpEquipment();
//		//ipEquipment.setMacAddress("00094555801E");
//		//ipEquipment.setSerialNumber("7777777777");
//		
//		IpEquipmentManufacturer ipEquipmentManufacturer = new IpEquipmentManufacturer();
//		//ipEquipmentManufacturer.setName("ATCOM");
//		
//		IpExtensionHasEquipment ipEquipExtensionHasEquipment = new IpExtensionHasEquipment();
//		//ipEquipExtensionHasEquipment.setEncryptionKey("1234567890987654321012345678909876543210123456789098765432101234");
//		
//		IpEquipmentModel ipEquipmentModel = new IpEquipmentModel();
//		//ipEquipmentModel.setProductNumber("1234564566EEEATCOM");
//		
//		IpExtension ipExtension = new IpExtension();
//		//ipExtension.setCallerId("Juan Perez");
//		
//		Vector<RtIaxPeer> rtIaxPeers = new Vector<RtIaxPeer>();
//		RtIaxPeer rtIaxPeer = new RtIaxPeer();
//		
//		String[] user =new String[2];
//		user[0]="a"; user[1]="b"; 
//		
//		String[] secret =new String[2];
//		secret[0]="123"; secret[1]="456"; 
//		
//		for (int i = 0; i < 2; i++) {
//			rtIaxPeer.setUsername(user[i]);
//			rtIaxPeer.setSecret(secret[i]);
//			rtIaxPeers.add(i, rtIaxPeer);
//		}
//		
//		
//		Vector<RtSipPeer> rtSipPeers = new Vector<RtSipPeer>();
//		RtSipPeer rtSipPeer = new RtSipPeer();
//		
//		for (int i = 0; i < 2; i++) {
//			rtSipPeer.setUsername(user[i]);
//			rtSipPeer.setSecret(secret[i]);
//			rtSipPeers.add(i, rtSipPeer);
//		}
//
//		
//		IpPbxPhoneConfig ipPbxPhoneConfig = new IpPbxPhoneConfig(ipEquipmentManufacturer, ipEquipmentModel, ipEquipment, ipExtension , ipEquipExtensionHasEquipment, rtIaxPeers, rtSipPeers);
//		ipPbxPhoneConfig.generateXml("/home/yusmery/Desktop/prueba/yuryriera.xml");
//		
//	}
}
