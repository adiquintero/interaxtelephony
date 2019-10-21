package com.contactmanager.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.interax.date.InteraxDate;

public class ContactManagerDate extends InteraxDate {
	
	private static final long serialVersionUID = 1L;
	
	public static final String SYSTEM_TIME_ZONE_ID = "GMT0";
	private static final String OUR_BASE_TIME_ZONE_ID = SYSTEM_TIME_ZONE_ID;
	
	public ContactManagerDate(){
		//this(Calendar.getInstance(TimeZone.getTimeZone(ContactManagerDate.OUR_BASE_TIME_ZONE_ID)).getTime().getTime());
		this(new Date().getTime());
	}
	
	public ContactManagerDate(long timeInMilliSeconds){
		this(timeInMilliSeconds, ContactManagerDate.OUR_BASE_TIME_ZONE_ID);
	}

	public ContactManagerDate(long timeInMilliSeconds, String displayTimeZoneId){
		super(timeInMilliSeconds, displayTimeZoneId);
	}


	public ContactManagerDate(String incomingTime) throws ParseException, Exception {
		this(incomingTime, ContactManagerDate.OUR_BASE_TIME_ZONE_ID, ContactManagerDate.OUR_BASE_TIME_ZONE_ID);
	}

	public ContactManagerDate(String incomingTime, String displayTimeZoneId) throws ParseException, Exception {
		this(incomingTime, ContactManagerDate.OUR_BASE_TIME_ZONE_ID, displayTimeZoneId);
	}
	
	public ContactManagerDate(String incomingDateTime, String incomingTimeZoneId, String displayTimeZoneId) throws ParseException, Exception {
		super(incomingDateTime, incomingTimeZoneId, displayTimeZoneId);
	}
	
	protected ContactManagerDate(Date a, String incomingTimeZoneId) throws Exception {
		super(a, incomingTimeZoneId);
	}
	
	
	public static ContactManagerDate getNow() {
		return ContactManagerDate.getNow(ContactManagerDate.OUR_BASE_TIME_ZONE_ID);
	}

	public static ContactManagerDate getNow(String displayTimeZoneId) {
		try
		{
			return new ContactManagerDate(new Date().getTime(), displayTimeZoneId);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static ContactManagerDate parse(String time, String pattern, String incomingTimeZoneId ) throws ParseException, Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		return new ContactManagerDate(sdf.parse(time), incomingTimeZoneId);
	}
	
	public static List<String> getAvaibleTimeZoneIDs(){
		return InteraxDate.getAvaibleTimeZoneIDs();
	}
	
	public static Timestamp getNullTimestamp(){
		return InteraxDate.getNullTimestamp();
	}
	
	public static String getDayOfWeekLiteral(int i){
		return InteraxDate.getDayOfWeekLiteral(i);
	}
	
	public static String getMonthOfYearLiteral(int i){
		return InteraxDate.getMonthOfYearLiteral(i);
	}
	
	
	
	
	public static void testMethods(){

		System.out.println("INI");
		SimpleDateFormat sdf = new SimpleDateFormat(InteraxDate.MySQLPatternDateTime);
		Date myDate = new Date();
		long longTimeInMillis = myDate.getTime();
//		String incomingTimeZoneId 	= "America/Caracas";
//		String displayTimeZoneId 	= "GMT-4";
		String incomingTimeZoneId 	= "America/Caracas";
		String displayTimeZoneId 	= "America/Caracas";

		String myDatStr = sdf.format(myDate);
		System.out.println(new SimpleDateFormat(InteraxDate.MySQLPatternDateTime+" zzz").format(myDate));
		try{
			System.out.println("----1");
			Thread.sleep(1000);
			
			System.out.println(new ContactManagerDate());
			System.out.println("----2");
			
			System.out.println(new ContactManagerDate(longTimeInMillis));
			System.out.println(new ContactManagerDate(longTimeInMillis, displayTimeZoneId));
			System.out.println("----3");
			
			System.out.println(new ContactManagerDate(myDatStr));
			System.out.println(new ContactManagerDate(myDatStr, displayTimeZoneId));
			System.out.println(new ContactManagerDate(myDatStr, incomingTimeZoneId, displayTimeZoneId));
			System.out.println("----4");
			
			System.out.println(ContactManagerDate.getNow());
			System.out.println(ContactManagerDate.getNow(displayTimeZoneId));
			System.out.println("----");
			
		}catch (Exception e) {
			e.printStackTrace();

		}
		System.out.println("FIN");
	}
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		ContactManagerDate.testMethods();
		Date myDate = new Date();
		//long longTimeInMillis = myDate.getTime();
		//ContactManagerDate now = new ContactManagerDate(longTimeInMillis+30);
		System.out.println(myDate);
		int a = myDate.getSeconds();
		myDate.setSeconds(a+30);
		System.out.println(myDate);
		
		ContactManagerDate testDateA = new ContactManagerDate();
		System.out.println(testDateA);
		testDateA.add(ContactManagerDate.SECOND, 20);
		System.out.println(testDateA);
		
		ContactManagerDate testDate;
		try {
			testDate = new ContactManagerDate("2009-08-26 00:00:00","America/Caracas","America/Caracas");
			System.out.println(testDate.format("dd/MM/yyyy hh:mm:ss", "America/Caracas"));
			System.out.println("esta la de arriba");
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		/*System.out.println(new Date().getTime());
		System.out.println(Calendar.getInstance(TimeZone.getTimeZone("GMT0")).getTime().getTime());
		System.out.println(Calendar.getInstance(TimeZone.getTimeZone("GMT-4")).getTime().getTime());
		
		System.out.println(new ContactManagerDate());
		System.out.println(ContactManagerDate.getNow());
		
		System.out.println(new InteraxDate());
		System.out.println(InteraxDate.getNow());*/
	}
	
}
