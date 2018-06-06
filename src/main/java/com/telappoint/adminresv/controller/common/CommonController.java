package com.telappoint.adminresv.controller.common;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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

import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.restws.model.DateJSListResponse;
import com.telappoint.adminresv.restws.model.EventListResponse;
import com.telappoint.adminresv.restws.model.LocationListResponse;
import com.telappoint.adminresv.restws.model.ReservationReportCheckboxResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.DateUtils;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class CommonController extends CommonMessagesController {
	
	private static Logger logger = Logger.getLogger(CommonController.class);
	
	@Autowired
	IAdminResvService adminResvService;	
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_GET_EVENT_LIST_BY_LOC_ID, method = RequestMethod.GET)
	public ModelAndView getEventListByLocationId(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("locationId") String locationId,
			@RequestParam("eventDropDownType") String eventDropDownType,@RequestParam(value="dropDownId",required=false) String dropDownId,
			@RequestParam(value="page",required=false) String page,Model model, HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView();
		EventListResponse eventListResponse =  new EventListResponse();
		try{
			eventListResponse = adminResvService.getEventListByLocationId(homeBean.getBaseReq(),locationId);
		}catch (Exception e) {
			logger.error("Error : "+e.getMessage(),e);
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("No input parameters"));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		if(dropDownId==null || "".equals(dropDownId)){
			dropDownId = "eventId";
		}
		modelView.addObject("dropDownId",dropDownId);
		modelView.addObject("eventDropDownType",eventDropDownType);
		modelView.addObject("eventListResponse",eventListResponse);
		modelView.addObject("page",page);
		modelView.setViewName("events_dropdown");
		
		return modelView;
		
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_GET_EVENT_DATE_TIME_FOR_L_E_D_R, method = RequestMethod.GET)
	public ModelAndView getEventDateTimeForLocEventDateRange(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam("locationId") String locationId,@RequestParam("eventId") String eventId,
			@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,
			@RequestParam("dropDownType") String dropDownType,@RequestParam(value="dropDownId",required=false) String dropDownId,
			@RequestParam(value="page",required=false) String page,Model model, HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView();
		ReservationReportCheckboxResponse resvReportCheckboxResponse =  new ReservationReportCheckboxResponse();
		try{
			resvReportCheckboxResponse = adminResvService.getEventDateTimeForLocEventDateRange(homeBean.getBaseReq(), locationId, eventId, startDate, endDate);
		}catch (Exception e) {
			logger.error("Error : "+e.getMessage(),e);
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("No input parameters"));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		if(dropDownId==null || "".equals(dropDownId)){
			dropDownId = "eventDateTimeIds";
		}
		modelView.addObject("dropDownId",dropDownId);
		modelView.addObject("dropDownType",dropDownType);
		modelView.addObject("resvReportCheckboxResponse",resvReportCheckboxResponse);
		modelView.addObject("page",page);
		modelView.setViewName("event_date_time_dropdown");
		
		return modelView;		
	}
	
	@RequestMapping(value="loadJSCalendarDates", method = RequestMethod.GET)
	public ModelAndView loadJSCalendarDates(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam("locationId") String locationId,Model model, HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView();
		DateJSListResponse jsCalDateListResponse = new DateJSListResponse();
		String jsCalFirstDate = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);
		try{
			jsCalDateListResponse = adminResvService.getJSCalendarDateList(homeBean.getBaseReq(),locationId,String.valueOf("-1"));
			LocationListResponse locListRes = adminResvService.getLocationById(homeBean.getBaseReq(),locationId);
			if(locListRes!=null && locListRes.isResponseStatus() && !locListRes.isErrorStatus()){
				if(jsCalDateListResponse!=null && jsCalDateListResponse.getDateMap()!=null && jsCalDateListResponse.getDateMap().size()>0){
					jsCalFirstDate = getDateBasedOnAvailableDates(jsCalDateListResponse.getDateMap(),jsCalFirstDate,AdminUtils.getTimeZone(locListRes.getLocationList(),locationId));
				} 
			}
		}catch (Exception e) {
			logger.error("Error : "+e.getMessage(),e);
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("locationId = "+locationId));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
		model.addAttribute("jsCalDateListResponse",jsCalDateListResponse);
		model.addAttribute("jsCalFirstDate",jsCalFirstDate);
		modelView.setViewName("js-calendar-dates");
		
		return modelView;		
	}
}
