package org.leoyy.utils4j.algo;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;


/**
 * Created by yuanyi on 2018/1/18.
 */
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchmarkArraysSort {

    public static int[] createRandomIntData(int len) {
        Random r = new Random(System.currentTimeMillis());
        int[] rs = new int[len];
        for (int i = 0; i < len; ++i) {
            rs[i] = r.nextInt();
        }
        return rs;
    }

    public static double[] createRandomDoubleData(int len) {
        Random r = new Random(System.currentTimeMillis());
        double[] rs = new double[len];
        for (int i = 0; i < len; ++i) {
            rs[i] = r.nextDouble();
        }
        return rs;
    }

    static int[] RAND_INT_DATA;
    static {
        RAND_INT_DATA = createRandomIntData(10000000);
    }

    @Test
    public void testSort() {
        int[] data = createRandomIntData(10000000);
        ArraysSort.introSort(data, 0, data.length, (l, r) -> (Integer.compare(l, r)));
        //ArraysSort.heapSort(data, 0, data.length, (l, r) -> (Integer.compare(l, r)));
        for (int i = 0; i < data.length - 1; ++i) {
            if (data[i] > data[i + 1]) {
                assertTrue(false);
            }
        }
    }

    @Benchmark
    public void benchmarkHeapSortRandomInt(Blackhole hole) {
        int[] data = new int[RAND_INT_DATA.length];
        System.arraycopy(RAND_INT_DATA, 0, data, 0, RAND_INT_DATA.length);
        ArraysSort.heapSort(data, 0, data.length, (l, r) -> (Integer.compare(l, r)));
    }

    @Benchmark
    public void benchmarkIntroSortRandomInt(Blackhole hole) {
        int[] data = new int[RAND_INT_DATA.length];
        System.arraycopy(RAND_INT_DATA, 0, data, 0, RAND_INT_DATA.length);
        ArraysSort.introSort(data, 0, data.length, (l, r) -> (Integer.compare(l, r)));
        hole.consume(data);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkArraysSort.class.getSimpleName())
                .forks(1)
                .warmupIterations(10)
                .measurementIterations(5)
                .build();
        new Runner(opt).run();
    }
}
