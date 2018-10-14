package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.XMLReader;
import com.codingcompetition.statefarm.model.PointOfInterest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that parses point of interest information from an OpenStreetMap XML file
 * @author Jeremy Schonfeld and Robert Pooley
 */
public class PointOfInterestParser {

	private String fileName;
	
	private double minLat;
	
	private double minLong;
	
	private double maxLat;
	
	private double maxLong;

	/**
	 * Parses an OpenStreetMap XML file into a list of POIs
	 * @param fileName The name of the XML file to parse
	 * @return A list of all points of interest contained in the XML file
	 * @throws IOException if there is an issue opening/reading the file
	 * @throws SAXException if there is an issue parsing the XML content of the file
	 */
    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {
    	if (fileName == null) {
    		throw new IllegalArgumentException("fileName cannot be null!");
    	}
    	
    	this.fileName = fileName;
    	
    	// Open and parse the file into a Document Object
    	Document document = null;
    	try {
			document = XMLReader.parseXMLFile(fileName);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
    	
    	// Fetch the bounds
    	NodeList boundsList = document.getElementsByTagName("bounds");
    	if (boundsList.getLength() != 1) {
    		throw new RuntimeException("Could not load bounds for file " + fileName);
    	}
    	Node bounds = boundsList.item(0);
    	NamedNodeMap attrMap = bounds.getAttributes();
    	minLat = Double.parseDouble(attrMap.getNamedItem("minlat").getNodeValue());
    	minLong = Double.parseDouble(attrMap.getNamedItem("minlon").getNodeValue());
    	maxLat = Double.parseDouble(attrMap.getNamedItem("maxlat").getNodeValue());
    	maxLong = Double.parseDouble(attrMap.getNamedItem("maxlon").getNodeValue());
    	
    	// Loop through all <node> tags
    	NodeList nodes = document.getElementsByTagName("node");
    	List<PointOfInterest> pois = new ArrayList<PointOfInterest>();
    	int len = nodes.getLength();
    	
    	for (int i = 0; i < len; i++) {
    		Node n = nodes.item(i);
    		if (n.getNodeType() != Node.ELEMENT_NODE) { // Only look at element nodes
    			continue;
    		}
    		Element node = (Element) n;
    		
    		// Store the lattitude and longitude attributes
    		NamedNodeMap attr = node.getAttributes();
    		Node latNode = attr.getNamedItem("lat"), lonNode = attr.getNamedItem("lon");
    		String lat = null, lon = null;
    		if (latNode != null && lonNode != null) {
    			lat = latNode.getNodeValue();
    			lon = lonNode.getNodeValue();
    		}
    		
    		// Store all <tag> elements as descriptors
    		Map<Object, String> descriptors = new HashMap<Object, String>();
    		NodeList tags = node.getChildNodes();
    		int tagLen = tags.getLength();
    		for (int j = 0; j < tagLen; j++) {
    			Node t = tags.item(j);
    			if (t.getNodeType() != Node.ELEMENT_NODE) { // Only look at element nodes
        			continue;
        		}
    			Element tag = (Element) t;
    			
    			// Store the key (k) and value (v) in descriptors
    			NamedNodeMap tagAttr = tag.getAttributes();
    			String key = tagAttr.getNamedItem("k").getNodeValue();
    			String value = tagAttr.getNamedItem("v").getNodeValue();
    			descriptors.put(key, value);
    		}
    		
    		PointOfInterest poi = new PointOfInterest(lat, lon, descriptors);
    		pois.add(poi);
    	}
    	
    	return pois;
    	
    }

    public String getFileName() {
    	return fileName;
    }
    
    public double getMinLat() {
    	return minLat;
    }
    
    public double getMinLong() {
    	return minLong;
    }
    
    public double getMaxLat() {
    	return maxLat;
    }
    
    public double getMaxLong() {
    	return maxLong;
    }
}
