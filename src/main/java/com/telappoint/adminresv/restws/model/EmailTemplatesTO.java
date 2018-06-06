package com.telappoint.adminresv.restws.model;


import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.telappoint.adminresv.form.BaseRequest;
/**
 * 
 * @author Murali
 * 
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class EmailTemplatesTO extends BaseRequest {
	
	private int id;
	private String mailLangCode;
	private String message_key;
	private String message_value;
	
	public String getMailLangCode() {
		return mailLangCode;
	}
	public void setMailLangCode(String mailLangCode) {
		this.mailLangCode = mailLangCode;
	}
	public String getMessage_key() {
		return message_key;
	}
	public void setMessage_key(String message_key) {
		this.message_key = message_key;
	}
	public String getMessage_value() {
		return message_value;
	}
	public void setMessage_value(String message_value) {
		this.message_value = message_value;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}