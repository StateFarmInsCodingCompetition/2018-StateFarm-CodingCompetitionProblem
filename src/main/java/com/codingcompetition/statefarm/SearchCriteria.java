package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;

/**
 * The SearchCriteria class can be used to perform search and
 * filter operations on points of interest.
 */
public class SearchCriteria {

    private final Category cat;
    private final String value;

    /**
     * Construct a search criteria that can be used on points of interest.
     * @param cat the category to match using
     * @param value the target value of the category filter
     */
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

    /**
     * Test if a point of interest matches this search criteria.
     * @param point the point of interest to check
     * @return true if the point of interest matches, false otherwise
     */
    public boolean test(PointOfInterest point) {
        return this.cat.apply(point, this.value);
    }
}
