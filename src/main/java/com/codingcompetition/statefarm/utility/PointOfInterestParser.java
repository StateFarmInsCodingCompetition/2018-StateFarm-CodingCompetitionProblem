package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.Category;
import com.codingcompetition.statefarm.model.PointOfInterest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.print.attribute.HashPrintJobAttributeSet;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.lang.Object;
import java.io.File;
import java.io.IOException;


public class PointOfInterestParser extends DefaultHandler{


    // Stacks for storing the elements and objects.
    private Stack<String> elements = new Stack<String>();
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();


    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        handle handler = new handle();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            handler = new handle();
            saxParser.parse(getClass().getResource(fileName).getFile(),handler);
            //TODO: change path name to generic

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return handler.getPoiList();
    }

}

class handle extends DefaultHandler {
    private List<PointOfInterest> poiList = new ArrayList<>();
    private PointOfInterest temp = new PointOfInterest();
    private HashMap<Object, String> desc = new HashMap<>();
    private int nodeNum = 0;

    public List<PointOfInterest> getPoiList() {
        return poiList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("node")) {
            //create a new Employee and put it in Map
            String lat = attributes.getValue("lat");
            String lon = attributes.getValue("lon");
            temp = new PointOfInterest(lat, lon, desc);
        } else if (qName.equalsIgnoreCase("tag")) {
            //set boolean values for fields, will be used in setting Employee variables
            String k = attributes.getValue("k").toUpperCase();
            String v = attributes.getValue("v");
                try {
                    desc.put(Category.valueOf(k).toString().toLowerCase(),v);
                } catch (IllegalArgumentException ex) {
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("node")) {
            //add Employee object to list
            poiList.add(temp);
            desc = new HashMap<>();
            nodeNum++;
        }
    }

}
