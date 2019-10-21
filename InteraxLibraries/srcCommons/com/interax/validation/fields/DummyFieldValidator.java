
package com.interax.validation.fields;

import java.util.ArrayList;
//import java.util.Hashtable;
import java.util.List;

import com.interax.validation.fields.exception.FieldValidatorException;
import com.interax.validation.fields.exception.InvalidBooleanValueException;
import com.interax.validation.fields.exception.InvalidDateValueException;
import com.interax.validation.fields.exception.InvalidDoubleValueException;
import com.interax.validation.fields.exception.InvalidIntValueException;
import com.interax.validation.fields.exception.InvalidListValueException;
import com.interax.validation.fields.exception.InvalidStringValueException;
import java.util.HashMap;


public class DummyFieldValidator extends FieldValidator {

	
	public DummyFieldValidator(String booleanTrueValue, String booleanFalseValue, String dateFormat, String intSeparator, String doubleSeparator, HashMap<String, List<String>> list) {
		super(booleanTrueValue, booleanFalseValue, dateFormat, intSeparator, doubleSeparator, list);
	}
	
	
	public static void main(String[] args) {
		
		String booleanTrueValue = "TrUe";
		String booleanFalseValue = "fAlsO";
		String dateFormat = "MM/dd/yyyy";
		String intSeparator = ".";
		String doubleSeparator = ",";
		HashMap<String, List<String>> list = new HashMap<String, List<String>>();

		List<String> booleanList = new ArrayList<String>();
		booleanList.add("true");
		booleanList.add("false");
		booleanList.add("yes");
		booleanList.add("no");
		
		list.put(FieldDataType.BOOLEAN.name(), booleanList);
		
		DummyFieldValidator fv = new DummyFieldValidator(booleanTrueValue, booleanFalseValue, dateFormat, intSeparator, doubleSeparator, list);
		
		
		String values [] = {
				/*"si", "no", "YeS", "TrUe","falSo",
				"si", "no", "YeS", "TrUe","falSo",
				"13/05/1984", "00/05/1984", "01/05/1984", "01-05-1984", "MM/dd/yyyy",  */
				"123456", "123.456.87", "adc.sdfsd", "123,456", "1.123.123,46", "1.123.12346", "1.123.123",
				};
		
		FieldDataType dataTypes [] = {
				/*FieldDataType.BOOLEAN, FieldDataType.BOOLEAN, FieldDataType.BOOLEAN, FieldDataType.BOOLEAN, FieldDataType.BOOLEAN,
				FieldDataType.LIST, FieldDataType.LIST, FieldDataType.LIST, FieldDataType.LIST, FieldDataType.LIST,
				FieldDataType.DATE, FieldDataType.DATE, FieldDataType.DATE, FieldDataType.DATE, FieldDataType.DATE, */
				FieldDataType.INT, FieldDataType.INT, FieldDataType.INT, FieldDataType.INT, FieldDataType.INT, FieldDataType.INT, FieldDataType.INT,
				};
		
		
		for (int i = 0; i < values.length; i++) {
			
			boolean valido = true; 
			try {
				String value = values [i];
				FieldDataType dataType = dataTypes[i];
				
				switch (dataType) {
				case LIST:
					fv.validateListValue(value, FieldDataType.BOOLEAN.name());
					break;
				default:
					fv.validateValue(value, dataType);
					break;
				}
				
			} catch (InvalidBooleanValueException e) {
				// TODO Auto-generated catch block
				valido = false; 
				e.printStackTrace();
			} catch (InvalidDateValueException e) {
				// TODO Auto-generated catch block
				valido = false;
				e.printStackTrace();
			} catch (InvalidDoubleValueException e) {
				// TODO Auto-generated catch block
				valido = false;
				e.printStackTrace();
			} catch (InvalidIntValueException e) {
				// TODO Auto-generated catch block
				valido = false;
				e.printStackTrace();
			} catch (InvalidStringValueException e) {
				// TODO Auto-generated catch block
				valido = false;
				e.printStackTrace();
			} catch (InvalidListValueException e) {
				// TODO Auto-generated catch block
				valido = false;
				e.printStackTrace();
			} catch (FieldValidatorException e) {
				// TODO Auto-generated catch block
				valido = false;
				e.printStackTrace();
			}
			System.out.println(" ************* " + i + " valido: " +valido);
		}
	}
}
