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
public class ReservationReportResponse extends BaseResponse {
	private List<ReservationDetails> reportsList;

	private String title;
	private String javaRef;
	private String hides;
	
	public List<ReservationDetails> getReportsList() {
		return reportsList;
	}

	public void setReportsList(List<ReservationDetails> reportsList) {
		this.reportsList = reportsList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJavaRef() {
		return javaRef;
	}

	public void setJavaRef(String javaRef) {
		this.javaRef = javaRef;
	}

	public String getHides() {
		return hides;
	}

	public void setHides(String hides) {
		this.hides = hides;
	}
}
