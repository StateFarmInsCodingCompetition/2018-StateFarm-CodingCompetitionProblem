package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PointOfInterestParser {


    // Stacks for storing the elements and objects.
    private Stack<String> elements = new Stack<String>();
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();


    public List<PointOfInterest> parse(String fileName) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("src\\main\\resources\\" + fileName));
        String line;
        int count = 0;
        List<PointOfInterest> list = new ArrayList<>();
        while ((line = bf.readLine()) != null) {
            int beginNodeIndex = line.indexOf("<node") + "<node ".length();
            if (beginNodeIndex >= "<node ".length()) {
                // read all the things and make a point of interest
                Map<Object, String> map = new HashMap<>();
                line = line.substring(beginNodeIndex);
                while (line.length() > 2) {
                    String key = parseKey(line);
                    String value = parseValue(line);
                    if (key != null && value != null) {
                        map.put(key, value);
                    }
                    int firstValueIndex = line.indexOf("\" ");
                    line = line.substring((firstValueIndex >= 0) ? firstValueIndex + 2 : line.lastIndexOf('"') + 1);
                }

                // Add all tags to map
                if (!line.contains("/>")) {
                    while (!(line = bf.readLine().trim()).contains("</node>")) {
                        if (line.contains("<tag")) {
                            int firstValueIndex = line.indexOf("=\"");
                            line = line.substring(line.lastIndexOf(' ', firstValueIndex) + 1);
                            String key = parseValue(line);
                            firstValueIndex = line.indexOf("=\"", firstValueIndex + 1);
                            line = line.substring(line.lastIndexOf(' ', firstValueIndex) + 1);
                            String value = parseValue(line);
                            if (key != null && value != null) {
                                map.put(key, value);
                            }
                        }
                    }
                }
                list.add(new PointOfInterest(map));
            }
        }
        return list;
    }

    private String parseKey(String s) {
        int keyEndIndex = s.indexOf('=');
        if (keyEndIndex < 0) {
            return null;
        }
        return s.substring(0, keyEndIndex);
    }

    private String parseValue(String s) {
        int valueBegin = s.indexOf('"') + 1;
        if (valueBegin < 1) {
            return null;
        }
        return s.substring(valueBegin, s.indexOf('"', valueBegin));
    }

}
