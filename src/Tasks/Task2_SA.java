package Tasks;

import java.util.Random;

public class Task2_SA {
    static double T = 100;
    static int size = 10;
    static int maxIteration = 1000;
    static double alpha = 0.99;
    static Random rnd = new Random();

    static double[] createInitialSolution(int size, double lowerBound, double upperBound) {
        double[] initialSolution = new double[size];
        for (int i = 0; i < size; i++) {
            initialSolution[i] = lowerBound + (upperBound - lowerBound) * rnd.nextDouble();
        }
        return initialSolution;
    }

    static double fitness(double[] x) {
        double sum = 0;
        for (double value : x) {
            sum += Math.abs(Math.pow(value + 0.5, 2));
        }
        return sum;
    }

    static double[] movement(double[] x, double lowerBound, double upperBound) {
        double[] newSolution = x.clone();
        int index = rnd.nextInt(x.length);
        double randomNumber = -5 + (10 * rnd.nextDouble());
        newSolution[index] += randomNumber;
        newSolution[index] = Math.max(lowerBound, Math.min(upperBound, newSolution[index]));  // clamping
        return newSolution;
    }

    public static void main(String[] args) {
        double lowerBound = -30;
        double upperBound = 30;

        double[] currentSolution = createInitialSolution(size, lowerBound, upperBound);
        double currentFitness = fitness(currentSolution);

        System.out.println("Initial fitness: " + currentFitness);

        int iteration = 0;

        while (iteration < maxIteration && T > 1) {
            double[] newSolution = movement(currentSolution, lowerBound, upperBound);
            double newFitness = fitness(newSolution);

            double delta = newFitness - currentFitness;

            if (delta < 0 || Math.exp(-delta / T) > rnd.nextDouble()) {
                currentSolution = newSolution;
                currentFitness = newFitness;
            }

            System.out.println("Iteration: " + (iteration + 1) + " Fitness: " + currentFitness);

            T *= alpha;
            iteration++;
        }

        System.out.println("\nFinal fitness: " + currentFitness);
        System.out.print("Best solution found: ");
        for (int i = 0; i < currentSolution.length; i++) {
            System.out.printf("%.3f", currentSolution[i]);
            if (i < currentSolution.length - 1) System.out.print(", ");
        }
    }
}
