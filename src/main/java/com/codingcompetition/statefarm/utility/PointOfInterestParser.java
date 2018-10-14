package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
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
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();

    // implements the handler for the SAXParser
    private class POIHandler extends DefaultHandler {
        // keeps track of whether you are inside of a node or not
        boolean inNode = false;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
                
            // either adds a new node to the stack or adds a descriptor to the topmost node (if we are in a node)
            if (qName.equalsIgnoreCase("node")) {
                PointOfInterest p = new PointOfInterest(attributes.getValue("lat"), attributes.getValue("lon"));
                objects.push(p);
                inNode = true;
            } else if (qName.equalsIgnoreCase("tag") && inNode) {
                objects.peek().addDescriptor(attributes.getValue("k"), attributes.getValue("v"));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase("node")) {
                inNode = false;
            }
        }
    }


    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        POIHandler handler = new POIHandler();
        saxParser.parse(this.getClass().getResourceAsStream(fileName), handler);

        return objects;
    }

}
