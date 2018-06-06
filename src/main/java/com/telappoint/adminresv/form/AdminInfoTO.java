/**
 * 
 */
package com.telappoint.adminresv.form;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author Murali G
 * 
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AdminInfoTO {
	
	private String login;
	private String clientCode;
	private String accessLevel;
	private String locationAccess;
	private String resourceAccess;
	private String passwordExpiryAlert;
	private String firstName;
	private String lastName;
	private String contactPhone;
	private String contactEmail;
	private String lastLoginDateTime;
	private String lastLoginIP;
	private String displaySLA;
	private String slaText;
	private String slaSkip;
	private String forwardURL;
	private Integer clientId;
	private ClientTO clientTO;
	
	private String username;
	private String passwordResetAlgorithm;
	private String passwordComplexity;
	
	//private String appVersion;
	
	private int id;
	private int loginUserId;
	private String userName;
	private String password;
	private String masterPassword;
	private String locationIds;
	private String resourceIds;
	private String startDate;
	private String expireDate;
	private char locked;
	
	private char suspend;
	
	private String clinetTimeZone;
	private String call_center_logic;
	
	@JsonIgnore
	private String privalageDetails;
	
	public ClientTO getClientTO()
	{
		return clientTO;
	}

	public void setClientTO(ClientTO clientTO)
	{
		this.clientTO = clientTO;
	}

	public Integer getClientId()
	{
		return clientId;
	}

	public void setClientId(Integer clientId)
	{
		this.clientId = clientId;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getClientCode()
	{
		return clientCode;
	}

	public void setClientCode(String clientCode)
	{
		this.clientCode = clientCode;
	}

	public String getAccessLevel()
	{
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel)
	{
		this.accessLevel = accessLevel;
	}

	public String getLocationAccess()
	{
		return locationAccess;
	}

	public void setLocationAccess(String locationAccess)
	{
		this.locationAccess = locationAccess;
	}

	public String getResourceAccess()
	{
		return resourceAccess;
	}

	public void setResourceAccess(String resourceAccess)
	{
		this.resourceAccess = resourceAccess;
	}

	public String getPasswordExpiryAlert()
	{
		return passwordExpiryAlert;
	}

	public void setPasswordExpiryAlert(String passwordExpiryAlert)
	{
		this.passwordExpiryAlert = passwordExpiryAlert;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getContactPhone()
	{
		return contactPhone;
	}

	public void setContactPhone(String contactPhone)
	{
		this.contactPhone = contactPhone;
	}

	public String getContactEmail()
	{
		return contactEmail;
	}

	public void setContactEmail(String contactEmail)
	{
		this.contactEmail = contactEmail;
	}

	public String getLastLoginDateTime()
	{
		return lastLoginDateTime;
	}

	public void setLastLoginDateTime(String lastLoginDateTime)
	{
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public String getLastLoginIP()
	{
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP)
	{
		this.lastLoginIP = lastLoginIP;
	}

	public String getDisplaySLA()
	{
		return displaySLA;
	}

	public void setDisplaySLA(String displaySLA)
	{
		this.displaySLA = displaySLA;
	}

	public String getSlaText()
	{
		return slaText;
	}

	public void setSlaText(String slaText)
	{
		this.slaText = slaText;
	}

	public String getSlaSkip()
	{
		return slaSkip;
	}

	public void setSlaSkip(String slaSkip)
	{
		this.slaSkip = slaSkip;
	}

	public String getForwardURL()
	{
		return forwardURL;
	}

	public void setForwardURL(String forwardURL)
	{
		this.forwardURL = forwardURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordResetAlgorithm() {
		return passwordResetAlgorithm;
	}

	public void setPasswordResetAlgorithm(String passwordResetAlgorithm) {
		this.passwordResetAlgorithm = passwordResetAlgorithm;
	}

	public String getPasswordComplexity() {
		return passwordComplexity;
	}

	public void setPasswordComplexity(String passwordComplexity) {
		this.passwordComplexity = passwordComplexity;
	}

	public String getUserName() {
		if(getUsername()!=null && getUsername()!="") {
			return getUsername();
		}else{
			return userName;
		}
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMasterPassword() {
		return masterPassword;
	}

	public void setMasterPassword(String masterPassword) {
		this.masterPassword = masterPassword;
	}

	public String getLocationIds() {
		return locationIds;
	}

	public void setLocationIds(String locationIds) {
		this.locationIds = locationIds;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public char getLocked() {
		return locked;
	}

	public void setLocked(char locked) {
		this.locked = locked;
	}

	public int getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(int loginUserId) {
		this.loginUserId = loginUserId;
	}

	public char getSuspend() {
		return suspend;
	}

	public void setSuspend(char suspend) {
		this.suspend = suspend;
	}

	public String getClinetTimeZone() {
		return clinetTimeZone;
	}

	public void setClinetTimeZone(String clinetTimeZone) {
		this.clinetTimeZone = clinetTimeZone;
	}

	public String getPrivalageDetails() {
		return privalageDetails;
	}

	public void setPrivalageDetails(String privalageDetails) {
		this.privalageDetails = privalageDetails;
	}

	public String getCall_center_logic() {
		return call_center_logic;
	}

	public void setCall_center_logic(String call_center_logic) {
		this.call_center_logic = call_center_logic;
	}
}
