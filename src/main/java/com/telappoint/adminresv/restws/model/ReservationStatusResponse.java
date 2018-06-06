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
public class ReservationStatusResponse extends BaseResponse {
	private List<ReservationStatus> resvStatusList;

	public List<ReservationStatus> getResvStatusList() {
		return resvStatusList;
	}

	public void setResvStatusList(List<ReservationStatus> resvStatusList) {
		this.resvStatusList = resvStatusList;
	}
	
}
