package edu.sumdu.dl.calc;

public class TabPart {

    double r_min, r_max, c_min, c_max;
    TabInt table;

    public TabPart(double rc_min, double rc_max, double cc_min, double cc_max,
            double row_vals[], double col_vals[], double tdata[][]) {
        r_min = rc_min;
        r_max = rc_max;
        c_min = cc_min;
        c_max = cc_max;
        table = new TabInt(row_vals, col_vals, tdata);
    }

    public double get(double rv, double cv) {
        return table.get(rv, cv);
    }

    public boolean check(double rc, double cc) {
        return rc > r_min && rc < r_max && cc > c_min && cc < c_max;
    }
}
