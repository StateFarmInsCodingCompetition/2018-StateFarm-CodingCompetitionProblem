package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {

    private final String latitude;
    private final String longitude;
    private final Map<Object, String> descriptors = new HashMap<>();

    public PointOfInterest(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
