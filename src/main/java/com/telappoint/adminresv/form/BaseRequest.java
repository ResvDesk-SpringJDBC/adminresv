package com.telappoint.adminresv.form;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 
 * @author Murali G
 * 
 */
public class BaseRequest {
	
	protected String langCode;
	protected String clientCode;
	private String userName="";
	@JsonIgnore
	private long userId;
	private String transId;
	private String device;
	@JsonIgnore
	private String token="";

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
