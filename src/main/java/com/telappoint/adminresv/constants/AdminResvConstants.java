package com.telappoint.adminresv.constants;


/**
 * @author Murali G
 * 
 */
public interface AdminResvConstants {
	
	public final static String DEVICE_NAME = "admin";
	
	public final String RESVDESK_APP_CODE = "RESVDESK";
	
	public final static int LOOKUP_ACCESS_LEVEL_SUPERUSER = 9;
	public final static int USER_PRIVILEGE_APPT_SCHEDULER = 1;
	public final static int USER_PRIVILEGE_MANAGER = 2;
	public final static int USER_PRIVILEGE_ADMINISTRATOR = 3;
	public final static int USER_PRIVILEGE_RESOURCE = 4;
	public final static int USER_PRIVILEGE_LOCATION = 5;
	
	public final static String USER_PASSWORD_INVALID = "Please enter a valid password (minimum 6 characters).<br>";
	public final static String USERNAME_EMPTY = "Please enter your Username.<br>";
	public final static String PASSWORD_EMPTY = "Please enter your Password.<br>";
	public final static String APPCODE = "resv";// should be same client table appCode

	public final static int GOTO_SERVICE_EXCEPTION = 407;

	public final static String DISPLAY_ERROR_TEXT = "We are sorry, but your operation was unexpectedly interrupted.  This is most likely a temporary error and you should be able to try again immediately.  However, if you get this message again, please contact TelAppoint by sending an email to <a href=\"mailto:info@telappoint.com?subject=Appointment Schedule help\">info@telappoint.com</a>.  When calling or writing, please include your full name, home phone number and the error number you received.";
	public final static String DINAMIC_DISPLAY_TABLE_PROPETY_NOT_AVAILABLE = "Wrong Property Configured - ";
	
}