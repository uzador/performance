package com.cci.bit_manipulation;

public class Insertion {
    public static void main(String[] args) {
        int N = 1024;  // 100000000
        int M = 19;    // 10011

        System.out.println(Integer.toBinaryString(N));
        System.out.println(Integer.toBinaryString(M));

        int start = 2;
        int end = 6;

        int j = 0;
        for (int i = start; i <= end; i++) {
            boolean bitToSet = getBit(M, j++);
            N = updateBit(N, i, bitToSet);

            System.out.println(Integer.toBinaryString(N));
        }

        System.out.println("N: " + Integer.toBinaryString(N));

        System.out.println("-1 =  " + Integer.toBinaryString(-1));
    }

    private static boolean getBit(int num, int i) {
        return (num & (1 << i)) != 0;
    }

    private static int setBit(int num, int i) {
        return num | (1 << i);
    }

    private static int updateBit(int num, int i, boolean bitIs1) {
        int value = bitIs1 ? 1 : 0;
        int mask = ~(1 << i);
        return (num & mask) | (value << i);
    }
}
