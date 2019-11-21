package com.interview;

import java.util.Collection;

public interface TopN<T> {
    void push(T val);

    Collection<T> top();

    void clear();
}
