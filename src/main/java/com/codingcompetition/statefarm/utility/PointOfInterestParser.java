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

public class PointOfInterestParser {


    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {
    	// Open and parse the file into a Document Object
    	Document document = null;
    	try {
			document = XMLReader.parseXMLFile(fileName);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
    	
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
    		String lat = attr.getNamedItem("lat").getNodeValue();
    		String lon = attr.getNamedItem("lon").getNodeValue();
    		
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

}
