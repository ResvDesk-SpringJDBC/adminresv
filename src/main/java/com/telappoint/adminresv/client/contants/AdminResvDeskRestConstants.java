package com.telappoint.adminresv.client.contants;

public enum AdminResvDeskRestConstants {
		
		//Common RestWS request related
		COMMON_REST_SERVICE_AUTHENTICATE_LOGIN("authenticateLogin"),
		COMMONRESTWS_REST_SERVICE_REST_PASSWORD_REQUEST_TOKEN("commonnotify/sendResetPasswordRequestToken"),
		COMMONRESTWS_REST_SERVICE_REST_PASSWORD_REQUEST("commonnotify/resetPasswordRequest?token=@tokenParam@"),
		COMMONRESTWS_REST_SERVICE_UPDATE_PASSWORD("commonnotify/updatePassword"),
		COMMONRESTWS_REST_SERVICE_UPDATE_CHANGE_PASSWORD("commonnotify/updatechangepassword"),
		COMMONRESTWS_REST_SERVICE_VALIDATE_OLD_PASSWORD("commonnotify/validateoldpassword"),
		
		COMMONRESTWS_REST_SERVICE_GET_USER_LIST("commonnotify/getUserList"),
		COMMONRESTWS_REST_SERVICE_ADD_USER_DETAILS("commonnotify/addUserDetails"),
		COMMONRESTWS_REST_SERVICE_GET_USER_DETAILS("commonnotify/getUserDetails"),
		COMMONRESTWS_REST_SERVICE_DELETE_USER_DETAILS("commonnotify/deleteUserDetails?userId="),
		COMMONRESTWS_REST_SERVICE_UPDATE_USER_DETAILS("commonnotify/updateUserDetails"), 
		COMMONRESTWS_REST_SERVICE_UPDATE_USER_DETAILS_BY_ADMIN("commonnotify/updateUserDetailsByAdmin"), 
		COMMONRESTWS_REST_SERVICE_GET_USER_DETAILS_BY_ID("commonnotify/findUsersById?userId="),
		
		COMMONRESTWS_REST_SERVICE_CLIENT_ID_AND_CLIENT_CODE("?clientId=@clientIdParam@&clientCode=@clientCodeParam@"),
		COMMONRESTWS_REST_SERVICE_VALIDATE_USERNAME("commonnotify/validateUserName?username=@usernameParam@&id=@idParam@"),
		COMMONRESTWS_REST_SERVICE_UPDATE_PASSWORD_CHANGED_BY_ADMIN("commonnotify/updatePasswordChangedByAdmin"),
		
		//Reservation RestWS request related
		REST_SERVICE_BASIC_REQUEST_PARAMETERS("?clientCode=@clientCodeParam@&langCode=@langCodeParam@&device=@deviceParam@&token=@tokenParam@&transId=@transIdParam@&userId=@userIdParam@"),
		REST_SERVICE_ACESSES_PRIVILEGE_NAME_REQUEST_PARAMETERS("&privilegeName=@privilegeNameParam@"),
		REST_SERVICE_COMPANY_ID_REQUEST_PARAMETERS("&companyId=@companyIdParam@"),
		REST_SERVICE_PROCEDURE_ID_REQUEST_PARAMETERS("&procedureId=@procedureIdParam@"),
		REST_SERVICE_LOCATION_ID_REQUEST_PARAMETERS("&locationId=@locationIdParam@"),
		REST_SERVICE_DEPARTMENT_ID_REQUEST_PARAMETERS("&departmentId=@departmentIdParam@"),
		REST_SERVICE_EVENT_ID_REQUEST_PARAMETERS("&eventId=@eventIdParam@"),
		REST_SERVICE_SEAT_ID_REQUEST_PARAMETERS("&seatId=@seatIdParam@"),
		REST_SERVICE_EVENT_DATE_TIME_ID_REQUEST_PARAMETERS("&eventDateTimeId=@eventDateTimeIdParam@"),
		REST_SERVICE_CUSTOMER_ID_REQUEST_PARAMETERS("&customerId=@customerIdParam@"),
		REST_SERVICE_DATE_REQUEST_PARAMETERS("&date=@dateParam@"),
		REST_SERVICE_TIME_REQUEST_PARAMETERS("&time=@timeParam@"),
		REST_SERVICE_OPEN_CLOSE_LOCATION_REQUEST_PARAMETERS("&openClosedFlag=@openClosedFlagParam@"),
		REST_SERVICE_START_DATE_REQUEST_PARAMETERS("&startDate=@startDateParam@"),
		REST_SERVICE_END_DATE_REQUEST_PARAMETERS("&endDate=@endDateParam@"),
		REST_SERVICE_ACESSES_CUSTOMER_NAME_REQUEST_PARAMETERS("&customerName=@customerNameParam@"),
		REST_SERVICE_SCHEDULE_ID_REQUEST_PARAMETERS("&scheduleId=@scheduleIdParam@"),
		REST_SERVICE_COMMENT_REQUEST_PARAMETERS("&comment=@commentParam@"),
		REST_SERVICE_SEARCH_CATEGORY_REQUEST_PARAMETERS("&searchCategory=@searchCategoryParam@"),
		REST_SERVICE_CAMPAIGN_ID_REQUEST_PARAMETERS("&campaignId=@campaignIdParam@"),
		REST_SERVICE_STATUS_REQUEST_PARAMETERS("&status=@statusParam@"),
		REST_SERVICE_SHOW_REMAINDER_ICONS_REQUEST_PARAMETERS("&showRemainderIcons=@showRemainderIconsParam@"),
				
		//Online Reservation RestWS request related
		ONLINE_RESVDESK_REST_SERVICE_BASIC_REQUEST_PARAMETERS("?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@"),
		ONLINE_RESVDESK_REST_SERVICE_LOGIN_PARAMS_REQUEST_PARAMETERS("&loginParams=@loginParamsParam@"),
		
		//Customer related	
		REST_SERVICE_GET_CUSTOMER_NAMES_REQUEST_URL("getCustomerNames"),	
		REST_SERVICE_GET_CUSTOMER_BY_ID_REQUEST_URL("getCustomerById"),	
		ONLINE_RESVDESK_REST_SERVICE_GET_LOGIN_FIELDS_LIST("getLoginInfo"),
		REST_SERVICE_GET_FUTURE_RESERVATIONS_REQUEST_URL("getFutureReservations"),	
		REST_SERVICE_GET_PAST_RESERVATIONS_REQUEST_URL("getPastReservations"),	
		REST_SERVICE_GET_CUST_REG_DETAILS("getRegistrationInfo"),
		ONLINE_RESVDESK_REST_SERVICE_AUTH_CUSTOMER("authenticateCustomer"),
		ONLINE_RESVDESK_REST_SERVICE_HOLD_RESERVATION("holdReservation"),
		ONLINE_RESVDESK_REST_SERVICE_CONFIRM_RESERVATION("confirmReservation"),
		REST_SERVICE_CREATE_OR_UPDATE_CUSTOMER("createOrUpdateCustomer"),
		ONLINE_RESVDESK_REST_SERVICE_CANCEL_RESERVATION("cancelReservation"),
		REST_SERVICE_UPDATE_CUSTOMER("updateCustomer"),
		
		//Location related	
		REST_SERVICE_GET_LOCATION_LIST_REQUEST_URL("getLocationList"),		
		REST_SERVICE_DELETE_LOCATION_REQUEST_URL("deleteLocation"),		
		REST_SERVICE_OPEN_CLOSE_LOCATION_REQUEST_URL("openCloseLocation"),		
		REST_SERVICE_ADD_LOCATION_REQUEST_URL("addLocation"),
		REST_SERVICE_GET_LOCATION_BY_ID_REQUEST_URL("getLocationById"),	
		REST_SERVICE_UPDATE_LOCATION_REQUEST_URL("updateLocation"),
		REST_SERVICE_GET_LOCATION_DROP_DOWN_REQUEST_URL("getLocationListDropDown"),
		
		//Event related	
		REST_SERVICE_GET_EVENT_LIST_REQUEST_URL("getEventList"),
		REST_SERVICE_DELETE_EVENT_REQUEST_URL("deleteEvent"),
		REST_SERVICE_OPEN_CLOSE_EVENT_REQUEST_URL("openCloseEvent"),		
		REST_SERVICE_ADD_EVENT_REQUEST_URL("addEvent"),
		REST_SERVICE_GET_EVENT_BY_ID_REQUEST_URL("getEventById"),	
		REST_SERVICE_UPDATE_EVENT_REQUEST_URL("updateEvent"),
		REST_SERVICE_GET_EVENT_LIST_BY_LOC_ID_REQUEST_URL("getEventListByLocationId"),
		
		//EventDateTime related
		REST_SERVICE_GET_EVENT_DATE_TIME_LIST_REQUEST_URL("getEventDateTimeList"),
		REST_SERVICE_OPEN_CLOSE_EVENT_DATE_TIME_REQUEST_URL("openCloseEventDateTime"),		
		REST_SERVICE_ADD_EVENT_DATE_TIME_REQUEST_URL("addEventDateTime"),
		REST_SERVICE_UPDATE_EVENT_DATE_TIME_REQUEST_URL("updateEventDateTime"),
		REST_SERVICE_GET_EVENT_DATE_TIME_BY_ID_REQUEST_URL("getEventDateTimeById"),
		REST_SERVICE_UPDATE_EVENT_SEATS_REQUEST_URL("updateEventSeats"),
		REST_SERVICE_GET_EVENT_DATE_TIME_LIST_FOR_L_E_D_R_REQUEST_URL("getEventDateTimeForLocEventDateRange"),
		
		//Reservations > Reservation Overview
		REST_SERVICE_GET_CALENDAR_OVER_VIEW_REQUEST_URL("getCalendarOverview"),
		REST_SERVICE_GET_CALENDAR_OVER_VIEW_DETAILS_REQUEST_URL("getCalendarOverViewDetails"),
		
		//Reservation > Calendar >
		REST_SERVICE_GET_JS_CALENDAR_VIEW_DATE_LIST_REQUEST_URL("getJSCalendarDateList"),
		REST_SERVICE_GET_CALENDAR_VIEW_DATE_LIST_REQUEST_URL("getCalendarDateList"),
		REST_SERVICE_GET_CALENDAR_VIEW_TIME_LIST_REQUEST_URL("getSeatViewTimeList"),
		
		// Seat View
		REST_SERVICE_GET_SEATS_CALENDAR_VIEW_REQUEST_URL("getSeatsCalendarView"),
		
		//Daily View
		REST_SERVICE_GET_DAILY_CALENDAR_VIEW_REQUEST_URL("getDailyCalendarView"),
		
		//Reservation Reports
		REST_SERVICE_GET_RESERVATION_REPORTS_REQUEST_URL("getReservationReports"),
		REST_SERVICE_GET_RESERVATION_REPORTS_STATUS_REQUEST_PARAMETER("&resvStatus=@resvStatusParam@"),
		REST_SERVICE_GET_RESERVATION_REPORTS_EVENT_DATE_TIME_IDS_REQUEST_PARAMETER("&eventDateTimeIds=@eventDateTimeIdsParam@"),
		
		//Table Print view
		REST_SERVICE_GET_TABLE_PRINT_VIEW_REQUEST_URL("getTablePrintView"),
		
		//Reservation Search
		REST_SERVICE_SEARCH_RESERVATIONS_REQUEST_URL("getReservationSearch"),
		
		//Dynamic Search Fields
		REST_SERVICE_DYNAMIC_SEARCH_FIELDS_REQUEST_URL("getDynamicSearchFields"),
		
		//Client Details Search
		REST_SERVICE_SEARCH_CUSTOMER_DETAILS_REQUEST_URL("getClientDetails"),
		
		//Blocked Client Details Search
		REST_SERVICE_SEARCH_BLOCKED_CUSTOMER_DETAILS_REQUEST_URL("getBlockedClientDetails"),
		
		//Graphs related
		REST_SERVICE_GET_GRAPH_DETAILS_REQUEST_URL("getGraphDetails"),
		
		//Accesses Privileges
		REST_SERVICE_GET_ACCESSES_PRIVILEGES_REQUEST_URL("getAccessesPrivilages"),
		
		//Automatic Email Report configs
		REST_SERVICE_GET_RESERVATION_REPORT_CONFIG_REQUEST_URL("getReservationReportConfig"),
		REST_SERVICE_ADD_RESERVATION_REPORT_CONFIG_REQUEST_URL("addReservationReportConfig"),
		REST_SERVICE_DELETE_RESERVATION_REPORT_CONFIG_REQUEST_URL("deleteResvReportConfigById"),
		REST_SERVICE_RESERVATION_REPORT_CONFIG_ID_REQUEST_PARAMETERS("&reportConfigId=@reportConfigIdParam@"),
		REST_SERVICE_GET_RESV_STATUSES_REQUEST_URL("getReservationStatusList"),
		
		//Reservation Reminders
		REST_SERVICE_GET_RESERVATION_REMAINDERS_REQUEST_URL("getReservationReminders"),
		REST_SERVICE_GET_CAMPAIGNS_REQUEST_URL("getCampaigns"),
		REST_SERVICE_GET_NOTIFY_REMAINDER_STATUSES_REQUEST_URL("getNotifyRemainderStatusList"),
		REST_SERVICE_GET_NOTIFICATION_BY_ID_REQUEST_URL("getNotificationById"),	
		REST_SERVICE_NOTIFICATION_ID_REQUEST_PARAMETERS("&notificationId=@notificationIdParam@"),
		REST_SERVICE_UPDATE_NOTIFICATION_REQUEST_URL("updateNotifyStatus"),
		
		//Home Page related	
		REST_SERVICE_GET_HOME_PAGE_DETAILS_REQUEST_URL("getHomePageDetails"),
		REST_SERVICE_CHANGE_SCHEDULAR_STATUS_REQUEST_URL("changeSchedulerStatus"),
			
		//Support Ticket related	
		COMMONRESTWS_REST_SERVICE_GET_SUPPORT_RESPONSE("support/getSupportResponse?clientCode=@clientCodeParam@"),
		COMMONRESTWS_REST_SERVICE_GET_SUPPORT_TYPES("support/getSupportTypes"),
		COMMONRESTWS_REST_SERVICE_GET_SUPPORT_STATUS("support/getSupportStatus"),
		COMMONRESTWS_REST_SERVICE_ADD_TICKET("support/addTicket"),
		COMMONRESTWS_REST_SERVICE_GET_TICKET_BY_ID("support/getTicketById?ticketId=@ticketIdParam@"),
		COMMONRESTWS_REST_SERVICE_UPDATE_TICKET("support/updateTicket"),
		COMMONRESTWS_REST_SERVICE_DELETE_TICKET_BY_ID("support/deleteTicket?ticketId=@ticketIdParam@"),
		COMMONRESTWS_REST_SERVICE_CHECK_ANY_TICKET_WAITING_FOR_CLIENT_RESPONSE("support/ticketWaitingForClientResponse?clientCode=@clientCodeParam@&userName=@userNameParam@"),
		
		REST_SERVICE_GET_IN_BOUND_CALL_LOGS_REQUEST_URL("getInBoundCallReportList"),
		REST_SERVICE_GET_OUT_BOUND_CALL_LOGS_REQUEST_URL("getOutBoundCallReportList"),
		REST_SERVICE_GET_TRANS_STATES_REQUEST_URL("getTransStates"),	
		REST_SERVICE_TRANS_ID_REQUEST_PARAMETERS("&transStateId=@transIdParam@"),
		
		REST_SERVICE_GET_PRIVILEGE_SETTINGS_RESPONSE("getPrivilegeSettingsResponse?clientCode=@clientCodeParam@&access_privilege_id=@access_privilege_idParam@&access_privilege_name=@access_privilege_nameParam@&includeSuperAccessPrivilage=@includeSuperAccessPrivilageParam@"),
		REST_SERVICE_UPDATE_PRIVILEGE_SETTINGS("updatePrivilegeSettings"),
		
		REST_SERVICE_GET_EMAIL_TEXT("getEmailTemplatesText"),
		REST_SERVICE_GET_EMAIL_TEXT_REQUEST_PARAMS("&mailLangcode=@mailLangcodeParam@&messageKey=@messageKeyParam@"),
		REST_SERVICE_SAVE_EMAIL_TEXT("saveEmailTemplatesText"),
		
		REST_SERVICE_GET_LANDING_PAGE_TEXT("getLandingPageText"),
		REST_SERVICE_GET_LANDING_PAGE_TEXT_REQUEST_PARAMS("&landingPageLangcode=@landingPageLangcodeParam@"),
		REST_SERVICE_SAVE_LANDING_PAGE_TEXT("saveLandingPageText"),
		
		REST_SERVICE_UPDATE_SCREENED_STATUS_REQUEST_URL("updateScreenedStatus"),
		REST_SERVICE_SCHEDULE_ID_REQUEST_PARAM("&scheduleId=@scheduleIdParam@"),
		REST_SERVICE_SCREENED_REQUEST_PARAM("&screened=@screenedParam@"),
		
		REST_SERVICE_UPDATE_SEAT_RESERVED_STATUS_REQUEST_URL("updateSeatReservedStatus"),
		REST_SERVICE_RESERVE_REQUEST_PARAM("&reserved=@reservedParam@"),
		
		;
		
		
		
	private String value;

	AdminResvDeskRestConstants(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

}
