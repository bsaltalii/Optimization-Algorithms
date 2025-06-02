package HillClimb;

import java.util.Random;

public class SumToZero_HC {
    static int size=10;
    static int lowerBound = -30;
    static int upperBound = 30;
    static int maxIteration = 1000;
    static Random rnd = new Random();

    public static int getRandomNumber(int min,int max){
        return (rnd.nextInt(max - min +1) + min);
    }

    public static int fitness(int[] solution){
        int sum=0;
        for (int val : solution){
            sum+=val;
        }
        return Math.abs(sum);
    }

    public static int[] createInitialSolution(){
        int[] solution = new int[size];
        for (int i=0;i<solution.length;i++){
            solution[i] = getRandomNumber(lowerBound,upperBound);
        }
        return solution;
    }

    public static int[] movement(int[] solution){
        int[] newSolution = solution.clone();
        int randomIndex = rnd.nextInt(solution.length);
        int change = getRandomNumber(-5,5);
        newSolution[randomIndex] += change;
        newSolution[randomIndex] = Math.max(lowerBound,Math.min(upperBound,newSolution[randomIndex]));
        return newSolution;
    }

    public static void main(String[] args) {
        int[] currentSolution = createInitialSolution();
        int currentFitness = fitness(currentSolution);
        boolean improved = true;
        int iteration = 0;

        while (improved && iteration < maxIteration){
            improved=false;
            int[] candidateSolution = movement(currentSolution);
            int candidateFitness = fitness(candidateSolution);

            if (candidateFitness < currentFitness){
                currentSolution = candidateSolution;
                currentFitness=candidateFitness;
                improved=true;
                System.out.println(" Iteration : "+(iteration+1) + " Fitness : "+currentFitness);
            }
            iteration++;
        }
        System.out.println("\nFinal fitness : "+ currentFitness);
        System.out.print("Best Solution : ");
        for (int element : currentSolution){
            System.out.print(element+" ");
        }
    }
}
