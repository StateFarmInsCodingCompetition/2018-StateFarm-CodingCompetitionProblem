package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {
    private List<PointOfInterest> points;

    public StreetMapDataInterpreter(String s) throws IOException, SAXException {
        PointOfInterestParser parser = new PointOfInterestParser();
        points = parser.parse(s);
    }

    public boolean matchesCriteria(PointOfInterest p, SearchCriteria sc) {
        if (sc.getCategory() == Category.NAMEENDSWITH) {
            return p.getDescriptors().containsKey("name") && p.getDescriptors().get("name").endsWith(sc.getValue());
        }
        else if (sc.getCategory() == Category.NAMESTARTSWITH) {
            return p.getDescriptors().containsKey("name") && p.getDescriptors().get("name").startsWith(sc.getValue());
        }
        else {
            String key = sc.getCategory().name().toLowerCase();
            return p.getDescriptors().containsKey(key) && p.getDescriptors().get(key).equals(sc.getValue());
        }
    }

    @Override
    public List<PointOfInterest> interpret() {
        return points;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        if (criteria == null)
            return Collections.emptyList();

        return points.stream()
                .filter(p -> matchesCriteria(p, criteria))
                .collect(Collectors.toList());
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        return null;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        return points.stream()
                .filter( p ->
                        criterias.stream().anyMatch(sc -> matchesCriteria(p, sc)))
                .collect(Collectors.toList());
    }
}
