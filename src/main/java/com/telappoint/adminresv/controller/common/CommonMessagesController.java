package com.telappoint.adminresv.controller.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.telappoint.adminresv.constants.AdminResvConstants;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.DateUtils;

public class CommonMessagesController implements AdminResvConstants{
	
	public String sucessesMessage = "";
	public String errorMessage = "";
	
	public void populateMessages(Model model){
		if(sucessesMessage!=null && !"".equals(sucessesMessage)){
			model.addAttribute("sucessesMessage",sucessesMessage);
			sucessesMessage = "";
		}
		if(errorMessage!=null && !"".equals(errorMessage)){
			model.addAttribute("errorMessage",errorMessage);	
			errorMessage = "";
		}
	}
	
	public String getDateBasedOnAvailableDates(Map<String, String> datesMap,String date, String timeZone) {
		String selectedDate = ""; 	
		
	    if(datesMap!=null && datesMap.size()>0){
	    	List<String> dates = new ArrayList<String>(datesMap.keySet());	    	
	    	Date timeZoneSpecificDate = DateUtils.getTargetDateFromUTC(DateUtils.getDateFromString(date,DateUtils.MMDDYYYY_DATE_FORMAT),timeZone);
	    	
	    	int sdIndex = -1;
	    	
			for(int i=0;i<dates.size();i++){//MM/dd/YYYY
				Date timeZoneSpecificConvertedDate = DateUtils.getTargetDateFromUTC(DateUtils.getDateFromString(dates.get(i),DateUtils.MMDDYYYY_DATE_FORMAT),timeZone);
				if(timeZoneSpecificConvertedDate.compareTo(timeZoneSpecificDate)>=0){
					sdIndex = i;
					break;
				}
			}
			
			if(sdIndex==-1){
				for(int i=dates.size()-1;i>=0;i--){
					Date timeZoneSpecificConvertedDate = DateUtils.getTargetDateFromUTC(DateUtils.getDateFromString(dates.get(i),DateUtils.MMDDYYYY_DATE_FORMAT),timeZone);
					if(timeZoneSpecificConvertedDate.compareTo(timeZoneSpecificDate)<=0){
						sdIndex = i;
						break;
					}
				}
			}
			
			selectedDate = dates.get(sdIndex);	
		}else{
			selectedDate = date;	
		}		
		
		return selectedDate;
	}
	
	//Using in Daily Calendar and Table Print View
	public void populateDatesBasedOnAvailableDatesResponse(Map<String, String> datesMap,Object requestObj, String date, String timeZone) throws Exception {
		
		String previousDate = "";
		String selectedDate = ""; 	
		String nextDate = "";
		
	    if(datesMap!=null && datesMap.size()>0){
	    	List<String> dates = new ArrayList<String>(datesMap.keySet());
	    	
	    	int pdIndex = -1;
			int sdIndex = -1;
			int ndIndex = -1; 
			
			if(date==null || "".equals(date)) { //This is Initial Request ie,  Page loading
				date = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);//For Initial loading we are considering today as default date.
		    	Date timeZoneSpecificDate = DateUtils.getTargetDateFromUTC(DateUtils.getDateFromString(date,DateUtils.MMDDYYYY_DATE_FORMAT),timeZone);
		    	
				for(int i=0;i<dates.size();i++){//MM/dd/YYYY
					Date timeZoneSpecificConvertedDate = DateUtils.getTargetDateFromUTC(DateUtils.getDateFromString(dates.get(i),DateUtils.MMDDYYYY_DATE_FORMAT),timeZone);
					if(timeZoneSpecificConvertedDate.compareTo(timeZoneSpecificDate)>=0){					
						if("N".equals(datesMap.get(dates.get(i)))){//Some reservations are available
							sdIndex = i;
							break;
						}else{
							if(sdIndex==-1){
								sdIndex = i;
							}
						}
					}
				}
				
				if(sdIndex==-1){
					for(int i=dates.size()-1;i>=0;i--){
						Date timeZoneSpecificConvertedDate = DateUtils.getTargetDateFromUTC(DateUtils.getDateFromString(dates.get(i),DateUtils.MMDDYYYY_DATE_FORMAT),timeZone);
						if(timeZoneSpecificConvertedDate.compareTo(timeZoneSpecificDate)<=0){
							sdIndex = i;
							break;
						}
					}
				}
			}else{
				sdIndex = dates.indexOf(date);
			}
			
			//to get previous available date
			if(sdIndex>0){
				pdIndex = sdIndex-1;
			}else{
				pdIndex = sdIndex;
			}
			
			//to get next available date
			if(sdIndex<dates.size()-1){
				ndIndex = sdIndex+1;
			}else{
				ndIndex = sdIndex;
			}	
			previousDate = dates.get(pdIndex);
			selectedDate = dates.get(sdIndex); 	
			nextDate = dates.get(ndIndex);			
		}else{
			if(date==null || "".equals(date)) { //This is Initial Request ie,  Page loading
				date = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);//For Initial loading we are considering today as default date.
		    }
			previousDate = date;
			selectedDate = date; 	
			nextDate = date;		
		}
		
		AdminUtils.setPropertyValue(requestObj,"selectedDate",selectedDate);
		AdminUtils.setPropertyValue(requestObj,"firstAvailableDate",selectedDate);
		AdminUtils.setPropertyValue(requestObj,"jsCalendarDate",selectedDate);
		AdminUtils.setPropertyValue(requestObj,"previousAvailableDate",previousDate);
		AdminUtils.setPropertyValue(requestObj,"nextAvailableDate",nextDate);
	}
}
