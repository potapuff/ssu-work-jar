package math;

/**
 * Надає методи для роботи із векторами та матрицями.
 * @author Eduard Bakhlov
 * @version 1.0
 */
public class Matrix {

    /**
     * Знаходить розв'язок системи  за методом Крамера.
     * @param a матриця А (розмірність 2*2)
     * @param b матриця В (розмірність 2*1)
     * @return вектор-розв'язок
     */
    public static double[] kramer(double[][] a, double[] b) {
        double detMain = a[0][0] * a[1][1] - a[0][1] * a[1][0];
        double detFirst = a[1][1] * b[0] - a[0][1] * b[1];
        double detSecond = a[0][0] * b[1] - a[1][0] * b[0];
        return new double[]{detFirst / detMain, detSecond / detMain};
    }

    /**
     * Транспонування матриці
     * @param matrix вихідна матриця
     * @return транспонована матриця
     */
    public static int[][] transpose(int[][] matrix) {
        int[][] res = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = matrix[j][i];
            }
        }
        return res;
    }

    /**
     * Транспонування матриці
     * @param matrix вихідна матриця
     * @return транспонована матриця
     */
    public static double[][] transpose(double[][] matrix) {
        double[][] res = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = matrix[j][i];
            }
        }
        return res;
    }

    /**
     * Знаходження розвязку системи лінійних рівнянь методом Гауса
     * @param a матриця А
     * @param b матриця B
     * @return вектор Х - розвязок системи лінійних рівнянь
     */
    public static double[] gauss(double[][] a, double[] b) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int best = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(a[j][i]) > Math.abs(a[best][i])) {
                    best = j;
                }
            }
            double[] tmp = a[i];
            a[i] = a[best];
            a[best] = tmp;
            double t = b[i];
            b[i] = b[best];
            b[best] = t;

            for (int j = i + 1; j < n; j++) {
                a[i][j] /= a[i][i];
            }
            b[i] /= a[i][i];

            for (int j = 0; j < n; j++) {
                if (j != i && a[j][i] != 0) {
                    for (int p = i + 1; p < n; p++) {
                        a[j][p] -= a[i][p] * a[j][i];
                    }
                    b[j] -= b[i] * a[j][i];
                }
            }
        }
        return b;
    }

    public static int[][] summate(int[][] a, int[][] b) {
        int[][] res = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                res[i][j] = a[i][j] + b[i][j];
            }
        }
        return res;
    }

    public static double[][] summate(double[][] a, double[][] b) {
        double[][] res = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                res[i][j] = a[i][j] + b[i][j];
            }
        }
        return res;
    }

    public static int[][] subtract(int[][] a, int[][] b) {
        int[][] res = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                res[i][j] = a[i][j] - b[i][j];
            }
        }
        return res;
    }

    public static double[][] subtract(double[][] a, double[][] b) {
        double[][] res = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                res[i][j] = a[i][j] - b[i][j];
            }
        }
        return res;
    }

    public static int[][] multiply(int[][] a, int value) {
        int[][] res = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                res[i][j] = a[i][j] * value;
            }
        }
        return res;
    }

    public static double[][] multiply(double[][] a, double value) {
        double[][] res = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                res[i][j] = a[i][j] * value;
            }
        }
        return res;
    }

    public static int[][] multiply(int[][] a, int[][] b) {
        int[][] res = new int[a.length][b[0].length];
        //...
        return res;
    }

    public static double[][] multiply(double[][] a, double[][] b) {
        double[][] res = new double[a.length][b[0].length];
        //...
        return res;
    }
}
