package com.telappoint.adminresv.restws.model;

/**
 * 
 * @author Murali G
 *
 */
public class DailyReminderView {
	private String totalNotifications;
	private String totalConfirmed;
	private Long eventId;
	private String eventName;
	
	public String getTotalNotifications() {
		return totalNotifications;
	}

	public void setTotalNotifications(String totalNotifications) {
		this.totalNotifications = totalNotifications;
	}

	public String getTotalConfirmed() {
		return totalConfirmed;
	}

	public void setTotalConfirmed(String totalConfirmed) {
		this.totalConfirmed = totalConfirmed;
	}
	
	@Override
	public String toString() {
		return "DailyReminderView [totalNotifications=" + totalNotifications + ", totalConfirmed=" + totalConfirmed + ", eventId=" + eventId + ", eventName=" + eventName+ "]";
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
}
