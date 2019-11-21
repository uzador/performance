package com.examples.graph;

public class GraphNode {
    final String name;
    final GraphNode[] children;

    int count = 0;
    boolean visited = false;

    public GraphNode(String name, int capacity) {
        this.name = name;
        children = new GraphNode[capacity];
    }

    public void add(GraphNode node) {
        children[count++] = node;
    }
}
