package AntColony;

import java.util.Random;

public class ACOMinFunctionValue {

    static final int NUM_ANTS = 20;
    static final int NUM_POINTS = 101;
    static final int MAX_ITER = 30;

    static final double ALPHA = 1;
    static final double BETA = 2;
    static final double RHO = 0.2;
    static final double Q = 100;

    static double[] pheromones = new double[NUM_POINTS];
    static double[] xPoints = new double[NUM_POINTS];

    public static void main(String[] args) {

        for (int i = 0; i < NUM_POINTS; i++) {
            pheromones[i] = 1;
            xPoints[i] = i * 0.1;
        }

        Random rand = new Random();
        double bestX = 0;
        double bestFx = Double.MAX_VALUE;

        for (int iter = 0; iter < MAX_ITER; iter++) {

            int[] selectedIndices = new int[NUM_ANTS];
            double[] fitnessValues = new double[NUM_ANTS];

            for (int ant = 0; ant < NUM_ANTS; ant++) {
                selectedIndices[ant] = selectIndex(rand);
                double x = xPoints[selectedIndices[ant]];
                fitnessValues[ant] = objectiveFunction(x);

                if (fitnessValues[ant] < bestFx) {
                    bestFx = fitnessValues[ant];
                    bestX = x;
                }
            }

            evaporatePheromones();
            depositPheromones(selectedIndices, fitnessValues);

            System.out.printf("Iteration %2d - Best x: %.2f, f(x): %.4f%n", iter + 1, bestX, bestFx);
        }

        System.out.println("\nBest found solution:");
        System.out.printf("x = %.2f, f(x) = %.4f%n", bestX, bestFx);
    }

    public static double objectiveFunction(double x) {
        return Math.pow((x - 3), 5) - Math.pow(x,3) + Math.pow((x + 2),2) + 3*x +1;
    }

    public static int selectIndex(Random rand) {
        double sum = 0;
        for (double p : pheromones) sum += p;

        double r = rand.nextDouble() * sum;
        double cumulative = 0;

        for (int i = 0; i < pheromones.length; i++) {
            cumulative += pheromones[i];
            if (r <= cumulative) return i;
        }
        return pheromones.length - 1;
    }

    static void evaporatePheromones() {
        for (int i = 0; i < pheromones.length; i++) {
            pheromones[i] *= (1 - RHO);
        }
    }

    static void depositPheromones(int[] selectedIndices, double[] fitnessValues) {
        for (int i = 0; i < selectedIndices.length; i++) {
            int index = selectedIndices[i];
            double fitness = fitnessValues[i];

            double deltaTau = Q / (fitness + 1e-6);
            pheromones[index] += deltaTau;
        }
    }
}
