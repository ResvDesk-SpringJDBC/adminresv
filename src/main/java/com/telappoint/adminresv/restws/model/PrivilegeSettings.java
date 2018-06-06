package com.telappoint.adminresv.restws.model;

import java.util.LinkedHashMap;
import java.util.List;

import com.telappoint.adminresv.form.BaseRequest;

/**
 * 
 * @author Murali
 * 
 */

public class PrivilegeSettings extends BaseRequest{
	private long selectedAccessPrivilegeId;
	private String selectedAccessPrivilege = "";
	private List<AccessPrivilege> accessPrivilegeTOList;	
	private List<String> privilegedPageNames;	
	private LinkedHashMap<String,List<JSPPageNames>> jspPagesPrivilegesMap;
	
	public List<AccessPrivilege> getAccessPrivilegeTOList() {
		return accessPrivilegeTOList;
	}
	public void setAccessPrivilegeTOList(
			List<AccessPrivilege> accessPrivilegeTOList) {
		this.accessPrivilegeTOList = accessPrivilegeTOList;
	}
	public long getSelectedAccessPrivilegeId() {
		return selectedAccessPrivilegeId;
	}
	public void setSelectedAccessPrivilegeId(long selectedAccessPrivilegeId) {
		this.selectedAccessPrivilegeId = selectedAccessPrivilegeId;
	}
	public List<String> getPrivilegedPageNames() {
		return privilegedPageNames;
	}
	public void setPrivilegedPageNames(List<String> privilegedPageNames) {
		this.privilegedPageNames = privilegedPageNames;
	}
	public String getSelectedAccessPrivilege() {
		return selectedAccessPrivilege;
	}
	public void setSelectedAccessPrivilege(String selectedAccessPrivilege) {
		this.selectedAccessPrivilege = selectedAccessPrivilege;
	}
	public LinkedHashMap<String, List<JSPPageNames>> getJspPagesPrivilegesMap() {
		return jspPagesPrivilegesMap;
	}
	public void setJspPagesPrivilegesMap(
			LinkedHashMap<String, List<JSPPageNames>> jspPagesPrivilegesMap) {
		this.jspPagesPrivilegesMap = jspPagesPrivilegesMap;
	}	
}
