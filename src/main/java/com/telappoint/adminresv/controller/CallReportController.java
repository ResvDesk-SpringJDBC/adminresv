package com.telappoint.adminresv.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.adminresv.constants.AdminResvConstants;
import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.form.CallReportRequestTO;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.InBoundCallReportResponse;
import com.telappoint.adminresv.restws.model.OutBoundCallReportResponse;
import com.telappoint.adminresv.restws.model.TransStateResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.DateUtils;

@Controller
@SessionAttributes(value = "homeBean", types = HomeBean.class)
public class CallReportController implements AdminResvConstants {
	
	private Logger logger = Logger.getLogger(CallReportController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
	
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_CALL_REPORT, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showCallReportPage(@ModelAttribute("homeBean") HomeBean homeBean, BindingResult result, Model model
			,@ModelAttribute("callReportRequestTO") CallReportRequestTO callReportRequestTO,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelView = new ModelAndView();
		String targetPage = JspPageNameConstants.ADMIN_RESV_CALL_REPORT;
		try{
			String reportType = request.getParameter("reportType");
			
			if(null==reportType || "".equals(reportType)){
				reportType = "inbound";
				callReportRequestTO.setReportType(reportType);

				String dateStr = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);
				callReportRequestTO.setInBoundPeriodFrom(dateStr);
				callReportRequestTO.setInBoundPeriodTo(dateStr);
				callReportRequestTO.setOutBoundPeriodFrom(dateStr);
				callReportRequestTO.setOutBoundPeriodTo(dateStr);				
			}
			if("inbound".equals(reportType)){
				InBoundCallReportResponse inBoundCallsResponse = adminResvService.getInBoundCallReportList(callReportRequestTO,homeBean.getBaseReq());
				model.addAttribute("inBoundCallsResponse",inBoundCallsResponse);			
			}else if("outbound".equals(reportType)){
				OutBoundCallReportResponse outBoundCallsResponse = adminResvService.getOutBoundCallReportList(callReportRequestTO,homeBean.getBaseReq());
				model.addAttribute("outBoundCallsResponse",outBoundCallsResponse);			
			}
			model.addAttribute("callReportRequestTO",callReportRequestTO);
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_CALL_REPORT_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_CALL_REPORT_SHOW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_CALL_REPORT_SHOW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_CALL_REPORT_SHOW.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("No Input parameter");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);			
		}
		modelView.setViewName(targetPage);
		return modelView;
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_CALL_REPORT_TRANS_STATES, method = RequestMethod.GET)
	public String getTransStates( @RequestParam("transId") String transId, @ModelAttribute("homeBean") HomeBean homeBean, 
			Model model, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_CALL_REPORT_TRANS_STATES;	
		try{
			TransStateResponse transStateResponse = adminResvService.getTransStates(transId,homeBean.getBaseReq());			
			model.addAttribute("transStateResponse", transStateResponse);
			model.addAttribute("transId", transId);
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_CALL_REPORT_GET_TRANS_STATES.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_CALL_REPORT_GET_TRANS_STATES.getPage(), 
					ErrorCodeConstants.ERROR_CODE_CALL_REPORT_GET_TRANS_STATES.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_CALL_REPORT_GET_TRANS_STATES.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			model.addAttribute("errorBean", errorBean);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("transId=["+transId+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
	    return targetPage;  
	}
}
