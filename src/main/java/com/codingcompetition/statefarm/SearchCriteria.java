package com.codingcompetition.statefarm;

public class SearchCriteria {
  private Category category;
  private String value;

	public SearchCriteria(Category cat, String value) {
	  this.category = cat;
	  this.value = value;
	}
	
	public Category getCategory() {
	  return this.category;
  }

  public String getValue() {
	  return this.value;
  }
}
