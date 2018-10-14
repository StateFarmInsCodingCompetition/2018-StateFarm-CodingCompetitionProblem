package com.codingcompetition.statefarm;

import java.util.Map;

import com.codingcompetition.statefarm.model.PointOfInterest;

public class SearchCriteria {
	
	private Category category;
	
	private String value;

	public SearchCriteria(Category cat, String value) {
		this.category = cat;
		this.value = value;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public String getValue() {
		return value;
	}
	
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
