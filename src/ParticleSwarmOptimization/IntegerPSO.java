package ParticleSwarmOptimization;

import java.util.Random;

public class IntegerPSO {
    static final int DIM = 3;
    static final int numParticles = 20;
    static final int maxIter = 100;
    static final int[] area = {3,5,7};
    static final int[] profit = {10,20,30};
    static final int maxArea = 400;

    static final double c1 = 2;
    static final double c2 = 2;

    public static void main(String[] args) {
        Random rnd = new Random();
        int[][] position = new int[numParticles][DIM];
        int[][] pBest = new int[numParticles][DIM];
        int[] gBest = new int[DIM];
        double[][] velocity = new double[numParticles][DIM];

        double gBestFit = 0;

        for (int i=0; i < numParticles; i++){
            do {
                for (int j = 0; j < DIM; j++){
                    position[i][j] = rnd.nextInt(5) + 1;
                    pBest[i][j] = position[i][j];
                    velocity[i][j] = 0;
                }
            }while (!isFeasible(position[i]));
        }

        for (int iter=0; iter<maxIter; iter++){
            for (int i=0; i < numParticles; i++){
                int fit = fitness(position[i]);
                int pBestFit = fitness(pBest[i]);

                if (fit>pBestFit && isFeasible(position[i])){
                    pBest[i] = position[i].clone();
                }
                if (fit > gBestFit && isFeasible(position[i])){
                    gBest = position[i].clone();
                    gBestFit = fit;
                }
            }

            System.out.println("Iter " + (iter+1) + " - Best Profit: " + gBestFit);

            for (int i = 0; i < numParticles; i++) {
                for (int j = 0; j < DIM; j++) {
                    double r1 = rnd.nextDouble();
                    double r2 = rnd.nextDouble();

                    velocity[i][j] = velocity[i][j]
                            + c1 * r1 * (pBest[i][j] - position[i][j])
                            + c2 * r2 * (gBest[j] - position[i][j]);

                    position[i][j] += (int) Math.round(velocity[i][j]);
                    if (position[i][j] < 0) position[i][j] = 0;
                }

                if (!isFeasible(position[i])) {
                    for (int j = 0; j < DIM; j++) {
                        position[i][j] = rnd.nextInt(5) + 1;
                        velocity[i][j] = 0;
                    }
                }
            }
        }

        System.out.print("\nBest Solution Found: ");
        for (int i = 0; i < DIM; i++) {
            System.out.print(gBest[i] + " ");
        }
        System.out.println("\nMax Profit: " + gBestFit);
    }

    static int fitness(int[] x){
        int sum = 0;
        for (int i=0; i < x.length; i++){
            sum += x[i] * profit[i];
        }
        return sum;
    }

    static boolean isFeasible(int[] x){
        int areaSum = 0, total = 0;
        for (int i = 0; i < x.length; i++){
            areaSum += x[i] * area[i];
            total += x[i];
        }
        for (int i = 0; i < x.length; i++){
            if (x[i] > total / 2) return false;
        }
        return areaSum <= maxArea;
    }
}
