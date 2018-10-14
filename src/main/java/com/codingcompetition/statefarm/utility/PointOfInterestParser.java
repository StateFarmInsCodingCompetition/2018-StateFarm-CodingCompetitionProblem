package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.Category;
import com.codingcompetition.statefarm.model.PointOfInterest;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PointOfInterestParser {
  public static final String LATITUDE_KEY = "lat";
  public static final String LONGITUDE_KEY = "lon";

  /**
   * Returns a List of parsed PointOfInterest objects from a given XML file
   *
   * @param fileName Name of XML file with PointOfInterest information
   * @return List of parsed PointOfInterest objects
   * @throws IOException if invalid filename is provided
   * @throws SAXException if an error occurs when parsing the XML file
   * @throws ParserConfigurationException if an error occurs when parsing the XML file
   */
  public List<PointOfInterest> parse(String fileName) throws IOException, SAXException, ParserConfigurationException {
      List<PointOfInterest> result = new ArrayList<>();

      File inputFile = getFile(fileName);
      if (inputFile == null) {
        System.out.println("Specified file does not exist. Check the file and try again!");
        return result;
      }

      DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document document = documentBuilder.parse(inputFile);
      document.getDocumentElement().normalize();

      // Parse point of interests into objects
      NodeList childNodes = document.getDocumentElement().getChildNodes();
      for (int i = 0; i < childNodes.getLength(); i++) {
          Node node = childNodes.item(i);
          if (!node.getNodeName().equals("node")) {
              continue;
          }

          PointOfInterest poi = parsePointOfInterest(node);
          if (poi != null) {
            result.add(poi);
          }
      }

      return result;
  }

  /**
   * Returns a parsed PointOfInterest object for a given XML node that
   * contains information about a point of interest. If the given node
   * does not contain latitude or longitude information, returns null.
   *
   * @param poiNode XML node that contains information regarding point of interest
   * @return parsed PointOfInterest object that represents given node or null if node was invalid
   */
  private PointOfInterest parsePointOfInterest(Node poiNode) {
    NamedNodeMap attributeMap = poiNode.getAttributes();
    String latitude = attributeMap.getNamedItem(LATITUDE_KEY).getNodeValue();
    String longitude = attributeMap.getNamedItem(LONGITUDE_KEY).getNodeValue();
    if (latitude == null || longitude == null) {
      return null;
    }

    // Construct point of interest object and parse attributes
    PointOfInterest poi = new PointOfInterest(latitude, longitude);
    NodeList children = poiNode.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node childNode = children.item(i);
      // Skip non-attribute children nodes
      if (!childNode.getNodeName().equals("tag")) {
        continue;
      }

      NamedNodeMap childAttributeMap = childNode.getAttributes();
      String key = childAttributeMap.getNamedItem("k").getNodeValue();
      String value = childAttributeMap.getNamedItem("v").getNodeValue();
      if (Category.isValidCategory(key)) {
        poi.addDescriptor(key, value);
      }
    }

    return poi;
  }

  /**
   * Helper function that returns a File object for a specified file name that exists
   * in the local resources directory. If no such file exists, returns null
   *
   * @param fileName name of the file to read
   * @return File object for specified filename, if it exists. Returns null otherwise
   */
  private File getFile(String fileName) {
    // Remove forward slash at the beginning of file name, if present
    if (fileName.charAt(0) == '/') {
      fileName = fileName.substring(1);
    }

    try {
      ClassLoader classLoader = getClass().getClassLoader();
      return new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
    } catch (NullPointerException ex) {
      return null;
    }
  }

}
