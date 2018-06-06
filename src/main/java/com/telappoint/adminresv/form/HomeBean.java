package com.telappoint.adminresv.form;

import java.util.List;

import com.telappoint.adminresv.restws.model.DisplayNames;
import com.telappoint.adminresv.restws.model.ResvSysConfig;

/**
 * @author Murali G
 *
 */
public class HomeBean {
		
	private BaseRequest baseReq;
	
	private AdminInfoTO adminInfoTO; //TODO: This class we have to revisit ie, unnecessary fields are there
	private String appVersion;	
	private DisplayNames displayNames;
	private ResvSysConfig resvSysConfig;
	private List<String> privilegePageMapping;
	
	public DisplayNames getDisplayNames() {
		return displayNames;
	}
	public void setDisplayNames(DisplayNames displayNames) {
		this.displayNames = displayNames;
	}
	public ResvSysConfig getResvSysConfig() {
		return resvSysConfig;
	}
	public void setResvSysConfig(ResvSysConfig resvSysConfig) {
		this.resvSysConfig = resvSysConfig;
	}
	public List<String> getPrivilegePageMapping() {
		return privilegePageMapping;
	}
	public void setPrivilegePageMapping(List<String> privilegePageMapping) {
		this.privilegePageMapping = privilegePageMapping;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public AdminInfoTO getAdminInfoTO() {
		return adminInfoTO;
	}

	public void setAdminInfoTO(AdminInfoTO adminInfoTO) {
		this.adminInfoTO = adminInfoTO;
	}

	public BaseRequest getBaseReq() {
		return baseReq;
	}

	public void setBaseReq(BaseRequest baseReq) {
		this.baseReq = baseReq;
	}
}
