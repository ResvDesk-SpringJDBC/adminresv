package com.telappoint.adminresv.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.adminresv.constants.AdminResvConstants;
import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.form.CalendarBaseRequest;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.restws.model.DateJSListResponse;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.EventListResponse;
import com.telappoint.adminresv.restws.model.LocationListResponse;
import com.telappoint.adminresv.restws.model.SeatsCalendarView;
import com.telappoint.adminresv.restws.model.TimeListResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.DateUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ReservationSeatViewCalendarController implements AdminResvConstants{
	
	private Logger logger = Logger.getLogger(ReservationSeatViewCalendarController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_SEAT_VIEW_CALENDAR, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getSeatViewCalendar(@ModelAttribute("homeBean") HomeBean homeBean, Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_SEAT_VIEW_CALENDAR;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				CalendarBaseRequest seatViewCalendar = new CalendarBaseRequest();
				seatViewCalendar.setChangedParameter("INITIAL_LOAD");
				//TODO : ShowRemainderIcons Functionality -- Change to true
				//seatViewCalendar.setShowRemainderIcons(false);
				
				populateSeatVewCalendarDetails(seatViewCalendar,homeBean,model);
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_SEAT_VIEW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_SEAT_VIEW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_SEAT_VIEW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_SEAT_VIEW.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
		
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("No Input parameters"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_SEARCH_SEAT_VIEW_CALENDAR, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView searchSeatViewCalendar(@ModelAttribute("homeBean") HomeBean homeBean,
			@ModelAttribute("seatViewCalendar")CalendarBaseRequest seatViewCalendar,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_SEAT_VIEW_CALENDAR;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				populateSeatVewCalendarDetails(seatViewCalendar,homeBean,model);
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_SEAT_VIEW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_SEAT_VIEW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_SEAT_VIEW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_SEAT_VIEW.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
		
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(seatViewCalendar));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	public void populateSeatVewCalendarDetails(CalendarBaseRequest seatViewCalendar,HomeBean homeBean, Model model) throws Exception {
		String locationId = seatViewCalendar.getLocationId();
		String eventId = seatViewCalendar.getEventId();
		String date = seatViewCalendar.getDate();
		String time = seatViewCalendar.getTime();
		String showRemainderIcons = "N";
		if(seatViewCalendar!=null && seatViewCalendar.getShowRemainderIcons()){
			showRemainderIcons = "Y";
		}
		
		LocationListResponse locationListResponse = adminResvService.getLocationListDropDown(homeBean.getBaseReq());
		EventListResponse eventListResponse = new EventListResponse();
		DateJSListResponse jsCalDateListResponse =  new DateJSListResponse();
		TimeListResponse timeListResponse = new TimeListResponse();
		SeatsCalendarView seatsCalendarView = new SeatsCalendarView();		
		
		//To Display message initial values we are setting
		seatsCalendarView.setErrorStatus(true);
		seatsCalendarView.setResponseStatus(false);
		
		 switch(seatViewCalendar.getChangedParameter()) {
			case "INITIAL_LOAD": {
				if(locationListResponse!=null && locationListResponse.getLocationList()!=null && locationListResponse.getLocationList().size()>0){
					locationId = String.valueOf(locationListResponse.getLocationList().get(0).getLocationId());
				}
			}
			case "Location": {
				if(locationId!=null && !"".equals(locationId) && !"-1".equals(locationId)){
					eventListResponse  = adminResvService.getEventListByLocationId(homeBean.getBaseReq(),locationId);
					if(eventListResponse!=null && eventListResponse.getEventList()!=null && eventListResponse.getEventList().size()>0){
						eventId = String.valueOf(eventListResponse.getEventList().get(0).getEventId());
					}else{
						break;
					}
				}else{
					break;
				}
			}
			case "Event":{				
				if(eventId!=null && !"".equals(eventId) && !"-1".equals(eventId)){
					jsCalDateListResponse  = adminResvService.getJSCalendarDateList(homeBean.getBaseReq(),locationId,eventId);
					if(jsCalDateListResponse!=null && jsCalDateListResponse.getDateMap()!=null && jsCalDateListResponse.getDateMap().size()>0){
						//Whenever Location or Event is changed we have to check with respective to the today date
						date = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);//For Initial loading we are considering today as default date.
						
						date = getDateBasedOnAvailableDatesResponse(jsCalDateListResponse.getDateMap(),date,AdminUtils.getTimeZone(locationListResponse.getLocationList(),locationId));
					}else{
						break;
					}
				}else{
					break;
				}
			}			   
			case "JsCalender":{				
				if(date!=null && !"".equals(date)){
					new SimpleDateFormat(DateUtils.MMDDYYYY_DATE_FORMAT).parse(date); //To ensure the date is in proper date format
					timeListResponse = adminResvService.getSeatViewTimeList(homeBean.getBaseReq(),locationId,eventId,date);
					if(timeListResponse!=null && timeListResponse.getTimeList()!=null && timeListResponse.getTimeList().size()>0){
						time = timeListResponse.getTimeList().get(0);
						seatsCalendarView = adminResvService.getSeatsCalendarView(homeBean.getBaseReq(),locationId,eventId,date,time,showRemainderIcons);
						break;
					}
				}else{
					break;
				}
			}
			case "Time":{
				if((locationId!=null && !"".equals(locationId) && !"-1".equals(locationId)) 
						&& (eventId!=null && !"".equals(eventId) && !"-1".equals(eventId)) 
						&& (date!=null && !"".equals(date)) && (time!=null && !"".equals(time) && !"-1".equals(time))){
					new SimpleDateFormat(DateUtils.MMDDYYYY_DATE_FORMAT).parse(date); //To ensure the date is in proper date format
					seatsCalendarView = adminResvService.getSeatsCalendarView(homeBean.getBaseReq(),locationId,eventId,date,time,showRemainderIcons);
					break;
				}
			}
			case "SearchBtn":{
				if((locationId!=null && !"".equals(locationId) && !"-1".equals(locationId)) 
						&& (eventId!=null && !"".equals(eventId) && !"-1".equals(eventId)) 
						&& (date!=null && !"".equals(date)) && (time!=null && !"".equals(time) && !"-1".equals(time))){
					new SimpleDateFormat(DateUtils.MMDDYYYY_DATE_FORMAT).parse(date); //To ensure the date is in proper date format
					seatsCalendarView = adminResvService.getSeatsCalendarView(homeBean.getBaseReq(),locationId,eventId,date,time,showRemainderIcons);
					break;
				}
			}
			case "Refresh":{
				if((locationId!=null && !"".equals(locationId) && !"-1".equals(locationId)) 
						&& (eventId!=null && !"".equals(eventId) && !"-1".equals(eventId)) && (date!=null && !"".equals(date))){
					new SimpleDateFormat(DateUtils.MMDDYYYY_DATE_FORMAT).parse(date); //To ensure the date is in proper date format
					timeListResponse = adminResvService.getSeatViewTimeList(homeBean.getBaseReq(),locationId,eventId,date);
					if(timeListResponse!=null && timeListResponse.getTimeList()!=null && timeListResponse.getTimeList().size()>0){
						time = timeListResponse.getTimeList().get(0);
						seatsCalendarView = adminResvService.getSeatsCalendarView(homeBean.getBaseReq(),locationId,eventId,date,time,showRemainderIcons);
						break;
					}
				}
			}
			case "ShowRemainderIcons":{
				if((locationId!=null && !"".equals(locationId) && !"-1".equals(locationId)) 
						&& (eventId!=null && !"".equals(eventId) && !"-1".equals(eventId)) && (date!=null && !"".equals(date))
						&& (time!=null && !"".equals(time) && !"-1".equals(time))){
					new SimpleDateFormat(DateUtils.MMDDYYYY_DATE_FORMAT).parse(date); //To ensure the date is in proper date format
					timeListResponse = adminResvService.getSeatViewTimeList(homeBean.getBaseReq(),locationId,eventId,date);
					seatsCalendarView = adminResvService.getSeatsCalendarView(homeBean.getBaseReq(),locationId,eventId,date,time,showRemainderIcons);
				}
			}
		 } 
		 
		 if(eventListResponse==null || eventListResponse.getEventList()==null || eventListResponse.getEventList().size()<=0){
			eventListResponse  = adminResvService.getEventListByLocationId(homeBean.getBaseReq(),locationId);
		 }
		 if(jsCalDateListResponse==null || jsCalDateListResponse.getDateMap()==null || jsCalDateListResponse.getDateMap().size()<=0){
			 jsCalDateListResponse  = adminResvService.getJSCalendarDateList(homeBean.getBaseReq(),locationId,eventId);
		 }
		 if(timeListResponse==null || timeListResponse.getTimeList()==null || timeListResponse.getTimeList().size()<=0){
			 timeListResponse = adminResvService.getSeatViewTimeList(homeBean.getBaseReq(),locationId,eventId,date);
		 }		 
		
		seatViewCalendar.setLocationId(locationId);
		seatViewCalendar.setEventId(eventId);
		seatViewCalendar.setDate(date);
		seatViewCalendar.setTime(time);
		
		model.addAttribute("seatViewCalendar",seatViewCalendar);	
		model.addAttribute("locationListResponse",locationListResponse);	
		model.addAttribute("eventListResponse",eventListResponse);	
		model.addAttribute("jsCalDateListResponse",jsCalDateListResponse);	
		model.addAttribute("timeListResponse",timeListResponse);	
		model.addAttribute("seatsCalendarView",seatsCalendarView);		
	}	
	
	private String getDateBasedOnAvailableDatesResponse(Map<String, String> datesMap,String date, String timeZone) {
		String selectedDate = ""; 	
		
	    if(datesMap!=null && datesMap.size()>0){
	    	List<String> dates = new ArrayList<String>(datesMap.keySet());	    	
	    	Date timeZoneSpecificDate = DateUtils.getTargetDateFromUTC(DateUtils.getDateFromString(date,DateUtils.MMDDYYYY_DATE_FORMAT),timeZone);
	    	
	    	int sdIndex = -1;
	    	
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
			
			selectedDate = dates.get(sdIndex);	
		}else{
			selectedDate = date;	
		}		
		
		return selectedDate;
	}
}
