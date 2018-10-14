package com.codingcompetition.statefarm.model;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {
    private double lat;
    private double lon;
    public PointOfInterest(Node n) {
        NamedNodeMap map = n.getAttributes();
        Node lat = map.getNamedItem("lat");
        this.lat = Double.parseDouble(lat.getNodeValue());
        this.lon = Double.parseDouble(map.getNamedItem("lon").getNodeValue());
    }

    public Map<Object,String> getDescriptors() {
    return new HashMap<>();
    }

    public String getLatitude() {
        return Double.toString(lat);
    }

    public String getLongitude() {
        return Double.toString(lon);
    }
}
