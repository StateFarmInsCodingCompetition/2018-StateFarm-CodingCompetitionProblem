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
	
    public StreetMapDataInterpreter(String s) throws IOException, SAXException {
    	PointOfInterestParser poiParser = new PointOfInterestParser();
    	pois = poiParser.parse(s);
    }

    @Override
    public List<PointOfInterest> interpret() {
        return pois;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
    	return pois.stream().filter(
    		t -> t.getDescriptors().get(criteria.cat).equals(criteria.value)
        ).collect(Collectors.toList());
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        return null;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        return null;
    }
}
