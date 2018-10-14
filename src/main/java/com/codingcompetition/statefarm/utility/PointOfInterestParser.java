package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class PointOfInterestParser extends DefaultHandler {

	/**
	 * The list of all points of interest parsed by this parser.
	 */
	private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();

	/**
	 * The map of all tags of the point of interest currently being parsed.
	 */
	private HashMap<Object, String> currentTagDescs;

	/**
	 * Parses the xml file with the provided file name.
	 * 
	 * @param fileName The file name of the xml file.
	 * @return The list of all points of interest found in the file.
	 * @throws IOException
	 * @throws SAXException
	 */
	public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {
		File input = new File(fileName);
		if (!input.exists()) {
			input = new File("src/main/resources" + fileName);
		}
		if (!input.exists()) {
			input = new File("src/test/resources" + fileName);
		}
		if (!input.exists()) {
			throw new FileNotFoundException(
					"Could not find the specified file. Please ensure the file is placed in any of the following directories:\n"
					+ "/\n"
					+ "src/main/resources\n"
					+ "src/test/resources");
		}
		SAXParser reader;
		try {
			reader = SAXParserFactory.newInstance().newSAXParser();
			reader.parse(input, this);
		} catch (ParserConfigurationException e) {
			System.err.println("A serious error occurred when configuring the XML parser.");
			e.printStackTrace();
		}

		return objects;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (qName.equalsIgnoreCase("node")) {
			currentTagDescs = new HashMap<>();
			objects.add(new PointOfInterest(attributes.getValue("lat"), attributes.getValue("lon"), currentTagDescs));
		} else if (qName.equalsIgnoreCase("tag")) {
			currentTagDescs.put(attributes.getValue("k"), attributes.getValue("v"));
		}
	}

}
