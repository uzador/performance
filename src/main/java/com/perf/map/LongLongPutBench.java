package com.perf.map;

import com.koloboke.collect.map.hash.HashLongLongMap;
import com.koloboke.collect.map.hash.HashLongLongMaps;
import com.koloboke.collect.map.hash.HashObjObjMaps;
import gnu.trove.map.hash.THashMap;
import gnu.trove.map.hash.TLongLongHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.agrona.collections.Long2LongHashMap;
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
public class LongLongPutBench {

    @Param({"128", "512", "1024"})
    int qCapacity;

    Map<Long, Long> jdkMap;
    Long2LongOpenHashMap fastUtilMap;
    TLongLongHashMap troveMap;
    Long2LongHashMap agronaMap;
    HashLongLongMap kolobokeMap;
    int index;
    long[] data = new long[BATCH_SIZE];

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LongLongPutBench.class.getSimpleName())
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
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
            data[i] = ThreadLocalRandom.current().nextLong(qCapacity);
            data[i] = 1_000_000;
        }

        jdkMap = new HashMap<>(qCapacity, LOAD_FACTOR);
        kolobokeMap = HashLongLongMaps.newMutableMap();
        troveMap = new TLongLongHashMap(qCapacity, LOAD_FACTOR);
        fastUtilMap = new Long2LongOpenHashMap(qCapacity, LOAD_FACTOR);
        agronaMap = new Long2LongHashMap(qCapacity, LOAD_FACTOR, -1_000_000L);

        index = 0;
    }

    @TearDown(Level.Iteration)
    public void setIndex() {
        index = ThreadLocalRandom.current().nextInt(data.length - 1);
//        index++;
    }

    @Benchmark
    public void jdk(Blackhole bh) {
        bh.consume(jdkMap.put(data[index], 1L));
    }

    @Benchmark
    public void kolobok(Blackhole bh) {
        bh.consume(kolobokeMap.put(data[index], 1L));
    }

    @Benchmark
    public void trove(Blackhole bh) {
        bh.consume(troveMap.put(data[index], 1L));
    }

    @Benchmark
    public void fastUtil(Blackhole bh) {
        bh.consume(fastUtilMap.put(data[index], 1L));
    }

    @Benchmark
    public void agrona(Blackhole bh) {
        bh.consume(agronaMap.put(data[index], 1L));
    }
}
