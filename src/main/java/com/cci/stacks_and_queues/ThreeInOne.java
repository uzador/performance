package com.cci.stacks_and_queues;

import java.util.Arrays;

public class ThreeInOne {

    private static final int NUMBER_OF_STACKS = 3;

    private final int stackSize;
    private final int[] values;
    private final int[] sizes;

    ThreeInOne(int stackSize) {
        this.stackSize = stackSize;

        values = new int[stackSize * NUMBER_OF_STACKS];
        sizes = new int[NUMBER_OF_STACKS];
    }

    public void push(int stackNum, int value) {
        if (isFull(stackNum)) {
            System.err.println(String.format("Can't add value{%s} cause stack{%s} is full", value, stackNum));
            return;
        }

        values[topIdx(stackNum)] = value;
        sizes[stackNum]++;
    }

    public int pop(int stackNum) {
        if (isEmpty(stackNum)) {
            System.err.println(String.format("Can't pop value cause stack{%s} is empty", stackNum));
            return Integer.MIN_VALUE;
        }

        int idx = topIdx(stackNum) - 1;
        sizes[stackNum]--;

        return values[idx];
    }

    private int topIdx(int stackNum) {
        return stackNum * stackSize + sizes[stackNum];
    }

    private boolean isFull(int stackNum) {
        return sizes[stackNum] == stackSize;
    }

    private boolean isEmpty(int stackNum) {
        return sizes[stackNum] == 0;
    }

    public void print(int stackNumber) {
        System.out.println(Arrays.toString(values));
    }

    public static void main(String[] args) {
        ThreeInOne stack = new ThreeInOne(3);
        stack.push(0, 1);
        stack.push(0, 2);
        stack.push(0, 3);
        stack.push(0, 4);
        stack.print(0);

        System.out.println(stack.pop(0));

        stack.push(0, 4);
        stack.print(0);

        System.out.println(stack.pop(0));
        System.out.println(stack.pop(0));
        System.out.println(stack.pop(0));
        System.out.println(stack.pop(0));
    }

}
