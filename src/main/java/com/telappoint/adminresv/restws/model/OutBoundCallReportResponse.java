package com.telappoint.adminresv.restws.model;

import java.util.List;

/**
 * 
 * @author Murali G
 *
 */
public class OutBoundCallReportResponse extends BaseResponse {
	
	private List<OutBoundIvrCalls> ivrcallList;
	private String title;
	private String hides;
	private String javaRef;

	public String getHides() {
		return hides;
	}

	public void setHides(String hides) {
		this.hides = hides;
	}

	public String getJavaRef() {
		return javaRef;
	}

	public void setJavaRef(String javaRef) {
		this.javaRef = javaRef;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<OutBoundIvrCalls> getIvrcallList() {
		return ivrcallList;
	}

	public void setIvrcallList(List<OutBoundIvrCalls> ivrcallList) {
		this.ivrcallList = ivrcallList;
	}
}
