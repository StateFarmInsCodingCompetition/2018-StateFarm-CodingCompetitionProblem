package com.codingcompetition.statefarm.model;

import java.util.Map;

public class PointOfInterest {

	Map<Object, String> descriptors;

	public PointOfInterest(final Map<Object, String> descriptors) {

		this.descriptors = descriptors;
	}

	public Map<Object, String> getDescriptors() {

		return descriptors;
	}

	public String getLatitude() {

		return descriptors.get("latitude");
	}

	public String getLongitude() {

		return descriptors.get("longitude");
	}
}
