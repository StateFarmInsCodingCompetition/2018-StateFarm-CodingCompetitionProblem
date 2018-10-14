package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;

import java.util.function.BiFunction;

import static com.codingcompetition.statefarm.MatchingStrategy.ENDS_WITH;
import static com.codingcompetition.statefarm.MatchingStrategy.EXACT_MATCH;
import static com.codingcompetition.statefarm.MatchingStrategy.STARTS_WITH;

/**
 *  Categories for each tag
 *  
 *  @author State Farm
 */
public enum Category {
	
	/**
	 * Denotes a spot of leisure
	 */
    LEISURE, 
    
    /**
	 * Denotes a name
	 */
    NAME, 
    
    /**
	 * Denotes a spot of amenity
	 */
    AMENITY, 
    
    /**
	 * Denotes a spot of cuisine
	 */
    CUISINE, 
    
    /**
	 * Denotes a shop
	 */
    SHOP, 
    
    /**
	 * Denotes a wheelchair spot
	 */
    WHEELCHAIR, 
    
    /**
	 * Denotes a highway
	 */
    HIGHWAY, 
    
    /**
	 * Denotes a place
	 */
    PLACE, 
    
    /**
	 * Denotes the population
	 */
    POPULATION, 
    
    /**
	 * Denotes the power
	 */
    POWER, 
    
    /**
	 * Denotes a building
	 */
    BUILDING, 
    
    /**
	 * Denotes a beauty
	 */
    BEAUTY, 
    
    /**
	 * Denotes names starts with
	 */
    NAMESTARTSWITH, 
    
    /**
	 * Denotes names ends with
	 */
    NAMEENDSWITH;
}
