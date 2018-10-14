package com.codingcompetition.statefarm;

public class SearchCriteria {
	private Category category;
	private String val;

	public SearchCriteria(Category cat, String value) {
		category = cat;
		val = value;
	}

	public Category getCategory() {
		return category;
	}

	public String getLowerCategory() {return category.name().toLowerCase();}

	public String getVal() {
		return val;
	}
}
