package TwoOpt;

import java.util.Random;

public class TwoOptTSP {

    static int N = 6;
    static int[][] distances = {
            {  0, 20, 42, 35, 12, 25 },
            { 20,  0, 30, 34, 23, 20 },
            { 42, 30,  0, 10, 40, 25 },
            { 35, 34, 10,  0, 30, 15 },
            { 12, 23, 40, 30,  0, 10 },
            { 25, 20, 25, 15, 10,  0 }
    };

    static int calculateRouteDistance(int[] route) {
        int distance = 0;
        for (int i = 0; i < N - 1; i++) {
            distance += distances[route[i]][route[i + 1]];
        }
        distance += distances[route[N - 1]][route[0]];
        return distance;
    }

    static void twoOptSwap(int[] route, int i, int k) {
        while (i < k) {
            int temp = route[i];
            route[i] = route[k];
            route[k] = temp;
            i++;
            k--;
        }
    }

    static int[] twoOpt(int[] route) {
        int[] bestRoute = route.clone();
        int bestDistance = calculateRouteDistance(bestRoute);
        boolean improvement = true;

        while (improvement) {
            improvement = false;
            for (int i = 1; i < N - 2; i++) {
                for (int k = i + 1; k < N; k++) {
                    int[] newRoute = bestRoute.clone();
                    twoOptSwap(newRoute, i, k);
                    int newDistance = calculateRouteDistance(newRoute);

                    if (newDistance < bestDistance) {
                        bestRoute = newRoute.clone();
                        bestDistance = newDistance;
                        improvement = true;
                    }
                }
            }
        }

        return bestRoute;
    }

    public static void main(String[] args) {
        int[] route = {0, 1, 2, 3, 4, 5};

        System.out.println("Initial Distance: " + calculateRouteDistance(route));

        int[] optimizedRoute = twoOpt(route);

        System.out.println("Optimized Distance: " + calculateRouteDistance(optimizedRoute));
        System.out.print("Optimized Route: ");
        for (int city : optimizedRoute) {
            System.out.print(city + " ");
        }
        System.out.println();
    }
}
