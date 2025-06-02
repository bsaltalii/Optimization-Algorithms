package GreyWolfOptimizer;

import java.util.Random;

public class GWO_QuadraticValley {

    static final int dim = 3;
    static final int numWolves = 10;
    static final int maxIter = 30;
    static final double lowerBound = 0.0;
    static final double upperBound = 10.0;
    static Random rand = new Random();

    public static void main(String[] args) {
        double[][] wolves = new double[numWolves][dim];
        double[] alpha = new double[dim];
        double[] beta = new double[dim];
        double[] delta = new double[dim];
        double alphaFit = Double.NEGATIVE_INFINITY;
        double betaFit = Double.NEGATIVE_INFINITY;
        double deltaFit = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < numWolves; i++) {
            wolves[i] = generateVector();
        }

        for (int iter = 0; iter < maxIter; iter++) {
            double a = 2.0 - 2.0 * iter / (double) maxIter;

            for (int i = 0; i < numWolves; i++) {
                double fit = fitness(wolves[i]);

                if (fit > alphaFit) {
                    delta = beta.clone();
                    deltaFit = betaFit;
                    beta = alpha.clone();
                    betaFit = alphaFit;
                    alpha = wolves[i].clone();
                    alphaFit = fit;
                } else if (fit > betaFit) {
                    delta = beta.clone();
                    deltaFit = betaFit;
                    beta = wolves[i].clone();
                    betaFit = fit;
                } else if (fit > deltaFit) {
                    delta = wolves[i].clone();
                    deltaFit = fit;
                }
            }

            for (int i = 0; i < numWolves; i++) {
                double[] newWolf = new double[dim];
                for (int j = 0; j < dim; j++) {
                    double r1 = rand.nextDouble();
                    double r2 = rand.nextDouble();
                    double A = 2 * a * r1 - a;
                    double C = 2 * r2;

                    double x1 = alpha[j] - A * Math.abs(C * alpha[j] - wolves[i][j]);
                    double x2 = beta[j] - A * Math.abs(C * beta[j] - wolves[i][j]);
                    double x3 = delta[j] - A * Math.abs(C * delta[j] - wolves[i][j]);

                    newWolf[j] = (x1 + x2 + x3) / 3.0;

                    if (newWolf[j] < lowerBound) newWolf[j] = lowerBound;
                    if (newWolf[j] > upperBound) newWolf[j] = upperBound;
                }

                wolves[i] = newWolf;
            }

            System.out.printf("Iteration %2d - Best Score: %.4f%n", iter + 1, alphaFit);
        }

        System.out.println("\nBest Solution Found:");
        for (int i = 0; i < dim; i++) {
            System.out.printf("x[%d] = %.4f%n", i, alpha[i]);
        }
        System.out.printf("Final Fitness = %.4f%n", alphaFit);
    }

    static double fitness(double[] x) {
        return -((x[0] - 3) * (x[0] - 3) + (x[1] - 2) * (x[1] - 2) + (x[2] - 5) * (x[2] - 5));
    }

    static double[] generateVector() {
        double[] v = new double[dim];
        for (int i = 0; i < dim; i++) {
            v[i] = lowerBound + (upperBound - lowerBound) * rand.nextDouble();
        }
        return v;
    }
}
