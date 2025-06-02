package LocalSearch;

import java.util.Random;

public class Knapsack_LS {
    static class Item{
        int value,weight;
        public Item(int value,int weight){
            this.value=value;
            this.weight=weight;
        }
    }
    public static int knapsackCapacity=100;
    public static Random rnd = new Random();

    public static boolean[] generateInitialSolution(Item[] items){
        boolean[] solution = new boolean[items.length];
        int currentWeight = 0;

        for(int i=0;i<items.length;i++){
            if(currentWeight + items[i].weight <= knapsackCapacity){
                solution[i] = true;
                currentWeight += items[i].weight;
            }
        }
        return solution;
    }

    public static int calculateValue(boolean[] solution , Item[] items){
        int total = 0;

        for(int i=0;i<solution.length;i++){
            if(solution[i]){
                total+=items[i].value;
            }
        }
        return total;
    }

    public static int calculateWeight(boolean[] solution , Item[] items){
        int total = 0;

        for(int i=0;i<solution.length;i++){
            if(solution[i]){
                total+=items[i].weight;
            }
        }
        return total;
    }

    public static boolean[] getNeighbor(boolean[] solution, Item[] items){
        boolean[] newSolution = solution.clone();
        int index = rnd.nextInt(items.length);

        newSolution[index] = !newSolution[index];

        while (calculateWeight(newSolution,items)>knapsackCapacity){
            int removeIndex = rnd.nextInt(items.length);
            newSolution[removeIndex]=false;
        }
        return newSolution;
    }

    public static boolean[] localSearch(Item[] items){
        boolean[] currentSolution = generateInitialSolution(items);
        int currentValue = calculateValue(currentSolution,items);

        for(int i=0;i<1000;i++){
            boolean[] neighborSolution = getNeighbor(currentSolution,items);
            int neighborValue = calculateValue(currentSolution,items);

            if(neighborValue>currentValue){
                currentSolution=neighborSolution;
                currentValue=neighborValue;
            }
        }
        return currentSolution;
    }

    public static void main(String[] args) {
        Item[] items = {
                new Item(60, 10),
                new Item(100, 20),
                new Item(120, 30),
                new Item(90, 25),
                new Item(75, 15),
                new Item(50, 12),
                new Item(110, 22),
                new Item(130, 35),
                new Item(95, 28),
                new Item(80, 18),
                new Item(65, 14),
                new Item(85, 24),
                new Item(105, 26),
                new Item(140, 40),
                new Item(55, 9)
        };

        boolean[] bestSolution = localSearch(items);
        System.out.println("Best Solution:");
        for (int i = 0; i < bestSolution.length; i++) {
            if (bestSolution[i]) {
                System.out.println("Item " + (i + 1) + " - Value: " + items[i].value + ", Weight: " + items[i].weight);
            }
        }
        System.out.println("Total Value: " + calculateValue(bestSolution, items));
        System.out.println("Total Weight: " + calculateWeight(bestSolution, items));
    }
}


