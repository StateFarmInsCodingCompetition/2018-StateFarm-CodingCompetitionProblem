package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;
import org.xml.sax.SAXException;

import javax.security.sasl.SaslServer;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreetMapDataInterpreter implements Interpreter {

    private String filename;

    public StreetMapDataInterpreter(String s) {
        filename = s;
    }

    @Override
    public List<PointOfInterest> interpret() {
        PointOfInterestParser p = new PointOfInterestParser();
        List<PointOfInterest> Result;
        try {
            Result = p.parse(filename);
        } catch (IOException e) {
            e.printStackTrace();
            Result = new ArrayList<PointOfInterest>();
        } catch (SAXException e) {
            e.printStackTrace();
            Result = new ArrayList<PointOfInterest>();
        }
        return Result;
    }

    @Override
    public List<PointOfInterest> interpret(SearchCriteria criteria) {
        List<PointOfInterest> Result = new ArrayList<PointOfInterest>();

        if(criteria == null)
            return Result;

        List<PointOfInterest> tempList = this.interpret();

        for (PointOfInterest POI : tempList)    {
            if (Match(POI, criteria))
                Result.add(POI);
        }

        return Result;
    }

    @Override
    public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
        List<PointOfInterest> Result = new ArrayList<PointOfInterest>();

        List<PointOfInterest> tempList = this.interpret();

            //Result.addAll(this.interpret(m.getValue()));

            for (PointOfInterest p : tempList)
            {
                boolean hasall = true;
                for (Map.Entry<Integer, SearchCriteria> m : prioritizedCriteria.entrySet())
                {
                    if (!Match(p, m.getValue()))
                        hasall = false;
                }
                if (hasall)
                    Result.add(p);
            }


        return Result.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {

        List<PointOfInterest> tempList = this.interpret();
        List<PointOfInterest> Ret = new ArrayList<PointOfInterest>();

        for (PointOfInterest p : tempList)
        {
            if(criterias.stream().anyMatch(Criteria -> Match(p, Criteria)))
                Ret.add(p);
        }

        return Ret;
    }

    private boolean Match(PointOfInterest POI, SearchCriteria criteria) {
        if (criteria.getCat() == Category.NAMEENDSWITH) {
            if (POI.getDescriptors().containsKey("name") && POI.getDescriptors().get("name").endsWith(criteria.getValue()))
                return true;
            return false;
        }
        else if (criteria.getCat() == Category.NAMESTARTSWITH) {
            if (POI.getDescriptors().containsKey("name") && POI.getDescriptors().get("name").startsWith(criteria.getValue()))
                return true;
            return false;
        }

        String key = criteria.getCat().name().toLowerCase();
        return POI.getDescriptors().containsKey(key) && POI.getDescriptors().get(key).equals(criteria.getValue());
    }
}
