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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.adminresv.client.contants.ResvDeskContants;
import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.FrontEndMessageConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.controller.common.CommonMessagesController;
import com.telappoint.adminresv.form.BaseRequest;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.form.validator.CustomerFormValidatorImpl;
import com.telappoint.adminresv.restws.model.AuthResponse;
import com.telappoint.adminresv.restws.model.ClientDetailsResponse;
import com.telappoint.adminresv.restws.model.Customer;
import com.telappoint.adminresv.restws.model.CustomerResponse;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.LoginInfoResponse;
import com.telappoint.adminresv.restws.model.SearchFieldsResponse;
import com.telappoint.adminresv.restws.model.SearchRequestData;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class CustomerDetailsController  extends CommonMessagesController{
	
	private Logger logger = Logger.getLogger(CustomerDetailsController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_CUSTOMER_DETAILS, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView searchCustomerDetails(@ModelAttribute("homeBean") HomeBean homeBean, 
			@ModelAttribute("custDetailsSearchRequest")SearchRequestData custDetailsSearchRequest,@RequestParam(value="isSearchReq",required=false)String isSearchReq,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_CUSTOMER_DETAILS;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				ClientDetailsResponse clientDetailsResponse = null;
				if(isSearchReq!=null && "yes".equals(isSearchReq)){
					clientDetailsResponse = adminResvService.getClientDetails(homeBean.getBaseReq(),custDetailsSearchRequest);
				}else{
					clientDetailsResponse = new ClientDetailsResponse();
				}
				model.addAttribute("clientDetailsResponse",clientDetailsResponse);
				SearchFieldsResponse searchFieldsResponse = adminResvService.getDynamicSearchFields(homeBean.getBaseReq(),ResvDeskContants.SEARCH_CATEGORY_CLIENT_SEARCH.getValue());
				model.addAttribute("searchFieldsResponse",searchFieldsResponse);
				model.addAttribute("custDetailsSearchRequest",custDetailsSearchRequest);
				populateMessages(model);
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_CUSTOMER_DETAILS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_CUSTOMER_DETAILS.getPage(), 
					ErrorCodeConstants.ERROR_CODE_CUSTOMER_DETAILS.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_CUSTOMER_DETAILS.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
		
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(custDetailsSearchRequest));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	
	@RequestMapping(value = "validateCustomerDetails", method = RequestMethod.POST)
	public @ResponseBody String validateCustomerDetailsForm(@ModelAttribute(JspPageNameConstants.HOME_BEAN) HomeBean homeBean,
			@ModelAttribute("customer")Customer customer) throws Exception {
		String validationResponse = "";
		try{
			LoginInfoResponse loginInfoResponse = adminResvService.getLoginInfo(homeBean.getBaseReq());
			if(loginInfoResponse!=null && loginInfoResponse.isResponseStatus() && !loginInfoResponse.isErrorStatus()){
				validationResponse = new CustomerFormValidatorImpl().validate(customer,loginInfoResponse.getLoginFieldList());
			}else{
				validationResponse = "Error while validating Customer Details form!";
			}
		}catch (Exception e) {
			validationResponse = "Error while validating Customer Details Form";
			logger.error(" Error validateReservationForm :  ",e);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(customer));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}	
		return validationResponse;
	}
	
	@RequestMapping(value="add-customer", method = RequestMethod.GET)
	public ModelAndView addCustomer(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,Model model) throws Exception {
		String targetPage = "add-customer";		
		ModelAndView modelView = new ModelAndView();	
		try{
			LoginInfoResponse loginInfoResponse = adminResvService.getLoginInfo(homeBean.getBaseReq());
			model.addAttribute("loginInfoResponse",loginInfoResponse);
			model.addAttribute("customer",new Customer());
		}catch(Exception e){
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_BOOKING_WINDOW_DISPLAY.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_BOOKING_WINDOW_DISPLAY.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_BOOKING_WINDOW_DISPLAY.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_BOOKING_WINDOW_DISPLAY.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("No Parameters");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value ="save-customer", method = RequestMethod.POST)
	public String saveCustomer(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute("customer") Customer customer,HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_CUSTOMER_DETAILS+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION+"?isSearchReq=yes";
		try{
			LoginInfoResponse loginInfoResponse = adminResvService.getLoginInfo(homeBean.getBaseReq());
			AuthResponse authResponse  = adminResvService.authenticateCustomer(customer,homeBean.getBaseReq(), loginInfoResponse.getLoginFieldList());
			if(authResponse!=null && authResponse.isAuthSuccess() && !authResponse.isErrorStatus() && authResponse.isResponseStatus()){
				sucessesMessage = FrontEndMessageConstants.SAVE_CUSTOMER_SUCESSES_MSG;
			}else{
				errorMessage = FrontEndMessageConstants.SAVE_CUSTOMER_ERROR_MSG;
			}		
		}catch (Exception e) {
			logger.error(" Error while booking an reservation! ",e);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");			
			errorMsg.append(AdminUtils.getJSONString(customer));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
			errorMessage = FrontEndMessageConstants.SAVE_CUSTOMER_ERROR_MSG;
		}
		return targetPage;
	}
	
	@RequestMapping(value="edit-customer", method = RequestMethod.GET)
	public ModelAndView editCustomer(@RequestParam("customerId") String customerId,String scheduleId,@ModelAttribute("homeBean") HomeBean homeBean, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String targetPage = "edit-customer";		
		ModelAndView modelView = new ModelAndView();	
		try{
			BaseRequest baseRequest = homeBean.getBaseReq();
			LoginInfoResponse loginInfoResponse = adminResvService.getLoginInfo(baseRequest);
			Customer customer = new Customer();
			if(customerId!=null && !"".equals(customerId) && !"0".equals(customerId) && Integer.parseInt(customerId)>0){
				CustomerResponse customerResponse = adminResvService.getCustomerById(homeBean.getBaseReq(),customerId);
				if(customerResponse!=null && customerResponse.isResponseStatus() && !customerResponse.isErrorStatus()
						&& customerResponse.getCustomerList()!=null && customerResponse.getCustomerList().size()>0){
					customer = customerResponse.getCustomerList().get(0);
				}
			}
			model.addAttribute("customer",customer);
			model.addAttribute("loginInfoResponse",loginInfoResponse);			
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_EDIT_CUSTOMER.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_EDIT_CUSTOMER.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_EDIT_CUSTOMER.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_EDIT_CUSTOMER.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("No Input Parameters");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
		return modelView;	    
	}
	
	@RequestMapping(value="update-customer", method = RequestMethod.POST)
	public String updateCustomer(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute("customer") Customer customer) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_CUSTOMER_DETAILS+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION+"?isSearchReq=yes";
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				AuthResponse authResponse  = adminResvService.updateCustomer(customer,homeBean.getBaseReq());
				if(authResponse!=null && authResponse.isAuthSuccess() && !authResponse.isErrorStatus() && authResponse.isResponseStatus()){
					sucessesMessage = FrontEndMessageConstants.UPDATE_CUSTOMER_SUCESSES_MSG;
				}else{
					errorMessage = FrontEndMessageConstants.UPDATE_CUSTOMER_ERROR_MSG;
				}	
			}			
		}catch (Exception e) {
			logger.error("Error in updateCustomerDetails : ",e);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString(customer));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
			errorMessage = FrontEndMessageConstants.UPDATE_CUSTOMER_ERROR_MSG;
		}
		return targetPage;  
	}
	
	
}
