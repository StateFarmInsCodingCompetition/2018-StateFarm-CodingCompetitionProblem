package com.codingcompetition.statefarm;

public class SearchCriteria {
	private Category category;
	private String value;


	/**
	 * constructor of SearchCriteria
	 * @param cat	Category
	 * @param value	String representation of the value
	 */
	public SearchCriteria(Category cat, String value) {
		this.category = cat;
		this.value = value;
	}

	/**
	 * getter method for category
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * getter method for value
	 */
	public String getValue() {
		return value;
	}
}
