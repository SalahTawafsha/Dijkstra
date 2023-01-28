package com.example.dijkstra;

public class Table {
    private final Country header;
    private boolean known = false;
    private double distance = 1000000;
    private int prev;

    public Table(Country header) {
        this.header = header;
    }

    public Country getHeader() {
        return header;
    }

    public int getPrev() {
        return prev;
    }

    public boolean notKnown() {
        return !known;
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

    public void setPrev(int prev) {
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
