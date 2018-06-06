package com.telappoint.adminresv.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.controller.common.CommonMessagesController;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.restws.model.BaseResponse;
import com.telappoint.adminresv.restws.model.DateJSListResponse;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.EventListResponse;
import com.telappoint.adminresv.restws.model.Location;
import com.telappoint.adminresv.restws.model.LocationListResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.DateUtils;
import com.telappoint.adminresv.utils.EmailUtils;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class HomeController  extends CommonMessagesController {
		
	@Autowired
	private IAdminResvService adminResvService;
	@Autowired
	public EmailUtils emailUtils;
	
	private Logger logger = Logger.getLogger(HomeController.class);
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_HOME_PAGE, method = RequestMethod.GET)
	public String showHomePage(@ModelAttribute("homeBean") HomeBean homeBean, BindingResult result, Model model,HttpServletRequest request)throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_HOME_PAGE;
		try{
			LocationListResponse locationListResponse = adminResvService.getLocationList(homeBean.getBaseReq());
			model.addAttribute("locationListResponse",locationListResponse);	
			EventListResponse eventListResponse = adminResvService.getEventList(homeBean.getBaseReq());
			model.addAttribute("eventListResponse",eventListResponse);	
			model.addAttribute("states", AdminUtils.getStatesMap());
			
			String date = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);
			DateJSListResponse jsCalDateListResponse = new DateJSListResponse();
			if(locationListResponse!=null && locationListResponse.getLocationList()!=null && locationListResponse.getLocationList().size()>0){
				String locationId = "";
				for(Location location : locationListResponse.getLocationList()) {
					locationId = String.valueOf(location.getLocationId());
					if(locationId!=null && !"".equals(locationId)){
						jsCalDateListResponse = adminResvService.getJSCalendarDateList(homeBean.getBaseReq(),locationId,String.valueOf("-1"));
						if(jsCalDateListResponse!=null && jsCalDateListResponse.getDateMap()!=null && jsCalDateListResponse.getDateMap().size()>0){
							date = getDateBasedOnAvailableDates(jsCalDateListResponse.getDateMap(),date,AdminUtils.getTimeZone(locationListResponse.getLocationList(),locationId));
							if(date!=null && !"".equals(date)){
								break;
							}
						} 
					}
				}
			} 
			
			if(date==null || "".equals(date)){
				date = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);
			}			
			model.addAttribute("date",date);
			model.addAttribute("jsCalDateListResponse",jsCalDateListResponse);
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESV_WINDOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_RESV_WINDOW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESV_WINDOW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESV_WINDOW.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("No Input parameters"));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return targetPage;
	}
	
	//Graphs related functionality.	
	@RequestMapping(value="getGraphsData", method = RequestMethod.GET)
	public @ResponseBody
	String getGraphsData(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam String requestedGraphType,@RequestParam String locationId,@RequestParam String date) throws Exception {
		String jsonGraphResponse = "";
		try{
			jsonGraphResponse = adminResvService.getGraphDetails(homeBean.getBaseReq(), locationId, date);
		}catch(Exception e){
			logger.error("Error in getGraphsData : ",e);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			StringBuilder inputParams = new StringBuilder();
			inputParams.append("requestedGraphType=["+requestedGraphType+"],");
			inputParams.append("locationId=["+locationId+"],");
			inputParams.append("date=["+date+"],");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
		return jsonGraphResponse;
	}
	
	@RequestMapping(value="changeSchedulerStatus", method = RequestMethod.GET)
	public @ResponseBody
	String changeAppSysConfigVal(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,
				@RequestParam("schedulerStatus") String schedulerStatus) throws Exception {
		String response = "FAILURE";
		try{
			BaseResponse baseRes = adminResvService.changeSchedulerStatus(homeBean.getBaseReq(),schedulerStatus);
			if(baseRes!=null && !baseRes.isErrorStatus() && baseRes.isResponseStatus()){					
				response = "SUCCESS";
				if("open".equalsIgnoreCase(schedulerStatus)) {
					homeBean.getResvSysConfig().setSchedulerClosed("N");
				} else {
					homeBean.getResvSysConfig().setSchedulerClosed("Y");
				}
				
			}else{
				response = "FAILURE";
			}
			
			String subject = "Scheduler "+schedulerStatus;
			subject = subject + " for "+homeBean.getBaseReq().getClientCode();
			StringBuilder bodyMsg =new StringBuilder();
			bodyMsg.append("<br/>");
			bodyMsg.append("Client Name : "+homeBean.getAdminInfoTO().getClientTO().getClient_name());
			bodyMsg.append("<br/>");	
			bodyMsg.append("Client code : "+homeBean.getBaseReq().getClientCode());
			bodyMsg.append("<br/>");
			bodyMsg.append("User Id : "+homeBean.getAdminInfoTO().getLoginUserId());
			bodyMsg.append("<br/>");			
			bodyMsg.append("User Name : "+homeBean.getAdminInfoTO().getUsername());
			
			emailUtils.sendEmail(subject,bodyMsg.toString(),"SCHEDULER_DETAILS_CHANGED");
			//PropertyUtils.sendSchedulerDetailsChangedAlertMail(subject,bodyMsg.toString());
		}catch(Exception e){
			logger.error("Error in changeAppSysConfigVal : ",e);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			StringBuilder inputParams = new StringBuilder("schedulerStatus=["+schedulerStatus+"],");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}	
		return response;
	}
}
