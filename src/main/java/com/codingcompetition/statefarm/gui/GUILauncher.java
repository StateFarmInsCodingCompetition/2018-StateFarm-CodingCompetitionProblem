package com.codingcompetition.statefarm.gui;

import java.io.File;

import javax.swing.JOptionPane;

import com.codingcompetition.statefarm.StreetMapDataInterpreter;

public class GUILauncher {

	private static final String[] files = new String[] {"small-metro.xml", "large-metro.xml"};
	
	public static void main(String[] args) {
		int option = JOptionPane.showOptionDialog(null, "Please select which XML file to load:", "Select XML File", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, files, "Select an Option");
		String filename = File.separator + files[option];
		StreetMapDataInterpreter interpreter = new StreetMapDataInterpreter(filename);
		MainWindow window = new MainWindow(interpreter);
		window.setVisible(true);
	}
	
}
