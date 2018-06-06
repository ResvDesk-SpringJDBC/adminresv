package com.telappoint.adminresv.constants;

/**
 * 
 * @author Murali
 * 
 */
public enum ErrorCodeConstants {
	
	ERROR_CODE_RESV_WINDOW("ERROR_CODE_RESV_WINDOW",1000,"Error in loading page/function"),
	
	ERROR_CODE_LOCATIONS_SHOW("LOCATIONS_SHOW",1001,"Error in show locations page/function"),
	ERROR_CODE_LOCATIONS_ADD("LOCATIONS_ADD",1002,"Error in add location page/function"),
	ERROR_CODE_LOCATIONS_SAVE("LOCATIONS_SAVE",1003,"Error in save location page/function"),
	ERROR_CODE_LOCATIONS_EDIT("LOCATIONS_EDIT",1004,"Error in edit location page/function"),
	ERROR_CODE_LOCATIONS_UPDATE("LOCATIONS_UPDATE",1005,"Error in update location page/function"),
	
	ERROR_CODE_EVENTS_SHOW("EVENTS_SHOW",1101,"Error in show events page/function"),
	ERROR_CODE_EVENTS_ADD("EVENTS_ADD",1102,"Error in add event page/function"),
	ERROR_CODE_EVENTS_SAVE("EVENTS_SAVE",1103,"Error in save event page/function"),
	ERROR_CODE_EVENTS_EDIT("EVENTS_EDIT",1104,"Error in edit event page/function"),
	ERROR_CODE_EVENTS_UPDATE("EVENTS_UPDATE",1105,"Error in update event page/function"),
	
	ERROR_CODE_EVENT_DATE_TIME_SHOW("EVENT_DATE_TIME_SHOW",1201,"Error in show event date time page/function"),
	ERROR_CODE_EVENT_DATE_TIME_ADD("EVENT_DATE_TIME_ADD",1202,"Error in add event date time page/function"),
	ERROR_CODE_EVENT_DATE_TIME_SAVE("EVENT_DATE_TIME_SAVE",1203,"Error in save event date time page/function"),
	ERROR_CODE_EVENT_DATE_TIME_EDIT("EVENT_DATE_TIME_EDIT",1204,"Error in edit event date time page/function"),
	ERROR_CODE_EVENT_DATE_TIME_UPDATE("EVENT_DATE_TIME_UPDATE",1205,"Error in event date time event page/function"),
	
	ERROR_CODE_USAERS_SHOW("USAERS_SHOW",1301,"Error in show Users page/function"),
	ERROR_CODE_USAERS_ADD("USAERS_ADD",1302,"Error in add User page/function"),
	ERROR_CODE_USAERS_SAVE("USAERS_SAVE",1303,"Error in save User page/function"),
	ERROR_CODE_USAERS_EDIT("USAERS_EDIT",1304,"Error in edit User page/function"),
	ERROR_CODE_USAERS_UPDATE("USAERS_UPDATE",1305,"Error in update User page/function"),
	ERROR_CODE_USAERS_DELETE("USAERS_DELETE",1306,"Error in delete User page/function"),
	ERROR_CODE_USAERS_CHANGE_PASSWORD("USAERS_CHANGE_PASSWORD",1307,"Error in change password page/function"),
	ERROR_CODE_USAERS_UPDATE_PASSWORD("USAERS_UPDATE_PASSWORD",1308,"Error in update password page/function"),
	
	ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW("RESERVATION_OVERVIEW",1400,"Error in show Reservation Overview page/function"),
	ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW_DETAILS("RESERVATION_OVERVIEW_DETAILS",1401,"Error in show Reservation Overview Details page/function"),
	
	ERROR_CODE_RESERVATIONS_RESERVATION_SEAT_VIEW("RESERVATION_SEAT_VIEW",1500,"Error in show Reservation Seat view page/function"),
	ERROR_CODE_RESERVATIONS_RESERVATION_DAILY_CALENDAR("RESERVATION_DAILY_CALENDAR",1600,"Error in show Reservation Daily Calendar page/function"),
	ERROR_CODE_RESERVATIONS_RESERVATION_MONTHLY_CALENDAR("RESERVATION_MONTHLY_CALENDAR",1700,"Error in show Reservation Monthly Calendar page/function"),
	
	ERROR_CODE_RESERVATIONS_RESERVATION_REPORTS("RESERVATION_REPORTS",1800,"Error in show Reservation Reports page/function"),
	
	ERROR_CODE_RESERVATIONS_RESERVATION_BOOKING_WINDOW_DISPLAY("RESERVATION_BOOKING_WINDOW_DISPLAY",1900,"Error in show Reservation booking window display page/function"),
	ERROR_CODE_RESERVATIONS_EDIT_CUSTOMER("EDIT_CUSTOMER",2000,"Error in show edit customer details display page/function"),
	ERROR_CODE_RESERVATIONS_UPDATE_CUSTOMER("UPDATE_CUSTOMER",2100,"Error in update customer details function"),
	
	ERROR_CODE_RESERVATIONS_RESERVATION_TABLE_PRINT_VIEW("TABLE_PRINT_VIEW",2200,"Error in show Reservation Table print view page/function"),
	
	ERROR_CODE_RESERVATIONS_SEARCH_RESERVATIONS("SEARCH_RESERVATIONS",2300,"Error in search Reservations page/function"),
	
	ERROR_CODE_CUSTOMER_DETAILS("CUSTOMER_DETAILS",2400,"Error in Customer Details page/function"),
	
	ERROR_CODE_BLOCKED_CUSTOMER_DETAILS("BLOCKED_CUSTOMER_DETAILS",2500,"Error in Blocked Customer Details page/function"),
	ERROR_CODE_BLOCKED_EDIT_CUSTOMER("BLOCKED_EDIT_CUSTOMER",2600,"Error in show edit blocked customer details display page/function"),
	
	ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS("AUTOMATIC_EMAIL_RESERVATION_REPORTS",2700,"Error in show Reservation Automatic Email Reports page/function"),
	ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_ADD("AUTOMATIC_EMAIL_RESERVATION_REPORTS_ADD",2701,"Error in add Automatic Email Reports page/function"),
	ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_SAVE("AUTOMATIC_EMAIL_RESERVATION_REPORTS_SAVE",2702,"Error in save Automatic Email Reports page/function"),
	ERROR_CODE_AUTOMATIC_EMAIL_RESERVATION_REPORTS_DELETE("AUTOMATIC_EMAIL_RESERVATION_REPORTS_DELETE",2703,"Error in delete Automatic Email Reports page/function"),
	
	ERROR_CODE_RESERVATIONS_RESERVATION_REMAINDERS("RESERVATION_REMAINDERS",2800,"Error in show Reservation Remainders view page/function"),
	ERROR_CODE_RESERVATION_REMAINDERS_EDIT_STATUS("RESERVATION_REMAINDERS_EDIT_STATUS",2801,"Error in edit Reservation Remainders phone status page/function"),
	ERROR_CODE_RESERVATION_REMAINDERS_UPDATE_STATUS("RESERVATION_REMAINDERS_UPDATE_STATUS",2802,"Error in update Reservation Remainders phone status page/function"),
	
	ERROR_CODE_SUPPORT_SHOW("SUPPORT_SHOW",2900,"Error in show Support page/function"),
	ERROR_CODE_SUPPORT_ADD("SUPPORT_ADD",2901,"Error in add Support page/function"),
	ERROR_CODE_SUPPORT_SAVE("SUPPORT_SAVE",2902,"Error in save Support page/function"),
	ERROR_CODE_SUPPORT_EDIT("SUPPORT_EDIT",2903,"Error in edit Support page/function"),
	ERROR_CODE_SUPPORT_UPDATE("SUPPORT_UPDATE",2904,"Error in update Support page/function"),
	
	ERROR_CODE_CALL_REPORT_SHOW("CALL_REPORT_SHOW",3000,"Error in show call report page/function"),
	ERROR_CODE_CALL_REPORT_GET_TRANS_STATES("CALL_REPORT_GET_TRANS_STATES",3001,"Error in get trans states page/function"),
	
	ERROR_CODE_SHOW_PRIVILEGE_SETTIMGS("SHOW_PRIVILEGE_SETTIMGS",3101,"Error in show privilege settings page/function"),
	ERROR_CODE_SEARCH_PRIVILEGE_SETTIMGS("SEARCH_PRIVILEGE_SETTIMGS",3102,"Error in search privilege settings page/function"),
	ERROR_CODE_PRIVILEGE_SETTIMGS_UPDATE("PRIVILEGE_SETTIMGS_UPDATE",3103,"Error in update privilege settings page/function"),
	
	ERROR_CODE_EDIT_CONFIRMATION_EMAIL_SHOW("EDIT_CONFIRMATION_EMAIL_SHOW",3201,"Error in show Edit Confirmation Email page/function"),
	ERROR_CODE_EDIT_CONFIRMATION_EMAIL_GET("EDIT_CONFIRMATION_EMAIL_GET",3202,"Error in get Edit Confirmation Email page/function"),
	ERROR_CODE_EDIT_CONFIRMATION_EMAIL_SAVE("EDIT_CONFIRMATION_EMAIL_SAVE",3203,"Error in get Save Confirmation Email page/function"),
	
	ERROR_CODE_EDIT_LANDING_PAGE_SHOW("EDIT_LANDING_PAGE_SHOW",3301,"Error in show Edit landing page text page/function"),
	ERROR_CODE_EDIT_LANDING_PAGE_GET("EDIT_LANDING_PAGE_GET",3302,"Error in get Edit landing page text page/function"),
	ERROR_CODE_EDIT_LANDING_PAGE_SAVE("EDIT_LANDING_PAGE_SAVE",3303,"Error in get Save landing page text page/function"),
	
	ERROR_CODE_EDIT_REMAINDER_EMAIL_SHOW("EDIT_REMAINDER_EMAIL_SHOW",3401,"Error in show Edit Remainder Email page/function"),
	ERROR_CODE_EDIT_REMAINDER_EMAIL_GET("EDIT_REMAINDER_EMAIL_GET",3402,"Error in get Edit Remainder Email page/function"),
	ERROR_CODE_EDIT_REMAINDER_EMAIL_SAVE("EDIT_REMAINDER_EMAIL_SAVE",3403,"Error in get Save Remainder Email page/function"),
	
	;
	
    private String page;
	private int errorCode;
	private String description;

	private ErrorCodeConstants(String page,int errorCode,String description) {
		this.page = page;
		this.errorCode = errorCode;
		this.description = description;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}