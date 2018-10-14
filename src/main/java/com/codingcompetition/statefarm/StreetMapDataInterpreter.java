package com.codingcompetition.statefarm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;

public class StreetMapDataInterpreter implements Interpreter {
	
	private List<PointOfInterest> poiList;

	public StreetMapDataInterpreter(String s) throws IOException, SAXException {
		poiList = new PointOfInterestParser().parse(s);
	}

	@Override
	public List<PointOfInterest> interpret() {
		return poiList;
	}

	@Override
	public List<PointOfInterest> interpret(SearchCriteria criteria) {
		return null;
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
