package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {
    private String latitude;
    private String longitude;
    private Map<Object, String> descriptors;

    public PointOfInterest() {
        this.latitude = "";
        this.longitude = "";
        this.descriptors = new HashMap<>();
    }

    public PointOfInterest(String latitude, String longtitude, Map<Object, String> descriptors) {
        this.latitude = latitude;
        this.longitude = longtitude;
        this.descriptors = descriptors;

    }

    public Map<Object,String> getDescriptors() {
        return this.descriptors;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }
}
