package com.cci.array_and_string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsUniqueTest {

    @ParameterizedTest(name = "string {0} is unique")
    @DisplayName("string is unique")
    @ValueSource(strings = {"abcdefghij", "0123456789", "asdfgh!@#$%^)(123", ""})
    public void success(String string) {
        assertTrue(IsUnique.isUnique(string));
    }

    @ParameterizedTest(name = "string {0} isn't unique")
    @DisplayName("string isn't unique")
    @ValueSource(strings = {"abcdefghija", "01234567895", "asdfgh!@#$%^)(123)"})
    public void failed(String string) {
        assertFalse(IsUnique.isUnique(string));
    }

}