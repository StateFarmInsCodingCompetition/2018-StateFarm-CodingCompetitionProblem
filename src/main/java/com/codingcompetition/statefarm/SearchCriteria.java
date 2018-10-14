package com.codingcompetition.statefarm;

import java.util.Map;

import com.codingcompetition.statefarm.model.PointOfInterest;

/**
 * A class representing a search criteria based on a category and a value
 * @author Jeremy Schonfeld and Robert Pooley
 *
 */
public class SearchCriteria {
	
	/** The category of this search criteria */
	private Category category;
	
	/** The value to match for this search criteria */
	private String value;

	/**
	 * Create a search criteria given a category and a value
	 * @param cat The category for this search criteria
	 * @param value The value for this search criteria
	 */
	public SearchCriteria(Category cat, String value) {
		this.category = cat;
		this.value = value;
	}
	
	/**
	 * @return The category of this search criteria
	 */
	public Category getCategory() {
		return category;
	}
	
	/**
	 * @return The value of this search criteria
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Check if the given point of interest matches this search criteria
	 * @param point The point of interest to check
	 * @return True if the point of interest matches the search criteria, false otherwise
	 */
	public boolean matches(PointOfInterest point) {
		Map<Object, String> descriptors = point.getDescriptors();
		String name = descriptors.get("name");
		switch (category) {
		case NAMESTARTSWITH:
			return name != null && name.startsWith(value);
		case NAMEENDSWITH:
			return name != null && name.endsWith(value);
		default:
			String key = category.name().toLowerCase();
			if (!descriptors.containsKey(key)) {
				return false;
			} else {
				return descriptors.get(key).equals(value);
			}
		}
	}
}
