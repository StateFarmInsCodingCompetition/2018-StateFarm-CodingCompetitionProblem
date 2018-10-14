package com.codingcompetition.statefarm.model;

import java.util.Map;

/**
 * Stores basic OpenStreetMap point of interest information
 * @author Jeremy Schonfeld and Robert Pooley
 */
public class PointOfInterest {
	
	/** The latitude of the POI */
	private String latitude;
	
	/** The longitude of the POI */
	private String longitude;
	
	/** A mapping of descriptors from their String keys to the String values */
	private Map<Object, String> descriptors;
	
	/**
	 * Creates a point of interest object from raw information
	 * @param lat The latitude of the POI
	 * @param lon The longitude of the POI
	 * @param desc The key/value pair descriptors
	 */
	public PointOfInterest(String lat, String lon, Map<Object, String> desc) {
		latitude = lat;
		longitude = lon;
		descriptors = desc;
	}

	/**
	 * Returns the descriptors of the object in key/value pairs
	 * @return The associated object's descriptors
	 */
    public Map<Object,String> getDescriptors() {
    	return descriptors;
    }

    /**
     * Returns the latitude of the POI
     * @return The associated object's latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the POI
     * @return The associated object's longitude
     */
    public String getLongitude() {
        return longitude;
    }
}
