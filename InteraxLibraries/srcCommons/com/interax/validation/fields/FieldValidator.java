
package com.interax.validation.fields;

import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.Hashtable;
import java.util.List;

import com.interax.validation.fields.exception.InvalidBooleanValueException;
import com.interax.validation.fields.exception.InvalidDataTypeException;
import com.interax.validation.fields.exception.InvalidDateValueException;
import com.interax.validation.fields.exception.InvalidDoubleValueException;
import com.interax.validation.fields.exception.InvalidIntValueException;
import com.interax.validation.fields.exception.InvalidListNameException;
import com.interax.validation.fields.exception.InvalidListValueException;
import com.interax.validation.fields.exception.InvalidStringValueException;
import java.util.HashMap;


public abstract class FieldValidator {

	private String booleanTrueValue;
	private String booleanFalseValue;
	protected String dateFormat;
	protected String intSeparator;
	protected String doubleSeparator;
	protected HashMap<String, List<String>> list;
	
	
	public FieldValidator(String booleanTrueValue, String booleanFalseValue, String dateFormat, String intSeparator, String doubleSeparator, HashMap<String, List<String>> list) {
		super();
		this.booleanTrueValue = booleanTrueValue;
		this.booleanFalseValue = booleanFalseValue;
		this.dateFormat = dateFormat;
		this.intSeparator = intSeparator;
		this.doubleSeparator = doubleSeparator;
		this.list = list;
	}
	
	/**
	 * This method validate the string value, according of the specified dataType
	 * @param thisStr
	 * @param dataType
	 * @throws InvalidBooleanValueException
	 * @throws InvalidDateValueException
	 * @throws InvalidDoubleValueException
	 * @throws InvalidIntValueException
	 * @throws InvalidStringValueException
	 * @throws InvalidListValueException
	 * @throws InvalidDataTypeException
	 */
	public void validateValue(String thisStr, FieldDataType dataType) 
						throws InvalidBooleanValueException, InvalidDateValueException, InvalidDoubleValueException, 
							InvalidIntValueException, InvalidStringValueException, InvalidListValueException, InvalidDataTypeException,
							InvalidListNameException, InvalidListValueException, 
							InvalidDataTypeException {
			
		if (thisStr == null){
			throw new IllegalArgumentException("el valor a validar debe ser distinto de null");
		}

		switch (dataType) {
		case BOOLEAN:
			validateBoolean(thisStr);
			break;
		case DATE:
			validateDate(thisStr);
			break;
		case DOUBLE:
			validateDouble(thisStr);
			break;
		case INT:
			validateInt(thisStr);
			break;
		case STRING:
			validateString(thisStr);
			break;
		case LIST:
			//throw new InvalidDataTypeException("use este metodo solo FieldDataType distintos de: " + FieldDataType.LIST.name());
			validateListValue(thisStr, dataType.name());
		default:
			break;
		}
	}
	
	/**
	 * This method validate if a string value, is equals and ignore case to other string value in Hashtable<String, List<String>> list, 
	 * according with the list specified for the list name
	 * @param thisStr
	 * @param dataType
	 * @param listName
	 * @throws InvalidListNameException
	 * @throws InvalidListValueException
	 * @throws InvalidDataTypeException
	 */
	protected void validateListValue(String thisStr, String listName) 
						throws InvalidListNameException, InvalidListValueException, 
							InvalidDataTypeException  {
		/*
		if (thisStr == null){
			throw new IllegalArgumentException("el valor debe ser distinto de null");
		}
		switch (dataType) {
		case LIST:
			validateListValue(thisStr, listName);
			break;
		default: 
			throw new InvalidDataTypeException("use este metodo solo para FieldDataType de tipo: " + FieldDataType.LIST.name());
		}*/
		
		if(listName == null || listName.length() == 0){
			throw new InvalidListNameException("Debe especificar el nombre de la lista ");
		}else{
			
			List<String> tempList = list.get(listName);
			if (tempList == null){
				throw new InvalidListNameException("lista vacia");
			}
			
			for (int i = 0; i < tempList.size(); i++) {
				String tempString = tempList.get(i);
				
				if (tempString.equalsIgnoreCase(thisStr)){
					return ;
				}
			}
			throw new InvalidListValueException("No hizo match con ningun valor en la lista");
		}
	}
	
	private void validateBoolean(String thisStr) throws InvalidBooleanValueException {
		try{
			if(thisStr!= null && !booleanTrueValue.equalsIgnoreCase(thisStr) && !booleanFalseValue.equalsIgnoreCase(thisStr)){
				throw new InvalidDoubleValueException("Valor de tipo booleano invalido");
			}
		}catch (Exception e) {
			throw new InvalidBooleanValueException("Valor de tipo booleano invalido");
		}
	}
	
	private void validateDate(String thisStr) throws InvalidDateValueException {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Date a = sdf.parse(thisStr);
			if (a == null || !sdf.format(a).equals(thisStr))
				throw new InvalidDateValueException("Valor de la fecha invalido");
			
		}catch (Exception e) {
			throw new InvalidDateValueException("Valor de la fecha invalido");
		}
	}
	
	private void validateDouble(String thisStr) throws InvalidDoubleValueException {
		
		try{
			if(!thisStr.contains(intSeparator) && !thisStr.contains(doubleSeparator)){
				Integer.parseInt(thisStr);
			}else{

				String tempIntSeparator = ((intSeparator.contains(".")) ? "\\." :intSeparator);
				String tempDoubleSeparator = ((doubleSeparator.contains(".")) ? "\\." :doubleSeparator);

				String regex = "[-|+]?((\\d{1,3}("+tempIntSeparator+"\\d{3})*)|(\\d+))("+tempDoubleSeparator+"\\d+)?";
				if(thisStr!=null && !thisStr.matches(regex)){
					throw new InvalidDoubleValueException("Valor de tipo decimal invalido");
				}
			}
		}catch (Exception e) {
				throw new InvalidDoubleValueException("Valor de tipo decimal invalido");
			}
		}
	
	private void validateInt(String thisStr) throws InvalidIntValueException {
		try{
			if(!thisStr.contains(intSeparator)){
				Integer.parseInt(thisStr);
			}else{
				String tempIntSeparator = ((intSeparator.contains(".")) ? "\\." :intSeparator);

				String regex = "[-|+]?((\\d{1,3}("+tempIntSeparator+"\\d{3})*))?";
				if(thisStr!=null && !thisStr.matches(regex)){
					throw new InvalidIntValueException("Valor de tipo int invalido");
				}
			}
		}catch (Exception e) {
			throw new InvalidIntValueException("Valor de tipo int invalido");
		}
	}
	
	private void validateString(String thisStr) throws InvalidStringValueException {
		if(thisStr.length() == 0){
			throw new InvalidStringValueException("Valor de tipo string invalido");
		}
	}
}
