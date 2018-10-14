package com.codingcompetition.statefarm;

/**
 * This enum provides three ways of matching attributes;
 * if passed in the constructor to a SearchCriteria, then 
 * that method will be used for comparing values.
 * 
 * @author State Farm
 */
public enum MatchingStrategy {
    EXACT_MATCH, STARTS_WITH, ENDS_WITH;
}
