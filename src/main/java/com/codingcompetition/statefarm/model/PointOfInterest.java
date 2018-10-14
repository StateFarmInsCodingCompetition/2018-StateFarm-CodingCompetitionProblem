package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

import com.codingcompetition.statefarm.Category;

public class PointOfInterest {

	private String latitude;
	private String longtitude;
	private HashMap<String, String> descriptors;

	public PointOfInterest() {
		descriptors = new HashMap<>();
	}
    public Map<String,String> getDescriptors() {
    	return this.descriptors;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public String getLongitude() {
        return this.longtitude;
    }

    public void setLatitude(String latitude) {
    	this.latitude = latitude;
    }
    public void setLongtitude(String longtitude) {
    	this.longtitude = longtitude;
    }
}
