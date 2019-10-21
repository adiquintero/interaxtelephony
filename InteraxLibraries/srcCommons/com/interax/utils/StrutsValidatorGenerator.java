package com.interax.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
//import java.util.HashMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
//import java.util.Vector;


public class StrutsValidatorGenerator {
	
	
	
	/**
	 * This method generate a validator.xml file for each languages files exist in directoryFiles
	 * @param directoryFiles
	 * @param validatorTemplate
	 * @param formsetTemplate
	 * @param outputFile
	 * @throws IOException
	 */
	public static void process(String directoryFiles, String validatorTemplate, String formsetTemplate, String outputFile) throws IOException {
		
		
		BufferedReader bufferedReaderValidatorTemplate = new BufferedReader(new FileReader(validatorTemplate));

		File directory = new File(directoryFiles);
    	StringBuffer output = new StringBuffer();
    	StringBuffer validator = new StringBuffer();

    	if (directory.exists()) {

	    	List<String> languageFiles = new ArrayList<String>();
			String pointPropertiesFile;				
			File[] pointPropertiesFiles = directory.listFiles();	
			int pointPropertiesFilesLength = pointPropertiesFiles.length;
			for (int x=0;x<pointPropertiesFilesLength;x++) {
				pointPropertiesFile = pointPropertiesFiles[x].toString();
				pointPropertiesFile = pointPropertiesFile.substring(pointPropertiesFile.length() - 11, pointPropertiesFile.length());
				if (pointPropertiesFile.equals(".properties"))
					languageFiles.add(pointPropertiesFiles[x].getName());
			}

	    	int languageCountFiles = languageFiles.size();
	    	for(int i=0; i<languageCountFiles; i++) {
	    		String currentLanguageFile;	
	    		String fileLanguage = languageFiles.get(i);
	    		int punto = fileLanguage.indexOf(".");
	    		currentLanguageFile = fileLanguage.substring(0, punto);
	    		output.append("<formset language=\"" + currentLanguageFile + "\">\n");
	            BufferedReader inLanguageFile = new BufferedReader(new FileReader(directoryFiles+"/"+fileLanguage));
                BufferedReader inFormsetTemplate = new BufferedReader(new FileReader(formsetTemplate));
    			String lineLanguageFile;
    			HashMap<String, String> values = new HashMap<String, String>();
    			while (( lineLanguageFile = inLanguageFile.readLine() ) != null) {
    				if(lineLanguageFile.contains("=")){
    					String pattern[] = lineLanguageFile.split("=");	
    					values.put(pattern[0], pattern[1]);
    				}
    			}
    			
	            String lineFormSet;
    			while (( lineFormSet = inFormsetTemplate.readLine() ) != null) {

                                Set<String> dictionaryKeys = values.keySet();
                                Iterator<String> iterator = dictionaryKeys.iterator();
    				while (iterator.hasNext()) {
						String dictionaryKey = (String)iterator.next();
						lineFormSet = lineFormSet.replace("${"+dictionaryKey+"}", values.get(dictionaryKey));
					}
    				
//    				
//    				String tmp = lineFormSet;
//    				int openPar = tmp.indexOf("{");
//    				int closePar = tmp.indexOf("}");
//    				while (openPar > 0){
//    					if ( openPar < closePar && "$".equals(""+tmp.charAt(openPar-1)) ) {
//    						String replaceable = tmp.substring(openPar + 1, closePar);
//    						
//    					}
//    					
//    					if(tmp.length() >= closePar+1){
//    						tmp = tmp.substring(closePar+1);
//    						openPar = tmp.indexOf("{");
//    						closePar = tmp.indexOf("}");
//    					}
//    				}
    				output.append(lineFormSet);
					output.append("\n");
		        }		            
	    		output.append("</formset>\n");
	    	}	
		
    	} else {
			System.out.println("no existe el directorio!");					
    	}	    	

		String lineValidatorTemplate;
        while (( lineValidatorTemplate = bufferedReaderValidatorTemplate.readLine()) != null) {
        	if(lineValidatorTemplate.contains("${FORMSETS}")){
        		validator.append(output);
        		validator.append("\n");
        	} else {
        		validator.append(lineValidatorTemplate);
        		validator.append("\n");
        	}
		}
    	
        BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
        out.append(validator);
        out.flush();      
    	
    	//System.out.println(validator);	    	
        System.out.println("Generator ends, check file: " + outputFile);
	
	}
	
	
	
    public static void main(String args[]) throws IOException {
    	
    	if ( args.length == 4 ) {    	
			String directoryFiles = args[0];
			String validatorTemplate = args[1];
			String formsetTemplate = args[2];			
			String fileOutput = args[3];
			
			StrutsValidatorGenerator.process(directoryFiles, validatorTemplate, formsetTemplate, fileOutput);
			
    	} else {
			System.out.println("Debe ingresar cuatro parámetros");
    	}
    }

}