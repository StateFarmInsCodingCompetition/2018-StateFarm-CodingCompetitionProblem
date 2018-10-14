package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;

import java.util.List;
import java.util.Map;

public interface Interpreter {

    public List<PointOfInterest> interpret();

    public List<PointOfInterest> interpret(SearchCriteria criteria);

    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria);

    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias);
}
