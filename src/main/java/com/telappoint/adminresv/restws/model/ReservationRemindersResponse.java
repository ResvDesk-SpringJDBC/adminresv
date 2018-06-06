package com.telappoint.adminresv.restws.model;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Murali G
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ReservationRemindersResponse extends BaseResponse {
	private Map<DailyReminderView, List<Notification>> resvReminders;
	private String remindStatusNeedAppt;
	private boolean showTime;

	public Map<DailyReminderView, List<Notification>> getResvReminders() {
		return resvReminders;
	}

	public void setResvReminders(Map<DailyReminderView, List<Notification>> resvReminders) {
		this.resvReminders = resvReminders;
	}

	public String getRemindStatusNeedAppt() {
		return remindStatusNeedAppt;
	}

	public void setRemindStatusNeedAppt(String remindStatusNeedAppt) {
		this.remindStatusNeedAppt = remindStatusNeedAppt;
	}

	public boolean isShowTime() {
		return showTime;
	}

	public void setShowTime(boolean showTime) {
		this.showTime = showTime;
	}
	
	
}
