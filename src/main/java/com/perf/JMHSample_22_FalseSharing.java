package com.perf;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class JMHSample_22_FalseSharing {

    /*
     * One of the unusual thing that can bite you back is false sharing.
     * If two threads access (and possibly modify) the adjacent values
     * in memory, chances are, they are modifying the values on the same
     * cache line. This can yield significant (artificial) slowdowns.
     *
     * JMH helps you to alleviate this: @States are automatically padded.
     * This padding does not extend to the State internals though,
     * as we will see in this example. You have to take care of this on
     * your own.
     */

    /*
     * Suppose we have two threads:
     *   a) innocuous reader which blindly reads its own field
     *   b) furious writer which updates its own field
     */

    /*
     * BASELINE EXPERIMENT:
     * Because of the false sharing, both reader and writer will experience
     * penalties.
     */

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHSample_22_FalseSharing.class.getSimpleName())
                .threads(Runtime.getRuntime().availableProcessors())
//                .warmupTime(TimeValue.seconds(1))
//                .measurementTime(TimeValue.seconds(1))
                .forks(1)
                .warmupForks(1)
                .measurementIterations(10)
                .warmupIterations(10)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    @Group("baseline")
    public int reader(StateBaseline s) {
        return s.readOnly;
    }

    @Benchmark
    @Group("baseline")
    public void writer(StateBaseline s) {
        s.writeOnly++;
    }

    /*
     * APPROACH 1: PADDING
     *
     * We can try to alleviate some of the effects with padding.
     * This is not versatile because JVMs can freely rearrange the
     * field order.
     */

    @Benchmark
    @Group("padded")
    public int reader(StatePadded s) {
        return s.readOnly;
    }

    @Benchmark
    @Group("padded")
    public void writer(StatePadded s) {
        s.writeOnly++;
    }

    @Benchmark
    @Group("hierarchy")
    public int reader(StateHierarchy s) {
        return s.readOnly;
    }

    /*
     * APPROACH 2: CLASS HIERARCHY TRICK
     *
     * We can alleviate false sharing with this convoluted hierarchy trick,
     * using the fact that superclass fields are usually laid out first.
     * In this construction, the protected field will be squashed between
     * paddings.
     */

    @Benchmark
    @Group("hierarchy")
    public void writer(StateHierarchy s) {
        s.writeOnly++;
    }

    @Benchmark
    @Group("sparse")
    public int reader(StateArray s) {
        return s.arr[0];
    }

    @Benchmark
    @Group("sparse")
    public void writer(StateArray s) {
        s.arr[64]++;
    }

    @Benchmark
    @Group("contended")
    public int reader(StateContended s) {
        return s.readOnly;
    }

    @Benchmark
    @Group("contended")
    public void writer(StateContended s) {
        s.writeOnly++;
    }

    @State(Scope.Group)
    public static class StateBaseline {
        int readOnly;
        int writeOnly;
    }

    @State(Scope.Group)
    public static class StatePadded {
        int readOnly;
        int p01, p02, p03, p04, p05, p06, p07, p08;
        int p11, p12, p13, p14, p15, p16, p17, p18;
        int writeOnly;
        int q01, q02, q03, q04, q05, q06, q07, q08;
        int q11, q12, q13, q14, q15, q16, q17, q18;
    }

    /*
     * APPROACH 3: ARRAY TRICK
     *
     * This trick relies on the contiguous allocation of an array.
     * Instead of placing the fields in the class, we mangle them
     * into the array at very sparse offsets.
     */

    public static class StateHierarchy_1 {
        int readOnly;
    }

    public static class StateHierarchy_2 extends StateHierarchy_1 {
        int p01, p02, p03, p04, p05, p06, p07, p08;
        int p11, p12, p13, p14, p15, p16, p17, p18;
    }

    public static class StateHierarchy_3 extends StateHierarchy_2 {
        int writeOnly;
    }

    /*
     * APPROACH 4:
     *
     * @Contended (since JDK 8):
     *  Uncomment the annotation if building with JDK 8.
     *  Remember to flip -XX:-RestrictContended to enable.
     */

    public static class StateHierarchy_4 extends StateHierarchy_3 {
        int q01, q02, q03, q04, q05, q06, q07, q08;
        int q11, q12, q13, q14, q15, q16, q17, q18;
    }

    @State(Scope.Group)
    public static class StateHierarchy extends StateHierarchy_4 {
    }

    @State(Scope.Group)
    public static class StateArray {
        int[] arr = new int[128];
    }

    @State(Scope.Group)
    public static class StateContended {
        int readOnly;

        @jdk.internal.vm.annotation.Contended
        int writeOnly;
    }

}
