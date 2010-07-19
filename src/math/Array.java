package math;

import java.util.Arrays;

/**
 * Надає методи для роботи з масивами.
 * @author Eduard Bakhlov
 * @version 1.0
 */
public class Array {

    /**
     * Сортування за зростанням.
     * Якщо передаються масиви як список, то кожен із них буде відсортований за
     * зростанням.
     * Якщо передається двовимірний масив, то буде відсортовано елементи за зростанням
     * у кожному з рядків.
     * <pre>
     *  int[][] m = {{2, 1}, {891, 4, 7}, {-4, 5, 34}};
     *  int[] a = {2, -3};
     *  int[] b = {34, -3, 234};
     *
     *  math.Array.sort(a, b);
     *  math.Array.sort(m);

     *  math.Console.out("a", a);
     *  math.Console.out("b", b);
     *  math.Console.out("m", m);
     * </pre>
     * @param matrix список масивів, які необхідно відсортувати
     */
    public static void sort(int[]... matrix) {
        for (int[] m : matrix) {
            sort(m);
        }
    }

    /**
     * Сортування за зростанням.
     * Якщо передаються масиви як список, то кожен із них буде відсортований за
     * зростанням.
     * Якщо передається двовимірний масив, то буде відсортовано елементи за зростанням
     * у кожному з рядків.
     * <pre>
     *  double[][] m = {{2, 1}, {891, 4, 7}, {-4, 5, 34}};
     *  double[] a = {2, -3};
     *  double[] b = {34, -3, 234};
     *
     *  math.Array.sort(a, b);
     *  math.Array.sort(m);

     *  math.Console.out("a", a);
     *  math.Console.out("b", b);
     *  math.Console.out("m", m);
     * </pre>
     * @param matrix список масивів, які необхідно відсортувати
     */
    public static void sort(double[]... matrix) {
        for (double[] m : matrix) {
            sort(m);
        }
    }

    /**
     * Сортування за спаданням.
     * Якщо передаються масиви як список, то кожен із них буде відсортований за
     * спаданням.
     * Якщо передається двовимірний масив, то буде відсортовано елементи за спаданням
     * у кожному з рядків.
     * <pre>
     *  int[][] m = {{2, 1}, {891, 4, 7}, {-4, 5, 34}};
     *  int[] a = {2, -3};
     *  int[] b = {34, -3, 234};
     *
     *  math.Array.inverseSort(a, b);
     *  math.Array.inverseSort(m);

     *  math.Console.out("a", a);
     *  math.Console.out("b", b);
     *  math.Console.out("m", m);
     * </pre>
     * @param matrix список масивів, які необхідно відсортувати
     */
    public static void inverseSort(int[]... matrix) {
        for (int[] m : matrix) {
            sort(m);
            swap(m);
        }
    }

    /**
     * Сортування за зростанням.
     * Якщо передаються масиви як список, то кожен із них буде відсортований за
     * зростанням.
     * Якщо передається двовимірний масив, то буде відсортовано елементи за зростанням
     * у кожному з рядків.
     * <pre>
     *  double[][] m = {{2, 1}, {891, 4, 7}, {-4, 5, 34}};
     *  double[] a = {2, -3};
     *  double[] b = {34, -3, 234};
     *
     *  math.Array.inverseSort(a, b);
     *  math.Array.inverseSort(m);

     *  math.Console.out("a", a);
     *  math.Console.out("b", b);
     *  math.Console.out("m", m);
     * </pre>
     * @param matrix список масивів, які необхідно відсортувати
     */
    public static void inverseSort(double[]... matrix) {
        for (double[] m : matrix) {
            sort(m);
            swap(m);
        }
    }

    /**
     * Сортування за зростанням.
     * @param array вихідний масив
     */
    public static void sort(int... array) {
        Arrays.sort(array);
    }

    /**
     * Сорування за зростанням.
     * @param array вихідний масив
     */
    public static void sort(double... array) {
        Arrays.sort(array);
    }

    /**
     * Сортування за спаданням.
     * @param array вихідний масив
     */
    public static void inverseSort(int... array) {
        sort(array);
        swap(array);
    }

    /**
     * Сортування за спаданням.
     * @param array вихідний масив
     */
    public static void inverseSort(double... array) {
        sort(array);
        swap(array);
    }

    /**
     * Перестановка елементів в зворотному порядку.
     * @param array вихідний масив
     */
    public static void swap(int... array) {
        int buf = 0;
        for (int i = 0; i < array.length / 2; i++) {
            buf = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = buf;
        }
    }

    /**
     * Перестановка елементів в зворотному порядку.
     * @param array вихідний масив
     */
    public static void swap(double... array) {
        double buf = 0;
        for (int i = 0; i < array.length / 2; i++) {
            buf = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = buf;
        }
    }

    /**
     * Значення максимального елементу.
     * @param array вихідний масив
     * @return значення максимального елементу
     */
    public static int max(int... array) {
        return array[maxIndex(array)];
    }

    /**
     * Значення максимального елементу.
     * @param array вихідний масив
     * @return значення максимального елементу
     */
    public static double max(double... array) {
        return array[maxIndex(array)];
    }

    /**
     * Індекс максимального елементу.
     * @param array вихідний масив
     * @return індекс максимального елементу
     */
    public static int maxIndex(int... array) {
        int min = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[min]) {
                min = i;
            }
        }
        return min;
    }

    /**
     * Індекс максимального елементу.
     * @param array вихідний масив
     * @return індекс максимального елементу
     */
    public static int maxIndex(double... array) {
        int min = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[min]) {
                min = i;
            }
        }
        return min;
    }

    /**
     * Значення мінімального елементу.
     * @param array вихідний масив
     * @return значення мінімального елементу
     */
    public static int min(int... array) {
        return array[minIndex(array)];
    }

    /**
     * Значення мінімального елементу.
     * @param array вихідний масив
     * @return значення мінімального елементу
     */
    public static double min(double... array) {
        return array[minIndex(array)];
    }

    /**
     * Індекс мінімального елементу.
     * @param array вихідний масив
     * @return індекс мінімального елементу
     */
    public static int minIndex(int... array) {
        int min = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[min]) {
                min = i;
            }
        }
        return min;
    }

    /**
     * Індекс мінімального елементу.
     * @param array вихідний масив
     * @return індекс мінімального елементу
     */
    public static int minIndex(double... array) {
        int min = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[min]) {
                min = i;
            }
        }
        return min;
    }

    /**
     * Знаходження найбільшої різниці між елементами.
     * @param array вихідний масив
     * @return значення максимальної різниці
     */
    public static int maxDifference(int... array) {
        int max = Math.abs(array[0] - array[1]);

        for (int j = 0; j < array.length; j++) {
            for (int i = j + 1; i < array.length; i++) {
                int m = Math.abs(array[j] - array[i]);
                if (m > max) {
                    max = m;
                }
            }
        }
        return max;
    }

    /**
     * Знаходження найбільшої різниці між елементами.
     * @param array вихідний масив
     * @return значення максимальної різниці
     */
    public static double maxDifference(double... array) {
        double max = Math.abs(array[0] - array[1]);

        for (int j = 0; j < array.length; j++) {
            for (int i = j + 1; i < array.length; i++) {
                double m = Math.abs(array[j] - array[i]);
                if (m > max) {
                    max = m;
                }
            }
        }
        return max;
    }

    /**
     * Знаходження найменшої різниці між елементами.
     * @param array вихідний масив
     * @return значення мінімальної різниці
     */
    public static int minDifference(int... array) {
        int max = Math.abs(array[0] - array[1]);

        for (int j = 0; j < array.length; j++) {
            for (int i = j + 1; i < array.length; i++) {
                int m = Math.abs(array[j] - array[i]);
                if (m < max) {
                    max = m;
                }
            }
        }
        return max;
    }

    /**
     * Знаходження найменшої різниці між елементами.
     * @param array вихідний масив
     * @return значення мінімальної різниці
     */
    public static double minDifference(double... array) {
        double max = Math.abs(array[0] - array[1]);

        for (int j = 0; j < array.length; j++) {
            for (int i = j + 1; i < array.length; i++) {
                double m = Math.abs(array[j] - array[i]);
                if (m < max) {
                    max = m;
                }
            }
        }
        return max;
    }

    /**
     * Знаходження суми елементів масиву.
     * @param array вихідний масив
     * @return сума
     */
    public static int sum(int... array) {
        int s = 0;
        for (int a : array) {
            s += a;
        }
        return s;
    }

    /**
     * Знаходження суми елементів масиву.
     * @param array вихідний масив
     * @return сума
     */
    public static double sum(double... array) {
        double s = 0;
        for (double a : array) {
            s += a;
        }
        return s;
    }

    /**
     * Знаходження середнього значення
     * @param array вихідний масив
     * @return середнє значення
     */
    public static double average(int... array) {
        return (double) sum(array) / array.length;
    }

    /**
     * Знаходження середнього значення
     * @param array вихідний масив
     * @return середнє значення
     */
    public static double average(double... array) {
        return sum(array) / array.length;
    }

    /**
     * Поелементне додавання.
     * @param a перший масив
     * @param b другий масив
     * @return результуючий масив
     */
    public static int[] summate(int[] a, int[] b) {
        int[] res = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i] + b[i];
        }

        return res;
    }

    /**
     * Поелементне додавання.
     * @param a перший масив
     * @param b другий масив
     * @return результуючий масив
     */
    public static double[] summate(double[] a, double[] b) {
        double[] res = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i] + b[i];
        }
        return res;
    }

    /**
     * Додавання до кожного елементу масиву числа.
     * @param array вихідний масив
     * @param value число, яке додаємо
     * @return новий масив
     */
    public static int[] summate(int[] array, int value) {
        int[] res = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i] + value;
        }
        return res;
    }

    /**
     * Додавання до кожного елементу масиву числа.
     * @param array вихідний масив
     * @param value число, яке додаємо
     * @return новий масив
     */
    public static double[] summate(double[] array, double value) {
        double[] res = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i] + value;
        }
        return res;
    }

    /**
     * Віднімання від кожного елементу масиву числа.
     * @param array вихідний масив
     * @param value число, яке віднімаємо
     * @return новий масив
     */
    public static int[] subtract(int[] array, int value) {
        int[] res = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i] - value;
        }
        return res;
    }

    /**
     * Віднімання від кожного елементу масиву числа.
     * @param array вихідний масив
     * @param value число, яке віднімаємо
     * @return новий масив
     */
    public static double[] subtract(double[] array, double value) {
        double[] res = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i] - value;
        }
        return res;
    }

    /**
     * Множення вектора на число
     * @param array вихідний вектор
     * @param value число на яке множиться
     * @return результуючий вектор
     */
    public static int[] multiply(int[] array, int value) {
        int[] res = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i] * value;
        }
        return res;
    }

    /**
     * Множення вектора на число
     * @param array вихідний вектор
     * @param value число на яке множиться
     * @return результуючий вектор
     */
    public static double[] multiply(double[] array, double value) {
        double[] res = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i] * value;
        }
        return res;
    }

    /**
     * Поелементне ділення на число.
     * @param array вихідний масив
     * @param value число на яке ділимо
     * @return рузультуючий масив
     */
    public static double[] division(double[] array, double value) {
        double[] res = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i] / value;
        }
        return res;
    }

    /**
     * Виконує поелементне множення масивів.
     * @param a перший масив
     * @param b другий масив
     * @return результуючий масив
     */
    public static int[] multiply(int[] a, int[] b) {
        int[] res = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i] * b[i];
        }
        return res;
    }

    /**
     * Виконує поелементне множення масивів.
     * @param a перший масив
     * @param b другий масив
     * @return результуючий масив
     */
    public static double[] multiply(double[] a, double[] b) {
        double[] res = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i] * b[i];
        }
        return res;
    }

    /**
     * Поелементне піднесення до степеня.
     * @param array вихідний масив
     * @param stepin степінь до якої підносити
     * @return результуючий масив
     */
    public static int[] pow(int[] array, int stepin) {
        int[] res = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = (int) Math.pow(array[i], stepin);
        }
        return res;
    }

    /**
     * Поелементне піднесення до степеня.
     * @param array вихідний масив
     * @param stepin степінь до якої підносити
     * @return результуючий масив
     */
    public static double[] pow(int[] array, double stepin) {
        double[] res = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = Math.pow(array[i], stepin);
        }
        return res;
    }

    /**
     * Поелементне піднесення до степеня.
     * @param array вихідний масив
     * @param stepin степінь до якої підносити
     * @return результуючий масив
     */
    public static double[] pow(double[] array, double stepin) {
        double[] res = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = Math.pow(array[i], stepin);
        }
        return res;
    }

    /**
     * Перевірка на рівність двох масивів.
     * @param a перший масив
     * @param b другий масив
     * @return true - якщо рівні, інакше -  false.
     */
    public static boolean isEqual(int[] a, int[] b) {
        return Arrays.equals(a, b);
    }

    /**
     * Перевірка на рівність двох масивів.
     * @param a перший масив
     * @param b другий масив
     * @return true - якщо рівні, інакше -  false.
     */
    public static boolean isEqual(double[] a, double[] b) {
        return Arrays.equals(a, b);
    }

    /**
     * Повертає масив місць за якими розподілені величини.
     * Найбільше число займає 1 місце. Менше число - 2 місце.
     * Найменше - останнє місце.
     * @param array вихідний масив
     * @return масив місць за якими розподілені величини
     */
    public static int[] getPlaces(double[] array) {
        int[] p = new int[array.length];
        double[] sort = new double[array.length];
        System.arraycopy(array, 0, sort, 0, array.length);
        Array.inverseSort(sort);
        for (int i = 0; i < array.length; i++) {
            p[i] = 1 + getIndex(sort, array[i]);
        }
        return p;
    }

    /**
     * Повертає індекс числа в масиві за значенням.
     * @param a вихідний масив
     * @param value число, індекс якого шукаємо
     * @return індекс числа в масиві, якщо таке присутнє в ньому, інакше повертає -1
     */
    private static int getIndex(double[] a, double value) {
        for (int i = 0; i < a.length; i++) {
            if (Value.diff(a[i], value, 0.0001)) {
                return i;
            }
        }
        return -1;
    }
}
