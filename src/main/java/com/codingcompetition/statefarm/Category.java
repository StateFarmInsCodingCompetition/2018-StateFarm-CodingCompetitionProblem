package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;

import java.util.function.BiFunction;

import static com.codingcompetition.statefarm.MatchingStrategy.ENDS_WITH;
import static com.codingcompetition.statefarm.MatchingStrategy.EXACT_MATCH;
import static com.codingcompetition.statefarm.MatchingStrategy.STARTS_WITH;

public enum Category implements BiFunction<PointOfInterest, String, Boolean> {

    LEISURE("leisure"),
    NAME("name"),
    AMENITY("amenity"),
    CUISINE("cuisine"),
    SHOP("shop"),
    WHEELCHAIR("wheelchair"),
    HIGHWAY("highway"),
    PLACE("place"),
    POPULATION("population"),
    POWER("power"),
    BUILDING("building"),
    BEAUTY("beauty"),
    NAMESTARTSWITH("name"),
    NAMEENDSWITH("name");

    private final String key;
    Category(String key) {
        this.key = key;
    }

    /**
     * Check if a point of interest and target value match this category.
     * @param pointOfInterest the point of interest to apply the filter on
     * @param value the value of the filter target
     * @return a boolean which is true if the filter matches, and false otherwise
     */
    @Override
    public Boolean apply(PointOfInterest pointOfInterest, String value) {
        MatchingStrategy matchingStrategy = EXACT_MATCH;

        if (this == NAMESTARTSWITH) {
            matchingStrategy = STARTS_WITH;
        } else if (this == NAMEENDSWITH) {
            matchingStrategy = ENDS_WITH;
        }

        String descriptor = pointOfInterest.getDescriptors().get(this.key);

        if (descriptor == null) {
            return false;
        }

        switch (matchingStrategy) {
            case EXACT_MATCH:
                return descriptor.equalsIgnoreCase(value);
            case STARTS_WITH:
                return descriptor.toLowerCase().startsWith(value.toLowerCase());
            case ENDS_WITH:
                return descriptor.toLowerCase().endsWith(value.toLowerCase());
            default:
                return false;  // an invalid matching strategy
        }
    }
}
