package com.codingcompetition.statefarm.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = -5880550599073357438L;
	
	private static final Dimension WINDOW_BOUNDS = new Dimension(1000, 500);

	public MainWindow() {
		super("OpenStreetMap Data Viewer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle bounds = new Rectangle(screen.width / 2 - WINDOW_BOUNDS.width / 2, screen.height / 2 - WINDOW_BOUNDS.height / 2, WINDOW_BOUNDS.width, WINDOW_BOUNDS.height);
		this.setBounds(bounds);
		this.setContentPane(new MainPanel());
	}
	
	
}
