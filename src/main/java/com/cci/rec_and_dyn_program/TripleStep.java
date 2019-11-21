package com.cci.rec_and_dyn_program;

import java.util.Arrays;
import java.util.stream.IntStream;

class TS {
    int countWays(int n) {
        if (n < 0) {
            return 0;
        } else if (n == 0) {
            return 1;
        } else {
            return countWays(n - 3) + countWays(n - 2) + countWays(n - 1);
        }
    }

    int countWaysWithMemoization(int n) {
        final int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);

        return memoization(n, memo);
    }

    private int memoization(int n, int[] memo) {
        if (n < 0) {
            return 0;
        } else if (n == 0) {
            return 1;
        } else if (memo[n] > -1) {
            return memo[n];
        } else {
            memo[n] = countWays(n - 3) + countWays(n - 2) + countWays(n - 1);
            return memo[n];
        }
    }
}

public class TripleStep {
    public static void main(String[] args) {
        int steps = 20;
        TS t = new TS();

        long start = System.currentTimeMillis();
        IntStream.rangeClosed(1, steps)
                .forEach(n -> System.out.println("" + n + ": " + t.countWays(n)));
        System.out.println("It takes: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        IntStream.rangeClosed(1, steps)
                .forEach(n -> System.out.println("" + n + ": " + t.countWaysWithMemoization(n)));
        System.out.println("It takes: " + (System.currentTimeMillis() - start));
    }
}
