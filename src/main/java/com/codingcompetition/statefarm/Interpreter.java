package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;

import java.util.List;
import java.util.Map;

/**
 * Interprets and searches a list of points of interests.
 */
public interface Interpreter {

    /**
     * Fetch all points of interest.
     * @return a list containing all points of interest
     */
    public List<PointOfInterest> interpret();

    /**
     * Finds the points of interest matching the given criteria.
     * @param criteria the criteria to search for
     * @return a list containing the points which match the criteria
     */
    public List<PointOfInterest> interpret(SearchCriteria criteria);

    /**
     * Performs a prioritized search operation which assigns scores to each
     * point of interest based on the prioritized criteria which they match,
     * and returns the points with the highest scores.
     * @param prioritizedCriteria a map of criteria, keyed by a numeric priority
     * @return a list containing the top, matching results
     */
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria);

    /**
     * Find points such that at least one criteria matches.
     * @param criterias a list of SearchCriteria
     * @return a list of points of interest where each matches at least one criteria
     */
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias);
}
