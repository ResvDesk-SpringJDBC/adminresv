package com.telappoint.adminresv.restws.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.telappoint.adminresv.form.BaseRequest;
import com.telappoint.adminresv.utils.DateUtils;

/**
 * 
 * @author Murali G
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class EventDateTime extends BaseRequest {
	
	private long eventDateTimeId;
	private long eventId;
	private int locationId;
	private String locationName;
	private String eventName;
	private String date;
	private String time;
	private String noOfSeats;
	private String enable = "Y";
	private int noOfNotifiedReservations;
	
	@JsonIgnore
	private String timeHr;
	@JsonIgnore
	private String timeMin;
	@JsonIgnore
	private String timeAmPm;
	@JsonIgnore
	private boolean showEditIcon;

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(String noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public long getEventDateTimeId() {
		return eventDateTimeId;
	}

	public void setEventDateTimeId(long eventDateTimeId) {
		this.eventDateTimeId = eventDateTimeId;
	}

	public String getTimeHr() {
		if(time!=null && !"".equals(time)){//"time":"09:00 AM"
			timeHr = (time.split(" ")[0]).split(":")[0];
		}
		return timeHr;
	}

	public void setTimeHr(String timeHr) {
		this.timeHr = timeHr;
	}

	public String getTimeMin() {
		if(time!=null && !"".equals(time)){//"time":"09:00 AM"
			timeMin = (time.split(" ")[0]).split(":")[1];
		}
		return timeMin;
	}

	public void setTimeMin(String timeMin) {
		this.timeMin = timeMin;
	}

	public String getTimeAmPm() {
		if(time!=null && !"".equals(time)){//"time":"09:00 AM"
			timeAmPm = time.split(" ")[1];
		}
		return timeAmPm;
	}

	public void setTimeAmPm(String timeAmPm) {
		this.timeAmPm = timeAmPm;
	}

	public int getNoOfNotifiedReservations() {
		return noOfNotifiedReservations;
	}

	public void setNoOfNotifiedReservations(int noOfNotifiedReservations) {
		this.noOfNotifiedReservations = noOfNotifiedReservations;
	}

	public boolean isShowEditIcon() {
		Date eventDate = DateUtils.getDateFromString(date+" "+time,DateUtils.MMDDYYYYHHMMSS_TWELWE_HOURS);
		if(eventDate.compareTo(new Date())>=0){
			showEditIcon = true;
		}
		//System.out.println("Date :: "+new Date()+" ,eventDate :: "+eventDate+" , showEditIcon ::: "+showEditIcon);
		return showEditIcon;
	}

	public void setShowEditIcon(boolean showEditIcon) {
		this.showEditIcon = showEditIcon;
	}
}
