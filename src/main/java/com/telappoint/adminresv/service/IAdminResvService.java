package com.telappoint.adminresv.service;

import java.util.List;
import java.util.Map;

import com.telappoint.adminresv.form.AdminInfoTO;
import com.telappoint.adminresv.form.BaseRequest;
import com.telappoint.adminresv.form.CallReportRequestTO;
import com.telappoint.adminresv.form.ReservationBookingForm;
import com.telappoint.adminresv.form.ReservationReportRequest;
import com.telappoint.adminresv.form.bean.ChangePasswordTO;
import com.telappoint.adminresv.form.bean.ResetPasswordTO;
import com.telappoint.adminresv.form.bean.UserLoginTO;
import com.telappoint.adminresv.restws.model.AuthResponse;
import com.telappoint.adminresv.restws.model.BaseResponse;
import com.telappoint.adminresv.restws.model.CalendarOverviewDetailsResponse;
import com.telappoint.adminresv.restws.model.CalendarOverviewResponse;
import com.telappoint.adminresv.restws.model.CampaignResponse;
import com.telappoint.adminresv.restws.model.ClientDetailsResponse;
import com.telappoint.adminresv.restws.model.ConfirmResvResponse;
import com.telappoint.adminresv.restws.model.Customer;
import com.telappoint.adminresv.restws.model.CustomerResponse;
import com.telappoint.adminresv.restws.model.DailyCalendarView;
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
import com.telappoint.adminresv.restws.model.TablePrintViewResponse;
import com.telappoint.adminresv.restws.model.TicketTO;
import com.telappoint.adminresv.restws.model.TimeListResponse;
import com.telappoint.adminresv.restws.model.TransStateResponse;

/**
 * @author  Murali G
 * 
 */
public interface IAdminResvService {
	
	//Common RestWS request related methods
	public AdminInfoTO getUserDetails(UserLoginTO loginForm);
	public String sendResetPasswordRequestToken(ResetPasswordTO resetPasswordTO);
	public ResetPasswordTO resetPasswordRequest(String token);
	public String updatePassword(ResetPasswordTO resetPasswordTO);
	public String updatechangepassword(ChangePasswordTO changePasswordTO);
	public String validateoldpassword(ChangePasswordTO changePasswordTO);
	public List<AdminInfoTO> getUserList(Integer clientId, String clientCode);
	public boolean addUserDetails(AdminInfoTO adminInfoTO);
	public AdminInfoTO getUserDetailsById(String id);
	public boolean updateUserDetails(AdminInfoTO adminInfoTO);
	public boolean deleteUserDetails(String id);
	public String updatePasswordChangedByAdmin(ResetPasswordTO resetPasswordTO);
	
	//Reservation RestWS request related methods
	//Home page related 
	public HomePageResponse getHomePageDetails(BaseRequest baseRequest,String privilegeName) throws Exception;
	public BaseResponse changeSchedulerStatus(BaseRequest baseRequest,String schedulerStatus) throws Exception;
	
	
	//Customer related	
	public CustomerResponse getCustomerNames(BaseRequest baseRequest,String customerName) throws Exception;
	public CustomerResponse getCustomerById(BaseRequest baseRequest, String customerId)throws Exception;
	public LoginInfoResponse getLoginInfo(BaseRequest baseRequest) throws Exception;
	public FutureReservationResponse getFutureReservations(BaseRequest baseRequest,String customerId) throws Exception;
	public PastReservationResponse getPastReservations(BaseRequest baseRequest,String customerId) throws Exception;
	public RegistrationInfoResponse getRegistrationInfo(BaseRequest baseRequest)throws Exception;
	public AuthResponse authenticateCustomer(Customer customer,BaseRequest baseRequest, List<LoginField> loginFieldList)throws Exception;
	public HoldResvResponse holdReservation(BaseRequest baseRequest,ReservationBookingForm resvBookingForm, String customerId)throws Exception;
	public ConfirmResvResponse confirmReservation(BaseRequest baseRequest,String scheduleId, String customerId, String comment)throws Exception;
	public AuthResponse createOrUpdateCustomer(Customer customer,BaseRequest baseRequest) throws Exception;
	public BaseResponse cancelReservation(BaseRequest baseRequest, String scheduleId,String customerId) throws NoSuchFieldException;
	public AuthResponse updateCustomer(Customer customer, BaseRequest baseRequest)throws Exception;
	public String validateUserName(String username, String id);
	
	//Location related	
	public LocationListResponse getLocationList(BaseRequest baseRequest) throws Exception;
	public String deleteLocation(BaseRequest baseRequest,String locationId) throws Exception;
	public BaseResponse openCloseLocation(BaseRequest baseRequest,String locationId,String enable)throws Exception;
	public BaseResponse addLocation(BaseRequest baseRequest, Location location)throws Exception;
	public LocationListResponse getLocationListDropDown(BaseRequest baseRequest)throws Exception;
	public LocationListResponse getLocationById(BaseRequest baseRequest, String locationId)throws Exception;
	public BaseResponse updateLocation(BaseRequest baseRequest, Location location)throws Exception;
	
	//Event related	
	public EventListResponse getEventList(BaseRequest baseRequest)throws Exception;
	public String deleteEvent(BaseRequest baseRequest, String eventId)throws Exception;
	public BaseResponse openCloseEvent(BaseRequest baseRequest, String eventId,String enable) throws Exception;
	public BaseResponse addEvent(BaseRequest baseRequest,Event event) throws Exception;
	public BaseResponse updateEvent(BaseRequest baseRequest,Event event) throws Exception;
	public EventListResponse getEventListByLocationId(BaseRequest baseRequest,String locationId) throws Exception;	
	public EventListResponse getEventById(BaseRequest baseRequest, String eventId)throws Exception;
	
	//EventDateTime related
	public EventDateTimeResponse getEventDateTimeList(BaseRequest baseRequest)throws Exception;
	public BaseResponse openCloseEventDateTime(BaseRequest baseRequest,String eventDateTimeId, String enable) throws Exception;
	public BaseResponse addEventDateTime(BaseRequest baseRequest,EventDateTime eventDateTime) throws Exception;
	public BaseResponse updateEventDateTime(BaseRequest baseRequest,EventDateTime eventDateTime) throws Exception;
	public EventDateTime getEventDateTimeById(BaseRequest baseRequest,String eventDateTimeById)throws Exception;
	public BaseResponse updateEventSeats(BaseRequest baseRequest,EventDateTime eventDateTime) throws Exception;
		
	//Reservations > Reservation Overview
	public CalendarOverviewResponse getReservationOverview(BaseRequest baseRequest,String startDate,String endDate)throws Exception;
	public CalendarOverviewDetailsResponse getReservationOverviewDetails(BaseRequest baseRequest, String eventDateTimeId) throws Exception;	
	
	//Reservation > Calendar > Seat View	
	public DateJSListResponse getJSCalendarDateList(BaseRequest baseRequest,String locationId, String eventId) throws Exception;
	public DateListResponse getCalendarDateList(BaseRequest baseRequest,String locationId, String eventId) throws Exception;
	public TimeListResponse getSeatViewTimeList(BaseRequest baseRequest,String locationId, String eventId, String date) throws Exception;	
	
	//Seat View
	public SeatsCalendarView getSeatsCalendarView(BaseRequest baseRequest,String locationId, String eventId, String date,String time,String showRemainderIcons) throws Exception;
	
	//Daily View
	public DailyCalendarView getDailyCalendarView(BaseRequest baseRequest,String locationId, String eventId, String date,String showRemainderIcons)throws Exception;
	
	//Reservation Reports
	public ReservationReportResponse getReservationReports(BaseRequest baseRequest,	ReservationReportRequest resReportRequest) throws Exception;
	public ReservationReportCheckboxResponse getEventDateTimeForLocEventDateRange(BaseRequest baseRequest, String locationId, String eventId,String startDate, String endDate) throws Exception;
	
	//Table Print view
	public TablePrintViewResponse getTablePrintView(BaseRequest baseRequest,String locationId,String eventIds,String date) throws Exception;
	
	//Reservation Search
	public ReservationSearchResponse getReservationSearch(BaseRequest baseRequest,SearchRequestData resvSearchRequest) throws Exception;
	
	//Dynamic Search Fields
	public SearchFieldsResponse getDynamicSearchFields(BaseRequest baseRequest,String searchCategory) throws Exception;
	
	
	//Customer Details Search
	public ClientDetailsResponse getClientDetails(BaseRequest baseRequest,SearchRequestData resvSearchRequest) throws Exception;
	
	//Graphs related
	public String getGraphDetails(BaseRequest baseRequest, String locationId,String date) throws Exception;
	
	//Accesses Privileges
	public PrivilageResponse getAccessesPrivileges(BaseRequest baseRequest)throws Exception;
	
	//Blocked Client Details Search
	public ClientDetailsResponse getBlockedClientDetails(BaseRequest baseRequest,SearchRequestData custDetailsSearchRequest) throws Exception;
	
	//Automatic Email Report configs
	public ReservationReportConfigResponse getReservationReportConfig(BaseRequest baseRequest) throws Exception;
	public BaseResponse addReservationReportConfig(BaseRequest baseRequest,ReservationReportConfig resvReportConfig) throws Exception;
	public BaseResponse deleteResvReportConfig(BaseRequest baseRequest, String reportConfigId)throws Exception;
	public ReservationStatusResponse getReservationStatusList(BaseRequest baseRequest)throws Exception;
	
	//Reservation Reminders
	public ReservationRemindersResponse getReservationReminders(BaseRequest baseRequest,String locationId,String campaignId,String date)throws Exception;
	public CampaignResponse getCampaigns(BaseRequest baseRequest) throws Exception;
	public ReminderStatusResponse getNotifyRemainderStatusList(BaseRequest baseRequest)throws Exception;
	public Notification getNotificationById(BaseRequest baseRequest, String notifyId)throws Exception;
	public BaseResponse updateNotifyStatus(BaseRequest baseRequest,NotifyRequest notifyRequest) throws Exception;
	
	//Support Ticket related
	public SupportResponseTO getSupportResponse(String clientCode);
	public Map<Integer, String> getSupportTypes() throws Exception;
	public Map<Integer, String> getSupportStatus() throws Exception;
	public TicketTO addTicket(TicketTO ticketTO) throws Exception;
	public TicketTO getTicketById(String ticketId);
	public boolean updateTicket(TicketTO ticketTO) throws Exception;
	public boolean deleteTicket(String ticketId);
	public boolean isAnyTicketWaitingForClientResponse(String clientCode,String userName);
	
	//Call Report functionality
	public InBoundCallReportResponse getInBoundCallReportList(CallReportRequestTO callReportRequestTO,BaseRequest baseRequest);
	public OutBoundCallReportResponse getOutBoundCallReportList(CallReportRequestTO callReportRequestTO,BaseRequest baseRequest);
	public TransStateResponse getTransStates(String transId,BaseRequest baseRequest);
	
	//Privilege Settings related
	public PrivilegeSettings getPrivilegeSettingsResponse(String access_privilege_id,String access_privilege_name,String includeSuperAccessPrivilage,BaseRequest baseRequest) throws Exception;
	public boolean updatePrivilegeSettings(PrivilegeSettings privilegeSettings,BaseRequest baseRequest);
	
	public EmailTemplatesTO getEmailTemplatesText(String mailLangcode,String messageKey,BaseRequest baseRequest);
	public boolean saveEmailTemplatesText(EmailTemplatesTO emailTemplatesTO,BaseRequest baseRequest)throws Exception;
	
	public LandingPageContentTO getLandingPageText(String landingPageLangcode,BaseRequest baseRequest);
	public boolean saveLandingPageText(LandingPageContentTO landingPageContent,BaseRequest baseRequest) throws Exception;
	
	public BaseResponse updateScreenedStatus(BaseRequest baseRequest,String sceduleId, String screened) throws Exception;
	public String updateSeatReservedStatus(BaseRequest baseRequest,String seatId, String reserved) throws Exception;
						
}
