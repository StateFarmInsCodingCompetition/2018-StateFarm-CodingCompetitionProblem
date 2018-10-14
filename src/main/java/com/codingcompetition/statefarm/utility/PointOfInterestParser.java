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
            //saxParser.parse(new File("/Users/ubicomp/Desktop/Dhruva/2018-StateFarm-CodingCompetitionProblem/src/test/resources"+fileName), handler);
            saxParser.parse(getClass().getResource(fileName).getFile(),handler);//new File("/Users/ubicomp/Desktop/Dhruva/2018-StateFarm-CodingCompetitionProblem/src/test/resources"+fileName), handler);
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
//            System.out.println(lat + " " + lon);
            //initialize Employee object and set id attribute
            // emp = new Employee();
            // emp.setId(Integer.parseInt(id));
            //initialize list
            // if (empList == null)
            //     empList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("tag")) {
            //set boolean values for fields, will be used in setting Employee variables
            String k = attributes.getValue("k").toUpperCase();
            String v = attributes.getValue("v");
            try {
                desc.put(Category.valueOf(k).toString().toLowerCase(),v);
            } catch (IllegalArgumentException ex) {
            }

            //System.out.println(k + " " + v);
        }
        // } else if (qName.equalsIgnoreCase("age")) {
        //     bAge = true;
        // } else if (qName.equalsIgnoreCase("gender")) {
        //     bGender = true;
        // } else if (qName.equalsIgnoreCase("role")) {
        //     bRole = true;
        // }
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

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        // if (bAge) {
        //     //age element, set Employee age
        //     emp.setAge(Integer.parseInt(new String(ch, start, length)));
        //     bAge = false;
        // } else if (bName) {
        //     emp.setName(new String(ch, start, length));
        //     bName = false;
        // } else if (bRole) {
        //     emp.setRole(new String(ch, start, length));
        //     bRole = false;
        // } else if (bGender) {
        //     emp.setGender(new String(ch, start, length));
        //     bGender = false;
        // }
    }
}
