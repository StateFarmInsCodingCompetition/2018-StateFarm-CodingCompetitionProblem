package com.codingcompetition.statefarm.gui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.codingcompetition.statefarm.SearchCriteria;
import com.codingcompetition.statefarm.StreetMapDataInterpreter;
import com.codingcompetition.statefarm.gui.MainPanel.SearchCriteriaListener;
import com.codingcompetition.statefarm.model.PointOfInterest;

public class ListPanel extends JPanel implements SearchCriteriaListener {
	private static final long serialVersionUID = 4002155079815639095L;
	
	private JTable table;
	private OSMTableModel tableModel;
	
	private StreetMapDataInterpreter interpreter;
	
	public ListPanel(String title, StreetMapDataInterpreter interpreter) {
		this.interpreter = interpreter;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(title));
		this.add(Box.createHorizontalStrut(200));
		
		tableModel = new OSMTableModel();
		tableModel.setPOI(new LinkedList<>());
		table = new JTable(tableModel);
	}

	@Override
	public void onSearchCriteriaChange(List<SearchCriteria> criteria) {
		Map<Integer, SearchCriteria> prioMap = new HashMap<Integer, SearchCriteria>();
		for (int i = 0; i < criteria.size(); i++) {
			prioMap.put(i, criteria.get(i));
		}
		tableModel.setPOI(interpreter.interpret(prioMap));
	}
	
	private class OSMTableModel extends DefaultTableModel {
		private List<PointOfInterest> poi;
		
		public void setPOI(List<PointOfInterest> poi) {
			this.poi = poi;
		}

		@Override
		public int getRowCount() {
			return poi == null ? 0 : poi.size();
		}

		@Override
		public int getColumnCount() {
			return 1;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return "Name";
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return poi.get(rowIndex).getDescriptors().get("name");
		}
		
	}

}
