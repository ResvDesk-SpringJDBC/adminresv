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
public class LandingPageContentTO extends BaseRequest {

	private int id;
	private String device;
	private String landingPageLangCode;
	private String landingPageKey;
	private String landingPageText;
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
		
	public void setDevice(String value) {
		this.device = value;
	}
	
	public String getDevice() {
		return device;
	}
			
	public String getLandingPageKey() {
		return landingPageKey;
	}

	public void setLandingPageKey(String landingPageKey) {
		this.landingPageKey = landingPageKey;
	}

	public String getLandingPageText() {
		return landingPageText;
	}

	public void setLandingPageText(String landingPageText) {
		this.landingPageText = landingPageText;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getId());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("LandingPageContent[ ");
			sb.append("Id=").append(getId()).append(" ");
			sb.append("Device=").append(getDevice()).append(" ");
			sb.append("landingPageLangCode=").append(getLandingPageLangCode()).append(" ");
			sb.append("Key=").append(getLandingPageKey()).append(" ");
			sb.append("Text=").append(getLandingPageText()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

	public String getLandingPageLangCode() {
		return landingPageLangCode;
	}

	public void setLandingPageLangCode(String landingPageLangCode) {
		this.landingPageLangCode = landingPageLangCode;
	}	
}
