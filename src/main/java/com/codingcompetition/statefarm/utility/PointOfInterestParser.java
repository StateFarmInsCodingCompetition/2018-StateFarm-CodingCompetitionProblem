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


    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(this.getClass().getResourceAsStream(fileName), new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if (qName.equalsIgnoreCase("node")) {
                    objects.push(new PointOfInterest(attributes.getValue("lat"), attributes.getValue("lon")));
                } else if (qName.equalsIgnoreCase("tag")) {
                    PointOfInterest poi = objects.peek();
                    poi.getDescriptors().put(attributes.getValue("k"), attributes.getValue("v"));
                }
//                super.startElement(uri, localName, qName, attributes);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                super.endElement(uri, localName, qName);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                super.characters(ch, start, length);
            }
        });

        return objects;
    }

}
