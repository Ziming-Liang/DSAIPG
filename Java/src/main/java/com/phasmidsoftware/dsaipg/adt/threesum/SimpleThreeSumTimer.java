package com.phasmidsoftware.dsaipg.adt.threesum;

import java.util.Arrays;
import java.util.Random;

/**
 * Simple performance timer for ThreeSum algorithms
 * Uses doubling method to test at least 5 different N values
 */
public class SimpleThreeSumTimer {

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("ThreeSum Performance Analysis (Doubling Method)");
        System.out.println("==============================================\n");

        // 至少 5 个 N 值，使用 doubling method
        int[] sizes = {250, 500, 1000, 2000, 4000};

        // 打印表头
        System.out.printf("%-10s %-15s %-20s %-18s%n",
                "N", "Cubic (ms)", "Quadrithmic (ms)", "Quadratic (ms)");
        System.out.println("----------------------------------------------------------");

        // 对每个 N 值进行测试
        for (int n : sizes) {
            // 生成测试数据（排序的随机数组）
            int[] data = generateSortedArray(n);

            // 测试 Cubic（N > 4000 时跳过，因为太慢）
            long cubicTime = 0;
            if (n <= 4000) {
                cubicTime = testCubic(data.clone());
            }

            // 测试 Quadrithmic
            long quadrithmicTime = testQuadrithmic(data.clone());

            // 测试 Quadratic
            long quadraticTime = testQuadratic(data.clone());

            // 输出结果
            if (n <= 4000) {
                System.out.printf("%-10d %-15d %-20d %-18d%n",
                        n, cubicTime, quadrithmicTime, quadraticTime);
            } else {
                System.out.printf("%-10d %-15s %-20d %-18d%n",
                        n, "N/A", quadrithmicTime, quadraticTime);
            }
        }

        System.out.println("----------------------------------------------------------");
        System.out.println("\nNote: Cubic algorithm skipped for N > 4000 (too slow)");
        System.out.println("Time measured using System.currentTimeMillis()");
    }

    /**
     * 生成排序的随机数组
     */
    private static int[] generateSortedArray(int n) {
        Random rand = new Random(42); // 固定种子确保可重复
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(2000) - 1000;  // -1000 到 1000
        }
        Arrays.sort(arr);
        return arr;
    }

    /**
     * 测试 Cubic 算法
     */
    private static long testCubic(int[] data) {
        long startTime = System.currentTimeMillis();
        ThreeSumCubic cubic = new ThreeSumCubic(data);
        cubic.getTriples();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * 测试 Quadrithmic 算法
     */
    private static long testQuadrithmic(int[] data) {
        long startTime = System.currentTimeMillis();
        ThreeSumQuadrithmic quadrithmic = new ThreeSumQuadrithmic(data);
        quadrithmic.getTriples();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * 测试 Quadratic 算法
     */
    private static long testQuadratic(int[] data) {
        long startTime = System.currentTimeMillis();
        ThreeSumQuadratic quadratic = new ThreeSumQuadratic(data);
        quadratic.getTriples();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}