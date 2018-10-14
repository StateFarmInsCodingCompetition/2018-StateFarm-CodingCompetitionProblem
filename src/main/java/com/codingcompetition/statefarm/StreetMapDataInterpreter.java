package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {
	
	String fileToRead;

    public StreetMapDataInterpreter(String s) {
    	fileToRead = s;
    }

    @Override
    public List<PointOfInterest> interpret() {
    	try {
    		String filepath = fileToRead;
    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		Document doc = docBuilder.parse(filepath);

    		NodeList list = doc.getElementsByTagName("node");

    		System.out.println("Total of elements : " + list.getLength());
    		
    		System.out.println(list.equals(fileToRead));
    		
    		for (int i = 0; i < list.getLength(); i++) {
    			
    		}
    		
    	} catch (ParserConfigurationException pce) {
    		pce.printStackTrace();
    	} catch (IOException ioe) {
    		ioe.printStackTrace();
    	} catch (SAXException sae) {
    		sae.printStackTrace();
    	}
    	
		return null;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
    	if (criteria == null) {
    		return Collections.emptyList();
    	}
    	
        return null;
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        return null;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        return null;
    }
}
