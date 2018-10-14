package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {
    private PointOfInterestParser POIParser = new PointOfInterestParser();
    private List<PointOfInterest> POIs;

    /**
     * construct a StreetMapDataInterpreter from PointOfInterestParser
     * @param s filename to be parsed to the PointOfInterestParser
     */
    public StreetMapDataInterpreter(String s) throws IOException, SAXException {
        this.POIs = this.POIParser.parse(s);
    }

    @Override
    public List<PointOfInterest> interpret() {
        return this.POIs;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        return interpret(criteria, this.POIs);
    }

    /**
     * Interpret a given criteria within the given list of PointOfInterest
     * @Param criteria  the search criteria
     * @Param POIs  the list of PointOfInterest to search from
     * @return list of PointOfInterest if the criteria is valid (in the Category Enum class), null otherwise
     */
    private List<PointOfInterest> interpret(SearchCriteria criteria, List<PointOfInterest> POIs ) {
        String criteriaString = CriteriaToString(criteria);
        if (!criteriaString.equals("")) {
            if (criteriaString.equals("nameendwith") || criteriaString.equals("namestartwith")) {
                if (criteriaString.equals("nameendwith")) {
                    return findNameEnd(criteria.getValue(), POIs);
                } else {
                    return findNameStart(criteria.getValue(), POIs);
                }
            } else {
                return findByTag(criteriaString, criteria.getValue(), POIs);
            }
        } else {
            return null;
        }
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        List<PointOfInterest> result = new ArrayList<>();
        Set<String> POIId = new HashSet<>();
        List<Integer> priorityList = priorityList(prioritizedCriteria);
        for (Integer priority : priorityList) {
            if (result.size() == 0) {
                result = interpret(prioritizedCriteria.get(priority));
            } else {
                result = interpret(prioritizedCriteria.get(priority), result);
            }
        }
        return result;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        Map<String, String> criteriaMap = new HashMap<>();
        for (SearchCriteria aCriteria: criterias) {
            String citeriaString = CriteriaToString(aCriteria);
            criteriaMap.put(citeriaString, aCriteria.getValue());
        }
        return findByCriterias(criteriaMap, this.POIs);

    }

    /**
     * order the key of prioritizedCriteria based in increasing numeric order and return as a list of integers
     * @param prioritizedCriteria   a map of criteria with priority
     * @return list of keys in increasing numeric order
     */
    private List<Integer> priorityList(Map<Integer, SearchCriteria> prioritizedCriteria) {
        List<Integer> priorityList = new ArrayList<>();
        for (Integer priority : prioritizedCriteria.keySet()) {
            priorityList.add(priority);
        }
        Collections.sort(priorityList);
        return priorityList;
    }


    /**
     * find the matched PointOfInterest based on the given criteria in criteriaMap from the given list of PointOfInterests
     * @param criteriaMap   a map of criteria category and criteria value
     * @param POIs  list of PointOfInterest to be searched from
     * @return list of PointOfInterests if satisfy any of the criteria (no duplicate)
     */
    private List<PointOfInterest> findByCriterias(Map<String, String> criteriaMap, List<PointOfInterest> POIs) {
        List<PointOfInterest> result = new ArrayList<>();
        Set<String> POIId = new HashSet<>();
        for (PointOfInterest aPOI : POIs) {

            if (criteriaMap.keySet().size() != 0) {
                boolean match = false;
                for (String key : criteriaMap.keySet()) {
                    String value = criteriaMap.get(key);
                    if (aPOI.getValueFromTag(key) != null && aPOI.getValueFromTag(key).equals(value)) {
                        match = true;
                        break;
                    }
                }
                if (match && !POIId.contains(aPOI.getId())) {
                    result.add(aPOI);
                    POIId.add(aPOI.getId());
                }

            }
        }
        return result;
    }

    /**
     * get the list of PointOfInterests with the specified starting name
     * @param POIs  list of PointOfInterest to be searched from
     * @param value String representation of the start of the name
     * @return list of PointOfInterests if the name start with given value within the given POIs
     */
    private List<PointOfInterest> findNameStart(String value, List<PointOfInterest> POIs) {
        List<PointOfInterest> result = new ArrayList<>();
        Set<String> POIId = new HashSet<>();
        for (PointOfInterest aPOI : POIs) {
            String name = aPOI.getValueFromTag("name");
            if (name != null) {
                char[] valueArray = value.toCharArray();
                char[] nameArray = name.toCharArray();
                if (valueArray.length <= nameArray.length) {
                    int index = 0;
                    boolean matched = true;

                    while (index < valueArray.length) {
                        if (valueArray[index] != nameArray[index]) {
                            matched = false;
                            break;
                        } else {
                            index++;
                        }
                    }
                    if (matched && !POIId.contains(aPOI.getId())) {
                        result.add(aPOI);
                        POIId.add(aPOI.getId());
                    }
                }
            }

        }
        return result;
    }

    /**
     * get the list of PointOfInterests with the specified ending name
     * @param POIs  list of PointOfInterest to be searched from
     * @param value String representation of the end of the name
     * @return list of PointOfInterests if the name end with given value within the given POIs
     */
    private List<PointOfInterest> findNameEnd(String value, List<PointOfInterest> POIs) {
        List<PointOfInterest> result = new ArrayList<>();
        Set<String> POIId = new HashSet<>();
        for (PointOfInterest aPOI: POIs) {
            String name = aPOI.getValueFromTag("name");
            if (name != null) {
                char[] valueArray = value.toCharArray();
                char[] nameArray = name.toCharArray();
                boolean matched = true;
                int nameArrayIndex = nameArray.length - 1;
                int valueArrayIndex = valueArray.length - 1;
                if (valueArray.length <= nameArray.length) {
                    while (valueArrayIndex >= 0) {
                        if (nameArray[nameArrayIndex] != valueArray[valueArrayIndex]) {
                            matched = false;
                            break;
                        } else {
                            nameArrayIndex--;
                            valueArrayIndex--;
                        }
                    }
                    if (matched && !POIId.contains(aPOI.getId())) {
                        result.add(aPOI);
                        POIId.add(aPOI.getId());
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param key   String representation of the tag key
     * @param value String representation of the tag value
     * @param POIs  list of PointOfInterest to be searched from
     * @return list of PointOfInterests if the key and value matches with its descriptor within the given POIs
     */
    private List<PointOfInterest> findByTag(String key, String value, List<PointOfInterest> POIs) {
        List<PointOfInterest> result = new ArrayList<>();
        Set<String> POIId = new HashSet<>();
        for (PointOfInterest aPOI: POIs) {
            if (aPOI.getValueFromTag(key) != null && aPOI.getValueFromTag(key).equals(value) && !POIId.contains(aPOI.getId())) {
                POIId.add(aPOI.getId());
                result.add(aPOI);
            }
        }
        return result;
    }
    /**
     * map the criteria category to string
     * @param criteria  a search criteria
     * @return string representation of the category of criteria
     */
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
