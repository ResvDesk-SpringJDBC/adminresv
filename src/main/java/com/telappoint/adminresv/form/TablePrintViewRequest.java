package com.telappoint.adminresv.form;


/**
 * 
 * @author Murali G
 *
 */

public class TablePrintViewRequest extends CalendarBaseRequest{
	
	private String[]  selectedEventIdsList;
	private String  eventIds;
	
	public String[] getSelectedEventIdsList() {
		return selectedEventIdsList;
	}
	public void setSelectedEventIdsList(String[] selectedEventIdsList) {
		this.selectedEventIdsList = selectedEventIdsList;
	}
	public String getEventIds() {
		return eventIds;
	}
	public void setEventIds(String eventIds) {
		this.eventIds = eventIds;
	}	
}
