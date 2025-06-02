package TabuSearch;

import java.util.*;

public class SumToZero_TabuSearch {
    static int size = 10;
    static int lowerBound = -30;
    static int upperBound = 30;
    static int maxIteration = 1000;
    static int tabuTenure = 5;

    static Random rnd = new Random();

    static int getRandomNumber(int min, int max) {
        return rnd.nextInt(max - min + 1) + min;
    }

    static int fitness(int[] solution) {
        int sum = 0;
        for (int val : solution) sum += val;
        return Math.abs(sum);
    }

    static int[] createInitialSolution() {
        int[] solution = new int[size];
        for (int i = 0; i < size; i++) {
            solution[i] = getRandomNumber(lowerBound, upperBound);
        }
        return solution;
    }

    public static void main(String[] args) {
        int[] currentSolution = createInitialSolution();
        int currentFitness = fitness(currentSolution);

        int[] bestSolution = currentSolution.clone();
        int bestFitness = currentFitness;

        int[] tabuList = new int[size];

        for (int iter = 0; iter < maxIteration; iter++) {
            int[] bestNeighbor = null;
            int bestNeighborFitness = Integer.MAX_VALUE;
            int changedIndex = -1;
            int changeValue = 0;

            for (int i = 0; i < size; i++) {
                if (tabuList[i] > 0) continue;

                for (int delta = -5; delta <= 5; delta++) {
                    if (delta == 0) continue;

                    int[] neighbor = currentSolution.clone();
                    neighbor[i] += delta;
                    neighbor[i] = Math.max(lowerBound, Math.min(upperBound, neighbor[i]));

                    int neighborFitness = fitness(neighbor);

                    if (neighborFitness < bestNeighborFitness) {
                        bestNeighbor = neighbor;
                        bestNeighborFitness = neighborFitness;
                        changedIndex = i;
                        changeValue = delta;
                    }
                }
            }

            if (bestNeighbor == null) break;

            currentSolution = bestNeighbor;
            currentFitness = bestNeighborFitness;

            tabuList[changedIndex] = tabuTenure;

            if (currentFitness < bestFitness) {
                bestFitness = currentFitness;
                bestSolution = currentSolution.clone();
                System.out.println("Iteration " + iter + " New Best Fitness: " + bestFitness);
            }

            for (int i = 0; i < size; i++) {
                if (tabuList[i] > 0) tabuList[i]--;
            }
        }

        System.out.println("\nFinal Fitness: " + bestFitness);
        System.out.print("Best Solution: ");
        for (int val : bestSolution) System.out.print(val + " ");
    }
}

