package com.interax.utils;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.HashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
//import java.util.Vector;

public class ISOUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String [] otherDefaultLanguagesKeys = {"DEF", "UNK"};
	public static String [] otherDefaultCountriesKeys = {"DEF", "UNK"};
	
	private HashMap<String, List<String>> languageNames;
	private HashMap<String, List<String>> languageIsos;
	private HashMap<String, HashMap<String,String>> reverseLanguageNames;
	
	private HashMap<String, List<String>> countryNames;
	private HashMap<String, List<String>> countryIsos;
	private HashMap<String, HashMap<String,String>> reverseCountryNames;
	
	
	public ISOUtils() {
		super();
		languageNames = new HashMap<String, List<String>>();
		languageIsos = new HashMap<String, List<String>>();
		countryNames = new HashMap<String, List<String>>();
		countryIsos = new HashMap<String, List<String>>();
		reverseLanguageNames = new HashMap<String, HashMap<String,String>>();
		reverseCountryNames = new HashMap<String, HashMap<String,String>>();
		
	}
	
	
	
	public ISOUtils(String lang, List<String> otherLanguageIsos, List<String> otherLanguagesLabels,
			List<String> otherCountriesIsos, List<String> otherCountriesLabels ){
		
		this(lang, otherLanguageIsos, otherLanguagesLabels, otherCountriesIsos, otherCountriesLabels, true);
	}
	
	// Ej: getISOLanguageCountry("ES", "Por defecto", true ), getISOLanguageCountry("EN", "Default", true )
	
	public ISOUtils(String lang, 
			List<String> otherLanguageIsos, List<String> otherLanguagesLabels,
			List<String> otherCountriesIsos, List<String> otherCountriesLabels, boolean withISO ){
		
		this.languageNames = new HashMap<String, List<String>>();
		this.languageIsos = new HashMap<String, List<String>>();
		this.countryNames = new HashMap<String, List<String>>();
		this.countryIsos = new HashMap<String, List<String>>();
		this.reverseLanguageNames = new HashMap<String, HashMap<String,String>>();
		this.reverseCountryNames = new HashMap<String, HashMap<String,String>>();
		
		
		addISOLanguageCountry(lang, otherLanguageIsos, otherLanguagesLabels, otherCountriesIsos, otherCountriesLabels, withISO, true, true);
	} 
	

	// CountryIsos
	public HashMap<String, List<String>> getCountryIsos() {
		return countryIsos;
	}
	public void setCountryIsos(HashMap<String, List<String>> countryIsos) {
		this.countryIsos = countryIsos;
	}
	// CountryNames
	public HashMap<String, List<String>> getCountryNames() {
		return countryNames;
	}
	public void setCountryNames(HashMap<String, List<String>> countryNames) {
		this.countryNames = countryNames;
	}
	public HashMap<String, HashMap<String, String>> getReverseCountryNames() {
		return reverseCountryNames;
	}
	public void setReverseCountryNames(
			HashMap<String, HashMap<String, String>> reverseCountryNames) {
		this.reverseCountryNames = reverseCountryNames;
	}
	
	// LanguageIsos
	public HashMap<String, List<String>> getLanguageIsos() {
		return languageIsos;
	}
	public void setLanguageIsos(HashMap<String, List<String>> languageIsos) {
		this.languageIsos = languageIsos;
	}
	// LanguageNames
	public HashMap<String, List<String>> getLanguageNames() {
		return languageNames;
	}
	public void setLanguageNames(HashMap<String, List<String>> languageNames) {
		this.languageNames = languageNames;
	}
	public HashMap<String, HashMap<String, String>> getReverseLanguageNames() {
		return reverseLanguageNames;
	}
	public void setReverseLanguageNames(
			HashMap<String, HashMap<String, String>> reverseLanguageNames) {
		this.reverseLanguageNames = reverseLanguageNames;
	}
	
	public void addISOLanguage(String lang, boolean withISO ){
		addISOLanguageCountry(lang, null, null, null, null, withISO, true, false);
	}
	
	public void addISOCountry(String lang, boolean withISO ){
		addISOLanguageCountry(lang, null, null, null, null, withISO, false, true);
	}
	
	
	/* to fill language and country Names and Isos */
	public void addISOLanguageCountry(String lang, 
							List<String> otherLanguagesIsos, List<String> otherLanguagesLabels,
							List<String> otherCountriesIsos, List<String> otherCountriesLabels,
							boolean withISO, boolean loadLanguages, boolean loadCountries ){
		
		Locale userLocale = new Locale(lang);
		
		if(loadLanguages){
			List<String> allLanguageIsos = new ArrayList<String>();
			List<String> allLanguageNames = new ArrayList<String>();
			HashMap<String, String> allReverseLanguageNames = new HashMap<String, String>();
			
			// 
			String [] availableLanguageIsos = Locale.getISOLanguages();
			int availableLanguageCount = availableLanguageIsos.length;
			String [] availableLanguageNames = new String[availableLanguageCount];
			String [] tempLanguageNames = new String[availableLanguageCount];
			
			for(int i=0; i<availableLanguageCount; i++)
			{
				availableLanguageIsos[i] = availableLanguageIsos[i].toUpperCase();
				Locale locale = new Locale(availableLanguageIsos[i]);
				availableLanguageNames[i] = StringUtils.toCamelCase(locale.getDisplayLanguage(userLocale), " ", userLocale, false, false);
				tempLanguageNames[i] = StringUtils.toCamelCase(locale.getDisplayLanguage(userLocale), " ", userLocale, false, false);
				availableLanguageNames[i] += ((withISO) ? " (" + availableLanguageIsos[i] + ")" : "" ) ;
				
				allLanguageNames.add(availableLanguageNames[i]);
			}
			
			Collator localeCollator = Collator.getInstance(userLocale);
			Arrays.sort(availableLanguageNames, localeCollator);
			Arrays.sort(tempLanguageNames, localeCollator);
			
			for(int i=0; i<availableLanguageCount; i++)
			{
				int isoIndex = allLanguageNames.indexOf(availableLanguageNames[i]);
				allLanguageIsos.add(availableLanguageIsos[isoIndex]);
				
				if(allReverseLanguageNames.containsKey(tempLanguageNames[i].toUpperCase()))
					allReverseLanguageNames.put(tempLanguageNames[i].toUpperCase()+" (" +availableLanguageIsos[isoIndex]+")" , availableLanguageIsos[isoIndex]);
				else
					allReverseLanguageNames.put(tempLanguageNames[i].toUpperCase(), availableLanguageIsos[isoIndex]);
			}
			
			if(otherLanguagesIsos != null)
			// agrega los q vengan en el vector
			for (int i = 0; i < otherLanguagesIsos.size(); i++) {
				allLanguageIsos.add(otherLanguagesIsos.get(i));
			}
			
			allLanguageNames = new ArrayList<String>(Arrays.asList(availableLanguageNames));
			if(otherLanguagesLabels != null)
			for (int i = 0; i < otherLanguagesLabels.size(); i++) {
				allLanguageNames.add(  otherLanguagesLabels.get(i) + ((withISO) ? " (" + otherLanguagesIsos.get(i) + ")" : "" ));
				if(allReverseLanguageNames.containsKey(otherLanguagesLabels.get(i).toUpperCase()))
					return ;
				allReverseLanguageNames.put(otherLanguagesLabels.get(i).toUpperCase(), otherLanguagesIsos.get(i));
			}
			
			
			languageNames.put(lang.toUpperCase(), allLanguageNames);
			languageIsos.put(lang.toUpperCase(), allLanguageIsos);
			reverseLanguageNames.put(lang.toUpperCase(), allReverseLanguageNames);
		}
		
		
		if(loadCountries){
			List<String> allCountryIsos = new ArrayList<String>();
			List<String> allCountryNames = new ArrayList<String>();
			HashMap<String, String> allReverseCountryNames = new HashMap<String, String>();
			
			
			// Se recopila la información de paises disponibles
			allCountryIsos = new ArrayList<String>();
			allCountryNames = new ArrayList<String>();
			String [] availableCountryIsos = Locale.getISOCountries();
			int availableCountryCount = availableCountryIsos.length;
			String [] availableCountryNames = new String[availableCountryCount];
			String [] tempCountryNames = new String[availableCountryCount];
			
			
			for(int i=0; i<availableCountryCount; i++)
			{
				availableCountryIsos[i] = availableCountryIsos[i].toUpperCase();
				Locale locale = new Locale("", availableCountryIsos[i]);
				availableCountryNames[i] = StringUtils.toCamelCase(locale.getDisplayCountry(userLocale), " ", userLocale, false, false);
				tempCountryNames[i] = StringUtils.toCamelCase(locale.getDisplayCountry(userLocale), " ", userLocale, false, false);
				availableCountryNames[i] += ((withISO) ? " (" + availableCountryIsos[i] + ")" : "" ) ; 
				allCountryNames.add(availableCountryNames[i]);
			}
			
			Arrays.sort(availableCountryNames);
			Arrays.sort(tempCountryNames);
			
			
			for(int i=0; i<availableCountryCount; i++)
			{
				int isoIndex = allCountryNames.indexOf(availableCountryNames[i]);
				allCountryIsos.add(availableCountryIsos[isoIndex]);
				
				if(allReverseCountryNames.containsKey(tempCountryNames[i].toUpperCase()))
					allReverseCountryNames.put(tempCountryNames[i].toUpperCase()+" (" +availableCountryIsos[isoIndex]+")" , availableCountryIsos[isoIndex]);
				else
					allReverseCountryNames.put(tempCountryNames[i].toUpperCase(), availableCountryIsos[isoIndex]);
			}
			
			// agrega los q vengan en el vector
			if(otherCountriesIsos != null)
				for (int i = 0; i < otherCountriesIsos.size(); i++) {
					allCountryIsos.add(otherCountriesIsos.get(i));
				}
			
			allCountryNames = new ArrayList<String>(Arrays.asList(availableCountryNames));
			if(otherCountriesLabels != null)
				for (int i = 0; i < otherCountriesLabels.size(); i++) {
					allCountryNames.add(otherCountriesLabels.get(i) + ((withISO) ? " (" + otherCountriesIsos.get(i) + ")" : "" ) );
					if(allReverseCountryNames.containsKey(otherCountriesLabels.get(i).toUpperCase()))
						return;
					allReverseCountryNames.put(otherCountriesLabels.get(i).toUpperCase(), otherCountriesLabels.get(i));
				}
			
			countryNames.put(lang.toUpperCase(), allCountryNames);
			countryIsos.put(lang.toUpperCase(), allCountryIsos);
			reverseCountryNames.put(lang.toUpperCase(), allReverseCountryNames);
		}
	}
	
	public static String getLanguageName(String languageIso, String targetLanguageIso, String targetCountryIso){
		Locale targetLocale = new Locale(targetLanguageIso, targetCountryIso);
		Locale languageLocale = new Locale(languageIso, "");
		return StringUtils.toCamelCase(languageLocale.getDisplayLanguage(targetLocale));
	}
	
	public static String getCountryName(String countryIso, String targetLanguageIso, String targetCountryIso){
		Locale targetLocale = new Locale(targetLanguageIso, targetCountryIso);
		Locale countryLocale = new Locale("", countryIso);
		return StringUtils.toCamelCase(countryLocale.getDisplayCountry(targetLocale));
	}
	
	
	public static void main(String[] args) {
		
		String lang = "EN";
		ISOUtils isoUtils = new ISOUtils();
		isoUtils.addISOCountry(lang, false);
		
		HashMap<String, List<String>> countryIsos = isoUtils.getCountryIsos();
		
		List<String> allCountryIsos = countryIsos.get(lang);
		System.out.println(" *********** COUNTRY ISOs ***********");
		for (int i = 0; i < allCountryIsos.size() - 200; i++) {
			String element = (String) allCountryIsos.get(i);
			System.out.println(element);
		}
		System.out.println(" *********** ");
		System.out.println(allCountryIsos.size());
		System.out.println(allCountryIsos.get(234));
		System.out.println(allCountryIsos.get(241));
		if(allCountryIsos.contains("VE")){
			System.out.println("TENGP VE");
		}
		
		isoUtils.addISOLanguage(lang, true);
		HashMap<String, List<String>> languageIsos = isoUtils.getLanguageIsos();
		List<String> allLanguageIsos = languageIsos.get(lang);
		System.out.println(" *********** LANGUAGE ISOs ***********");
		System.out.println(allLanguageIsos.size());
		System.out.println(allLanguageIsos.get(150));
		System.out.println(allLanguageIsos.get(187));
		
		String temp = "ES";
		if(allLanguageIsos.contains(temp)){
			System.out.println("TENGP ES");
		}
		
		/*
		// Como se dice EN en español de Venezuela
		System.out.println(ISOUtils.getLanguageName("EN", "ES", "VE"));
		// Como se dice US en español de Venezuela
		System.out.println(ISOUtils.getCountryName("US", "ES", "VE"));

		// Como se dice EN en inglés de EEUU
		System.out.println(ISOUtils.getLanguageName("EN", "EN", "US"));
		// Como se dice US en inglés de EEUU
		System.out.println(ISOUtils.getCountryName("US", "EN", "US"));
		*/
	}
}
