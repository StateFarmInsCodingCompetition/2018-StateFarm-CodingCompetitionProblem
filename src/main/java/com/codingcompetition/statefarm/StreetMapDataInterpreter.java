package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.xml.sax.SAXException;

/**
 * Provides functionality for parsing an XML file containing
 * points of interest and filtering based on search criteria
 * @author Jeremy Schonfeld and Robert Pooley
 *
 */
public class StreetMapDataInterpreter implements Interpreter {

	/** A list of all points of interest found in the provided file */
	private List<PointOfInterest> interestList;
	
	/** The parser instance */
	private PointOfInterestParser parser;

	/**
	 * @param fileName The file to parse
	 */
    public StreetMapDataInterpreter(String fileName) {
    	this.parser = new PointOfInterestParser();
    	
    	try {
			interestList = this.parser.parse(fileName);
		} catch (IOException | SAXException e) {
			e.printStackTrace();
		}
    }

    /**
     * @return A list of all points of interest found
     */
    @Override
    public List<PointOfInterest> interpret() {
        return new ArrayList<>(interestList);
    }

    /**
     * Interpret points of interest based on a search criteria
     * @param criteria A single search criteria to filter points of interest by
     * @return A list of all points of interest matching the provided criteria
     */
    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
    	List<SearchCriteria> list = new ArrayList<>();
    	list.add(criteria);
        return findByCriterias(list);
    }

    /**
     * Interpret points of interest based on prioritized search criteria
     * @param prioritizedCriteria A mapping of priority to search criteria
     * @return A list of the points of interest that match all of the provided search criteria
     */
    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        return interestList.stream()
        		.filter(poi -> {
        			for (SearchCriteria criteria : prioritizedCriteria.values()) {
        				if (!criteria.matches(poi)) {
        					return false;
        				}
        			}
        			return true;
        		})
        		.collect(Collectors.toList());
    }

    /**
     * Interpret points of interest based on multiple search criteria
     * @param criterias A list of search criteria
     * @return A list of points of interest matching at least one of the provided search criteria
     */
    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        return interestList.stream()
        		.filter(poi -> {
        			for (SearchCriteria criteria : criterias) {
        				if (criteria.matches(poi)) {
        					return true;
        				}
        			}
        			return false;
        		})
        		.collect(Collectors.toList());
    }
    
    /**
     * @return The parser
     */
    public PointOfInterestParser getParser() {
    	return parser;
    }
}
