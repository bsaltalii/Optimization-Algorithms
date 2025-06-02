package ParticleSwarmOptimization;

import java.util.Random;
class Particle {
    int[] position;
    int[] bestPosition;
    double[] velocity;
    int bestFitness;

    public Particle(int dim) {
        position = new int[dim];
        bestPosition = new int[dim];
        velocity = new double[dim];
        bestFitness = 0;
    }
}

public class SimplePSO {
    static final int dim = 2;
    static final int numParticles = 10;
    static final int maxIter = 100;

    static final int[] area = {5, 13};
    static final int[] profit = {5, 10};
    static final int maxArea = 60;

    static final double c1 = 2.0;
    static final double c2 = 2.0;

    public static void main(String[] args) {
        Random rand = new Random();
        Particle[] swarm = new Particle[numParticles];
        int[] gBest = new int[dim];
        int gBestFit = 0;

        for (int i = 0; i < numParticles; i++) {
            swarm[i] = new Particle(dim);

            do {
                for (int j = 0; j < dim; j++) {
                    swarm[i].position[j] = rand.nextInt(5) + 1;
                    swarm[i].velocity[j] = 0;
                    swarm[i].bestPosition[j] = swarm[i].position[j];
                }
            } while (!isFeasible(swarm[i].position));

            swarm[i].bestFitness = fitness(swarm[i].position);
        }

        System.out.println("Starting Points:");
        for (int i = 0; i < numParticles; i++) {
            System.out.print("Particle " + (i + 1) + ": ");
            for (int j = 0; j < dim; j++) {
                System.out.print(swarm[i].position[j] + " ");
            }
            System.out.println();
        }

        for (int iter = 0; iter < maxIter; iter++) {
            for (int i = 0; i < numParticles; i++) {
                int currentFitness = fitness(swarm[i].position);

                if (currentFitness > swarm[i].bestFitness && isFeasible(swarm[i].position)) {
                    swarm[i].bestFitness = currentFitness;
                    swarm[i].bestPosition = swarm[i].position.clone();
                }

                if (currentFitness > gBestFit && isFeasible(swarm[i].position)) {
                    gBestFit = currentFitness;
                    gBest = swarm[i].position.clone();
                }
            }

            System.out.println("Iteration " + (iter + 1) + " - Best profit: " + gBestFit);

            for (int i = 0; i < numParticles; i++) {
                for (int j = 0; j < dim; j++) {
                    double r1 = rand.nextDouble();
                    double r2 = rand.nextDouble();

                    swarm[i].velocity[j] = swarm[i].velocity[j]
                            + c1 * r1 * (swarm[i].bestPosition[j] - swarm[i].position[j])
                            + c2 * r2 * (gBest[j] - swarm[i].position[j]);

                    swarm[i].position[j] += (int) Math.round(swarm[i].velocity[j]);
                    if (swarm[i].position[j] < 0) swarm[i].position[j] = 0;
                }

                if (!isFeasible(swarm[i].position)) {
                    for (int j = 0; j < dim; j++) {
                        swarm[i].position[j] = rand.nextInt(5) + 1;
                        swarm[i].velocity[j] = 0;
                    }
                }
            }
        }

        System.out.print("\nBest Solution: ");
        for (int i = 0; i < dim; i++) {
            System.out.print(gBest[i] + " ");
        }
        System.out.println("\nTotal best profit: " + gBestFit);
    }

    static int fitness(int[] x) {
        int sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * profit[i];
        }
        return sum;
    }

    static boolean isFeasible(int[] x) {
        int areaSum = 0, total = 0;
        for (int i = 0; i < x.length; i++) {
            areaSum += x[i] * area[i];
            total += x[i];
        }
        for (int i = 0; i < x.length; i++) {
            if (x[i] > total / 2.0) return false;
        }
        return areaSum <= maxArea;
    }
}
