package com.codingcompetition.statefarm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.codingcompetition.statefarm.SearchCriteria;
import com.codingcompetition.statefarm.gui.MainPanel.SearchCriteriaListener;

public class SearchPanel extends JPanel {
	private static final long serialVersionUID = -598557242155104578L;
	
	private SearchCriteriaListener listener;
	
	private JButton searchButton;
	
	public SearchPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Search"));
		
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listener != null) {
					listener.onSearchCriteriaChange(getSearchCriteria());
				}
			}
		});
		this.add(searchButton);
	}
	
	public void setListener(SearchCriteriaListener listener) {
		this.listener = listener;
	}
	
	private List<SearchCriteria> getSearchCriteria() {
		return null;
	}

}
