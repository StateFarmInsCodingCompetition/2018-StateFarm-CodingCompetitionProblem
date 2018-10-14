package com.codingcompetition.statefarm.model;

import com.codingcompetition.statefarm.Category;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {

    private HashMap<Category, String> descriptors;
    private String latitude;
    private String longitude;

    public Map<Category, String> getDescriptors() {
        return descriptors;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
