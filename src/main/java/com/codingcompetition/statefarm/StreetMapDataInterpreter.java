package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    /**
     * Unfiltered list of points parsed from files
     * @return Returns all parsed points of interest
     */
    @Override
    public List<PointOfInterest> interpret() {
        return points;
    }

    /**
     * Applies a single filter to the points
     * @param criteria The single SearchCriteria to apply
     * @return The list of points to which the single filter applies
     */
    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        List<PointOfInterest> found = new ArrayList<>();
        if (criteria == null) {
            return found;
        } else {
            Category cat = criteria.getCategory();
            String value = criteria.getValue();
            switch(cat) {
                case NAMESTARTSWITH:
                    points = points.stream().filter(p -> p != null && p.getDescriptors().containsKey("name") && p.getDescriptors().get("name").matches("^" + value + ".*")).collect(Collectors.toList());
                    break;
                case NAMEENDSWITH:
                    points = points.stream().filter(p -> p != null && p.getDescriptors().containsKey("name") && p.getDescriptors().get("name").matches(".*" + value +"$")).collect(Collectors.toList());
                    break;
                default:
                    points = points.stream().filter(p -> p != null && p.getDescriptors().containsKey(cat.toString().toLowerCase())
                            && p.getDescriptors().containsValue(value)).collect(Collectors.toList());
                    break;
            }
            return points;
        }
    }

    /**
     * Applies multiple criteria in order of priority, all criteria must be fulfilled, like logical AND
     * @param prioritizedCriteria The Map containing SearchCriteria and their associated priority as key, 1 is highest priority
     * @return The list of points to which all criteria apply in their priority order
     */
    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        if (prioritizedCriteria == null) {
            return new ArrayList<>();
        } else {
            Map<Integer, SearchCriteria> treeMap = new TreeMap<>(prioritizedCriteria);
            List<SearchCriteria> sortedCriteria = new ArrayList<>(treeMap.values());
            for (SearchCriteria criteria : sortedCriteria) {
                Category cat = criteria.getCategory();
                String value = criteria.getValue();
                switch(cat) {
                    case NAMESTARTSWITH:
                        points = points.stream().filter(p -> p != null && p.getDescriptors().containsKey("name") && p.getDescriptors().get("name").matches("^" + value + ".*")).collect(Collectors.toList());
                        break;
                    case NAMEENDSWITH:
                        points = points.stream().filter(p -> p != null && p.getDescriptors().containsKey("name") && p.getDescriptors().get("name").matches(".*" + value +"$")).collect(Collectors.toList());
                        break;
                    default:
                        points = points.stream().filter(p -> p != null && p.getDescriptors().containsKey(cat.toString().toLowerCase())
                        && p.getDescriptors().containsValue(value)).collect(Collectors.toList());
                        break;
                }
            }
            return points;
        }
    }

    /**
     * Applies all criteria in an overlapping fashion, like logical OR
     * @param criterias The list of criteria to apply
     * @return List of points to which at least one criteria applies
     */
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
                List<PointOfInterest> all;
                switch(cat) {
                    case NAMESTARTSWITH:
                        all = points.stream().filter(p -> p != null && !alreadyFound.contains(p) && p.getDescriptors().containsKey("name") && p.getDescriptors().get("name").matches("^" + value + ".*")).collect(Collectors.toList());
                        break;
                    case NAMEENDSWITH:
                        all = points.stream().filter(p -> p != null && !alreadyFound.contains(p) && p.getDescriptors().containsKey("name") && p.getDescriptors().get("name").matches(".*" + value +"$")).collect(Collectors.toList());
                        break;
                    default:
                        all = points.stream().filter(p -> p != null && !alreadyFound.contains(p) && p.getDescriptors().containsKey(cat.toString().toLowerCase())
                                && p.getDescriptors().containsValue(value)).collect(Collectors.toList());
                        break;
                }
                found.addAll(all);
                alreadyFound.addAll(all);
                }
            }
            return found;
        }
    }
