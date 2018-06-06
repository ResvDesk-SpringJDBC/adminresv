package com.telappoint.adminresv.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.telappoint.adminresv.client.contants.ResvDeskContants;
import com.telappoint.adminresv.client.json.JsonDataHandler;
import com.telappoint.adminresv.client.json.RevJsonDataHandler;
import com.telappoint.adminresv.form.AdminInfoTO;
import com.telappoint.adminresv.form.BaseRequest;
import com.telappoint.adminresv.form.CallReportRequestTO;
import com.telappoint.adminresv.form.ReservationBookingForm;
import com.telappoint.adminresv.form.ReservationReportRequest;
import com.telappoint.adminresv.form.bean.ChangePasswordTO;
import com.telappoint.adminresv.form.bean.ResetPasswordTO;
import com.telappoint.adminresv.form.bean.UserLoginTO;
import com.telappoint.adminresv.restclient.AdminResvRestClient;
import com.telappoint.adminresv.restws.model.AuthResponse;
import com.telappoint.adminresv.restws.model.BaseResponse;
import com.telappoint.adminresv.restws.model.CalendarOverviewDetailsResponse;
import com.telappoint.adminresv.restws.model.CalendarOverviewResponse;
import com.telappoint.adminresv.restws.model.CallReportRequest;
import com.telappoint.adminresv.restws.model.CampaignResponse;
import com.telappoint.adminresv.restws.model.ClientDetailsResponse;
import com.telappoint.adminresv.restws.model.ConfirmResvResponse;
import com.telappoint.adminresv.restws.model.Customer;
import com.telappoint.adminresv.restws.model.CustomerResponse;
import com.telappoint.adminresv.restws.model.DailyCalendarView;
import com.telappoint.adminresv.restws.model.DailyReminderView;
import com.telappoint.adminresv.restws.model.DateJSListResponse;
import com.telappoint.adminresv.restws.model.DateListResponse;
import com.telappoint.adminresv.restws.model.EmailTemplatesTO;
import com.telappoint.adminresv.restws.model.Event;
import com.telappoint.adminresv.restws.model.EventDateTime;
import com.telappoint.adminresv.restws.model.EventDateTimeResponse;
import com.telappoint.adminresv.restws.model.EventListResponse;
import com.telappoint.adminresv.restws.model.FutureReservationResponse;
import com.telappoint.adminresv.restws.model.HoldResvResponse;
import com.telappoint.adminresv.restws.model.HomePageResponse;
import com.telappoint.adminresv.restws.model.InBoundCallReportResponse;
import com.telappoint.adminresv.restws.model.LandingPageContentTO;
import com.telappoint.adminresv.restws.model.Location;
import com.telappoint.adminresv.restws.model.LocationListResponse;
import com.telappoint.adminresv.restws.model.LoginField;
import com.telappoint.adminresv.restws.model.LoginInfoResponse;
import com.telappoint.adminresv.restws.model.Notification;
import com.telappoint.adminresv.restws.model.NotifyRequest;
import com.telappoint.adminresv.restws.model.OutBoundCallReportResponse;
import com.telappoint.adminresv.restws.model.PastReservationResponse;
import com.telappoint.adminresv.restws.model.PrivilageResponse;
import com.telappoint.adminresv.restws.model.PrivilegeSettings;
import com.telappoint.adminresv.restws.model.RegistrationInfoResponse;
import com.telappoint.adminresv.restws.model.ReminderStatusResponse;
import com.telappoint.adminresv.restws.model.ReservationRemindersResponse;
import com.telappoint.adminresv.restws.model.ReservationReportCheckboxResponse;
import com.telappoint.adminresv.restws.model.ReservationReportConfig;
import com.telappoint.adminresv.restws.model.ReservationReportConfigResponse;
import com.telappoint.adminresv.restws.model.ReservationReportResponse;
import com.telappoint.adminresv.restws.model.ReservationSearchResponse;
import com.telappoint.adminresv.restws.model.ReservationStatusResponse;
import com.telappoint.adminresv.restws.model.SearchFieldsResponse;
import com.telappoint.adminresv.restws.model.SearchRequestData;
import com.telappoint.adminresv.restws.model.SeatsCalendarView;
import com.telappoint.adminresv.restws.model.SupportResponseTO;
import com.telappoint.adminresv.restws.model.TablePrintView;
import com.telappoint.adminresv.restws.model.TablePrintViewResponse;
import com.telappoint.adminresv.restws.model.TicketTO;
import com.telappoint.adminresv.restws.model.TimeListResponse;
import com.telappoint.adminresv.restws.model.TransStateResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;

/**
 * 
 * @author Murali G
 * 
 */

@Service
public class AdminResvServiceImpl implements IAdminResvService {
	
	private static final String SUCCESS = "success";
	
	private Logger logger = Logger.getLogger(AdminResvServiceImpl.class);
	
	//Common RestWS request related methods
	
	@Override
	public List<AdminInfoTO> getUserList(Integer clientId, String clientCode) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getUserList(clientId, clientCode);
		Gson gson = new GsonBuilder().create();
		String userDetails = gson.toJson(responseData.getData());
		List<AdminInfoTO> adminInfoTOs = gson.fromJson(userDetails, new TypeToken<ArrayList<AdminInfoTO>>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return adminInfoTOs;
	}
	
	@Override
	public String validateUserName(String username,String id) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().validateUserName(username,id);
		Gson gson = new GsonBuilder().create();
		String toJsonAdminInfoTO = gson.toJson(responseData.getData());
		String response = gson.fromJson(toJsonAdminInfoTO, new TypeToken<String>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return response;
	}
	
	@Override
	public boolean addUserDetails(AdminInfoTO adminInfoTO) {
		boolean isSaved = false;
		try {
			JsonDataHandler jsonDataHandler = AdminResvRestClient.getInstance().addUserDetals(adminInfoTO);
			if (SUCCESS.equals(jsonDataHandler.getDescription())) {
				isSaved = true;
			}
		} catch (Exception e) {
		}
		return isSaved;
	}
	
	@Override
	public AdminInfoTO getUserDetailsById(String id) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getUserDetailsById(id);
		Gson gson = new GsonBuilder().create();
		String toJsonAdminInfoTO = gson.toJson(responseData.getData());
		AdminInfoTO adminInfoTO = gson.fromJson(toJsonAdminInfoTO, new TypeToken<AdminInfoTO>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return adminInfoTO;
	}

	@Override
	public boolean updateUserDetails(AdminInfoTO adminInfoTO) {
		boolean isUpdated = false;
		try {
			JsonDataHandler jsonDataHandler = AdminResvRestClient.getInstance().updateUserDetails(adminInfoTO);
			if (SUCCESS.equals(jsonDataHandler.getDescription())) {
				isUpdated = true;
			}
		} catch (Exception e) {
		}
		return isUpdated;
	}
	
	@Override
	public boolean deleteUserDetails(String id) {
		boolean isDeleted = false;
		try {
			JsonDataHandler jsonDataHandler = AdminResvRestClient.getInstance().deleteUserDetails(id);
			if (SUCCESS.equals(jsonDataHandler.getDescription())) {
				isDeleted = true;
			}
		} catch (Exception e) {
		}
		return isDeleted;
	}
	
	@Override
	public AdminInfoTO getUserDetails(UserLoginTO loginForm) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getUserDetails(loginForm);
		Gson gson = new GsonBuilder().create();
		String jsonAdminInfoTO = gson.toJson(responseData.getData());
		logger.debug("getUserDetails ::: jsonAdminInfoTO ::: "+jsonAdminInfoTO);
		AdminInfoTO adminInfoTO = gson.fromJson(jsonAdminInfoTO, new TypeToken<AdminInfoTO>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return adminInfoTO;
	}
	
	@Override
	public String sendResetPasswordRequestToken(ResetPasswordTO resetPasswordTO) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().sendResetPasswordRequestToken(resetPasswordTO);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(responseData.getData());
		String response = gson.fromJson(jsonString, new TypeToken<String>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return response;
	}

	@Override
	public ResetPasswordTO resetPasswordRequest(String token) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().resetPasswordRequest(token);
		Gson gson = new GsonBuilder().create();
		String jsonResetPasswordTO = gson.toJson(responseData.getData());
		ResetPasswordTO resetPasswordTO = gson.fromJson(jsonResetPasswordTO, new TypeToken<ResetPasswordTO>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return resetPasswordTO;
	}

	@Override
	public String updatePassword(ResetPasswordTO resetPasswordTO) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().updatePassword(resetPasswordTO);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(responseData.getData());
		String response = gson.fromJson(jsonString, new TypeToken<String>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return response;
	}

	@Override
	public String updatechangepassword(ChangePasswordTO changePasswordTO) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().updatechangepassword(changePasswordTO);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(responseData.getData());
		String response = gson.fromJson(jsonString, new TypeToken<String>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return response;
	}

	@Override
	public String validateoldpassword(ChangePasswordTO changePasswordTO) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().validateoldpassword(changePasswordTO);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(responseData.getData());
		String response = gson.fromJson(jsonString, new TypeToken<String>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return response;
	}
	@Override
	public String updatePasswordChangedByAdmin(ResetPasswordTO resetPasswordTO) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().updatePasswordChangedByAdmin(resetPasswordTO);
		Gson gson = new GsonBuilder().create();
		String toJsonAdminInfoTO = gson.toJson(responseData.getData());
		String response = gson.fromJson(toJsonAdminInfoTO, new TypeToken<String>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return response;
	}
	
	//Reservation RestWS request related methods
	private BaseResponse getBaseResponse(RevJsonDataHandler responseData) {
		Gson gson = new GsonBuilder().create();
		String jsonBaseResponse = gson.toJson(responseData.getData());
		logger.debug("BaseResponse :: "+jsonBaseResponse);
		BaseResponse baseResponse = gson.fromJson(jsonBaseResponse, new TypeToken<BaseResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return baseResponse;
	}
	
	private void populateBaseRequestData(BaseRequest sourceBaseRequest,BaseRequest destObj) {
		destObj.setClientCode(sourceBaseRequest.getClientCode());
		destObj.setLangCode(sourceBaseRequest.getLangCode());
		destObj.setTransId(sourceBaseRequest.getTransId());
		destObj.setUserId(sourceBaseRequest.getUserId());
		destObj.setDevice(ResvDeskContants.DEVICE_TYPE_ADMIN.getValue());
	}
	
	//Home page related
	@Override
	public HomePageResponse getHomePageDetails(BaseRequest baseRequest,String privilegeName) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getHomePageDetails(baseRequest,privilegeName);
		Gson gson = new GsonBuilder().create();
		String jsonHomePageResponse = gson.toJson(responseData.getData());
		logger.debug("getHomePageDetails HomePageResponse :: "+jsonHomePageResponse);
		HomePageResponse homePageResponse = gson.fromJson(jsonHomePageResponse, new TypeToken<HomePageResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return homePageResponse;
	}
	
	@Override
	public BaseResponse changeSchedulerStatus(BaseRequest baseRequest,String schedulerStatus) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().changeSchedulerStatus(baseRequest,schedulerStatus);
		return getBaseResponse(responseData);
	}
	
	//Customer related	
	@Override
	public CustomerResponse getCustomerNames(BaseRequest baseRequest,String customerName) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getCustomerNames(baseRequest,customerName);
		Gson gson = new GsonBuilder().create();
		String jsonCustomerResponse = gson.toJson(responseData.getData());
		logger.debug("getCustomerNames CustomerResponse :: "+jsonCustomerResponse);
		CustomerResponse customerResponse = gson.fromJson(jsonCustomerResponse, new TypeToken<CustomerResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return customerResponse;
	}
	
	@Override
	public CustomerResponse getCustomerById(BaseRequest baseRequest,String customerId) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getCustomerById(baseRequest,customerId);
		Gson gson = new GsonBuilder().create();
		String jsonCustomerResponse = gson.toJson(responseData.getData());
		logger.debug("getCustomerById CustomerResponse :: "+jsonCustomerResponse);
		CustomerResponse customerResponse = gson.fromJson(jsonCustomerResponse, new TypeToken<CustomerResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return customerResponse;
	}
	
	@Override
	public LoginInfoResponse getLoginInfo(BaseRequest baseRequest) throws Exception {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getLoginInfo(baseRequest);
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.debug("getLoginInfo : response :  "+response);
		LoginInfoResponse loginInfoResponse = gson.fromJson(response, new TypeToken<LoginInfoResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return loginInfoResponse;
	}
	
	//TODO : No need we have to delete
	@Override
	public RegistrationInfoResponse getRegistrationInfo(BaseRequest baseRequest) throws Exception {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getRegistrationInfo(baseRequest);
		Gson gson = new GsonBuilder().create();
		String jsonRegInfoResponse = gson.toJson(responseData.getData());
		logger.debug("getRegistrationInfo : RegistrationInfoResponse :  "+jsonRegInfoResponse);
		RegistrationInfoResponse regInfoResponse = gson.fromJson(jsonRegInfoResponse, new TypeToken<RegistrationInfoResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return regInfoResponse;
	}
	
	@Override
	public FutureReservationResponse getFutureReservations(BaseRequest baseRequest,String customerId) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getFutureReservations(baseRequest,customerId);
		Gson gson = new GsonBuilder().create();
		String jsonFutureResvResponse = gson.toJson(responseData.getData());
		logger.debug("getFutureReservations : FutureReservationResponse :  "+jsonFutureResvResponse);
		FutureReservationResponse futureResvResponse = gson.fromJson(jsonFutureResvResponse, new TypeToken<FutureReservationResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return futureResvResponse;
	}
	
	@Override
	public PastReservationResponse getPastReservations(BaseRequest baseRequest,String customerId) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getPastReservations(baseRequest,customerId);
		Gson gson = new GsonBuilder().create();
		String jsonPastReservResponse = gson.toJson(responseData.getData());
		logger.debug("getPastReservations : PastReservationResponse :  "+jsonPastReservResponse);
		PastReservationResponse pastReservResponse = gson.fromJson(jsonPastReservResponse, new TypeToken<PastReservationResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return pastReservResponse;
	}
	
	@Override
	public AuthResponse authenticateCustomer(Customer customer,BaseRequest baseRequest,List<LoginField> loginFieldList) throws Exception {
		String loginParams = prepareLoginParamsData(loginFieldList,customer);
		System.out.println("loginParams :::::::::::::::::::::::::;  "+loginParams);
		JsonDataHandler responseData = AdminResvRestClient.getInstance().authenticateCustomer(baseRequest,loginParams);		
		Gson gson = new GsonBuilder().create();
		String jsonAuthResponse = gson.toJson(responseData.getData());
		logger.debug("authenticateCustomer : AuthResponse :  "+jsonAuthResponse);
		AuthResponse authResponse  = gson.fromJson(jsonAuthResponse, new TypeToken<AuthResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return authResponse;
	}
		
	private String prepareLoginParamsData(List<LoginField> loginFieldList,Customer customer) throws NoSuchFieldException {
		StringBuilder loginParams = new StringBuilder();
		for (LoginField loginField : loginFieldList) {
			String displayType = loginField.getDisplayType();
			if (displayType != null && !displayType.contains("button")) {
				Object fieldValue = null;
				if (loginField.getValidateRules() != null && (loginField.getValidateRules().contains("dob") 
						|| loginField.getValidateRules().contains("date"))) {					
					StringBuilder fieldVal = new StringBuilder();
					fieldVal.append((String)AdminUtils.getPropertyValue(customer,loginField.getJavaRef()+1));
					fieldVal.append("/");
					fieldVal.append((String)AdminUtils.getPropertyValue(customer,loginField.getJavaRef()+2));
					fieldVal.append("/");
					fieldVal.append((String)AdminUtils.getPropertyValue(customer,loginField.getJavaRef()+3));
					fieldValue = fieldVal.toString();
				}else{
					fieldValue = AdminUtils.getPropertyValue(customer,loginField.getJavaRef());
				}				
				loginParams.append((String)fieldValue).append("%7C");// '%7C'  represents '|'
			}
		}
		return loginParams.toString().substring(0,loginParams.toString().length()-3);
	}
	
	@Override
	public AuthResponse updateCustomer(Customer customer,BaseRequest baseRequest) throws Exception {
		populateBaseRequestData(baseRequest,customer);
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().updateCustomer(customer);		
		Gson gson = new GsonBuilder().create();
		String jsonAuthResponse = gson.toJson(responseData.getData());
		logger.debug("updateCustomer : AuthResponse :  "+jsonAuthResponse);
		AuthResponse authResponse  = gson.fromJson(jsonAuthResponse, new TypeToken<AuthResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return authResponse;
	}
	
	//TODO : No need we have to delete
	@Override
	public AuthResponse createOrUpdateCustomer(Customer customer,BaseRequest baseRequest) throws Exception {
		populateBaseRequestData(baseRequest,customer);
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().createOrUpdateCustomer(customer);		
		Gson gson = new GsonBuilder().create();
		String jsonAuthResponse = gson.toJson(responseData.getData());
		logger.debug("createOrUpdateCustomer : AuthResponse :  "+jsonAuthResponse);
		AuthResponse authResponse  = gson.fromJson(jsonAuthResponse, new TypeToken<AuthResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return authResponse;
	}
	
	@Override
	public HoldResvResponse holdReservation(BaseRequest baseRequest, ReservationBookingForm resvBookingForm,String customerId)throws Exception {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().holdReservation(baseRequest,resvBookingForm,customerId);	
		Gson gson = new GsonBuilder().create();
		String jsonHoldResvResponse = gson.toJson(responseData.getData());
		logger.debug("holdReservation : HoldResvResponse :  "+jsonHoldResvResponse);
		HoldResvResponse holdResvResponse  = gson.fromJson(jsonHoldResvResponse, new TypeToken<HoldResvResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return holdResvResponse;
	}
	
	@Override
	public ConfirmResvResponse confirmReservation(BaseRequest baseRequest, String scheduleId, String customerId, String comment) throws Exception {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().confirmReservation(baseRequest,scheduleId,customerId,comment);	
		Gson gson = new GsonBuilder().create();
		String jsonConfirmResvResponse = gson.toJson(responseData.getData());
		logger.debug("confirmReservation : ConfirmResvResponse :  "+jsonConfirmResvResponse);
		ConfirmResvResponse confirmResvResponse  = gson.fromJson(jsonConfirmResvResponse, new TypeToken<ConfirmResvResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return confirmResvResponse;
	}	
	
	@Override
	public BaseResponse cancelReservation(BaseRequest baseRequest, String scheduleId, String customerId) throws NoSuchFieldException {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().cancelReservation(baseRequest,scheduleId,customerId);	
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.debug("cancelReservation : response :  "+response);
		BaseResponse baseResponse  = gson.fromJson(response, new TypeToken<BaseResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return baseResponse;
	}	
	
	//Location related	
	@Override
	public LocationListResponse getLocationList(BaseRequest baseRequest) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getLocationList(baseRequest);
		Gson gson = new GsonBuilder().create();
		String jsonLocationListResponse = gson.toJson(responseData.getData());
		logger.debug("getLocationList LocationListResponse :: "+jsonLocationListResponse);
		LocationListResponse locationListResponse = gson.fromJson(jsonLocationListResponse, new TypeToken<LocationListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return locationListResponse;
	}
	@Override
	public String deleteLocation(BaseRequest baseRequest, String locationId) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().deleteLocation(baseRequest,locationId);
		Gson gson = new GsonBuilder().create();
		String jsonBaseResponse = gson.toJson(responseData.getData());
		logger.debug("deleteLocation	BaseResponse :: "+jsonBaseResponse);
		return jsonBaseResponse;
	}	
	@Override
	public BaseResponse openCloseLocation(BaseRequest baseRequest,String locationId, String enable) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().openCloseLocation(baseRequest,locationId,enable);
		return getBaseResponse(responseData);
	}	
	@Override
	public BaseResponse addLocation(BaseRequest baseRequest,Location location)throws Exception {
		populateBaseRequestData(baseRequest,location);
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().addLocation(location);
		return getBaseResponse(responseData);
	}
	@Override
	public LocationListResponse getLocationListDropDown(BaseRequest baseRequest) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getLocationListDropDown(baseRequest);
		Gson gson = new GsonBuilder().create();
		String jsonLocationListResponse = gson.toJson(responseData.getData());
		logger.debug("getLocationListDropDown LocationListResponse :: "+jsonLocationListResponse);
		LocationListResponse locationListResponse = gson.fromJson(jsonLocationListResponse, new TypeToken<LocationListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return locationListResponse;
	}
	
	@Override
	public LocationListResponse getLocationById(BaseRequest baseRequest,String locationId) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getLocationById(baseRequest,locationId);
		Gson gson = new GsonBuilder().create();
		String jsonLocationListResponse = gson.toJson(responseData.getData());
		logger.debug("getLocationById	LocationListResponse :: "+jsonLocationListResponse);
		LocationListResponse locationListResponse = gson.fromJson(jsonLocationListResponse, new TypeToken<LocationListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return locationListResponse;
	}	
	@Override
	public BaseResponse updateLocation(BaseRequest baseRequest,Location location)throws Exception {
		populateBaseRequestData(baseRequest,location);
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().updateLocation(location);
		return getBaseResponse(responseData);
	}
	
	//Event related
	@Override
	public EventListResponse getEventList(BaseRequest baseRequest) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getEventList(baseRequest);
		Gson gson = new GsonBuilder().create();
		String jsonEventListResponse = gson.toJson(responseData.getData());
		logger.debug("getEventList EventListResponse :: "+jsonEventListResponse);
		EventListResponse eventListResponse = gson.fromJson(jsonEventListResponse, new TypeToken<EventListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return eventListResponse;
	}
	
	@Override
	public String deleteEvent(BaseRequest baseRequest, String eventId) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().deleteEvent(baseRequest,eventId);
		Gson gson = new GsonBuilder().create();
		String jsonBaseResponse = gson.toJson(responseData.getData());
		logger.debug("deleteEvent	BaseResponse :: "+jsonBaseResponse);
		return jsonBaseResponse;
	}
	@Override
	public BaseResponse openCloseEvent(BaseRequest baseRequest,String eventId, String enable) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().openCloseEvent(baseRequest,eventId,enable);
		return getBaseResponse(responseData);

	}
	@Override
	public BaseResponse addEvent(BaseRequest baseRequest,Event event)throws Exception {
		populateBaseRequestData(baseRequest,event);
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().addEvent(event);
		return getBaseResponse(responseData);
	}
	
	@Override
	public EventListResponse getEventById(BaseRequest baseRequest,String eventId) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getEventById(baseRequest,eventId);
		Gson gson = new GsonBuilder().create();
		String jsonEventListResponse = gson.toJson(responseData.getData());
		logger.debug("getEventById EventListResponse :: "+jsonEventListResponse);
		EventListResponse eventListResponse = gson.fromJson(jsonEventListResponse, new TypeToken<EventListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return eventListResponse;
	}
	
	@Override
	public BaseResponse updateEvent(BaseRequest baseRequest,Event event)throws Exception {
		populateBaseRequestData(baseRequest,event);
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().updateEvent(event);
		return getBaseResponse(responseData);
	}
	
	@Override
	public EventListResponse getEventListByLocationId(BaseRequest baseRequest,String locationId) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getEventListByLocationId(baseRequest,locationId);
		Gson gson = new GsonBuilder().create();
		String jsonEventListResponse = gson.toJson(responseData.getData());
		logger.debug("getEventListByLocationId EventListResponse :: "+jsonEventListResponse);
		EventListResponse eventListResponse = gson.fromJson(jsonEventListResponse, new TypeToken<EventListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return eventListResponse;
	}
	
	//EventDateTime related
	@Override
	public EventDateTimeResponse getEventDateTimeList(BaseRequest baseRequest) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getEventDateTimeList(baseRequest);
		Gson gson = new GsonBuilder().create();
		String jsonEventDateTimeResponse = gson.toJson(responseData.getData());
		logger.debug("getEventDateTimeList EventDateTimeResponse :: "+jsonEventDateTimeResponse);
		EventDateTimeResponse eventDateTimeResponse = gson.fromJson(jsonEventDateTimeResponse, new TypeToken<EventDateTimeResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return eventDateTimeResponse;
	}
	@Override
	public BaseResponse openCloseEventDateTime(BaseRequest baseRequest,String eventDateTimeId, String enable) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().openCloseEventDateTime(baseRequest,eventDateTimeId,enable);
		return getBaseResponse(responseData);
	}
	@Override
	public BaseResponse addEventDateTime(BaseRequest baseRequest,EventDateTime eventDateTime)throws Exception {
		populateBaseRequestData(baseRequest,eventDateTime);
		eventDateTime.setTime(eventDateTime.getTimeHr()+":"+eventDateTime.getTimeMin()+" "+eventDateTime.getTimeAmPm());
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().addEventDateTime(eventDateTime);
		return getBaseResponse(responseData);
	}
	@Override
	public BaseResponse updateEventDateTime(BaseRequest baseRequest,EventDateTime eventDateTime)throws Exception {
		populateBaseRequestData(baseRequest,eventDateTime);
		eventDateTime.setTime(eventDateTime.getTimeHr()+":"+eventDateTime.getTimeMin()+" "+eventDateTime.getTimeAmPm());		
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().updateEventDateTime(eventDateTime);
		return getBaseResponse(responseData);
	}	
	@Override
	public EventDateTime getEventDateTimeById(BaseRequest baseRequest,String eventDateTimeById) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getEventDateTimeById(baseRequest,eventDateTimeById);
		Gson gson = new GsonBuilder().create();
		String jsonEventDateTime = gson.toJson(responseData.getData());
		logger.debug("getEventDateTimeById EventDateTime :: "+jsonEventDateTime);
		EventDateTime eventDateTime = gson.fromJson(jsonEventDateTime, new TypeToken<EventDateTime>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return eventDateTime;
	}
	@Override
	public BaseResponse updateEventSeats(BaseRequest baseRequest,EventDateTime eventDateTime)throws Exception {
		populateBaseRequestData(baseRequest,eventDateTime);
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().updateEventSeats(eventDateTime);
		return getBaseResponse(responseData);
	}	
		
	//Reservations > Reservation Overview
	@Override
	public CalendarOverviewResponse getReservationOverview(BaseRequest baseRequest,String startDate,String endDate) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getReservationOverview(baseRequest,startDate,endDate);
		Gson gson = new GsonBuilder().create();
		String jsonCalOverviewResponse = gson.toJson(responseData.getData());
		logger.debug("getReservationOverview CalendarOverviewResponse :: "+(jsonCalOverviewResponse !=null ? jsonCalOverviewResponse.trim() : jsonCalOverviewResponse));
		CalendarOverviewResponse calOverviewResponse = gson.fromJson(jsonCalOverviewResponse, new TypeToken<CalendarOverviewResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return calOverviewResponse;
	}
	
	@Override
	public CalendarOverviewDetailsResponse getReservationOverviewDetails(BaseRequest baseRequest,String eventDateTimeId) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getReservationOverviewDetails(baseRequest,eventDateTimeId);
		Gson gson = new GsonBuilder().create();
		String jsonOverviewDetailsRes = gson.toJson(responseData.getData());
		logger.debug("getReservationOverviewDetails CalendarOverviewDetailsResponse :: "+jsonOverviewDetailsRes);
		CalendarOverviewDetailsResponse overviewDetailsResponse = gson.fromJson(jsonOverviewDetailsRes, new TypeToken<CalendarOverviewDetailsResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return overviewDetailsResponse;
	}
	
	//Reservation > Calendar > 	
	
	@Override
	public DateJSListResponse getJSCalendarDateList(BaseRequest baseRequest,String locationId,String eventId) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getJSCalendarDateList(baseRequest,locationId,eventId);
		Gson gson = new GsonBuilder().create();
		String jsonDateListResponse = gson.toJson(responseData.getData());
		logger.debug("getJSCalendarDateList DateJSListResponse :: "+jsonDateListResponse);
		DateJSListResponse dateJSListResponse = gson.fromJson(jsonDateListResponse, new TypeToken<DateJSListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return dateJSListResponse;
	}
	
	@Override
	public DateListResponse getCalendarDateList(BaseRequest baseRequest,String locationId,String eventId) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getCalendarDateList(baseRequest,locationId,eventId);
		Gson gson = new GsonBuilder().create();
		String jsonDateListResponse = gson.toJson(responseData.getData());
		logger.debug("getCalendarDateList DateListResponse :: "+jsonDateListResponse);
		DateListResponse dateListResponse = gson.fromJson(jsonDateListResponse, new TypeToken<DateListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return dateListResponse;
	}
	
	@Override
	public TimeListResponse getSeatViewTimeList(BaseRequest baseRequest,String locationId,String eventId,String date) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getSeatViewTimeList(baseRequest,locationId,eventId,date);
		Gson gson = new GsonBuilder().create();
		String jsonTimeListResponse = gson.toJson(responseData.getData());
		logger.debug("getSeatViewTimeList TimeListResponse :: "+jsonTimeListResponse);
		TimeListResponse timeListResponse = gson.fromJson(jsonTimeListResponse, new TypeToken<TimeListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return timeListResponse;
	}
	
	//Seat View
	@Override
	public SeatsCalendarView getSeatsCalendarView(BaseRequest baseRequest,String locationId, String eventId, String date,String time,String showRemainderIcons) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getSeatsCalendarView(baseRequest,locationId,eventId,date,time,showRemainderIcons);
		Gson gson = new GsonBuilder().create();
		String jsonSeatsCalendarView = gson.toJson(responseData.getData());
		logger.debug("getSeatsCalendarView SeatsCalendarView :: "+jsonSeatsCalendarView);
		SeatsCalendarView seatsCalendarView = gson.fromJson(jsonSeatsCalendarView, new TypeToken<SeatsCalendarView>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return seatsCalendarView;
	}
	
	//Daily View
	@Override
	public DailyCalendarView getDailyCalendarView(BaseRequest baseRequest,String locationId,String eventId,String date,String showRemainderIcons) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getDailyCalendarView(baseRequest,locationId,eventId,date,showRemainderIcons);
		Gson gson = new GsonBuilder().create();
		String jsonDailyCalendarView = gson.toJson(responseData.getData());
		logger.debug("getDailyCalendarView DailyCalendarView :: "+jsonDailyCalendarView);
		DailyCalendarView dailyCalendarView = gson.fromJson(jsonDailyCalendarView, new TypeToken<DailyCalendarView>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return dailyCalendarView;
	}
	
	//Reservation Reports
	@Override
	public ReservationReportResponse getReservationReports(BaseRequest baseRequest,ReservationReportRequest resReportRequest) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getReservationReports(baseRequest,resReportRequest);
		Gson gson = new GsonBuilder().create();
		String jsonResReportResponse = gson.toJson(responseData.getData());
		logger.debug("getReservationReports ReservationReportResponse :: "+jsonResReportResponse);
		ReservationReportResponse resReportResponse = gson.fromJson(jsonResReportResponse, new TypeToken<ReservationReportResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return resReportResponse;
	}
	
	@Override
	public ReservationReportCheckboxResponse getEventDateTimeForLocEventDateRange(BaseRequest baseRequest,String locationId,String eventId,String startDate,String endDate) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getEventDateTimeForLocEventDateRange(baseRequest,locationId,eventId,startDate,endDate);
		Gson gson = new GsonBuilder().create();
		String jsonEventDateTimeResponse = gson.toJson(responseData.getData());
		logger.debug("getEventDateTimeForLocEventDateRange ReservationReportCheckboxResponse :: "+jsonEventDateTimeResponse);
		logger.error("getEventDateTimeForLocEventDateRange ReservationReportCheckboxResponse :: "+jsonEventDateTimeResponse);
		ReservationReportCheckboxResponse resvReportCheckboxResponse = gson.fromJson(jsonEventDateTimeResponse, new TypeToken<ReservationReportCheckboxResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return resvReportCheckboxResponse;
	}
	
	//Table Print view
	@Override
	public TablePrintViewResponse getTablePrintView(BaseRequest baseRequest,String locationId,String eventIds,String date) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getTablePrintView(baseRequest,locationId,eventIds,date);
		
		 GsonBuilder gb = new GsonBuilder();
	     gb.setPrettyPrinting();
	     gb.registerTypeAdapter(TablePrintView.class, new TablePrintViewSerializer());
	     Gson gson = gb.create();
	     	
		 String jsonTablePrintViewResponse = gson.toJson(responseData.getData());
		 logger.debug("getTablePrintView TablePrintViewResponse :: "+jsonTablePrintViewResponse);
		 TablePrintViewResponse tablePrintViewResponse = gson.fromJson(jsonTablePrintViewResponse, new TypeToken<TablePrintViewResponse>() {
			private static final long serialVersionUID = 1L;
		 }.getType());
		 
		 return tablePrintViewResponse;
	}
	
	//This is to avoid error when we are using Object as MAP key with JSON response
	static public class TablePrintViewSerializer implements JsonSerializer<TablePrintView>,
	    JsonDeserializer<TablePrintView> {
		@Override
		public TablePrintView deserialize(JsonElement je, Type t, JsonDeserializationContext ctx)throws JsonParseException {
			TablePrintView tablePrintView = new TablePrintView();
		
			if (je.isJsonObject()) {
				tablePrintView = new TablePrintView();				
			} else {
				tablePrintView = new GsonBuilder().create().fromJson(je.getAsString(), new TypeToken<TablePrintView>() {
					private static final long serialVersionUID = 1L;
				}.getType());
			}
			
			//logger.debug("deserialize returns: "+tablePrintView.toString());
			return tablePrintView;
		}
		
		@Override
		public JsonElement serialize(TablePrintView data, Type type, JsonSerializationContext jsonSerializationContext) {
			return  new JsonObject();
		}
	}
	
	//Reservation Search
	@Override
	public ReservationSearchResponse getReservationSearch(BaseRequest baseRequest,SearchRequestData resvSearchRequest) throws Exception{
		populateBaseRequestData(baseRequest,resvSearchRequest);
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getReservationSearch(resvSearchRequest);
		Gson gson = new GsonBuilder().create();
		String jsonReservationSearchResponse = gson.toJson(responseData.getData());
		logger.debug("getReservationSearch ReservationSearchResponse :: "+jsonReservationSearchResponse);
		ReservationSearchResponse resvSearchResponse = gson.fromJson(jsonReservationSearchResponse, new TypeToken<ReservationSearchResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return resvSearchResponse;
	}
	
	//Dynamic Search Fields
	@Override
	public SearchFieldsResponse getDynamicSearchFields(BaseRequest baseRequest,String searchCategory) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getDynamicSearchFields(baseRequest,searchCategory);
		Gson gson = new GsonBuilder().create();
		String jsonSearchFieldsResponse = gson.toJson(responseData.getData());
		logger.debug("getDynamicSearchFields SearchFieldsResponse :: "+jsonSearchFieldsResponse);
		SearchFieldsResponse searchFieldsResponse = gson.fromJson(jsonSearchFieldsResponse, new TypeToken<SearchFieldsResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return searchFieldsResponse;
	}
	
	//Client Details Search
	@Override
	public ClientDetailsResponse getClientDetails(BaseRequest baseRequest,SearchRequestData custDetailsSearchRequest) throws Exception{
		populateBaseRequestData(baseRequest,custDetailsSearchRequest);
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getClientDetails(custDetailsSearchRequest);
		Gson gson = new GsonBuilder().create();
		String jsonClientDetailsResponse = gson.toJson(responseData.getData());
		logger.debug("getClientDetails ClientDetailsResponse :: "+jsonClientDetailsResponse);
		ClientDetailsResponse clientDetailsResponse = gson.fromJson(jsonClientDetailsResponse, new TypeToken<ClientDetailsResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return clientDetailsResponse;
	}
	
	//Blocked Client Details Search
	@Override
	public ClientDetailsResponse getBlockedClientDetails(BaseRequest baseRequest,SearchRequestData custDetailsSearchRequest) throws Exception{
		populateBaseRequestData(baseRequest,custDetailsSearchRequest);
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getBlockedClientDetails(custDetailsSearchRequest);
		Gson gson = new GsonBuilder().create();
		String jsonClientDetailsResponse = gson.toJson(responseData.getData());
		logger.debug("getBlockedClientDetails ClientDetailsResponse :: "+jsonClientDetailsResponse);
		ClientDetailsResponse clientDetailsResponse = gson.fromJson(jsonClientDetailsResponse, new TypeToken<ClientDetailsResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return clientDetailsResponse;
	}
	
	//Graphs related
	@Override
	public String getGraphDetails(BaseRequest baseRequest,String locationId,String date) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getGraphDetails(baseRequest,locationId,date);
		Gson gson = new GsonBuilder().create();
		String jsonGraphResponse = gson.toJson(responseData.getData());
		logger.debug("getGraphDetails GraphResponse :: "+jsonGraphResponse);
		logger.debug("getGraphDetails GraphResponse :: "+jsonGraphResponse);
		/*GraphResponse graphResponse = gson.fromJson(jsonGraphResponse, new TypeToken<GraphResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());*/
		return jsonGraphResponse;
	}
	
	//Accesses Privileges
	@Override
	public PrivilageResponse getAccessesPrivileges(BaseRequest baseRequest) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getAccessesPrivileges(baseRequest);
		Gson gson = new GsonBuilder().create();
		String jsonPrivilageResponse = gson.toJson(responseData.getData());
		logger.debug("getAccessesPrivilages PrivilageResponse :: "+jsonPrivilageResponse);
		PrivilageResponse privilageResponse = gson.fromJson(jsonPrivilageResponse, new TypeToken<PrivilageResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return privilageResponse;
	}
	
	//Automatic Email Report configs
	@Override
	public ReservationReportConfigResponse getReservationReportConfig(BaseRequest baseRequest) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getReservationReportConfig(baseRequest);
		Gson gson = new GsonBuilder().create();
		String jsonReservationReportConfigResponse = gson.toJson(responseData.getData());
		logger.debug("getReservationReportConfig ReservationReportConfigResponse :: "+jsonReservationReportConfigResponse);
		ReservationReportConfigResponse reservationReportConfigResponse = gson.fromJson(jsonReservationReportConfigResponse, new TypeToken<ReservationReportConfigResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return reservationReportConfigResponse;
	}
	
	@Override
	public BaseResponse addReservationReportConfig(BaseRequest baseRequest,ReservationReportConfig resvReportConfig) throws Exception{
		populateBaseRequestData(baseRequest,resvReportConfig);
		logger.debug("UserName :::: "+baseRequest.getUserName());
		resvReportConfig.setUserName(baseRequest.getUserName());
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().addReservationReportConfig(resvReportConfig);
		return getBaseResponse(responseData);
	}
	
	@Override
	public BaseResponse deleteResvReportConfig(BaseRequest baseRequest, String reportConfigId) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().deleteResvReportConfig(baseRequest,reportConfigId);
		return getBaseResponse(responseData);
	}
	
	@Override
	public ReservationStatusResponse getReservationStatusList(BaseRequest baseRequest) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getReservationStatusList(baseRequest);
		Gson gson = new GsonBuilder().create();
		String jsonReservationStatusResponse = gson.toJson(responseData.getData());
		logger.debug("getReservationStatusList ReservationStatusResponse :: "+jsonReservationStatusResponse);
		ReservationStatusResponse resvStatusResponse = gson.fromJson(jsonReservationStatusResponse, new TypeToken<ReservationStatusResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return resvStatusResponse;
	}
	
	//Reservation Reminders
	@Override
	public ReservationRemindersResponse getReservationReminders(BaseRequest baseRequest,String locationId,String campaignId,String date) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getReservationReminders(baseRequest,locationId,campaignId,date);
		GsonBuilder gb = new GsonBuilder();
	    gb.setPrettyPrinting();
	    gb.registerTypeAdapter(DailyReminderView.class, new DailyReminderViewSerializer());
	    Gson gson = gb.create();
	    
		String jsonReservationRemindersResponse = gson.toJson(responseData.getData());
		logger.debug("getReservationReminders ReservationRemindersResponse :: "+jsonReservationRemindersResponse);
		ReservationRemindersResponse resvReminder = gson.fromJson(jsonReservationRemindersResponse, new TypeToken<ReservationRemindersResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return resvReminder;
	}
		
	//This is to avoid error when we are using Object as MAP key with JSON response
	static public class DailyReminderViewSerializer implements JsonSerializer<DailyReminderView>,JsonDeserializer<DailyReminderView> {
		@Override
		public DailyReminderView deserialize(JsonElement je, Type t, JsonDeserializationContext ctx)throws JsonParseException {
			DailyReminderView dailyReminderView = new DailyReminderView();
		
			if (je.isJsonObject()) {
				dailyReminderView = new DailyReminderView();				
			} else {
				dailyReminderView = new GsonBuilder().create().fromJson(je.getAsString(), new TypeToken<DailyReminderView>() {
					private static final long serialVersionUID = 1L;
				}.getType());
			}
			
			//logger.debug("DailyReminderView :::: deserialize returns: "+dailyReminderView.toString());
			return dailyReminderView;
		}
		
		@Override
		public JsonElement serialize(DailyReminderView data, Type type, JsonSerializationContext jsonSerializationContext) {
			return  new JsonObject();
		}
	}
		
	
	@Override
	public CampaignResponse getCampaigns(BaseRequest baseRequest) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getCampaigns(baseRequest);
		Gson gson = new GsonBuilder().create();
		String jsonCampaignResponse = gson.toJson(responseData.getData());
		logger.debug("getCampaigns CampaignResponse :: "+jsonCampaignResponse);
		CampaignResponse campaignResponse = gson.fromJson(jsonCampaignResponse, new TypeToken<CampaignResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return campaignResponse;
	}
	
	@Override
	public ReminderStatusResponse getNotifyRemainderStatusList(BaseRequest baseRequest) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getNotifyRemainderStatusList(baseRequest);
		Gson gson = new GsonBuilder().create();
		String jsonReminderStatusResponse = gson.toJson(responseData.getData());
		logger.debug("getNotifyRemainderStatusList ReminderStatusResponse :: "+jsonReminderStatusResponse);
		ReminderStatusResponse reminderStatusResponse = gson.fromJson(jsonReminderStatusResponse, new TypeToken<ReminderStatusResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return reminderStatusResponse;
	}
	
	@Override
	public Notification getNotificationById(BaseRequest baseRequest,String notifyId) throws Exception {
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getNotificationById(baseRequest,notifyId);
		Gson gson = new GsonBuilder().create();
		String jsonNotification = gson.toJson(responseData.getData());
		logger.debug("getNotificationById	Notification :: "+jsonNotification);
		Notification notification = gson.fromJson(jsonNotification, new TypeToken<Notification>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return notification;
	}
	
	@Override
	public BaseResponse updateNotifyStatus(BaseRequest baseRequest,NotifyRequest notifyRequest)throws Exception {
		populateBaseRequestData(baseRequest,notifyRequest);
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().updateNotifyStatus(notifyRequest);
		return getBaseResponse(responseData);
	}
	
	//Support Ticket related	
	@Override
	public SupportResponseTO getSupportResponse(String clientCode) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getSupportResponse(clientCode);
		Gson gson = new GsonBuilder().create();
		String data = gson.toJson(responseData.getData());
		logger.debug("getSupportResponse	JSON :: "+data);
		SupportResponseTO supportResponse =  gson.fromJson(data, new TypeToken<SupportResponseTO>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return supportResponse;
	}
	
	@Override
	public Map<Integer, String> getSupportTypes() throws Exception {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getSupportTypes();
		Gson gson = new GsonBuilder().create();
		String data = gson.toJson(responseData.getData());
		logger.debug("getSupportTypes	JSON :: "+data);
		Map<Integer, String> supportTypes = gson.fromJson(data, new TypeToken<LinkedHashMap<Integer, String>>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return supportTypes;
	}
	
	@Override
	public Map<Integer, String> getSupportStatus() throws Exception {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getSupportTypes();
		Gson gson = new GsonBuilder().create();
		String data = gson.toJson(responseData.getData());
		logger.debug("getSupportStatus JSON :: "+data);
		Map<Integer, String> supportStatus = gson.fromJson(data, new TypeToken<LinkedHashMap<Integer, String>>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return supportStatus;
	}
	
	@Override
	public TicketTO addTicket(TicketTO ticketTO) throws Exception {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().addTicket(ticketTO);
		Gson gson = new GsonBuilder().create();
		String data = gson.toJson(responseData.getData());
		logger.debug("addTicket JSON :: "+data);
		ticketTO = gson.fromJson(data, new TypeToken<TicketTO>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return ticketTO;
	}
	
	@Override
	public TicketTO getTicketById(String ticketId) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getTicketById(ticketId);
		Gson gson = new GsonBuilder().create();
		String data = gson.toJson(responseData.getData());
		logger.debug("getTicketById JSON :: "+data);
		TicketTO ticketTO = gson.fromJson(data, new TypeToken<TicketTO>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return ticketTO;
	}
	
	@Override
	public boolean updateTicket(TicketTO ticketTO) throws Exception {
		boolean isSaved = false;
		JsonDataHandler jsonDataHandler = AdminResvRestClient.getInstance().updateTicket(ticketTO);
		if (SUCCESS.equals(jsonDataHandler.getDescription())) {
			isSaved = true;
		}
		logger.debug("updateTicket isSaved :: "+isSaved);
		return isSaved;
	}
	
	@Override
	public boolean deleteTicket(String ticketId) {
		boolean isDeleted = false;
		JsonDataHandler jsonDataHandler = AdminResvRestClient.getInstance().deleteTicket(ticketId);
		if (SUCCESS.equals(jsonDataHandler.getDescription())) {
			isDeleted = true;
		}
		logger.debug("deleteTicket isDeleted :: "+isDeleted);
		return isDeleted;
	}
	
	@Override
	public boolean isAnyTicketWaitingForClientResponse(String clientCode,String userName) {
		boolean isAnyTicketWaiting = false;
		JsonDataHandler jsonDataHandler = AdminResvRestClient.getInstance().isAnyTicketWaitingForClientResponse(clientCode,userName);
		if (SUCCESS.equals(jsonDataHandler.getDescription())) {
			isAnyTicketWaiting = true;
		}
		logger.debug("isAnyTicketWaitingForClientResponse	isAnyTicketWaiting :: "+isAnyTicketWaiting);
		return isAnyTicketWaiting;
	}
	
	//Call Report functionality
	@Override
	public InBoundCallReportResponse getInBoundCallReportList(CallReportRequestTO callReportRequestTO,BaseRequest baseRequest) {
		CallReportRequest callReportRequest = new CallReportRequest();
		callReportRequest.setFromDate(callReportRequestTO.getInBoundPeriodFrom());
		callReportRequest.setToDate(callReportRequestTO.getInBoundPeriodTo());
		callReportRequest.setCallerId(callReportRequestTO.getInBoundCallerId());
		populateBaseRequestData(baseRequest,callReportRequest);
		
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getInBoundCallLogs(callReportRequest);
		Gson gson = new GsonBuilder().create();
		String ivrLogs = gson.toJson(responseData.getData());
		logger.debug("getInBoundCallReportList InBoundCallReportResponse :: "+ivrLogs);
		InBoundCallReportResponse inBoundCallsResponse = gson.fromJson(ivrLogs, new TypeToken<InBoundCallReportResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return inBoundCallsResponse;
	}
	
	@Override
	public OutBoundCallReportResponse getOutBoundCallReportList(CallReportRequestTO callReportRequestTO,BaseRequest baseRequest) {
		CallReportRequest callReportRequest = new CallReportRequest();
		callReportRequest.setFromDate(callReportRequestTO.getOutBoundPeriodFrom());
		callReportRequest.setToDate(callReportRequestTO.getOutBoundPeriodTo());
		callReportRequest.setCallerId(callReportRequestTO.getOutBoundCallerId());
		populateBaseRequestData(baseRequest,callReportRequest);
		
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().getOutBoundCallLogs(callReportRequest);
		Gson gson = new GsonBuilder().create();
		String logs = gson.toJson(responseData.getData());
		logger.debug("getOutBoundCallReportList OutBoundCallReportResponse :: "+logs);
		OutBoundCallReportResponse outBoundCallsResponse = gson.fromJson(logs, new TypeToken<OutBoundCallReportResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return outBoundCallsResponse;
	}
		
	@Override
	public TransStateResponse getTransStates(String transId,BaseRequest baseRequest) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getTransStates(transId,baseRequest) ;
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(responseData.getData());
		logger.debug("getTransStates	TransStateResponse :: "+json);
		TransStateResponse transStateResponse = gson.fromJson(json, new TypeToken<TransStateResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return transStateResponse;
	}	
	
	//Privilege Settings related
	@Override
	public PrivilegeSettings getPrivilegeSettingsResponse(String access_privilege_id,String access_privilege_name,String includeSuperAccessPrivilage,BaseRequest baseRequest)throws Exception {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getPrivilegeSettingsResponse(access_privilege_id,access_privilege_name,includeSuperAccessPrivilage,baseRequest);
		Gson gson = new GsonBuilder().create();
		String data = gson.toJson(responseData.getData());
		logger.debug("getPrivilegeSettingsResponse PrivilegeSettings :: "+data);
		PrivilegeSettings privilegeSettings = gson.fromJson(data, new TypeToken<PrivilegeSettings>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return privilegeSettings;
	}
	
	@Override
	public boolean updatePrivilegeSettings(PrivilegeSettings privilegeSettings,BaseRequest baseRequest) {
		populateBaseRequestData(baseRequest,privilegeSettings);
		JsonDataHandler jsonDataHandler = AdminResvRestClient.getInstance().updatePrivilegeSettings(privilegeSettings);
		if (SUCCESS.equals(jsonDataHandler.getDescription())) {
			return true;
		}
		return false;
	}
	
	@Override
	public EmailTemplatesTO getEmailTemplatesText(String mailLangcode,String messageKey,BaseRequest baseRequest) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getEmailTemplatesText(mailLangcode,messageKey,baseRequest);		
		Gson gson = new GsonBuilder().create();
		String data = gson.toJson(responseData.getData());
		EmailTemplatesTO emailTemplatesTO = gson.fromJson(data, new TypeToken<EmailTemplatesTO>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		
		return emailTemplatesTO;
	}
	
	@Override
	public boolean saveEmailTemplatesText(EmailTemplatesTO emailTemplatesTO,BaseRequest baseRequest) throws Exception {
		populateBaseRequestData(baseRequest,emailTemplatesTO);
		JsonDataHandler jsonDataHandler =  AdminResvRestClient.getInstance().saveEmailTemplatesText(emailTemplatesTO);
		if (SUCCESS.equals(jsonDataHandler.getDescription())) {
			return true;
		}
		return false;
	}
	
	@Override
	public LandingPageContentTO getLandingPageText(String landingPageLangcode,BaseRequest baseRequest) {
		JsonDataHandler responseData = AdminResvRestClient.getInstance().getLandingPageText(landingPageLangcode,baseRequest);		
		Gson gson = new GsonBuilder().create();
		String data = gson.toJson(responseData.getData());
		LandingPageContentTO landingPageContent = gson.fromJson(data, new TypeToken<LandingPageContentTO>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		
		return landingPageContent;
	}
	
	@Override
	public boolean saveLandingPageText(LandingPageContentTO landingPageContent,BaseRequest baseRequest) throws Exception {
		populateBaseRequestData(baseRequest,landingPageContent);
		JsonDataHandler jsonDataHandler = AdminResvRestClient.getInstance().saveLandingPageText(landingPageContent);
		if (SUCCESS.equals(jsonDataHandler.getDescription())) {
			return true;
		}
		return false;
	}
	
	@Override
	public BaseResponse updateScreenedStatus(BaseRequest baseRequest,String sceduleId,String screened) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().updateScreenedStatus(baseRequest,sceduleId,screened);
		return getBaseResponse(responseData);
	}
	
	@Override
	public String updateSeatReservedStatus(BaseRequest baseRequest,String seatId,String reserved) throws Exception{
		RevJsonDataHandler responseData = AdminResvRestClient.getInstance().updateSeatReservedStatus(baseRequest,seatId,reserved);
		Gson gson = new GsonBuilder().create();
		String jsonBaseResponse = gson.toJson(responseData.getData());
		logger.debug("BaseResponse :: "+jsonBaseResponse);
		return jsonBaseResponse;
	}
}
