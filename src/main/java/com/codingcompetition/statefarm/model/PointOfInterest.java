package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {
	
	private Map<Object, String> hashMap;
	private String latitude;
	private String longitude;
	
	public PointOfInterest() {}
	
	public PointOfInterest(String latitude, String longitude) {
		this(null, latitude, longitude);
	}
	
	public PointOfInterest(Map<Object, String> hashMap, String latitude, String longitude) {
		this.hashMap = hashMap;
		this.latitude = latitude;
		this.longitude = longitude;
	}

    public Map<Object,String> getDescriptors() {
    	return this.hashMap;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }
    
    @Override
    public boolean equals(Object object) {
    	if (this == object) {return true;}
    	
    	if (!(this instanceof PointOfInterest)) {
    		return false;
    	}
    	
    	PointOfInterest p = (PointOfInterest) object;
    	
    	return this.longitude.equals(p.getLongitude())
    			&& this.latitude.equals(p.getLatitude());
    }
    
    @Override
    public String toString() {
    	return this.latitude + " - " + this.longitude;
    }
}
