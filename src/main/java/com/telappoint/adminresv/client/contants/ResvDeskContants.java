package com.telappoint.adminresv.client.contants;

/**
 * @author Murali
 */
public enum ResvDeskContants {

	LANG_CODE("us-en"),
	DEVICE_TYPE_ADMIN("admin"),
	//DEVICE_TYPE_ONLINE("online"),
	
	SEARCH_CATEGORY_RESERVATION_SEARCH("resv_search_fields"),
	SEARCH_CATEGORY_CLIENT_SEARCH("client_search_fields"),
	
	ONLINE_EMAIL_APPT_CONF_BODY("ONLINE_EMAIL_APPT_CONF_BODY"),
	ONLINE_EMAIL_APPT_REMIND_BODY("ONLINE_EMAIL_APPT_REMIND_BODY"),
	;

	private String value;

	private ResvDeskContants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
