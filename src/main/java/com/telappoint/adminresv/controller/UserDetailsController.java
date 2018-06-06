package com.telappoint.adminresv.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import com.telappoint.adminresv.form.AdminInfoTO;
import com.telappoint.adminresv.form.ClientTO;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.form.bean.ChangePasswordTO;
import com.telappoint.adminresv.form.bean.ResetPasswordTO;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.EventListResponse;
import com.telappoint.adminresv.restws.model.LocationListResponse;
import com.telappoint.adminresv.restws.model.PrivilageResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.PasswordValidator;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class UserDetailsController  extends CommonMessagesController {
	
	private Logger logger = Logger.getLogger(UserDetailsController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_USER_DETAILS_PAGE, method = RequestMethod.GET)
	public String showUserDetailsPage(@ModelAttribute("homeBean") HomeBean homeBean, BindingResult result, Model model) throws Exception {
		String targetPage =JspPageNameConstants.ADMIN_RESV_USER_DETAILS_PAGE;	
		try {
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				Integer clientId = homeBean.getAdminInfoTO().getClientId();
				String clientCode = homeBean.getBaseReq().getClientCode();
				
				List<AdminInfoTO> adminInfoTos = adminResvService.getUserList(clientId,clientCode);	
				
				/*LocationListResponse locationListResponse = adminResvService.getLocationListDropDown(homeBean.getBaseReq());				
				List<LocationTO> locations = adminResvService.getLocationList(homeBean.getBaseReq().getClientCode(),"N");
				List<ResourceTO> resources = adminResvService.getResourceListByOrder(homeBean.getBaseReq().getClientCode(),"N","locationName");
				StringBuilder assignedLocations = null;
				StringBuilder assignedResources = null;
				List<String> ids = null;
				
				if(adminInfoTos!=null && adminInfoTos.size()>0){
					String resouceName = homeBean.getDisplayNamesTO().getResource_name();
					for(AdminInfoTO adminInfoTO : adminInfoTos){
						String accessLevel = adminInfoTO.getAccessLevel();						
						if(accessLevel!=null && "Location".equals(accessLevel)){
							ids = Arrays.asList(adminInfoTO.getLocationIds().split("\\s*,\\s*"));
							assignedLocations = new StringBuilder();
							for(LocationTO location : locations){
								if(ids.contains(String.valueOf(location.getId()))){
									assignedLocations.append(location.getLocation_name_online()).append("<br/>");
								}
							}
							if(assignedLocations.toString()!=null && assignedLocations.toString().length()>5){
								adminInfoTO.setPrivalageDetails(assignedLocations.toString().substring(0,assignedLocations.toString().length()-5));
							}
						}else if(accessLevel!=null && "Provider".equals(accessLevel)){
							adminInfoTO.setAccessLevel(resouceName);
							ids = Arrays.asList(adminInfoTO.getResourceIds().split("\\s*,\\s*"));
							assignedResources = new StringBuilder();
							for(ResourceTO resource : resources){
								if(ids.contains(String.valueOf(resource.getId()))){
									//System.out.println("Resource Name @ Location Name ::: "+(resource.getDisplayName()+" @ "+resource.getLocationName()));
									assignedResources.append(resource.getDisplayName()+" @ "+resource.getLocationName()).append("<br/>");
								}
							}
							if(assignedResources.toString()!=null && assignedResources.toString().length()>5){
								adminInfoTO.setPrivalageDetails(assignedResources.toString().substring(0,assignedResources.toString().length()-5));
							}
						}
						
					}
				}*/
				model.addAttribute("adminInfoTOs", adminInfoTos);	
				populateMessages(model);
			}	
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_USAERS_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_USAERS_SHOW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_SHOW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_SHOW.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("No Input parameters"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
	    return targetPage;  
	}
	
	@RequestMapping(value = "validateUserName", method = RequestMethod.GET)
	public @ResponseBody String validateUserName(@RequestParam("username") String username,@RequestParam("id") String id, 
			@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {	
		String response = "NO";
		try {
			response = adminResvService.validateUserName(username,id);
		} catch (Exception e) {
			logger.error("Error while validateUserName :" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString());
			String subject = "Error in AdminResv for UserName - "+username;
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString("userName=["+username+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return response;
	}
	
	@RequestMapping(value = "validatePassword", method = RequestMethod.GET)
	public @ResponseBody String validatePassword(@RequestParam("username") String username,@RequestParam("password") String password, @ModelAttribute("homeBean") HomeBean homeBean,
			HttpServletRequest request) throws Exception {	
		String response = "";
		try {
			if (homeBean != null && homeBean.getBaseReq() != null) {
				String passwordComplexity = homeBean.getAdminInfoTO().getPasswordComplexity();
				response = PasswordValidator.validatePassword(passwordComplexity, username, password);
			}
		} catch (Exception e) {
			logger.error("Error while validatePassword :" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString());
			String subject = "Error in AdminResv for UserName - "+username;
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString("userName=["+username+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return response;
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_USER_ADD_USER, method = RequestMethod.GET)
	public ModelAndView showAddUserPage(@ModelAttribute("homeBean") HomeBean homeBean) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_USER_ADD_USER;		
		ModelAndView modelView = new ModelAndView();	
		try{			
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				AdminInfoTO adminInfoTOs = new AdminInfoTO();
				modelView.addObject("adminInfoTOs", adminInfoTOs);				
				LocationListResponse locationListResponse = adminResvService.getLocationListDropDown(homeBean.getBaseReq());	
				modelView.addObject("locationListResponse",locationListResponse);
				EventListResponse eventListResponse = adminResvService.getEventList(homeBean.getBaseReq());
				modelView.addObject("eventListResponse",eventListResponse);		
				PrivilageResponse privilageResponse = adminResvService.getAccessesPrivileges(homeBean.getBaseReq());
				modelView.addObject("privilageResponse",privilageResponse);	
			}						
		}catch (Exception e) {		
			logger.error(ErrorCodeConstants.ERROR_CODE_USAERS_ADD.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_USAERS_ADD.getPage(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_ADD.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_ADD.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("No Input parameters");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;	    
	}
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_USER_SAVE_USER, method = RequestMethod.POST)
	public String saveUserDetails(@ModelAttribute("homeBean") HomeBean homeBean, @ModelAttribute("adminInfoTO") AdminInfoTO adminInfoTO, 
			HttpServletRequest request,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_USER_DETAILS_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				ClientTO clientTO = homeBean.getAdminInfoTO().getClientTO();
				//ClientTO clientTO = adminResvService.getClientDetailsByCode(homeBean.getBaseReq().getClientCode());
				adminInfoTO.setClientId(clientTO.getId());	
				adminInfoTO.setExpireDate(clientTO.getDate_cancelled());			
				adminInfoTO.setStartDate(clientTO.getDate_subscribed());	
				adminInfoTO.setLocked('N');
				/*String accessLevel = adminInfoTO.getAccessLevel();
				String resouceName = homeBean.getDisplayNames().getResource_name();
				if(resouceName!=null && accessLevel!=null && accessLevel.equals(resouceName)){
					adminInfoTO.setAccessLevel("Provider");
				}*/
				boolean isSaved = adminResvService.addUserDetails(adminInfoTO);
				if(isSaved){
					sucessesMessage = FrontEndMessageConstants.SAVE_USER_SUCESSES_MSG;
				}else{
					errorMessage = FrontEndMessageConstants.SAVE_USER_ERROR_MSG;
				}
			}
						
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_USAERS_SAVE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_USAERS_SAVE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_SAVE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_SAVE.getDescription());
			model.addAttribute("errorBean", errorBean);		
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString(adminInfoTO));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
	    return targetPage;  
	}
	

	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_USER_EDIT_USER, method = RequestMethod.GET)
	public String editUserDetails( @RequestParam("id") String id, @ModelAttribute("homeBean") HomeBean homeBean, BindingResult result, Model model, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_USER_EDIT_USER;	
		try{			
			
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				AdminInfoTO adminInfoTOs = adminResvService.getUserDetailsById(id);
				String accessLevel = adminInfoTOs.getAccessLevel();
				String resouceName = ""; //homeBean.getDisplayNames().getResource_name();
				if(accessLevel!=null && "Provider".equals(accessLevel)){
					adminInfoTOs.setAccessLevel(resouceName);
				}
				model.addAttribute("adminInfoTOs", adminInfoTOs);	
				
				LocationListResponse locationListResponse = adminResvService.getLocationListDropDown(homeBean.getBaseReq());	
				model.addAttribute("locationListResponse",locationListResponse);
				EventListResponse eventListResponse = adminResvService.getEventList(homeBean.getBaseReq());
				model.addAttribute("eventListResponse",eventListResponse);	
				PrivilageResponse privilageResponse = adminResvService.getAccessesPrivileges(homeBean.getBaseReq());
				model.addAttribute("privilageResponse",privilageResponse);	
			}			
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_USAERS_EDIT.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_USAERS_EDIT.getPage(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_EDIT.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_EDIT.getDescription());
			model.addAttribute("errorBean", errorBean);				
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString("Id=["+id+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
	    return targetPage;  
	}
	 
	 
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_USER_UPDATE_USER, method = RequestMethod.POST)
	public String updateUserDetails(@ModelAttribute("adminInfoTO") AdminInfoTO adminInfoTO, @ModelAttribute("homeBean") HomeBean homeBean,
			HttpServletRequest request,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_USER_DETAILS_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				String clientCode = homeBean.getBaseReq().getClientCode();
				ClientTO clientTO = homeBean.getAdminInfoTO().getClientTO();
				adminInfoTO.setClientId(clientTO.getId());
				adminInfoTO.setClientCode(clientCode);
				adminInfoTO.setExpireDate(clientTO.getDate_cancelled());
				adminInfoTO.setStartDate(clientTO.getDate_subscribed());
				
				String accessLevel = adminInfoTO.getAccessLevel();
				String resouceName = ""; //homeBean.getDisplayNames().getResource_name();
				if(resouceName!=null && accessLevel!=null && accessLevel.equals(resouceName)){
					adminInfoTO.setAccessLevel("Provider");
				}
				accessLevel = adminInfoTO.getAccessLevel();
				if(accessLevel!=null &&  !"Provider".equals(accessLevel)  &&  !"Location".equals(accessLevel)){
					adminInfoTO.setResourceIds("");
					adminInfoTO.setLocationIds("");
				}
				if(accessLevel!=null &&  "Provider".equals(accessLevel)){
					adminInfoTO.setLocationIds("");
				}
				if(accessLevel!=null &&  "Location".equals(accessLevel)){
					adminInfoTO.setResourceIds("");
				}
				adminInfoTO.setLocked('N');
				boolean isUpdated = adminResvService.updateUserDetails(adminInfoTO);
				if(isUpdated){
					sucessesMessage = FrontEndMessageConstants.SAVE_USER_SUCESSES_MSG;
				}else{
					errorMessage = FrontEndMessageConstants.SAVE_USER_ERROR_MSG;
				}
			}						
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_USAERS_UPDATE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_USAERS_UPDATE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_UPDATE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_UPDATE.getDescription());	
			model.addAttribute("errorBean", errorBean);		
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString(adminInfoTO));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
	    return targetPage;  
	}
	

	 @RequestMapping(value=JspPageNameConstants.ADMIN_RESV_USER_DELETE_USER, method = RequestMethod.GET)
	public String deleteUserDetails( @RequestParam("id") String id,@ModelAttribute("homeBean") HomeBean homeBean, 
			HttpServletRequest request,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_USER_DETAILS_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;	
		try{
			boolean isDeleted = adminResvService.deleteUserDetails(id);
			if(isDeleted){
				sucessesMessage = FrontEndMessageConstants.DELETE_USER_SUCESSES_MSG;
			}else{
				errorMessage = FrontEndMessageConstants.DELETE_USER_ERROR_MSG;
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_USAERS_DELETE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_USAERS_DELETE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_DELETE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_DELETE.getDescription());
			model.addAttribute("errorBean", errorBean);		
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString("User Id : "+id));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
	    return targetPage;  
	}
	
	
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_CHANGE_PASSWORD_REQUEST, method = RequestMethod.GET)
	public ModelAndView changepassword(@ModelAttribute("homeBean") HomeBean homeBean) {		
		String targetPage = JspPageNameConstants.ADMIN_RESV_CHANGE_PASSWORD_REQUEST;
		ModelAndView modelView = new ModelAndView();	
		try{
			ChangePasswordTO changePasswordTO = new ChangePasswordTO();
			changePasswordTO.setUserName(homeBean.getAdminInfoTO().getUsername());
			modelView.addObject("changePasswordTO",changePasswordTO);			
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_USAERS_CHANGE_PASSWORD.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_USAERS_CHANGE_PASSWORD.getPage(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_CHANGE_PASSWORD.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_CHANGE_PASSWORD.getDescription());	
			modelView.addObject("errorBean", errorBean);		
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString("No Input parameter"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;
	}
	
	
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_UPDATE_CHANGE_PASSWORD, method = RequestMethod.POST)
	public ModelAndView updatechangepassword(@ModelAttribute("resetPasswordTO") ChangePasswordTO changePasswordTO,
			@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) {		
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_LOGIN_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
		ModelAndView modelView = new ModelAndView();	
		try{
			String response = adminResvService.updatechangepassword(changePasswordTO);
			request.getSession().setAttribute("changePasswordResponse", response);
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_USAERS_UPDATE_PASSWORD.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_USAERS_UPDATE_PASSWORD.getPage(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_UPDATE_PASSWORD.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_USAERS_UPDATE_PASSWORD.getDescription());	
			modelView.addObject("errorBean", errorBean);		
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
						
			String subject = "Error in AdminResv for UserName - "+changePasswordTO.getUserName();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString(changePasswordTO));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;
	}
	
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_VALIDATE_OLD_PASSWORD, method = RequestMethod.GET)
	public @ResponseBody String validateoldpassword(@RequestParam("userName") String userName,@RequestParam("oldpassword") String oldpassword,ModelMap model) {		
		try{
			ChangePasswordTO changePasswordTO = new ChangePasswordTO();
			changePasswordTO.setUserName(userName);
			changePasswordTO.setOldpassword(oldpassword);
			String response = adminResvService.validateoldpassword(changePasswordTO);
			return response;
		}catch (Exception e) {
			logger.error("exception in validateoldpassword - ", e);
			String subject = "Error in AdminResv for UserName - "+userName;
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString("userName=["+userName+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
	    return null;
	}
		
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_CHANGE_USER_PASSWORD_REQUEST, method = RequestMethod.GET)
	public String changeUserPassword( @RequestParam("id") String id, @ModelAttribute("homeBean") HomeBean homeBean, BindingResult result, Model model, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_CHANGE_USER_PASSWORD_REQUEST;	
		try {
			ResetPasswordTO resetPasswordTO = new ResetPasswordTO();
			resetPasswordTO.setUserId(Integer.parseInt(id));
			model.addAttribute("resetPasswordTO", resetPasswordTO);	
		} catch (Exception e) {
			logger.error("Error while changeUserPassword :" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString());
			String subject = "Error in AdminResv for UserId - "+id;
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString("UserId=["+id+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return targetPage;
	}
	
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_UPDATE_USER_PASSWORD, method = RequestMethod.POST)
	public String updateUserPassword(@ModelAttribute("resetPasswordTO") ResetPasswordTO resetPasswordTO, 
			@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {	
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_USER_DETAILS_PAGE+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
		try {
			sucessesMessage = adminResvService.updatePasswordChangedByAdmin(resetPasswordTO);
		} catch (Exception e) {
			logger.error("Error while updatePasswordChangedByAdmin :" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString());
			String subject = "Error in AdminResv for UserId - "+resetPasswordTO.getUserId();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString("UserId=["+resetPasswordTO.getUserId()+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return targetPage;
	}
}
