package com.codingcompetition.statefarm.model;

import com.codingcompetition.statefarm.Category;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//this is crucial
import org.w3c.dom.NamedNodeMap;

import java.util.HashMap;
import java.util.Map;


public class PointOfInterest {
    private Map<Object, String> items = new HashMap<Object, String>();
    private String longitude;
    private String latitude;

    public PointOfInterest(Node instant) {
        NamedNodeMap attributes = instant.getAttributes();
        latitude = attributes.getNamedItem("lat").getNodeValue();
        longitude = attributes.getNamedItem("lon").getNodeValue();

        //System.out.println(latitude + " " + longitude);

        NodeList children = instant.getChildNodes();
        for(int i = 0; i < children.getLength(); i++)
        {
            Node currentNode = children.item(i);
            if(currentNode != null && currentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                if(currentNode.getNodeName().equals("tag"))
                {
                    NamedNodeMap subattributes = currentNode.getAttributes();
                    String k = subattributes.getNamedItem("k").getNodeValue();
                    String v = subattributes.getNamedItem("v").getNodeValue();
                    try {
                        items.put(Category.valueOf(k.toUpperCase()), v);
                    } catch (IllegalArgumentException e){

                }
                }
            }
        }
    }

    public Map<Object,String> getDescriptors()
    {
        return items;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }


}