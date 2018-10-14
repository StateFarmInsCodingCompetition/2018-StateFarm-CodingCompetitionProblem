package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {
    private List<PointOfInterest> points;

    public StreetMapDataInterpreter(String s) {
        PointOfInterestParser POIParser = new PointOfInterestParser();
        try {
            points = POIParser.parse(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PointOfInterest> interpret() {
        return points;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        List<PointOfInterest> found = new ArrayList<>();
        if (criteria == null) {
            return found;
        } else {
            Category cat = criteria.getCategory();
            String value = criteria.getValue();
            found = points.stream().filter(p -> p.getDescriptors().containsKey(cat.toString().toLowerCase()) && p.getDescriptors().containsValue(value)).collect(Collectors.toList());
            return found;
        }
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        return null;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        List<PointOfInterest> found = new ArrayList<>();
        HashSet<PointOfInterest> alreadyFound = new HashSet<>();
        if (criterias == null) {
            return found;
        } else {
            for (SearchCriteria criteria : criterias) {
                Category cat = criteria.getCategory();
                String value = criteria.getValue();
                List<PointOfInterest> all = points.stream().filter(p -> !alreadyFound.contains(p) && p.getDescriptors().containsKey(cat.toString().toLowerCase())
                        && p.getDescriptors().containsValue(value)).collect(Collectors.toList());
                for (PointOfInterest p : all) {
                	for (String s : p.getDescriptors().values()) {
                		System.out.print(s + " ");
                	}
                	System.out.println();
                }
                found.addAll(all);
                alreadyFound.addAll(all);
            }
            return found;
        }
    }
}
