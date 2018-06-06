package com.telappoint.adminresv.restws.model;

import java.util.List;

/**
 * 
 * @author Murali G
 *
 */
public class InBoundCallReportResponse extends BaseResponse {
	
	private List<InBoundIvrCalls> ivrcallList;
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

	public List<InBoundIvrCalls> getIvrcallList() {
		return ivrcallList;
	}

	public void setIvrcallList(List<InBoundIvrCalls> ivrcallList) {
		this.ivrcallList = ivrcallList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
