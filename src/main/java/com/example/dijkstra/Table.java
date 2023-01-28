package com.example.dijkstra;

public class Table {
    Country header;
    boolean known = false;
    double distance = 1000000;
    Country prev;

    public Table(Country header) {
        this.header = header;
    }

    public Country getHeader() {
        return header;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPrev(Country prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "Table{" +
                "header=" + header +
                ", known=" + known +
                ", distance=" + distance +
                ", prev=" + prev +
                '}';
    }
}
