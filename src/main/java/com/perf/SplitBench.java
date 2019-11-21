package com.perf;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SplitBench {

    String message = "2019-04-27 14:56:31|123456789|SOME|1.ZDR:per1=val1:per2=val2:per3=val3:per4=val4:";
    String regEx = "\\|";

    Splitter sIterable;
    Splitter sList;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(SplitBench.class.getSimpleName())
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.seconds(1))
                .forks(1)
                .warmupForks(1)
                .measurementIterations(10)
                .warmupIterations(10)
                .build();

        new Runner(opt).run();
    }

    @Setup(Level.Trial)
    public void createSplitter() {
        sIterable = Splitter.on(regEx).limit(4);
        sList = Splitter.on(regEx).limit(4);
    }

    @Benchmark
    public void javaStringSplit(Blackhole bh) {
        bh.consume(message.split(regEx, 4));
    }

    @Benchmark
    public void guavaSplitterIterable(Blackhole bh) {
        bh.consume(sIterable.split(message));
    }

    @Benchmark
    public void guavaSplitterToList(Blackhole bh) {
        bh.consume(sList.splitToList(message));
    }

    @Benchmark
    public void apacheStringUtil(Blackhole bh) {
        bh.consume(StringUtils.split(message, regEx, 4));
    }

}
