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

	private List<PointOfInterest> pois;
	
    public StreetMapDataInterpreter(String s) throws IOException, SAXException, ParserConfigurationException {
    	PointOfInterestParser poiParser = new PointOfInterestParser();
    	pois = poiParser.parse(s);
    }

    @Override
    public List<PointOfInterest> interpret() {
        return pois;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
    	List<PointOfInterest> list = new ArrayList<>();
        for(PointOfInterest poi: pois) {
        	Map<Object, String> desc = poi.getDescriptors();
        	String key = criteria.cat.name().toLowerCase();
        	String val = desc.get(key);
        	if(val != null && val.equals(criteria.value)) {
        		list.add(poi);
        		continue;
        	}

        	String name = desc.get(Category.NAME.name().toLowerCase());
        	if(name != null) {
        		String value = criteria.value.toLowerCase();
        		name = name.toLowerCase();
        		if((criteria.cat == Category.NAMESTARTSWITH && name.startsWith(value))
        				|| (criteria.cat == Category.NAMEENDSWITH && name.endsWith(value)))
        			list.add(poi);
        	}
        }
        return list;
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
    	return null;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
    	List<PointOfInterest> list = new ArrayList<>();
    	return null;
    }
}
