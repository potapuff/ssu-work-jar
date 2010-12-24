package math;

public class Value {

    protected Value() {
    }

    /**
     * Порівняння двох чисел із абсолютною точністю.
     * @param a перше число
     * @param b друге число
     * @param eps точність
     * @return true - якщо рівні, інакше - false
     */
    public static boolean diff(double a, double b, double eps) {
        return Math.abs(a - b) < Math.abs(eps);
    }

    /**
     * Приведення дійсного числа до цілого.
     * @param value вихідне значення
     * @return цілу чатину від дійсного числа
     */
    public static int toInt(double value) {
        return Double.valueOf(value).intValue();
    }

    /**
     * Отримання дійсного числа із строки
     * @param str строка із числом дійсного типу
     * @return число дійсного типу
     */
    public static double toDouble(String str) {
        return Double.valueOf(str);
    }
}
