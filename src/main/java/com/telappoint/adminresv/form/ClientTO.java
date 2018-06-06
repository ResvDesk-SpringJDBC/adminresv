package com.telappoint.adminresv.form;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.telappoint.adminresv.utils.AdminUtils;

/**
 * 
 * @author Murali
 * 
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ClientTO {
	private Integer id;

	private String client_name;

	private String client_code;

	private String contact_person;

	private String contact_phone;

	private String contact_email;

	private String website;

	private String phone_transfer_number;

	private String fax;

	private String address;

	private String address2;

	private String city;

	private String state;

	private String zip;

	private String country;

	private String appt_link;

	private String forward_url;

	private String business_type;

	private String appcode;

	private String db_server;

	private String db_name;

	private String license_key;

	private String date_subscribed;

	private String date_cancelled;

	private Character delete_flag = new Character('N');

	private Character active = new Character('Y');

	private Character locked = new Character('N');

	private String group_account;

	private String channel_partner_id;

	private String sales_rep;

	private Character billable = new Character('N');

	private String dbUserName;

	private String dbPassword;	
	
	private String client_dnis_1;
	private String client_dnis_2;
	private String client_dnis_3;
	private String extension;
	private String redirect_url;
	private String direct_access_number;

	@JsonIgnore
	private String phone_scheduler_number;
	@JsonIgnore
	private String direct_access_phone;
	
	public Character getActive() {
		return active;
	}

	public void setActive(Character active) {
		this.active = active;
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

	public String getAppcode() {
		return appcode;
	}

	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}

	public String getAppt_link() {
		return appt_link;
	}

	public void setAppt_link(String appt_link) {
		this.appt_link = appt_link;
	}

	public Character getBillable() {
		return billable;
	}

	public void setBillable(Character billable) {
		this.billable = billable;
	}

	public String getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}

	public String getChannel_partner_id() {
		return channel_partner_id;
	}

	public void setChannel_partner_id(String channel_partner_id) {
		this.channel_partner_id = channel_partner_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDate_cancelled() {
		return date_cancelled;
	}

	public void setDate_cancelled(String date_cancelled) {
		this.date_cancelled = date_cancelled;
	}

	public String getDate_subscribed() {
		return date_subscribed;
	}

	public void setDate_subscribed(String date_subscribed) {
		this.date_subscribed = date_subscribed;
	}

	public String getDb_name() {
		return db_name;
	}

	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}

	public String getDb_server() {
		return db_server;
	}

	public void setDb_server(String db_server) {
		this.db_server = db_server;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public Character getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Character delete_flag) {
		this.delete_flag = delete_flag;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getForward_url() {
		return forward_url;
	}

	public void setForward_url(String forward_url) {
		this.forward_url = forward_url;
	}

	public String getGroup_account() {
		return group_account;
	}

	public void setGroup_account(String group_account) {
		this.group_account = group_account;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLicense_key() {
		return license_key;
	}

	public void setLicense_key(String license_key) {
		this.license_key = license_key;
	}

	public Character getLocked() {
		return locked;
	}

	public void setLocked(Character locked) {
		this.locked = locked;
	}

	public String getPhone_transfer_number() {
		return phone_transfer_number;
	}

	public void setPhone_transfer_number(String phone_transfer_number) {
		this.phone_transfer_number = phone_transfer_number;
	}

	public String getSales_rep() {
		return sales_rep;
	}

	public void setSales_rep(String sales_rep) {
		this.sales_rep = sales_rep;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getId());
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("Client[ ");
			sb.append("Id=").append(getId()).append(" ");
			sb.append("Client_name=").append(getClient_name()).append(" ");
			sb.append("Client_code=").append(getClient_code()).append(" ");
			sb.append("Contact_person=").append(getContact_person()).append(" ");
			sb.append("Contact_phone=").append(getContact_phone()).append(" ");
			sb.append("Contact_email=").append(getContact_email()).append(" ");
			sb.append("Website=").append(getWebsite()).append(" ");
			sb.append("Phone_transfer_number=").append(getPhone_transfer_number()).append(" ");
			sb.append("Fax=").append(getFax()).append(" ");
			sb.append("Address=").append(getAddress()).append(" ");
			sb.append("Address2=").append(getAddress2()).append(" ");
			sb.append("City=").append(getCity()).append(" ");
			sb.append("State=").append(getState()).append(" ");
			sb.append("Zip=").append(getZip()).append(" ");
			sb.append("Country=").append(getCountry()).append(" ");
			sb.append("Appt_link=").append(getAppt_link()).append(" ");
			sb.append("Forward_url=").append(getForward_url()).append(" ");
			sb.append("Business_type=").append(getBusiness_type()).append(" ");
			sb.append("Appcode=").append(getAppcode()).append(" ");
			sb.append("Db_server=").append(getDb_server()).append(" ");
			sb.append("Db_name=").append(getDb_name()).append(" ");
			sb.append("License_key=").append(getLicense_key()).append(" ");
			sb.append("Date_subscribed=").append(getDate_subscribed()).append(" ");
			sb.append("Date_cancelled=").append(getDate_cancelled()).append(" ");
			sb.append("Delete_flag=").append(getDelete_flag()).append(" ");
			sb.append("Active=").append(getActive()).append(" ");
			sb.append("Locked=").append(getLocked()).append(" ");
			sb.append("Group_account=").append(getGroup_account()).append(" ");
			sb.append("Channel_partner_id=").append(getChannel_partner_id()).append(" ");
			sb.append("Sales_rep=").append(getSales_rep()).append(" ");
			sb.append("Billable=").append(getBillable()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

	public String getClient_dnis_1() {
		return client_dnis_1;
	}

	public void setClient_dnis_1(String client_dnis_1) {
		this.client_dnis_1 = client_dnis_1;
	}

	public String getClient_dnis_2() {
		return client_dnis_2;
	}

	public void setClient_dnis_2(String client_dnis_2) {
		this.client_dnis_2 = client_dnis_2;
	}

	public String getClient_dnis_3() {
		return client_dnis_3;
	}

	public void setClient_dnis_3(String client_dnis_3) {
		this.client_dnis_3 = client_dnis_3;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getRedirect_url() {
		return redirect_url;
	}

	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}

	public String getDirect_access_number() {
		return direct_access_number;
	}

	public void setDirect_access_number(String direct_access_number) {
		this.direct_access_number = direct_access_number;
	}
	/*
	 * private String client_dnis_1;
	private String client_dnis_2;
	private String client_dnis_3;
	 */
	public String getPhone_scheduler_number() {
		String clientdnis1 = AdminUtils.formatPhoneNumber(getClient_dnis_1());
		String clientdnis2 = AdminUtils.formatPhoneNumber(getClient_dnis_2());
		String clientdnis3 = AdminUtils.formatPhoneNumber(getClient_dnis_3());
		StringBuilder phoneSchedulerNumber = new StringBuilder();
		if(AdminUtils.isValid(clientdnis1) || AdminUtils.isValid(clientdnis2) || AdminUtils.isValid(clientdnis3)){
			if(AdminUtils.isValid(clientdnis1)){
				phoneSchedulerNumber.append(clientdnis1);
			}
			if(AdminUtils.isValid(clientdnis1) && AdminUtils.isValid(clientdnis2)){
				phoneSchedulerNumber.append(" / ");
			}
			if(AdminUtils.isValid(clientdnis2)){
				phoneSchedulerNumber.append(clientdnis2);
			}
			if((AdminUtils.isValid(clientdnis1) && AdminUtils.isValid(clientdnis3)) || (AdminUtils.isValid(clientdnis2) && AdminUtils.isValid(clientdnis3))){
				phoneSchedulerNumber.append(" / ");
			}
			if(AdminUtils.isValid(clientdnis3)){
				phoneSchedulerNumber.append(clientdnis3);
			}
		}
		return phoneSchedulerNumber.toString();
	}
	
	

	public void setPhone_scheduler_number(String phone_scheduler_number) {
		this.phone_scheduler_number = phone_scheduler_number;
	}

	public String getDirect_access_phone() {
		String direct_access_number = AdminUtils.formatPhoneNumber(getDirect_access_number());
		String extension = getExtension();
		StringBuilder directAccessPhone = new StringBuilder();
		if(AdminUtils.isValid(direct_access_number) && AdminUtils.isValid(extension)){
			directAccessPhone.append(direct_access_number);
			directAccessPhone.append(" Ext ");
			directAccessPhone.append(extension);
		}
		return directAccessPhone.toString();
	}

	public void setDirect_access_phone(String direct_access_phone) {
		this.direct_access_phone = direct_access_phone;
	}

}
