package com.interview;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface TopNFunc<T> extends Consumer<T>, Supplier<Collection<T>> {
    void clear();
}
