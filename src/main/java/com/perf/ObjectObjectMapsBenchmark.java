package com.perf;

import com.koloboke.collect.map.hash.HashObjObjMaps;
import gnu.trove.map.hash.THashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.agrona.collections.Object2ObjectHashMap;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.SingleShotTime)
@Warmup(batchSize = 1_000_000)
@Measurement(batchSize = 1_000_000)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ObjectObjectMapsBenchmark {
    private static final String JDK_HASH_MAP = "JDK:HashMap";
    private static final String FAST_UTIL_OPEN_HASH_MAP = "FAST_UTIL:Object2ObjectOpenHashMap";
    private static final String TROVE_HASH_MAP = "TROVE:THashMap";
    private static final String AGRONA_HASH_MAP = "AGRONE:Object2ObjectHashMap";
    private static final String ECLIPSE_CONCURRENT_HASH_MAP = "ECLIPSE:EclipseConcurrentHashMap";
    private static final String JDK_CONCURRENT_HASH_MAP = "JDK:ConcurrentHashMap";
    private static final String KOLOBOKE_HASH_MAP = "KOLOBOKE:HashObjObjMaps.newMutableMap";

    Map<Integer, Integer> map;
    int index;

    @Param(value = {JDK_HASH_MAP, KOLOBOKE_HASH_MAP, TROVE_HASH_MAP, FAST_UTIL_OPEN_HASH_MAP, AGRONA_HASH_MAP, JDK_CONCURRENT_HASH_MAP, ECLIPSE_CONCURRENT_HASH_MAP})
    String qType;

    @Param({"128", "1024"})
    int qCapacity;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ObjectObjectMapsBenchmark.class.getSimpleName())
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.seconds(1))
                .forks(1)
                .warmupForks(1)
                .measurementIterations(10)
                .warmupIterations(10)
                .build();

        new Runner(opt).run();
    }

    @Setup(Level.Iteration)
    public void createMap() {
        switch (qType) {
            case JDK_HASH_MAP:
                map = new HashMap<>(qCapacity, 0.75F);
                break;
            case KOLOBOKE_HASH_MAP:
                map = HashObjObjMaps.newMutableMap();
                break;
            case TROVE_HASH_MAP:
                map = new THashMap<>(qCapacity, 0.75F);
                break;
            case FAST_UTIL_OPEN_HASH_MAP:
                map = new Object2ObjectOpenHashMap<>(qCapacity, 0.75F);
                break;
            case AGRONA_HASH_MAP:
                map = new Object2ObjectHashMap<>(qCapacity, 0.75F);
                break;
            case JDK_CONCURRENT_HASH_MAP:
                map = new ConcurrentHashMap<>(qCapacity, 0.75F);
                break;
            case ECLIPSE_CONCURRENT_HASH_MAP:
                map = new org.eclipse.collections.impl.map.mutable.ConcurrentHashMap(qCapacity);
                break;
            default:
                throw new IllegalArgumentException();
        }

        index = 0;
        for (int i = 0; i < qCapacity; i++) {
            map.put(i, i);
        }
    }

    @TearDown(Level.Invocation)
    public void setIndex() {
        index = ThreadLocalRandom.current().nextInt(qCapacity);
//        index++;
    }

//    @AuxCounters
//    @State(Scope.Thread)
//    public static class PollCounters {
//        public long pollsFaild;
//        public long pollsMade;
//    }

//    @AuxCounters
//    @State(Scope.Thread)
//    public static class OfferCounters {
//        public long offersFaild;
//        public long offersMade;
//    }

    @Benchmark
    public void get(Blackhole bh) {
        bh.consume(map.get(index));
    }

//    @Benchmark
//    @Group("tpt")
//    public void poll(PollCounters counters) {
//        Integer e = q.poll();
//        if (e == null) {
//            counters.pollsFaild++;
//        } else if (e == TEST_ELEMENT) {
//            counters.pollsMade++;
//        } else {
//            escape = e;
//        }
//    }
}
