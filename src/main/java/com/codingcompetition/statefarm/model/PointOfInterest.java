package com.codingcompetition.statefarm.model;

import com.codingcompetition.statefarm.SearchCriteria;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {
  private String latitude;
  private String longitude;
  private HashMap<Object, String> descriptors;

  public PointOfInterest(String latitude, String longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.descriptors = new HashMap<>();
  }

  public Map<Object, String> getDescriptors() {
    return descriptors;
  }

  public String getLatitude() {
    return latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void addDescriptor(String key, String value) {
    this.descriptors.put(key, value);
  }

  public boolean satisfiesCriteria(SearchCriteria criteria) {
    if (!descriptors.containsKey(criteria.getCategory())) {
      return false;
    }

    String expectedValue = criteria.getValue();
    String actualValue = descriptors.get(criteria.getCategory());
    switch (criteria.getMatchingStrategy()) {
      case EXACT_MATCH:
        return actualValue.equals(expectedValue);
      case STARTS_WITH:
        return actualValue.startsWith(expectedValue);
      case ENDS_WITH:
        return actualValue.endsWith(expectedValue);
    }
    return false;
  }
}
