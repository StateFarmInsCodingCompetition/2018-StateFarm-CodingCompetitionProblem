package com.codingcompetition.statefarm;

import java.util.Arrays;

public enum Category {
  LEISURE, NAME, AMENITY, CUISINE, SHOP, WHEELCHAIR, HIGHWAY, PLACE, POPULATION, POWER, BUILDING, BEAUTY, NAMESTARTSWITH, NAMEENDSWITH;

  /**
   * Returns true if the given string represents a valid category.
   *
   * @param categoryStr String that may represent a Category
   * @return true if given string represents valid category, false otherwise
   */
  public static boolean isValidCategory(String categoryStr) {
    return Arrays.stream(Category.values()).anyMatch((category) -> category.name().equalsIgnoreCase(categoryStr));
  }
}
