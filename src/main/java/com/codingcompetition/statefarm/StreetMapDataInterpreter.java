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
