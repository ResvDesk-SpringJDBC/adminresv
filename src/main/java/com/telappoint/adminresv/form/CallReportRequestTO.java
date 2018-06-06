package com.telappoint.adminresv.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


/**
 * 
 * @author Murali
 * 
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class CallReportRequestTO {
	
	private String reportType;
	private String inBoundPeriodFrom;
	private String inBoundPeriodTo;
	private String inBoundCallerId;
	private String outBoundPeriodFrom;
	private String outBoundPeriodTo;
	private String outBoundCallerId;
	
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getInBoundPeriodFrom() {
		return inBoundPeriodFrom;
	}
	public void setInBoundPeriodFrom(String inBoundPeriodFrom) {
		this.inBoundPeriodFrom = inBoundPeriodFrom;
	}
	public String getInBoundPeriodTo() {
		return inBoundPeriodTo;
	}
	public void setInBoundPeriodTo(String inBoundPeriodTo) {
		this.inBoundPeriodTo = inBoundPeriodTo;
	}
	public String getOutBoundPeriodFrom() {
		return outBoundPeriodFrom;
	}
	public void setOutBoundPeriodFrom(String outBoundPeriodFrom) {
		this.outBoundPeriodFrom = outBoundPeriodFrom;
	}
	public String getOutBoundPeriodTo() {
		return outBoundPeriodTo;
	}
	public void setOutBoundPeriodTo(String outBoundPeriodTo) {
		this.outBoundPeriodTo = outBoundPeriodTo;
	}
	public String getInBoundCallerId() {
		return inBoundCallerId;
	}
	public void setInBoundCallerId(String inBoundCallerId) {
		this.inBoundCallerId = inBoundCallerId;
	}
	public String getOutBoundCallerId() {
		return outBoundCallerId;
	}
	public void setOutBoundCallerId(String outBoundCallerId) {
		this.outBoundCallerId = outBoundCallerId;
	}	


}
