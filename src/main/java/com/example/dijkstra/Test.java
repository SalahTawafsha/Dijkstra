package com.example.dijkstra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("C:\\Users\\hp\\IdeaProjects\\Dijkstra\\src\\main\\resources\\com\\example\\dijkstra\\test.txt"));
        HashMap<Country, LinkedList<Node>> graph = new HashMap<>();

        double countries = scan.nextDouble();
        double edges = scan.nextDouble();
        scan.nextLine();
        while (countries-- != 0) {
            String[] tokens = scan.nextLine().split(" ");
            graph.put(new Country(tokens[0], Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])), new LinkedList<>());
        }

        while (edges-- != 0) {
            String[] tokens = scan.nextLine().split(" ");
            Country c1 = get(new Country(tokens[0], 0, 0), graph.keySet());
            Country c2 = get(new Country(tokens[1], 0, 0), graph.keySet());

            if (c1 == null || c2 == null) {
                System.out.println("you have error in edges");
                return;
            }
            double cost = findCost(c1, c2);
            graph.get(c1).add(new Node(c2, cost));
        }

        graph.forEach((country, nodes) -> System.out.println(country + " " + nodes));
    }

    private static double findCost(Country c1, Country c2) {
        return Math.sqrt((Math.pow(c2.getX() - c1.getX(), 2) + Math.pow(c2.getY() - c1.getY(), 2)));
    }

    static Country get(Country sample, Set<Country> all) {
        for (Country one : all) {
            if (one.equals(sample)) {
                return one;
            }
        }
        return null;
    }
}
