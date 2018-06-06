package com.telappoint.adminresv.form;

/**
 * 
 * @author Murali G
 *
 */

public class CalendarBaseRequest {
	
	private String customerId = "-1";
	private String customerName = "";
	private String locationId = "-1";
	private String eventId = "-1";
	private String date = "";
	private String time = "-1";
	private String changedParameter;
	
	private String jsCalendarDate;
	private String selectedDate;
	private String selectedDateDisplayFormat;
	private String previousAvailableDate;
	private String nextAvailableDate;
	
	private boolean showRemainderIcons;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getChangedParameter() {
		return changedParameter;
	}
	public void setChangedParameter(String changedParameter) {
		this.changedParameter = changedParameter;
	}
	public String getJsCalendarDate() {
		return jsCalendarDate;
	}
	public void setJsCalendarDate(String jsCalendarDate) {
		this.jsCalendarDate = jsCalendarDate;
	}
	public String getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}
	public String getSelectedDateDisplayFormat() {
		return selectedDateDisplayFormat;
	}
	public void setSelectedDateDisplayFormat(String selectedDateDisplayFormat) {
		this.selectedDateDisplayFormat = selectedDateDisplayFormat;
	}
	public String getPreviousAvailableDate() {
		return previousAvailableDate;
	}
	public void setPreviousAvailableDate(String previousAvailableDate) {
		this.previousAvailableDate = previousAvailableDate;
	}
	public String getNextAvailableDate() {
		return nextAvailableDate;
	}
	public void setNextAvailableDate(String nextAvailableDate) {
		this.nextAvailableDate = nextAvailableDate;
	}
	public boolean getShowRemainderIcons() {
		return showRemainderIcons;
	}
	public void setShowRemainderIcons(boolean showRemainderIcons) {
		this.showRemainderIcons = showRemainderIcons;
	}
}
