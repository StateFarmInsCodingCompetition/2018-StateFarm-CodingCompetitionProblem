package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.StreetMapDataInterpreter;
import com.codingcompetition.statefarm.model.PointOfInterest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PointOfInterestParser {

    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {
    	return new StreetMapDataInterpreter(fileName).interpret();
    }

}
