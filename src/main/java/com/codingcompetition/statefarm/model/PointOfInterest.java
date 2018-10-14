package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A point of interest represents a physical location with a latitude,
 * longitude, and
 */
public class PointOfInterest {

    /**
     * The latitute of this point of interest.
     */
    private final String latitude;

    /**
     * The longitute of this point of interest.
     */
    private final String longitude;

    /**
     * A map containing a list of descriptors corresponding
     * to this point of interest.
     */
    private final Map<Object, String> descriptors = new HashMap<>();

    /**
     * Construct a point of interest.
     * @param latitude the latitude of this point of interest
     * @param longitude the longituge of this point of interest
     */
    public PointOfInterest(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Fetches the current list of descriptors for this point of interest.
     * @return the list of descriptors
     */
    public Map<Object,String> getDescriptors() {
        return this.descriptors;
    }

    /**
     * Get the latitude.
     * @return the latitude
     */
    public String getLatitude() {
        return this.latitude;
    }

    /**
     * Get the longitude.
     * @return the longitude
     */
    public String getLongitude() {
        return this.longitude;
    }
}
