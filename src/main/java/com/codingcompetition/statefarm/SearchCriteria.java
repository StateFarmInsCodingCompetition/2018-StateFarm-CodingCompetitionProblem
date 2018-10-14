package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;

public class SearchCriteria {

    private final Category cat;
    private final String value;

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

    public boolean test(PointOfInterest point) {
        return this.cat.apply(point, this.value);
    }
}
