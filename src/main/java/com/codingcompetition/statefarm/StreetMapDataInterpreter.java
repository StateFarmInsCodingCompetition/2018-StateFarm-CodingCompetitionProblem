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
import java.util.Set;
import java.util.TreeSet;

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
    				Map<String, String> description = point.getDescriptors();
    				if (criteria.getCategory().name().toLowerCase().equals("nameendswith")
    					&& description.containsKey("name")) {
    					
    				}
    				if (!description.containsKey(criteria.getCategory().name().toLowerCase())
    					|| !description.get(criteria.getCategory().name().toLowerCase()).equals(criteria.getValue())) {
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

    	Set<PointOfInterest> filterSet = new TreeSet<>(listPoints.stream()
    			.filter(point -> {
    				Map<String, String> description = point.getDescriptors();
   
    				int numCriteria = prioritizedCriteria.size();
    				
    				for (int i = 1; i <= numCriteria; i++) {
    					if (prioritizedCriteria.containsKey(i)) {
    						SearchCriteria criteria = prioritizedCriteria.get(i);
    						
    						if (description.containsKey(criteria.getCategory().name().toLowerCase())
		    					&& description.get(criteria.getCategory().name().toLowerCase()).equals(criteria.getValue())) {
		    					return true;
    						}
    					}
    				}
    				return false;
    			}).sorted(new java.util.Comparator<PointOfInterest>() {
    	    		@Override
    	    		public int compare(PointOfInterest a, PointOfInterest b) {
    	    			Map<String, String> descriptionA = a.getDescriptors();
    	    			Map<String, String> descriptionB = a.getDescriptors();
    	    			int numCriteria = prioritizedCriteria.size();
    	    			for (int i = 1; i <= numCriteria; i++) {
    						if (prioritizedCriteria.containsKey(i)) {
    							SearchCriteria criteria = prioritizedCriteria.get(i);
    							boolean checkA = descriptionA.containsKey(criteria.getCategory().name().toLowerCase());
    							boolean checkB = descriptionB.containsKey(criteria.getCategory().name().toLowerCase());
    							if ((checkA && checkB) || (!checkA && !checkB)) {
    		    					continue;
    							} else if (checkA) {
    								return -1;
    							}
    							return 1;
    							
    						}
    					}
    	    			return 0;
    	    		}
    	    	}).collect(Collectors.toList()));
//    	Set<PointOfInterest> setPoints = new TreeSet<>(new java.util.Comparator<PointOfInterest>() {
//    		@Override
//    		public int compare(PointOfInterest a, PointOfInterest b) {
//    			Map<Object, String> descriptionA = a.getDescriptors();
//    			Map<Object, String> descriptionB = a.getDescriptors();
//    			int numCriteria = prioritizedCriteria.size();
//    			for (int i = 1; i <= numCriteria; i++) {
//					if (prioritizedCriteria.containsKey(i)) {
//						SearchCriteria criteria = prioritizedCriteria.get(i);
//						boolean checkA = descriptionA.containsKey(criteria.getCategory());
//						boolean checkB = descriptionB.containsKey(criteria.getCategory());
//						if ((checkA && checkB) || (!checkA && !checkB)) {
//	    					continue;
//						} else if (checkA) {
//							return -1;
//						}
//						return 1;
//						
//					}
//				}
//    			return 0;
//    		}
//    	});
    	List<PointOfInterest> filterList = new ArrayList<>(filterSet);
        return filterList;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
    	if (criterias == null) {
    		return new ArrayList<PointOfInterest>();
    	}
    	List<PointOfInterest> filterList = listPoints.stream()
    			.filter(point -> {
    				Map<String, String> description = point.getDescriptors();
    				for (SearchCriteria criteria : criterias) {
    					if (!description.containsKey(criteria.getCategory().name().toLowerCase())
	    					|| !description.get(criteria.getCategory().name().toLowerCase()).equals(criteria.getValue())) {
	    					return false;
	    				}
    				}
    				return true;
    			}).collect(Collectors.toList());
        return filterList;
    }
}
