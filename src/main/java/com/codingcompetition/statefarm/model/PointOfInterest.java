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

    /**
     * constructor for PointOfInterest
     * @param latitude String representation of latitude
     * @param longitude String representation of longitude
     * @param id String representation of id
     */
    public PointOfInterest(String latitude, String longitude, String id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.descriptors = null;
    }

    /**
     * setter method for descriptors
     * @param descriptors   map of descriptors
     */
    public void setDescriptors(Map<Object, String> descriptors) {
        this.descriptors = descriptors;
    }

    /**
     * getter method for descriptors
     * @return descriptors
     */
    public Map<Object,String> getDescriptors() {
        return this.descriptors;
    }

    /**
     * getter method for latitude
     * @return latitude
     */
    public String getLatitude() {
        return this.latitude;
    }

    /**
     * getter method for longitude
     * @return longitude
     */
    public String getLongitude() {
        return this.longitude;
    }

    /**
     * getter method for the id
     * @return id
     */
    public String getId() {
        return this.id;
    }

    /**
     * get the value of a tag if exist in the descriptor
     * @param tag   String representation of the tag key
     * @return value of the tag if exist, null otherwise
     */
    public String getValueFromTag(String tag) {
        return this.descriptors.get(tag);
    }

}
