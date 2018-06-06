package com.telappoint.adminresv.restws.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Murali G
 *
 */
public class EventListResponse extends BaseResponse {
	private List<Event> eventList = new ArrayList<Event>();

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
}
