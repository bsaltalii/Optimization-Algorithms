package LocalSearch;

import java.util.*;

public class EmployeeScheduling_LS {
    static int numEmployees = 5;
    static int numDays = 7;
    static int minWorkDays = 3;
    static int maxWorkDays = 5;
    static int minEmployeesPerDay = 2;
    static Random rand = new Random();

    public static int[][] generateInitialSchedule() {
        int[][] schedule = new int[numEmployees][numDays];
        for (int i = 0; i < numEmployees; i++) {
            int workDays = rand.nextInt((maxWorkDays - minWorkDays) + 1) + minWorkDays;
            List<Integer> assignedDays = new ArrayList<>();
            while (assignedDays.size() < workDays) {
                int day = rand.nextInt(numDays);
                if (!assignedDays.contains(day)) {
                    assignedDays.add(day);
                    schedule[i][day] = 1;
                }
            }
        }
        return schedule;
    }

    public static int[] calculateWorkload(int[][] schedule) {
        int[] workload = new int[numDays];
        for (int d = 0; d < numDays; d++) {
            for (int i = 0; i < numEmployees; i++) {
                workload[d] += schedule[i][d];
            }
        }
        return workload;
    }

    public static boolean isValidSchedule(int[][] schedule) {
        int[] workload = calculateWorkload(schedule);
        for (int d = 0; d < numDays; d++) {
            if (workload[d] < minEmployeesPerDay) return false;
        }
        return true;
    }

    public static int[][] getNeighbor(int[][] schedule) {
        int[][] newSchedule = Arrays.stream(schedule).map(int[]::clone).toArray(int[][]::new);
        int emp = rand.nextInt(numEmployees);
        int removeDay = rand.nextInt(numDays);
        int addDay = rand.nextInt(numDays);

        if (newSchedule[emp][removeDay] == 1) newSchedule[emp][removeDay] = 0;
        newSchedule[emp][addDay] = 1;

        return newSchedule;
    }

    public static int[][] localSearch(int[][] schedule) {
        int[][] bestSchedule = schedule;
        for (int i = 0; i < 1000; i++) {
            int[][] neighbor = getNeighbor(bestSchedule);
            if (isValidSchedule(neighbor)) {
                bestSchedule = neighbor;
            }
        }
        return bestSchedule;
    }
    public static void main(String[] args) {
        int[][] initialSchedule = generateInitialSchedule();
        System.out.println("Initial Schedule:");
        printSchedule(initialSchedule);

        int[][] optimizedSchedule = localSearch(initialSchedule);
        System.out.println("\nOptimized Schedule:");
        printSchedule(optimizedSchedule);
    }

    public static void printSchedule(int[][] schedule) {
        System.out.println("Days:  1  2  3  4  5  6  7");
        for (int i = 0; i < numEmployees; i++) {
            System.out.print("Emp " + (i + 1) + ": ");
            for (int d = 0; d < numDays; d++) {
                System.out.print(schedule[i][d] + "  ");
            }
            System.out.println();
        }
    }
}

