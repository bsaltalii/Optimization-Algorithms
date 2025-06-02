package GreyWolfOptimizer;

import java.util.Random;

public class GWO {

    static int populationSize = 10;
    static int maxIterations = 100;
    static double a;
    static Random rand = new Random();

    static double[] wolves = new double[populationSize];
    static double alpha, beta, delta;
    static double alphaScore = Double.MAX_VALUE;
    static double betaScore = Double.MAX_VALUE;
    static double deltaScore = Double.MAX_VALUE;

    static double objectiveFunction(double x) {
        return x * x + 3 * x + 2;
    }

    public static void main(String[] args) {
        for (int i = 0; i < populationSize; i++) {
            wolves[i] = -10 + 20 * rand.nextDouble();
        }

        for (int i = 0; i < maxIterations; i++) {
            a = 2.0 - ((double) i / maxIterations) * 2.0;

            for (int j = 0; j < populationSize; j++) {
                double fitness = objectiveFunction(wolves[j]);

                if (fitness < alphaScore) {
                    deltaScore = betaScore;
                    delta = beta;

                    betaScore = alphaScore;
                    beta = alpha;

                    alphaScore = fitness;
                    alpha = wolves[j];
                } else if (fitness < betaScore) {
                    deltaScore = betaScore;
                    delta = beta;

                    betaScore = fitness;
                    beta = wolves[j];
                } else if (fitness < deltaScore) {
                    deltaScore = fitness;
                    delta = wolves[j];
                }
            }

            for (int k = 0; k < populationSize; k++) {
                double A1 = a * (2 * rand.nextDouble() - 1);
                double C1 = 2 * rand.nextDouble();
                double D_alpha = Math.abs(C1 * alpha - wolves[k]);
                double X1 = alpha - A1 * D_alpha;

                double A2 = a * (2 * rand.nextDouble() - 1);
                double C2 = 2 * rand.nextDouble();
                double D_beta = Math.abs(C2 * beta - wolves[k]);
                double X2 = beta - A2 * D_beta;

                double A3 = a * (2 * rand.nextDouble() - 1);
                double C3 = 2 * rand.nextDouble();
                double D_delta = Math.abs(C3 * delta - wolves[k]);
                double X3 = delta - A3 * D_delta;

                wolves[k] = (X1 + X2 + X3) / 3;
            }

            System.out.println("Iter " + i + " -> Best: x = " + alpha + ", f(x) = " + alphaScore);
        }

        System.out.println("\nFinal best solution: x = " + alpha + ", f(x) = " + alphaScore);
    }
}
