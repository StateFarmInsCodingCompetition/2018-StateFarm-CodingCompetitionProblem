package com.codingcompetition.statefarm;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * An XML file reader wrapper
 * @author Jeremy Schonfeld and Robert Pooley
 */
public class XMLReader {
	
	/**
	 * Parses an XML file into a Document object
	 * 
	 * Note: This method loads the {@code filename} by calling {@code getResource} which loads the file from the resources
	 * 
	 * @param filename The name of the file containing the XML
	 * @return The Document object representing the XML structure
	 * @throws ParserConfigurationException if there is an issue creating the XML parser
	 * @throws SAXException if there is an issue parsing the XML file
	 * @throws IOException if there is an issue opening/reading the file
	 */
	public static Document parseXMLFile(String filename) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder build = factory.newDocumentBuilder();
		File file = new File(XMLReader.class.getResource(filename).getFile());
		return build.parse(file);
	}
	
}
