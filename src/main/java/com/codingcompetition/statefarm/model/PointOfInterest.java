package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Data class for storing points of interest with latitude, longitude,
 * and a set of descriptors with tag information.
 * 
 * @author Jerry Huang
 * @author Neil Thistlethwaite
 */
public class PointOfInterest {
    
    private final String latitude;
    private final String longitude;
    private Map<Object, String> descriptors;
    
    /**
     * Creates point of interest given latitude and longitude
     * 
     * @param latitude The latitude of the point of interest
     * @param longitude The longitude of the point of interest
     */
    public PointOfInterest(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.descriptors = new HashMap<>();
    }
    
    /**
     * Returns the descriptors of the point of interest
     * 
     * @return The point of interest descriptors
     */
    public Map<Object, String> getDescriptors() {
        return descriptors;
    }

    /**
     * Returns the latitude of the point of interest
     * 
     * @return The point of interest latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the point of interest
     * 
     * @return The point of interest longitude
     */
    public String getLongitude() {
        return longitude;
    }
    
    /**
     * Adds to the descriptor a key value pair
     * 
     * @param key The key being inserted with associated value
     * @value value The value being inserted with associated key
     */
    public void addDescriptor(Object key, String value) {
        descriptors.put(key, value);
    }
}
