package com.telappoint.adminresv.restws.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.telappoint.adminresv.form.BaseRequest;

/**
 * 
 * @author Murali G
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Location  extends BaseRequest{
	private int locationId;
	private String locationName;
	private String locationNameIvrTts;
	private String locationNameIvrAudio;
	private String website;
	private String workPhone;
	private String fax;
	private String address;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String timeZone;
	private String enable="Y";
	
	@JsonIgnore
	private String workPhone1;
	@JsonIgnore
	private String workPhone2;
	@JsonIgnore
	private String workPhone3;
	
	@JsonIgnore
	private String zip1;
	@JsonIgnore
	private String zip2;
	
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLocationNameIvrTts() {
		return locationNameIvrTts;
	}
	public void setLocationNameIvrTts(String locationNameIvrTts) {
		this.locationNameIvrTts = locationNameIvrTts;
	}
	public String getLocationNameIvrAudio() {
		return locationNameIvrAudio;
	}
	public void setLocationNameIvrAudio(String locationNameIvrAudio) {
		this.locationNameIvrAudio = locationNameIvrAudio;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getWorkPhone() {
		return getWorkPhone1() == null ? workPhone : getWorkPhone1()+""+getWorkPhone2()+""+getWorkPhone3();
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return getZip1() == null ? zip : getZip1()+""+getZip2();
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getWorkPhone1() {
		return this.workPhone1;
	}
	public void setWorkPhone1(String workPhone1) {
		this.workPhone1 = workPhone1;
	}
	public String getWorkPhone2() {
		return this.workPhone2;
	}
	public void setWorkPhone2(String workPhone2) {
		this.workPhone2 = workPhone2;
	}
	public String getWorkPhone3() {
		return this.workPhone3;
	}
	public void setWorkPhone3(String workPhone3) {
		this.workPhone3 = workPhone3;
	}
	public String getZip1() {
		return this.zip1;
	}
	public void setZip1(String zip1) {
		this.zip1 = zip1;
	}
	public String getZip2() {
		return this.zip2;
	}
	public void setZip2(String zip2) {
		this.zip2 = zip2;
	}
	
	private String getSubPart(String phone, int beginIndex, int endIndex) {
		try{
			if(endIndex==-1){
				if(phone!=null && !"".equals(phone)){
					endIndex =  phone.length();
				}
			}
			if(phone!=null && !"".equals(phone)){
				if(endIndex>phone.length()){
					endIndex = phone.length();
				}
			}
			if(phone!=null && !"".equals(phone) && !"--".equals(phone) && phone.length()>=(endIndex)){
				return phone.substring(beginIndex,endIndex);
			}			
		}catch(Exception e){
		}
		return "";
	}
	
	public void populateDisplayFieldsData(){
		this.workPhone1 = getSubPart(workPhone,0,3);
		this.workPhone2 = getSubPart(workPhone,3,6);
		this.workPhone3 = getSubPart(workPhone,6,-1);
		this.zip1 = getSubPart(zip,0,5);
		this.zip2 = getSubPart(zip,5,-1);
	}
	
}
