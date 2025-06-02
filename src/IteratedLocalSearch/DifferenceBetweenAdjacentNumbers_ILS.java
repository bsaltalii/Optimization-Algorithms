package IteratedLocalSearch;

import java.util.Random;

public class DifferenceBetweenAdjacentNumbers_ILS {
    public static int size = 10;
    public static int maxIteration = 1000;
    public static int lowerBound = -30;
    public static int upperBound = 30;
    public static Random rnd = new Random();

    public static int getRandomNumber(int min , int max){
        return (rnd.nextInt(max - min + 1) + min);
    }
    public static int fitness(int[] solution){
        int sum=0;
        for (int i=0;i<solution.length-1;i++){
            sum += Math.abs(solution[i] - solution[i+1]);
        }
        return sum;
    }
    public static int[] createInitialSolution(){
        int[] solution = new int[size];
        for (int i=0;i<size;i++){
            solution[i] = getRandomNumber(lowerBound,upperBound);
        }
        return solution;
    }

    public static int[] movement(int[] solution){
        int[] newSolution = solution.clone();
        int index = rnd.nextInt(size);
        int change = getRandomNumber(-5,5);
        newSolution[index] += change;
        newSolution[index] = Math.max(lowerBound,Math.min(upperBound,newSolution[index]));
        return newSolution;
    }

    public static int[] perturbation(int[] solution){
        int[] newSolution = solution.clone();
        int localIteration = getRandomNumber(2,4);
        for (int i=0;i<localIteration;i++){
            int index = rnd.nextInt(size);
            int change = getRandomNumber(-10,10);
            newSolution[index] += change;
            newSolution[index] = Math.max(lowerBound,Math.min(upperBound,newSolution[index]));
        }
        return newSolution;
    }

    public static int[] localSearch(int[] solution){
        int[] currentSolution = solution;
        int currentFitness = fitness(currentSolution);

        boolean improved = true;
        int iteration = 0;

        while (improved && iteration < maxIteration){
            improved=false;
            int[] newSolution = movement(solution);
            int newFitness = fitness(newSolution);

            if(newFitness < currentFitness){
                currentSolution=newSolution;
                currentFitness=newFitness;
                improved=true;
            }
            iteration++;
        }
        return currentSolution;
    }

    public static void main(String[] args) {
        int[] currentSolution = createInitialSolution();
        currentSolution = localSearch(currentSolution);
        int currentFitness = fitness(currentSolution);

        int[] bestSolution = currentSolution.clone();
        int bestFitness = fitness(bestSolution);

        for (int i=0;i<maxIteration;i++){
            int[] perturbed = perturbation(currentSolution);
            int[] localOptimum = localSearch(perturbed);
            int localFitness = fitness(localOptimum);

            if (localFitness<currentFitness){
                currentSolution = localOptimum;
                currentFitness = localFitness;

                if (localFitness < bestFitness){
                    bestSolution = localOptimum.clone();
                    bestFitness = localFitness;
                    System.out.println("Iteration : "+ (i+1) +" Fitness : "+ bestFitness);
                }
            }
        }
        System.out.println("\nFinal Fitness: " + bestFitness);
        System.out.print("Best Solution: ");
        for (int val : bestSolution) {
            System.out.print(val + " ");
        }
    }
}
