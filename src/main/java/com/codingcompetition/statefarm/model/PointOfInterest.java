package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {
    private String latitude;
    private String longitude;
    private String id;
    private Map<Object, String> descriptors;

    public PointOfInterest() {
        this.latitude = "";
        this.longitude = "";
        this.id = null;
        this.descriptors = new HashMap<>();
    }

    public PointOfInterest(String latitude, String longitude, String id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.descriptors = null;
    }

    public void setDescriptors(Map<Object, String> descriptors) {
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
