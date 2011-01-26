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
     * Отримання дійсного числа з рядка
     * @param str строка із числом дійсного типу
     * @return число дійсного типу
     * @throws NumberFormatException якщо не можливо виділити число з рядка
     */
    public static double toDouble(String str) {
        return Double.valueOf(str);
    }

    /**
     * Ділення дійсний чисел
     * @param a ділене
     * @param b дільник
     * @return частка
     */
    public static double divide(double a, double b) {
        return a / b;
    }

    /**
     * Приведення відсотків до сотих доль одиниці
     * @param value відсоток %
     * @return соті долі
     */
    public static double fromPercent(double value) {
        return value / 100;
    }

    /**
     * Приведення сотих доль одниці до відсотків
     * @param value cоті доді
     * @return відсоток %
     */
    public static double toPercent(double value) {
        return value * 100;
    }

    /**
     * Визначення модуля дійсного чила
     * @param a дійсне цисло
     * @return модуль дійсного числа
     */
    public static double abs(double a) {
        return Math.abs(a);
    }

    /**
     * Визначення модуля цілого чила
     * @param a ціле цисло
     * @return модуль цілого числа
     */
    public static int abs(int a) {
        return Math.abs(a);
    }

//    /**
//     * Знаходження максимуму з двох цілих чмсел
//     * @param a перше чило
//     * @param b друге число
//     * @return максимальне число
//     */
//    public static int max(int a, int b) {
//        return Math.max(a, b);
//    }
//
//    /**
//     * Знаходження максимуму з двох дійсних чмсел
//     * @param a перше чило
//     * @param b друге число
//     * @return максимальне число
//     */
//    public static double max(double a, double b) {
//        return Math.max(a, b);
//    }

//    /**
//     * Знаходження мінімуму з двох цілих чмсел
//     * @param a перше чило
//     * @param b друге число
//     * @return мінімальне число
//     */
//    public static int min(int a, int b) {
//        return Math.min(a, b);
//    }
//
//    /**
//     * Знаходження мінімуму з двох дійсних чмсел
//     * @param a перше чило
//     * @param b друге число
//     * @return мінімальне число
//     */
//    public static double min(double a, double b) {
//        return Math.min(a, b);
//    }
    /**
     * Знаходження тригонометричного синусу кута
     * @param a кут у радіанах
     * @return синус аргументу
     */
    public static double sin(double a) {
        return Math.sin(a);
    }

    /**
     * Знаходження тригонометричного косинусу кута
     * @param a кут у радіанах
     * @return косинус аргументу
     */
    public static double cos(double a) {
        return Math.cos(a);
    }

    /**
     * Знаходження тригонометричного тангенса кута
     * @param a кут у радіанах
     * @return тангенса аргументу
     */
    public static double tg(double a) {
        return Math.tan(a);
    }

    /**
     * Знаходження тригонометричного арксинусу кута
     * @param a кут у радіанах
     * @return арксинус аргументу
     */
    public static double asin(double a) {
        return Math.asin(a);
    }

    /**
     * Знаходження тригонометричного арккосинусу кута
     * @param a кут у радіанах
     * @return арккосинус аргументу
     */
    public static double acos(double a) {
        return Math.acos(a);
    }

    /**
     * Знаходження тригонометричного арктангенса кута
     * @param a кут у радіанах
     * @return арктангенса аргументу
     */
    public static double atg(double a) {
        return Math.atan(a);
    }

    /**
     * Знаходження числа Ейлера е, піднесеноно да степені а
     * @param a степінь числа е
     * @return число Ейлера
     */
    public static double exp(double a) {
        return Math.exp(a);
    }

    /**
     * Знаходження натурального логарифму числа
     * @param a число, логарифм якого вираховуеться (а > 0)
     * @return значення натурального логарифму числа
     */
    public static double ln(double a){
        return Math.log(a);
    }

    /**
     * Знаходження десяткового логарифму числа
     * @param a число, логарифм якого вираховуеться (а > 0)
     * @return значення десяткового логарифму числа
     */
    public static double lg(double a){
        return Math.log10(a);
    }
    /**
     * Знаходження логарифму числа
     * @param a число, логарифм якого вираховуеться (а > 0)
     * @param b основа логарифму
     * @return значення логарифму числа
     */
    public static double log(double a, int b){
        return Math.log10(a)/Math.log10(b);
    }

    /**
     * Знаходження квадратного корню числа
     * @param a число (a > 0)
     * @return квадратний корінь числа
     */
    public static double sqrt(double a){
        return Math.sqrt(a);
    }

    /**
     * Знаходження числа піднесеного до степені
     * @param a число
     * @param b ступінь числа
     * @return число а піднесене до степені b
     */
    public static double pow(double a, double b){
        return Math.pow(a, b);
    }
}