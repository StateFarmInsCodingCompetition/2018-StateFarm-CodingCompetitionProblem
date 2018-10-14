package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PointOfInterestParser {

    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new IOException();
        }
        Document d = builder.parse(getClass().getResource(fileName).getFile());
        NodeList meta = d.getElementsByTagName("node");
        List<PointOfInterest> poi = new ArrayList<>();
        int len = meta.getLength();
        for(int i = 0; i < len; i++) {
            Node n = meta.item(i);
            poi.add(new PointOfInterest(n));
        }
        return poi;
    }

}
