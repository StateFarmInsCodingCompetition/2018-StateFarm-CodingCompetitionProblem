package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PointOfInterestParser {

    // Stacks for storing the elements and objects.
    private Stack<String> elements = new Stack<String>();
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();

    // Handler for parsing
    private DefaultHandler saxHandler = new DefaultHandler() {
        @Override
        public void startDocument() throws SAXException {
            elements.clear();
        }

        @Override
        public void endDocument() throws SAXException {
            if (elements.size() != 0)
                throw new SAXException("untermintated tags exist");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase("node")) {
                String lat = attributes.getValue("lat");
                String lon = attributes.getValue("lon");
                PointOfInterest poi = new PointOfInterest(lat, lon);

                objects.push(poi);
            } else if (qName.equalsIgnoreCase("tag")) {
                if (elements.peek().equalsIgnoreCase("node")) {
                    PointOfInterest poi = objects.peek();
                    poi.getDescriptors().put(attributes.getValue("k"), attributes.getValue("v"));
                }
            }

            elements.push(qName);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (!elements.peek().equalsIgnoreCase(qName)) {
                throw new SAXException("unmatched tag: " + qName);
            }
            elements.pop();
        }
    };

    /**
     * Read a list of points of interested from a file.
     * @param fileName the name of the file to read from
     * @return a list of the points of interest contained within the referenced file
     */
    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(this.getClass().getResourceAsStream(fileName), this.saxHandler);

        return new ArrayList<>(this.objects);
    }

}
