package com.CK;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
//        int[] quality = {10, 20, 5}, wage = {70, 50, 30};
//        int K = 2;
        int[] quality = {3, 1, 10, 10, 1}, wage = {4, 8, 2, 2, 7};
        int K = 3;
        new Solution().mincostToHireWorkers(quality, wage, K);
    }
}

//TLE
class Solution1 {
    public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
        int n = quality.length;
        double global = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            PriorityQueue<Double> pq = new PriorityQueue<>();
            for (int j = 0; j < n; j++) {
                double expected = (double) quality[j] * (double) wage[i] / (double) quality[i];
                double minimum = wage[j];
                if (expected < minimum) {
                    expected = 10000000.0;
                }
                pq.offer(Math.max(expected, minimum));
            }

            double local = 0.0;
            for (int k = 0; k < K; k++) {
                local += pq.poll();
            }
            global = Math.min(global, local);
        }
        return global;
    }
}

//O(NlogN)
class Solution {
    public double mincostToHireWorkers(int[] q, int[] w, int K) {
        double[][] workers = new double[q.length][2];
        for (int i = 0; i < q.length; ++i)
            workers[i] = new double[]{(double) (w[i]) / q[i], (double) q[i]};
        Arrays.sort(workers, (a, b) -> Double.compare(a[0], b[0]));
        double res = Double.MAX_VALUE, qsum = 0;
        PriorityQueue<Double> pq = new PriorityQueue<>();
        for (double[] worker : workers) {
            qsum += worker[1];
            pq.add(-worker[1]);
            if (pq.size() > K)
                qsum += pq.poll();
            if (pq.size() == K)
                res = Math.min(res, qsum * worker[0]);
        }
        return res;
    }
}