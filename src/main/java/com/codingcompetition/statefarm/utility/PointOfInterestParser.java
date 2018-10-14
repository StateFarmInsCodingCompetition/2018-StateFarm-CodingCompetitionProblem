package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.Category;
import com.codingcompetition.statefarm.model.PointOfInterest;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
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


    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException, ParserConfigurationException {
        List<PointOfInterest> result = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(classLoader.getResource(fileName.substring(1)).getFile());

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getDocumentElement().getChildNodes();

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);

            if (!node.getNodeName().equals("node")) {
                continue;
            }

            NamedNodeMap attributeMap = node.getAttributes();
            String latitude = attributeMap.getNamedItem("lat").getNodeValue();
            String longitude = attributeMap.getNamedItem("lon").getNodeValue();
            PointOfInterest poi = new PointOfInterest(latitude, longitude);
            NodeList children = node.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node childNode = children.item(j);
                if (!childNode.getNodeName().equals("tag")) {
                    continue;
                }
                NamedNodeMap childAttributeMap = childNode.getAttributes();
                String key = childAttributeMap.getNamedItem("k").getNodeValue();
                try {
                    Category category = Category.valueOf(key.toUpperCase());
                    String value = childAttributeMap.getNamedItem("v").getNodeValue();
                    poi.addDescriptor(category, value);
                } catch (IllegalArgumentException ex) {

                }
            }
            result.add(poi);
        }
        return result;
    }

}
