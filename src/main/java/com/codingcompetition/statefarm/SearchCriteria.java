package com.codingcompetition.statefarm;

public class SearchCriteria {
  private String category;
  private String value;

  public SearchCriteria(Category category, String value) {
    this.category = category.name().toLowerCase();
    this.value = value;
  }

  public String getCategory() {
    return this.category;
  }

  public String getValue() {
    return this.value;
  }
}
