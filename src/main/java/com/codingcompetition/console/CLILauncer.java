package com.codingcompetition.console;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.codingcompetition.statefarm.*;
import com.codingcompetition.statefarm.model.PointOfInterest;

public class CLILauncer {
	
	
	public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException {
		String fileName = "";
		Scanner scan = new Scanner(System.in);
		System.out.println("Would You Like to Search the Large or Small Metro Data? ");
		System.out.print("Input (Large: L/l, Small: S/s): ");
		fileName = scan.nextLine();
		while (fileName.length() == 0
			|| (fileName.toLowerCase().charAt(0) != 'l' && fileName.toLowerCase().charAt(0) != 's')
		) {
			System.out.println("Error: Invalid input.  Press any key to continue.");
			scan.nextLine();
			clearScreen();
			System.out.println("Would You Like to Search the Large or Small Metro Data? ");
			System.out.print("Input (Large: L/l, Small: S/s): ");
			fileName = scan.nextLine();
		}
		
		fileName = (fileName.charAt(0) == 'l' ? "/large-metro.xml" : "/small-metro.xml");
		
		boolean noErrors = true;
		StreetMapDataInterpreter interpreter = new StreetMapDataInterpreter(fileName);
		
		/*try {
			//interpreter = ;
		} catch (IOException | SAXException | ParserConfigurationException e) {
			noErrors = false;
			System.out.println("Error: Invalid File Path or File Could not be Read");
		}*/
		
		if(noErrors) {
			boolean running = true;
			int numResults = 10;
			while(running) {
				clearScreen();
				System.out.println("Search for the following types of POIs from the chosen data: ");
				System.out.println("LEISURE, NAME, AMENITY, CUISINE, SHOP, WHEELCHAIR, HIGHWAY, PLACE, POPULATION, POWER, BUILDING, BEAUTY");
				System.out.print("Enter one of the above types (case insensitive): ");
				String input = scan.nextLine();
				try {
					Category c = Category.valueOf(input.toUpperCase());
				} catch(Exception e) {
					System.out.println("Error: Invalid input.  Press any key to continue.");
					scan.nextLine();
					continue;
				}
				clearScreen();
				System.out.print("Enter a search criteria with this POI: ");
				String criteria = scan.nextLine();
				SearchCriteria searchCriteria = new SearchCriteria(Category.valueOf(input.toUpperCase()), criteria);
				List<PointOfInterest> results = interpreter.interpret(searchCriteria);
				int i = 0;
				for(PointOfInterest poi: results) {
					System.out.println(
							input + " " +
							poi.getDescriptors().get(Category.valueOf(input.toUpperCase()))
					+ " at " + poi.getLatitude() + ", " + poi.getLongitude()
					);
				}
				scan.nextLine();
				continue;
				
			}
		}
		
	}
	
	public static void clearScreen() {
		for(int i = 0;i < 100; i++) {
			System.out.println("\n");
		}
	}

}
