package com.telappoint.adminresv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.FrontEndMessageConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.controller.common.CommonMessagesController;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.restws.model.BaseResponse;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.ReservationReportConfig;
import com.telappoint.adminresv.restws.model.ReservationReportConfigResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ReservationAutomaticEmailReportsController  extends CommonMessagesController{
	
	private Logger logger = Logger.getLogger(ReservationAutomaticEmailReportsController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_AUTOMATIC_EMAIL_REPORT_CONFIGS, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getReservationReports(@ModelAttribute("homeBean") HomeBean homeBean, Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_AUTOMATIC_EMAIL_REPORT_CONFIGS;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				ReservationReportConfigResponse reseReportConfigResponse = adminResvService.getReservationReportConfig(homeBean.getBaseReq());
				model.addAttribute("reseReportConfigResponse",reseReportConfigResponse);	
			}
			populateMessages(model);
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS.getPage(), 
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS.getDescription());
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
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_ADD_AUTOMATIC_EMAIL_REPORT_CONFIG, method = RequestMethod.GET)
	public ModelAndView addResvReportConfig(@ModelAttribute("homeBean") HomeBean homeBean,ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_ADD_AUTOMATIC_EMAIL_REPORT_CONFIG;		
		ModelAndView modelView = new ModelAndView();	
		try{
			model.put("resvReportConfig",new ReservationReportConfig());
			model.addAttribute("locationListResponse",adminResvService.getLocationListDropDown(homeBean.getBaseReq()));
			model.addAttribute("resvStatusList",adminResvService.getReservationStatusList(homeBean.getBaseReq()));
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_ADD.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_ADD.getPage(), 
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_ADD.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_ADD.getDescription());
			model.put("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
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
	
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_SAVE_AUTOMATIC_EMAIL_REPORT_CONFIG, method = RequestMethod.POST)
	public String saveResvReportConfig(@ModelAttribute("homeBean") HomeBean homeBean, ModelMap model,@ModelAttribute("resvReportConfig")
	ReservationReportConfig resvReportConfig, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_AUTOMATIC_EMAIL_REPORT_CONFIGS+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
		try{			
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				
				String statusFields = AdminUtils.getSelectedValues(resvReportConfig.getResvStatusFields());
				String otherAppointmentStatus =  AdminUtils.getSelectedOtherResvStatusAsString(resvReportConfig.getOtherResvStatusList());
				if(otherAppointmentStatus!=null && !"".equals(otherAppointmentStatus)){
					statusFields = statusFields+","+otherAppointmentStatus;
				}
				resvReportConfig.setSelectedStatusFields(statusFields);
				resvReportConfig.setResvStatusFetch(statusFields);
				
				BaseResponse baseRes = adminResvService.addReservationReportConfig(homeBean.getBaseReq(), resvReportConfig);
				if(baseRes!=null && !baseRes.isErrorStatus() && baseRes.isResponseStatus()){
					sucessesMessage = FrontEndMessageConstants.SAVE_AUTOMATIC_EMAIL_REPORT_SUCESSES_MSG;
				}else{
					errorMessage = FrontEndMessageConstants.SAVE_AUTOMATIC_EMAIL_REPORT_ERROR_MSG;
				}	
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_SAVE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_SAVE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_SAVE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_SAVE.getDescription());
			model.put("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(resvReportConfig));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
	    return targetPage;  
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_DELETE_AUTOMATIC_EMAIL_REPORT_CONFIG, method = RequestMethod.GET)
	public String deleteResvReportConfig(@RequestParam("id") String id, @ModelAttribute("homeBean") HomeBean homeBean, Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_AUTOMATIC_EMAIL_REPORT_CONFIGS+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;	
		try{	
			BaseResponse baseRes = adminResvService.deleteResvReportConfig(homeBean.getBaseReq(),id);
			if(baseRes!=null && !baseRes.isErrorStatus() && baseRes.isResponseStatus()){
				sucessesMessage = FrontEndMessageConstants.DELETE_AUTOMATIC_EMAIL_REPORT_SUCESSES_MSG;
			}else{
				errorMessage = FrontEndMessageConstants.DELETE_AUTOMATIC_EMAIL_REPORT_ERROR_MSG;
			}	
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_DELETE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_DELETE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_DELETE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_DELETE.getDescription());	
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_ERROR_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("reportConfigId=["+id+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
	    return targetPage;  
	}
		
}
