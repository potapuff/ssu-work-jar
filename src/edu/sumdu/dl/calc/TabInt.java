package edu.sumdu.dl.calc;

public class TabInt {

    double rows[], cols[], data[][];
    private boolean row_asc, col_asc;
    int M, N;

    TabInt(double row_vals[], double col_vals[], double tdata[][]) {
        M = row_vals.length;
        N = col_vals.length;
        rows = new double[M];
        cols = new double[N];
        for (int i = 0; i < M; i++) {
            rows[i] = row_vals[i];
        }
        for (int i = 0; i < N; i++) {
            cols[i] = col_vals[i];
        }
        data = new double[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < tdata[i].length; j++) {
                data[i][j] = tdata[i][j];
            }
        }
        row_asc = rows[0] < rows[M - 1];
        col_asc = cols[0] < cols[N - 1];
    }

    private double rowv(int idx) {
        return row_asc ? rows[idx] : rows[M - idx - 1];
    }

    private double colv(int idx) {
        return col_asc ? cols[idx] : cols[M - idx - 1];
    }

    private double datav(int r, int c) {
        int rw = row_asc ? r : M - 1 - r;
        int cl = row_asc ? c : N - 1 - c;
        return data[rw][cl];
    }

    public double get(double rv, double cv) {
        /*
         * rows[the_row] <= rv <= rows[the_row1] cols[the_col] <= cv <=
         * cols[the_col1]
         */
        int the_row = 0;
        while (the_row < M && rowv(the_row) <= rv) {
            the_row++;
        }
        int the_col = 0;
        while (the_col < N && colv(the_col) <= cv) {
            the_col++;
        }

        boolean row_out, col_out;
        row_out = rv < rowv(0) || the_row >= M - 1;
        col_out = cv < colv(0) || the_col >= N - 1;
        double y1, y2, y3, y4, u, t;
        the_row--;
        the_col--;
        if (the_row < 0) {
            the_row = 0;
        }
        if (the_col < 0) {
            the_col = 0;
        }
        if (the_row == M) {
            the_row = M - 1;
        }
        if (the_col == N) {
            the_col = N - 1;
        }

        y1 = datav(the_row, the_col);
        y2 = row_out ? 0 : datav(the_row + 1, the_col);

        y3 = row_out || col_out ? 0 : datav(the_row + 1, the_col + 1);
        y4 = col_out ? 0 : datav(the_row, the_col + 1);

        t = row_out ? 0 : (rv - rowv(the_row))
                / (rowv(the_row + 1) - rowv(the_row));
        u = col_out ? 0 : (cv - colv(the_col))
                / (colv(the_col + 1) - colv(the_col));
        return (1 - t) * (1 - u) * y1 + t * (1 - u) * y2 + t * u * y3 + (1 - t)
                * u * y4;
        // return data[the_row][the_col];
    }
}
