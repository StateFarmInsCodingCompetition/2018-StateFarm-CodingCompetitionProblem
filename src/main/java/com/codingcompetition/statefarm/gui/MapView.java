package com.codingcompetition.statefarm.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.codingcompetition.statefarm.SearchCriteria;
import com.codingcompetition.statefarm.StreetMapDataInterpreter;
import com.codingcompetition.statefarm.gui.MainPanel.SearchCriteriaListener;
import com.codingcompetition.statefarm.model.PointOfInterest;

public class MapView extends JPanel implements SearchCriteriaListener, MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private StreetMapDataInterpreter interpreter;
	
	private BufferedImage background;
	
	private BufferedImage marker;

	private final Object filteredPointsLock;
	
	private List<PointOfInterest> filteredPoints;
	
	private double minLat, minLon, maxLat, maxLon;
	
	private Point mse;
	
	private volatile PointOfInterest selected;
	
	public MapView(StreetMapDataInterpreter interpreter) {
		this.interpreter = interpreter;
		this.filteredPointsLock = new Object();
		this.filteredPoints = this.interpreter.interpret();
		this.mse = new Point(0, 0);
		
		try {
			marker = ImageIO.read(loadRes("/marker.png"));
			
			if (interpreter.getParser().getFileName().contains("small-metro")) {
				// Bloomington
				background = ImageIO.read(loadRes("/map_bloomington.png"));
			} else {
				// Chicago
				background = ImageIO.read(loadRes("/map_chicago.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		minLat = interpreter.getParser().getMinLat();
		minLon = interpreter.getParser().getMinLong();
		maxLat = interpreter.getParser().getMaxLat();
		maxLon = interpreter.getParser().getMaxLong();
		
		this.addMouseMotionListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		
		int markerWidth, markerHeight;
		
		// Draw each point of interest
		synchronized (filteredPointsLock) {
			if (filteredPoints.size() < 50) {
				markerWidth = 15;
			} else {
				markerWidth = 5;
			}
			markerHeight = (int) (marker.getHeight() * 1.0 / marker.getWidth() * markerWidth);
			
			PointOfInterest selected = null;
				
			for (PointOfInterest point : filteredPoints) {
				double latPercent = (Double.parseDouble(point.getLatitude()) - minLat) / (maxLat - minLat);
				double lonPercent = (Double.parseDouble(point.getLongitude()) - minLon) / (maxLon - minLon);
				int xLoc = (int) (this.getWidth() * latPercent);
				int yLoc = (int) (this.getHeight() * (1 - lonPercent));
				
				if (selected == null && mse.x >= xLoc - markerWidth / 2 && mse.y >= yLoc - markerHeight && mse.x < xLoc + markerWidth / 2 && mse.y < yLoc) {
					selected = point;
					
				} else {
					g.drawImage(marker, xLoc - markerWidth / 2, yLoc - markerHeight, markerWidth, markerHeight, null);
				}
			}
			
			// Draw selected one last
			if (selected != null) {
				double latPercent = (Double.parseDouble(selected.getLatitude()) - minLat) / (maxLat - minLat);
				double lonPercent = (Double.parseDouble(selected.getLongitude()) - minLon) / (maxLon - minLon);
				int xLoc = (int) (this.getWidth() * latPercent);
				int yLoc = (int) (this.getHeight() * (1 - lonPercent));
				int mw = 40;
				int mh = (int) (marker.getHeight() * 1.0 / marker.getWidth() * mw);
				g.drawImage(marker, xLoc - mw / 2, yLoc - mh, mw, mh, null);
			}
			this.selected = selected;
		}
	}

	@Override
	public void onSearchCriteriaChange(List<SearchCriteria> criteria) {
		Map<Integer, SearchCriteria> priorityMap = new HashMap<>();
		for (int i = 0; i < criteria.size(); i++) {
			priorityMap.put(i, criteria.get(i));
		}
		synchronized (filteredPointsLock) {
			this.filteredPoints = this.interpreter.interpret(priorityMap);	
		}
		this.repaint();
	}
	
	private File loadRes(String name) {
		return new File(MapView.class.getResource(name).getFile());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mse = new Point(e.getX(), e.getY());
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("clicked " + selected);
		if (selected != null) {
			String str = "";
			for (Object key : selected.getDescriptors().keySet()) {
				str += " " + (String)key + ": " + selected.getDescriptors().get(key) + "\n";
			}
			str = str.trim();
			JOptionPane.showMessageDialog(null, str, "Detail Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
