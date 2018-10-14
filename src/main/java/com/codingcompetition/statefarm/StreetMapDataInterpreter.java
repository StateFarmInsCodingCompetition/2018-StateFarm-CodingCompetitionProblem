package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {
    private static final String NAME_STARTS_WITH = Category.NAMESTARTSWITH.toString().toLowerCase();
    private static final String NAME_ENDS_WITH = Category.NAMEENDSWITH.toString().toLowerCase();
    private static final String NAME = Category.NAME.toString().toLowerCase();

    private List<PointOfInterest> poiList;

    public StreetMapDataInterpreter(String fileName) {
        PointOfInterestParser pointOfInterestParser = new PointOfInterestParser();
        try {
            poiList = pointOfInterestParser.parse(fileName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<PointOfInterest> interpret() {
        return poiList;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        if (criteria == null) {
            return new ArrayList<>();
        }

        List<SearchCriteria> criteriaList = new ArrayList<>();
        criteriaList.add(criteria);
        return findByCriterias(criteriaList);
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        return null;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        List<PointOfInterest> res = new ArrayList<>();
        if (criterias == null || criterias.size() == 0) {
            return res;
        }

        for (PointOfInterest curPoi : poiList) {
            Map<Object, String> desMap = curPoi.getDescriptors();

            for (SearchCriteria criteria : criterias) {
                if (isCriteriaSatisfy(criteria, desMap)) {
                    res.add(curPoi);
                    break;
                }
            }
        }

        return res;
    }

    private boolean isCriteriaSatisfy(SearchCriteria criteria, Map<Object, String> desMap) {
        if (criteria.getCategory().equals(NAME_STARTS_WITH) && desMap.containsKey(NAME) && desMap.get(NAME).startsWith(criteria.getValue())) {
            return true;
        }

        if (criteria.getCategory().equals(NAME_ENDS_WITH) && desMap.containsKey(NAME) && desMap.get(NAME).endsWith(criteria.getValue())) {
            return true;
        }

        return desMap.containsKey(criteria.getCategory()) && desMap.get(criteria.getCategory()).equals(criteria.getValue());
    }
}
