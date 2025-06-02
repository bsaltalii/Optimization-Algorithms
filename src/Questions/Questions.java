package Questions;

import java.util.Random;

public class Questions {
    static Random rnd = new Random();
    static int UPPERBOUND = 30;
    static int LOWERBOUND = -30;
    static int MAX_ITERATION = 1000;

    public static double fitness(double[] array){
        double sum = 0;
        for (int i = 0; i < array.length - 1; i++) {
            sum += 100 * Math.pow(array[i + 1] - array[i] * array[i], 2) + Math.pow(array[i] - 1, 2);
        }
        return sum;
    }

    public static double[] movement(double[] solution){
        double[] newSolution = solution.clone();
        int index = rnd.nextInt(solution.length);
        double randNum = -5 + (10 * rnd.nextDouble());
        newSolution[index] += randNum;
        newSolution[index] = Math.max(LOWERBOUND, Math.min(UPPERBOUND, newSolution[index]));
        return newSolution;
    }

    public static double[] getInitialSolution(){
        double[] initialSolution = new double[5];
        for (int i=0;i<initialSolution.length;i++){
            initialSolution[i] = LOWERBOUND + (UPPERBOUND - LOWERBOUND) * rnd.nextDouble();
        }
        return initialSolution;
    }

    public static void printSolution(double[] arr){
        for (double element : arr){
            System.out.print(element+" ");
        }
    }

    public static void main(String[] args) {
        double[] currentSolution = getInitialSolution();
        double currentFitness = fitness(currentSolution);

        double[] bestSolution = currentSolution;
        double bestFitness = currentFitness;

        for (int i=0; i<MAX_ITERATION; i++){
            double[] neighbor = movement(currentSolution);
            double neighborFitness = fitness(neighbor);

            if (neighborFitness < currentFitness){
                currentSolution=neighbor;
                currentFitness=neighborFitness;

                if (currentFitness < bestFitness){
                    bestSolution = currentSolution;
                    bestFitness=currentFitness;

                    System.out.println("New best found at : "+i);
                    printSolution(bestSolution);
                    System.out.println("Fitness : "+bestFitness);
                }
            }
        }
        System.out.println("Final best solution + ");
        printSolution(bestSolution);
        System.out.println("Final fitness : "+ bestFitness);
    }
}
