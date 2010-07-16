package edu.sumdu.dl.common;

// случайные числа и проверка чисел с заданной точностью
public class CMath {

    CMath() {
    }

    /** случайное число в интервале [-5;5] */
    public static double RS() {
        return Math.ceil(random(-5, 5));
    }

    /** случайное число в интервале [a;b] */
    public static double random(double min, double max) {
        double r = Math.random();
        return r * (max - min) + min;
    }

    /** сравнение двух чисел с абсолютной точностью */
    public static boolean diff(double r1, double r2, double eps) {
        return Math.abs(r1 - r2) < Math.abs(eps);
    }

    /** сравнение двух чисел с относительной точностью */
    public static boolean diffp(double r1, double r2, double eps) {
        return Math.abs(r1 - r2) < Math.abs(eps * r1) || Math.abs(r2) < eps
                && Math.abs(r1 - r2) < Math.abs(eps);
    }

    /** случайное целое число в интервале [a;b] */
    public static double crandom(double a, double b) {
        return Math.floor(CMath.random(a, b) + 0.5);
    }

    /**
     * nrandom(2,3,100) - random number between 2 and 3 with 2 digits after coma
     */
    public static double nrandom(double a, double b, double e) {
        return Math.ceil(CMath.random(a, b) * e) / e;
    }

    /** Вычисление определтителя */
    public static int calcDet(int[][] M) {
        if (M.length == 1) {
            return M[0][0];
        }
        if (M.length == 3) {
            return M[0][0]
                    * M[1][1]
                    * M[2][2]
                    + M[0][2]
                    * M[1][0]
                    * M[2][1]
                    + M[0][1]
                    * M[1][2]
                    * M[2][0]
                    - (M[2][0] * M[1][1] * M[0][2] + M[0][1] * M[1][0]
                    * M[2][2] + M[0][0] * M[1][2] * M[2][1]);
        } else if (M.length == 2) {
            return M[0][0] * M[1][1] - M[0][1] * M[1][0];
        } else {
            int deter = 0, k = 1;
            for (int i = 0; i < M.length; i++) {
                deter += M[0][i] * k * calcDet(minor(M, 0, i));
                k = -k;
            }
            return deter;
        }
    }

    /** Матрица -минор */
    public static int[][] minor(int[][] M, int row, int col) {
        int rw = 0;
        int N = M.length;
        int[][] ret = new int[N - 1][N - 1];
        for (int i = 0; i < N; i++) {
            if (i == row) {
                continue;
            }
            int cl = 0;
            for (int j = 0; j < N; j++) {
                if (j != col) {
                    ret[rw][cl++] = M[i][j];
                }
            }
            rw++;
        }
        return ret;
    }
}
