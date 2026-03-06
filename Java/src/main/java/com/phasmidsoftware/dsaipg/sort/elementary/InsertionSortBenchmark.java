package com.phasmidsoftware.dsaipg.sort.elementary;

import java.util.Random;

public class InsertionSortBenchmark {

    public static void main(String[] args) {
        int[] sizes = {100, 200, 400, 800, 1600};

        for (int n : sizes) {
            System.out.println("\n=== Array size: " + n + " ===");

            benchmark("Random", n, generateRandom(n));
            benchmark("Ordered", n, generateOrdered(n));
            benchmark("Partial", n, generatePartial(n));
            benchmark("Reverse", n, generateReverse(n));
        }
    }

    private static void benchmark(String name, int n, Integer[] array) {
        InsertionSortBasic<Integer> sorter = InsertionSortBasic.create();
        long start = System.nanoTime();

        for (int i = 0; i < 10; i++) {
            sorter.sort(array.clone());
        }

        double time = (System.nanoTime() - start) / 10_000_000.0;
        System.out.println(name + ": " + time + " ms");
    }

    private static Integer[] generateRandom(int n) {
        Random r = new Random();
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt(n);
        return a;
    }

    private static Integer[] generateOrdered(int n) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = i;
        return a;
    }

    private static Integer[] generatePartial(int n) {
        Integer[] a = generateOrdered(n);
        Random r = new Random();
        for (int i = 0; i < n/10; i++) {
            int x = r.nextInt(n), y = r.nextInt(n);
            Integer t = a[x]; a[x] = a[y]; a[y] = t;
        }
        return a;
    }

    private static Integer[] generateReverse(int n) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = n - i - 1;
        return a;
    }
}