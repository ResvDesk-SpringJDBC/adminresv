package com.telappoint.adminresv.restws.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Murali G
 *
 */
public class LocationListResponse extends BaseResponse {
	private List<Location> locationList = new ArrayList<Location>();

	public List<Location> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
}
