package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreetMapDataInterpreter implements Interpreter {

    // A list containing the points of interest
    private List<PointOfInterest> pointsOfInterest;

    /**
     * Parses and interprets street map data from an XML file.
     * @param fileName the name of the XML file
     */
    public StreetMapDataInterpreter(String fileName) throws ParserConfigurationException, SAXException, IOException {
        PointOfInterestParser parser = new PointOfInterestParser();
        this.pointsOfInterest = parser.parse(fileName);
    }

    /**
     * Fetch all points of interest.
     * @return a list containing all points of interest
     */
    @Override
    public List<PointOfInterest> interpret() {
        return this.pointsOfInterest;
    }

    /**
     * Finds the points of interest matching the given criteria.
     * @param criteria the criteria to search for
     * @return a list containing the points which match the criteria
     */
    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        return this.pointsOfInterest.stream()
                .filter(criteria::test)
                .collect(Collectors.toList());
    }

    /**
     * Performs a prioritized search operation which assigns scores to each
     * point of interest based on the prioritized criteria which they match,
     * and returns the points with the highest scores.
     * @param prioritizedCriteria a map of criteria, keyed by a numeric priority
     * @return a list containing the top, matching results
     */
    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        // Compute the scores of each point of interest
        Map<PointOfInterest, Integer> scores = new HashMap<>();
        prioritizedCriteria.forEach((priority, criteria) -> {
            this.interpret(criteria).forEach(pointOfInterest -> {
                scores.put(pointOfInterest, scores.getOrDefault(pointOfInterest, 0) + priority);
            });
        });

        // Pick only those with the highest scores
        int bestScore = Collections.max(scores.values());
        return scores.entrySet().stream()
                .filter(entry -> entry.getValue() == bestScore)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Find points such that at least one criteria matches.
     * @param criterias a list of SearchCriteria
     * @return a list of points of interest where each matches at least one criteria
     */
    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        return this.pointsOfInterest.stream()
                .filter(poi -> criterias.stream().anyMatch(criteria -> criteria.test(poi)))
                .collect(Collectors.toList());
    }
}
