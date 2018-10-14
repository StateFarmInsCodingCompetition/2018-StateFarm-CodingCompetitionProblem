package com.codingcompetition.statefarm.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.codingcompetition.statefarm.SearchCriteria;
import com.codingcompetition.statefarm.SearchPredicate;

public class PointOfInterest {

	private String id;
	private String lat;
	private String lon;
	private Map<Object, String> tags;
	
	public PointOfInterest(Node node) {
		tags = new HashMap<Object, String>();
		
		NamedNodeMap attributes = node.getAttributes();
		try {
        	id = attributes.getNamedItem("id").getTextContent();
        	lat = attributes.getNamedItem("lat").getTextContent();
        	lon = attributes.getNamedItem("lon").getTextContent();
		} catch(NullPointerException e) {} 
		
		//get all of the "tag" tags in the node
		NodeList nl = node.getChildNodes();
		if(nl!=null) {
			for(int i=0; i<nl.getLength(); i++){
	        	Node n = nl.item(i);
	        	if(n.getNodeName().equals("tag")) {
	        		//the tag name is attribute k and the value is v
	        		NamedNodeMap nm = n.getAttributes();
	        		try {
	                	tags.put(nm.getNamedItem("k").getTextContent(), nm.getNamedItem("v").getTextContent());
	        		} catch(NullPointerException e) {}
	        	}
	        }		
		}		
		
	}
	
	public boolean satisfiesAll(List<? extends SearchPredicate> criterias) {
		for(SearchPredicate c : criterias) {
			boolean match = false;
			for(Entry<Object, String> e : tags.entrySet()) {
				if(c.fits(e.getKey().toString(), e.getValue())) {
					match = true;
					break;
				}
			}
			if(!match) return false;
		}
		return true;
	}
	
	public boolean satisfiesOne(List<? extends SearchPredicate> criterias) {
		for(SearchPredicate c : criterias) {
			for(Entry<Object, String> e : tags.entrySet()) {
				if(c.fits(e.getKey().toString(), e.getValue())) {
					return true;
				}
			}
		}
		return false;
	}
	
    public Map<Object,String> getDescriptors() {
    	return tags;
    }
    
    public String getId() {
    	return id;
    }

    public String getLatitude() {
        return lat;
    }

    public String getLongitude() {
        return lon;
    }
}
