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
public class ReminderStatusResponse extends BaseResponse {
	private List<ReminderStatus> reminderStatusList;

	public List<ReminderStatus> getReminderStatusList() {
		return reminderStatusList;
	}

	public void setReminderStatusList(List<ReminderStatus> reminderStatusList) {
		this.reminderStatusList = reminderStatusList;
	}

	
}
