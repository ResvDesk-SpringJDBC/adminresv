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
public class ReservationReportConfigResponse extends BaseResponse {
	private List<ReservationReportConfig> resvReportConfigList;

	public List<ReservationReportConfig> getResvReportConfigList() {
		return resvReportConfigList;
	}

	public void setResvReportConfigList(List<ReservationReportConfig> resvReportConfigList) {
		this.resvReportConfigList = resvReportConfigList;
	}
}
