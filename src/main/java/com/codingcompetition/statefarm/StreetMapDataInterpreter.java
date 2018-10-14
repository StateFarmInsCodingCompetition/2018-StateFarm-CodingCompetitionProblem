package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {

    List<PointOfInterest> list;

    public StreetMapDataInterpreter(String s) throws IOException {
        list = (new PointOfInterestParser()).parse(s);
    }

    @Override
    public List<PointOfInterest> interpret() {
        return list;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        return interpret(criteria, list);
    }

    private List<PointOfInterest> interpret(SearchCriteria criteria, List<PointOfInterest> baseList) {
        List<PointOfInterest> interpreted = new ArrayList<>();
        if (criteria == null) {
            return interpreted;
        }
        String key = "name";
        if (criteria.cat == Category.NAMESTARTSWITH) {
            for (PointOfInterest poi : baseList) {
                String val = poi.getDescriptors().get(key);
                if (val != null && val.indexOf(criteria.value) == 0) {
                    interpreted.add(poi);
                }
            }
        } else {
            key = criteria.cat.name().toLowerCase();
            for (PointOfInterest poi : baseList) {
                if (criteria.value.equals(poi.getDescriptors().get(key))) {
                    interpreted.add(poi);
                }
            }
        }

        return interpreted;
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        List<PointOfInterest> currentList = list;
        for (Map.Entry<Integer, SearchCriteria> me : prioritizedCriteria.entrySet()) {
            SearchCriteria criteria = me.getValue();
            if (currentList.size() == 0) {
                return currentList;
            }
            currentList = interpret(criteria, currentList);
        }

        return currentList;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        List<PointOfInterest> currentList = new ArrayList<>();
        for (SearchCriteria criteria : criterias) {
            currentList.addAll(interpret(criteria, list));
        }
        return currentList;
    }
}
