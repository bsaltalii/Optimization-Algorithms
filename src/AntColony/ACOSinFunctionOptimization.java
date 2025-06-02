package AntColony;

import java.util.Random;

public class ACOSinFunctionOptimization {
    static final int NUM_ANTS = 30;
    static final int NUM_POINTS = 101;
    static final int MAX_ITER = 50;

    static final double ALPHA = 1;
    static final double BETA = 2;
    static final double RHO = 0.2;
    static final double Q = 100;

    static double[] pheromones = new double[NUM_POINTS];
    static double[] xPoints = new double[NUM_POINTS];

    public static void main(String[] args) {
        Random rnd = new Random();

        for (int i = 0; i < NUM_POINTS; i++) {
            xPoints[i] = i * 0.1;
            pheromones[i] = 1;
        }

        double bestX = 0;
        double bestFx = Double.MAX_VALUE;

        for (int iter = 0; iter < MAX_ITER; iter++) {
            int[] selectedIndices = new int[NUM_ANTS];
            double[] fitnessValues = new double[NUM_ANTS];

            for (int ant = 0; ant < NUM_ANTS; ant++) {
                int index = selectIndex(rnd);
                selectedIndices[ant] = index;

                double x = xPoints[index];
                double fx = objectiveFunction(x);
                fitnessValues[ant] = fx;

                if (fx < bestFx) {
                    bestFx = fx;
                    bestX = x;
                }
            }

            evaporatePheromones();
            depositPheromones(selectedIndices, fitnessValues);
            System.out.printf("Iteration %2d - Best x: %.2f, f(x): %.4f\n", iter + 1, bestX, bestFx);
        }

        System.out.println("\nBest solution found:");
        System.out.printf("x = %.2f, f(x) = %.4f\n", bestX, bestFx);
    }

    public static double objectiveFunction(double x) {
        return Math.sin(x) + 0.1 * x * x;
    }

    public static int selectIndex(Random rnd) {
        double[] probabilities = new double[NUM_POINTS];
        double sum = 0;

        for (int i = 0; i < NUM_POINTS; i++) {
            double heuristic = 1.0 / (objectiveFunction(xPoints[i]) + 1e-6);
            probabilities[i] = Math.pow(pheromones[i], ALPHA) * Math.pow(heuristic, BETA);
            sum += probabilities[i];
        }

        double r = rnd.nextDouble() * sum;
        double cumulative = 0;

        for (int i = 0; i < NUM_POINTS; i++) {
            cumulative += probabilities[i];
            if (r <= cumulative) return i;
        }

        return NUM_POINTS - 1;
    }

    public static void evaporatePheromones() {
        for (int i = 0; i < pheromones.length; i++) {
            pheromones[i] *= (1 - RHO);
        }
    }

    public static void depositPheromones(int[] selectedIndices, double[] fitnessValues) {
        for (int i = 0; i < NUM_ANTS; i++) {
            int idx = selectedIndices[i];
            double contribution = Q / (fitnessValues[i] + 1e-6);
            pheromones[idx] += contribution;
        }
    }
}
