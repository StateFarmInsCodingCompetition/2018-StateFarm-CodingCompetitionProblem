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
    private String s;

    public StreetMapDataInterpreter(String s) {
        this.s = s;
    }

    @Override
    public List<PointOfInterest> interpret() {
        return null;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        String criteriaString = CriteriaToString(criteria);
        if (!criteriaString.equals("")) {
            if (criteriaString.equals("nameendwith") || criteriaString.equals("namestartwith")) {
                if (criteriaString.equals("nameendwith")) {
                    return findNameEnd(criteria.getValue());
                } else {
                    return findNameStart(criteria.getValue());
                }
            } else {
                return findByTag(criteriaString, criteria.getValue());
            }
        } else {
            return null;
        }
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        return null;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        return null;
    }

    private List<PointOfInterest> findNameStart(String value) {
        return null;
    }

    private List<PointOfInterest> findNameEnd(String value) {
        return null;
    }

    private List<PointOfInterest> findByTag(String key, String value) {
        return null;
    }

    private String CriteriaToString(SearchCriteria criteria) {
        if(criteria.getCategory() == Category.AMENITY) {
            return "amenity";
        } else if (criteria.getCategory() == Category.BEAUTY) {
            return "beauty";
        } else if (criteria.getCategory() == Category.BUILDING) {
            return "building";
        } else if (criteria.getCategory() == Category.CUISINE) {
            return "cuisine";
        } else if (criteria.getCategory() == Category.HIGHWAY) {
            return "highway";
        } else if (criteria.getCategory() == Category.LEISURE) {
            return "leisure";
        } else if (criteria.getCategory() == Category.NAME) {
            return "name";
        } else if (criteria.getCategory() == Category.NAMEENDSWITH) {
            return "nameendwith";
        } else if (criteria.getCategory() == Category.NAMESTARTSWITH) {
            return "namestartwith";
        } else if (criteria.getCategory() == Category.PLACE) {
            return "place";
        } else if (criteria.getCategory() == Category.POPULATION) {
            return "population";
        } else if (criteria.getCategory() == Category.POWER) {
            return "power";
        } else if (criteria.getCategory() == Category.SHOP) {
            return "shop";
        } else if (criteria.getCategory() == Category.WHEELCHAIR) {
            return "wheelchair";
        } else {
            return "";
        }
    }
}
