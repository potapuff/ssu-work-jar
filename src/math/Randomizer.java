package math;

import java.util.Random;

/**
 * Надає методи для генерування випадкових чисел.
 * @author Eduard Bakhlov
 * @version 1.0
 */
public class Randomizer {

    /**
     * Генерує випадкове число цілого типу в деякому діапазоні.
     * @param min нижня границя діапазону
     * @param max верхня границя діапазону
     * @return випадкове число
     */
    public static int getInt(int min, int max) {
        return getInt(min, max, 1);
    }

    /**
     * Генерує випадкове число цілого типу в деякому діапазоні із вказаним кроком.
     * @param min нижня границя діапазону
     * @param max верхня границя діапазону
     * @param step крок
     * @return випадкове число
     */
    public static int getInt(int min, int max, int step) {
        return min + new Random().nextInt((max - min) / step + 1) * step;
    }

    /**
     * Генерує випадкове число дійсного типу в деякому діапазоні із вказаним кроком.
     * @param min нижня границя діапазону
     * @param max верхня границя діапазону
     * @param step крок
     * @return випадкове число
     */
    public static double getDouble(double min, double max, double step) {
        return (double) getInt(
                Math.round((float) min * 10),
                1 + Math.round((float) max * 10),
                Math.round((float) step * 10)) / 10;
    }

    /**
     * Генерує масив цілих випадкових чисел
     * @param min нижня границя діапазону
     * @param max верхня границя діапазону
     * @param step крок
     * @param count кількість чисел, що необхідно згенерувати
     * @return масив цілих випадкових чисел
     */
    public static int[] getInt(int min, int max, int step, int count) {
        int[] a = new int[count];
        for (int i = 0; i < count; i++) {
            a[i] = getInt(min, max, step);
        }
        return a;
    }

    /**
     * Генерує масив дійсних випадкових чисел
     * @param min нижня границя діапазону
     * @param max верхня границя діапазону
     * @param step крок
     * @param count кількість чисел, що необхідно згенерувати
     * @return масив дійсних випадкових чисел
     */
    public static double[] getDouble(double min, double max, double step, int count) {
        double[] a = new double[count];
        for (int i = 0; i < count; i++) {
            a[i] = getDouble(min, max, step);
        }
        return a;
    }
}
