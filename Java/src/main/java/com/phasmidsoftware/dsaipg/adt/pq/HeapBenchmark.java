package com.phasmidsoftware.dsaipg.adt.pq;

import com.phasmidsoftware.dsaipg.util.benchmark.Benchmark_Timer;
import com.phasmidsoftware.dsaipg.util.config.Config;
import java.util.*;

public class HeapBenchmark {

    public static void main(String[] args) {
        try {
            Config config = Config.load(HeapBenchmark.class);

            System.out.println("=== Heap Benchmark ===\n");

            test("Binary Heap", 4095, 16000, 4000, false, config);
            test("Binary + Floyd", 4095, 16000, 4000, true, config);
            test4ary("4-ary Heap", 4095, 16000, 4000, false, config);
            test4ary("4-ary + Floyd", 4095, 16000, 4000, true, config);
            testFib("Fibonacci", 4095, 16000, 4000, config);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test(String name, int cap, int ins, int del, boolean floyd, Config config) {
        Benchmark_Timer<Integer> timer = new Benchmark_Timer<>(name, config, x -> {
            PriorityQueue_BinaryHeap<Integer> pq =
                    new PriorityQueue_BinaryHeap<>(cap, false, Integer::compareTo, floyd);
            Random r = new Random(42);

            for (int i = 0; i < ins; i++) {
                if (pq.size() == cap) try { pq.take(); } catch (Exception e) {}
                pq.give(r.nextInt(100000));
            }
            for (int i = 0; i < del; i++) {
                try { pq.take(); } catch (Exception e) {}
            }
        });

        System.out.println(name + ": " + String.format("%.2f ms", timer.run(1, 1)));
    }

    private static void test4ary(String name, int cap, int ins, int del, boolean floyd, Config config) {
        Benchmark_Timer<Integer> timer = new Benchmark_Timer<>(name, config, x -> {
            PriorityQueue_QuaternaryHeap<Integer> pq =
                    new PriorityQueue_QuaternaryHeap<>(cap, false, Integer::compareTo, floyd);
            Random r = new Random(42);

            for (int i = 0; i < ins; i++) {
                if (pq.size() == cap) try { pq.take(); } catch (Exception e) {}
                pq.give(r.nextInt(100000));
            }
            for (int i = 0; i < del; i++) {
                try { pq.take(); } catch (Exception e) {}
            }
        });

        System.out.println(name + ": " + String.format("%.2f ms", timer.run(1, 1)));
    }

    private static void testFib(String name, int cap, int ins, int del, Config config) {
        Benchmark_Timer<Integer> timer = new Benchmark_Timer<>(name, config, x -> {
            PriorityQueue_FibonacciHeap<Integer> pq =
                    new PriorityQueue_FibonacciHeap<>(cap, false, Integer::compareTo);
            Random r = new Random(42);

            for (int i = 0; i < ins; i++) {
                if (pq.size() >= cap) try { pq.take(); } catch (Exception e) {}
                pq.give(r.nextInt(100000));
            }
            for (int i = 0; i < del; i++) {
                try { pq.take(); } catch (Exception e) {}
            }
        });

        System.out.println(name + ": " + String.format("%.2f ms", timer.run(1, 1)));
    }
}