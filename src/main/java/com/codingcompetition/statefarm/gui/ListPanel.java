package com.codingcompetition.statefarm.gui;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.codingcompetition.statefarm.SearchCriteria;
import com.codingcompetition.statefarm.gui.MainPanel.SearchCriteriaListener;

public class ListPanel extends JPanel implements SearchCriteriaListener {
	private static final long serialVersionUID = 4002155079815639095L;
	
	
	public ListPanel(String title) {
		this.setBorder(BorderFactory.createTitledBorder(title));
	}

	@Override
	public void onSearchCriteriaChange(List<SearchCriteria> criteria) {
		// TODO Auto-generated method stub
		
	}

}
