package IteratedLocalSearch;

import java.util.Random;

public class SumToZero_ILS {
    static int size = 10;
    static int lowerBound = -30;
    static int upperBound = 30;
    static int maxIteration = 1000;
    static Random rnd = new Random();

    public static int getRandomNumber(int min, int max) {
        return rnd.nextInt(max - min + 1) + min;
    }

    public static int fitness(int[] solution) {
        int sum = 0;
        for (int val : solution) {
            sum += val;
        }
        return Math.abs(sum);
    }

    public static int[] createInitialSolution() {
        int[] solution = new int[size];
        for (int i = 0; i < size; i++) {
            solution[i] = getRandomNumber(lowerBound, upperBound);
        }
        return solution;
    }

    public static int[] localSearch(int[] solution) {
        int[] current = solution.clone();
        int currentFitness = fitness(current);
        boolean improved = true;
        int maxLocalIter = 100;

        while (improved && maxLocalIter-- > 0) {
            improved = false;
            int[] neighbor = movement(current);
            int neighborFitness = fitness(neighbor);

            if (neighborFitness < currentFitness) {
                current = neighbor;
                currentFitness = neighborFitness;
                improved = true;
            }
        }
        return current;
    }

    public static int[] perturbation(int[] solution) {
        int[] newSolution = solution.clone();
        int changes = 2 + rnd.nextInt(3);
        for (int i = 0; i < changes; i++) {
            int index = rnd.nextInt(size);
            int delta = getRandomNumber(-10, 10);
            newSolution[index] += delta;
            newSolution[index] = Math.max(lowerBound, Math.min(upperBound, newSolution[index]));
        }
        return newSolution;
    }

    public static int[] movement(int[] solution) {
        int[] newSolution = solution.clone();
        int index = rnd.nextInt(size);
        int delta = getRandomNumber(-5, 5);
        newSolution[index] += delta;
        newSolution[index] = Math.max(lowerBound, Math.min(upperBound, newSolution[index]));
        return newSolution;
    }

    public static void main(String[] args) {
        int[] current = createInitialSolution();
        current = localSearch(current);
        int currentFitness = fitness(current);

        int[] best = current.clone();
        int bestFitness = currentFitness;

        for (int iter = 0; iter < maxIteration; iter++) {
            int[] perturbed = perturbation(current);
            int[] localOptimum = localSearch(perturbed);
            int localFitness = fitness(localOptimum);

            if (localFitness < currentFitness) {
                current = localOptimum;
                currentFitness = localFitness;

                if (localFitness < bestFitness) {
                    best = localOptimum.clone();
                    bestFitness = localFitness;
                    System.out.println("Iteration " + iter + " -> New Best Fitness: " + bestFitness);
                }
            }
        }

        System.out.println("\nFinal Fitness: " + bestFitness);
        System.out.print("Best Solution: ");
        for (int val : best) {
            System.out.print(val + " ");
        }
    }
}
