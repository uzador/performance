package com.perf;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

//@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ArrayBenchmark {

//    private int SIZE = 10;
//    private int src[] = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
//    private int[] dst;

    public static void main(String[] args) throws RunnerException {
//        org.openjdk.jmh.Main.main(args);

        Options opt = new OptionsBuilder()
                .include(ArrayBenchmark.class.getSimpleName())
                .forks(1)
                .warmupForks(1)
                .measurementIterations(10)
                .warmupIterations(10)
                .build();

        new Runner(opt).run();
    }

//    @Setup
//    public void init() {
//        dst = new int[SIZE];
//    }

//    @TearDown
//    public void check(ArarysState state) {
//        assert Arrays.equals(state.src, state.dst) : "Wrong?";
//    }

    @Benchmark
    public int[] copyUsingNativeCallSystemArrayCopy(ArarysState state) {
        System.arraycopy(state.src, 0, state.dst, 0, state.size);
        return state.dst;
    }

    @Benchmark
    public int[] copyUsingArrayCopy(ArarysState state) {
        return Arrays.copyOf(state.src, state.size);
    }

    @Benchmark
    public int[] copyUsingArrayCopyOfRange(ArarysState state) {
        return Arrays.copyOfRange(state.src, 0, state.size);
    }

    @Benchmark
    public int[] copyUsingStreamApi(ArarysState state) {
        return Arrays.stream(state.src).toArray();
    }

    @Benchmark
    public int[] copyUsingManualLoop(ArarysState state) {
        for (int i = 0; i < state.size; i++) {
            state.dst[i] = state.src[i];
        }

        return state.dst;
    }

    @Benchmark
    public int[] copyUsingClone(ArarysState state) {
        return state.src.clone();
    }

    @State(Scope.Thread)
    public static class ArarysState {
        public int size = 10;
        public int src[] = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        public int dst[] = new int[size];
    }

}
