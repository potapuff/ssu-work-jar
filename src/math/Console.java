package math;

/**
 * Надає методи для виведення різноманітних даних в консоль.
 *
 * @author Eduard Bakhlov
 * @version 1.1
 */
public class Console {

    protected Console() {
    }

    /**
     * Виведення в консоль бінарного числа.
     * @param name назва змінної
     * @param value true або false
     */
    public static void out(String name, boolean value) {
        out(name + ": " + value);
    }

    /**
     * Виведення в консоль цілого числа.
     * @param name назва змінної
     * @param value число
     */
    public static void out(String name, int value) {
        out(name + ": " + value);
    }

    /**
     * Виведення в консоль дійсного числа.
     * @param name назва змінної
     * @param value число
     */
    public static void out(String name, double value) {
        out(name + ": " + value);
    }

    /**
     * Виведення в консоль одновимірного масиву бінарних чисел.
     * @param name назва масиву
     * @param array масив
     */
    public static void out(String name, boolean... array) {
        outThis(name + ": ");

        for (boolean elem : array) {
            outThis(elem + "  ");
        }
        out();
    }

    /**
     * Виведення в консоль одновимірного масиву цілих чисел.
     * @param name назва масиву
     * @param array масив
     */
    public static void out(String name, int... array) {
        outThis(name + ": ");

        for (int elem : array) {
            outThis(elem + "  ");
        }
        out();
    }

    /**
     * Виведення в консоль одновимірного масиву дійсних чисел.
     * @param name назва масиву
     * @param array масив
     */
    public static void out(String name, double... array) {
        outThis(name + ": ");

        for (double elem : array) {
            outThis(elem + "  ");
        }
        out();
    }

    /**
     * Виведення в консоль двовимірного масиву бінарних чисел.
     * @param name назва масиву
     * @param array масив
     */
    public static void out(String name, boolean[][] array) {
        out(name + ": ");
        for (boolean[] row : array) {
            for (boolean elem : row) {
                outThis(elem + "  ");
            }
            out();
        }
        out();
    }

    /**
     * Виведення в консоль двовимірного масиву цілих чисел.
     * @param name назва масиву
     * @param array масив
     */
    public static void out(String name, int[][] array) {
        out(name + ": ");
        for (int[] row : array) {
            for (int elem : row) {
                outThis(elem + "  ");
            }
            out();
        }
        out();
    }

    /**
     * Виведення в консоль двовимірного масиву дійних чисел.
     * @param name назва масиву
     * @param array масив
     */
    public static void out(String name, double[][] array) {
        out(name + ": ");
        for (double[] row : array) {
            for (double elem : row) {
                outThis(elem + "  ");
            }
            out();
        }
        out();
    }

    /**
     * Виведення в консоль рядка.
     * @param name назва рядка
     * @param value рядок
     */
    public static void out(String name, String value) {
        out(name + ": " + value);
    }

    /**
     * Виведення в консоль масиву рядків.
     * @param name назва рядка
     */
    public static void out(String name, String... values) {
        outThis(name + ": ");

        for (String value : values) {
            outThis(value + "  ");
        }
        out();
    }

    /**
     * Виведення в консоль пустого рядка.
     */
    public static void out() {
        out("");
    }

    /**
     * Виведення в консоль рядка.
     *  @param str рядок
     */
    public static void out(String str) {
        System.out.println(str);
    }

    /**
     * Виведення в консоль без переносу на новий рядок.
     * @param str
     */
    protected static void outThis(String str) {
        System.out.print(str);
    }
}
