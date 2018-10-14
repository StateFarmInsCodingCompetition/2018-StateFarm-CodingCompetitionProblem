package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Intrepreter for Street Map Data in XML format. Has the ability to
 * interpret based on specific search criteria, as well as using combinations
 * of criteria for more advanced search functionality.
 * 
 * @author Neil Thistlethwaite
 * @author Jerry Huang
 */

public class StreetMapDataInterpreter implements Interpreter {

    final List<PointOfInterest> poiList;
    
    /**
     * Constructs a StreetMapDataInterpreter using the given XML file path. Loads the entire
     * file when instantiated, so interpret requests are relatively fast. 
     * 
     * @param s Path to the XML file to interpret
     * @throws IOException If the file does not exist or this program does not have access.
     * @throws ParserConfigurationException If the XML parser is incorrectly configured.
     * @throws SAXException If the XML file is not valid.
     */
    public StreetMapDataInterpreter(String s) throws IOException, ParserConfigurationException, SAXException {
        poiList = new PointOfInterestParser().parse(s);
    }

    /**
     * Interprets the given street data XML file with no search criteria, returning all found
     * points of interest.
     * 
     * @return The list of all points of interest
     */
    @Override
    public List<PointOfInterest> interpret() {
        return poiList;
    }

    /**
     * Interprets the given street data XML file with a single search criteria, returning all
     * points of interest that match it.
     * 
     * @param criteria The criteria to check all points of interest against
     * @return All matching points of interest
     */
    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        
        List<PointOfInterest> filtered = new ArrayList<>();
        
        for (PointOfInterest poi : poiList) {
            if (criteria != null && criteria.doesMatch(poi)) {
                filtered.add(poi);
            }
        }
        return filtered;
    }
    
    /**
     * Interprets the given street data XML file with a list of prioritized criteria, returning
     * all points of interest that match ALL given criteria.
     * 
     * @param prioritizedCriteria A map mapping priorities (integer values) to search criteria
     * @returns All points of interest that match ALL given criteria
     */
    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        
        List<PointOfInterest> filtered = new ArrayList<>();
        
        for (PointOfInterest poi : poiList) {
            boolean doesMatch = true;
            for (SearchCriteria criteria : prioritizedCriteria.values()) {
                if (criteria == null || !criteria.doesMatch(poi)) {
                    doesMatch = false;
                    break;
                }
            }
            if (doesMatch) {
                filtered.add(poi);
            }
        }
        return filtered;
    }

    
    /**
     * Interprets the given street data XML file with a list of criteria, returning all points
     * of interest that match at least one of the given criteria.
     * 
     * @param criterias A list of search criteria
     * @returns All points of interest that match any of the given criteria
     */
    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        
        List<PointOfInterest> filtered = new ArrayList<>();
        
        for (PointOfInterest poi : poiList) {
            for (SearchCriteria criteria : criterias) {
                if (criteria != null && criteria.doesMatch(poi)) {
                    filtered.add(poi);
                    break;
                }
            }
        }
        return filtered;
    }
}
