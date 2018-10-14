package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {

	private String _dataPath;
	private List<PointOfInterest> listPoints;
    public StreetMapDataInterpreter(String s) throws Exception {
    	String dataPath = "../../../../resources/" + s;
    	_dataPath = dataPath;
    	
    	PointOfInterestParser parser = new PointOfInterestParser();
    	listPoints = parser.parse(dataPath);
    }

    @Override
    public List<PointOfInterest> interpret() {
        return listPoints;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
    	if (criteria == null) {
    		return new ArrayList<PointOfInterest>();
    	}
    	List<PointOfInterest> filterList = listPoints.stream()
    			.filter(point -> {
    				Map<Object, String> description = point.getDescriptors();
    				if (!description.containsKey(criteria.getCategory())
    					|| !description.get(criteria.getCategory()).equals(criteria.getValue())) {
    					return false;
    				}
    				return true;
    			}).collect(Collectors.toList());
        return filterList;
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
    	if (prioritizedCriteria == null) {
    		return new ArrayList<PointOfInterest>();
    	}
    	List<PointOfInterest> filterList = listPoints.stream()
    			.filter(point -> {
    				Map<Object, String> description = point.getDescriptors();
   
    				int numCriteria = prioritizedCriteria.size();
    				
    				for (int i = 1; i <= numCriteria; i++) {
    					if (prioritizedCriteria.containsKey(i)) {
    						SearchCriteria criteria = prioritizedCriteria.get(i);
    						
    						if (!description.containsKey(criteria.getCategory())
		    					|| !description.get(criteria.getCategory()).equals(criteria.getValue())) {
		    					return false;
    						}
    					}
    				}
    				return true;
    			}).collect(Collectors.toList());
    	
        return filterList;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
    	if (criterias == null) {
    		return new ArrayList<PointOfInterest>();
    	}
    	List<PointOfInterest> filterList = listPoints.stream()
    			.filter(point -> {
    				Map<Object, String> description = point.getDescriptors();
    				for (SearchCriteria criteria : criterias) {
    					if (!description.containsKey(criteria.getCategory())
	    					|| !description.get(criteria.getCategory()).equals(criteria.getValue())) {
	    					return false;
	    				}
    				}
    				return true;
    			}).collect(Collectors.toList());
        return filterList;
    }
}
