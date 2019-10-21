package com.interax.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;


public class InteraxDate extends java.util.GregorianCalendar {

	private static final long serialVersionUID = 1L;

	public static final String MySQLPatternDateTime = "yyyy-MM-dd HH:mm:ss";
	public static final String MySQLPatternDate = "yyyy-MM-dd";
	public static final String MySQLPatternTime = "HH:mm:ss";

	public static final String emptyMySQLDate = "0000-00-00 00:00:00";
	public static final String emptyInteraxDate = "0002-11-30 00:00:00";

	public static final String myTimeZone = "myTimeZone";
	public static final String originTimeZone = "originTimeZone";

	private static final String OUR_BASE_TIME_ZONE_ID = "GMT0";

	public static final String DATETIME_REGEX = "^((\\d{2})|(\\d{4}))-(\\d{1,2})-(\\d{1,2}) (\\d{2}):(\\d{2}):(\\d{2})(\\.(\\d)+)?$";
	public static final String DATE_REGEX = "^((\\d{2})|(\\d{4}))-(\\d{1,2})-(\\d{1,2})$";
	public static final String TIME_REGEX = "^(\\d{2}):(\\d{2}):(\\d{2})(\\.(\\d)+)?$";

	public static final InteraxDate NULL_DATE = new InteraxDate(-62075462399330L);

	public static final int MINUTE_AFTER_NOW = 5;

	private InteraxDateType type;
	private String displayTimeZoneId;

	/**
	 * An array of week names used to map names to integer values.
	 */
	private static final String[] weekNames = { "Sun", "Mon", "Tue", "Wed",
												"Thu", "Fri", "Sat" };
	/**
	 * An array of month names used to map names to integer values.
	 */
	private static final String[] monthNames = {"Jan", "Feb", "Mar", "Apr",
												"May", "Jun", "Jul", "Aug",
												"Sep", "Oct", "Nov", "Dec" };
	/**
	 * Creates a new Date Object representing the current time.
	 */


	/**
	 * Create new InteraxDate using the default time zone
	 */
	public InteraxDate(){
		this(new Date().getTime());
	}

	/**
	 * TODO!!! Test This constructor
	 * Create new InteraxDate using the default time zone
	 */
	public InteraxDate(long timeInMilliSeconds){
		this(timeInMilliSeconds, InteraxDate.OUR_BASE_TIME_ZONE_ID);
	}

	/**
	 * TODO!!! Test This constructor
	 * Create new InteraxDate using the default time zone
	 */
	public InteraxDate(long timeInMilliSeconds, String displayTimeZoneId){
		super(TimeZone.getTimeZone("GMT0"));
		this.setTimeInMillis(timeInMilliSeconds); //- this.getTimeZone().getRawOffset()
		this.displayTimeZoneId = displayTimeZoneId ;
		this.type = InteraxDateType.DATETIME;
	}

	/**
	 * Create new InteraxDate using the given time yyyy-MM-dd HH:mm:ss in GMT0 TimeZone
	 * @param time - in the format: yyyy-MM-dd HH:mm:ss, and the GMT0 TimeZone
	 * @throws ParseException
	 */
	public InteraxDate(String incomingTime) throws ParseException, Exception {
		this(incomingTime, InteraxDate.OUR_BASE_TIME_ZONE_ID, InteraxDate.OUR_BASE_TIME_ZONE_ID);
	}


	/**
	 * Create new InteraxDate using the given time yyyy-MM-dd HH:mm:ss in timeZoneId, load the GMT TimeZone
	 * @param incomingTime  - in the format: yyyy-MM-dd HH:mm:ss
	 * @param creationTimeZoneId - id that identify the TimeZone for the first Argument
	 * @throws ParseException
	 * @throws Exception
	 */
	public InteraxDate(String incomingTime, String displayTimeZoneId) throws ParseException, Exception {
		this(incomingTime, InteraxDate.OUR_BASE_TIME_ZONE_ID, displayTimeZoneId);
	}

	public InteraxDate(String incomingDateTime, String incomingTimeZoneId, String displayTimeZoneId) throws ParseException, Exception {

		super(TimeZone.getTimeZone(incomingTimeZoneId));

		if(emptyMySQLDate.equals(incomingDateTime)){
			incomingDateTime = emptyInteraxDate;
		}

		int year = 0, month = InteraxDate.JANUARY, day = 0, hour = 0, minute = 0, second = 0, index = 0;


		if(incomingDateTime.matches(InteraxDate.DATETIME_REGEX)) this.type = InteraxDateType.DATETIME;
		else
			if(incomingDateTime.matches(InteraxDate.DATE_REGEX)) this.type = InteraxDateType.DATE;
			else if(incomingDateTime.matches(InteraxDate.TIME_REGEX)) this.type = InteraxDateType.TIME;

		if(this.type==null)
			throw new Exception("Bad incomingTime please fix this: "+incomingDateTime);

		// Parsear la fecha
		String incomingDate = incomingDateTime;
		switch(this.type)
		{
		case DATETIME:
			index = incomingDate.indexOf(" ");
			incomingDate = incomingDateTime.substring(0,index);

		case DATE:
			index = incomingDate.indexOf("-");
			year = Integer.parseInt(incomingDate.substring(0, index));
			incomingDate = incomingDate.substring(index+1);

			index = incomingDate.indexOf("-");
			month = Integer.parseInt(incomingDate.substring(0, index));
			incomingDate = incomingDate.substring(index+1);

			day = Integer.parseInt(incomingDate);
			break;

		case TIME:
			break;
		}

		// Parsear la hora
		String incomingTime = incomingDateTime;
		switch(this.type)
		{
		case DATETIME:
			index = incomingTime.indexOf(" ");
			incomingTime = incomingDateTime.substring(index+1);

		case TIME:
			index = incomingTime.indexOf(":");
			hour = Integer.parseInt(incomingTime.substring(0,index));
			incomingTime = incomingTime.substring(index+1);

			index = incomingTime.indexOf(":");
			minute = Integer.parseInt(incomingTime.substring(0,index));
			incomingTime = incomingTime.substring(index+1);

			index = incomingTime.indexOf(".");
			if(index>0) incomingTime = incomingTime.substring(0,index);
			second = Integer.parseInt(incomingTime);        	  
			break;

		case DATE:
			break;
		}
		
		this.set(Calendar.YEAR, year);
		this.set(Calendar.MONTH, month-1);
		this.set(Calendar.DAY_OF_MONTH, day);
		this.set(Calendar.HOUR_OF_DAY, hour);
		this.set(Calendar.MINUTE, minute);
		this.set(Calendar.SECOND, second);
		this.displayTimeZoneId = displayTimeZoneId;
		
	}

	protected InteraxDate(Date date, String incomingTimeZoneId) throws Exception {
		
		/*
		super(TimeZone.getTimeZone(incomingTimeZoneId));

		this.set(Calendar.YEAR, a.getYear()+1900);
		this.set(Calendar.MONTH, a.getMonth());               // 0..11
		this.set(Calendar.DAY_OF_MONTH, a.getDate());
		this.set(Calendar.HOUR_OF_DAY, a.getHours());            // 0..23
		this.set(Calendar.MINUTE, a.getMinutes());
		this.set(Calendar.SECOND, a.getSeconds());

		this.type = InteraxDateType.DATETIME;
		this.displayTimeZoneId = incomingTimeZoneId;
		*/
		
		this(new SimpleDateFormat(MySQLPatternDateTime).format(date), incomingTimeZoneId, incomingTimeZoneId);
		
	}

	public static InteraxDate getNow() {
		return InteraxDate.getNow(InteraxDate.OUR_BASE_TIME_ZONE_ID);
	}

	public static InteraxDate getNow(String displayTimeZoneId) {
		try
		{
			return new InteraxDate(new Date().getTime(), displayTimeZoneId);
		}
		catch(Exception e)
		{
			return null;
		}
	}


	public String getDisplayTimeZoneId() {
		return displayTimeZoneId;
	}

	public void setDisplayTimeZoneId(String displayTimeZoneId) {
		this.displayTimeZoneId = displayTimeZoneId;
	}


	/**
	 * Create new InteraxDate using the given time yyyy-MM-dd HH:mm:ss in the default TimeZone
	 * @param time
	 * @param pattern
	 * @return a InteraxDate
	 * @throws ParseException
	 */
	public static InteraxDate parse(String time, String pattern, String incomingTimeZoneId ) throws ParseException, Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		return new InteraxDate(sdf.parse(time), incomingTimeZoneId);
	}


	/**
	 * Format the InteraxDate using the given pattern, this pattern have to match with SimpleDateFormat
	 * @param pattern
	 * @return String using the pattern and using GMT0
	 * @throws ParseException
	 */
	public String format(String pattern) throws ParseException {
		return format(pattern, displayTimeZoneId);
	}
	

	/**
	 * Format the InteraxDate using the given pattern, this pattern have to match with SimpleDateFormat
	 * @param pattern
	 * @param timeZoneId
	 * @return String using the pattern and using GMT0
	 * @throws ParseException
	 */
	public String format(String pattern, String timeZoneId) throws ParseException {
		DateFormat df = new SimpleDateFormat(pattern);
		df.setTimeZone(TimeZone.getTimeZone(timeZoneId));
		return df.format(this.getTime());
	}

	
	

	public String toString()
	{
		Calendar g = new GregorianCalendar(TimeZone.getTimeZone(displayTimeZoneId));
		g.setTimeInMillis(this.getTimeInMillis());

//		int offset = TimeZone.getTimeZone(displayTimeZoneId).getRawOffset();
//		offset -= TimeZone.getTimeZone(displayTimeZoneId).getOffset(0);
//		g.add(Calendar.MILLISECOND, offset);


		g.setTimeZone(TimeZone.getTimeZone(displayTimeZoneId));
		String day = "0" + g.get(Calendar.DATE);
		String hour = "0" + g.get(Calendar.HOUR_OF_DAY);
		String min = "0" + g.get(Calendar.MINUTE);
		String sec = "0" + g.get(Calendar.SECOND);
		String year = "000" + g.get(Calendar.YEAR);

		StringBuffer outString = new StringBuffer();
		switch(this.type)
		{
		case DATETIME:

		case DATE:
			outString.append(weekNames[g.get(Calendar.DAY_OF_WEEK) - 1] + " "+ monthNames[g.get(Calendar.MONTH)] + " "+ day.substring(day.length() - 2) + " ");
			break;
		}

		switch(this.type)
		{
		case DATETIME:

		case TIME:
			outString.append(hour.substring(hour.length() - 2) + ":"
					+ min.substring(min.length() - 2) + ":"
					+ sec.substring(sec.length() - 2)+ " ");
			break;
		}

		outString.append(g.getTimeZone().getDisplayName(false, TimeZone.SHORT));

		switch(this.type)
		{
		case DATETIME:

		case DATE:
			outString.append(" " + year.substring(year.length() - 4));
			break;
		}


		/*return weekNames[g.get(Calendar.DAY_OF_WEEK) - 1] + " "
        + monthNames[g.get(Calendar.MONTH)] + " "
        + day.substring(day.length() - 2) + " "
        + hour.substring(hour.length() - 2) + ":"
        + min.substring(min.length() - 2) + ":"
        + sec.substring(sec.length() - 2) + " "
        +
        g.getTimeZone().getDisplayName(false, TimeZone.SHORT) + " " +
        year.substring(year.length() - 4);*/
		return outString.toString();
	}


	/**
	 * using pattern "E M dd HH:mm:ss zzz Z yyyy"
	 * @param displayTimeZoneId
	 * @return a String with the given timeZoneId and formatted with pattern "E M dd HH:mm:ss zzz Z yyyy"
	 */
	public String toString(String displayTimeZoneId){
		DateFormat df = new SimpleDateFormat("E M dd HH:mm:ss zzz yyyy");
		df.setTimeZone(TimeZone.getTimeZone(displayTimeZoneId));
		return df.format(this.getTime());
	}


	/**
	 * @return a String asociated wiht MySql format (yyyy-MM-dd HH:mm:ss)
	 */
	public String toMySqlString() throws ParseException {
		return this.toMySqlString(InteraxDate.MySQLPatternDateTime);
	}


	/**
	 * @param pattern
	 * @return a String with the given pattern
	 */ 
	public String toMySqlString(String pattern) throws ParseException{
		/*DateFormat f = new SimpleDateFormat(pattern);
		f.setTimeZone(TimeZone.getTimeZone(InteraxDate.OUR_BASE_TIME_ZONE_ID));
		return f.format(this.getTime());*/
		return format(pattern, InteraxDate.OUR_BASE_TIME_ZONE_ID);
	}

	/**
	 * @param timeZone
	 * @return a String with the given timeZone in MySQL format "yyyy-MM-dd HH:mm:ss" 
	 */ 
	public String toMySqlStringTimeZone(String timeZone)throws ParseException {
		return format(InteraxDate.MySQLPatternDateTime, timeZone);
	}



//	ready dont expand
	public static List<String> getAvaibleTimeZoneIDs(){

		String tmp [] = TimeZone.getAvailableIDs();

		Arrays.sort(tmp);

		List<String> ret = new ArrayList<String>();
		/* Clean unused IDs for TimeZone, this is for the backward compability of java versions has IDs that isn't for a unique Locale */
		for (int i = 0; i < tmp.length; i++) {
			String b = tmp[i];
			if(b.startsWith("SystemV/"))
				continue;

			if(b.startsWith("Etc/"))
				b = b.replace("Etc/", "");

			if (b.length() > 3 && !ret.contains(b))
				ret.add(b);
		}

		//System.out.println("InteraxDate-getAvaibleTimeZoneIDs: "+tmp.length +" "+ ret.size());

		return ret;
	}


	public static List<TimeZone> getAvaibleTimeZones(){
		String tmp [] = TimeZone.getAvailableIDs();
		Arrays.sort(tmp);

		List<TimeZone> ret = new ArrayList<TimeZone>();
		List<String> retString = new ArrayList<String>();
		/* Clean unused IDs for TimeZone, this is for the backward compability of java versions has IDs that isn't for a unique Locale */
		for (int i = 0; i < tmp.length; i++) {
			String b = tmp[i];
			if(b.startsWith("SystemV/"))
				continue;

			if(b.startsWith("Etc/"))
				b = b.replace("Etc/", "");

			if (b.length() > 3 && !retString.contains(b)){
				retString.add(b);
				ret.add(TimeZone.getTimeZone(b));
			}
		}

		//System.out.println("InteraxDate-getAvaibleTimeZones: "+tmp.length +" "+ ret.size());

		return ret;
	}



	public int getLocalized(int field){
		try
		{
			InteraxDate aux = new InteraxDate(this.format(MySQLPatternDateTime), this.displayTimeZoneId, this.displayTimeZoneId);
			return aux.get(field);
		}
		catch(Exception e)
		{
			return -1;	
		}
	}


	public void setLocalized(int field, int value) {
		try
		{
			InteraxDate aux = (InteraxDate) this.clone();
			int offset = TimeZone.getTimeZone(displayTimeZoneId).getRawOffset();
			offset -= aux.getTimeZone().getRawOffset();
			aux.add(InteraxDate.MILLISECOND, offset);
			aux.set(field, value);
			aux.add(InteraxDate.MILLISECOND, -offset);
			this.setTimeInMillis(aux.getTimeInMillis());
		}
		catch(Exception e)
		{
		}
		//super.set(field, value);
	}
	

	public InteraxDateType getType() {
		return type;
	}

	public boolean isNull()
	{
		switch(this.type){
		case TIME:
			return false;
		default:
			return (this.getTimeInMillis() - InteraxDate.NULL_DATE.getTimeInMillis()) < 1000;
		}
	}

	public static Timestamp getNullTimestamp()
	{
		return new Timestamp(InteraxDate.NULL_DATE.getTimeInMillis() - TimeZone.getDefault().getRawOffset());
	}


	public static String getDayOfWeekLiteral(int i){

		switch(i){
			case InteraxDate.SUNDAY:
				return "SUNDAY";
			case InteraxDate.MONDAY:
				return "MONDAY";
			case InteraxDate.TUESDAY:
				return "TUESDAY";
			case InteraxDate.WEDNESDAY:
				return "WEDNESDAY";
			case InteraxDate.THURSDAY:
				return "THURSDAY";
			case InteraxDate.FRIDAY:
				return "FRIDAY";
			case InteraxDate.SATURDAY:
				return "SATURDAY";
			default : return null;
		}
	}
	
	
	public static String getMonthOfYearLiteral(int i){

		switch(i){
			case InteraxDate.JANUARY:
				return "JANUARY";
			case InteraxDate.FEBRUARY:
				return "FEBRUARY";
			case InteraxDate.MARCH:
				return "MARCH";
			case InteraxDate.APRIL:
				return "APRIL";
			case InteraxDate.MAY:
				return "MAY";
			case InteraxDate.JUNE:
				return "JUNE";
			case InteraxDate.JULY:
				return "JULY";
			case InteraxDate.AUGUST:
				return "AUGUST";
			case InteraxDate.SEPTEMBER:
				return "SEPTEMBER";
			case InteraxDate.OCTOBER:
				return "OCTOBER";
			case InteraxDate.NOVEMBER:
				return "NOVEMBER";
			case InteraxDate.DECEMBER:
				return "DECEMBER";
			default : return null;
		}
	}
	
	
	
	/**
	 * TODO change name for getLocalizedDate() 
	 * */
	public Date getDate() throws Exception{
		
		switch(this.type){
			case TIME: 
				return new SimpleDateFormat("HH:mm:ss").parse(this.format("HH:mm:ss"));
			case DATE: 
				return new SimpleDateFormat("yyyy-MM-dd").parse(this.format("yyyy-MM-dd"));
			case DATETIME: 
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.format("yyyy-MM-dd HH:mm:ss"));
			default: return null;
		}
	}
	
	
	
	public static void testMethods(){

		System.out.println("INI");
		SimpleDateFormat sdf = new SimpleDateFormat(InteraxDate.MySQLPatternDateTime);
		Date myDate = new Date();
//		long longTimeInMillis = myDate.getTime();
//		String incomingTimeZoneId 	= "America/Caracas";
//		String displayTimeZoneId 	= "GMT-4";
		String incomingTimeZoneId 	= "America/Caracas";
		String displayTimeZoneId 	= "America/Caracas";

		String myDatStr = sdf.format(myDate);
		System.out.println(new SimpleDateFormat(InteraxDate.MySQLPatternDateTime+" zzz").format(myDate));
		try{
			/*System.out.println("----Constructors");
			System.out.println("----1");
			Thread.sleep(1000);
			
			System.out.println(new InteraxDate());
			System.out.println("----2");
			
			System.out.println(new InteraxDate(longTimeInMillis));
			System.out.println(new InteraxDate(longTimeInMillis, displayTimeZoneId));
			System.out.println("----3");
			
			System.out.println(new InteraxDate(myDatStr));
			System.out.println(new InteraxDate(myDatStr, displayTimeZoneId));
			System.out.println(new InteraxDate(myDatStr, incomingTimeZoneId, displayTimeZoneId));
			System.out.println("----4");
			
			System.out.println(InteraxDate.getNow());
			System.out.println(InteraxDate.getNow(displayTimeZoneId));
			System.out.println("----");*/
			
			
			System.out.println("----MySQL Methods");
			String otherDisplay = "GMT-3";
			String otherPattern = "yyyy/MM/dd HH.mm.ss zzz";
			
			InteraxDate testDateA = new InteraxDate(myDatStr);
			InteraxDate testDateB = new InteraxDate(myDatStr, displayTimeZoneId);
			InteraxDate testDateC = new InteraxDate(myDatStr, incomingTimeZoneId, displayTimeZoneId);
			
			
			System.out.println("----1");
			System.out.println(testDateA.toMySqlString());
			System.out.println(testDateB.toMySqlString());
			System.out.println(testDateC.toMySqlString());
			System.out.println("----2");
			System.out.println(testDateA.toMySqlString(otherPattern));
			System.out.println(testDateB.toMySqlString(otherPattern));
			System.out.println(testDateC.toMySqlString(otherPattern));
			System.out.println("----3");
			System.out.println(testDateA.toMySqlStringTimeZone(otherDisplay));
			System.out.println(testDateB.toMySqlStringTimeZone(otherDisplay));
			System.out.println(testDateC.toMySqlStringTimeZone(otherDisplay));
			
			System.out.println("----Text Methods");
			System.out.println("----1");
			System.out.println(testDateA.toString(otherDisplay));
			System.out.println(testDateB.toString(otherDisplay));
			System.out.println(testDateC.toString(otherDisplay));
			System.out.println("----2");
			System.out.println(testDateA.format(otherPattern));
			System.out.println(testDateB.format(otherPattern));
			System.out.println(testDateC.format(otherPattern));
			System.out.println("----3");
			System.out.println(testDateA.format(otherPattern, otherDisplay));
			System.out.println(testDateB.format(otherPattern, otherDisplay));
			System.out.println(testDateC.format(otherPattern, otherDisplay));
			
			
			System.out.println("----Parse Methods");
			System.out.println("----1");
			System.out.println(InteraxDate.parse(myDatStr, InteraxDate.MySQLPatternDateTime, incomingTimeZoneId));
			System.out.println(InteraxDate.parse(myDatStr, InteraxDate.MySQLPatternDateTime, otherDisplay));

			System.out.println("----Localized Methods");
			System.out.println("----1");
			System.out.println(testDateA.get(InteraxDate.HOUR_OF_DAY));
			System.out.println(testDateA.getLocalized(InteraxDate.HOUR_OF_DAY));
			System.out.println(testDateB.get(InteraxDate.HOUR_OF_DAY));
			System.out.println(testDateB.getLocalized(InteraxDate.HOUR_OF_DAY));
			System.out.println(testDateC.get(InteraxDate.HOUR_OF_DAY));
			System.out.println(testDateC.getLocalized(InteraxDate.HOUR_OF_DAY));
			
			System.out.println("----2");
			InteraxDate interaxDateA = (InteraxDate)testDateA.clone();
			InteraxDate interaxDateB = (InteraxDate)testDateB.clone();
			InteraxDate interaxDateC = (InteraxDate)testDateC.clone();
			System.out.println(interaxDateA);
			interaxDateA.set(InteraxDate.HOUR_OF_DAY, 10);
			System.out.println(interaxDateA);
			interaxDateA.setLocalized(InteraxDate.HOUR_OF_DAY, 10);
			System.out.println(interaxDateA);
			System.out.println(interaxDateB);
			interaxDateB.set(InteraxDate.HOUR_OF_DAY, 10);
			System.out.println(interaxDateB);
			interaxDateB.setLocalized(InteraxDate.HOUR_OF_DAY, 10);
			System.out.println(interaxDateB);
			System.out.println(interaxDateC);
			interaxDateC.set(InteraxDate.HOUR_OF_DAY, 10);
			System.out.println(interaxDateC);
			interaxDateC.setLocalized(InteraxDate.HOUR_OF_DAY, 10);
			System.out.println(interaxDateC);
		}catch (Exception e) {
			e.printStackTrace();

		}
		System.out.println("FIN");
	}
	

	public static void main(String[] args) {
		try {
//			InteraxDate InteraxDate;
			// Constructor base
			/*System.out.println("---------------- BASE ----------------");
			InteraxDate = new InteraxDate();
			System.out.println(InteraxDate);

			// Constructores basados en milisegundos
			System.out.println("---------------- MILISEGUNDOS ----------------");
			Calendar now = Calendar.getInstance(TimeZone.getTimeZone(ContactManagerDate.SYSTEM_TIME_ZONE_ID));
			long nowMiliseconds = now.getTimeInMillis();
			InteraxDate = new InteraxDate(nowMiliseconds);
			System.out.println(InteraxDate);
			InteraxDate = new InteraxDate(nowMiliseconds, "GMT-4");
			System.out.println(InteraxDate);
			InteraxDate = new InteraxDate(nowMiliseconds, "GMT-5", "GMT-4");
			System.out.println(InteraxDate);

			// Constructores basados en string
			System.out.println("---------------- STRING ----------------");
			String nowString = now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.DATE) + " " +  now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND);
			interaxDate = new InteraxDate(nowString);
			System.out.println(interaxDate);
			interaxDate = new InteraxDate(nowString, "GMT-4");
			System.out.println(interaxDate);
			interaxDate = new InteraxDate(nowString, "GMT-5", "GMT-4");
			System.out.println(interaxDate);

			// Factory
			System.out.println("---------------- FACTORY ----------------");
			InteraxDate = new InteraxDate();
			System.out.println(InteraxDate);
			InteraxDate = InteraxDate.getNow("GMT-4");
			System.out.println(InteraxDate);
			InteraxDate = InteraxDate.getNow("GMT-5", "GMT-4");
			System.out.println(InteraxDate);*/

//			InteraxDate = new InteraxDate("1959-10-30 12:00:01", "GMT+1");
//			System.out.println(InteraxDate);
//			InteraxDate = new InteraxDate("1959-10-30", "GMT+1");
//			System.out.println(InteraxDate);
//			InteraxDate = new InteraxDate("12:00:01", "GMT+1");
//			System.out.println(InteraxDate);

//			InteraxDate = new InteraxDate("195910-30 AA:00:01", "GMT+1");
//			System.out.println(InteraxDate);

			/*InteraxDate now = new InteraxDate("1959-10-30 18:00:01", ContactManagerDate.SYSTEM_TIME_ZONE_ID, "GMT+7");
			System.out.println(now);
			System.out.println(now.get(InteraxDate.DAY_OF_MONTH));
			System.out.println(now.get(InteraxDate.DAY_OF_WEEK));

			System.out.println(now.getLocalized(InteraxDate.DAY_OF_WEEK));
			System.out.println(now.getLocalized(InteraxDate.DAY_OF_MONTH));*/


			/*InteraxDate now = new InteraxDate("1984-12-12 18:00:01", ContactManagerDate.SYSTEM_TIME_ZONE_ID, "GMT+7");
			InteraxDate now2 = new InteraxDate("18:00:01", ContactManagerDate.SYSTEM_TIME_ZONE_ID, "GMT+7");
			InteraxDate now3 = new InteraxDate("1984-12-12", ContactManagerDate.SYSTEM_TIME_ZONE_ID, "GMT+7");
			InteraxDate now4 = new InteraxDate("0000-00-00", ContactManagerDate.SYSTEM_TIME_ZONE_ID, "GMT+7");
			System.out.println(now.toMySqlString());
			System.out.println(now.isNull());
			System.out.println(now2.isNull());
			System.out.println(now3.isNull());
			System.out.println(now4.isNull());*/
			
			
			/*InteraxDate apply = new InteraxDate("2008-06-18 17:19:00", "America/Caracas", ContactManagerDate.SYSTEM_TIME_ZONE_ID);
			//InteraxDate = new InteraxDate("2008-06-18 18:00:01", ContactManagerDate.SYSTEM_TIME_ZONE_ID, ContactManagerDate.SYSTEM_TIME_ZONE_ID);
			InteraxDate = InteraxDate.getNow(ContactManagerDate.SYSTEM_TIME_ZONE_ID,ContactManagerDate.SYSTEM_TIME_ZONE_ID);
			
			System.out.println(apply.toMySqlString());
			System.out.println(InteraxDate.toMySqlString());
			
			if(apply.before(InteraxDate))
				System.out.println("Aplly es menor");
			else
				System.out.println("Aplly es mayor");
				
			if(apply.getTimeInMillis() < InteraxDate.getTimeInMillis())
				System.out.println("Aplly es menor");
			else
				System.out.println("Aplly es mayor");
			 */
		
			/*
			InteraxDate fromHour = new InteraxDate("00:00:00", ContactManagerDate.SYSTEM_TIME_ZONE_ID, ContactManagerDate.SYSTEM_TIME_ZONE_ID);
			InteraxDate toHour = new InteraxDate("23:59:59", ContactManagerDate.SYSTEM_TIME_ZONE_ID, ContactManagerDate.SYSTEM_TIME_ZONE_ID);
			
			InteraxDate aHour = new InteraxDate("00:00:01", "America/Caracas", ContactManagerDate.SYSTEM_TIME_ZONE_ID);
			InteraxDate bHour = new InteraxDate("23:59:58", "America/Caracas", ContactManagerDate.SYSTEM_TIME_ZONE_ID);
			
			System.out.println(fromHour.getTimeInMillis()/1000);
			System.out.println(toHour.getTimeInMillis()/1000);
			System.out.println("******");
			System.out.println(aHour.getTimeInMillis()/1000);
			System.out.println(bHour.getTimeInMillis()/1000);
			
			// Ignoring millisenconds 
			long fh = fromHour.getTimeInMillis()/1000;
			long th = toHour.getTimeInMillis()/1000;
			
			long ah = aHour.getTimeInMillis()/1000;
			long bh = bHour.getTimeInMillis()/1000;
			
			if((fh <= ah && ah <= th ) || (fh <= bh && bh <= th ))
				System.out.println("Esta contenido");
			else
				System.out.println("No esta contenido");
			*/
			
		/*	String tz = "America/Caracas";
			
			InteraxDate fromHour = new InteraxDate("00:00:00", tz, tz);
			InteraxDate toHour = new InteraxDate("23:59:59", tz, tz);
			InteraxDate aHour = InteraxDate.parse("2009-01-01 00:00:00", MySQLPatternDateTime, tz);
			InteraxDate bHour = InteraxDate.parse("2009-01-01 23:59:59", MySQLPatternDateTime, tz);
			//new InteraxDate("23:59:58", tz, ContactManagerDate.SYSTEM_TIME_ZONE_ID);
			
			System.out.println(" *** ");
			System.out.println(fromHour.toMySqlString()+"\t# "+fromHour.format("HH:mm:ss", fromHour.displayTimeZoneId));
			System.out.println(toHour.toMySqlString()+"\t# "+toHour.format("HH:mm:ss", toHour.displayTimeZoneId));
			System.out.println(aHour.toMySqlString()+"\t# "+aHour.format("HH:mm:ss", aHour.displayTimeZoneId));
			System.out.println(bHour.toMySqlString()+"\t# "+bHour.format("HH:mm:ss", bHour.displayTimeZoneId));
			
			InteraxDate c = new InteraxDate (aHour.toMySqlString(), "GMT0", tz);
			InteraxDate d = new InteraxDate (bHour.toMySqlString(), "GMT0", tz);
			System.out.println(" *** ");
			System.out.println(c.toMySqlString()+"\t# "+c.format("HH:mm:ss", c.displayTimeZoneId));
			System.out.println(d.toMySqlString()+"\t# "+d.format("HH:mm:ss", d.displayTimeZoneId));
			
			
			System.out.println(new GregorianCalendar());
			System.out.println(new GregorianCalendar(TimeZone.getTimeZone("America/Caracas")));*/
			
			
			InteraxDate.testMethods();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
