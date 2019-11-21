package com.examples.graph;

import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    private final GraphNode root;

    Graph(GraphNode root) {
        this.root = root;
    }

    public void print() {
        dfs(root);
    }

    public void resetVisitedFlag() {
        bfs(root);
    }

    public boolean pathBetween(final GraphNode start, final GraphNode end) {
        if (start == null || end == null) {
            return false;
        }

        if (start == end || start.name.equals(end.name)) {
            return true;
        }

        return bfs(start, end);
    }

    private boolean bfs(final GraphNode start, final GraphNode end) {
        final Queue<GraphNode> queue = new LinkedList<>();

        start.visited = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            final GraphNode node = queue.poll();
            node.visited = true;
            if (node == end || node.name.equals(end.name)) {
                return true;
            }
            for (final GraphNode n : node.children) {
                if (n != null && !n.visited) {
                    n.visited = true;
                    if (n == end || n.name.equals(end.name)) {
                        return true;
                    }
                    queue.add(n);
                }
            }
        }

        return false;
    }

    private void dfs(final GraphNode node) {
        if (node == null) {
            return;
        }

        visit(node);
        node.visited = true;
        for (final GraphNode g : node.children) {
            if (g != null && !g.visited) {
                dfs(g);
            }
        }
    }

    private void bfs(final GraphNode node) {
        final Queue<GraphNode> queue = new LinkedList<>();
        node.visited = false;
        queue.add(node);

        while (!queue.isEmpty()) {
            final GraphNode n = queue.poll();
            visit(n);
            for (final GraphNode gn : n.children) {
                if (gn != null && gn.visited) {
                    gn.visited = false;
                    queue.add(gn);
                }
            }
        }
    }

    private void visit(final GraphNode node) {
        System.out.print(node.name + " ");
    }

    public static void main(String[] args) {
        final GraphNode n7 = new GraphNode("g7", 0);
        final GraphNode n6 = new GraphNode("g6", 1);
        n6.add(n7);
        final GraphNode n5 = new GraphNode("g5", 1);
        n5.add(n6);
        final GraphNode n4 = new GraphNode("g4", 1);
        n4.add(n5);
        final GraphNode n3 = new GraphNode("g3", 2);
        n3.add(n4);
        n3.add(n5);
        final GraphNode n2 = new GraphNode("g2", 2);
        n2.add(n3);
        n2.add(n7);
        final GraphNode n1 = new GraphNode("g1", 2);
        n1.add(n2);
        n1.add(n7);

        final Graph graph = new Graph(n1);

        graph.print();
        System.out.println();

        graph.resetVisitedFlag();
        System.out.println();

        graph.print();
        System.out.println();

        graph.resetVisitedFlag();
        System.out.println();

        System.out.println(graph.pathBetween(n7, n1));

        graph.resetVisitedFlag();
        System.out.println();

        System.out.println(graph.pathBetween(n1, n7));
    }
}
