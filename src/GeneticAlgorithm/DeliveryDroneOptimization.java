package GeneticAlgorithm;

import java.util.Random;

public class DeliveryDroneOptimization {
     static double[][] coordinates = {
            {10.5, 20.2},
            {14.7, 25.3},
            {20.0, 18.4},
            {30.2, 22.5},
            {35.1, 10.9},
            {40.4, 17.3},
            {28.6, 12.0},
            {22.1, 30.3},
            {15.0, 35.0},
            {38.4, 33.2},
            {42.0, 25.6},
            {26.5, 5.5}
    };

    static boolean[] isRequired = {
            true, true, true, true,
            true, true, true, true,
            false, false, false, false
    };

    static int REQUIRED = 8;
    static int OPTIONAL = 4;
    static Random rnd = new Random();

    public static double fitness(int[] tour){
        double distanceSum = 0;
        for (int i=0; i<tour.length-1;i++){
            distanceSum += distance(tour[i],tour[i+1]);
        }
        distanceSum += distance(tour[tour.length-1],tour[0]);

        int numberOfBonusVisited = 0;
        for(int j = 0; j < tour.length; j++){
            if (!isRequired[tour[j]]){
                numberOfBonusVisited++;
            }
        }
        return (distanceSum - (numberOfBonusVisited*10));
    }
    public static double distance(int i, int j){
        double dx = coordinates[i][0] - coordinates[j][0];
        double dy = coordinates[i][1] - coordinates[j][1];
        return Math.sqrt(dx * dx + dy * dy);
    }
//    public static int[] createInitialTour(){
//        int[] tour = new int[REQUIRED];
//        for (int i = 0; i < tour.length; i++){
//            tour[i] = i;
//        }
//    }
}
