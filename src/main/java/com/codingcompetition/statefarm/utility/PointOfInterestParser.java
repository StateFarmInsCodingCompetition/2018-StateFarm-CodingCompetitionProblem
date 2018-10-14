package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.text.html.parser.Parser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
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
        ArrayList<PointOfInterest> list = new ArrayList<>();
        String ps = File.pathSeparator;
        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                try {
                    File xmlFile = new File(ps + "src" + ps + "main"
                            + ps + "java" + ps + "resources" + ps + fileName);
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser parser = factory.newSAXParser();
                    DefaultHandler handler = new DefaultHandler();
                    parser.parse(xmlFile, handler);
                } catch (ParserConfigurationException | SAXException e) {
                    System.out.print(e.getMessage());
                }
                return 0;
            }
        };
        return list;
    }

}
