package com.codingcompetition.statefarm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.codingcompetition.statefarm.model.PointOfInterest;

public class StreetMapDataInterpreter implements Interpreter {

	//	private Document network;

	private List<PointOfInterest> points;

	public StreetMapDataInterpreter(String s) {
		//get the file
		File fXmlFile = new File("src/test/resources"+s);

		//parse the xml
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document network = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			network = dBuilder.parse(fXmlFile);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			network = null;
			e.printStackTrace();
		}

		//grab all tags under the first that are named "node" (coincidentally enough...)
		if(network == null) {
			points = new ArrayList<PointOfInterest>();
		} else {
			NodeList n = network.getChildNodes().item(0).getChildNodes();
			List<PointOfInterest> l = new ArrayList<PointOfInterest>(n.getLength());
			for(int i=0; i<n.getLength(); i++){
				Node node = n.item(i);
				if(node.getNodeName().equals("node")) {
					l.add(new PointOfInterest(node));
				}
			}
			points = l;
		}	        

	}

	@Override
	public List<PointOfInterest> interpret() {
		//lol, we already did
		return points;
	}

	@Override
	public List<PointOfInterest> interpret(SearchPredicate criteria) {
		//differ to the list version so we don't have to write the same thing twice
		if(criteria==null) {
			return new ArrayList<PointOfInterest>();
		} else {
			List<SearchPredicate> l = new ArrayList<SearchPredicate>();
			l.add(criteria);
			return findByCriterias(l);
		}
	}

	@Override
	public List<PointOfInterest> interpret(Map<Integer, ? extends SearchPredicate> prioritizedCriteria) {
		if(prioritizedCriteria==null) return new ArrayList<PointOfInterest>();

		//make a list in order of priority
		int[] priorities = new int[prioritizedCriteria.size()];
		List<SearchPredicate> criteria = new ArrayList<SearchPredicate>(prioritizedCriteria.size());

		//wooooo insert sort (hey now... it actually makes sense here)
		for(Integer i : prioritizedCriteria.keySet()) {
			int j=criteria.size();
			priorities[j] = i;
			criteria.add(prioritizedCriteria.get(i));

			while(j>0 && priorities[j-1] > priorities[j]) {
				int temp_p = priorities[j-1];
				priorities[j-1] = priorities[j];
				priorities[j] = temp_p;

				SearchPredicate temp = criteria.get(j-1);
				criteria.set(j-1, criteria.get(j));
				criteria.set(j, temp);
			}
		}

		//the new list of things that pass the criteria
		List<PointOfInterest> filtered = new ArrayList<PointOfInterest>(points.size());

		//check every point of interest
		for(int i=0; i<points.size(); i++) {
			PointOfInterest p = points.get(i);
			if(p.satisfiesAll(criteria)) {
				filtered.add(p);
			}
		}   	

		return filtered;

	}

	@Override
	public List<PointOfInterest> findByCriterias(List<? extends SearchPredicate> criterias) {
		if(criterias==null) return new ArrayList<PointOfInterest>();

		//the new list of things that pass the criteria
		List<PointOfInterest> filtered = new ArrayList<PointOfInterest>(points.size());

		//check every point of interest
		for(int i=0; i<points.size(); i++) {
			PointOfInterest p = points.get(i);
			if(p.satisfiesOne(criterias)) {
				filtered.add(p);
			}
		}   	

		return filtered;
	}
}
