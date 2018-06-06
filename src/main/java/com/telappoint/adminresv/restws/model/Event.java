package com.telappoint.adminresv.restws.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.telappoint.adminresv.form.BaseRequest;

/**
 * 
 * @author Balaji N
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Event extends BaseRequest {
	
	private long eventId;
	private String eventName;
	// onlineresv
	private Integer locationId;
	private String locationName;

	// adminresv
	private String locationIds;
	private String locationNames;

	private String eventNameIvrTts;
	private String eventNameIvrAudio;
	private String sendReminder;
	private String enable = "Y";
	private int duration = 60;
	 
	@JsonIgnore
	private String[]  selectedLocIdsList; //This we are using for Add page and in Edit page we are using dissabled ones
	@JsonIgnore
	private String[]  updateSelectedLocIdsList; //This we are using for Edit page
	
	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getEventNameIvrTts() {
		return eventNameIvrTts;
	}

	public void setEventNameIvrTts(String eventNameIvrTts) {
		this.eventNameIvrTts = eventNameIvrTts;
	}

	public String getEventNameIvrAudio() {
		return eventNameIvrAudio;
	}

	public void setEventNameIvrAudio(String eventNameIvrAudio) {
		this.eventNameIvrAudio = eventNameIvrAudio;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getSendReminder() {
		return sendReminder;
	}

	public void setSendReminder(String sendReminder) {
		this.sendReminder = sendReminder;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String[] getSelectedLocIdsList() {
		return selectedLocIdsList;
	}

	public void setSelectedLocIdsList(String[] selectedLocIdsList) {
		this.selectedLocIdsList = selectedLocIdsList;
	}

	public String getLocationIds() {
		return locationIds;
	}

	public void setLocationIds(String locationIds) {
		this.locationIds = locationIds;
	}

	public String getLocationNames() {
		return locationNames;
	}

	public void setLocationNames(String locationNames) {
		this.locationNames = locationNames;
	}

	public String[] getUpdateSelectedLocIdsList() {
		return updateSelectedLocIdsList;
	}

	public void setUpdateSelectedLocIdsList(String[] updateSelectedLocIdsList) {
		this.updateSelectedLocIdsList = updateSelectedLocIdsList;
	}

}
