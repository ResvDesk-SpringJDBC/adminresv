package com.telappoint.adminresv.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.FrontEndMessageConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.controller.common.CommonMessagesController;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.form.NotificationRequest;
import com.telappoint.adminresv.restws.model.BaseResponse;
import com.telappoint.adminresv.restws.model.CampaignResponse;
import com.telappoint.adminresv.restws.model.DateJSListResponse;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.LocationListResponse;
import com.telappoint.adminresv.restws.model.NotifyRequest;
import com.telappoint.adminresv.restws.model.ReservationRemindersResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.DateUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ReservationRemindersController  extends CommonMessagesController {
	
	private Logger logger = Logger.getLogger(ReservationRemindersController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_REMAINDERS, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getResvReminders(@ModelAttribute("homeBean") HomeBean homeBean,
			@ModelAttribute("notificationRequest")NotificationRequest notificationRequest, Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_REMAINDERS;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				if(notificationRequest==null){
					notificationRequest = new NotificationRequest();					
				}
				if(notificationRequest.getChangedParameter()==null || "".equals(notificationRequest.getChangedParameter())){
					notificationRequest.setChangedParameter("INITIAL_LOAD");
				}
				populateReservationRemaindersDetails(notificationRequest,homeBean, model);
			}
			populateMessages(model);
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REMAINDERS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REMAINDERS.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REMAINDERS.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REMAINDERS.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
		
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(notificationRequest!=null ? notificationRequest : "No Input parameters"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	public void populateReservationRemaindersDetails(NotificationRequest notificationRequest,HomeBean homeBean, Model model) throws Exception {
		String locationId = notificationRequest.getLocationId();
		String campaignId = notificationRequest.getCampaignId();
		String changedParameter = notificationRequest.getChangedParameter();
		String date = "";
		
		LocationListResponse locationListResponse = adminResvService.getLocationListDropDown(homeBean.getBaseReq());
		CampaignResponse campaignResponse =  adminResvService.getCampaigns(homeBean.getBaseReq());
		ReservationRemindersResponse resvReminder =  new ReservationRemindersResponse();
		DateJSListResponse jsCalDateListResponse =  new DateJSListResponse();
		
		if("INITIAL_LOAD".equalsIgnoreCase(changedParameter)){
			if(locationListResponse!=null && locationListResponse.getLocationList()!=null && locationListResponse.getLocationList().size()>0){
				locationId = String.valueOf(locationListResponse.getLocationList().get(0).getLocationId());
			}
		}else if("JsCalender".equalsIgnoreCase(changedParameter)){
			date = notificationRequest.getJsCalendarDate();
		}else if("PreviousDateLink".equalsIgnoreCase(changedParameter)){
			date = notificationRequest.getPreviousAvailableDate();
		}else if("NextDateLink".equalsIgnoreCase(changedParameter)){
			date = notificationRequest.getNextAvailableDate();
		}
		 
		 if(locationId!=null && !"".equals(locationId)){
			 jsCalDateListResponse  = adminResvService.getJSCalendarDateList(homeBean.getBaseReq(),locationId,"-1");
		 }
		 
		 if(jsCalDateListResponse!=null && jsCalDateListResponse.getDateMap()!=null && jsCalDateListResponse.getDateMap().size()>0){
			 populateDatesBasedOnAvailableDatesResponse(jsCalDateListResponse.getDateMap(),notificationRequest,date,AdminUtils.getTimeZone(locationListResponse.getLocationList(),locationId));
			 date = notificationRequest.getSelectedDate();
		 }
		 
		 if(date==null || "".equals(date)){
			 date = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);//For Initial loading we are considering today as default date.
		 }				
		 notificationRequest.setSelectedDateDisplayFormat(DateUtils.getStringFromDateString(date,DateUtils.MMDDYYYY_DATE_FORMAT,DateUtils.FULLTEXTUAL_DAY_FORMAT));
		 
		 if((locationId!=null && !"".equals(locationId)) && (campaignId!=null && !"".equals(campaignId)) && (date!=null && !"".equals(date)) ){
			new SimpleDateFormat(DateUtils.MMDDYYYY_DATE_FORMAT).parse(date); //To ensure the date is in proper date format
			resvReminder = adminResvService.getReservationReminders(homeBean.getBaseReq(),locationId,campaignId,date);
		 }
								
		 model.addAttribute("notificationRequest",notificationRequest);	
		 model.addAttribute("locationListResponse",locationListResponse);
		 model.addAttribute("resvReminder",resvReminder);
		 model.addAttribute("campaignResponse",campaignResponse);
		 model.addAttribute("jsCalDateListResponse",jsCalDateListResponse);	
	}
	
	public void populateDatesBasedOnAvailableDatesResponse(Map<String, String> datesMap,NotificationRequest notificationRequest, String date, String timeZone) throws Exception {
		
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
				date =getDateBasedOnAvailableDates(datesMap,date,timeZone);
			}
			
			sdIndex = dates.indexOf(date);
			
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
		
		notificationRequest.setSelectedDate(selectedDate);
		notificationRequest.setJsCalendarDate(selectedDate);
		notificationRequest.setPreviousAvailableDate(previousDate);
		notificationRequest.setNextAvailableDate(nextDate);
	}

	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV__EDIT_RESV_REMAINDERS_PHONE_STATUS, method = RequestMethod.GET)
	public String editResvNotifyStatus(@RequestParam("notifyId") String notifyId, @RequestParam("notifyPhoneStatus") String notifyPhoneStatus,
			@RequestParam("notifyStatus") String notifyStatus, @RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,
			@ModelAttribute("homeBean") HomeBean homeBean, Model model, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV__EDIT_RESV_REMAINDERS_PHONE_STATUS;	
		try{	
			//Notification locationListResponse = adminResvService.getNotificationById(homeBean.getBaseReq(),notifyId);
			NotifyRequest notifyRequest = new NotifyRequest();
			notifyRequest.setNotifyId(Long.parseLong(notifyId));
			notifyRequest.setNotifyPhoneStatus(Integer.parseInt(notifyPhoneStatus));
			notifyRequest.setNotifyStatus(Integer.parseInt(notifyStatus));
			
			model.addAttribute("notifyRequest",notifyRequest);
			model.addAttribute("firstName",firstName);
			model.addAttribute("lastName",lastName);
			model.addAttribute("reminderStatusResponse", adminResvService.getNotifyRemainderStatusList(homeBean.getBaseReq()));
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATION_REMAINDERS_EDIT_STATUS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATION_REMAINDERS_EDIT_STATUS.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATION_REMAINDERS_EDIT_STATUS.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATION_REMAINDERS_EDIT_STATUS.getDescription());	
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("notifyId=["+notifyId+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
	    return targetPage;  
	}

	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV__UPDATE_RESV_REMAINDERS_PHONE_STATUS, method = RequestMethod.POST)
	public @ResponseBody String updateResvNotifyStatus(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute("notifyRequest")NotifyRequest notifyRequest)throws Exception {
		String response = "";		
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				notifyRequest.setUserName(homeBean.getBaseReq().getUserName());
				BaseResponse baseRes = adminResvService.updateNotifyStatus(homeBean.getBaseReq(),notifyRequest);
				if(baseRes!=null && !baseRes.isErrorStatus() && baseRes.isResponseStatus()){
					response = "SUCESSES";
					sucessesMessage = FrontEndMessageConstants.RESV_REMAIDERS_STATUS_UPDATE_SUCESSES_MSG;
				}else{
					response = "FAILURE";
					errorMessage = FrontEndMessageConstants.RESV_REMAIDERS_STATUS_UPDATE_ERROR_MSG;
				}					
			}			
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATION_REMAINDERS_UPDATE_STATUS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			
			response = "FAILURE";
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(notifyRequest));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
	    return response;  
	}	
	
}
