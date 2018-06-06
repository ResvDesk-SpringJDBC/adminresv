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
public class CalendarOverviewDetailsResponse extends BaseResponse {
	private List<CalendarOverviewDetails> calendarOverviewDetailsList;

	public List<CalendarOverviewDetails> getCalendarOverviewDetailsList() {
		return calendarOverviewDetailsList;
	}

	public void setCalendarOverviewDetailsList(List<CalendarOverviewDetails> calendarOverviewDetailsList) {
		this.calendarOverviewDetailsList = calendarOverviewDetailsList;
	}
}
