package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;

public class StreetMapDataInterpreter implements Interpreter {

	private Document doc;
	private String fileName;
	private PointOfInterestParser parser;

    public StreetMapDataInterpreter(String s) throws ParserConfigurationException, SAXException, IOException {
    	File file = new File((System.getProperty("user.dir")) + "\\src\\main\\resources\\" + s);
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder builder = factory.newDocumentBuilder();
    	this.doc = builder.parse(file);
    	
    	this.fileName = (System.getProperty("user.dir")) + "\\src\\main\\resources\\" + s;
    	this.parser = new PointOfInterestParser();

    }

    @Override
    public List<PointOfInterest> interpret() { 
    	try {
			return this.parser.parse(fileName);
		} catch (IOException | SAXException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
    	List<PointOfInterest> finalList = new ArrayList<PointOfInterest>();
        if (criteria == null) {
        	return finalList;
        }
        
        NodeList nList = doc.getElementsByTagName("node");
        int length = nList.getLength();
        Element element;
        Map<Object, String> hashMap = new HashMap<Object, String>();
        for (int i = 0; i < length; i++) {
        	element = (Element) nList.item(i);
        	
        	NodeList tags = element.getElementsByTagName("tag");
        	int tagsLength = tags.getLength();
        	
        	Element tagElement;
        	boolean containsName = false;
        	for (int j = 0; j < tags.getLength(); j++) {
        		 tagElement = (Element) tags.item(j);
        		 if (tagElement != null) {
        			 if (tagElement.getAttribute("k").equals(criteria.getCategory().name().toLowerCase())
             				&& tagElement.getAttribute("v").equals(criteria.getValue())) {

            			 containsName = true;
            		 }
        		 }
        	}
        	
        	if (containsName) {

        		for (int k = 0; k < tagsLength; k++) {
            		tagElement = (Element) tags.item(k);
                	hashMap.put(tagElement.getAttribute("k"), tagElement.getAttribute("v"));
            	}
        		PointOfInterest point = new PointOfInterest(hashMap, element.getAttribute("lat"), element.getAttribute("lon"));
        		finalList.add(point);
        	}
		}
        return finalList;
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
    	List<PointOfInterest> finalList =  new ArrayList<PointOfInterest>();
    	List<SearchCriteria> criteriaList =  new ArrayList<SearchCriteria>();
    	
    	for (SearchCriteria c: prioritizedCriteria.values()) {
    		criteriaList.add(c);
    	}
    	
    	finalList.addAll(findByCriterias(criteriaList));
    	    	    	
    	return finalList;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
    	List<PointOfInterest> finalList =  new ArrayList<PointOfInterest>();
    	List<PointOfInterest> tempList;
 
    	boolean alreadyIn;
    	for (SearchCriteria c: criterias) {
    		
    		tempList = interpret(c);
    		for (PointOfInterest p: tempList) {
    			
    			if (finalList.size() == 0) {
    				finalList.add(p);
    			} else {
    				alreadyIn = true;
	    			for (int i = 0; i < finalList.size(); i++) {
	    				if (finalList.get(i).equals(p)) {
	    					alreadyIn = false;
	    				}
	    			}
	    			
	    			if (alreadyIn) {
	    				finalList.add(p);
	    			}
    			}
    		}
    	}
    	return finalList;
    }
}
