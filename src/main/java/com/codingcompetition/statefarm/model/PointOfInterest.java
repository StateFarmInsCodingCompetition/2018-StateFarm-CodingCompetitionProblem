package com.codingcompetition.statefarm.model;

import java.util.Map;

public class PointOfInterest {

    final String lat = "lat";
    final String lon = "lon";
    private Map<Object, String> descriptors;

    public PointOfInterest(Map<Object, String> descriptors) {
        this.descriptors = descriptors;
    }

    public Map<Object,String> getDescriptors() {
        return descriptors;
    }

    public String getLatitude() {
        return descriptors.get(lat);
    }

    public String getLongitude() { return descriptors.get(lon); }
}
