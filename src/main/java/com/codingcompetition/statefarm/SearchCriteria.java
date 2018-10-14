package com.codingcompetition.statefarm;

public class SearchCriteria {

	private Category _cat;
	private String _value;
	
	public SearchCriteria(Category cat, String value) {
		_cat = cat;
		_value = value;
	}
	
	public Category getCategory() {
		return _cat;
	}
	
	public String getValue() {
		return _value;
	}
}
