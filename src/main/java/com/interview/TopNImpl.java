package com.interview;

import java.util.*;

public class TopNImpl<T extends Comparable<T>> implements TopN<T> {
    private final int capacity;
    private final SortedSet<T> data;

    public TopNImpl(int capacity) {
        this.capacity = capacity;
        this.data  = new TreeSet<>((o1, o2) -> o2.compareTo(o1));
    }

    @Override
    public synchronized void push(T val) {
        data.add(val);

        if (data.size() > capacity) {
            data.remove(data.last());
        }

        if (data.size() == capacity) {
            notifyAll();
        }
    }

//    @Override
//    public synchronized void push(T val) {
//        if (data.size() < capacity) {
//            data.add(val);
//        } else {
//            if (!data.contains(val)) {
//                final T last = data.last();
//                if (last.compareTo(val) < 0) {
//                    data.remove(last);
//                    data.add(val);
//                }
//            }
//        }
//
//        if (data.size() == capacity) {
//            notifyAll();
//        }
//    }

    @Override
    public synchronized Collection<T> top() {
        while (data.size() < capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }

        return new TreeSet<>(data);

    }

    @Override
    public synchronized void clear() {
        data.clear();
    }

    public static void main(String[] args) throws InterruptedException {
        final TopN<Integer> top = new TopNImpl<>(11);

        final Runnable writer = () -> {
            for (int i = 1; i < 100; i++) {
                if (i % 25 == 0) {
                    top.clear();
                }

                top.push(i);
                System.out.println("Writer: wrote value " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        final Runnable reader = () -> {
            for (int i = 1; i < 100; i++) {
                System.out.println(top.top());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        final Thread r = new Thread(reader);
        final Thread w = new Thread(writer);
        r.start();
        w.start();
        w.join();
    }
}
