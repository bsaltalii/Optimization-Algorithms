package LocalSearch;

import java.util.ArrayList;
import java.util.Random;

public class AssignmentProblem_LS {
    static int[][] costMatrix = {
            {90, 76, 75, 70, 50, 74, 12, 68, 50, 90},
            {35, 85, 55, 65, 48, 50, 12, 60, 52, 60},
            {125, 95, 90, 105, 59, 63, 50, 85, 75, 65},
            {45, 110, 95, 115, 104, 83, 37, 71, 69, 76},
            {60, 105, 80, 75, 59, 62, 93, 88, 77, 90},
            {45, 65, 110, 95, 47, 31, 81, 34, 53, 72},
            {40, 70, 95, 90, 36, 49, 64, 60, 75, 85},
            {65, 85, 104, 95, 45, 61, 52, 70, 67, 80},
            {50, 55, 70, 85, 60, 59, 55, 61, 72, 95},
            {45, 65, 80, 95, 51, 50, 45, 65, 60, 80}
    };
    static int jobs = 10;
    static int worker = 10;
    static int iteration = 1000;
    static Random rnd = new Random();

    public static int fitness(boolean[][] assignment){
        int sum=0;
        for (int i=0;i<jobs;i++){
            for (int j=0;j<worker;j++){
                if (assignment[i][j]){
                    sum+=costMatrix[i][j];
                }
            }
        }
        return sum;
    }
    public static boolean[][] createInitialSolution() {
        boolean[][] assignment = new boolean[worker][jobs];
        ArrayList<Integer> jobIndices = new ArrayList<>();

        for (int i = 0; i < jobs; i++) {
            jobIndices.add(i);
        }

        java.util.Collections.shuffle(jobIndices);

        for (int i = 0; i < worker; i++) {
            assignment[i][jobIndices.get(i)] = true;
        }

        return assignment;
    }

    public static boolean[][] movement(boolean[][] assignment){
        return swapJobs(assignment);
    }
    public static boolean[][] swapJobs(boolean[][] assignment) {
        boolean[][] newAssignment = new boolean[worker][jobs];

        for (int i = 0; i < worker; i++) {
            System.arraycopy(assignment[i], 0, newAssignment[i], 0, jobs);
        }

        int worker1 = rnd.nextInt(worker);
        int worker2 = rnd.nextInt(worker);
        while (worker2 == worker1) {
            worker2 = rnd.nextInt(worker);
        }

        int job1 = -1, job2 = -1;
        for (int j = 0; j < jobs; j++) {
            if (newAssignment[worker1][j]) job1 = j;
            if (newAssignment[worker2][j]) job2 = j;
        }

        newAssignment[worker1][job1] = false;
        newAssignment[worker1][job2] = true;
        newAssignment[worker2][job2] = false;
        newAssignment[worker2][job1] = true;

        return newAssignment;
    }


    public static void printSolution(boolean[][] assignment){
        for(int i=0;i<jobs;i++){
            for(int j=0;j<worker;j++){
                if (!assignment[i][j]){
                    System.out.print("0 ");
                }else{
                    System.out.print("1 ");
                }
            }
            System.out.println(" ");
        }
    }

    public static void main(String[] args) {
        boolean[][] currentSolution = createInitialSolution();
        int currentFitness = fitness(currentSolution);

        boolean[][] bestSolution = currentSolution;
        int bestFitness = currentFitness;

        for (int i = 0; i < iteration; i++) {
            boolean[][] neighbor = movement(currentSolution);
            int neighborFitness = fitness(neighbor);

            if (neighborFitness < currentFitness) {
                currentSolution = neighbor;
                currentFitness = neighborFitness;

                if (currentFitness < bestFitness) {
                    bestSolution = currentSolution;
                    bestFitness = currentFitness;

                    System.out.println("New Best Found at Iteration " + i);
                    printSolution(bestSolution);
                    System.out.println("Fitness: " + bestFitness + "\n");
                }
            }
        }

        System.out.println("Final Best Solution:");
        printSolution(bestSolution);
        System.out.println("Final Fitness: " + bestFitness);
    }

}
