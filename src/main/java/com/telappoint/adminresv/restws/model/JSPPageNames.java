package com.telappoint.adminresv.restws.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Murali
 * 
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class JSPPageNames {
	
	private String group_title;
	private String pages_title;
	private String jsp_pages;
	private String jsp_pages_description;
	
	public String getGroup_title() {
		return group_title;
	}
	public void setGroup_title(String group_title) {
		this.group_title = group_title;
	}
	public String getPages_title() {
		return pages_title;
	}
	public void setPages_title(String pages_title) {
		this.pages_title = pages_title;
	}
	public String getJsp_pages() {
		return jsp_pages;
	}
	public void setJsp_pages(String jsp_pages) {
		this.jsp_pages = jsp_pages;
	}
	public String getJsp_pages_description() {
		return jsp_pages_description;
	}
	public void setJsp_pages_description(String jsp_pages_description) {
		this.jsp_pages_description = jsp_pages_description;
	}	
}
