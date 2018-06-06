package com.telappoint.adminresv.controller;

import java.util.Date;

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
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.form.ReservationReportRequest;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.EventListResponse;
import com.telappoint.adminresv.restws.model.ReservationReportResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.DateUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ReservationReportsController implements AdminResvConstants{
	
	private Logger logger = Logger.getLogger(ReservationReportsController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_REPORTS, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getReservationReports(@ModelAttribute("homeBean") HomeBean homeBean, Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_REPORTS;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				ReservationReportRequest resReportRequest = new ReservationReportRequest();
				resReportRequest.setStartDate(DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT));
				resReportRequest.setEndDate(DateUtils.getDateString(DateUtils.MMDDYYYY_DATE_FORMAT,30));
				model.addAttribute("resReportRequest",resReportRequest);	
				model.addAttribute("locationListResponse",adminResvService.getLocationListDropDown(homeBean.getBaseReq()));	
				model.addAttribute("eventListResponse",adminResvService.getEventList(homeBean.getBaseReq()));	
				model.addAttribute("resvStatusList",adminResvService.getReservationStatusList(homeBean.getBaseReq()));
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REPORTS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REPORTS.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REPORTS.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REPORTS.getDescription());
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
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_SEARCH_REPORTS, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView searchReservationReports(@ModelAttribute("homeBean") HomeBean homeBean,
			@ModelAttribute("resReportRequest")ReservationReportRequest resReportRequest,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_REPORTS;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				
				String statusFields = AdminUtils.getSelectedValues(resReportRequest.getResvStatusFields());
				String otherAppointmentStatus =  AdminUtils.getSelectedOtherResvStatusAsString(resReportRequest.getOtherResvStatusList());
				if(otherAppointmentStatus!=null && !"".equals(otherAppointmentStatus)){
					statusFields = statusFields+","+otherAppointmentStatus;
				}
				resReportRequest.setSelectedStatusFields(statusFields);
				resReportRequest.setSelectedEventDateTimeIds(AdminUtils.getSelectedValues(resReportRequest.getEventDateTimeIds()));
				
				ReservationReportResponse resReportResponse = adminResvService.getReservationReports( homeBean.getBaseReq(),resReportRequest);
				model.addAttribute("resReportResponse",resReportResponse);
				model.addAttribute("locationListResponse",adminResvService.getLocationListDropDown(homeBean.getBaseReq()));	
				EventListResponse eventListResponse =  null;
				if(resReportRequest.getLocationId()>0){
					eventListResponse =  adminResvService.getEventListByLocationId(homeBean.getBaseReq(),String.valueOf(resReportRequest.getLocationId()));
				}else{
					adminResvService.getEventList(homeBean.getBaseReq());
				}				
				modelView.addObject("eventListResponse",eventListResponse);
				model.addAttribute("resReportRequest",resReportRequest);	
				model.addAttribute("resvStatusList",adminResvService.getReservationStatusList(homeBean.getBaseReq()));
				model.addAttribute("resvReportCheckboxResponse",adminResvService.getEventDateTimeForLocEventDateRange(homeBean.getBaseReq(), 
						String.valueOf(resReportRequest.getLocationId()),String.valueOf(resReportRequest.getEventId()), resReportRequest.getStartDate(),resReportRequest.getEndDate()));
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REPORTS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REPORTS.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REPORTS.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_REPORTS.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
		
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(resReportRequest));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}	
	
}
