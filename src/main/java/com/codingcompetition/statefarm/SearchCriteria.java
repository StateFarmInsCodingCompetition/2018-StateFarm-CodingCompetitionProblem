package com.codingcompetition.statefarm;

public class SearchCriteria implements SearchPredicate{	
	
	private Category category;
	private String value;
	
	public SearchCriteria(Category category, String value) {
		this.category = category;
		this.value = value;
	}
	
	@Override
	public boolean fits(String key, String val) {
		switch(category) {
			case NAMESTARTSWITH: return key.equalsIgnoreCase("name") && val.toLowerCase().startsWith(value.toLowerCase());
			case NAMEENDSWITH: return key.equalsIgnoreCase("name") && val.toLowerCase().endsWith(value.toLowerCase());
			default: return key.equalsIgnoreCase(category.toString()) && val.equalsIgnoreCase(value);
		}
	}
	
}
