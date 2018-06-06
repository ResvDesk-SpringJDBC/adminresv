package com.telappoint.adminresv.form;

/**
 * 
 * @author Murali G
 *
 */
public class NotificationRequest {

	private String locationId = "-1";
	private String campaignId = "-1";
	private String selectedDateDisplayFormat;
	private String jsCalendarDate;
	private String selectedDate;
	private String changedParameter;
	private String previousAvailableDate;
	private String nextAvailableDate;
	
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	public String getSelectedDateDisplayFormat() {
		return selectedDateDisplayFormat;
	}
	public void setSelectedDateDisplayFormat(String selectedDateDisplayFormat) {
		this.selectedDateDisplayFormat = selectedDateDisplayFormat;
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
	public String getChangedParameter() {
		return changedParameter;
	}
	public void setChangedParameter(String changedParameter) {
		this.changedParameter = changedParameter;
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
}
