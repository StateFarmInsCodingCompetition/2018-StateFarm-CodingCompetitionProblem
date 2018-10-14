package com.codingcompetition.statefarm.model;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {
    private double lat;
    private double lon;
    private Map<Object,String> descriptors;
    public PointOfInterest(Node n) {
        NamedNodeMap map = n.getAttributes();
        Node lat = map.getNamedItem("lat");
        this.lat = Double.parseDouble(lat.getNodeValue());
        this.lon = Double.parseDouble(map.getNamedItem("lon").getNodeValue());

        this.descriptors = new HashMap<>();
        NodeList children = n.getChildNodes();
        int len = children.getLength();
        for(int i = 0; i < len; i++) {
            n = children.item(i);
            if(!n.getNodeName().equalsIgnoreCase("tag")) {
                System.out.println(n.getNodeName());
                continue;
            }
            System.out.println(n.getNodeName());
            NamedNodeMap m = n.getAttributes();
            descriptors.put(m.getNamedItem("k").getNodeValue(), m.getNamedItem("v").getNodeValue());
        }
    }

    public Map<Object,String> getDescriptors() {
        return this.descriptors;
    }

    public String getLatitude() {
        return Double.toString(lat);
    }

    public String getLongitude() {
        return Double.toString(lon);
    }
}
