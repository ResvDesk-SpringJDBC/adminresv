package com.telappoint.adminresv.restws.model;

import java.util.List;

/**
 * 
 * @author Murali G
 *
 */
public class TransStateResponse extends BaseResponse {
	private List<TransState> transStateList;

	public List<TransState> getTransStateList() {
		return transStateList;
	}

	public void setTransStateList(List<TransState> transStateList) {
		this.transStateList = transStateList;
	}	
}
