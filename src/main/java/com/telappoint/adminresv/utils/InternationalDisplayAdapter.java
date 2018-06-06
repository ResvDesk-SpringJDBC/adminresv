package com.telappoint.adminresv.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


public class InternationalDisplayAdapter {

	String clientCode;
	String timeZone;
	String countryCode;
	String dateFormat;
	String phoneFormat;
	String timeFormat;
	String fullDateFormat;
	String dateYYYYFormat;
	String popupCalendarDateFormat;
	String timeFormatWithSeconds;
	
	public InternationalDisplayAdapter(String clientCode,String timeZone, String countryCode, String phoneFormat, String dateFormat,String dateYYYYFormat,String fullDateFormat,String popupCalendarDateFormat, String timeFormat, String timeFormatWithSeconds){
		super();
		this.clientCode = clientCode;
		this.timeZone = timeZone;
		this.countryCode = countryCode;
		
		// set date , date time and phone formats
		this.phoneFormat = phoneFormat;
		this.dateFormat = dateFormat;
		this.timeFormat = timeFormat;
		this.dateYYYYFormat = dateYYYYFormat;
		this.fullDateFormat = fullDateFormat;
		this.popupCalendarDateFormat = popupCalendarDateFormat;
		
		this.timeFormatWithSeconds = timeFormatWithSeconds;
		
		
	}


	
	/*
	Typical format 	
					(01xxx) xxx xxx
					(01x1) xxx xxxx
					(011x) xxx xxxx
					(02x) xxxx xxxx
					03xx xxx xxxx
					07xxx xxx xxx
					08xx xxx xxxx
					09xx xxx xxxx
	*/
	private String getUKPhoneNumber(String str){
		if(str.startsWith("0")) str = str.substring(1);//remove trunk 0

		if(str.length() < 9) return new String (" ");
		String digit1 = str.substring(0, 1);
		String digit2 = str.substring(1, 2);
		String digit3 = str.substring(2, 3);

		String areacode = " ";
		String prefix = " ";
		String last = " ";

		if(digit1.equals("1")) {
			if(digit2.equals("1")) {
				areacode = str.substring(0, 3);
				prefix = str.substring(3, 6);
				last = str.substring(6);
			}
			else if(digit3.equals("1")) {
				areacode = str.substring(0, 3);
				prefix = str.substring(3, 6);
				last = str.substring(6);
			}
			else {
				areacode = str.substring(0, 4);
				prefix = str.substring(4, 7);
				last = str.substring(7);
			}
		}
		else if(digit1.equals("2")) {
			areacode = str.substring(0, 2);
			prefix = str.substring(2, 6);
			last = str.substring(6);
		}
		else if(digit1.equals("3")) {
			areacode = str.substring(0, 3);
			prefix = str.substring(3, 6);
			last = str.substring(6);
		}
		else if(digit1.equals("7")) {
			areacode = str.substring(0, 4);
			prefix = str.substring(4, 7);
			last = str.substring(7);
		}
		else if(digit1.equals("8")) {
			areacode = str.substring(0, 3);
			prefix = str.substring(3, 6);
			last = str.substring(6);
		}
		else if(digit1.equals("9")) {
			areacode = str.substring(0, 3);
			prefix = str.substring(3, 6);
			last = str.substring(6);
		}
		return new String(areacode + " " + prefix + " " + last);
	}
	
	
	public String getPhoneNumber(String str) {
		
		if(this.phoneFormat.equalsIgnoreCase("UK")){
			return getUKPhoneNumber(str);
		}
		else {
			return formatPhoneNumber(str);
		}
	}

	public String getUSDateYYYY(String dateStr){
		String sourceFormat = this.dateFormat;
		String destFormat = "MM/dd/yyyy";
		String retStr = "";
		
		//convert from source to destination
		SimpleDateFormat sdfSource = new SimpleDateFormat(sourceFormat);
		//parse the string into Date object
		try{
			Date date = sdfSource.parse(dateStr);
			//create SimpleDateFormat object with desired date format
			SimpleDateFormat sdfDestination = new SimpleDateFormat(destFormat);
			retStr = sdfDestination.format(date);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//parse the date into another format
		return retStr;
	}
	
	public String getUSDateYYYYFromPopupCalendarDate(String dateStr){
		String sourceFormat = this.popupCalendarDateFormat;
		String destFormat = "MM/dd/yyyy";
		String retStr = "";
		
		if(isValidDate(dateStr,this.popupCalendarDateFormat)){
			//convert from source to destination
			SimpleDateFormat sdfSource = new SimpleDateFormat(sourceFormat);
			//parse the string into Date object
			try{
				Date date = sdfSource.parse(dateStr);
				//create SimpleDateFormat object with desired date format
				SimpleDateFormat sdfDestination = new SimpleDateFormat(destFormat);
				retStr = sdfDestination.format(date);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		} else {
			retStr = dateStr;
		}

		//parse the date into another format
		return retStr;
	}
	
	public String getUSDateYY(String dateStr){
		String sourceFormat = this.dateFormat;
		String destFormat = "MM/dd/yy";
		String retStr = "";
		
		//convert from source to destination
		SimpleDateFormat sdfSource = new SimpleDateFormat(sourceFormat);
		//parse the string into Date object
		try{
			Date date = sdfSource.parse(dateStr);
			//create SimpleDateFormat object with desired date format
			SimpleDateFormat sdfDestination = new SimpleDateFormat(destFormat);
			retStr = sdfDestination.format(date);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//parse the date into another format
		return retStr;
	}
	
	public String getDate(String dateStr, String format){
		String sourceFormat = format;
		String destFormat = this.dateFormat;
		String retStr = "";
		
		//convert from source to destination
		SimpleDateFormat sdfSource = new SimpleDateFormat(sourceFormat);
		//parse the string into Date object
		try{
			Date date = sdfSource.parse(dateStr);
			//create SimpleDateFormat object with desired date format
			SimpleDateFormat sdfDestination = new SimpleDateFormat(destFormat);
			retStr = sdfDestination.format(date);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//parse the date into another format
		return retStr;
	}
	
	
	public String getPopupCalendarDate(String dateStr, String format){
		String sourceFormat = format;
		String destFormat = this.popupCalendarDateFormat;
		String retStr = "";
		
		//convert from source to destination
		SimpleDateFormat sdfSource = new SimpleDateFormat(sourceFormat);
		//parse the string into Date object
		try{
			Date date = sdfSource.parse(dateStr);
			//create SimpleDateFormat object with desired date format
			SimpleDateFormat sdfDestination = new SimpleDateFormat(destFormat);
			retStr = sdfDestination.format(date);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//parse the date into another format
		return retStr;
	}
	
	
	
	public String getDateYYYY(String dateStr, String format){
		String sourceFormat = format;
		String destFormat = this.dateYYYYFormat;

		String retStr = "";
		
		//convert from source to destination
		SimpleDateFormat sdfSource = new SimpleDateFormat(sourceFormat);
		//parse the string into Date object
		try{
			Date date = sdfSource.parse(dateStr);
			//create SimpleDateFormat object with desired date format
			SimpleDateFormat sdfDestination = new SimpleDateFormat(destFormat);
			retStr = sdfDestination.format(date);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//parse the date into another format
		return retStr;
	}
	

	public String getDateTime(String datetime, String format){
		String sourceFormat = format;
		String destFormat = this.dateFormat + " " + this.timeFormat;
		String retStr = "";
		
		//convert from source to destination
		SimpleDateFormat sdfSource = new SimpleDateFormat(sourceFormat);
		//parse the string into Date object
		try{
			Date date = sdfSource.parse(datetime);
			//create SimpleDateFormat object with desired date format
			SimpleDateFormat sdfDestination = new SimpleDateFormat(destFormat);
			retStr = sdfDestination.format(date);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//parse the date into another format
		return retStr;
	}
	
	public String getDateTimeWithSeconds(String datetime, String format){
		String sourceFormat = format;
		String destFormat = this.dateFormat + " " + this.timeFormatWithSeconds;
		String retStr = "";
		
		//convert from source to destination
		SimpleDateFormat sdfSource = new SimpleDateFormat(sourceFormat);
		//parse the string into Date object
		try{
			Date date = sdfSource.parse(datetime);
			//create SimpleDateFormat object with desired date format
			SimpleDateFormat sdfDestination = new SimpleDateFormat(destFormat);
			retStr = sdfDestination.format(date);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//parse the date into another format
		return retStr;
	}
	
	
	public String getFullDate(String datetime, String format){
		String sourceFormat = format;
		String destFormat = this.fullDateFormat;
		String retStr = "";
		
		//convert from source to destination
		SimpleDateFormat sdfSource = new SimpleDateFormat(sourceFormat);
		//parse the string into Date object
		try{
			Date date = sdfSource.parse(datetime);
			//create SimpleDateFormat object with desired date format
			SimpleDateFormat sdfDestination = new SimpleDateFormat(destFormat);
			retStr = sdfDestination.format(date);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//parse the date into another format
		return retStr;
	}



	public String getClientCode() {
		return clientCode;
	}



	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}



	public String getTimeZone() {
		return timeZone;
	}



	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}



	public String getCountryCode() {
		return countryCode;
	}



	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}



	public String getDateFormat() {
		return dateFormat;
	}



	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}



	public String getPhoneFormat() {
		return phoneFormat;
	}



	public void setPhoneFormat(String phoneFormat) {
		this.phoneFormat = phoneFormat;
	}



	public String getTimeFormat() {
		return timeFormat;
	}



	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}



	public String getFullDateFormat() {
		return fullDateFormat;
	}



	public void setFullDateFormat(String fullDateFormat) {
		this.fullDateFormat = fullDateFormat;
	}



	public String getDateYYYYFormat() {
		return dateYYYYFormat;
	}



	public void setDateYYYYFormat(String dateYYYYFormat) {
		this.dateYYYYFormat = dateYYYYFormat;
	}



	public String getPopupCalendarDateFormat() {
		return popupCalendarDateFormat;
	}



	public void setPopupCalendarDateFormat(String popupCalendarDateFormat) {
		this.popupCalendarDateFormat = popupCalendarDateFormat;
	}



	/**
	 * Format gc date to mmddyyyy.
	 * 
	 * @param dateGC the date gc
	 * 
	 * @return the string
	 */
	public String formatGCDateToPopupCalendarFormat(GregorianCalendar dateGC){
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat(this.popupCalendarDateFormat);
		formatter.setCalendar(dateGC);
		try{
			dateString = formatter.format(dateGC.getTime());
		} catch(Exception e){
			e.printStackTrace();
		}
	
		return dateString;
	}



	/**
		 * This method checks to see if the supplied dateValue format matches the supplied
		 * dateFormat.
		 * 
		 * @param dateValue the date value
		 * @param dateFormat the date format
		 * 
		 * @return true, if checks if is valid date
		 */
		public static boolean isValidDate(String dateValue, String dateFormat) {
			GregorianCalendar gcDate = new GregorianCalendar();
			SimpleDateFormat sim = new SimpleDateFormat(dateFormat);
			sim.setCalendar(gcDate);
			try{
				sim.setLenient(false);
				sim.parse(dateValue);
			}catch(Exception e){
				System.out.println("Exception :"+ e.toString());
	//			e.printStackTrace();
				return false;
			}  
	
			return true;
		}



	//only numeric characters or empty string
	//no leading or trailing spaces
	//no alpha, space, special characters, minus, plus, null are allowed.
	/**
	 * Checks if is numeric.
	 * 
	 * @param instr the instr
	 * 
	 * @return true, if is numeric
	 */
	public static boolean isNumeric(String instr){
		if(instr == null) return false;
		try{
			char x[] = instr.toCharArray();
			for(int i=0; i<x.length; i++){
				if(!Character.isDigit(x[i])) return false;
			}
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}	
		return true;
	}



	/*
	Typical format 	
					(01xxx) xxx xxx
					(01x1) xxx xxxx
					(011x) xxx xxxx
					(02x) xxxx xxxx
					03xx xxx xxxx
					07xxx xxx xxx
					08xx xxx xxxx
					09xx xxx xxxx
	*/
	public static String getPhoneNumberUk(String str) {
		
		if(str.startsWith("0")) str = str.substring(1);//remove trunk 0
		
		if(str.length() < 9) return new String (" ");
		String digit1 = str.substring(0, 1);
		String digit2 = str.substring(1, 2);
		String digit3 = str.substring(2, 3);
		
		String areacode = " ";
		String prefix = " ";
		String last = " ";
		
		if(digit1.equals("1")) {
			if(digit2.equals("1")) {
				areacode = str.substring(0, 3);
				prefix = str.substring(3, 6);
				last = str.substring(6);
			}
			else if(digit3.equals("1")) {
				areacode = str.substring(0, 3);
				prefix = str.substring(3, 6);
				last = str.substring(6);
			}
			else {
				areacode = str.substring(0, 4);
				prefix = str.substring(4, 7);
				last = str.substring(7);
			}
		}
		else if(digit1.equals("2")) {
			areacode = str.substring(0, 2);
			prefix = str.substring(2, 6);
			last = str.substring(6);
		}
		else if(digit1.equals("3")) {
			areacode = str.substring(0, 3);
			prefix = str.substring(3, 6);
			last = str.substring(6);
		}
		else if(digit1.equals("7")) {
			areacode = str.substring(0, 4);
			prefix = str.substring(4, 7);
			last = str.substring(7);
		}
		else if(digit1.equals("8")) {
			areacode = str.substring(0, 3);
			prefix = str.substring(3, 6);
			last = str.substring(6);
		}
		else if(digit1.equals("9")) {
			areacode = str.substring(0, 3);
			prefix = str.substring(3, 6);
			last = str.substring(6);
		}
		return new String(areacode + " " + prefix + " " + last);
	}



	public static boolean isValidPhoneUK(String str) {
		if(str != null && str.startsWith("0")) str = str.substring(1);//remove trunk 0
	    if(str == null || !(str.length() == 10 || str.length() == 9)) return false;
	    if(!isNumeric(str)) return false;
	    return true;
	}



	public static String getDateDisplayUk(String date) {
		String yy = date.substring(2,4);
		String mm = date.substring(5,7);
		String dd = date.substring(8,10);
		String display = dd + "/" + mm + "/" + yy;
		return display;
	}



	/**
	 * Gets the date time display for UK region.
	 * 
	 * @param date the date
	 * 
	 * @return the date time display
	 */
	public static String getDateTimeDisplayUk(String date) {
		String yy = date.substring(2,4);
		String mm = date.substring(5,7);
		String dd = date.substring(8,10);
		String hh = date.substring(11,13);
		String min = date.substring(14,16);
		int hour = Integer.parseInt(hh);
		String ampm = "AM";
		if(hour >= 12) ampm = "PM";
		if(hour > 12) hour = hour - 12;
		String display = dd + "/" + mm + "/" + yy + "   " + hour + ":" + min + " " + ampm;
		return display;
	}
	
	/**
	 * Format phone number.
	 * 
	 * @param phone the phone
	 * 
	 * @return the string
	 */
	private static String formatPhoneNumber(String ph) {
		StringBuffer buf = new StringBuffer("");

        if(ph == null ){
        	buf.append("NA.");
        }
        else if(ph.length() == 7) {
            buf.append(ph.substring(0, 3));
            buf.append("-");
            buf.append(ph.substring(3, 7));
            ph = ph.substring(7);
        }
        else if(ph.length() == 10) {
            buf.append(ph.substring(0, 3));
            buf.append("-");
            buf.append(ph.substring(3, 6));
            buf.append("-");
            buf.append(ph.substring(6, 10));
        }
        else if(ph.length() > 10) {
            buf.append(ph.substring(0, 3));
            buf.append("-");
            buf.append(ph.substring(3, 6));
            buf.append("-");
            buf.append(ph.substring(6, 10));
            buf.append("Ext");
            buf.append(ph.substring(10));
        }

		return buf.toString();
	}    


}
