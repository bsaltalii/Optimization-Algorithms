package SimulatedAnnealing;

import java.util.Random;

public class SumToZero_SA {
    public static double T = 100;
    public static double alpha = 0.99;
    public static int maxIteration = 1000;
    public static int size = 10;
    public static int lowerBound = -30;
    public static int upperBound = 30;

    public static Random rnd = new Random();

    public static int getRandomNumber(int lowerBound,int upperBound){
        return rnd.nextInt(upperBound-lowerBound+1) +lowerBound;
    }
    public static int fitness(int[] solution){
        int sum=0;
        for (int i : solution) {
            sum += i;
        }
        return Math.abs(sum);
    }
    public static int[] createInitialSolution(){
        int[] initialSolution = new int[size];
        for (int i=0;i<initialSolution.length;i++){
            initialSolution[i] = getRandomNumber(lowerBound,upperBound);
        }
        return initialSolution;
    }

    public static int[] movement(int[] initialSolution){
        int[] newSolution = initialSolution.clone();
        int randomIndex = rnd.nextInt(size);
        newSolution[randomIndex] += getRandomNumber(-5,5);
        newSolution[randomIndex] = Math.max(lowerBound,Math.min(upperBound,newSolution[randomIndex]));
        return newSolution;
    }

    public static void main(String[] args) {
        int[] currentSolution = createInitialSolution();
        int currentFitness = fitness(currentSolution);

        int iteration=0;
        while (iteration<maxIteration && T>1){
            int[] newSolution = movement(currentSolution);
            int newFitness = fitness(newSolution);

            double delta = newFitness-currentFitness;

            if(delta < 0 || Math.exp(-delta/T) > rnd.nextDouble()){
                currentSolution = newSolution;
                currentFitness = newFitness;
            }

            T*=alpha;

            System.out.println("Iteration: " + (iteration+1) + " Fitness: " + currentFitness);
            iteration++;
        }

        System.out.println("\nFinal fitness: " + currentFitness);
        System.out.println("Best solution found:");
        for (int x : currentSolution) {
            System.out.printf("%d ", x);
        }
    }
}
