package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {

	private String latitude;
	private String longtitude;

    public Map<Object,String> getDescriptors() {
    return new HashMap<>();
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
