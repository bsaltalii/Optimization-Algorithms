package GeneticAlgorithm;

import java.util.Arrays;
import java.util.Random;

public class TSPwithGeneticAlgorithm {
    static final int SIZE = 14;
    static final int POP_SIZE = 100;
    static final int MAX_GEN = 500;
    static final double MUTATION_RATE = 0.1;
    static final boolean ELITISM = true;
    static Random rnd = new Random();

    static final double[][] matrix = {
            {16.47, 96.10}, {16.47, 94.44}, {20.09, 92.54}, {22.39, 93.37},
            {25.23, 97.24}, {22.00, 96.05}, {20.47, 97.02}, {17.20, 96.29},
            {16.30, 97.38}, {14.05, 98.12}, {16.53, 97.38}, {21.52, 95.59},
            {19.41, 97.13}, {20.04, 95.55}
    };

    public static void main(String[] args) {
        int[][] population = new int[POP_SIZE][SIZE];
        for (int i = 0; i < POP_SIZE; i++) {
            population[i] = generateInitialTour();
        }

        int[] bestIndividual = population[0];
        double bestFitness = fitness(bestIndividual);

        for (int gen = 0; gen < MAX_GEN; gen++) {
            //Arrays.sort(population, (a, b) -> Double.compare(fitness(a), fitness(b)));

            for (int i = 0; i < population.length - 1; i++) {
                for (int j = i + 1; j < population.length; j++) {
                    double fitnessI = fitness(population[i]);
                    double fitnessJ = fitness(population[j]);

                    if (fitnessJ < fitnessI) {
                        int[] temp = population[i];
                        population[i] = population[j];
                        population[j] = temp;
                    }
                }
            }
            if (fitness(population[0]) < bestFitness) {
                bestFitness = fitness(population[0]);
                bestIndividual = population[0].clone();
                System.out.println("Generation " + gen + " | Best Fitness: " + bestFitness);
            }

            int[][] newPopulation = new int[POP_SIZE][SIZE];

            if (ELITISM) newPopulation[0] = bestIndividual.clone();

            for (int i = ELITISM ? 1 : 0; i < POP_SIZE; i++) {
                int[] parent1 = tournamentSelection(population);
                int[] parent2 = tournamentSelection(population);

                int[] child = singlePointCrossover(parent1, parent2);

                if (rnd.nextDouble() < MUTATION_RATE) {
                    mutate(child);
                }

                newPopulation[i] = child;
            }

            population = newPopulation;
        }

        System.out.println("\nFinal best fitness: " + bestFitness);
        System.out.println("Best tour: " + Arrays.toString(bestIndividual));
    }

    public static int[] generateInitialTour() {
        int[] tour = new int[SIZE];
        for (int i = 0; i < SIZE; i++) tour[i] = i;

        for (int i = SIZE - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int temp = tour[i];
            tour[i] = tour[j];
            tour[j] = temp;
        }
        return tour;
    }

    public static double distance(int i, int j) {
        double dx = matrix[i][0] - matrix[j][0];
        double dy = matrix[i][1] - matrix[j][1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double fitness(int[] tour) {
        double total = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            total += distance(tour[i], tour[i + 1]);
        }
        total += distance(tour[tour.length - 1], tour[0]);
        return total;
    }

    public static int[] singlePointCrossover(int[] parent1, int[] parent2) {
        int point = rnd.nextInt(SIZE);
        int[] child = new int[SIZE];
        Arrays.fill(child, -1);

        for (int i = 0; i < point; i++) {
            child[i] = parent1[i];
        }

        int index = point;
        for (int val : parent2) {
            if (!contains(child, val)) {
                child[index++] = val;
            }
        }

        return child;
    }

    public static boolean contains(int[] arr, int value) {
        for (int j : arr) {
            if (j == value) return true;
        }
        return false;
    }

    public static void mutate(int[] tour) {
        int i = rnd.nextInt(SIZE);
        int j = rnd.nextInt(SIZE);
        int temp = tour[i];
        tour[i] = tour[j];
        tour[j] = temp;
    }

    public static int[] tournamentSelection(int[][] population) {
        int i1 = rnd.nextInt(POP_SIZE);
        int i2 = rnd.nextInt(POP_SIZE);
        return fitness(population[i1]) < fitness(population[i2]) ? population[i1] : population[i2];
    }
}
