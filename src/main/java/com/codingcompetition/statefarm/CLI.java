package com.codingcompetition.statefarm;

import com.codingcompetition.statefarm.model.PointOfInterest;

import java.util.*;

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

        StreetMapDataInterpreter streetMapDataInterpreter = new StreetMapDataInterpreter(((city == 1) ? "/small-metro.xml" : "/large-metro.xml"));
        System.out.println("Welcome to " + ((city == 1) ? "Bloomington" : "Chicago") + "!");
        printValidSearchCriteria();

        mainLoop : while (true) {
            int filters;

            while (true) {
                System.out.println();
                System.out.print("Enter the number of filters you would like to apply. (-1 to exit) : ");
                filters = scanner.nextInt();
                scanner.nextLine();

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
                    Category category;
                    String value;

                    while (true) {
                        System.out.print("Enter search criteria: ");
                        String searchCriteria = scanner.nextLine();

                        if(Category.isValidCategory(searchCriteria)) {
                            category = Category.valueOf(searchCriteria.toUpperCase());
                            break;
                        } else {
                            System.out.println(searchCriteria + " is not a valid search criteria.");
                            printValidSearchCriteria();
                        }
                    }

                    System.out.print("Enter value: ");
                    value = scanner.nextLine();
                    filterList.add(new SearchCriteria(category, value));
                }

                boolean union;
                while (true) {
                    System.out.print("Do you want to find results that match ANY of the filters (1) or results which match ALL of the filters(2). Enter 1 or 2: ");
                    int opt = scanner.nextInt();
                    scanner.nextLine();

                    if (opt == 1) {
                        union = true;
                        break;
                    }

                    if (opt == 2) {
                        union = false;
                        break;
                    }

                    System.out.println("Invalid input.");
                }

                if (union) {
                    printLocations(streetMapDataInterpreter.findByCriterias(filterList));
                } else {
                    HashMap<Integer, SearchCriteria> prioritizedCriteria = new HashMap<>();

                    for (int i = 0; i < filterList.size(); i++) {
                        prioritizedCriteria.put(i + 1, filterList.get(i));
                    }

                    printLocations(streetMapDataInterpreter.interpret(prioritizedCriteria));
                }
            }
        }

        System.out.println("Bye!");
    }

    private static void printValidSearchCriteria() {
        System.out.print("Valid search criteria: ");
        for (Category category: Category.values()) {
            System.out.print(category.toString().toLowerCase() + " ");
        }
        System.out.println();
    }

    private static void printLocations(List<PointOfInterest> locations) {
        System.out.println();
        System.out.println("----------------RESULTS----------------");
        for (int i = 0; i < locations.size(); i++) {
            PointOfInterest pointOfInterest = locations.get(i);

            System.out.println((i + 1) + ".\tLatitude: " + pointOfInterest.getLatitude() + "   Longitude: " + pointOfInterest.getLongitude());

            for (Map.Entry<Object, String> pair: pointOfInterest.getDescriptors().entrySet()){
                System.out.println("\t" + pair.getKey() + " - " + pair.getValue());
            }
        }
        System.out.println("---------------------------------------");
        System.out.println();

    }
}