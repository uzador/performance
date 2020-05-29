package com.interview;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class SomeApp {

    static class TopN<T> {
        final T[] array;
        final int n;
        int idx = 0;

        TopN(int n, Class<T> clazz) {
            this.n = n;
//            array = (T[]) new Object[n];
            array = (T[]) Array.newInstance(clazz, n);
        }

        void add(T value) {
            if (idx < n) {
                array[idx++] = value;
            } else {
                System.out.println("Already full");
            }
        }

        void print() {
            for(T i : array) {
                System.out.print(i + " ");
            }
        }

        void printMore() {
            Set<Integer> set = new TreeSet<>();
            IntStream.rangeClosed(1, 10).forEach(set::add);
            Iterator<Integer> iterator = set.iterator();
            int[] tmp = new int[15];
            for(int i = 0; i < 15; i++) {
                tmp[i] = iterator.next();
            }

            System.out.println(Arrays.toString(tmp));
        }
    }

    public static void main(String[] args) {
        TopN<Integer> top = new TopN<>(5, Integer.class);
        IntStream.rangeClosed(1, 10).forEach(top::add);
        top.print();

        top.printMore();
    }
}
