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
    public String toString() {
        return name;
    }
}
