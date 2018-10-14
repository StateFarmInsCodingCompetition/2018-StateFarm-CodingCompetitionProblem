package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;
import com.codingcompetition.statefarm.utility.PointOfInterestParser;

import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StreetMapDataInterpreter implements Interpreter {

	private List<PointOfInterest> pois;

	/**
	 * Initializers a new StreeMapDataInterpreter object using the given XML file
	 * path.
	 * 
	 * @param s The path of the xml file.
	 * @throws Exception 
	 */
	public StreetMapDataInterpreter(String s) throws Exception {
		PointOfInterestParser poip = new PointOfInterestParser();
		try {
			pois = poip.parse(s);
		} catch (IOException | SAXException e) {
			System.err.println("An error occurred which likely prevented the map data from being completely parsed.");
			throw e;
		}
	}

	/**
	 * Returns the entire PointOfInterest list that the interpreter created.
	 * 
	 * @return The full list of PointOfInterest objects found in the original xml
	 *         file.
	 */
	@Override
	public List<PointOfInterest> interpret() {
		return pois;
	}

	/**
	 * Returns all PointOfInterest objects that contain the key-value pair within
	 * the given SearchCriteria.
	 * 
	 * @param criteria The criterion that all PointOfInterest objects in the
	 *                 returned list must meet.
	 * @return The list of all PointOfInterest objects that meet the given
	 *         criterion.
	 */
	@Override
	public List<PointOfInterest> interpret(SearchCriteria criteria) {
		return sift(pois, Arrays.asList(criteria));
	}

	/**
	 * Returns all PointOfInterest objects that meet all of the given criteria,
	 * prioritized in the mapped order.
	 * 
	 * @param prioritizedCriteria The criteria that all PointOfInterest objects in
	 *                            the returned list must meet. Criteria with a lower
	 *                            Integer key are checked first.
	 * @return The list of all PointOfInterest objects that meet all the given
	 *         criteria, in the given order.
	 */
	@Override
	public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
		List<Integer> sortedKeys = new ArrayList<>(prioritizedCriteria.keySet());
		Collections.sort(sortedKeys);
		List<PointOfInterest> result = new ArrayList<>(pois);
		for (Integer i : sortedKeys) {
			result = sift(result, Arrays.asList(prioritizedCriteria.get(i)));
		}
		return result;
	}

	/**
	 * Returns all PointOfInterest objects that meet at least one of the given
	 * criteria.
	 * 
	 * @param criterias A list containing different criteria, one of which each
	 *                  returned PointOfInterest must meet.
	 * @return The list of all PointOfInterest objects that meet at least one of the
	 *         given criteria.
	 */
	@Override
	public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
		return sift(pois, criterias);
	}

	/**
	 * Using a list of PointOfInterest objects and a list of criteria, this method
	 * returns only the objects in the given list that pass at least one of the
	 * given criteria.
	 * 
	 * @param input     A list of PointOfInterest objects to sift through.
	 * @param criterias A list of criteria, one of which each returned
	 *                  PointOfInterest object must meet.
	 * @return The list of all PointOfInterest objects provided by the input that
	 *         meet at least one of the given criteria.
	 */
	private List<PointOfInterest> sift(List<PointOfInterest> input, List<SearchCriteria> criterias) {
		ArrayList<PointOfInterest> matches = new ArrayList<>();
		for (PointOfInterest point : input) {
			boolean meetsAllCriteria = false;
			for (SearchCriteria crit : criterias) {
				Category cat = crit.getCategory();
				String lowercaseCategory = cat.toString().toLowerCase();
				Map<Object, String> descs = point.getDescriptors();
				Set<Object> keySet = descs.keySet();
				if (cat.equals(Category.NAMEENDSWITH) && keySet.contains("name")) {
					if (descs.get("name").endsWith(crit.getValue())) {
						meetsAllCriteria = true;
						break;
					}
				} else if (cat.equals(Category.NAMESTARTSWITH) && keySet.contains("name")) {
					if (descs.get("name").startsWith(crit.getValue())) {
						meetsAllCriteria = true;
						break;
					}
				} else if (keySet.contains(lowercaseCategory)) {
					if (descs.get(lowercaseCategory).equals(crit.getValue())) {
						meetsAllCriteria = true;
						break;
					}
				}
			}
			if (meetsAllCriteria) {
				matches.add(point);
			}
		}
		return matches;
	}
}
