package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {

    private PointOfInterestParser parser;
    private List<PointOfInterest> interpretedData;
    private String filename;


    public StreetMapDataInterpreter(String s) {
        parser = new PointOfInterestParser();
        try {
            interpretedData = parser.parse(s);
        } catch (IOException | SAXException e) {

        }
    }

    @Override
    public List<PointOfInterest> interpret() {
        return interpretedData;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        ArrayList<PointOfInterest> matchedCriteria = new ArrayList<>();
        if (criteria == null) {
            return matchedCriteria;
        }
        for (int i = 0; i < interpretedData.size(); i++) {
            Map<Object, String> descriptors = interpretedData.get(i).getDescriptors();
            int criteriaStringLength = criteria.getVal().length();
            if (criteria.getCategory().equals(Category.NAMESTARTSWITH)) {
                if (descriptors.get("name") != null && descriptors.get("name").substring(0, criteriaStringLength).equals(criteria.getVal())) {
                    matchedCriteria.add(interpretedData.get(i));
                }
            }
            else if (criteria.getCategory().equals(Category.NAMEENDSWITH)) {
                if (descriptors.get("name") != null && descriptors.get("name").substring(descriptors.get("name").length() - criteriaStringLength).equals(criteria.getVal())) {
                    matchedCriteria.add(interpretedData.get(i));
                }
            }
            else if (descriptors.get(criteria.getLowerCategory()) != null && descriptors.get(criteria.getLowerCategory()).equals(criteria.getVal())) {
                matchedCriteria.add(interpretedData.get(i));
            }
        }
        return matchedCriteria;
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        ArrayList<PointOfInterest> matchedCriteria = new ArrayList<>();
        if (prioritizedCriteria == null) {
            return matchedCriteria;
        }
        for (int i = 0; i < interpretedData.size(); i++) {
            Map<Object, String> descriptors = interpretedData.get(i).getDescriptors();
            int count = 0;
            for(int j = 0 ; j < prioritizedCriteria.size(); j++) {
                SearchCriteria instant = prioritizedCriteria.get(j);
                boolean changed = false;
                int criteriaStringLength = instant.getVal().length();
                if (instant.getCategory().equals(Category.NAMESTARTSWITH)) {
                    if (descriptors.get("name") != null && descriptors.get("name").substring(0, criteriaStringLength).equals(instant.getVal())) {
                        count++;
                        changed = true;
                    }
                }
                else if (instant.getCategory().equals(Category.NAMEENDSWITH)) {
                    if (descriptors.get("name") != null && descriptors.get("name").substring(descriptors.get("name").length() - criteriaStringLength).equals(instant.getVal())) {
                        count++;
                        changed = true;
                    }
                }
                else if (descriptors.get(instant.getLowerCategory()) != null && descriptors.get(instant.getLowerCategory()).equals(instant.getVal())) {
                    count++;
                    changed = true;
                }

                if(!changed) break;
            }
            if (count == prioritizedCriteria.size()) {
                matchedCriteria.add(interpretedData.get(i));
            }
        }
        return matchedCriteria;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        ArrayList<PointOfInterest> matchedCriteria = new ArrayList<>();
        if (criterias == null) {
            return matchedCriteria;
        }
        for (int i = 0; i < interpretedData.size(); i++) {
            Map<Object, String> descriptors = interpretedData.get(i).getDescriptors();
            for(int j = 0 ; j < criterias.size(); j++) {
                SearchCriteria instant = criterias.get(j);
                boolean changed = false;
                int criteriaStringLength = instant.getVal().length();
                if (instant.getCategory().equals(Category.NAMESTARTSWITH)) {
                    if (descriptors.get("name") != null && descriptors.get("name").substring(0, criteriaStringLength).equals(instant.getVal())) {
                        changed = true;
                    }
                }
                else if (instant.getCategory().equals(Category.NAMEENDSWITH)) {
                    if (descriptors.get("name") != null && descriptors.get("name").substring(descriptors.get("name").length() - criteriaStringLength).equals(instant.getVal())) {
                        changed = true;
                    }
                }
                else if (descriptors.get(instant.getLowerCategory()) != null && descriptors.get(instant.getLowerCategory()).equals(instant.getVal())) {
                    changed = true;
                }

                if(changed) {
                    matchedCriteria.add(interpretedData.get(i));
                    break;
                }
            }
        }
        return matchedCriteria;
    }
}
