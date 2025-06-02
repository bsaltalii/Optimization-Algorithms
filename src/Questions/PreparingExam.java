package Questions;

import java.util.Random;

public class PreparingExam {
    static Random rnd = new Random();

    public static int fitness(int[] array){
        int sum=0;
        for (int element : array){
            sum += Math.abs((element*element) - (3*element) + 2);
        }
        return sum;
    }

    public static int[] movement(int[] array){
        int[] copy = array.clone();
        int index = rnd.nextInt(array.length);
        copy[index] += rnd.nextInt(5 -(-5)+1) -5;
        copy[index] = Math.max(-50,Math.min(50,copy[index]));
        return copy;
    }
}
