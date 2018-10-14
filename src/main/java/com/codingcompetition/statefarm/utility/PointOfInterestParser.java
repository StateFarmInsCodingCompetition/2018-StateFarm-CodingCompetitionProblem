package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.Category;
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
import java.util.Arrays;
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
//            	System.out.println(currentNode.getAttributes());
            	PointOfInterest currentPOT = getPointOfInterest(currentNode.getAttributes());
<<<<<<< HEAD
            	System.out.println(i);
//            	System.out.println(currentNode.getAttributes().getLength());
=======
            	System.out.println("i " + i);
            	 if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
            		 Element element = (Element) currentNode;
            		 NodeList tagList = element.getElementsByTagName("tag");
            		 for (int j = 0; j < tagList.getLength(); j++) {
            			 String key = tagList.item(j).getAttributes().getNamedItem("k").getNodeValue();
            			 String value = tagList.item(j).getAttributes().getNamedItem("v").getNodeValue();
//            			 List<Category> enumList = Arrays.asList(Category.values());
//            			 System.out.println(key.toUpperCase());
//            			 if (enumList.contains(key.toUpperCase())) {
//            				 System.out.println("go hereee");
//            				 System.out.println(key);
//            			 }
            			 currentPOT.getDescriptors().put(key, value);
            		 }
            	 }
>>>>>>> 8582f498e23c7911a414a397b87c1d726fead41a
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

//    private static boolean containInEnum(String key, List<Category> list) {
//    	for (Category c : list) {
//    		if(c.name().equals(c))
//    			return true;
//    	}
//    	return false;
//    }


}
