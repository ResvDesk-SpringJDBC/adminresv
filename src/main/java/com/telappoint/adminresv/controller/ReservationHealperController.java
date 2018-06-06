package com.telappoint.adminresv.controller;

import java.util.List;

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

import com.google.gson.Gson;
import com.telappoint.adminresv.constants.AdminResvConstants;
import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.form.BaseRequest;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.form.ReservationBookingForm;
import com.telappoint.adminresv.form.validator.CustomerFormValidatorImpl;
import com.telappoint.adminresv.restws.model.AuthResponse;
import com.telappoint.adminresv.restws.model.BaseResponse;
import com.telappoint.adminresv.restws.model.ConfirmResvResponse;
import com.telappoint.adminresv.restws.model.Customer;
import com.telappoint.adminresv.restws.model.CustomerResponse;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.FutureReservationResponse;
import com.telappoint.adminresv.restws.model.HoldResvResponse;
import com.telappoint.adminresv.restws.model.LoginInfoResponse;
import com.telappoint.adminresv.restws.model.PastReservationResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ReservationHealperController implements AdminResvConstants{
	
	private Logger logger = Logger.getLogger(ReservationHealperController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_GET_CUSTOMERS, method = RequestMethod.GET)
	public @ResponseBody
	String getCustomerNames(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,
			@RequestParam("customerName") String customerName) throws Exception {
		String json = "";
		try{
			CustomerResponse customerResponse = adminResvService.getCustomerNames(homeBean.getBaseReq(),customerName);
			if(customerResponse!=null && !customerResponse.isErrorStatus() && customerResponse.isResponseStatus()){
				List<Customer> customerList = customerResponse.getCustomerList();
				Gson gson = new Gson();
				json = gson.toJson(customerList);
			}
		}catch(Exception e){
			logger.error("Error in getCustomerNames : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("customerName=["+customerName+"]");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
		return json;
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_GET_RESERVATION_BOOKING_WINDOW, method = RequestMethod.GET)
	public ModelAndView getReservationBookingWindow(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,Model model,
			@RequestParam("locationId") String locationId,
			@RequestParam("eventId") String eventId,
			@RequestParam("eventDateTimeId") String eventDateTimeId,
			@RequestParam("seatId") String seatId,
			@RequestParam("customerId") String customerId,
			@RequestParam("locationName") String locationName,
			@RequestParam("eventName") String eventName,
			@RequestParam("seatNumber") String seatNumber,
			@RequestParam("date") String date,
			@RequestParam("time") String time) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_GET_RESERVATION_BOOKING_WINDOW;		
		ModelAndView modelView = new ModelAndView();	
		try{
			BaseRequest baseRequest = homeBean.getBaseReq();
			LoginInfoResponse loginInfoResponse = adminResvService.getLoginInfo(baseRequest);
			Customer customer = new Customer();
			FutureReservationResponse futureResvResponse = new FutureReservationResponse();
			if(customerId!=null && !"".equals(customerId) && !"0".equals(customerId) && Integer.parseInt(customerId)>0){
				CustomerResponse customerResponse = adminResvService.getCustomerById(homeBean.getBaseReq(),customerId);
				if(customerResponse!=null && customerResponse.isResponseStatus() && !customerResponse.isErrorStatus()
						&& customerResponse.getCustomerList()!=null && customerResponse.getCustomerList().size()>0){
					customer = customerResponse.getCustomerList().get(0);
				}
				futureResvResponse = adminResvService.getFutureReservations(homeBean.getBaseReq(),customerId);
			}
			model.addAttribute("customer",customer);
			model.addAttribute("futureResvResponse",futureResvResponse);
			model.addAttribute("loginInfoResponse",loginInfoResponse);
			
			ReservationBookingForm resvBookingForm = new ReservationBookingForm();
			resvBookingForm.setCustomer(customer);
			resvBookingForm.setLocationId(locationId);
			resvBookingForm.setCompanyId("-1");
			resvBookingForm.setDepartmentId("-1");
			resvBookingForm.setProcedureId("-1");
			resvBookingForm.setEventDateTimeId(eventDateTimeId);
			resvBookingForm.setEventId(eventId);
			resvBookingForm.setSeatId(seatId);
			
			
			model.addAttribute("resvBookingForm",resvBookingForm);
			model.addAttribute("locationName",locationName);
			model.addAttribute("eventName",eventName);
			model.addAttribute("seatNumber",seatNumber);
			model.addAttribute("date",date);
			model.addAttribute("time",time);
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

			StringBuilder inputParams = new StringBuilder("customerId=["+customerId+"]");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value = "validateReservationForm", method = RequestMethod.POST)
	public @ResponseBody String validateReservationForm(@ModelAttribute(JspPageNameConstants.HOME_BEAN) HomeBean homeBean,
			@ModelAttribute("resvBookingForm") ReservationBookingForm resvBookingForm) throws Exception {
		String validationResponse = "";
		try{
			LoginInfoResponse loginInfoResponse = adminResvService.getLoginInfo(homeBean.getBaseReq());
			if(loginInfoResponse!=null && loginInfoResponse.isResponseStatus() && !loginInfoResponse.isErrorStatus()){
				validationResponse = new CustomerFormValidatorImpl().validate(resvBookingForm.getCustomer(),loginInfoResponse.getLoginFieldList());
			}else{
				validationResponse = "Error while validating Reservation form!";
			}
		}catch (Exception e) {
			validationResponse = "Error while validating Reservation Form";
			logger.error(" Error validateReservationForm :  ",e);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(resvBookingForm));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}	
		return validationResponse;
	}

	@RequestMapping(value ="bookResvAndUpdateCustDetails", method = RequestMethod.GET)
	public ModelAndView bookResvAndUpdateCustDetails(@ModelAttribute("homeBean") HomeBean homeBean, 
			@ModelAttribute("resvBookingForm") ReservationBookingForm resvBookingForm,HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView();
		try{
			LoginInfoResponse loginInfoResponse = adminResvService.getLoginInfo(homeBean.getBaseReq());
			AuthResponse authResponse  = adminResvService.authenticateCustomer(resvBookingForm.getCustomer(),homeBean.getBaseReq(), loginInfoResponse.getLoginFieldList());
			if(authResponse!=null && authResponse.isAuthSuccess() && !authResponse.isErrorStatus() && authResponse.isResponseStatus()){
				HoldResvResponse holdResvResponse = adminResvService.holdReservation(homeBean.getBaseReq(), resvBookingForm,String.valueOf(authResponse.getCustomerId()));
				if(holdResvResponse.isResponseStatus() && !holdResvResponse.isErrorStatus() && !"-1".equals(holdResvResponse.getScheduleId()) && !"0".equals(holdResvResponse.getScheduleId())){
					ConfirmResvResponse confirmResvResponse  = adminResvService.confirmReservation(homeBean.getBaseReq(),String.valueOf(holdResvResponse.getScheduleId()),String.valueOf(authResponse.getCustomerId()),resvBookingForm.getComments());
					if(confirmResvResponse!=null && confirmResvResponse.isResponseStatus() && !confirmResvResponse.isErrorStatus()){
						modelView.addObject("confirmResvResponse", confirmResvResponse);
						modelView.addObject("resvBookingSuccess", "SUCCESS");
						//modelView.addObject("bookingResvResponse",confirmResvResponse.getMessage());
						modelView.addObject("bookingResvResponse","Reservation booked sucessfully!");
					}else{
						logger.info("Error while confirming reservation for customer id - "+authResponse.getCustomerId());
						modelView.addObject("bookingResvResponse", "Error while confirming reservation");
					}						
				}else{
					logger.info("Error while holding reservation for customer id - "+authResponse.getCustomerId());
					modelView.addObject("bookingResvResponse", "Error while holding reservation");
				}
			}else{
				logger.info("Error while updating/creating customer");
				modelView.addObject("bookingResvResponse", "Error while updating/creating customer!");
			}		
		}catch (Exception e) {
			logger.error(" Error while booking an reservation! ",e);
			modelView.addObject("bookingResvResponse", "Error while booking reservation!");
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");			
			errorMsg.append(AdminUtils.getJSONString(resvBookingForm));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName("booked-resv-response");

		return modelView;
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_GET_CUSTOMER_RESERVATION_DETAILS_WINDOW, method = RequestMethod.GET)
	public ModelAndView getCustomerReservationDetailsWindow(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,
			@RequestParam("customerId") String customerId,@RequestParam("scheduleId") String scheduleId,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_GET_CUSTOMER_RESERVATION_DETAILS_WINDOW;		
		ModelAndView modelView = new ModelAndView();	
		try{
			BaseRequest baseRequest = homeBean.getBaseReq();
			LoginInfoResponse loginInfoResponse = adminResvService.getLoginInfo(baseRequest);
			Customer customer = new Customer();
			FutureReservationResponse futureResvResponse = new FutureReservationResponse();
			PastReservationResponse pastReservResponse = new PastReservationResponse();
			if(customerId!=null && !"".equals(customerId) && !"0".equals(customerId) && Integer.parseInt(customerId)>0){
				CustomerResponse customerResponse = adminResvService.getCustomerById(homeBean.getBaseReq(),customerId);
				if(customerResponse!=null && customerResponse.isResponseStatus() && !customerResponse.isErrorStatus()
						&& customerResponse.getCustomerList()!=null && customerResponse.getCustomerList().size()>0){
					customer = customerResponse.getCustomerList().get(0);
				}
				futureResvResponse = adminResvService.getFutureReservations(homeBean.getBaseReq(),customerId);
				pastReservResponse = adminResvService.getPastReservations(homeBean.getBaseReq(),customerId);
			}
			model.addAttribute("customer",customer);
			model.addAttribute("futureResvResponse",futureResvResponse);
			model.addAttribute("pastReservResponse",pastReservResponse);
			model.addAttribute("loginInfoResponse",loginInfoResponse);
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

			StringBuilder inputParams = new StringBuilder("customerId=["+customerId+"]");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value="edit-customer-details", method = RequestMethod.GET)
	public ModelAndView editCustomerDetails(@RequestParam("customerId") String customerId,@RequestParam(value="scheduleId",required=false) String scheduleId,@ModelAttribute("homeBean") HomeBean homeBean, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String targetPage = "edit-customer-details";		
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
			ReservationBookingForm resvBookingForm = new ReservationBookingForm();
			resvBookingForm.setCustomer(customer);			
			
			model.addAttribute("resvBookingForm",resvBookingForm);
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
	
	@RequestMapping(value="update-customer-details", method = RequestMethod.POST)
	public @ResponseBody
	String updateCustomerDetails(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute("resvBookingForm") ReservationBookingForm resvBookingForm) throws Exception {	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				//LoginInfoResponse loginInfoResponse = adminResvService.getLoginInfo(homeBean.getBaseReq());
				//AuthResponse authResponse  = adminResvService.authenticateCustomer(resvBookingForm.getCustomer(),homeBean.getBaseReq(), loginInfoResponse.getLoginFieldList());
				
				AuthResponse authResponse  = adminResvService.updateCustomer(resvBookingForm.getCustomer(),homeBean.getBaseReq());
				if(authResponse!=null && authResponse.isAuthSuccess() && !authResponse.isErrorStatus() && authResponse.isResponseStatus()){
					return "SUCCESS";
				}else{
					return "FAILURE";
				}
			}			
		}catch (Exception e) {
			logger.error("Error in updateCustomerDetails : ",e);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(AdminUtils.getJSONString(resvBookingForm.getCustomer()));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
			return "FAILURE";
		}
		return "FAILURE";  
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_GET_UPDATED_CUSTOMER_RESERVATION_DETAILS_WINDOW, method = RequestMethod.GET)
	public ModelAndView getUpdatedCustomerAndResvDetailsWindow(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,
			@RequestParam("customerId") String customerId,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_GET_UPDATED_CUSTOMER_RESERVATION_DETAILS_WINDOW;		
		ModelAndView modelView = new ModelAndView();	
		try{
			BaseRequest baseRequest = homeBean.getBaseReq();
			LoginInfoResponse loginInfoResponse = adminResvService.getLoginInfo(baseRequest);
			Customer customer = new Customer();
			FutureReservationResponse futureResvResponse = new FutureReservationResponse();
			PastReservationResponse pastReservResponse = new PastReservationResponse();
			if(customerId!=null && !"".equals(customerId) && !"0".equals(customerId) && Integer.parseInt(customerId)>0){
				CustomerResponse customerResponse = adminResvService.getCustomerById(homeBean.getBaseReq(),customerId);
				if(customerResponse!=null && customerResponse.isResponseStatus() && !customerResponse.isErrorStatus()
						&& customerResponse.getCustomerList()!=null && customerResponse.getCustomerList().size()>0){
					customer = customerResponse.getCustomerList().get(0);
				}
				futureResvResponse = adminResvService.getFutureReservations(homeBean.getBaseReq(),customerId);
				pastReservResponse = adminResvService.getPastReservations(homeBean.getBaseReq(),customerId);
			}
			model.addAttribute("customer",customer);
			model.addAttribute("futureResvResponse",futureResvResponse);
			model.addAttribute("pastReservResponse",pastReservResponse);
			model.addAttribute("loginInfoResponse",loginInfoResponse);
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

			StringBuilder inputParams = new StringBuilder("customerId=["+customerId+"]");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	
	@RequestMapping(value="cancle-resv-and-load-updated-resvs", method = RequestMethod.GET)
	public ModelAndView cancleResvAndLoadUpdatedResvs(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam("customerId") String customerId,@RequestParam("sceduleId") String sceduleId,Model model) throws Exception {
		ModelAndView modelView = new ModelAndView();
		try{
			BaseResponse baseResponse = adminResvService.cancelReservation(homeBean.getBaseReq(),sceduleId,customerId);	
			model.addAttribute("operationResponse",((!baseResponse.isErrorStatus() && baseResponse.isResponseStatus())? "Sucessfully cancelled Resvation!" : "Error while cancelling Resvation!"));
			model.addAttribute("operationResponseStatus",((!baseResponse.isErrorStatus() && baseResponse.isResponseStatus())? "SUCCESS" : "FAILURE"));
			
			FutureReservationResponse futureResvResponse = new FutureReservationResponse();
			if(customerId!=null && !"".equals(customerId) && !"0".equals(customerId) && Integer.parseInt(customerId)>0){
				futureResvResponse = adminResvService.getFutureReservations(homeBean.getBaseReq(),customerId);
			}
			model.addAttribute("resvDetailsResv",futureResvResponse.getResvDetails());
			model.addAttribute("resvDetailsResponseStatus",((!futureResvResponse.isErrorStatus() && futureResvResponse.isResponseStatus())? "SUCCESS" : "FAILURE"));
			model.addAttribute("resvDetailsResponse",((!futureResvResponse.isErrorStatus() && futureResvResponse.isResponseStatus())? "Sucessfully retrived Resvation details!" : "Error while retriving Resvation!"));
		} catch(Exception e){
			logger.error("Error in cancleResvAndLoadUpdatedResvs : ",e);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("customerId=["+customerId+"],");
			inputParams.append("sceduleId=["+sceduleId+"],");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}	
		model.addAttribute("responseDisplyDivId","upcomingResvResponseDisplyDivId");
		model.addAttribute("linkName","Cancel");
		model.addAttribute("linkCSSClassName","cancleUpcomingResv");
		
		modelView.setViewName("updated-reservation-details");
		return modelView;
	}
	
	@RequestMapping(value="update-screened-status", method = RequestMethod.GET)
	public @ResponseBody
	String updateScreenedStatus(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam("customerId") String customerId,@RequestParam("scheduleId") String sceduleId,
			@RequestParam("screened") String screened) throws Exception {	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				BaseResponse baseResponse  = adminResvService.updateScreenedStatus(homeBean.getBaseReq(),sceduleId,screened);
				if(baseResponse!=null && !baseResponse.isErrorStatus() && baseResponse.isResponseStatus()){
					return "SUCCESS";
				}else{
					return "FAILURE";
				}
			}			
		}catch (Exception e) {
			logger.error("Error in updateScreenedStatus : ",e);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("customerId=["+customerId+"],");
			inputParams.append("sceduleId=["+sceduleId+"],");
			inputParams.append("screened=["+screened+"],");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
			return "FAILURE";
		}
		return "FAILURE";  
	}
	
	@RequestMapping(value="update-seat-reserved-status", method = RequestMethod.GET)
	public @ResponseBody
	String updateSeatReservedStatus(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam("seatId") String seatId,
			@RequestParam("reserved") String reserved) throws Exception {
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				return adminResvService.updateSeatReservedStatus(homeBean.getBaseReq(),seatId,reserved);				
			}			
		}catch (Exception e) {
			logger.error("Error in updateSeatReservedStatus : ",e);
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("seatId=["+seatId+"],");
			inputParams.append("reserved=["+reserved+"]");
			errorMsg.append(AdminUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return AdminUtils.getBaseResponseJSONStringForFailureOperation();
	}
		
}
