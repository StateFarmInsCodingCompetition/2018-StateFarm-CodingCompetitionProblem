package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {

    private Map<Object, String> descriptors = new HashMap<>();
    private String latitude;
    private String longitude;
    private boolean added;  // tracks if this poi has been added to a filtered list

    public PointOfInterest(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.added = false;
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

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
}
