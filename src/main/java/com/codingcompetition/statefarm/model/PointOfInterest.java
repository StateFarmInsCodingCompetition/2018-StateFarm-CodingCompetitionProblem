package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {
	
	private Map<Object, String> desc;
	private String lat;
	private String lon;
	
	public PointOfInterest(String lat, String lon) {
		this.lat = lat;
		this.lon = lon;
		desc = new HashMap<Object, String>();
	}

	public void addDescriptor(Object k, String v ) {
		desc.put(k, v);
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
