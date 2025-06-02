package ThresholdAccepting;

import java.util.Random;

public class ThresholdAcceptingExample_TA {
    public static double threshold = 100;
    public static double rate= 0.95;
    public static int maxIteration = 100;
    public static int size = 10;
    public static int lowerBound = -30;
    public static int upperBound = 30;

    public static Random rnd = new Random();

    public static int getRandomNumber(int lowerBound,int upperBound){
        return (rnd.nextInt(upperBound - lowerBound +1) + lowerBound);
    }

    public static int fitness(int[] initialSolution){
        int sum = 0;
        for (int element : initialSolution){
            sum+=element;
        }
        return Math.abs(sum);
    }

    public static int[] createInitialSolution(){
        int[] initialSolution = new int[size];
        for (int i = 0; i <initialSolution.length;i++ ){
            initialSolution[i] = getRandomNumber(-30,30);
        }
        return initialSolution;
    }

    public static int[] movement(int[] initialSolution){
        int[] newSolution = initialSolution.clone();
        int randomIndex = rnd.nextInt(initialSolution.length);
        int change = getRandomNumber(-5,5);
        newSolution[randomIndex] += change;
        newSolution[randomIndex] = Math.max(lowerBound, Math.min(upperBound, newSolution[randomIndex]));
        return newSolution;
    }

    public static void main(String[] args) {
        int[] currentSolution = createInitialSolution();
        int currentFitness = fitness(currentSolution);

        for(int i=0;i<maxIteration;i++){
            int[] neighborSolution = movement(currentSolution);
            int neighborFitness = fitness(neighborSolution);

            int delta = neighborFitness - currentFitness;

            if (delta <= threshold){
                currentSolution = neighborSolution;
                currentFitness = neighborFitness;
            }
            threshold*=rate;
            System.out.println("Iteration : "+ (i+1) + " Fitness : "+currentFitness);
        }
        System.out.println("Final Fitness : "+currentFitness);
        System.out.println("Best Solution : ");
        for (int element : currentSolution){
            System.out.print(element +" ");
        }
    }
}
