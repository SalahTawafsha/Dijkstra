package com.example.dijkstra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("test.txt"));
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
            Country c1 = get(new Country(tokens[0]), graph.keySet());
            Country c2 = get(new Country(tokens[1]), graph.keySet());

            if (c1 == null || c2 == null) {
                System.out.println("you have error in edges");
                return;
            }
            double cost = findCost(c1, c2);
            graph.get(c1).add(new Node(c2, cost));
        }

        graph.forEach((country, nodes) -> System.out.println(country + " " + nodes));

        dijkstra(graph, get(new Country("Country1"), graph.keySet()));
    }

    public static void dijkstra(HashMap<Country, LinkedList<Node>> graph, Country from) {

        Table[] table = new Table[graph.size()];

        fillTable(table, graph.keySet(), from);
        Country v;

        while (true) {
            int index = smallest(table);
            v = (index != -1) ? table[index].getHeader() : null;

            if (v == null)
                break;
            System.out.println(table[index].getHeader());
            table[index].setKnown(true);

            LinkedList<Node> list = graph.get(v);
            for (Node node : list) {
                int j = find(node, table);
                if (!table[j].isKnown()) {
                    if (table[index].getDistance() + node.cost() < table[j].getDistance()) {
                        table[j].setDistance(table[index].getDistance() + node.cost());
                        table[j].setPrev(v);
                        System.out.println("path: " + v);
                    }
                }
            }

        }

        for (Table value : table) {
            System.out.println(value.getHeader() + "\t" + value.isKnown() + "\t" + value.distance + "\t" + value.prev);
        }


    }

    private static int find(Node node, Table[] table) {
        for (int i = 0; i < table.length; i++) {
            if (table[i].getHeader().equals(node.country()))
                return i;
        }
        return 0;
    }

    private static void fillTable(Table[] table, Set<Country> all, Country from) {
        int i = 0;
        for (Country one : all) {
            table[i++] = new Table(one);
            if (from.equals(one))
                table[i - 1].setDistance(0);
        }
    }


    private static int smallest(Table[] graph) {
        double d = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < graph.length; i++) {
            System.out.println(graph[i]);
            if (!graph[i].isKnown() && graph[i].getDistance() < d) {
                d = graph[i].getDistance();
                index = i;
            }
        }
        return index;
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
