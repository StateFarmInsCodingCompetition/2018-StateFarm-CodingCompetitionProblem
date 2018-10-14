package com.codingcompetition.statefarm.utility;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.codingcompetition.statefarm.model.PointOfInterest;

public class PointOfInterestParser {

	private DefaultHandler handler = new DefaultHandler() {

		public void startElement(String uri, String localName, String qName, Attributes attributes) {

			if (qName.equalsIgnoreCase("NODE")) {

				elements.push(attributes.getValue("lat"));
				elements.push(attributes.getValue("lon"));
			}
		}

		public void endElement(String uri, String localName, String qName) {

			if (qName.equalsIgnoreCase("NODE")) {

				objects.push(new PointOfInterest(elements.pop(), elements.pop()));
			}
		}
	};

	// Stacks for storing the elements and objects.
	private Stack<String> elements = new Stack<String>();
	private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();

	public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			saxParser.parse("src/main/resources" + fileName, handler);

			return objects;

		} catch (Exception e) {

			throw new SAXException(e);
		}
	}

}
