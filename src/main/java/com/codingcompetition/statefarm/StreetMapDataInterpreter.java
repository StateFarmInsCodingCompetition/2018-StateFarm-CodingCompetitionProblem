package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import javax.xml.xpath.*;
import java.io.*;

public class StreetMapDataInterpreter implements Interpreter {

    //xml file
	private File data;
	
	private List<PointOfInterest> query = new LinkedList<PointOfInterest>();
	
	XPathFactory factory = XPathFactory.newInstance();

    XPath xpath = factory.newXPath();

    public StreetMapDataInterpreter(String s) {
    	data = new File("src/main/resources" + s);
    }

    @Override
    public List<PointOfInterest> interpret() {
    	query.clear();
    	InputSource inputXml;
	   	
    	try {
            //parse for number of nodes
    		inputXml = new InputSource(new FileInputStream(data));
    		inputXml2 = new InputSource(new FileInputStream(data));
    		NodeList nodes = (NodeList) xpath.evaluate("//node", inputXml, XPathConstants.NODESET);
    		
    		//populate list with Blank Points of Interests
    		for (int i = 0; i < nodes.getLength(); i++) {
    			query.add(new PointOfInterest());  
    	      }
    	    		
    		return query;
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (FileNotFoundException ex) {
		      System.out.print("File Error\n");
		}
		return null;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {  	
    	query.clear();
    	if (criteria == null) {
    		System.out.println(query.size());
        	return query;
        }
        return query;
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
    	query = null;
        return null;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        return null;
    }
}
