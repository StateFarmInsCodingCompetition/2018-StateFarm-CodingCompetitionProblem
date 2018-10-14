package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public String getId() {
        return this.id;
    }

    public String getValueFromTag(String tag) {
        return this.descriptors.get(tag);
    }

}
