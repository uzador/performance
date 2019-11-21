package com.cci.trees_and_graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class RouteBetweenNodes {
    static <T> void print(Node<T> root, Consumer<Node<T>> visit) {
        if (root == null) {
            return;
        }
        visit.accept(root);
        root.visited = true;
        for (final Node<T> neighbour : root.neighbours) {
            if (!neighbour.visited) {
                print(neighbour, visit);
            }
        }
    }

    static <T> void reset(Node<T> root) {
        if (root == null) {
            return;
        }
        root.visited = false;
        for (final Node<T> neighbour : root.neighbours) {
            if (neighbour.visited) {
                reset(neighbour);
            }
        }
    }

    static <T> boolean isRouteBetween(final Node<T> start, final Node<T> end) {
        if (start == null || end == null) {
            return false;
        }

        if (start == end) {
            return true;
        }

        Predicate<Node<T>> isEqualTo = (node) -> node == end;

        return bfs(start, isEqualTo);
    }

    private static <T> boolean bfs(Node<T> node, Predicate<Node<T>> isEqualTo) {
        final LinkedList<Node<T>> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            final Node<T> n = queue.removeLast();
            if (!n.visited) {
                n.visited = true;
                if (isEqualTo.test(n)) {
                    return true;
                }
            }
            for (final Node<T> neighbour : n.neighbours) {
                if (!neighbour.visited) {
                    queue.add(neighbour);
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Node<Integer> root = new Node<>(100);
        Node<Integer> one = new Node<>(1);
        Node<Integer> two = new Node<>(2);
        Node<Integer> three = new Node<>(3);

        Node<Integer> twofour = new Node<>(4);
        Node<Integer> twofive = new Node<>(5);

        root.addNeighbour(one);
        root.addNeighbour(two);
        root.addNeighbour(three);

        two.addNeighbour(twofour);
        two.addNeighbour(twofive);

        Node<Integer> alone = new Node<>(1_000);

        Consumer<Node<Integer>> visit = (node) -> System.out.print(node.value.toString() + " -> ");
        RouteBetweenNodes.print(root, visit);
        System.out.println();

        RouteBetweenNodes.reset(root);
        System.out.println(RouteBetweenNodes.isRouteBetween(root, three));

        RouteBetweenNodes.reset(root);
        System.out.println(RouteBetweenNodes.isRouteBetween(root, twofive));

        RouteBetweenNodes.reset(root);
        System.out.println(RouteBetweenNodes.isRouteBetween(root, alone));
    }

    private static class Node<T> {
        T value;
        List<Node<T>> neighbours;
        boolean visited;

        Node(T value) {
            this.value = value;
            this.neighbours = new ArrayList<>();
        }

        void addNeighbour(Node<T> neighbour) {
            neighbours.add(neighbour);
        }
    }
}
