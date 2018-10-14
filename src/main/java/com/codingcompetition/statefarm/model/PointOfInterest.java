package com.codingcompetition.statefarm.model;

import java.util.Map;

public class PointOfInterest {

	private String lat;
	private String lon;
	private Map<Object, String> descs;

	/**
	 * Creates a new PointOfInterest object with the given latitude, longitude, and
	 * map of key-value sets.
	 * 
	 * @param lat   The latitude of the point.
	 * @param lon   The longitude of the point.
	 * @param descs A map of the tags of the point; each tag contains a key and a
	 *              value.
	 */
	public PointOfInterest(String lat, String lon, Map<Object, String> descs) {
		this.lat = lat;
		this.lon = lon;
		this.descs = descs;
	}

	/**
	 * Returns the key-value mappings of this point's tags.
	 * 
	 * @return The map of the tags' key-value pairs.
	 */
	public Map<Object, String> getDescriptors() {
		return descs;
	}

	/**
	 * Returns the latitude of the point.
	 * 
	 * @return The latitude of the point.
	 */
	public String getLatitude() {
		return lat;
	}

	/**
	 * Returns the longitude of the point.
	 * 
	 * @return The longitude of the point.
	 */
	public String getLongitude() {
		return lon;
	}
}
