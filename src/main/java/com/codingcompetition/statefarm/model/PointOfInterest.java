package com.codingcompetition.statefarm.model;

import com.codingcompetition.statefarm.Category;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {

    private HashMap<Object, String> descriptors;
    private String latitude;
    private String longitude;

    public PointOfInterest(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Map<Object, String> getDescriptors() {
        return descriptors;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
