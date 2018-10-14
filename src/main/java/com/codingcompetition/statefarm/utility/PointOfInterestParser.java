package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PointOfInterestParser {


    // Stacks for storing the elements and objects.
    private Stack<String> elements = new Stack<String>();
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();

    public class MyHandler extends DefaultHandler{
    	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    		if(qName.equals("node")) {
    			String lat = attributes.getValue("lat");
    			String lon = attributes.getValue("lon");
    			
    			PointOfInterest poi = new PointOfInterest(lat, lon);
    			
    			objects.add(poi);
    		}
    	}
    	
    	public void endElement(String uri, String localName, String qName) throws SAXException {
    		
    	}

    	public void characters(char ch[], int start, int length) throws SAXException {
    		
    		
    	}
    }
    
    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {
    	SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    	SAXParser parser = parserFactory.newSAXParser();
    	
    	MyHandler handler = new MyHandler();
    	
    	InputStream is = new FileInputStream(fileName);
    	parser.parse(is, handler);
   return null;
    }

}
