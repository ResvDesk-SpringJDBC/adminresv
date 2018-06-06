package com.telappoint.adminresv.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.telappoint.adminresv.constants.AdminResvConstants;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.restws.model.BaseResponse;
import com.telappoint.adminresv.restws.model.Location;

/**
 * The Class Utils.
 * 
 * @author anantha lakshmanan
 * 
 *  To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AdminUtils implements AdminResvConstants{
	
	private static Log log = LogFactory.getLog(AdminUtils.class);
	public static SimpleDateFormat tltimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getWeekDay(int d) {		
		String day = null;
		switch (d) {
		case Calendar.SUNDAY:
			day = "Sun";
			break;
		case Calendar.MONDAY:
			day = "Mon";
			break;
		case Calendar.TUESDAY:
			day = "Tue";
			break;                
		case Calendar.WEDNESDAY:
			day = "Wed";
			break;                
		case Calendar.THURSDAY:
			day = "Thu";
			break;                
		case Calendar.FRIDAY:
			day = "Fri";
			break;   
		case Calendar.SATURDAY:
			day = "Sat";
			break;     
		}       
		return day;
	}    

	public static String getMonth(GregorianCalendar gc){
		String month = null;

		switch(gc.get(Calendar.MONTH)){
		case Calendar.JANUARY:
			month = "January";
			break;
		case Calendar.FEBRUARY:
			month = "February";
			break;
		case Calendar.MARCH:
			month = "March";
			break;
		case Calendar.APRIL:
			month = "April";
			break;
		case Calendar.MAY:
			month = "May";
			break;
		case Calendar.JUNE:
			month = "June";
			break;
		case Calendar.JULY:
			month = "July";
			break;
		case Calendar.AUGUST:
			month = "August";
			break;
		case Calendar.SEPTEMBER:
			month = "September";
			break;
		case Calendar.OCTOBER:
			month = "October";
			break;
		case Calendar.NOVEMBER:
			month = "November";
			break;
		case Calendar.DECEMBER:
			month = "December";
			break;

		}
		return month;
	}

	public static GregorianCalendar getFirstSunday(String dateStr, String timeZone) {
	
		GregorianCalendar c = new GregorianCalendar(TimeZone.getTimeZone(timeZone)); 
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		// set timezone
		sdf.setCalendar(c);
		try{
			c.setTime(sdf.parse(dateStr));
		} catch(Exception e){}
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
	
		int decrement = 0;
		switch (c.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY:
			decrement = 0;
			break;
		case Calendar.MONDAY:
			decrement = 1;
			break;
		case Calendar.TUESDAY:
			decrement = 2;
			break;                
		case Calendar.WEDNESDAY:
			decrement = 3;
			break;                
		case Calendar.THURSDAY:
			decrement = 4;
			break;                
		case Calendar.FRIDAY:
			decrement = 5;
			break;   
		case Calendar.SATURDAY:
			decrement = 6;
			break;                    
		}        
		c.set(Calendar.DATE, c.get(Calendar.DATE) - decrement);
		return c;
	}
	
	
	public static String getOrdinalFor(int value) {
		 int hundredRemainder = value % 100;
		 if(hundredRemainder >= 10 && hundredRemainder <= 20) {
		  return "th";
		 }
		 int tenRemainder = value % 10;
		 switch (tenRemainder) {
		  case 1:
		   return "st";
		  case 2:
		   return "nd";
		  case 3:
		   return "rd";
		  default:
		   return "th";
		 }
	}

	public static boolean isAlphaNumericHypenPeriod(String str) {
	    if(str == null) return false;
	    try{
	        char x[] = str.toCharArray();
	        for(int i=0; i<x.length; i++){
	             if(!(Character.isLetterOrDigit(x[i]) || x[i] == '-' || x[i] == '.' )) return false;
	        }
	    } catch(Exception e){
	        return false;
	    }	
	    return true;        
	}

	public static boolean isAlphaNumeric(String str) {
		if(str == null) return false;
		try{
			char x[] = str.toCharArray();
			for(int i=0; i<x.length; i++){
				if(!(Character.isLetterOrDigit(x[i]))) return false;
			}
		} catch(Exception e){
			log.error("Exception :"+ e.toString());
			e.printStackTrace();
			return false;
		}	
		return true;        
	}
	
	
	public static boolean isAlphaNumericHypenSpaceApostrophePeriodCommaHashSlashBracketEtc(String str) {
		if(str == null) return false;
		try{
			char x[] = str.toCharArray();
			for(int i=0; i<x.length; i++){
				if(!(Character.isLetterOrDigit(x[i]) || Character.isSpaceChar(x[i]) || x[i] == '-' || x[i] == '\'' ||
						x[i] == '.' || x[i] == ',' || x[i] == '#' || x[i] == '/' || x[i] == '[' || x[i] == ']' || 
						x[i] == '\\' || x[i] == '>'|| x[i] == '!' )) return false;
			}
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}	
		return true;        
	}


	public static int getDiffInDays(GregorianCalendar startGC, GregorianCalendar endGC,String timeZone){
		int dateDiff = 0;
		long ldate1 = startGC.getTimeInMillis();
		long ldate2 = endGC.getTimeInMillis();
		// Use integer calculation, truncate the decimals
		int hr1   = (int)(ldate1/3600000); //60*60*1000
		int hr2   = (int)(ldate2/3600000);
		int days1 = (int)hr1/24;
		int days2 = (int)hr2/24;
		if(days2 > days1){
			dateDiff  = days2 - days1;
		} else {
			dateDiff  = days1 - days2;
		}
		return dateDiff;
	}
	
	public static Map<String,String> getStatesMap(){
		Map<String,String> states = new LinkedHashMap<String,String>();
		states.put("AL", "Alabama");
		states.put("AK", "Alaska");		
		states.put("AZ", "Arizona");
		states.put("AR", "Arkansas");
		states.put("CA", "California");
		states.put("CO", "Colorado");
		states.put("CT", "Connecticut");
		states.put("DE", "Delaware");
		states.put("FL", "Florida");
		states.put("GA", "Georgia");
		states.put("HI", "Hawaii");
		states.put("ID", "Idaho");
		states.put("IL", "Illinois");
		states.put("IN", "Indiana");
		states.put("IA", "Iowa");
		states.put("KS", "Kansas");
		states.put("KY", "Kentucky");
		states.put("LA", "Louisiana");
		states.put("ME", "Maine");
		states.put("MD", "Maryland");
		states.put("MA", "Massachusetts");
		states.put("MI", "Michigan");
		states.put("MN", "Minnesota");
		states.put("MS", "Mississippi");
		states.put("MO", "Missouri");
		states.put("MT", "Montana");
		states.put("NE", "Nebraska");
		states.put("NV", "Nevada");
		states.put("NH", "New Hampshire");
		states.put("NJ", "New Jersey");
		states.put("NM", "New Mexico");
		states.put("NY", "New York");
		states.put("NC", "North Carolina");
		states.put("ND", "North Dakota");
		states.put("OH", "Ohio");
		states.put("OK", "Oklahoma");
		states.put("OR", "Oregon");
		states.put("PA", "Pennsylvania");		
		states.put("RI", "Rhode Island");
		states.put("SC", "South Carolina");
		states.put("SD", "South Dakota");
		states.put("TN", "Tennessee");
		states.put("TX", "Texas");
		states.put("UT", "Utah");
		states.put("VT", "Vermont");
		states.put("VA", "Virginia");
		states.put("WA", "Washington");
		states.put("WV", "West Virginia");
		states.put("WI", "Wisconsin");
		states.put("WY", "Wyoming");
		
		return states;
	}
	
		
	public static Object getPropertyValue(Object object,String fieldName) throws NoSuchFieldException {
		try {
			BeanInfo info = Introspector.getBeanInfo(object.getClass());
			for (PropertyDescriptor pd : info.getPropertyDescriptors())
				if (pd.getName().equals(fieldName)) {
					Method getter = pd.getReadMethod();
					if(getter != null) {
						getter.setAccessible(true);
						return getter.invoke(object, null);
					}
					
				}
		} catch (Exception e) {
			throw new NoSuchFieldException(object.getClass() + " has no field " + fieldName);
		}
		return "";
	}

	public static void setPropertyValue(Object object, String propertyName, Object propertyValue) throws Exception {
		try {
			BeanInfo bi = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor pds[] = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				if (pd.getName().equals(propertyName)) {
					Method setter = pd.getWriteMethod();
					if (setter != null) {
						setter.invoke(object, new Object[] { propertyValue });
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
		
	public static String removeErrorNumber(String stMethodName) {
		int lastIndex = stMethodName.lastIndexOf(":");
		return stMethodName.substring(0,lastIndex);
	}
	
	public static String getMethodName(final int depth) { 
	  StackTraceElement stackTraceElements[] = (new Throwable()).getStackTrace();
      return stackTraceElements[depth].toString();
	}
	
	public static String getJSONString(Object object) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String response = gson.toJson(object);
		return "<br/>InputParameters: ["+response+"]";
	}
	
	public static void sendErrorEmail(StringBuilder body,Exception e,String subject) {
		body.append("<br/><br/>");
		body.append("Exception:");
		body.append("<br/>");
		body.append(AdminUtils.getStackTrace(e));	
		try {
	        PropertyUtils.sendErrorEmail(subject,body.toString());
		} catch(Exception ex) {
			log.error("Error: Unable to send application error email!"+ex,ex);
			e.printStackTrace();
		}
	}
	
	public static String getStackTrace(Exception e){
		final StringBuilder result = new StringBuilder();
		for (StackTraceElement element : e.getStackTrace() ){
	      result.append(element);
	      result.append("</br>");
	    }
	    return result.toString();
	}
	
	public static String convertToCommaDelimited(String[] list) {
        StringBuffer ret = new StringBuffer("");
        for (int i = 0; list != null && i < list.length; i++) {
            ret.append(list[i]);
            if (i < list.length - 1) {
                ret.append(',');
            }
        }
        return ret.toString();
    }
	
	public static String getClientCode(HomeBean homeBean) {
       if(homeBean!=null && homeBean.getBaseReq()!=null){
    	   return homeBean.getBaseReq().getClientCode();
       }else{
    	  return "NULL";
       }
    }
	public static String getTransId(HomeBean homeBean) {
       if(homeBean!=null && homeBean.getBaseReq()!=null){
    	   return homeBean.getBaseReq().getTransId();
       }else{
    	  return "NULL";
       }
    }
	
	public static String getBaseResponseJSONStringForFailureOperation() {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setErrorStatus(true);
		baseResponse.setResponseStatus(true);
		baseResponse.setErrorMessage("Error while performing this operation!");
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(baseResponse);
	}
	
	public static String getTimeZone(List<Location> locations,String locationId){
		String timeZone = "";
		int locationIdInt = Integer.parseInt(locationId);
		
		if(locations!=null && locations.size()>0){
			for(Location location : locations){
				if(locationIdInt==location.getLocationId()){
					timeZone = location.getTimeZone();
					break;
				}
			}
		}
		if(timeZone==null || "".equals(timeZone)){
			timeZone = "US/Eastern"; //considering as default time zone
		}
		return timeZone;
	}
	
	public static String getSelectedValues(String[] columns) {
		String selectedColumns = "";
		if (columns != null) {
			for (String columnName : columns) {
				selectedColumns = selectedColumns + columnName + ",";
			}
			if (selectedColumns != null && selectedColumns.endsWith(",")) {
				selectedColumns = selectedColumns.substring(0, selectedColumns.length() - 1);
			}
		}
		return selectedColumns;
	}
	
	public static String getSelectedOtherResvStatusAsString(List<Integer> otherResvStatusList) {
		String otherResvStatuses = new String();
		if(otherResvStatusList!=null){
			for (Integer otherStatus : otherResvStatusList) {
				otherResvStatuses = otherResvStatuses + String.valueOf(otherStatus) + ",";
			}
			if (otherResvStatuses != null && otherResvStatuses.endsWith(",")) {
				otherResvStatuses = otherResvStatuses.substring(0, otherResvStatuses.length() - 1);
			}
		}
		return otherResvStatuses;
	}
	
	public static boolean isValid(String validateStr){
		if(validateStr!=null && !"".equals(validateStr) && !"NA.".equals(validateStr)){
			return true;
		}else{
			return false;
		}
	}
	
	public static String formatPhoneNumber(String ph) {
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
	
	public static <T> String concatWithSeparator(String[] array,String separator) {
		StringBuilder idStr = new StringBuilder();
		boolean initial = true;
		if(null!=array && array.length > 0){
			for (String t: array) {
				if (!initial) {
					idStr.append(separator);
					idStr.append(t);
				} else {
					initial = false;
					idStr.append(t);
				}
			}
		}
		return idStr.toString();
	}
	
	//To get the phone/dob fields data
	public static String getSplitedFieldFormData(Object formData,String fieldName,int splitFieldCount){		
		try {
			String fieldData = (String) getPropertyValue(formData, fieldName);
			if(fieldData!=null && fieldData.length()>0){
				return fieldData;
			}else{
				StringBuilder concatinatedFieldData = new StringBuilder();
				for(int i=1;i<=splitFieldCount;i++){
					String splitedFieldData = (String) getPropertyValue(formData, fieldName+i);
					if(splitedFieldData!=null && splitedFieldData.length()>0){
						concatinatedFieldData.append(splitedFieldData);
					}
				}
				return concatinatedFieldData.toString();
			}
		}catch(Exception e){
			return "";
		}
	}
}
