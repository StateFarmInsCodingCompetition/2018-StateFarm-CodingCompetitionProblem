package com.codingcompetition.statefarm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.codingcompetition.statefarm.Category;
import com.codingcompetition.statefarm.SearchCriteria;
import com.codingcompetition.statefarm.gui.MainPanel.SearchCriteriaListener;

public class SearchPanel extends JPanel {
	private static final long serialVersionUID = -598557242155104578L;
	
	private SearchCriteriaListener listener;
	
	private JButton searchButton;
	private JTextField nameText;
	private JCheckBox wheelchairCheckbox, buildingCheckbox;
	private JComboBox<String> leisureCombo, amenityCombo, cuisineCombo;
	
	public SearchPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Search"));
		
		this.add(new JLabel("Name:"));
		nameText = new JTextField(30);
		this.add(nameText);
		
		wheelchairCheckbox = new JCheckBox("Weelchair");
		this.add(wheelchairCheckbox);
		buildingCheckbox = new JCheckBox("Building");
		this.add(buildingCheckbox);
		
		leisureCombo = new JComboBox<String>(new String[] {"Any Leisure", "Park", "Sports Centre", "Playground", "Fitness Centre", "Swimming Pool", "Picnic Table", "Pitch", "Water Park", "Golf Course", "Stadium", "Dog Park"});
		this.add(leisureCombo);
		
		amenityCombo = new JComboBox<String>(new String[] {"Any Amenity", "Fuel", "School", "Grave Yard", "Place Of Worship", "Post Office", "Public Building", "Arts Centre", "Fire Station", "Theatre", "Townhall", "Recycling", "Fast Food", "Restaurant", "Cafe", "Parking", "Bicycle Repair Station"});
		this.add(amenityCombo);
		
		cuisineCombo = new JComboBox<String>(new String[] {"Any Cuisine", "Chinese", "Mexican", "Sandwich", "Coffee Shop", "Burger", "Pizza", "American", "Donut", "Chicken", "Italian", "Barbecue"});
		this.add(cuisineCombo);
		
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
		List<SearchCriteria> sc = new LinkedList<>();
		if (nameText.getText() != null) {
			String text = nameText.getText().trim();
			if (!text.equals("")) {
				sc.add(new SearchCriteria(Category.NAMESTARTSWITH, text));
			}
		}
		if (wheelchairCheckbox.isSelected()) {
			sc.add(new SearchCriteria(Category.WHEELCHAIR, null));
		}
		if (buildingCheckbox.isSelected()) {
			sc.add(new SearchCriteria(Category.BUILDING, null));
		}
		if (leisureCombo.getSelectedIndex() != 0) {
			sc.add(new SearchCriteria(Category.LEISURE, ((String)leisureCombo.getSelectedItem()).toLowerCase().replaceAll(" ", "_")));
		}
		if (amenityCombo.getSelectedIndex() != 0) {
			sc.add(new SearchCriteria(Category.AMENITY, ((String)amenityCombo.getSelectedItem()).toLowerCase().replaceAll(" ", "_")));
		}
		if (cuisineCombo.getSelectedIndex() != 0) {
			sc.add(new SearchCriteria(Category.CUISINE, ((String)cuisineCombo.getSelectedItem()).toLowerCase().replaceAll(" ", "_")));
		}
		return sc;
	}

}
