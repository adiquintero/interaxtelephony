package com.interax.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.contactmanager.date.ContactManagerDate;




/*
 * TODO hacer los JUnits para verificar que todos estos metodos funcionen perfecto
 * */

public class StringUtils 
{

	public static String join( String token, String[] strings )
	{
		if(strings==null)
			return null;

		StringBuffer sb = new StringBuffer();

		for( int x = 0; x < ( strings.length - 1 ); x++ )
		{
			sb.append( strings[x] );
			sb.append( token );
		}
		sb.append( strings[ strings.length - 1 ] );

		return( sb.toString() );
	}


	/**
	 * @param var, indica el string al que se le quiere hacer el Camel Case
	 * @return
	 */
	public static String toCamelCase(String var){
		return toCamelCase(var, " ", Locale.getDefault(), false, false);
	}

	/**
	 * @param var, indica el string al que se le quiere hacer el Java Case
	 * @return
	 */
	public static String toJavaCase(String var){
		return toCamelCase(var, " ", Locale.getDefault(), false, true);
	}

	/**
	 * @param var, indica el string al que se le quiere hacer el Camel Case
	 * @param separator, indica el String de separacion entre las palabras para hacerle el Camel Case, Ej: " ", "_", "*" 
	 * @return
	 */
	public static String toCamelCase( String var, String separator){
		return toCamelCase(var, separator, Locale.getDefault(), false, false);
	}

	/**
	 * @param var, indica el string al que se le quiere hacer el Java Case
	 * @param separator, indica el String de separacion entre las palabras para hacerle el Java Case, Ej: " ", "_", "*" 
	 * @return
	 */
	public static String toJavaCase(String var, String separator){
		return toCamelCase(var, separator, Locale.getDefault(), false, true);
	}

	/**
	 * @param var, indica el string al que se le quiere hacer el Camel Case
	 * @param separator, indica el String de separacion entre las palabras para hacerle el Camel Case, Ej: " ", "_", "*"
	 * @param removeSeparator, indica si se eliminar el separador
	 * @param javaCase, indica si el string resultante comienza en minúscula
	 * @param userLocale
	 * @return
	 */
	public static String toCamelCase(String var, String separator, Locale userLocale, boolean removeSeparator, boolean javaCase){

		if(var==null)
			return null;

		StringBuffer result = new StringBuffer();
		String[] pieces = var.split(separator);
		for(int i=0; i<pieces.length; i++){
			if(i==0 && javaCase)
				result.append(pieces[i].substring(0,1).toLowerCase(userLocale));
			else
				result.append(pieces[i].substring(0,1).toUpperCase(userLocale));
			result.append(pieces[i].substring(1).toLowerCase(userLocale));
			if(!removeSeparator && i!=pieces.length-1) 
				result.append(separator);
		}

//		while(var.contains(separator)){
//		var = var.substring(0,var.indexOf(separator))  + 
//		((var.charAt(var.indexOf(separator)+1)+"").toUpperCase(userLocale)) + 
//		var.toLowerCase().substring(var.indexOf(separator)+2) ;
//		}
		// var.substring(0,1) + var.toLowerCase(userLocale).substring(1);
		return result.toString();
	}


	/**
	 * this method transform localized double value in to Big Decimal. 
	 * @param localizedDouble, double value in localized format, Ej: #.##0,00; #,##0.00
	 * @param localizedIntSeparator, it's necesary to remove it if exits
	 * @param localizedDoubleSeparator, if this is distinct of , have to be replaced
	 * @return BigDecimal object represeting localizedDouble
	 */
	public static Long transformToLong(String localizedLong, String localizedIntSeparator, String localizedDoubleSeparator ){
		
		//if( localizedLong.indexOf(localizedIntSeparator) < localizedLong.indexOf(localizedDoubleSeparator)){
			String value = localizedLong.replace(localizedIntSeparator, "");

			if( value.contains(localizedDoubleSeparator) ){
				value = value.substring(0, value.indexOf(localizedDoubleSeparator));
			}

			return Long.parseLong(value);
		/*}else{
			return null;
		}*/
	}
	
	
	public static String transformLongToLocalizedLong(Long longValue, String localizedIntSeparator){
		String pattern = "#,##0";
		
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setGroupingSeparator(localizedIntSeparator.charAt(0));
		DecimalFormat df = new DecimalFormat(pattern,dfs);
		
		return df.format(longValue);
	}
	
	
	/**
	 * this method transform localized double value in to Big Decimal. 
	 * @param localizedDouble, double value in localized format, Ej: #.##0,00; #,##0.00
	 * @param localizedIntSeparator, it's necesary to remove it if exits
	 * @param localizedDoubleSeparator, if this is distinct of , have to be replaced
	 * @return BigDecimal object represeting localizedDouble
	 */
	public static BigDecimal transformToBigDecimal(String localizedDouble, String localizedIntSeparator, String localizedDoubleSeparator ){

		//if( localizedDouble.indexOf(localizedIntSeparator) < localizedDouble.indexOf(localizedDoubleSeparator)){
			String value = localizedDouble.replace(localizedIntSeparator, "");

			if(value.contains(localizedDoubleSeparator) && !".".equals(localizedDoubleSeparator))
				value = value.replace(localizedDoubleSeparator,".");

			return new BigDecimal(value);
		/*}else{
			return null;
		}*/
	}

	public static String transformToDoubleToLocalizedDouble(Double localizedDouble, String localizedIntSeparator, String localizedDoubleSeparator ){
		/*String value = Double.toString(localizedDouble);

		if(".".equals(localizedIntSeparator)){
			//if(value.contains(localizedIntSeparator)){
				value = value.replace(localizedIntSeparator, "");
				//value = value.replace(localizedDoubleSeparator, ",");
			//}							
		}else{
			value = value.replace(localizedIntSeparator, "");
			//value = value.replace(localizedIntSeparator, ".");
		}
		return value;*/
		String pattern = "#,##0.00";
		
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(localizedDoubleSeparator.charAt(0));
		dfs.setGroupingSeparator(localizedIntSeparator.charAt(0));
		DecimalFormat df = new DecimalFormat(pattern,dfs);
		return df.format(localizedDouble);
	}


	public static Double transformLocalizedDoubleToDB(String localizedDouble, String localizedIntSeparator, String localizedDoubleSeparator ){
		
		//if( localizedDouble.indexOf(localizedIntSeparator) < localizedDouble.indexOf(localizedDoubleSeparator)){
			String value = localizedDouble.replace(localizedIntSeparator, "");
		
			if(value.contains(localizedDoubleSeparator) && !".".equals(localizedDoubleSeparator))
				value = value.replace(localizedDoubleSeparator,".");

			return Double.valueOf(value);
		/*}else{
			return null;
		}*/
	}
	
	
	public static String transformBigDecimalToLocalizedDouble(BigDecimal bdBigDecimal, String localizedIntSeparator, String localizedDoubleSeparator ){

		/*DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(localizedDoubleSeparator.charAt(0));
		dfs.setGroupingSeparator(localizedIntSeparator.charAt(0));
		DecimalFormat df = new DecimalFormat("#,###.##",dfs);
		
		String bdBigDecimalAux = bdBigDecimal.toPlainString();
		String[] separatedNumber = bdBigDecimalAux.split("\\.");
		Long longPart = Long.parseLong(separatedNumber[0]);
		String intPartAux = df.format(longPart);
		
		if(separatedNumber.length>1){
			
			String temp = (df.format(Double.parseDouble("0."+separatedNumber[1]))).replace("0"+localizedDoubleSeparator, "");
			return intPartAux + localizedDoubleSeparator + temp; 
		}
		else
			return intPartAux;*/
		
		return transformBigDecimalToLocalizedDouble(bdBigDecimal, localizedIntSeparator, localizedDoubleSeparator, "#,##0.000");
	}
	
	public static String transformBigDecimalToLocalizedDouble(BigDecimal bdBigDecimal, String localizedIntSeparator, String localizedDoubleSeparator, String pattern ){

		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(localizedDoubleSeparator.charAt(0));
		dfs.setGroupingSeparator(localizedIntSeparator.charAt(0));
		DecimalFormat df = new DecimalFormat(pattern,dfs);
		
		return df.format(bdBigDecimal);
	}
	
	
	public static String escapeQuotes(String text){
		return text.replace("\"","\\\"").replace("\'","\\\'");
	}
	
	
	public static void main(String[] args) throws ParseException {
		
		/*String hola = "USER_AGENT"; //"hola mundo * cruel";
		String hola2 = "DATE";
		
		System.out.println(StringUtils.toCamelCase(hola, "_", new Locale("ES"), true, true));
		System.out.println(StringUtils.toCamelCase(hola, "_", new Locale("ES"), true, false));
		
		
		System.out.println(StringUtils.toCamelCase(hola2, "_", new Locale("ES"), true, true));
		System.out.println(StringUtils.toCamelCase(hola2, "_", new Locale("ES"), true, false));
		
		//System.out.println(toCamelCase(hola, " ", new Locale("ES"), false, false));
		
//		String bdString = "0.0000001000";
		String bdString = "0.0000000000";
		
		
		
		BigDecimal bd = new BigDecimal(bdString);
		bd = new BigDecimal(bdString);
		System.out.println(transformBigDecimalToLocalizedDouble(bd,".",","));
		System.out.println(transformBigDecimalToLocalizedDouble(bd,",","."));
		
		System.out.println(transformBigDecimalToLocalizedDouble(bd,".",",","#,##0.0#"));
		System.out.println(transformBigDecimalToLocalizedDouble(bd,".",",","#,###.0#"));
		System.out.println(transformBigDecimalToLocalizedDouble(bd,".",",","#,###.##"));
		System.out.println(transformBigDecimalToLocalizedDouble(bd,".",",","#,##0.00000"));
		
		bd = new BigDecimal("4356.1234567890"
//				"4356.515000"
				);
		System.out.println(bd.toPlainString());
		System.out.println(com.interax.utils.StringUtils.transformBigDecimalToLocalizedDouble(bd,".",","));
		System.out.println(com.interax.utils.StringUtils.transformBigDecimalToLocalizedDouble(bd,".",",", "#,###.##"));
		System.out.println(com.interax.utils.StringUtils.transformBigDecimalToLocalizedDouble(bd,".",",", "#,##0.#####"));
		System.out.println(com.interax.utils.StringUtils.transformBigDecimalToLocalizedDouble(bd,".",",", "#,##0.00000"));
		
		
		System.out.println("******************** ");
		System.out.println("******************** ");
		System.out.println(StringUtils.transformToLong("156456459", ",", "."));
		
		System.out.println(StringUtils.transformToLong("156456.459", ",", "."));
		System.out.println(StringUtils.transformToLong("156,456.459", ",", "."));
		
		System.out.println(StringUtils.transformToLong("156456,459", ".", ","));
		System.out.println(StringUtils.transformToLong("156.456,459", ".", ","));
		
		System.out.println("******************** ");
		System.out.println("******************** ");
		System.out.println(StringUtils.transformLongToLocalizedLong(156456459L, ","));
		
		System.out.println("******************** ------------------------------");
		System.out.println("******************** ------------------------------");
		
		System.out.println(StringUtils.transformToBigDecimal("1,564.459", ",", "."));
		System.out.println(StringUtils.transformToBigDecimal("1.456,459", ".", ","));
		System.out.println(Double.valueOf("1456.00").doubleValue());*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sqlFormatter = new SimpleDateFormat(ContactManagerDate.MySQLPatternDate);
		//sdf.applyPattern("MM/dd/yyyy");
		//Date d2 = sdf.parse("2009/10/25");
		//String result = "".replace("-", "/");
		//Date d2 = new Date(result);
		//String fecha = sdf.format(d2);
		//System.out.println(fecha);
		
		
//		Date d1 = sdf.parse("10/25/2009");
//		String papa = sqlFormatter.format(d1);
//		System.out.println(papa);
//		
//		System.out.println(StringUtils.transformToDoubleToLocalizedDouble(Double.valueOf("1234.12").doubleValue(), ",", "."));
//		System.out.println(StringUtils.transformToDoubleToLocalizedDouble(Double.valueOf("1234.12").doubleValue(), ".", ","));
//		
//		System.out.println(Double.toString(StringUtils.transformLocalizedDoubleToDB("1,456.22", ",", ".")));
//		System.out.println(Double.toString(StringUtils.transformLocalizedDoubleToDB("1.456,22", ".", ",")));
//		
//		String quotedString = "Dia'Angelo";
//		String testText = " UPDATE customer SET name='"+StringUtils.escapeQuotes(quotedString)+"' WHERE id = 1 ";
//		System.out.println(testText);
		
//		System.out.println(StringUtils.transformBigDecimalToLocalizedDouble(new BigDecimal(1234.12), ",", "."));
//		Double a = new Double("0.000");
//		String b = StringUtils.transformBigDecimalToLocalizedDouble(new BigDecimal(1234.12), ",", ".");
//		//System.out.println((Double)Double.valueOf(StringUtils.transformLocalizedDoubleToDB("1,000.000",",",".")));
//		System.out.println(a);
//		
//		System.out.println(StringUtils.transformLocalizedDoubleToDB(b,",",".").toString());
//		System.out.println(new BigDecimal("0,000"));
		
		BigDecimal a = new BigDecimal(StringUtils.transformLocalizedDoubleToDB("0.003", ",", "."));
		//System.out.println(a);
		
		//double d = StringUtils.transformLocalizedDoubleToDB("1,1234", ".", ",");
		//System.out.println(d);
		Double ds = new Double("0.000");
		String aux = StringUtils.transformBigDecimalToLocalizedDouble(new BigDecimal(ds),".", ",");
		//System.out.println(aux);
		
		String aux2 = StringUtils.transformToDoubleToLocalizedDouble(Double.parseDouble("20.000"),".",",");
		System.out.println(aux2);
	}
}


