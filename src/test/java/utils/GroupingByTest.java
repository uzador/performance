package utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GroupingByTest {

    @Test
    public void groupingBy() {
        int batchSize = 16;
        AtomicInteger count = new AtomicInteger(0);

        final List<String> list = new ArrayList<>();
        IntStream.rangeClosed(1, 100_000).forEach(i -> list.add(String.valueOf(i)));

        Map<Integer, List<String>> map = list.stream()
                .collect(Collectors.groupingBy(e -> count.addAndGet(e.length()) / batchSize));

        System.out.println(map);
    }

}
