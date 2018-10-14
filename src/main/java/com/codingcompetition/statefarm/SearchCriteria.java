package com.codingcompetition.statefarm;

import java.util.Map;

import com.codingcompetition.statefarm.model.PointOfInterest;

/**
 * Data class for storing search criteria with given category, value,
 * and, optionally, matching strategy. If provided, the given matching
 * strategy is used for comparing the value of a potential POI against
 * the SearchCriteria value.
 * 
 * @author Jerry Huang
 * @author Neil Thistlethwaite
 */
public class SearchCriteria {
    
    private final Category category;
    private final String value;
    private final MatchingStrategy matchingStrategy;
    
    /** 
     * Initializes SearchCriteria with a provided category and value.
     * 
     * @param cat The category whose value should match
     * @param value The value that should match to
     */
    public SearchCriteria(Category cat, String value) {
        this.category = cat;
        this.value = value;
        this.matchingStrategy = null;
    }
    
    /** 
     * Initializes SearchCriteria with a provided category, value, and
     * matching strategy.
     * 
     * @param cat The category whose value should match
     * @param value The value that should match to
     * @param matchStrat The matching strategy to use
     */
    public SearchCriteria(Category cat, String value, MatchingStrategy matchStrat) {
        this.category = cat;
        this.value = value;
        this.matchingStrategy = matchStrat;
    }
	
	/** 
     * Returns the category of this SearchCriteria
     * 
     * @return The category of this SearchCriteria
     */
	public Category getCategory() {
	    return category;
	}
	
	/** 
     * Return the value of this SearchCriteria
     * 
     * @return The value of this SearchCriteria
     */
	public String getValue() {
	    return value;
	}
	
	/** 
     * Checks whether a given PointOfInterest matches this SearchCriteria
     * 
     * @param poi The given PointOfInterest we are testing for a match
     * @return Whether the given PointOfInterest matches this SearchCriteria 
     */
	public boolean doesMatch(PointOfInterest poi) {
	    Map<Object, String> attrs = poi.getDescriptors();
	    if (category == Category.NAMESTARTSWITH) {
	        return attrs.containsKey("name") && attrs.get("name").startsWith(value);
	    } else if (category == Category.NAMEENDSWITH) {
	        return attrs.containsKey("name") && attrs.get("name").endsWith(value);
	    } else {
	        if (!attrs.containsKey(category.name().toLowerCase()))
	            return false;
	        
	        if (matchingStrategy == MatchingStrategy.STARTS_WITH) {
                return attrs.get(category.name().toLowerCase()).startsWith(value);
	        } else if (matchingStrategy == MatchingStrategy.ENDS_WITH) {
                return attrs.get(category.name().toLowerCase()).endsWith(value);
	        } else {
                return attrs.get(category.name().toLowerCase()).equals(value);
	        }
	    }
	}
}
