package com.perf;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class RegExBench {

    String message = "Hello Dolly";
    String regEx = ".*Dolly.*";

    Pattern p;
    Matcher m;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(RegExBench.class.getSimpleName())
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
    public void createPattern() {
        p = Pattern.compile(regEx);
        m = Pattern.compile(regEx).matcher("To_Gen_Matcher");
    }

    @Benchmark
    public void withoutCompile(Blackhole bh) {
        bh.consume(Pattern.compile(regEx).matcher(message).matches());
    }

    @Benchmark
    public void withPreCompile(Blackhole bh) {
        bh.consume(p.matcher(message).matches());
    }

    @Benchmark
    public void withoutPreCompileAndMatcherReuse(Blackhole bh) {
        bh.consume(m.reset(message).matches());
    }

}
