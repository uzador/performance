package com.perf;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ArrayBenchmark {

    int[] src, dst;

    public static void main(String[] args) throws RunnerException {
//        org.openjdk.jmh.Main.main(args);

        Options opt = new OptionsBuilder()
                .include(ArrayBenchmark.class.getSimpleName())
//                .timeout(TimeValue.seconds(1))
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.seconds(1))
                .forks(1)
                .warmupForks(1)
                .measurementIterations(10)
                .warmupIterations(10)
                .build();

        new Runner(opt).run();
    }

    @Param({"16", "32", "64", "128", "256"})
    int size;

    @Setup
    public void init() {
        src = new int[size];
        dst = new int[size];
        Arrays.fill(src, 23);
    }

//    @TearDown
//    public void check(ArarysState state) {
//        assert Arrays.equals(state.src, state.dst) : "Wrong?";
//    }

    @Benchmark
    public int[] copyUsingNativeCallSystemArrayCopy() {
        System.arraycopy(src, 0, dst, 0, size);
        return dst;
    }

    @Benchmark
    public int[] copyUsingArrayCopy() {
        return Arrays.copyOf(src, size);
    }

    @Benchmark
    public int[] copyUsingArrayCopyOfRange() {
        return Arrays.copyOfRange(src, 0, size);
    }

    @Benchmark
    public int[] copyUsingStreamApi() {
        return Arrays.stream(src).toArray();
    }

    @Benchmark
    public int[] copyUsingManualLoop() {
        for (int i = 0; i < size; i++) {
            dst[i] = src[i];
        }

        return dst;
    }

    @Benchmark
    public int[] copyUsingClone() {
        return src.clone();
    }

//    @State(Scope.Thread)
//    public static class ArarysState {
//        public int size = 10;
//        public int src[] = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
//        public int dst[] = new int[size];
//    }

}
