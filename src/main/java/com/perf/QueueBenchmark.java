package com.perf;

import org.agrona.concurrent.OneToOneConcurrentArrayQueue;
import org.jctools.queues.SpscArrayQueue;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@State(Scope.Group)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class QueueBenchmark {

    private static final String SPSC_ARRAY_QUEUE = "SpscArrayQueue";
    private static final String ARRAY_BLOCKING_QUEUE = "ArrayBlockingQueue";
    private static final String ONE_TO_ONE_CONCURRENT_ARRAY_QUEUE = "OneToOneConcurrentArrayQueue";

    static final Object TEST_ELEMENT = 1;

    Queue<Integer> q;
    Integer element = 1;
    Integer escape;

    @Param(value = {SPSC_ARRAY_QUEUE, ARRAY_BLOCKING_QUEUE, ONE_TO_ONE_CONCURRENT_ARRAY_QUEUE})
    String qType;

    @Param("128000")
    int qCapacity;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(QueueBenchmark.class.getSimpleName())
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.seconds(1))
                .forks(1)
                .warmupForks(1)
                .measurementIterations(5)
                .warmupIterations(5)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void createQueue() {
        switch (qType) {
            case SPSC_ARRAY_QUEUE:
                q = new SpscArrayQueue<>(qCapacity);
                break;
            case ARRAY_BLOCKING_QUEUE:
                q = new ArrayBlockingQueue<>(qCapacity);
                break;
            case ONE_TO_ONE_CONCURRENT_ARRAY_QUEUE:
                q = new OneToOneConcurrentArrayQueue<>(qCapacity);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @TearDown(Level.Iteration)
    public void clearQ() {
        synchronized (q) {
            q.clear();
        }
    }

    @AuxCounters
    @State(Scope.Thread)
    public static class PollCounters {
        public long pollsFaild;
        public long pollsMade;
    }

    @AuxCounters
    @State(Scope.Thread)
    public static class OfferCounters {
        public long offersFaild;
        public long offersMade;
    }

    @Benchmark
    @Group("tpt")
    public void offer(OfferCounters counters) {
        if (!q.offer(element)) {
            counters.offersFaild++;
        } else {
            counters.offersMade++;
        }
    }

    @Benchmark
    @Group("tpt")
    public void poll(PollCounters counters) {
        Integer e = q.poll();
        if (e == null) {
            counters.pollsFaild++;
        } else if (e == TEST_ELEMENT) {
            counters.pollsMade++;
        } else {
            escape = e;
        }
    }
}
