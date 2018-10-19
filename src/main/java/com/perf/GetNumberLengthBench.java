package com.perf;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;


@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class GetNumberLengthBench {

    private static final String NANOS = "nanos";
    private static final String MICROS = "micros";
    private static final String MILLIS = "millis";
    private static final String SEC = "sec";

    long timestamp;

    @Param({NANOS, MICROS, MILLIS, SEC})
    String tsType;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(GetNumberLengthBench.class.getSimpleName())
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
    public void createTs() {
        switch (tsType) {
            case NANOS:
                timestamp = System.nanoTime();
                break;
            case MICROS:
                timestamp = System.currentTimeMillis() * 1000;
                break;
            case MILLIS:
                timestamp = System.currentTimeMillis();
                break;
            case SEC:
                timestamp = System.currentTimeMillis() / 1000;
                break;
            default:
                throw new RuntimeException("Wrong tsType: " + tsType);
        }
    }

    @Benchmark
    public void string(Blackhole bh) {
        bh.consume(String.valueOf(timestamp).length());
    }

    @Benchmark
    public void log(Blackhole bh) {
        bh.consume((int) Math.log10(timestamp) + 1);
    }

    @Benchmark
    public void fastest(Blackhole bh) {
        if (timestamp > 1_000_000_000_000_000_000L) {
            bh.consume(19);
        } else if (timestamp > 100_000_000_000_000_000L) {
            bh.consume(18);
        } else if (timestamp > 10_000_000_000_000_000L) {
            bh.consume(17);
        } else if (timestamp > 1_000_000_000_000_000L) {
            bh.consume(16);
        } else if (timestamp > 100_000_000_000_000L) {
            bh.consume(15);
        } else if (timestamp > 10_000_000_000_000L) {
            bh.consume(14);
        } else if (timestamp > 1_000_000_000_000L) {
            bh.consume(13);
        } else {
            bh.consume(10);
        }
    }
}
