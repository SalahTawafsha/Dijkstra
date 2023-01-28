package com.example.dijkstra;

public class Country {
    private final String name;
    private final double x;
    private final double y;

    public Country(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Country(String name) {
        this.name = name;
        x = 0;
        y = 0;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Country)
            return name.equals(((Country) (obj)).name);
        return super.equals(obj);
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
