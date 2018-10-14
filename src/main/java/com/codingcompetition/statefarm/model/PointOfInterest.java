package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {
	
	private String latitude, longitude;
	
	public PointOfInterest(final String latitude, final String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

    public Map<Object,String> getDescriptors() {
    	Map<Object, String> map = new HashMap<>();
    	map.put("latitude", getLatitude());
    	map.put("longitude", getLongitude());
    	return map;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
