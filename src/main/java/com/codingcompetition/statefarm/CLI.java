package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {

    public static void main(String[] args) {
        System.out.println("Welcome to location search engine! All the data has been provided by www.openstreetmap.org - © OpenStreetMap contributors.");

        Scanner scanner = new Scanner(System.in);
        int city;

        while (true) {
            System.out.print("Hello! Do you want to search in Bloomington(1) or Chicago(2): ");
            city = scanner.nextInt();

            if (city == 1 || city == 2)
                break;

            System.out.println("Wrong input");
        }

        StreetMapDataInterpreter streetMapDataInterpreter = new StreetMapDataInterpreter(((city == 1) ? "small-metro.xml" : "large-metro.xml"));
        System.out.println("Welcome to " + ((city == 1) ? "Bloomington" : "Chicago") + "!");
        System.out.println("Valid search criteria: ");
        for (Category category: Category.values()) {
            System.out.println(category.toString());
        }

        mainLoop : while (true) {
            int filters;

            while (true) {
                System.out.print("Enter the number of filters you would like to apply. (-1 to exit) : ");
                filters = scanner.nextInt();

                if (filters == -1)
                    break mainLoop;

                if (filters >= 0)
                    break;

                System.out.println("Wrong input");
            }

            if (filters == 0) {
                printLocations(streetMapDataInterpreter.interpret());
            } else {
                List<SearchCriteria> filterList = new ArrayList<>();

                for (int i = 0; i < filters; i++) {
                    while (true) {
                        System.out.print("Enter search criteria: ");
                        String searchCriteria = scanner.next();
                        
                    }
                }
            }
        }
    }

    private static void printLocations(List<PointOfInterest> locations) {

    }
}
