package utils;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JolTest {

    @Test
    public void jol() {
        final Map<Integer, Integer> map = new HashMap<>(16);
        IntStream.rangeClosed(1, 1).forEach(i -> map.put(i, i + i));

        System.out.println(VM.current().details());

//        System.out.println(ClassLayout.parseClass(HashMap.class).toPrintable());
//        System.out.println(ClassLayout.parseClass(HashMap.class).toPrintable(map));
//        System.out.println(GraphLayout.parseInstance(map).toPrintable());

        System.out.println(GraphLayout.parseInstance(map).toFootprint());
        System.out.println("Total size: " + GraphLayout.parseInstance(map).totalSize());
    }

}
