package com.telappoint.adminresv.restws.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Balaji N
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class HomePageResponse extends BaseResponse {
	private DisplayNames displayNames;
	private ResvSysConfig resvSysConfig;
	private List<String> privilegeMapping;

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

	public List<String> getPrivilegeMapping() {
		return privilegeMapping;
	}

	public void setPrivilegeMapping(List<String> privilegeMapping) {
		this.privilegeMapping = privilegeMapping;
	}
}
