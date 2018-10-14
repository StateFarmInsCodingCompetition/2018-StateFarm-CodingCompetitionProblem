package com.codingcompetition.statefarm.model;

import java.util.Map;

public class PointOfInterest {
	
	private String latitude, longitude;
	private Map<Object, String> descriptors;
	
	public PointOfInterest(String lat, String lon, Map<Object, String> desc) {
		latitude = lat;
		longitude = lon;
		descriptors = desc;
	}

    public Map<Object,String> getDescriptors() {
    	return descriptors;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
