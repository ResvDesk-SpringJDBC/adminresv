package com.telappoint.adminresv.restws.model;

import java.util.List;

/**
 * 
 * @author Murali G
 *
 */
public class EventDateTimeResponse extends BaseResponse {
	private List<EventDateTime> eventDateTimeList;

	public List<EventDateTime> getEventDateTimeList() {
		return eventDateTimeList;
	}

	public void setEventDateTimeList(List<EventDateTime> eventDateTimeList) {
		this.eventDateTimeList = eventDateTimeList;
	}

}
