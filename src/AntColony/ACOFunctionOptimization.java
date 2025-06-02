package AntColony;

import java.util.Random;

public class ACOFunctionOptimization {

    static final int numAnts = 20;
    static final int numIterations = 50;
    static final int numPoints = 100;
    static final double alpha = 1.0;
    static final double beta = 2.0;
    static final double evaporation = 0.5;
    static final double Q = 100.0;

    static final double xmin = -10;
    static final double xmax = 10;
    static double[] positions = new double[numPoints];
    static double[] values = new double[numPoints];
    static double[] pheromones = new double[numPoints];

    static Random rand = new Random();

    public static void main(String[] args) {

        double step = (xmax - xmin) / (numPoints - 1);
        for (int i = 0; i < numPoints; i++) {
            positions[i] = xmin + i * step;
            values[i] = objective(positions[i]);
            pheromones[i] = 1.0;
        }

        double bestVal = Double.MAX_VALUE;
        double bestX = 0;

        for (int iter = 0; iter < numIterations; iter++) {
            int[] selectedIndices = new int[numAnts];

            for (int k = 0; k < numAnts; k++) {
                selectedIndices[k] = selectPosition();
            }

            for (int i = 0; i < numPoints; i++) {
                pheromones[i] *= (1 - evaporation);
            }

            for (int k = 0; k < numAnts; k++) {
                int idx = selectedIndices[k];
                double f = values[idx];
                pheromones[idx] += Q / f;

                if (f < bestVal) {
                    bestVal = f;
                    bestX = positions[idx];
                }
            }

            System.out.printf("Iteration %2d - Best f(x): %.4f at x = %.4f%n", iter + 1, bestVal, bestX);
        }

        System.out.println("\nOptimal x ≈ " + bestX);
        System.out.println("Minimum f(x) ≈ " + bestVal);
    }

    static double objective(double x) {
        return x * x + 4 * Math.sin(x);
    }

    static int selectPosition() {
        double[] probabilities = new double[numPoints];
        double sum = 0;
        for (int i = 0; i < numPoints; i++) {
            double heuristic = 1.0 / (values[i] + 1e-6);
            probabilities[i] = Math.pow(pheromones[i], alpha) * Math.pow(heuristic, beta);
            sum += probabilities[i];
        }

        double r = rand.nextDouble() * sum;
        double cumulative = 0;
        for (int i = 0; i < numPoints; i++) {
            cumulative += probabilities[i];
            if (r <= cumulative) return i;
        }

        return numPoints - 1;
    }
}
