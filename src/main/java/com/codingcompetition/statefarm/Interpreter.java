package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;

import java.util.List;
import java.util.Map;

/**
 *  An interpreter that interprets xml data as points of interest 
 *  
 */
public interface Interpreter {

	/**
	 *  Returns all points of interest
	 *  
	 *  @return A point of interest list
	 */
    public List<PointOfInterest> interpret();
    
    /**
	 *  Returns all points of interest adhering to a certain SearchCriteria
	 *  
	 *  @param criteria The SearchCriteria that we filter through
	 *  @return A point of interest list adhering to a certain SearchCriteria
	 */
    public List<PointOfInterest> interpret(SearchCriteria criteria);
    
    /**
	 *  Returns all points of interest adhering to a certain prioritizedCriteria
	 *  
	 *  @param prioritizedCriteria The prioritizedCriteria that we filter through
	 *  @return A point of interest list adhering to a certain prioritizedCriteria
	 */
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria);
    
    /**
	 *  Returns all points of interest that match at least one of the given criteria.
	 *  
	 *  @param criterias The criterias that we filter through
	 *  @return A point of interest list adhering to certain criterias
	 */
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias);
}
