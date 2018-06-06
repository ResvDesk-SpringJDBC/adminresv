package com.telappoint.adminresv.restws.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Murali G
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CalendarOverviewResponse extends BaseResponse {
	private List<CalendarOverview> calendarOverViewList;

	public List<CalendarOverview> getCalendarOverViewList() {
		return calendarOverViewList;
	}

	public void setCalendarOverViewList(List<CalendarOverview> calendarOverViewList) {
		this.calendarOverViewList = calendarOverViewList;
	}
}
