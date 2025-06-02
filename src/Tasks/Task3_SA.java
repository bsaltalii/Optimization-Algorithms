package Tasks;
import java.util.Random;

public class Task3_SA {
    static double T = 200;
    static final int SIZE = 14;
    static final int MAX_ITERATION = 1000;
    static final double ALPHA = 0.99;
    static final Random rnd = new Random();

    static final double[][] matrix = {
            {16.47, 96.10}, {16.47, 94.44}, {20.09, 92.54}, {22.39, 93.37},
            {25.23, 97.24}, {22.00, 96.05}, {20.47, 97.02}, {17.20, 96.29},
            {16.30, 97.38}, {14.05, 98.12}, {16.53, 97.38}, {21.52, 95.59},
            {19.41, 97.13}, {20.04, 95.55}
    };

    static double[] createInitialSolution() {
        double[] solution = new double[SIZE];
        for (int i = 0; i < SIZE; i++)
            solution[i] = i;
        return shuffle(solution);
    }

    static double[] shuffle(double[] arr) {
        double[] copy = arr.clone();
        for (int i = 0; i < 100; i++) {
            int i1 = rnd.nextInt(SIZE);
            int i2 = rnd.nextInt(SIZE);
            double temp = copy[i1];
            copy[i1] = copy[i2];
            copy[i2] = temp;
        }
        return copy;
    }

    static double distance(int i1, int i2) {
        double dx = matrix[i1][0] - matrix[i2][0];
        double dy = matrix[i1][1] - matrix[i2][1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    static double fitness(double[] solution) {
        double total = 0;
        for (int i = 0; i < solution.length - 1; i++) {
            total += distance((int)solution[i], (int)solution[i + 1]);
        }
        total += distance((int)solution[solution.length - 1], (int)solution[0]); // return to start
        return total;
    }

    static double[] movement(double[] current) {
        double[] neighbor = current.clone();
        int i1 = rnd.nextInt(SIZE);
        int i2 = rnd.nextInt(SIZE);
        double temp = neighbor[i1];
        neighbor[i1] = neighbor[i2];
        neighbor[i2] = temp;
        return neighbor;
    }

    public static void main(String[] args) {
        double[] currentSolution = createInitialSolution();
        double currentFitness = fitness(currentSolution);

        System.out.println("Initial Fitness: " + currentFitness);

        for (int iteration = 0; iteration < MAX_ITERATION && T > 1; iteration++) {
            double[] newSolution = movement(currentSolution);
            double newFitness = fitness(newSolution);
            double delta = newFitness - currentFitness;

            if (delta < 0 || Math.exp(-delta / T) > rnd.nextDouble()) {
                currentSolution = newSolution;
                currentFitness = newFitness;
            }

            System.out.printf("Iteration: %-4d Fitness: %.4f\n", iteration + 1, currentFitness);
            T *= ALPHA;
        }

        System.out.println("\nFinal Fitness: " + currentFitness);
        System.out.print("Best Route (City Indices): ");
        for (double city : currentSolution) {
            System.out.print((int) city + " ");
        }
    }
}

//distance iki node arasındaki mesafeyi hesapla distance hesaplandıktan sonra bşr de fitness fonksiyonu hesapla bir tane
//current soluiton(1'den 14'e kadar nodeların ismi olacak mesela 10 14 20) fitness fonksiyonu bu current solutiondaki toplam yolu hesaplayacak
//en sonda da bir de başa dönmesi lazım movement iki index belirleyip swap edecek