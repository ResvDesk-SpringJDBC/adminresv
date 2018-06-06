package com.telappoint.adminresv.constants;

/**
 * This enum is used to get the all types of date and time string formats.
 * 
 * @author Murali
 */

public enum ResvDateContants {
	DATETIME_FORMAT_YYYYMMDDHHMMSS("yyyy-MM-dd hh:mm:ss"),
	DATE_FORMAT_MMDDYYYY("MM/dd/yyyy");

	private String value;

	private ResvDateContants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
