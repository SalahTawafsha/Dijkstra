package com.example.dijkstra;

public record Node(Country country, Double cost) {

    @Override
    public String toString() {
        return country +
                "\t" + cost;
    }
}
