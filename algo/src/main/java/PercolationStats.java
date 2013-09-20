/**
 *
 * Copyright (c) Baboune AB, 2013.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 *
 * Baboune MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Baboune SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * User: Baboune
 * Date: 9/3/13
 */


import java.util.Random;

/**
 * comments.
 */
public class PercolationStats {
    private double[] results;
    private int nbOfExperiments;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (T <= 0 || N <= 0) {
            throw new IllegalArgumentException("T and N must be positive integers.");
        }
        double gridSize = N * N;
        nbOfExperiments = T;
        results = new double[T];

        Random rnd = new Random();
        int p, q;
        for (int i = 0; i < results.length; i++) {
            long counter = 0;
            Percolation obj = new Percolation(N);
            while (!obj.percolates()) {
                p = rnd.nextInt(N) + 1;
                q = rnd.nextInt(N) + 1;
                if (!obj.isOpen(p, q)) {
                    obj.open(p, q);
                    counter++;
                }
                //System.out.println("status: " + obj);
            }
            System.out.println("(" + i + ") Percolated at: " + counter);
            results[i] = counter / gridSize;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double r = 0;
        for (double result : results) {
            r += result;
        }
        return r / nbOfExperiments;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = mean();
        return stddevWithMean(mean);
    }


    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddevWithMean(mean);
        return mean - (1.96 * stddev / Math.sqrt(nbOfExperiments));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddevWithMean(mean);
        return mean + (1.96 * stddev / Math.sqrt(nbOfExperiments));
    }

    private double stddevWithMean(final double mean) {
        if (nbOfExperiments == 1)
            return Double.NaN;
        double q = 0;
        for (double result : results) {
            q += (result - mean) * (result - mean);
        }
        return Math.sqrt(q / (nbOfExperiments - 1));
    }

    // test client, described below
    //Your task is to write a computer program to estimate p*.
    // p* the percolation threshold such as:
    // - p < p* -> no percolation
    // - p> p* -> percolates
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        System.out.println("Using N= " + N + ", T= " + T);

        PercolationStats p = new PercolationStats(N, T);
        double mean = p.mean();
        double stddev = p.stddevWithMean(mean);
        double hiConf = p.confidenceHi();
        double loConf = p.confidenceLo();

        System.out.println("mean\t\t\t\t\t\t= " + mean);
        System.out.println("stddev\t\t\t\t\t\t= " + stddev);
        System.out.println("95% confidence interval\t\t= " + loConf + ", " + hiConf);
    }

}
