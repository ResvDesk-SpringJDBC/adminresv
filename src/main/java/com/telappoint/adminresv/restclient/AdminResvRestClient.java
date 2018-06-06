package com.telappoint.adminresv.restclient;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.telappoint.adminresv.client.contants.AdminResvDeskRestConstants;
import com.telappoint.adminresv.client.contants.ResvDeskContants;
import com.telappoint.adminresv.client.json.JsonDataHandler;
import com.telappoint.adminresv.client.json.RevJsonDataHandler;
import com.telappoint.adminresv.form.AdminInfoTO;
import com.telappoint.adminresv.form.BaseRequest;
import com.telappoint.adminresv.form.ReservationBookingForm;
import com.telappoint.adminresv.form.ReservationReportRequest;
import com.telappoint.adminresv.form.bean.ChangePasswordTO;
import com.telappoint.adminresv.form.bean.ResetPasswordTO;
import com.telappoint.adminresv.form.bean.UserLoginTO;
import com.telappoint.adminresv.restws.model.CallReportRequest;
import com.telappoint.adminresv.restws.model.Customer;
import com.telappoint.adminresv.restws.model.EmailTemplatesTO;
import com.telappoint.adminresv.restws.model.Event;
import com.telappoint.adminresv.restws.model.EventDateTime;
import com.telappoint.adminresv.restws.model.LandingPageContentTO;
import com.telappoint.adminresv.restws.model.Location;
import com.telappoint.adminresv.restws.model.NotifyRequest;
import com.telappoint.adminresv.restws.model.PrivilegeSettings;
import com.telappoint.adminresv.restws.model.ReservationReportConfig;
import com.telappoint.adminresv.restws.model.SearchRequestData;
import com.telappoint.adminresv.restws.model.TicketTO;
import com.telappoint.adminresv.utils.PropertyUtils;

/**
 * 
 * @author Murali G
 * 
 */
public class AdminResvRestClient {
	
	private static Client client;

	private static volatile AdminResvRestClient instance;
	
	private Logger logger = Logger.getLogger(AdminResvRestClient.class);

	private AdminResvRestClient() {
		getApptdeskClient();
	}

	public static AdminResvRestClient getInstance() {
		if (instance == null) {
			synchronized (AdminResvRestClient.class) {
				if (instance == null)
					instance = new AdminResvRestClient();
			}
		}
		return instance;
	}

	public static Client getApptdeskClient() {
		ClientConfig config = new DefaultClientConfig();
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(config);
		return client;
	}
	
	//Common RestWS request related methods
	public JsonDataHandler getUserDetails(UserLoginTO loginForm) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()+ AdminResvDeskRestConstants.COMMON_REST_SERVICE_AUTHENTICATE_LOGIN.getValue();
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(JsonDataHandler.class, loginForm);
	}
	
	public JsonDataHandler sendResetPasswordRequestToken(ResetPasswordTO resetPasswordTO) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()	+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_REST_PASSWORD_REQUEST_TOKEN.getValue();
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(JsonDataHandler.class, resetPasswordTO);
	}

	public JsonDataHandler resetPasswordRequest(String token) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL() + AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_REST_PASSWORD_REQUEST.getValue();
		endPointUrl = endPointUrl.replaceAll("@tokenParam@", token);

		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}

	public JsonDataHandler updatePassword(ResetPasswordTO resetPasswordTO) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL() + AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_UPDATE_PASSWORD.getValue();
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(JsonDataHandler.class, resetPasswordTO);
	}

	public JsonDataHandler updatechangepassword(ChangePasswordTO changePasswordTO) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL() + AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_UPDATE_CHANGE_PASSWORD.getValue();
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(JsonDataHandler.class, changePasswordTO);
	}

	public JsonDataHandler validateoldpassword(ChangePasswordTO changePasswordTO) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL() + AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_VALIDATE_OLD_PASSWORD.getValue();
	
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(JsonDataHandler.class, changePasswordTO);
	}
	
	public JsonDataHandler getUserList(Integer clientId,String clientCode) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()
				+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_GET_USER_LIST.getValue()
				+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_CLIENT_ID_AND_CLIENT_CODE.getValue();
		endPointUrl = endPointUrl.replaceAll("@clientIdParam@", String.valueOf(clientId));
		endPointUrl = endPointUrl.replaceAll("@clientCodeParam@", clientCode);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler validateUserName(String username,String id){
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()
				+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_VALIDATE_USERNAME.getValue();
		endPointUrl = endPointUrl.replaceAll("@usernameParam@", username);
		endPointUrl = endPointUrl.replaceAll("@idParam@", id);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler addUserDetals(AdminInfoTO adminInfoTO) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()	
				+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_ADD_USER_DETAILS.getValue();
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(JsonDataHandler.class, adminInfoTO);
	}
	
	public JsonDataHandler getUserDetailsById(String id) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()
				+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_GET_USER_DETAILS_BY_ID.getValue()+ id;
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}

	public JsonDataHandler updateUserDetails(AdminInfoTO adminInfoTO) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()
				+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_UPDATE_USER_DETAILS.getValue();
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(JsonDataHandler.class, adminInfoTO);
	}
	public JsonDataHandler deleteUserDetails(String id) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()
				+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_DELETE_USER_DETAILS.getValue()+ id;
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler updatePasswordChangedByAdmin(ResetPasswordTO resetPasswordTO){
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()
				+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_UPDATE_PASSWORD_CHANGED_BY_ADMIN.getValue();
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(JsonDataHandler.class, resetPasswordTO);
	}
	
	//--------------------------------------->  Reservation RestWS request related methods   <--------------------------------------------
	//Reservation RestWS request related methods	
	private String prepareReuestURL(String reuestURL,String parametersURL,BaseRequest baseRequest) {
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		if(reuestURL!=null && !"".equals(reuestURL)){
			endPointUrl = endPointUrl+ reuestURL;
		}
		endPointUrl = endPointUrl+ AdminResvDeskRestConstants.REST_SERVICE_BASIC_REQUEST_PARAMETERS.getValue();
		
		if(parametersURL!=null && !"".equals(parametersURL)){
			endPointUrl = endPointUrl+ parametersURL;
		}
		
		endPointUrl = endPointUrl.replaceAll("@clientCodeParam@",baseRequest.getClientCode());
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@",baseRequest.getLangCode());
		endPointUrl = endPointUrl.replaceAll("@deviceParam@",ResvDeskContants.DEVICE_TYPE_ADMIN.getValue());
		endPointUrl = endPointUrl.replaceAll("@tokenParam@",baseRequest.getToken());
		endPointUrl = endPointUrl.replaceAll("@transIdParam@",baseRequest.getTransId());
		endPointUrl = endPointUrl.replaceAll("@userIdParam@",String.valueOf(baseRequest.getUserId()));

		return endPointUrl;
	}
	
	private String prepareOnlineReuestURL(BaseRequest baseRequest,String reuestURL,String parametersURL) {
		String endPointUrl = PropertyUtils.getRESVDESKRestWSOnlineEndPointURL();
		if(reuestURL!=null && !"".equals(reuestURL)){
			endPointUrl = endPointUrl+ reuestURL;
		}
		endPointUrl = endPointUrl+ AdminResvDeskRestConstants.ONLINE_RESVDESK_REST_SERVICE_BASIC_REQUEST_PARAMETERS.getValue();
		
		if(parametersURL!=null && !"".equals(parametersURL)){
			endPointUrl = endPointUrl+ parametersURL;
		}
		endPointUrl = endPointUrl.replaceAll("@clientCodeParam@",baseRequest.getClientCode());
		endPointUrl = endPointUrl.replaceAll("@deviceParam@",ResvDeskContants.DEVICE_TYPE_ADMIN.getValue());
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@",baseRequest.getLangCode());
		endPointUrl = endPointUrl.replaceAll("@tokenParam@", baseRequest.getTransId());	//TODO : In this case what we have to pass
		endPointUrl = endPointUrl.replaceAll("@transIdParam@", baseRequest.getTransId());
		return endPointUrl;
	}
	
	//Home Page related	
	public RevJsonDataHandler getHomePageDetails(BaseRequest baseRequest,String privilegeName){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_HOME_PAGE_DETAILS_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_ACESSES_PRIVILEGE_NAME_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@privilegeNameParam@", privilegeName);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("getHomePageDetails endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler changeSchedulerStatus(BaseRequest baseRequest,String schedulerStatus){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_CHANGE_SCHEDULAR_STATUS_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_STATUS_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@statusParam@", schedulerStatus);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");		
		logger.debug("changeSchedulerStatus endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	//Customer related	
	public RevJsonDataHandler getCustomerNames(BaseRequest baseRequest,String customerName){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_CUSTOMER_NAMES_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_ACESSES_CUSTOMER_NAME_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@customerNameParam@", customerName);
		logger.debug("getCustomerNames endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	public RevJsonDataHandler getCustomerById(BaseRequest baseRequest,String customerId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_CUSTOMER_BY_ID_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_CUSTOMER_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@", customerId);
		logger.debug("getCustomerById endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public JsonDataHandler getLoginInfo(BaseRequest baseRequest) {
		String endPointUrl = prepareOnlineReuestURL(baseRequest,AdminResvDeskRestConstants.ONLINE_RESVDESK_REST_SERVICE_GET_LOGIN_FIELDS_LIST.getValue(),null);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.debug("getLoginInfo : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	//TODO : No need we have to delete
	public JsonDataHandler getRegistrationInfo(BaseRequest baseRequest) {
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_CUST_REG_DETAILS.getValue(),null,baseRequest);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.debug("getRegistrationInfo : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public RevJsonDataHandler getFutureReservations(BaseRequest baseRequest,String customerId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_FUTURE_RESERVATIONS_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_CUSTOMER_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@", customerId);
		logger.debug("getFutureReservations endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler getPastReservations(BaseRequest baseRequest,String customerId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_PAST_RESERVATIONS_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_CUSTOMER_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@", customerId);
		logger.debug("getPastReservations endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public JsonDataHandler authenticateCustomer(BaseRequest baseRequest,String loginParams) {
		String endPointUrl = prepareOnlineReuestURL(baseRequest,AdminResvDeskRestConstants.ONLINE_RESVDESK_REST_SERVICE_AUTH_CUSTOMER.getValue(),
				AdminResvDeskRestConstants.ONLINE_RESVDESK_REST_SERVICE_LOGIN_PARAMS_REQUEST_PARAMETERS.getValue());
		
		endPointUrl = endPointUrl.replaceAll("@loginParamsParam@",loginParams);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.debug("authenticateCustomer : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public RevJsonDataHandler updateCustomer(Customer customer){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_UPDATE_CUSTOMER.getValue();
		logger.debug("updateCustomer endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class,customer);
	}
	
	//TODO : No need we have to delete
	public RevJsonDataHandler createOrUpdateCustomer(Customer customer){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_CREATE_OR_UPDATE_CUSTOMER.getValue();
		logger.debug("createOrUpdateCustomer endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class,customer);
	}
	
	public JsonDataHandler holdReservation(BaseRequest baseRequest, ReservationBookingForm resvBookingForm,String customerId){
		String endPointUrl = prepareOnlineReuestURL(baseRequest,AdminResvDeskRestConstants.ONLINE_RESVDESK_REST_SERVICE_HOLD_RESERVATION.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_COMPANY_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_PROCEDURE_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_DEPARTMENT_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_SEAT_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_DATE_TIME_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_CUSTOMER_ID_REQUEST_PARAMETERS.getValue());
		
		endPointUrl = endPointUrl.replaceAll("@companyIdParam@",resvBookingForm.getCompanyId());
		endPointUrl = endPointUrl.replaceAll("@procedureIdParam@",resvBookingForm.getProcedureId());		
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",resvBookingForm.getLocationId());
		endPointUrl = endPointUrl.replaceAll("@departmentIdParam@",resvBookingForm.getDepartmentId());		
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@",resvBookingForm.getEventId());
		endPointUrl = endPointUrl.replaceAll("@seatIdParam@",resvBookingForm.getSeatId());		
		endPointUrl = endPointUrl.replaceAll("@eventDateTimeIdParam@",resvBookingForm.getEventDateTimeId());
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.debug("holdReservation : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler confirmReservation(BaseRequest baseRequest, String scheduleId, String customerId, String comment) {
		String endPointUrl = prepareOnlineReuestURL(baseRequest,AdminResvDeskRestConstants.ONLINE_RESVDESK_REST_SERVICE_CONFIRM_RESERVATION.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_SCHEDULE_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_CUSTOMER_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_COMMENT_REQUEST_PARAMETERS.getValue());
		
		endPointUrl = endPointUrl.replaceAll("@scheduleIdParam@",scheduleId);
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		endPointUrl = endPointUrl.replaceAll("@commentParam@",(comment!=null ? comment : ""));
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.debug("confirmReservation : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler cancelReservation(BaseRequest baseRequest, String scheduleId, String customerId) {
		String endPointUrl = prepareOnlineReuestURL(baseRequest,AdminResvDeskRestConstants.ONLINE_RESVDESK_REST_SERVICE_CANCEL_RESERVATION.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_SCHEDULE_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_CUSTOMER_ID_REQUEST_PARAMETERS.getValue());
		
		endPointUrl = endPointUrl.replaceAll("@scheduleIdParam@",scheduleId);
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.debug("confirmReservation : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	//Location related	
	public RevJsonDataHandler getLocationList(BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_LOCATION_LIST_REQUEST_URL.getValue(),
				null,baseRequest);
		logger.debug("getLocationList endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	public RevJsonDataHandler deleteLocation(BaseRequest baseRequest, String locationId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_DELETE_LOCATION_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@", locationId);
		logger.debug("deleteLocation endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler openCloseLocation(BaseRequest baseRequest,String locationId, String enable){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_OPEN_CLOSE_LOCATION_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()+AdminResvDeskRestConstants.REST_SERVICE_OPEN_CLOSE_LOCATION_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@", locationId);
		endPointUrl = endPointUrl.replaceAll("@openClosedFlagParam@", enable);
		logger.debug("openCloseLocation endPointUrl :: "+endPointUrl);

		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler addLocation(Location location){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_ADD_LOCATION_REQUEST_URL.getValue();		
		logger.debug("addLocation endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class, location);
	}
	
	public RevJsonDataHandler getLocationListDropDown(BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_LOCATION_DROP_DOWN_REQUEST_URL.getValue(),
				null,baseRequest);
		logger.debug("getLocationListDropDown endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler getLocationById(BaseRequest baseRequest,String locationId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_LOCATION_BY_ID_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@", locationId);
		
		logger.debug("getLocationById endPointUrl :: "+endPointUrl);

		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler updateLocation(Location location){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_UPDATE_LOCATION_REQUEST_URL.getValue();		
		
		logger.debug("updateLocation endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class, location);
	}
	
	//Event related	
	public RevJsonDataHandler getEventList(BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_EVENT_LIST_REQUEST_URL.getValue(),
				null,baseRequest);
		logger.debug("getEventList endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler deleteEvent(BaseRequest baseRequest, String eventId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_DELETE_EVENT_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@", eventId);
		logger.debug("deleteEvent endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler openCloseEvent(BaseRequest baseRequest,String eventId, String enable){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_OPEN_CLOSE_EVENT_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue()+AdminResvDeskRestConstants.REST_SERVICE_OPEN_CLOSE_LOCATION_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@", eventId);
		endPointUrl = endPointUrl.replaceAll("@openClosedFlagParam@", enable);
		logger.debug("openCloseEvent endPointUrl :: "+endPointUrl);

		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	public RevJsonDataHandler addEvent(Event event){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_ADD_EVENT_REQUEST_URL.getValue();
		logger.debug("addEvent endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class, event);
	}
	public RevJsonDataHandler updateEvent(Event event){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_UPDATE_EVENT_REQUEST_URL.getValue();
		logger.debug("updateEvent endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class, event);
	}
	public RevJsonDataHandler getEventListByLocationId(BaseRequest baseRequest,String locationId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_EVENT_LIST_BY_LOC_ID_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@", locationId);
		logger.debug("getEventListByLocationId endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler getEventById(BaseRequest baseRequest,String eventId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_EVENT_BY_ID_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@",eventId);
		logger.debug("getEventById endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	//EventDateTime related	
	public RevJsonDataHandler getEventDateTimeList(BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_EVENT_DATE_TIME_LIST_REQUEST_URL.getValue(),
				null,baseRequest);
		logger.debug("getEventDateTimeList endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	public RevJsonDataHandler openCloseEventDateTime(BaseRequest baseRequest,String eventDateTimeId, String enable){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_OPEN_CLOSE_EVENT_DATE_TIME_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_DATE_TIME_ID_REQUEST_PARAMETERS.getValue()+AdminResvDeskRestConstants.REST_SERVICE_OPEN_CLOSE_LOCATION_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@eventDateTimeIdParam@", eventDateTimeId);
		endPointUrl = endPointUrl.replaceAll("@openClosedFlagParam@", enable);
		logger.debug("openCloseEventDateTime endPointUrl :: "+endPointUrl);

		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	public RevJsonDataHandler addEventDateTime(EventDateTime eventDateTime){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_ADD_EVENT_DATE_TIME_REQUEST_URL.getValue();		
		logger.debug("addEventDateTime endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class,eventDateTime);
	}
	public RevJsonDataHandler updateEventDateTime(EventDateTime eventDateTime){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_UPDATE_EVENT_DATE_TIME_REQUEST_URL.getValue();
		logger.debug("updateEventDateTime endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class, eventDateTime);
	}
	public RevJsonDataHandler getEventDateTimeById(BaseRequest baseRequest,String eventDateTimeById){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_EVENT_DATE_TIME_BY_ID_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_DATE_TIME_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@eventDateTimeIdParam@",eventDateTimeById);
		logger.debug("getEventDateTimeById endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	public RevJsonDataHandler updateEventSeats(EventDateTime eventDateTime){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_UPDATE_EVENT_SEATS_REQUEST_URL.getValue();
		logger.debug("updateEventSeats endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class, eventDateTime);
	}
	
	//Reservations > Reservation Overview
	public RevJsonDataHandler getReservationOverview(BaseRequest baseRequest,String startDate,String endDate){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_CALENDAR_OVER_VIEW_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_START_DATE_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_END_DATE_REQUEST_PARAMETERS.getValue(),baseRequest);
			
		endPointUrl = endPointUrl.replaceAll("@startDateParam@",startDate);
		endPointUrl = endPointUrl.replaceAll("@endDateParam@",endDate);
		logger.debug("getReservationOverview endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	public RevJsonDataHandler getReservationOverviewDetails(BaseRequest baseRequest,String eventDateTimeId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_CALENDAR_OVER_VIEW_DETAILS_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_DATE_TIME_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@eventDateTimeIdParam@", eventDateTimeId);
		logger.debug("getReservationOverviewDetails endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	//Reservation > Calendar > 	
	public RevJsonDataHandler getJSCalendarDateList(BaseRequest baseRequest,String locationId,String eventId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_JS_CALENDAR_VIEW_DATE_LIST_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@", locationId);
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@", eventId);
		logger.debug("getJSCalendarDateList endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler getCalendarDateList(BaseRequest baseRequest,String locationId,String eventId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_CALENDAR_VIEW_DATE_LIST_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@", locationId);
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@", eventId);
		logger.debug("getCalendarDateList endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler getSeatViewTimeList(BaseRequest baseRequest,String locationId,String eventId,String date){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_CALENDAR_VIEW_TIME_LIST_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_DATE_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@", locationId);
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@", eventId);
		endPointUrl = endPointUrl.replaceAll("@dateParam@", date);
		logger.debug("getSeatViewTimeList endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	//Seat View
	public RevJsonDataHandler getSeatsCalendarView(BaseRequest baseRequest,String locationId, String eventId, String date,String time,String showRemainderIcons){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_SEATS_CALENDAR_VIEW_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_DATE_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_TIME_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_SHOW_REMAINDER_ICONS_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@", locationId);
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@", eventId);
		endPointUrl = endPointUrl.replaceAll("@dateParam@", date);
		endPointUrl = endPointUrl.replaceAll("@timeParam@", time);
		endPointUrl = endPointUrl.replaceAll("@showRemainderIconsParam@",showRemainderIcons);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("getSeatsCalendarView :::: endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	//Daily View
	public RevJsonDataHandler getDailyCalendarView(BaseRequest baseRequest,String locationId,String eventId,String date,String showRemainderIcons){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_DAILY_CALENDAR_VIEW_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_DATE_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_SHOW_REMAINDER_ICONS_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@", locationId);
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@", eventId);
		endPointUrl = endPointUrl.replaceAll("@dateParam@", date);
		endPointUrl = endPointUrl.replaceAll("@showRemainderIconsParam@",showRemainderIcons);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("getDailyCalendarView endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
		
	//Reservation Reports
	public RevJsonDataHandler getReservationReports(BaseRequest baseRequest,ReservationReportRequest resReportRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_RESERVATION_REPORTS_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_START_DATE_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_END_DATE_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_GET_RESERVATION_REPORTS_STATUS_REQUEST_PARAMETER.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_GET_RESERVATION_REPORTS_EVENT_DATE_TIME_IDS_REQUEST_PARAMETER.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",String.valueOf(resReportRequest.getLocationId()));
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@",String.valueOf(resReportRequest.getEventId()));
		endPointUrl = endPointUrl.replaceAll("@startDateParam@",resReportRequest.getStartDate());
		endPointUrl = endPointUrl.replaceAll("@endDateParam@",resReportRequest.getEndDate());
		endPointUrl = endPointUrl.replaceAll("@resvStatusParam@",resReportRequest.getSelectedStatusFields());
		endPointUrl = endPointUrl.replaceAll("@eventDateTimeIdsParam@",resReportRequest.getSelectedEventDateTimeIds());
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("getReservationReports endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler getEventDateTimeForLocEventDateRange(BaseRequest baseRequest,String locationId,String eventId,String startDate,String endDate){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_EVENT_DATE_TIME_LIST_FOR_L_E_D_R_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_START_DATE_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_END_DATE_REQUEST_PARAMETERS.getValue(),baseRequest);
		
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@",eventId);
		endPointUrl = endPointUrl.replaceAll("@startDateParam@",startDate);
		endPointUrl = endPointUrl.replaceAll("@endDateParam@",endDate);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.debug("getEventDateTimeForLocEventDateRange endPointUrl :: "+endPointUrl);
		logger.error("getEventDateTimeForLocEventDateRange endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	//Table Print view
	public RevJsonDataHandler getTablePrintView(BaseRequest baseRequest,String locationId,String eventIds,String date){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_TABLE_PRINT_VIEW_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_DATE_REQUEST_PARAMETERS.getValue(),baseRequest);
		
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@",eventIds);
		endPointUrl = endPointUrl.replaceAll("@dateParam@",date);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("getTablePrintView endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	//Reservation Search
	public RevJsonDataHandler getReservationSearch(SearchRequestData resvSearchRequest){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_SEARCH_RESERVATIONS_REQUEST_URL.getValue();
		logger.debug("getReservationSearch endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class, resvSearchRequest);
	}
	
	//Dynamic Search Fields
	public RevJsonDataHandler getDynamicSearchFields(BaseRequest baseRequest,String searchCategory){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_DYNAMIC_SEARCH_FIELDS_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_SEARCH_CATEGORY_REQUEST_PARAMETERS.getValue(),baseRequest);
		
		endPointUrl = endPointUrl.replaceAll("@searchCategoryParam@",searchCategory);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("getDynamicSearchFields endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	//Client Details Search
	public RevJsonDataHandler getClientDetails(SearchRequestData custDetailsSearchRequest){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_SEARCH_CUSTOMER_DETAILS_REQUEST_URL.getValue();
		logger.debug("getClientDetails endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class,custDetailsSearchRequest);
	}
	
	//Blocked Client Details Search
	public RevJsonDataHandler getBlockedClientDetails(SearchRequestData custDetailsSearchRequest){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_SEARCH_BLOCKED_CUSTOMER_DETAILS_REQUEST_URL.getValue();
		logger.debug("getBlockedClientDetails endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class,custDetailsSearchRequest);
	}
	
	//Graphs related
	public RevJsonDataHandler getGraphDetails(BaseRequest baseRequest,String locationId,String date){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_GRAPH_DETAILS_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()
				+AdminResvDeskRestConstants.REST_SERVICE_DATE_REQUEST_PARAMETERS.getValue(),baseRequest);
		
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@dateParam@",date);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("getGraphDetails endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	//Accesses Privileges
	public RevJsonDataHandler getAccessesPrivileges(BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_ACCESSES_PRIVILEGES_REQUEST_URL.getValue(),null,baseRequest);
		
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("getAccessesPrivilages endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	//Automatic Email Report configs
	public RevJsonDataHandler getReservationReportConfig(BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_RESERVATION_REPORT_CONFIG_REQUEST_URL.getValue(),null,baseRequest);
		
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("getReservationReportConfig endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler addReservationReportConfig(ReservationReportConfig resvReportConfig){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_ADD_RESERVATION_REPORT_CONFIG_REQUEST_URL.getValue();
		logger.debug("addReservationReportConfig endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class,resvReportConfig);
	}
	
	public RevJsonDataHandler deleteResvReportConfig(BaseRequest baseRequest, String reportConfigId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_DELETE_RESERVATION_REPORT_CONFIG_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_RESERVATION_REPORT_CONFIG_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@reportConfigIdParam@", reportConfigId);
		logger.debug("deleteResvReportConfig endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler getReservationStatusList(BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_RESV_STATUSES_REQUEST_URL.getValue(),null,baseRequest);
		
		logger.debug("getReservationStatusList endPointUrl :: "+endPointUrl);		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	//Reservation Reminders
	public RevJsonDataHandler getReservationReminders(BaseRequest baseRequest,String locationId,String campaignId,String date){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_RESERVATION_REMAINDERS_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS.getValue()
				+AdminResvDeskRestConstants.REST_SERVICE_CAMPAIGN_ID_REQUEST_PARAMETERS.getValue()
				+AdminResvDeskRestConstants.REST_SERVICE_DATE_REQUEST_PARAMETERS.getValue(),baseRequest);
		
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@campaignIdParam@",campaignId);
		endPointUrl = endPointUrl.replaceAll("@dateParam@",date);
		
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("getReservationReminders endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler getCampaigns(BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_CAMPAIGNS_REQUEST_URL.getValue(),null,baseRequest);
		
		logger.debug("getCampaigns endPointUrl :: "+endPointUrl);		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler getNotifyRemainderStatusList(BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_NOTIFY_REMAINDER_STATUSES_REQUEST_URL.getValue(),null,baseRequest);
		
		logger.debug("getNotifyRemainderStatusList endPointUrl :: "+endPointUrl);		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler getNotificationById(BaseRequest baseRequest,String locationId){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_NOTIFICATION_BY_ID_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_NOTIFICATION_ID_REQUEST_PARAMETERS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@notificationIdParam@", locationId);
		
		logger.debug("getNotificationById endPointUrl :: "+endPointUrl);

		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler updateNotifyStatus(NotifyRequest notifyRequest){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_UPDATE_NOTIFICATION_REQUEST_URL.getValue();		
		
		logger.debug("updateNotification endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class,notifyRequest);
	}
	
	//Support Ticket related
	public JsonDataHandler getSupportResponse(String clientCode) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_GET_SUPPORT_RESPONSE.getValue();
		endPointUrl = endPointUrl.replaceAll("@clientCodeParam@", clientCode);
		
		logger.debug("getSupportResponse endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getSupportTypes() {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()	+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_GET_SUPPORT_TYPES.getValue();
		logger.debug("getSupportTypes endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getSupportStatus() {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL() + AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_GET_SUPPORT_STATUS.getValue();
		logger.debug("getSupportStatus endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler addTicket(TicketTO ticketTO) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL()+ AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_ADD_TICKET.getValue();
		logger.debug("addTicket endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(JsonDataHandler.class,ticketTO);
	}
	
	public JsonDataHandler getTicketById(String ticketId) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL() + AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_GET_TICKET_BY_ID.getValue();
		endPointUrl = endPointUrl.replaceAll("@ticketIdParam@",ticketId);
		logger.debug("getTicketById endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl.toString());
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler updateTicket(TicketTO ticketTO) {
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL() + AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_UPDATE_TICKET.getValue();
		logger.debug("updateTicket endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(JsonDataHandler.class,ticketTO);
	}
	
	public JsonDataHandler deleteTicket(String ticketId){
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL() + AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_DELETE_TICKET_BY_ID.getValue();
		endPointUrl = endPointUrl.replaceAll("@ticketIdParam@",ticketId);
		logger.debug("deleteTicket endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl.toString());
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler isAnyTicketWaitingForClientResponse(String clientCode,String userName){
		String endPointUrl = PropertyUtils.getCommonRestWSEndPointURL() + AdminResvDeskRestConstants.COMMONRESTWS_REST_SERVICE_CHECK_ANY_TICKET_WAITING_FOR_CLIENT_RESPONSE.getValue();
		endPointUrl = endPointUrl.replaceAll("@clientCodeParam@",clientCode);
		endPointUrl = endPointUrl.replaceAll("@userNameParam@",userName);
		logger.debug("isAnyTicketWaitingForClientResponse endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl.toString());
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	//Call Report functionality
	public RevJsonDataHandler getInBoundCallLogs(CallReportRequest callReportRequest) {
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_GET_IN_BOUND_CALL_LOGS_REQUEST_URL.getValue();
		logger.debug("getInBoundCallLogs endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class,callReportRequest);
	}
	
	public RevJsonDataHandler getOutBoundCallLogs(CallReportRequest callReportRequest) {
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_GET_OUT_BOUND_CALL_LOGS_REQUEST_URL.getValue();
		logger.debug("getOutBoundCallLogs endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(RevJsonDataHandler.class,callReportRequest);
	}
	
	public JsonDataHandler getTransStates(String transId,BaseRequest baseRequest) {
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl+ AdminResvDeskRestConstants.REST_SERVICE_GET_TRANS_STATES_REQUEST_URL.getValue();
		endPointUrl = endPointUrl+ AdminResvDeskRestConstants.REST_SERVICE_BASIC_REQUEST_PARAMETERS.getValue();
				
		endPointUrl = endPointUrl.replaceAll("@clientCodeParam@",baseRequest.getClientCode());
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@",baseRequest.getLangCode());
		endPointUrl = endPointUrl.replaceAll("@deviceParam@",ResvDeskContants.DEVICE_TYPE_ADMIN.getValue());
		endPointUrl = endPointUrl.replaceAll("@tokenParam@",baseRequest.getToken());		
		endPointUrl = endPointUrl.replaceAll("@userIdParam@",String.valueOf(baseRequest.getUserId()));
		
		endPointUrl = endPointUrl.replaceAll("@transIdParam@",transId); //Here Trans Id is the InBound calls View Trans History id
		
		logger.debug("getTransStates endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	//Privilege Settings related
	public JsonDataHandler getPrivilegeSettingsResponse(String access_privilege_id,String access_privilege_name,String includeSuperAccessPrivilage,BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_PRIVILEGE_SETTINGS_RESPONSE.getValue(),null,baseRequest);

		endPointUrl = endPointUrl.replaceAll("@access_privilege_idParam@", access_privilege_id);
		endPointUrl = endPointUrl.replaceAll("@access_privilege_nameParam@", access_privilege_name);
		endPointUrl = endPointUrl.replaceAll("@includeSuperAccessPrivilageParam@", includeSuperAccessPrivilage);
		logger.debug("getPrivilegeSettingsResponse endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler updatePrivilegeSettings(PrivilegeSettings privilegeSettings){
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL();
		endPointUrl = endPointUrl +AdminResvDeskRestConstants.REST_SERVICE_UPDATE_PRIVILEGE_SETTINGS.getValue();
		logger.debug("updatePrivilegeSettings endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(JsonDataHandler.class,privilegeSettings);
	}
	
	public JsonDataHandler getEmailTemplatesText(String mailLangcode,String messageKey,BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_EMAIL_TEXT.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_GET_EMAIL_TEXT_REQUEST_PARAMS.getValue(),baseRequest);
		
		endPointUrl = endPointUrl.replaceAll("@mailLangcodeParam@",mailLangcode);
		endPointUrl = endPointUrl.replaceAll("@messageKeyParam@",messageKey);
		
		logger.debug("getEmailTemplatesText endPointUrl :: "+endPointUrl);		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler saveEmailTemplatesText(EmailTemplatesTO emailTemplatesTO) {
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL()+ AdminResvDeskRestConstants.REST_SERVICE_SAVE_EMAIL_TEXT.getValue();
		logger.debug("saveEmailTemplatesText endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(JsonDataHandler.class,emailTemplatesTO);
	}
	
	public JsonDataHandler getLandingPageText(String landingPageLangcode,BaseRequest baseRequest){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_GET_LANDING_PAGE_TEXT.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_GET_LANDING_PAGE_TEXT_REQUEST_PARAMS.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@landingPageLangcodeParam@", landingPageLangcode);
		logger.debug("getLandingPageText endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler saveLandingPageText(LandingPageContentTO landingPageContent) {
		String endPointUrl = PropertyUtils.getRESVDESKRestWSEndPointURL()+ AdminResvDeskRestConstants.REST_SERVICE_SAVE_LANDING_PAGE_TEXT.getValue();
		logger.debug("saveLandingPageText endPointUrl :: "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(JsonDataHandler.class,landingPageContent);
	}
	
	public RevJsonDataHandler updateScreenedStatus(BaseRequest baseRequest,String sceduleId,String screened){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_UPDATE_SCREENED_STATUS_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_SCHEDULE_ID_REQUEST_PARAM.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_SCREENED_REQUEST_PARAM.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@scheduleIdParam@", sceduleId);
		endPointUrl = endPointUrl.replaceAll("@screenedParam@", screened);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("updateScreenedStatus endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
	
	public RevJsonDataHandler updateSeatReservedStatus(BaseRequest baseRequest,String seatId,String reserved){
		String endPointUrl = prepareReuestURL(AdminResvDeskRestConstants.REST_SERVICE_UPDATE_SEAT_RESERVED_STATUS_REQUEST_URL.getValue(),
				AdminResvDeskRestConstants.REST_SERVICE_SEAT_ID_REQUEST_PARAMETERS.getValue()+
				AdminResvDeskRestConstants.REST_SERVICE_RESERVE_REQUEST_PARAM.getValue(),baseRequest);
		endPointUrl = endPointUrl.replaceAll("@seatIdParam@", seatId);
		endPointUrl = endPointUrl.replaceAll("@reservedParam@", reserved);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		logger.debug("updateSeatReservedStatus endPointUrl :: "+endPointUrl);
		
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(RevJsonDataHandler.class);
	}
}
