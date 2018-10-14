package com.codingcompetition.statefarm.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import com.codingcompetition.statefarm.SearchCriteria;
import com.codingcompetition.statefarm.StreetMapDataInterpreter;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = -7253307128816380033L;
	
	interface SearchCriteriaListener {
		void onSearchCriteriaChange(List<SearchCriteria> criteria);
	}
	
	private SearchPanel searchPanel;
	private ListPanel listPanel;
	private MapView mapView;
	
	public MainPanel(StreetMapDataInterpreter interpreter) {
		this.setLayout(new BorderLayout());
		
		listPanel = new ListPanel("Results", interpreter);
		searchPanel = new SearchPanel();
		mapView = new MapView(interpreter);
		
		searchPanel.setListener(new SearchCriteriaListener() {
			@Override
			public void onSearchCriteriaChange(List<SearchCriteria> criteria) {
				listPanel.onSearchCriteriaChange(criteria);
			}
		});
		
		this.add(searchPanel, BorderLayout.NORTH);
		this.add(listPanel, BorderLayout.EAST);
		this.add(mapView, BorderLayout.CENTER);
	}

}
