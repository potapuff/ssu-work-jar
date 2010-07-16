package math;

/**
 * Надає методи для округлення.
 * @author Eduard Bakhlov
 * @version 1.0
 */
public class Round {

    /**
     * Округлення числа до n-го знаку.
     * @param value вихідне число
     * @param n кількість знаків після точки
     * @return округлене число
     */
    public static double round(double value, int n) {
        return (double) Math.round(value * Math.pow(10, n)) / Math.pow(10, n);
    }

    /**
     * Округлення всіх елементів масиву до n-го знаку.
     * @param array вихідний масив
     * @param n кількість знаків після точки
     */
    public static void round(double[] array, int n) {
        for (int i = 0; i < array.length; i++) {
            array[i] = round(array[i], n);
        }
    }

    public static void round(double[][] array, int n) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = round(array[i][j], n);
            }
        }
    }
}
