package com.codingcompetition.statefarm;

public class SearchCriteria {
	
	private Category category;
	private String value;

	public SearchCriteria(Category cat, String value) {
		this.setCategory(cat);
		this.setValue(value);
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
