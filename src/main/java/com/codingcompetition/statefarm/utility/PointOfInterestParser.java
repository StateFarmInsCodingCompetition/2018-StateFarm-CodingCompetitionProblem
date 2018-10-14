package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PointOfInterestParser {


    // Stacks for storing the elements and objects.
    private Stack<String> elements = new Stack<String>();
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();


    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {
    	String filePath = "./src/main/resources/small-metro.xml";
    	File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        List<PointOfInterest> potList = new ArrayList<PointOfInterest>();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("node");
            
            //now XML is loaded as Document in memory, lets convert it to Object List
            for (int i = 0; i < nodeList.getLength(); i++) {
            	Node currentNode = nodeList.item(i);
            	PointOfInterest currentPOT = getPointOfInterest(currentNode.getAttributes());
            	System.out.println(i);
//            	System.out.println(currentNode.getAttributes().getLength());
                potList.add(currentPOT);
     
            }
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }

        
    	return potList;

    }

        private static PointOfInterest getPointOfInterest(NamedNodeMap node) {
        //XMLReaderDOM domReader = new XMLReaderDOM();
        PointOfInterest pot = new PointOfInterest();
        pot.setLongtitude(getTagValue("lon", node));
        pot.setLatitude(getTagValue("lat", node));
        return pot;
    }


    private static String getTagValue(String tag, NamedNodeMap element) {
        String res = element.getNamedItem(tag).getNodeValue();
        return res;
    }

}
