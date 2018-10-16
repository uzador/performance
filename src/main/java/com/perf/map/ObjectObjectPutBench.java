package com.perf.map;

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

import static com.perf.map.Util.BATCH_SIZE;
import static com.perf.map.Util.LOAD_FACTOR;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.SingleShotTime)
@Warmup(batchSize = BATCH_SIZE)
@Measurement(batchSize = BATCH_SIZE)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ObjectObjectPutBench {
    private static final String JDK_HASH_MAP = "JDK:HashMap";
    private static final String FAST_UTIL_OPEN_HASH_MAP = "FAST_UTIL:Object2ObjectOpenHashMap";
    private static final String TROVE_HASH_MAP = "TROVE:THashMap";
    private static final String AGRONA_HASH_MAP = "AGRONE:Object2ObjectHashMap";
    private static final String ECLIPSE_CONCURRENT_HASH_MAP = "ECLIPSE:EclipseConcurrentHashMap";
    private static final String JDK_CONCURRENT_HASH_MAP = "JDK:ConcurrentHashMap";
    private static final String KOLOBOKE_HASH_MAP = "KOLOBOKE:HashObjObjMaps.newMutableMap";

    @Param(value = {JDK_HASH_MAP, KOLOBOKE_HASH_MAP, TROVE_HASH_MAP, FAST_UTIL_OPEN_HASH_MAP, AGRONA_HASH_MAP, JDK_CONCURRENT_HASH_MAP, ECLIPSE_CONCURRENT_HASH_MAP})
    String qType;

    @Param({"128", "512", "1024"})
    int qCapacity;

    Map<Integer, Integer> map;
    int index;
    Integer[] data = new Integer[BATCH_SIZE];

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ObjectObjectPutBench.class.getSimpleName())
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
                map = new HashMap<>(qCapacity, LOAD_FACTOR);
                break;
            case KOLOBOKE_HASH_MAP:
                map = HashObjObjMaps.newMutableMap();
                break;
            case TROVE_HASH_MAP:
                map = new THashMap<>(qCapacity, LOAD_FACTOR);
                break;
            case FAST_UTIL_OPEN_HASH_MAP:
                map = new Object2ObjectOpenHashMap<>(qCapacity, LOAD_FACTOR);
                break;
            case AGRONA_HASH_MAP:
                map = new Object2ObjectHashMap<>(qCapacity, LOAD_FACTOR);
                break;
            case JDK_CONCURRENT_HASH_MAP:
                map = new ConcurrentHashMap<>(qCapacity, LOAD_FACTOR);
                break;
            case ECLIPSE_CONCURRENT_HASH_MAP:
                map = new org.eclipse.collections.impl.map.mutable.ConcurrentHashMap(qCapacity);
                break;
            default:
                throw new IllegalArgumentException();
        }

        index = 0;
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
//            data[i] = ThreadLocalRandom.current().nextInt(qCapacity);
//            data[i] = 1_000_000;
        }
    }

    @TearDown(Level.Invocation)
    public void setIndex() {
        index = ThreadLocalRandom.current().nextInt(data.length - 1);
//        index++;
    }

    @Benchmark
    public void get(Blackhole bh) {
        bh.consume(map.put(data[index], index));
    }
}
