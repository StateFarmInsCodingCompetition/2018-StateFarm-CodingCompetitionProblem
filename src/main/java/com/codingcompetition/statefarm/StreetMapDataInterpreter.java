package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {

	private String fileName;
	
	private Stack<String> elements = new Stack<String>();
	
	private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();
	
	private DefaultHandler handler = new DefaultHandler() {
		
		public void startElement(String uri, String localName, String qName, Attributes attributes) {

			if (qName.equalsIgnoreCase("NODE")) {

				elements.push(attributes.getValue("lat"));
				elements.push(attributes.getValue("lon"));
			}
			
			if (qName.equalsIgnoreCase("TAG")) {
				
				
				elements.push(attributes.getValue("k"));
				elements.push(attributes.getValue("v"));

			}
		}

		public void endElement(String uri, String localName, String qName) {

			if (qName.equalsIgnoreCase("NODE")) {

//				objects.push(new PointOfInterest(elements.pop(), elements.pop()));
			}
		}
	};;

    public StreetMapDataInterpreter(String s) {
    	fileName = s;
    }

    @Override
    public List<PointOfInterest> interpret() {
        return null;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
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
