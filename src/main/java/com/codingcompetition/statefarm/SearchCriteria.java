package com.codingcompetition.statefarm;

public class SearchCriteria {

	Category cat;
	String value;

	public SearchCriteria(Category cat, String value) {
		this.cat = cat;
		this.value = value;
	}

	public Category getCat() {
		return cat;
	}

	public String getValue() {
		return value;
	}
}
