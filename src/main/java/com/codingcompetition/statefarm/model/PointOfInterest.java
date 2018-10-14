package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

import com.codingcompetition.statefarm.Category;

public class PointOfInterest {
	
	private Map<Object, String> desc;
	private String lat;
	private String lon;
	
	public PointOfInterest(String lat, String lon) {
		this.lat = lat;
		this.lon = lon;
		desc = new HashMap<Object, String>();
	}

	public void addDescriptor(String k, String v) {
		try {
			Category.valueOf(k.toUpperCase());
			desc.put(k, v);
		} catch (IllegalArgumentException e) {}
	}

    public Map<Object,String> getDescriptors() {
    	return desc;
    }

    public String getLatitude() {
        return lat;
    }

    public String getLongitude() {
        return lon;
    }
}
