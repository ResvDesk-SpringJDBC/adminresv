package com.telappoint.adminresv.form;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Murali G
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ReservationReportRequest{
	private Integer locationId;
	private Long eventId;
	private String startDate;
	private String endDate;
	
	@JsonIgnore
	private String[] resvStatusFields;	
	@JsonIgnore
	private String selectedStatusFields;	
	@JsonIgnore
	private List<Integer> otherResvStatusList;
	
	@JsonIgnore
	private String[] eventDateTimeIds;	
	@JsonIgnore
	private String selectedEventDateTimeIds;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String[] getResvStatusFields() {
		return resvStatusFields;
	}

	public void setResvStatusFields(String[] resvStatusFields) {
		this.resvStatusFields = resvStatusFields;
	}

	public String getSelectedStatusFields() {
		return selectedStatusFields;
	}

	public void setSelectedStatusFields(String selectedStatusFields) {
		this.selectedStatusFields = selectedStatusFields;
	}

	public List<Integer> getOtherResvStatusList() {
		return otherResvStatusList;
	}

	public void setOtherResvStatusList(List<Integer> otherResvStatusList) {
		this.otherResvStatusList = otherResvStatusList;
	}

	public String[] getEventDateTimeIds() {
		return eventDateTimeIds;
	}

	public void setEventDateTimeIds(String[] eventDateTimeIds) {
		this.eventDateTimeIds = eventDateTimeIds;
	}

	public String getSelectedEventDateTimeIds() {
		return selectedEventDateTimeIds;
	}

	public void setSelectedEventDateTimeIds(String selectedEventDateTimeIds) {
		this.selectedEventDateTimeIds = selectedEventDateTimeIds;
	}
}
