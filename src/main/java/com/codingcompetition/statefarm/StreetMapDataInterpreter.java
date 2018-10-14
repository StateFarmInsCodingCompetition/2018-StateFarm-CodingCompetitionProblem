package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.xml.sax.SAXException;

public class StreetMapDataInterpreter implements Interpreter {

	private List<PointOfInterest> interestList;

    public StreetMapDataInterpreter(String s) {
    	try {
			interestList = new PointOfInterestParser().parse(s);
		} catch (IOException | SAXException e) {
			e.printStackTrace();
		}
    }

    @Override
    public List<PointOfInterest> interpret() {
        return interestList;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
    	List<SearchCriteria> list = new ArrayList<>();
    	list.add(criteria);
        return findByCriterias(list);
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        return null;
    }

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
}
