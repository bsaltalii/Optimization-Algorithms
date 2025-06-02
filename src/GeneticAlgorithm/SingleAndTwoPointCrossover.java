package GeneticAlgorithm;

import java.util.Random;

public class SingleAndTwoPointCrossover {
    static Random rnd = new Random();
    public static int[] singlePointCrossover(int[] parent1 , int[] parent2){
        int length = parent1.length;
        int[] child = new int[length];


        int crossoverPoint = rnd.nextInt(length);

        for (int i = 0; i < length; i++){
            if (i <= crossoverPoint){
                child[i] = parent1[i];
            }else{
                child[i] = parent2[i];
            }
        }
        return child;
    }

    public static int[] twoPointCrossover(int[] parent1 , int[] parent2){
        int length = parent1.length;
        int[] child = new int[length];

        int point1 = rnd.nextInt(length);
        int point2 = rnd.nextInt(length);

        if (point1 > point2){
            int temp = point1;
            point1 = point2;
            point2 = temp;
        }

        for (int i = 0; i < length; i++){
            if (i >= point1 && i <= point2){
                child[i] = parent2[i];
            }else {
                child[i] = parent1[i];
            }
        }
        return child;
    }

    public static void printArray(int[] array){
        System.out.print("[");
        for (int element : array){
            System.out.print(element + " ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static void main(String[] args) {
        int[] parent1 = {1,2,3,4,5,6,7,8};
        int[] parent2 = {8,7,6,5,4,3,2,1};

        System.out.println("Parent 1: ");
        printArray(parent1);
        System.out.println("Parent 2: ");
        printArray(parent2);

        int[] child1 = singlePointCrossover(parent1,parent2);
        System.out.println("Child 1: ");
        printArray(child1);

        int[] child2 = twoPointCrossover(parent1,parent2);
        System.out.println("Child 2: ");
        printArray(child2);
    }
}
