package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {

    private HashMap<Object, String> descriptors;
    private String latitude;
    private String longitude;

    public void setLatitude(String setLat) {
        latitude = setLat;
    }

    public void setLongitude(String setLong) {
        longitude = setLong;
    }

    public void addToDesc(Object theObj, String theStr) {
        descriptors.put(theObj.hashCode(), theStr);
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

    public String toString() {
        return "latitude: " + latitude + " longitude: " + longitude;
    }

}
