package com.telappoint.adminresv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.adminresv.client.contants.LoginConstants;
import com.telappoint.adminresv.constants.AdminResvConstants;
import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.form.AdminInfoTO;
import com.telappoint.adminresv.form.BaseRequest;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.form.bean.ResetPasswordTO;
import com.telappoint.adminresv.form.bean.UserLoginTO;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.HomePageResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.PasswordValidator;

/**
 * 
 * @author Murali G
 * 
 */
@Controller
public class LoginController implements AdminResvConstants{
	
	private Logger logger = Logger.getLogger(LoginController.class);

	private String resetPasswordResponse="";
	private String useLoginResponse="";
	
	@Autowired
	private IAdminResvService adminResvService;
	
	@RequestMapping(value={"/","redirect",JspPageNameConstants.ADMIN_RESV_LOGIN_PAGE}, method = RequestMethod.GET)
	public ModelAndView showAdminLoginPage(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelView = new ModelAndView();	
		UserLoginTO loginForm =new UserLoginTO();
		String ipAddress = request.getRemoteAddr();
		loginForm.setIpAddress(ipAddress);
		model.addAttribute("loginForm", loginForm);
		if(!"".equals(resetPasswordResponse) && resetPasswordResponse!=null){
			model.addAttribute("resetPasswordResponse",resetPasswordResponse);
		}
		if(!"".equals(useLoginResponse) && useLoginResponse!=null){
			model.addAttribute("useLoginResponse",useLoginResponse);
		}
		modelView.setViewName(JspPageNameConstants.ADMIN_RESV_LOGIN_PAGE);
	    return modelView;  
	}
	
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_LOGIN_AUTH, method = RequestMethod.POST)
	public String authenticate(@ModelAttribute("loginForm") UserLoginTO loginForm,  Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_LOGIN_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
		try{
			resetPasswordResponse = "";
			useLoginResponse = "";
			String error = validateForm(loginForm);
			if (error != null && error != ""){
				useLoginResponse = error;
			} else {
				AdminInfoTO adminInfoTO =  adminResvService.getUserDetails(loginForm);				
				
				if(JspPageNameConstants.SUCCESS.equals(adminInfoTO.getLogin())){
					
					if(loginForm.isKeepMeLoggedIn()){
						request.getSession().setMaxInactiveInterval(LoginConstants.KEEP_ME_LOGGED_IN_SESSION_INTERVAL);
					}
					
					/*boolean isWaiting = adminResvService.isAnyTicketWaitingForClientResponse(adminInfoTO.getClientTO().getClient_code(),loginForm.getUsername());	
					if(isWaiting){
						targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_SHOW_SUPPORT_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;					
					}else{
						targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_HOME_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
					}*/
					
					targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_HOME_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
					
					HomeBean homeBean =new HomeBean();				
					
					BaseRequest baseReq = new BaseRequest();
					baseReq.setClientCode(adminInfoTO.getClientTO().getClient_code());
					baseReq.setLangCode("us-en");//TODO : This value we have to get dynamically
					baseReq.setTransId("123"); //TODO : This value we have to get dynamically
					baseReq.setUserId(adminInfoTO.getLoginUserId());
					baseReq.setUserName(loginForm.getUsername());
					homeBean.setBaseReq(baseReq);
					
					adminInfoTO.setUsername(loginForm.getUsername());
					homeBean.setAdminInfoTO(adminInfoTO);
						
					//Reading Commonly used details
					HomePageResponse homePageResponse = adminResvService.getHomePageDetails(homeBean.getBaseReq(),homeBean.getAdminInfoTO().getAccessLevel());
					homeBean.setDisplayNames(homePageResponse.getDisplayNames());
					homeBean.setPrivilegePageMapping(homePageResponse.getPrivilegeMapping());
					homeBean.setResvSysConfig(homePageResponse.getResvSysConfig());
					
					HttpSession session = request.getSession();
					session.setAttribute("homeBean", homeBean);				
				}else {
					useLoginResponse = USER_PASSWORD_INVALID;
				}
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENTS_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean("Null",APPCODE,"Null", 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_SHOW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_SHOW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_SHOW.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client -  Null";
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("No Input parameters"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return targetPage;
	}
	
	public String validateForm(UserLoginTO loginForm) {
		String error = null;
		if (loginForm != null) {
			if (loginForm.getUsername().isEmpty()) {
				error = AdminResvConstants.USERNAME_EMPTY;
			} else if (loginForm.getPassword().isEmpty()) {
				error = PASSWORD_EMPTY;
			} else if (loginForm.getPassword().length()<5) {
				error = USER_PASSWORD_INVALID;
			}
		}
		return error;
	}
	
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_LOG_OUT, method = RequestMethod.GET)
    public String logOut(HttpServletRequest request, Model model) {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_LOGIN_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;	
		try{
			HttpSession session = request.getSession();
	        session.invalidate();
		}catch(Exception e){
			
		}
	    return targetPage; 
    }
	
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_RESET_PASSWORD_REQUEST_TOKEN, method = RequestMethod.POST)
	public ModelAndView sendResetPasswordRequestToken(@RequestParam("usename") String usename,ModelMap model,HttpServletRequest request) {	
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_LOGIN_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
		ModelAndView modelView = new ModelAndView();	
		String hostName = request.getServerName();
		String hostport = String.valueOf(request.getServerPort());
		String applicationName = "adminnotify";
	    String contextPath = request.getContextPath();
		if(contextPath.contains("/")){
			applicationName = contextPath.substring(contextPath.indexOf("/")+1);
		}
		ResetPasswordTO resetPasswordTO = new ResetPasswordTO();
		resetPasswordTO.setUserName(usename);
		resetPasswordTO.setApplicationName(applicationName);
		resetPasswordTO.setHostName(hostName);
		resetPasswordTO.setHostport(hostport);
		resetPasswordResponse = adminResvService.sendResetPasswordRequestToken(resetPasswordTO);
		modelView.setViewName(targetPage);
	    return modelView;
	}
	
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_RESET_PASSWORD_REQUEST, method = RequestMethod.GET)
	public ModelAndView resetPasswordRequest(@RequestParam("token") String token,ModelMap model) {		
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESET_PASSWORD;
		ModelAndView modelView = new ModelAndView();
		try{
			ResetPasswordTO resetPasswordTO  = adminResvService.resetPasswordRequest(token);
			if(resetPasswordTO!=null) {
				resetPasswordTO.setToken(token);
				resetPasswordTO.setNewpassword(null);
				resetPasswordTO.setConformpassword(null);
				model.addAttribute("resetPasswordTO",resetPasswordTO);
			}else{
				targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT + JspPageNameConstants.ADMIN_RESV_LOGIN_PAGE + JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
				resetPasswordResponse = "Invalid request";
			}
		} catch (Exception e) {
			logger.error("exception in resetPasswordRequest - "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(), e);
			String subject = "Error in AdminResv for Client";
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("token=["+token+"]");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;
	}
	
	@RequestMapping(value = "validateResetPassword", method = RequestMethod.GET)
	public @ResponseBody String validateResetPassword(@RequestParam("username") String username,@RequestParam("password") String password,
			@RequestParam("passwordComplexity") String passwordComplexity,HttpServletRequest request) throws Exception {	
			String response = "";
		try {
			response = PasswordValidator.validatePassword(passwordComplexity, username, password);
		} catch (Exception e) {
			logger.error("exception in validateResetPassword - ", e);
			String subject = "Error in AdminResv for UserName - "+username;
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("username=["+username+"]");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return response;
	}
	
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_UPDATE_PASSWORD, method = RequestMethod.POST)
	public ModelAndView updatePassword(@ModelAttribute("resetPasswordTO") ResetPasswordTO resetPasswordTO) {		
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT + JspPageNameConstants.ADMIN_RESV_LOGIN_PAGE + JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
		ModelAndView modelView = new ModelAndView();	
		try{			
			resetPasswordResponse = adminResvService.updatePassword(resetPasswordTO);
			modelView.setViewName(targetPage);
		}catch (Exception e) {
			logger.error("exception in updatePassword - ", e);
			String subject = "Error in AdminResv for Client - "+resetPasswordTO.getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString(resetPasswordTO));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
	    return modelView;
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_SESSION_EXPIRED, method = RequestMethod.GET)
	public ModelAndView showSessionExpiredPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelView = new ModelAndView();
		logger.error(" Session is Expired so redirecting to login page");
		modelView.setViewName(JspPageNameConstants.ADMIN_RESV_SESSION_EXPIRED);
	    return modelView;  
	}
}
