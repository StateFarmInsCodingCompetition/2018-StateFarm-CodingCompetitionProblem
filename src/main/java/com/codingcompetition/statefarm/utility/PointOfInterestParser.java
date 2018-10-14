package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class PointOfInterestParser {


    // Stacks for storing the elements and objects.
    private Stack<String> elements = new Stack<String>();
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();


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
        for(int i = 0; i < meta.getLength(); i++) {
            Node n = meta.item(i);
            poi.add(new PointOfInterest(n));
        }
        return poi;
    }

}
