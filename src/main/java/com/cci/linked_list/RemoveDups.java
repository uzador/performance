package com.cci.linked_list;

import java.util.HashSet;
import java.util.Set;

public class RemoveDups<T> {
    private static class Node<T extends Comparable<T>> {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    private static class LL<T extends Comparable<T>> {
        Node<T> head;

        public void addToTail(T value) {
            Node<T> n = new Node<>(value);
            if (isEmpty()) {
                head = n;
            } else {
                getLastElement().next = n;
            }

        }

        private boolean isEmpty() {
            return head == null;
        }

        private Node<T> getLastElement() {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }

            return current;
        }

        public void print() {
            Node<T> current = head;
            while (current != null) {
                System.out.print(String.format("%s -> ", current.value));
                current = current.next;
            }
            System.out.println();
        }


        public void removeDups() {
            Node<T> current = head;
            while (current != null) {

                Node<T> secondCurrent = current;
                while (secondCurrent.next != null) {
                    if (current.value.compareTo(secondCurrent.next.value) == 0) {
                        secondCurrent.next = secondCurrent.next.next;
                    } else {
                        secondCurrent = secondCurrent.next;
                    }
                }

                current = current.next;
            }
        }

        public void removeDupsWithBuff() {
            Set<T> elements = new HashSet<>();
            Node<T> current = head;

            Node<T> previous = null;
            while (current != null) {
                if (elements.contains(current.value)) {
                    previous.next = current.next;
                } else {
                    elements.add(current.value);
                    previous = current;
                }

                current = current.next;
            }
        }
    }

    public static void main(String[] args) {
        LL<Integer> list = new LL<>();
        list.addToTail(10);
        list.addToTail(10);
        list.addToTail(20);
        list.addToTail(30);
        list.addToTail(40);
        list.addToTail(20);
        list.addToTail(50);
        list.addToTail(50);
        list.print();

        list.removeDupsWithBuff();
        list.print();
    }

}
