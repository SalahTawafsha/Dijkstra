package com.example.dijkstra;

public class Country {
    private final String name;
    private final double x;
    private final double y;
    private double distance;
    private boolean known;
    private Country path;

    public Country(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        distance = Double.MAX_VALUE;
        known = false;
    }

    public Country(String name) {
        this.name = name;
        x = 0;
        y = 0;
        distance = Double.MAX_VALUE;
        known = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Country)
            return name.equals(((Country) (obj)).name);
        return super.equals(obj);
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public double getX() {
        return x;
    }

    public boolean getKnown() {
        return known;
    }

    public double getDistance() {
        return distance;
    }

    public void setPath(Country path) {
        this.path = path;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return name;
    }
}
