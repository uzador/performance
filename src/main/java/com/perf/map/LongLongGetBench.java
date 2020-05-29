package com.perf.map;

import com.koloboke.collect.map.hash.HashLongLongMap;
import com.koloboke.collect.map.hash.HashLongLongMaps;
import gnu.trove.map.hash.TLongLongHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;
import net.openhft.chronicle.values.Values;
import org.agrona.collections.Long2LongHashMap;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.perf.map.MapUtil.BATCH_SIZE;
import static com.perf.map.MapUtil.LOAD_FACTOR;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Warmup(batchSize = BATCH_SIZE)
@Measurement(batchSize = BATCH_SIZE)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class LongLongGetBench {

    @Param({"128", "512", "1024"})
    int qCapacity;

    Map<Long, Long> jdkMap;
    Long2LongOpenHashMap fastUtilMap;
    TLongLongHashMap troveMap;
    Long2LongHashMap agronaMap;
    HashLongLongMap kolobokeMap;
    ChronicleMap<Long, Long> chronicleInMemoryMap;

    int index;
    long[] data = new long[BATCH_SIZE];

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LongLongGetBench.class.getSimpleName())
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
    public void createMap() throws IOException {
        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
            //data[i] = ThreadLocalRandom.current().nextLong(qCapacity);
            //data[i] = 1_000_000;
        }

        jdkMap = new HashMap<>(qCapacity, LOAD_FACTOR);
        kolobokeMap = HashLongLongMaps.newMutableMap();
        troveMap = new TLongLongHashMap(qCapacity, LOAD_FACTOR);
        fastUtilMap = new Long2LongOpenHashMap(qCapacity, LOAD_FACTOR);
        agronaMap = new Long2LongHashMap(qCapacity, LOAD_FACTOR, -1_000_000L);
        chronicleInMemoryMap = ChronicleMapBuilder.of(Long.class, Long.class)
                .name("chronicleInMemory")
                .entries(qCapacity)
                .create();

        index = 0;
        for (int i = 0; i < qCapacity; i++) {
            jdkMap.put(data[i], (long) i);
            fastUtilMap.put(data[i], (long) i);
            troveMap.put(data[i], (long) i);
            agronaMap.put(data[i], (long) i);
            kolobokeMap.put(data[i], (long) i);
            chronicleInMemoryMap.put(data[i], (long) i);
        }
    }

    @TearDown(Level.Iteration)
    public void setIndex() {
        index = ThreadLocalRandom.current().nextInt(data.length - 1);
//        index++;
        chronicleInMemoryMap.close();
    }

    @Benchmark
    public void jdk(Blackhole bh) {
        bh.consume(jdkMap.get(data[index]));
    }

    @Benchmark
    public void kolobok(Blackhole bh) {
        bh.consume(kolobokeMap.get(data[index]));
    }

    @Benchmark
    public void trove(Blackhole bh) {
        bh.consume(troveMap.get(data[index]));
    }

    @Benchmark
    public void fastUtil(Blackhole bh) {
        bh.consume(fastUtilMap.get(data[index]));
    }

    @Benchmark
    public void agrona(Blackhole bh) {
        bh.consume(agronaMap.get(data[index]));
    }

    @Benchmark
    public void chronicleInMemoryMap(Blackhole bh) {
        bh.consume(chronicleInMemoryMap.get(data[index]));
    }
}
