package Tasks;

import java.util.Random;

public class Task1_LS {
    static Random rand = new Random();

    static double fitness(double[] x) {
        double sum = 0;
        for (double value : x) {
            sum += value * value;
        }
        return sum;
    }

    static double[] movement(double[] x, double lowerBound, double upperBound) {
        double[] newSolution =new double[x.length];
        System.arraycopy(x, 0, newSolution, 0, x.length);
        int index = rand.nextInt(x.length);
        double randomNumber = -5 + (10 * rand.nextDouble());
        newSolution[index] += randomNumber;
        newSolution[index] = Math.max(lowerBound, Math.min(upperBound, newSolution[index]));
        return newSolution;
    }

    public static void main(String[] args) {
        int n = 10;                  
        int maxIteration = 1000;           
        double lowerBound = -100;
        double upperBound = 100;

        double[] currentSolution = new double[n];
        for (int i = 0; i < n; i++) {
            currentSolution[i] = lowerBound + (upperBound - lowerBound) * rand.nextDouble();
        }
        double currentFitness = fitness(currentSolution);

        System.out.println("Initial fitness: " + currentFitness);

        int iteration = 0;
        
        while (iteration < maxIteration) {
            double[] newSolution = movement(currentSolution, lowerBound, upperBound);
            double newFitness = fitness(newSolution);

            if (newFitness < currentFitness) {
                currentSolution = newSolution;
                currentFitness = newFitness;
            }
            System.out.println("\nIteration :"+iteration + " Fitness :" + currentFitness);
            iteration++;
        }
        System.out.println("Final fitness: " + currentFitness);
    }
}