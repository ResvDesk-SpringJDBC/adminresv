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
public class PastReservationResponse extends BaseResponse {
	List<ReservationDetails> resvDetails;

	public List<ReservationDetails> getResvDetails() {
		return resvDetails;
	}

	public void setResvDetails(List<ReservationDetails> resvDetails) {
		this.resvDetails = resvDetails;
	}
}
