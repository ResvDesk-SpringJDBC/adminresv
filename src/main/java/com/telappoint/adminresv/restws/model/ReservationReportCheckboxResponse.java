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
public class ReservationReportCheckboxResponse extends BaseResponse {
	private List<ReportDateTimeCheckBox> reportDateTimeCheckBoxList;

	public List<ReportDateTimeCheckBox> getReportDateTimeCheckBoxList() {
		return reportDateTimeCheckBoxList;
	}

	public void setReportDateTimeCheckBoxList(List<ReportDateTimeCheckBox> reportDateTimeCheckBoxList) {
		this.reportDateTimeCheckBoxList = reportDateTimeCheckBoxList;
	}
}
