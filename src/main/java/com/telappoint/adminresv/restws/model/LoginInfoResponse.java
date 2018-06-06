package com.telappoint.adminresv.restws.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class LoginInfoResponse extends BaseResponse {
	
	// online field
	private List<LoginField> loginFieldList;
	
	// ivr fields
	private String submitFieldList;

	public List<LoginField> getLoginFieldList() {
		return loginFieldList;
	}

	public void setLoginFieldList(List<LoginField> loginFieldList) {
		this.loginFieldList = loginFieldList;
	}

	public String getSubmitFieldList() {
		return submitFieldList;
	}

	public void setSubmitFieldList(String submitFieldList) {
		this.submitFieldList = submitFieldList;
	}
}
