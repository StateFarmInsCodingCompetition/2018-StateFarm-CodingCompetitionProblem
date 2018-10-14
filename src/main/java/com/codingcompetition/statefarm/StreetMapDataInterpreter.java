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

	private List<PointOfInterest> pois;

	public StreetMapDataInterpreter(String s) throws IOException, SAXException, ParserConfigurationException {
		PointOfInterestParser poiParser = new PointOfInterestParser();
		pois = poiParser.parse(s);
	}

	@Override
	public List<PointOfInterest> interpret() {
		return pois;
	}

	@Override
	public List<PointOfInterest> interpret(SearchCriteria criteria) {
		String nameCriteriaKey = Category.NAME.name().toLowerCase();
		List<PointOfInterest> list = new ArrayList<>();

		if (criteria == null)
			return list;

		for (PointOfInterest poi : pois) {
			Map<Object, String> desc = poi.getDescriptors();
			String key = criteria.cat.name().toLowerCase();
			String val = desc.get(key);
			if (val != null && val.equals(criteria.value)) {
				list.add(poi);
				continue;
			}

			String name = desc.get(nameCriteriaKey);
			if (name != null) {
				String value = criteria.value.toLowerCase();
				name = name.toLowerCase();
				if ((criteria.cat == Category.NAMESTARTSWITH && name.startsWith(value))
						|| (criteria.cat == Category.NAMEENDSWITH && name.endsWith(value)))
					list.add(poi);
			}
		}
		return list;
	}

	@Override
	public List<PointOfInterest> interpret(Map<Integer, SearchCriteria> prioritizedCriteria) {
		String nameCriteriaKey = Category.NAME.name().toLowerCase();
		List<PointOfInterest> list = new ArrayList<>();

		for (PointOfInterest poi : pois) {
			int i = 1;
			SearchCriteria criteria;
			boolean isValid = true;
			while ((criteria = prioritizedCriteria.get(i)) != null) {
				Map<Object, String> desc = poi.getDescriptors();
				
				if(criteria.cat != Category.NAMESTARTSWITH && criteria.cat != Category.NAMEENDSWITH) {
					String key = criteria.cat.name().toLowerCase();
					String val = desc.get(key);
					if (val == null || !val.equals(criteria.value)) {
						isValid = false;
						break;
					}
				}
				
				String name = desc.get(nameCriteriaKey);
				if (name != null) {
					String value = criteria.value.toLowerCase();
					name = name.toLowerCase();
					if ((criteria.cat == Category.NAMESTARTSWITH && !name.startsWith(value))
							|| (criteria.cat == Category.NAMEENDSWITH && !name.endsWith(value))) {
						isValid = false;
						break;
					}
				}
				i++;
			}
			if (isValid)
				list.add(poi);
		}

		return list;
	}

	@Override
	public List<PointOfInterest> findByCriterias(List<SearchCriteria> criterias) {
		String nameCriteriaKey = Category.NAME.name().toLowerCase();
		List<PointOfInterest> list = new ArrayList<>();

		for (PointOfInterest poi : pois) {
			for (SearchCriteria criteria : criterias) {
				Map<Object, String> desc = poi.getDescriptors();
				String key = criteria.cat.name().toLowerCase();
				String val = desc.get(key);
				if (val != null && val.equals(criteria.value)) {
					list.add(poi);
					break;
				}

				String name = desc.get(nameCriteriaKey);
				if (name != null) {
					String value = criteria.value.toLowerCase();
					name = name.toLowerCase();
					if ((criteria.cat == Category.NAMESTARTSWITH && name.startsWith(value))
							|| (criteria.cat == Category.NAMEENDSWITH && name.endsWith(value))) {
						list.add(poi);
						break;
					}
				}
			}
		}

		return list;
	}
}
