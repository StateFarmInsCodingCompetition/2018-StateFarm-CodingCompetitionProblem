package com.codingcompetition.statefarm;

public enum Category {

    LEISURE, NAME, AMENITY, CUISINE, SHOP, WHEELCHAIR, HIGHWAY, PLACE, POPULATION, POWER, BUILDING, BEAUTY, NAMESTARTSWITH, NAMEENDSWITH;
	
	
	public static Category getCategory(String name) {
		for(Category c : Category.values()) {
			if(c.toString().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}
	
	
	
}
