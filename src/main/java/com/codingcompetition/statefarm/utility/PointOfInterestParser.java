package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Provides the core logic to parse a Street Map Data XML file
 * into a list of points of interest, with latitude, longitude,
 * and other attributes (from tags) filled in.
 * 
 * @author Neil Thistlethwaite
 * @author Jerry Huang
 */
public class PointOfInterestParser {

    /**
     * Parses the given XML file and returns a list of points of interest.
     * 
     * @param fileName The XML file to parse
     * @return All the points of interest loaded from the given XML file
     * @throws IOException If the file does not exist or this program does not have access.
     * @throws ParserConfigurationException If the XML parser is incorrectly configured.
     * @throws SAXException If the XML file is not valid.
     */
    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException, ParserConfigurationException {
        
        List<PointOfInterest> poiList = new ArrayList<>();
        
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        final Document doc = dBuilder.parse(getClass().getResource(fileName).getFile());
        
        // Only consider <node> tags
        final NodeList nodes = doc.getElementsByTagName("node");
        
        // This is O(n), doing this once outside the loop prevents a massive slowdown
        final int numNodes = nodes.getLength(); 
        
        for (int i = 0; i < numNodes; i++) {
            
            final Node node = nodes.item(i);
            
            // Prevents us from getting any extraneous text nodes (sometimes whitespace and newlines cause issues here)
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            
            final NamedNodeMap nodeAttributes = node.getAttributes();
            
            PointOfInterest poi = null;
            // Try and create a POI with lat and lon attributes, failing loudly if not found
            try {
                poi = new PointOfInterest(nodeAttributes.getNamedItem("lat").getNodeValue(),
                        nodeAttributes.getNamedItem("lon").getNodeValue());
            } catch(Exception e) {
                throw new SAXException("Malformed Street Data XML file!"
                        + " Node tag does not have 'lat'/'lon' attributes!");
            }
            
            final NodeList tags = node.getChildNodes();
            final int numTags = tags.getLength();
            
            // Iterate over all the tags with attribute data and add them as POI attributes
            for (int j = 0; j < numTags; j ++) {
                
                final Node tag = tags.item(j);

                // Again, prevents us from getting any extraneous text nodes (sometimes whitespace and newlines cause issues here)
                if (tag.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                
                final NamedNodeMap tagAttributes = tag.getAttributes();
                
                // Try and add the descriptor to the POI, failing loudly if malformed
                try {
                    poi.addDescriptor(tagAttributes.getNamedItem("k").getNodeValue().toLowerCase(),
                            tagAttributes.getNamedItem("v").getNodeValue());
                } catch(Exception e) {
                    throw new SAXException("Malformed Street Data XML file!"
                            + " Tag does not have 'k'/'v' attributes!");
                }
            }
            poiList.add(poi);
        }
        return poiList;
    }

}
