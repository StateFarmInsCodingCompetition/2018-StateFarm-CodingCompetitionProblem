package com.codingcompetition.statefarm.application;

import java.util.List;
import java.util.Scanner;

import com.codingcompetition.statefarm.Category;
import com.codingcompetition.statefarm.SearchCriteria;
import com.codingcompetition.statefarm.StreetMapDataInterpreter;
import com.codingcompetition.statefarm.model.PointOfInterest;

public class DataSearch {

	/**
	 * A simple CLI that allows the user to read a file's data.
	 * @param args Does not accept command line arguments.
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		State state = State.AWAIT_FILE;
		StreetMapDataInterpreter interpret = null;
		List<PointOfInterest> parsed = null;
		boolean stop = false;
		while(!stop) {
			switch(state) {
			case AWAIT_FILE:
				System.out.println("Please enter the filename of an xml file.\n"
						+ "To ensure it can be found, please place it in the same location as this program or in the following locations:\n"
						+ "src/main/resources\n"
						+ "src/test/resources\n"
						+ "Also, be sure to add the / before the filename, like so: /small-metro.xml");
				System.out.println();
				String file = input.nextLine();
				boolean success = true;
				try {
					interpret = new StreetMapDataInterpreter(file);
					interpret.interpret();
				} catch (Exception e) {
					System.out.println("An error occurred when opening and parsing the file. Please try a different filename.");
					success = false;
				}
				if (!success) break;
				System.out.println();
				System.out.println("File successfully parsed.");
				state = State.AWAIT_SEARCH_TYPE;
				break;
			case AWAIT_SEARCH_TYPE:
				System.out.println("Enter 1 if you would like to read all of the street map data.\n"
						+ "Enter 2 if you would like to read all data that matches one criterion.\n");
				String response = input.nextLine();
				if (response.equals("1")){
					parsed = interpret.interpret();
					System.out.println("There are a total of " + parsed.size() + " points of interest.\n");
					state = State.AWAIT_NEXT_ACTION;
					break;
				} else if (response.equals("2")) {
					state = State.AWAIT_CRITERIA;
					break;
				}
				System.out.println("\nUnknown input. Please try again.\n");
				break;
			case AWAIT_CRITERIA:
				System.out.println("\nEnter the key and value of the criteria, separated by a single space.\n"
						+ "The key must match a valid category: "
						+ "LEISURE, NAME, AMENITY, CUISINE, SHOP,"
						+ " WHEELCHAIR, HIGHWAY, PLACE, POPULATION, POWER,"
						+ " BUILDING, BEAUTY, NAMESTARTSWITH, NAMEENDSWITH");
				String crit = input.nextLine();
				String[] parts = crit.split(" ");
				boolean successWithCriteria = false;
				if (parts.length == 2) {
					for (Category cat : Category.values()) {
						if (cat.toString().equals(parts[0].toUpperCase())) {
							parsed = interpret.interpret(new SearchCriteria(cat, parts[1]));
							System.out.println("There are a total of " + parsed.size() + " points of interest matching the given criteria.\n");
							state = State.AWAIT_NEXT_ACTION;
							successWithCriteria = true;
							break;
						}
					}
				}
				if (successWithCriteria) break;
				System.out.println("\nInvalid input. Please try again.\n");
				break;
			case AWAIT_NEXT_ACTION:
				System.out.println("Type 1 to choose a new search type.\n"
						+ "Type 2 to choose a new file.\n"
						+ "Type 3 to close this program.\n");
				String re = input.nextLine();
				if (re.equals("1")){
					state = State.AWAIT_SEARCH_TYPE;
					break;
				} else if (re.equals("2")) {
					state = State.AWAIT_FILE;
					break;
				} else if (re.equals("3")) {
					stop = true;
					break;
				}
				System.out.println("\nUnknown input. Please try again.\n");
				break;
			}
		}
		input.close();
	}
	
	public enum State {
		AWAIT_FILE, AWAIT_SEARCH_TYPE, AWAIT_CRITERIA, AWAIT_NEXT_ACTION;
	}
}
