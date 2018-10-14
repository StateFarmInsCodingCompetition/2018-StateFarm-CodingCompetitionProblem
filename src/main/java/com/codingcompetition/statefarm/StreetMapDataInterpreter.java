package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {
    String fileName;
    List<PointOfInterest> pointsOfInterest;


    public StreetMapDataInterpreter(String fileName) {
        this.fileName = fileName;
        try {
            pointsOfInterest = new PointOfInterestParser().parse(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            pointsOfInterest = null;
        }
    }

    @Override
    public List<PointOfInterest> interpret() {
        return pointsOfInterest;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        if (criteria == null) {
            return Collections.emptyList();
        }
        List<PointOfInterest> toReturn = pointsOfInterest.stream()
                .filter(poi -> {
                    Map<Object, String> descriptors = poi.getDescriptors();
                    boolean shouldAdd = false;
                    for (Object key : descriptors.keySet()) {
                        String s = (String) key; // always safe, no worries
                        boolean success = false;
                        String poiValue = poi.getDescriptors().get(key);
                        if (criteria.getCat() == Category.NAMESTARTSWITH) {
                            success = descriptors.containsKey("name") && descriptors.get("name").charAt(0) == criteria.getValue().charAt(0) && !poi.isAdded();
                        } else if (criteria.getCat() == Category.NAMEENDSWITH) {
                            success = descriptors.containsKey("name") && descriptors.get("name").substring(descriptors.get("name").length() - criteria.getValue().length()).equals(criteria.getValue()) && !poi.isAdded();
                        } else {
                            success = determineCategory(s) == criteria.getCat() && poiValue.equals(criteria.getValue())
                                    && !poi.isAdded();
                        }

                        if (success) {
                            poi.setAdded(true);
                            shouldAdd = true;
                        }
                    }
                    return shouldAdd;
                }).collect(Collectors.toList());

        // reset flags
        for(PointOfInterest poi : pointsOfInterest) {
            poi.setAdded(false);
        }
        return toReturn;
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        List<PointOfInterest> toReturn = new ArrayList<>();
        boolean success = false;
        if (prioritizedCriteria.size() > 0) {
            toReturn.addAll(pointsOfInterest);

            for(Integer i : prioritizedCriteria.keySet()) {
                toReturn.retainAll(interpret(prioritizedCriteria.get(i)));
            }
        }

        return toReturn == null ? new ArrayList<>() : toReturn;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        Set<PointOfInterest> unique = new HashSet<PointOfInterest>();
        for(SearchCriteria criteria : criterias) {
            unique.addAll(interpret(criteria));
        }
        List<PointOfInterest> toReturn = new ArrayList<>();
        toReturn.addAll(unique);
        return toReturn;
    }

    private Category determineCategory(String cat) {
        switch (cat.toLowerCase()) {
            case "leisure":
                return Category.LEISURE;
            case "name":
                return Category.NAME;
            case "amenity":
                return Category.AMENITY;
            case "cuisine":
                return Category.CUISINE;
            case "shop":
                return Category.SHOP;
            case "wheelchair":
                return Category.WHEELCHAIR;
            case "highway":
                return Category.HIGHWAY;
            case "place":
                return Category.PLACE;
            case "population":
                return Category.POPULATION;
            case "power":
                return Category.POWER;
            case "building":
                return Category.BUILDING;
            case "beauty":
                return Category.BEAUTY;
            default:
                return Category.PLACE;
        }
    }
}
