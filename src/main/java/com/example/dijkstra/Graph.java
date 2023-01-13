package com.example.dijkstra;

import java.util.LinkedList;

public class Graph<T extends Comparable<T>> {
    private LinkedList<T>[] arr;
    private int size;

    public Graph(T[] arr) {
        this.size = arr.length;
        this.arr = new LinkedList[size];

        for (int i = 0; i < arr.length; i++) {
            this.arr[i] = new LinkedList<>();
            this.arr[i].add(arr[i]);
        }
    }

    public Graph(int size) {
        this.size = size;
        arr = new LinkedList[size];
    }

    public void setArr(LinkedList[] arr) {
        this.arr = arr;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void insert(T insertOn,T data) {
        
    }
}
