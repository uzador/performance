package com.interview;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

public class TopNFuncImpl<T extends Comparable<T>> implements TopNFunc<T> {

    private final int capacity;
    private final SortedSet<T> data;

    public TopNFuncImpl(int capacity) {
        this.capacity = capacity;
        this.data = new TreeSet<>((o1, o2) -> o2.compareTo(o1));
    }

    @Override
    public synchronized void accept(T value) {
        if (data.size() <= capacity) {
            data.add(value);
        }

        if (data.size() > capacity) {
            data.remove(data.last());
        }

        if  (data.size() == capacity) {
            notifyAll();
        }
    }

    @Override
    public synchronized Collection<T> get() {
        while (data.size() < capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new TreeSet<>(data);
    }

    @Override
    public void clear() {
        data.clear();
    }

    public static void main(String[] args) throws InterruptedException {
        final TopNFunc<Integer> top = new TopNFuncImpl<>(11);

        final Runnable writer = () -> {
            for (int i = 1; i < 100; i++) {
                if (i % 25 == 0) {
                    top.clear();
                }

                top.accept(i);
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
                System.out.println(top.get());
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
