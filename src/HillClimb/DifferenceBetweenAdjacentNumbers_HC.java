package HillClimb;

import java.util.Random;

public class DifferenceBetweenAdjacentNumbers_HC {
    static int size = 10;
    static int lowerBound = -30;
    static int upperBound = 30;
    static int maxIteration = 1000;
    static Random rnd = new Random();

    public static int getRandomNumber(int min , int max){
        return (rnd.nextInt(max - min +1) + min);
    }
    public static int[] createInitialSolution(){
        int[] solution = new int[size];
        for (int i=0;i<solution.length;i++){
            solution[i] = getRandomNumber(lowerBound,upperBound);
        }
        return solution;
    }
    public static int fitness(int[] solution){
        int sum=0;
        for(int i=0;i<solution.length-1;i++){
            sum += Math.abs(solution[i] - solution[i+1]);
        }
        return sum;
    }

    public static int[] movement(int[] solution){
        int[] newSolution = solution.clone();
        int index = rnd.nextInt(size);
        int change = getRandomNumber(-5,5);
        newSolution[index] += change;
        newSolution[index] = Math.max(lowerBound,Math.min(upperBound,newSolution[index]));
        return newSolution;
    }

    public static void main(String[] args) {
        int[] currentSolution = createInitialSolution();
        int currentFitness = fitness(currentSolution);

        boolean improved = true;
        int iteration=0;

        while (improved && iteration <maxIteration){
            improved=false;

            int[] newSolution = movement(currentSolution);
            int newFitness = fitness(newSolution);

            if (newFitness < currentFitness){
                currentSolution = newSolution;
                currentFitness = newFitness;
                improved=true;
                System.out.println("Iteration : " + (iteration+1) + " Fitness : "+ currentFitness);
            }
            iteration++;
        }

        System.out.println("\nFinal Fitness : "+currentFitness);
        System.out.print("Best solution : ");
        for (int element : currentSolution){
            System.out.print(element + " ");
        }
    }
}
