package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {

    String fileStr;

    public StreetMapDataInterpreter(String s) {
        fileStr = s;
    }

    @Override
    public List<PointOfInterest> interpret() throws IOException, SAXException, ParserConfigurationException  {
        PointOfInterestParser parser = new PointOfInterestParser();
        
        List<PointOfInterest> interpretedData = parser.parse(fileStr);

        System.out.println(interpretedData.size());
        return interpretedData;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        if (criteria == null) {
            return new ArrayList<PointOfInterest>();
        }
        return null;
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        return null;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        return null;
    }
}
