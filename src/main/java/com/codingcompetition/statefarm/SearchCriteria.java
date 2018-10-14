package com.codingcompetition.statefarm;

public class SearchCriteria {
  public static final String STARTS_WITH_SUFFIX = "STARTSWITH";
  public static final String ENDS_WITH_SUFFIX = "ENDSWITH";

  private String category;
  private String value;
  private MatchingStrategy matchingStrategy;

  public SearchCriteria(Category category, String value) {
    String categoryStr = category.name();

    if (categoryStr.endsWith(STARTS_WITH_SUFFIX)) {
      String actualCategoryStr = categoryStr.substring(0, categoryStr.length() - STARTS_WITH_SUFFIX.length());
      this.category = actualCategoryStr.toLowerCase();
      this.matchingStrategy = MatchingStrategy.STARTS_WITH;
    } else if (categoryStr.endsWith(ENDS_WITH_SUFFIX)) {
      String actualCategoryStr = categoryStr.substring(0, categoryStr.length() - ENDS_WITH_SUFFIX.length());
      this.category = actualCategoryStr.toLowerCase();
      this.matchingStrategy = MatchingStrategy.ENDS_WITH;
    } else {
      this.category = category.name().toLowerCase();
      this.matchingStrategy = MatchingStrategy.EXACT_MATCH;
    }
    this.value = value;
  }

  public String getCategory() {
    return this.category;
  }

  public String getValue() {
    return this.value;
  }

  public MatchingStrategy getMatchingStrategy() {
    return this.matchingStrategy;
  }
}
