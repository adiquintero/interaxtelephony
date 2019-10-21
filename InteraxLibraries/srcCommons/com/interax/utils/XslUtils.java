package com.interax.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

 
public class XslUtils {
 
	 
    public static String stringTransform(String inputXmlString, String inputXslString){
		
    	ByteArrayInputStream inputXmlStream=new ByteArrayInputStream(inputXmlString.getBytes());
    	ByteArrayInputStream inputXslStream=new ByteArrayInputStream(inputXslString.getBytes());
    	ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		streamTransform(inputXmlStream, inputXslStream, outputStream);
		
		return new String(outputStream.toByteArray());	
    }
	
	public static void stringBufferTransform(StringBuffer inputXmlStringBuffer, StringBuffer inputXslStringBuffer, StringBuffer outputStringBuffer){
    		
		ByteArrayInputStream inputXmlStream=new ByteArrayInputStream(inputXmlStringBuffer.toString().getBytes());
		ByteArrayInputStream inputXslStream=new ByteArrayInputStream(inputXslStringBuffer.toString().getBytes());
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		streamTransform(inputXmlStream, inputXslStream, outputStream);
		try {
			outputStream.flush();
			outputStringBuffer.append(outputStream);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
    		
    }
    
    public static void fileTransform(String inputXmlFileName, String inputXslFileName, String outputFileName){
		File inputXmlFile=new File(inputXmlFileName);
		File inputXslFile=new File(inputXslFileName);
		File outputFile = new File(outputFileName);
		fileTransform(inputXmlFile, inputXslFile, outputFile);
    }
    
    public static void fileTransform(File inputXmlFile, File inputXslFile, File outputFile){
    	try{
        	InputStream inputXmlStream = new FileInputStream(inputXmlFile);
        	InputStream inputXslStream = new FileInputStream(inputXslFile);
        	OutputStream outputStream = new FileOutputStream(outputFile);
        	streamTransform(inputXmlStream, inputXslStream, outputStream);
        }
    	catch(IOException e){
    		System.out.println("Transformer exception2: " + e.getMessage());
    	}
    }
    
    public static void streamTransform(InputStream inputXmlStream, InputStream inputXslStream, OutputStream outputStream){
        try {
        	Source inputXsl = new StreamSource(inputXslStream);
            Source inputXml = new StreamSource(inputXmlStream);
            Result output = new StreamResult(outputStream);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(inputXsl);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(inputXml, output);
                       
        }
        catch (TransformerException te) {
            System.out.println("Transformer exception1: " + te.getMessage());
        }
    }
    
    
//    public static void main(String[] args) {
//
//    	/*******Prueba 1 pasando ruta archivos -->fileTransform   (Lista) *****/
//    	String xmlStr = "/home/yusmery/Desktop/prueba/uno.xml";
//    	String xslStr   = "/home/yusmery/Desktop/prueba/stylesheet.xslt";
//    	String output= "/home/yusmery/Desktop/salida.txt";
//    	  
//        fileTransform(xmlStr,xslStr,output);
//        
//    	/******Prueba 2 pasando string-->stringTransform (Lista )******/
//    	
//    	 String xmlStr2= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Raiz>\n   <elem1>valor elem1 </elem1>\n   <elem2>valor elem2 </elem2>\n   <elem3>valor elem3 </elem3>\n</Raiz>\n";
//    	 String xslStr2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:output method=\"xml\" version=\"1.0\" encoding=\"UTF-8\" indent=\"yes\" omit-xml-declaration=\"yes\"/><xsl:template match=\"/\"><xsl:value-of select=\".\"/></xsl:template></xsl:stylesheet>";
//         String outputStr = stringTransform(xmlStr2,xslStr2);
//         System.out.println(outputStr);
//    	
//    	
//         /******Prueba 3  stringbuffer-->stringBufferTransform (Lista )******/
//  
//         StringBuffer xmlStr3 = new StringBuffer(); 
//         StringBuffer xslStr3 = new StringBuffer(); 
//         StringBuffer output3 = new StringBuffer(); 
//        
//         xmlStr3.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Raiz>\n   <elem1>valor elem1 </elem1>\n   <elem2>valor elem2 </elem2>\n   <elem3>valor elem3 </elem3>\n</Raiz>\n");
//         xslStr3.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:output method=\"xml\" version=\"1.0\" encoding=\"UTF-8\" indent=\"yes\" omit-xml-declaration=\"yes\"/><xsl:template match=\"/\"><xsl:value-of select=\".\"/></xsl:template></xsl:stylesheet>");
//
//    	 stringBufferTransform(xmlStr3,xslStr3,output3);
//         System.out.println(output3.toString());
//    }
    

}
