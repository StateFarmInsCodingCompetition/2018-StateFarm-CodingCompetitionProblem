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
import java.util.stream.Stream;

public class StreetMapDataInterpreter implements Interpreter {

    private List<PointOfInterest> pointsOfInterest;

    public StreetMapDataInterpreter(String fileName) throws ParserConfigurationException, SAXException, IOException {
        PointOfInterestParser parser = new PointOfInterestParser();
        this.pointsOfInterest = parser.parse(fileName);
    }

    @Override
    public List<PointOfInterest> interpret() {
        return this.pointsOfInterest;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        return pointsOfInterest.stream()
                .filter(criteria::test)
                .collect(Collectors.toList());
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        return this.pointsOfInterest.stream()
                .filter(poi -> prioritizedCriteria.values().stream().allMatch(criteria -> criteria.test(poi)))
                .collect(Collectors.toList());
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        return this.pointsOfInterest.stream()
                .filter(poi -> criterias.stream().anyMatch(criteria -> criteria.test(poi)))
                .collect(Collectors.toList());
    }
}
