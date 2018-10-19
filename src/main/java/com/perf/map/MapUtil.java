package com.perf.map;

class MapUtil {
    static final int BATCH_SIZE = 1_000_000;
    static final float LOAD_FACTOR = 0.55F;

    public static void main(String[] args) {
        long timestamp = System.currentTimeMillis() * 1_000;

        if (timestamp > 1_000_000_000_000_000_000L) {
            System.out.println(19);
        } else if (timestamp > 100_000_000_000_000_000L) {
            System.out.println(18);
        } else if (timestamp > 10_000_000_000_000_000L) {
            System.out.println(17);
        } else if (timestamp > 1_000_000_000_000_000L) {
            System.out.println(16);
        } else if (timestamp > 100_000_000_000_000L) {
            System.out.println(15);
        } else if (timestamp > 10_000_000_000_000L) {
            System.out.println(14);
        } else if (timestamp > 1_000_000_000_000L) {
            System.out.println(13);
        } else {
            System.out.println(10);
        }
        System.out.println();

        System.out.println(String.valueOf(timestamp).length());
        System.out.println((int) Math.log10(timestamp) + 1);
    }
}
