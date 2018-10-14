package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.sun.javafx.scene.paint.GradientUtils.Parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PointOfInterestParser {

    // Stacks for storing the elements and objects.
    private Stack<String> elements = new Stack<String>();
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();

    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {
    	System.out.println();
    	try {
    		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        	
        	SAXParser parser = parserFactory.newSAXParser();

        	DefaultHandler handler = new DefaultHandler() {
        		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {         		
            		if (qName.equalsIgnoreCase("NODE")) {
            			String lat = attributes.getValue("lat");
            			elements.push(lat);
            			String lon = attributes.getValue("lon");
            			elements.push(lon);
            			
            			objects.add(new PointOfInterest());
            		} 
            		
            		if (qName.equalsIgnoreCase("TAGS")) {
            			String k = attributes.getValue("k");
            			elements.push(k);
            			String v = attributes.getValue("v");
            			elements.push(v);
            			
            		}
            	}
        		
        		@SuppressWarnings("unused")
				public void lastElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            		System.out.println("Last element: " + qName);
            	}
        	};
        	
        	parser.parse(fileName, handler);
        	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
		return objects;	
    }
}
