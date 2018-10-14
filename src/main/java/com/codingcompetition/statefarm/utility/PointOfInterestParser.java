package com.codingcompetition.statefarm.utility;

import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

import javax.security.sasl.SaslException;

import com.codingcompetition.statefarm.model.PointOfInterest;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;



public class PointOfInterestParser {

    private class MyHandler extends DefaultHandler {
        private List<PointOfInterest> poiList;
        private PointOfInterest poi = null;

        public List<PointOfInterest> getPOIList() {
            return poiList;
        }

        @Override
        public void startElement(String uri, String localname, String qName, Attributes attributes){
            
            if (qName.equalsIgnoreCase("node")) {
                String lati = attributes.getValue("lat");
                String longi = attributes.getValue("lon");
                poi = new PointOfInterest();
                poi.setLatitude(lati);
                poi.setLongitude(longi);

                ArrayList<String> otherAttributes = new ArrayList<String>();
                otherAttributes.add("version");
                otherAttributes.add("timestamp");
                otherAttributes.add("changeset");
                otherAttributes.add("uid");
                otherAttributes.add("user");
                for (String s : otherAttributes) {
                    try {
                        poi.addToDesc(s, attributes.getValue(s));
                    } catch (Exception e) {
                        //System.out.println(s);
                    }
                }


                if (poiList == null) {
                    poiList = new ArrayList<PointOfInterest>();
                }
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase("node")) {
                poiList.add(poi);
            }
        }

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {
            /*
            if (bAge) {
                //age element, set Employee age
                emp.setAge(Integer.parseInt(new String(ch, start, length)));
                bAge = false;
            } else if (bName) {
                emp.setName(new String(ch, start, length));
                bName = false;
            } else if (bRole) {
                emp.setRole(new String(ch, start, length));
                bRole = false;
            } else if (bGender) {
                emp.setGender(new String(ch, start, length));
                bGender = false;
            }
            
        */}

    }


    // Stacks for storing the elements and objects.
    private Stack<String> elements = new Stack<String>();
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();


    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException, ParserConfigurationException {
        System.out.println(fileName);
        /*
        SAXParserFactory spfac = SAXParserFactory.newInstance();
        SAXParser sp = spfac.newSAXParser();
        sp.parse(File(filename))
        */
        //SAXParser saxParser = saxParserFactory.newSAXParser();
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        MyHandler handler = new MyHandler();




        File directory = new File(System.getProperty("user.dir") + "\\target\\test-classes\\");

        String [] directoryContents = directory.list();
        
        List<String> fileLocations = new ArrayList<String>();
        
        for(String y: directoryContents) {
            File temp1 = new File(String.valueOf(directory),y);
            System.out.println(temp1);
            fileLocations.add(String.valueOf(temp1));
        }
        

        System.out.println(System.getProperty("user.dir"));
        System.out.println(fileName);
        String filePathPrefix = System.getProperty("user.dir") + "\\target\\test-classes\\";
        String readString = filePathPrefix + fileName;

        




        File theNewFile =  new File(readString);
        saxParser.parse(theNewFile, handler);
        //Get Employees list
        List<PointOfInterest> poiList = handler.getPOIList();
        //print employee information
        //for(PointOfInterest x : poiList) {
            //System.out.println(x);
        //}

        return poiList;
    }

    
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        PointOfInterestParser parser = new PointOfInterestParser();
        List<PointOfInterest> interpretedData = parser.parse("/small-metro.xml");
        System.out.println("interpreted");
        System.out.println(interpretedData.get(0));
        //System.out.println(interpretedData.size());
    }
    

}
