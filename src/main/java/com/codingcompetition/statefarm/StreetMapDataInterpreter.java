package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {

    private List<PointOfInterest> dataList;


    public StreetMapDataInterpreter(String s) throws IOException, SAXException {
        this.dataList = new PointOfInterestParser().parse(s);
    }

    @Override
    public List<PointOfInterest> interpret() {
        return dataList;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        List<PointOfInterest> newList = new ArrayList<>();
        for (PointOfInterest poi : dataList) {
            if (poi.getDescriptors().values().contains(criteria.getValue())) {
                newList.add(poi);
            }
        }
        return newList;
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {

        return dataList;
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
        List<PointOfInterest> newList = new ArrayList<>();
        //some stuff here
        return newList;
    }
}
