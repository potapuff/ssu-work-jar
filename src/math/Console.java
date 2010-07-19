package math;

/**
 * Надає методи для виведення різноманітних даних в консоль.
 *
 * @author Eduard Bakhlov
 * @version 1.0
 */
public class Console {

    /**
     * Виведення в консоль цілого числа.
     * @param name назва змінної
     * @param value число
     */
    public static void out(String name, int value) {
        System.out.println(name + ": " + value);
    }

    /**
     * Виведення в консоль дійсного числа.
     * @param name назва змінної
     * @param value число
     */
    public static void out(String name, double value) {
        System.out.println(name + ": " + value);
    }

    /**
     * Виведення в консоль одновимірного масиву цілих чисел.
     * @param name назва масиву
     * @param array масив
     */
    public static void out(String name, int[] array) {
        System.out.print(name + ": ");

        for (int elem : array) {
            System.out.print(elem + "  ");
        }
        System.out.println();
    }

    /**
     * Виведення в консоль одновимірного масиву дійсних чисел.
     * @param name назва масиву
     * @param array масив
     */
    public static void out(String name, double[] array) {
        System.out.print(name + ": ");

        for (double elem : array) {
            System.out.print(elem + "  ");
        }
        System.out.println();
    }

    /**
     * Виведення в консоль двовимірного масиву цілих чисел.
     * @param name назва масиву
     * @param array масив
     */
    public static void out(String name, int[][] array) {
        System.out.println(name + ": ");
        for (int[] row : array) {
            for (int elem : row) {
                System.out.print(elem + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Виведення в консоль двовимірного масиву дійних чисел.
     * @param name назва масиву
     * @param array масив
     */
    public static void out(String name, double[][] array) {
        System.out.println(name + ": ");
        for (double[] row : array) {
            for (double elem : row) {
                System.out.print(elem + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Виведення в консоль строки.
     * @param name назва строки
     * @param value строка
     */
    public static void out(String name, String value) {
        System.out.println(name + ":" + value);
    }
}
