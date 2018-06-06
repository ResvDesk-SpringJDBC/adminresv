package com.telappoint.adminresv.restws.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Murali G
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SeatsCalendarView  extends BaseResponse {

	public Object[][] seatView;
	private String eventDateTimeId;
	private String title;
	private String javaRef;
	private String hides;
	
	public void init(int row, int column) {
		seatView = new Object[row][column];
	}

	public Object[][] getSeatView() {
		return seatView;
	}

	public void setSeatView(Object[][] seatView) {
		this.seatView = seatView;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJavaRef() {
		return javaRef;
	}

	public void setJavaRef(String javaRef) {
		this.javaRef = javaRef;
	}

	public String getEventDateTimeId() {
		return eventDateTimeId;
	}

	public void setEventDateTimeId(String eventDateTimeId) {
		this.eventDateTimeId = eventDateTimeId;
	}

	public String getHides() {
		return hides;
	}

	public void setHides(String hides) {
		this.hides = hides;
	}	
}
