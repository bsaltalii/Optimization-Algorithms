package Tasks;

import java.util.*;

public class Task4 {
    static int dimension = 5;
    static int pop_size = 20;
    static int max_generation = 1000;
    static double crossoverRate = 0.8;
    static double mutationRate = 0.2;
    static double lower = -5;
    static double upper = 5;
    static Random rnd = new Random();

    public static double fitness(double[] x) {
        double sum = 0;
        for (int i = 0; i < x.length - 1; i++) {
            sum += (100 * Math.pow((x[i + 1] - x[i] * x[i]), 2) - Math.pow((1 - x[i]), 2));
        }
        return sum;
    }

    public static double getRandomNumber(double min, double max) {
        return rnd.nextDouble((max - min) + 1) + min;
    }

    public static double[] getRandomRow() {
        double[] row = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            row[i] = getRandomNumber(lower, upper);
        }
        return row;
    }

    public static double[][] getInitialSolution() {
        double[][] pop = new double[pop_size][dimension];
        for (int i = 0; i < pop_size; i++) {
            pop[i] = getRandomRow();
        }
        return pop;
    }

    public static double[] singlePointCrossover(double[] p1, double[] p2) {
        double[] child = new double[dimension];
        int point = rnd.nextInt(dimension);
        for (int i = 0; i < dimension; i++) {
            if (i < point)
                child[i] = p1[i];
            else
                child[i] = p2[i];
        }
        return child;
    }

    public static void mutation(double[] row) {
        for (int i = 0; i < dimension; i++) {
            if (rnd.nextDouble() < mutationRate) {
                row[i] += getRandomNumber(-5, 5);
                row[i] = Math.max(lower, Math.min(upper, row[i]));
            }
        }
    }

    public static double[][] tournamentSelection(double[][] population, int numSelected) {
        List<double[]> popList = new ArrayList<>(Arrays.asList(population));
        List<double[]> selected = new ArrayList<>();

        while (selected.size() < numSelected) {
            int i = rnd.nextInt(popList.size());
            int j = rnd.nextInt(popList.size());
            while (j == i) j = rnd.nextInt(popList.size());

            double[] p1 = popList.get(i);
            double[] p2 = popList.get(j);

            double[] winner = (fitness(p1) < fitness(p2)) ? p1 : p2;
            selected.add(winner);
            popList.remove(winner);
        }

        return selected.toArray(new double[0][]);
    }

    public static void main(String[] args) {
        double[][] population = getInitialSolution();
        double[] best = population[0];
        double bestFitness = fitness(best);

        int generation = 0;

        while (generation < max_generation) {
            double[][] selected = tournamentSelection(population, pop_size - 10);
            double[][] newPop = new double[pop_size][dimension];

            for (int i = 0; i < selected.length; i++) {
                double[] parent1 = selected[rnd.nextInt(selected.length)];
                double[] parent2 = selected[rnd.nextInt(selected.length)];
                double[] child;

                if (rnd.nextDouble() < crossoverRate) {
                    child = singlePointCrossover(parent1, parent2);
                } else {
                    child = parent1.clone();
                }

                mutation(child);
                newPop[i] = child;

                double childFitness = fitness(child);
                if (childFitness < bestFitness) {
                    bestFitness = childFitness;
                    best = child;
                    System.out.println("Gen " + generation + " Best fitness: " + bestFitness);
                }
            }

            for (int i = pop_size - 10; i < pop_size; i++) {
                newPop[i] = population[rnd.nextInt(pop_size)].clone();
            }

            population = newPop;
            generation++;
        }

        System.out.println("\nFinal best fitness: " + bestFitness);
        System.out.println("Best solution:");
        System.out.println(Arrays.toString(best));
    }
}