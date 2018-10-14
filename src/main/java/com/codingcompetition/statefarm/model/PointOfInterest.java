package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.Map;

import com.codingcompetition.statefarm.Category;

public class PointOfInterest {
    private String latitude;
    private String longitude;
    private HashMap<Object, String> descriptors;

    public PointOfInterest(String lat, String lon) {
        latitude = lat;
        longitude = lon;
        descriptors = new HashMap<>();
    }

    public void addDescriptor(String k, String v) {
        descriptors.put(k, v);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descriptors == null) ? 0 : descriptors.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointOfInterest other = (PointOfInterest) obj;
		if (descriptors == null) {
			if (other.descriptors != null)
				return false;
		} else if (!descriptors.equals(other.descriptors))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		return true;
	}

}
