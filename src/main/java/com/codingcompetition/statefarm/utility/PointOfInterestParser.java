package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Scanner;
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
        File mFile = new File(fileName);
        SAXParser sP = new SAXParser() {
            @Override
            public Parser getParser() throws SAXException {
                return null;
            }

            @Override
            public XMLReader getXMLReader() throws SAXException {
                return null;
            }

            @Override
            public boolean isNamespaceAware() {
                return false;
            }

            @Override
            public boolean isValidating() {
                return false;
            }

            @Override
            public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {

            }

            @Override
            public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
                return null;
            }
        };
        return null;
    }

}
