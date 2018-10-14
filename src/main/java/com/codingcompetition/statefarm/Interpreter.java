package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;

import java.util.List;
import java.util.Map;

import java.io.IOException;


import javax.security.sasl.SaslException;


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public interface Interpreter {

    public List<PointOfInterest> interpret() throws IOException, SAXException, ParserConfigurationException;

    public List<PointOfInterest> interpret(SearchCriteria criteria);

    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria);

    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias);
}
