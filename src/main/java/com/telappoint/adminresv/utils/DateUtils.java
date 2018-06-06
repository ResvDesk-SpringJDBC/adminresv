package com.telappoint.adminresv.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * @author Murali
 * 
 */
public class DateUtils {

	public static String MMDDYYYY_DATE_FORMAT = "MM/dd/yyyy";
	public static String MMDDYYYYHHMMSS_TWELWE_HOURS = "MM/dd/yyyy hh:mm a";
	public static String FULLTEXTUAL_DAY_FORMAT = "EEEEE, MMM d, yyyy";
	
	private static final Logger logger = Logger.getLogger(DateUtils.class);

	private static Map<String, ThreadLocal<DateFormat>> tlDateFormatMap = new HashMap<String, ThreadLocal<DateFormat>>();
	
	public static ThreadLocal<DateFormat> getSimpleDateFormat(String dateTimeFormatStr) {
		ThreadLocal<DateFormat> tldf = null;
		try {
			if (tlDateFormatMap.containsKey(dateTimeFormatStr)) {
				tldf = tlDateFormatMap.get(dateTimeFormatStr);
				return tldf;
			}
			tldf = getThreadLocal(dateTimeFormatStr);
			tlDateFormatMap.put(dateTimeFormatStr, tldf);
			return tldf;
		} catch (Exception e) {
			logger.error("Error:" + e, e);
		}
		return tldf;
	}

	public static ThreadLocal<DateFormat> getThreadLocal(final String dateTimeForamtStr) {
		final ThreadLocal<DateFormat> tldf_ = new ThreadLocal<DateFormat>() {
			@Override
			protected DateFormat initialValue() {
				return new SimpleDateFormat(dateTimeForamtStr);
			}
		};
		return tldf_;
	}
	
	public static String getDateString(String dateFormat, int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		ThreadLocal<DateFormat> threadLocal = getSimpleDateFormat(dateFormat);
		return threadLocal.get().format(cal.getTime());
	}
	
	public static Date addDays(Date date, int days){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	public static String getStringFromDate(Date date, String formate) {
		String dateTimeString = "";
		if (date != null) {
			try {
				ThreadLocal<DateFormat> dateFormat = getSimpleDateFormat(formate);
				dateTimeString = dateFormat.get().format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dateTimeString;
	}
	
	public static String getStringFromDateString(String dateString, String dateFormate,String requiredFormate) {
		if (dateString != null) {
			try {
				ThreadLocal<DateFormat> dateFormat = getSimpleDateFormat(dateFormate);
				Date date = dateFormat.get().parse(dateString);
				ThreadLocal<DateFormat> dateStrFormat = getSimpleDateFormat(requiredFormate);
				dateString = dateStrFormat.get().format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dateString;
	}
	
	//Time zone specific methods
	
	public static Date getTargetDateFromUTC(Date utcDate, String targetTimezoneId) {
		return fromUTC(utcDate, targetTimezoneId);
	}

	public static Date fromUTC(Date date, String targetTimezoneId){

		TimeZone targettz = TimeZone.getTimeZone(targetTimezoneId);
		Date ret = new Date( date.getTime() + targettz.getRawOffset() );

		// if we are now in DST, back off by the delta.  Note that we are checking the GMT date, this is the KEY.
		if ( targettz.inDaylightTime( ret )){
			Date dstDate = new Date(ret.getTime() + targettz.getDSTSavings());
			// check to make sure we have not crossed back into standard time
			// this happens when we are on the cusp of DST (7pm the day before the change for PDT)
			if ( targettz.inDaylightTime( dstDate )){
				ret = dstDate;
			}
		}
		return ret;
	}

	public static Date getDateFromString(String dateString,String format){
		Date date =  null;
		if(dateString!=null){
			try{	
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				date = sdf.parse(dateString);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return date;
	}
}
