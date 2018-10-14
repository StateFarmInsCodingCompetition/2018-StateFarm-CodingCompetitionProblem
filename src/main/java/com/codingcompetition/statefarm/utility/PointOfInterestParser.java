package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class PointOfInterestParser {


    // Stacks for storing the elements and objects.
    private Stack<String> elements = new Stack<String>();
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();


    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {
        fileName = "src/test/resources" + fileName;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        List<PointOfInterest> poiList = null;
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(new File(fileName), handler);
            poiList = handler.getPoiList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return poiList;
    }

    private class MyHandler extends DefaultHandler {
        private List<PointOfInterest> poiList = new ArrayList<>();
        private PointOfInterest poi = null;
        private HashMap<Object, String> descriptor = null;
        private int i = 0;

        public List<PointOfInterest> getPoiList() {
            return poiList;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equalsIgnoreCase("node")){
                descriptor = new HashMap<>();
                poi = new PointOfInterest(attributes.getValue("lat"), attributes.getValue("lon"), attributes.getValue("id"));
            } else if (qName.equalsIgnoreCase("tag")) {
                descriptor.put(attributes.getValue("k"), attributes.getValue("v"));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase("node")) {
                i += 1;
//                System.out.println(poiList.size());
                poi.setDescriptors(descriptor);
                poiList.add(poi);
            }
        }
    }

}
