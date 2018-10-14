package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class StreetMapDataInterpreter implements Interpreter {
  private List<PointOfInterest> pointOfInterestList;

  public StreetMapDataInterpreter(String fileName) {
    this.pointOfInterestList = new ArrayList<>();

    PointOfInterestParser pointOfInterestParser = new PointOfInterestParser();
    try {
      pointOfInterestList.addAll(pointOfInterestParser.parse(fileName));
    } catch (Exception ex) {
      System.out.println("An error occurred while parsing the given file name.");
      ex.printStackTrace();
    }
  }

  /**
   * Returns all parsed points of interests
   * @return List of parsed points of interests
   */
  @Override
  public List<PointOfInterest> interpret() {
    return pointOfInterestList;
  }

  /**
   * Returns a list of point of interests that match a specified search criteria
   * @param criteria search criteria to match
   * @return list of point of interests that match a specified search criteria
   */
  @Override
  public List<PointOfInterest> interpret(SearchCriteria criteria) {
    if (criteria == null) {
      return new ArrayList<>();
    }

    List<SearchCriteria> criteriaList = new ArrayList<>();
    criteriaList.add(criteria);
    return findByCriterias(criteriaList);
  }

  /**
   * Returns a list of point of interests that match all specified search criterias
   * @param prioritizedCriteria search criterias to match
   * @return a list of point of interests that match all specified search criterias
   */
  @Override
  public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
    return findByCriterias(prioritizedCriteria.values(), true);
  }

  /**
   * Returns a list of point of interests that match any of the specified search criterias
   * @param criterias search criterias to match
   * @return a list of point of interests that match any of the specified search criterias
   */
  @Override
  public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
    return findByCriterias(criterias, false);
  }

  /**
   * Helper function that returns a list of point of interests that match given search criterias
   * @param criterias list of criterias to match
   * @param shouldMatchAll Whether all criterias have to be satisfied
   * @return returns a list of point of interests that match given search criterias
   */
  private List<PointOfInterest> findByCriterias(Collection<SearchCriteria> criterias, boolean shouldMatchAll) {
    List<PointOfInterest> matchedPoiList = new ArrayList<>();
    if (criterias == null || criterias.size() == 0) {
      return matchedPoiList;
    }

    for (PointOfInterest curPoi : pointOfInterestList) {
      boolean allCriteriaSatisifed = true;
      for (SearchCriteria criteria : criterias) {
        boolean isCriteriaSatisfied = curPoi.satisfiesCriteria(criteria);
        if (!isCriteriaSatisfied && shouldMatchAll) {
          allCriteriaSatisifed = false;
          break;
        } else if (isCriteriaSatisfied && !shouldMatchAll) {
          matchedPoiList.add(curPoi);
          break;
        }
      }

      if (shouldMatchAll && allCriteriaSatisifed) {
        matchedPoiList.add(curPoi);
      }
    }
    return matchedPoiList;
  }
}
