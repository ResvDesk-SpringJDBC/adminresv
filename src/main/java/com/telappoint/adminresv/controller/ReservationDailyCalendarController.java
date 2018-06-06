package com.telappoint.adminresv.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.controller.common.CommonMessagesController;
import com.telappoint.adminresv.form.CalendarRequest;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.restws.model.DailyCalendarView;
import com.telappoint.adminresv.restws.model.DateJSListResponse;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.EventListResponse;
import com.telappoint.adminresv.restws.model.LocationListResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.DateUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ReservationDailyCalendarController extends CommonMessagesController {
	
	private Logger logger = Logger.getLogger(ReservationDailyCalendarController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_DAILY_VIEW_CALENDAR, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getDailyViewCalendar(@ModelAttribute("homeBean") HomeBean homeBean, Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_DAILY_VIEW_CALENDAR;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				CalendarRequest dailyCalendar = new CalendarRequest();
				dailyCalendar.setChangedParameter("INITIAL_LOAD");
				populateDailyVewCalendarDetails(dailyCalendar,homeBean, model);
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getDescription());
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
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_SEARCH_DAILY_VIEW_CALENDAR, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView searchDailyViewCalendar(@ModelAttribute("homeBean") HomeBean homeBean,
			@ModelAttribute("dailyCalendar")CalendarRequest dailyCalendar,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_DAILY_VIEW_CALENDAR;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				populateDailyVewCalendarDetails(dailyCalendar,homeBean, model);
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
		
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(dailyCalendar));
			errorMsg.append("<br/> Caused By : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_DAILY_VIEW_CALENDAR_FOR_SELECT_SUCT, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView dailyCalendarViewForSelectedCust(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam(value="customerId")String customerId,@RequestParam(value="customerName")String customerName,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_DAILY_VIEW_CALENDAR;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				CalendarRequest dailyCalendar = new CalendarRequest();
				dailyCalendar.setChangedParameter("INITIAL_LOAD");	
				dailyCalendar.setCustomerId(customerId);
				dailyCalendar.setCustomerName(customerName);
				populateDailyVewCalendarDetails(dailyCalendar,homeBean, model);
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR.getDescription());
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
	
	public void populateDailyVewCalendarDetails(CalendarRequest dailyCalendar,HomeBean homeBean, Model model) throws Exception {
		String locationId = dailyCalendar.getLocationId();
		String eventId = dailyCalendar.getEventId();	
		String changedParameter = dailyCalendar.getChangedParameter();
		String date = "";
		String showRemainderIcons = "N";
		if(dailyCalendar!=null && dailyCalendar.getShowRemainderIcons()){
			showRemainderIcons = "Y";
		}
		
		LocationListResponse locationListResponse = adminResvService.getLocationListDropDown(homeBean.getBaseReq());
		EventListResponse eventListResponse = new EventListResponse();
		DateJSListResponse jsCalDateListResponse =  new DateJSListResponse();
		DailyCalendarView dailyCalendarView = new DailyCalendarView();		
		//To Display message initial values we are setting
		dailyCalendarView.setErrorStatus(true);
		dailyCalendarView.setResponseStatus(false);
				
		if("INITIAL_LOAD".equalsIgnoreCase(changedParameter)){
			//date = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);//For Initial loading we are considering today as default date.
		}else if("GoToFirstAvailableDate".equalsIgnoreCase(changedParameter)){
			date = dailyCalendar.getFirstAvailableDate();
		}else if("PreviousDateLink".equalsIgnoreCase(changedParameter)){
			date = dailyCalendar.getPreviousAvailableDate();
		}else if("NextDateLink".equalsIgnoreCase(changedParameter)){
			date = dailyCalendar.getNextAvailableDate();
		}else if("JsCalender".equalsIgnoreCase(changedParameter)){
			date = dailyCalendar.getJsCalendarDate();
		}else if("Refresh".equalsIgnoreCase(changedParameter)){
			date = dailyCalendar.getJsCalendarDate();
		}
				
		 switch(changedParameter) {
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
						//Nothing is to do
					}else{
						break;
					}
				}else{
					break;
				}
			}			   
			default:{	//It has to execute even if Changed Parameter is -- SearchBtn,Refresh,GoToFirstAvailableDate,PreviousDateLink,NextDateLink,JsCalender
				if(jsCalDateListResponse==null || jsCalDateListResponse.getDateMap()==null || jsCalDateListResponse.getDateMap().size()<=0){
					jsCalDateListResponse  = adminResvService.getJSCalendarDateList(homeBean.getBaseReq(),locationId,eventId);
				 }
								
				if(jsCalDateListResponse!=null && jsCalDateListResponse.getDateMap()!=null && jsCalDateListResponse.getDateMap().size()>0){
					 populateDatesBasedOnAvailableDatesResponse(jsCalDateListResponse.getDateMap(),dailyCalendar,date,AdminUtils.getTimeZone(locationListResponse.getLocationList(),locationId));
					 date = dailyCalendar.getSelectedDate();
					 dailyCalendar.setSelectedDateDisplayFormat(DateUtils.getStringFromDateString(date,DateUtils.MMDDYYYY_DATE_FORMAT,DateUtils.FULLTEXTUAL_DAY_FORMAT));
				 }
				
				if(date==null || "".equals(date)){
					 date = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);//For Initial loading we are considering today as default date.
				}
				
				if((locationId!=null && !"".equals(locationId) && !"-1".equals(locationId)) 
						&& (eventId!=null && !"".equals(eventId) && !"-1".equals(eventId)) 
						&& (date!=null && !"".equals(date)) ){
					new SimpleDateFormat(DateUtils.MMDDYYYY_DATE_FORMAT).parse(date); //To ensure the date is in proper date format
					dailyCalendarView = adminResvService.getDailyCalendarView(homeBean.getBaseReq(),locationId,eventId,date,showRemainderIcons);
				}
			}			
		 } 
		 
		 if(eventListResponse==null || eventListResponse.getEventList()==null || eventListResponse.getEventList().size()<=0){
			eventListResponse  = adminResvService.getEventListByLocationId(homeBean.getBaseReq(),locationId);
		 }
		 if(jsCalDateListResponse==null || jsCalDateListResponse.getDateMap()==null || jsCalDateListResponse.getDateMap().size()<=0){
			 jsCalDateListResponse  = adminResvService.getJSCalendarDateList(homeBean.getBaseReq(),locationId,eventId);
		 }
				 		 
		dailyCalendar.setLocationId(locationId);
		dailyCalendar.setEventId(eventId);
				
		model.addAttribute("dailyCalendar",dailyCalendar);	
		model.addAttribute("locationListResponse",locationListResponse);	
		model.addAttribute("eventListResponse",eventListResponse);	
		model.addAttribute("jsCalDateListResponse",jsCalDateListResponse);	
		model.addAttribute("dailyCalendarView",dailyCalendarView);		
	}
}
