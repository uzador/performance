package com.cci.array_and_string;

public class IsUnique {
    private static final int ALPHABET_SIZE = 128;

    public static boolean isUnique(String message) {
        if (message.length() > ALPHABET_SIZE) {
            return false;
        }

        boolean[] symbols = new boolean[ALPHABET_SIZE];
        for(int i = 0; i < message.length(); i++) {
            char symbol = message.charAt(i);

            if (symbols[symbol]) {
                return false;
            }

            symbols[symbol] = true;
        }

        return true;
    }
}
