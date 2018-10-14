package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;

import java.util.function.BiFunction;

import static com.codingcompetition.statefarm.MatchingStrategy.ENDS_WITH;
import static com.codingcompetition.statefarm.MatchingStrategy.EXACT_MATCH;
import static com.codingcompetition.statefarm.MatchingStrategy.STARTS_WITH;

public enum Category {

    LEISURE, NAME, AMENITY, CUISINE, SHOP, WHEELCHAIR, HIGHWAY, PLACE, POPULATION, POWER, BUILDING, BEAUTY, NAMESTARTSWITH, NAMEENDSWITH;
}
