package com.telappoint.adminresv.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.controller.common.CommonMessagesController;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.form.TablePrintViewRequest;
import com.telappoint.adminresv.restws.model.DateJSListResponse;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.Event;
import com.telappoint.adminresv.restws.model.EventListResponse;
import com.telappoint.adminresv.restws.model.LocationListResponse;
import com.telappoint.adminresv.restws.model.TablePrintViewResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.DateUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ReservationTablePrintViewController  extends CommonMessagesController {
	
	private Logger logger = Logger.getLogger(ReservationTablePrintViewController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_TABLE_PRINT_VIEW_CALENDAR, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getTablePrintView(@ModelAttribute("homeBean") HomeBean homeBean, Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_TABLE_PRINT_VIEW_CALENDAR;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				TablePrintViewRequest tablePrintViewRequest = new TablePrintViewRequest();
				tablePrintViewRequest.setChangedParameter("INITIAL_LOAD");		
				populateTablePrintViewDetails(tablePrintViewRequest,homeBean, model);
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_TABLE_PRINT_VIEW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_TABLE_PRINT_VIEW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_TABLE_PRINT_VIEW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_TABLE_PRINT_VIEW.getDescription());
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
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_SEARCH_TABLE_PRINT_VIEW_CALENDAR, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView searchTablePrintView(@ModelAttribute("homeBean") HomeBean homeBean,
			@ModelAttribute("tablePrintViewRequest")TablePrintViewRequest tablePrintViewRequest,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_TABLE_PRINT_VIEW_CALENDAR;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				populateTablePrintViewDetails(tablePrintViewRequest,homeBean, model);
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_TABLE_PRINT_VIEW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_TABLE_PRINT_VIEW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_TABLE_PRINT_VIEW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_TABLE_PRINT_VIEW.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
		
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(tablePrintViewRequest));
			errorMsg.append("<br/> Caused By : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	public void populateTablePrintViewDetails(TablePrintViewRequest tablePrintViewRequest,HomeBean homeBean, Model model) throws Exception {
		String locationId = tablePrintViewRequest.getLocationId();
		String[] eventIdsList = tablePrintViewRequest.getSelectedEventIdsList();
		String changedParameter = tablePrintViewRequest.getChangedParameter();
		String date = "";
		
		LocationListResponse locationListResponse = adminResvService.getLocationListDropDown(homeBean.getBaseReq());
		EventListResponse eventListResponse = new EventListResponse();
		TablePrintViewResponse tablePrintViewResponse = new TablePrintViewResponse();		
		DateJSListResponse jsCalDateListResponse =  new DateJSListResponse();
		
		if("INITIAL_LOAD".equalsIgnoreCase(changedParameter)){
			//date = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);//For Initial loading we are considering today as default date.
		}else if("JsCalender".equalsIgnoreCase(changedParameter)){
			date = tablePrintViewRequest.getJsCalendarDate();
		}else if("PreviousDateLink".equalsIgnoreCase(changedParameter)){
			date = tablePrintViewRequest.getPreviousAvailableDate();
		}else if("NextDateLink".equalsIgnoreCase(changedParameter)){
			date = tablePrintViewRequest.getNextAvailableDate();
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
				}else{
					if(eventListResponse==null || eventListResponse.getEventList()==null || eventListResponse.getEventList().size()<=0){
						eventListResponse  = adminResvService.getEventListByLocationId(homeBean.getBaseReq(),locationId);
					 }
					break;
				}
			}	   
			default:{	//It has to execute even if Changed Parameter is -- View Reservation click,PreviousDateLink,NextDateLink, Go Button click				
				 if(eventListResponse==null || eventListResponse.getEventList()==null || eventListResponse.getEventList().size()<=0){
					eventListResponse  = adminResvService.getEventListByLocationId(homeBean.getBaseReq(),locationId);
				 }
				 String eventIds =  "";
				 
				 if("INITIAL_LOAD".equalsIgnoreCase(changedParameter)){
					 if(eventListResponse!=null && eventListResponse.isResponseStatus() && !eventListResponse.isErrorStatus()
							&& eventListResponse.getEventList()!=null && eventListResponse.getEventList().size()>0){
						 eventIds =  getEventIds(tablePrintViewRequest,eventListResponse.getEventList());
					 }
				 }else{
					 //eventIds = getEventIds(eventIdsList);
					 eventIds = AdminUtils.concatWithSeparator(eventIdsList,",");
				 }
				 
				 jsCalDateListResponse  = adminResvService.getJSCalendarDateList(homeBean.getBaseReq(),locationId,"-1");
				 if(jsCalDateListResponse!=null && jsCalDateListResponse.getDateMap()!=null && jsCalDateListResponse.getDateMap().size()>0){
					 populateDatesBasedOnAvailableDatesResponse(jsCalDateListResponse.getDateMap(),tablePrintViewRequest,date,AdminUtils.getTimeZone(locationListResponse.getLocationList(),locationId));
					 date = tablePrintViewRequest.getSelectedDate();
					 tablePrintViewRequest.setSelectedDateDisplayFormat(DateUtils.getStringFromDateString(date,DateUtils.MMDDYYYY_DATE_FORMAT,DateUtils.FULLTEXTUAL_DAY_FORMAT));
				 }
				
				 if(date==null || "".equals(date)){
					 date = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);//For Initial loading we are considering today as default date.
					 tablePrintViewRequest.setSelectedDateDisplayFormat(DateUtils.getStringFromDateString(date,DateUtils.MMDDYYYY_DATE_FORMAT,DateUtils.FULLTEXTUAL_DAY_FORMAT));
				 }
				 
				if((locationId!=null && !"".equals(locationId) && !"-1".equals(locationId)) 
						&& (eventIds!=null && !"".equals(eventIds) && !",".equals(eventIds)) 
						&& (date!=null && !"".equals(date)) ){
					new SimpleDateFormat(DateUtils.MMDDYYYY_DATE_FORMAT).parse(date); //To ensure the date is in proper date format
					tablePrintViewResponse = adminResvService.getTablePrintView(homeBean.getBaseReq(),locationId,eventIds,date);
				}
								 
			}			
		 } 
		 	 
		tablePrintViewRequest.setLocationId(locationId);
		tablePrintViewRequest.setSelectedDate(date);
		tablePrintViewRequest.setJsCalendarDate(date);
						
		model.addAttribute("tablePrintViewRequest",tablePrintViewRequest);	
		model.addAttribute("locationListResponse",locationListResponse);	
		model.addAttribute("eventListResponse",eventListResponse);	
		model.addAttribute("tablePrintViewResponse",tablePrintViewResponse);	
		
		if(jsCalDateListResponse==null || jsCalDateListResponse.getDateMap()==null || jsCalDateListResponse.getDateMap().size()<=0){
			jsCalDateListResponse  = adminResvService.getJSCalendarDateList(homeBean.getBaseReq(),locationId,"-1");
		}
		model.addAttribute("jsCalDateListResponse",jsCalDateListResponse);	
	}
	
	/*private String getEventIds(String[] eventIdsList){		
		String eventIds = "";
		if(null!=eventIdsList && eventIdsList.length > 0){
			for(String eventId : eventIdsList){
				eventIds = eventIds + eventId+",";
			}
		}
		if(null!=eventIds && !"".equals(eventIds) && !",".equals(eventIds) ){
			eventIds = eventIds.substring(0, eventIds.length()-1);
		}
		return eventIds;
	}*/
	
	private String getEventIds(TablePrintViewRequest tablePrintViewRequest,List<Event> eventList){		
		String eventIds = "";		
		if(null!=eventList && eventList.size() > 0){
			String[] eventIdsList = new String[eventList.size()];
			int i = 0;
			for(Event event : eventList){
				eventIds = eventIds + event.getEventId()+",";
				eventIdsList[i++] = String.valueOf(event.getEventId());
			}
			tablePrintViewRequest.setSelectedEventIdsList(eventIdsList);
		}
		if(null!=eventIds && !"".equals(eventIds) && !",".equals(eventIds) ){
			eventIds = eventIds.substring(0, eventIds.length()-1);
		}
		return eventIds;
	}
}
