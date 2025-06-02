package ParticleSwarmOptimization;

import java.util.Random;

public class ParticleSwarmOptimization {

    static final int DIM = 2;
    static final int NUM_PARTICLES = 30;
    static final int MAX_ITER = 100;

    static final double MIN_POS = -10;
    static final double MAX_POS = 10;

    static final double w = 0.7;
    static final double c1 = 1.5;
    static final double c2 = 1.5;

    static class Particle {
        double[] position = new double[DIM];
        double[] velocity = new double[DIM];
        double[] bestPosition = new double[DIM];
        double bestValue = Double.MAX_VALUE;
    }

    static double evaluate(double[] pos) {
        double sum = 0;
        for (double x : pos) {
            sum += x * x;
        }
        return sum;
    }

    public static void main(String[] args) {
        Random rand = new Random();
        Particle[] swarm = new Particle[NUM_PARTICLES];

        double[] globalBest = new double[DIM];
        double globalBestValue = Double.MAX_VALUE;

        for (int i = 0; i < NUM_PARTICLES; i++) {
            swarm[i] = new Particle();
            for (int d = 0; d < DIM; d++) {
                swarm[i].position[d] = MIN_POS + (MAX_POS - MIN_POS) * rand.nextDouble();
                swarm[i].velocity[d] = (MAX_POS - MIN_POS) * (rand.nextDouble() - 0.5);
                swarm[i].bestPosition[d] = swarm[i].position[d];
            }
            swarm[i].bestValue = evaluate(swarm[i].position);
            if (swarm[i].bestValue < globalBestValue) {
                globalBestValue = swarm[i].bestValue;
                globalBest = swarm[i].position.clone();
            }
        }

        for (int iter = 0; iter < MAX_ITER; iter++) {
            for (Particle p : swarm) {

                for (int d = 0; d < DIM; d++) {
                    double r1 = rand.nextDouble();
                    double r2 = rand.nextDouble();

                    p.velocity[d] = w * p.velocity[d]
                            + c1 * r1 * (p.bestPosition[d] - p.position[d])
                            + c2 * r2 * (globalBest[d] - p.position[d]);
                }

                for (int d = 0; d < DIM; d++) {
                    p.position[d] += p.velocity[d];

                    if (p.position[d] < MIN_POS) p.position[d] = MIN_POS;
                    if (p.position[d] > MAX_POS) p.position[d] = MAX_POS;
                }

                double newValue = evaluate(p.position);
                if (newValue < p.bestValue) {
                    p.bestValue = newValue;
                    p.bestPosition = p.position.clone();
                }

                if (newValue < globalBestValue) {
                    globalBestValue = newValue;
                    globalBest = p.position.clone();
                }
            }

            if (iter % 10 == 0 || iter == MAX_ITER - 1) {
                System.out.printf("Iteration %d - Best Value: %.6f\n", iter, globalBestValue);
            }
        }

        System.out.println("\nFinal Best Value: " + globalBestValue);
        System.out.print("Best Position: ");
        for (double val : globalBest) {
            System.out.printf("%.4f ", val);
        }
        System.out.println();
    }
}
