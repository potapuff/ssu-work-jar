package edu.sumdu.dl.calc;

import java.text.*;
import javax.swing.*;
import edu.sumdu.dl.common.CMath;

public class Variable {

    public String name, type, descr, varDim, strVal, formDim, formula;
    double min_val, max_val, init_val, eps, cur_val;
    public boolean visible, setable, is_set;
    double array[];
    String strings[];
    SuperTable table;

    public double getMininum() {
        return min_val;
    }

    public double getMaximum() {
        return max_val;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return "<var min=\"" + min_val + "\" max=\"" + max_val + "\">" + name
                + "</var>";
    }

    public Variable(String name, String type, String descr, boolean setable,
            boolean visible, String varDim) {
        this.name = new String(name);
        this.type = new String(type);
        this.descr = new String(descr);
        this.varDim = new String(varDim);
        this.setable = setable;
        this.visible = visible;
        if (type.charAt(0) == 'c') {
            this.setable = false;
        }
    }

    public Variable(String name, String type, double min, double max,
            double init, double eps, String descr, boolean setable,
            boolean visible, String varDim) {
        this(name, type, descr, setable, visible, varDim);
        this.eps = eps;
        this.cur_val = init;
        this.min_val = min;
        this.max_val = max;
        Double d = new Double(prec_lost(init, eps));
        this.cur_val = d.doubleValue();
        this.is_set = true;
    }

    public Variable(String name, String formula, String desc, String varDim,
            String formDim) {
        this(name, "formula", desc, false, true, varDim);
        this.formula = new String(formula);
        this.formDim = new String(formDim);
        this.is_set = false;
    }

    public Variable(String name, String descr, String varDim, SuperTable sup,
            double eps) {
        this(name, "table", descr, false, true, varDim);
        this.eps = eps;
        this.table = sup;
        this.is_set = true;
    }

    public Variable(String name, String type, double data[], String descr,
            String varDim) {
        this(name, type, descr, false, false, varDim);
        this.array = data;
        this.is_set = true;
    }

    public Variable(String name, String type, String[] data, String descr,
            String varDim) {
        this(name, type, descr, false, false, varDim);
        this.strings = data;
        this.is_set = true;
    }

    public Variable(String name, String type, String init, String descr) {
        this(name, type, descr, false, false, "");
        this.strVal = new String(init);
        this.is_set = true;
    }

    public boolean CanSet(double dv) {
        if (!setable) {
            return false;
        }
        if (type.equals("double") || type.equals("int")) {
            return dv >= min_val && dv <= max_val;
        }
        return false;
    }

    public void set(double d) {
        if (CanSet(d)) {
            is_set = true;
            cur_val = d;
            if (type.equals("int")) {
                cur_val = Math.ceil(d);
            }
        }
    }

    public void set_f(double d) {
        if (type.equals("formula") || type.equals("selector")) {
            is_set = true;
            cur_val = d;
        }
        if (type.equals("int")) {
            cur_val = Math.ceil(d);
            is_set = true;
        }
    }

    /*
     * vars.add(new Variable(vname,vtype,dt[0], DataParser.asDouble(dt[1]),
     * dt[2],dt[3], evald(dt[4]),evald(dt[5]), visible,varDim); <!-- var name,
     * eps, names, data, startidx, leng -->
     */
    public Variable(String vname, String vtype, String descr, String va_name,
            double eps, String nam_tab, String val_tab, double st_idx,
            double len, boolean visible, String varDim) {
        this(vname, vtype, descr, false, visible, varDim);
        this.eps = eps;
        this.is_set = false;
        this.formula = new String(va_name);
        this.name_table = new String(nam_tab);
        this.val_table = new String(val_tab);
        this.start_idx = (int) st_idx;
        this.len_idx = (int) len;
    }
    String name_table, val_table, var_name;
    int start_idx, len_idx;

    public double select(VarTable vt) {
        is_set = false;
        if (!type.equals("selector")) {
            return 0.0;
        }
        Variable v1 = vt.getVar(formula);
        Variable c1 = vt.getVar(name_table);
        Variable c2 = vt.getVar(val_table);
        if (v1 == null || !v1.isSet() || c1 == null || c2 == null
                || c1.strings == null || c2.array == null) {
            return 0.0;
        }
        int k = (int) (Math.min(c1.strings.length, c2.array.length));
        if (k < start_idx + len_idx - 1) {
            return 0.0;
        }
        double d1 = v1.doubleValue();
        String s[] = new String[len_idx];
        boolean b[] = new boolean[len_idx];
        for (int i = 0; i < len_idx; i++) {
            s[i] = c1.strVal((double) (i + start_idx)) + "|"
                    + c2.doubleValue((double) (i + start_idx));
            b[i] = CMath.diffp(d1, c2.doubleValue((double) (i + start_idx)),
                    eps);
        }
        JComboBox message = new JComboBox(s);

        JOptionPane.showMessageDialog(null, message, "Выбор значения для "
                + this.name, JOptionPane.QUESTION_MESSAGE, null);
        int idx = message.getSelectedIndex();
        if (!b[idx]) {
            return 0.0;
        }
        set_f(c2.doubleValue((double) (idx + start_idx)));
        return cur_val;
    }

    public void reset() {
        is_set = false;
    }

    public static String prec_lost(double value, double ps) {
        if (ps < 1e-10) {
            return "" + value;
        }
        int k = (int) Math.ceil(Math.log(ps) / Math.log(10.0));
        if (k >= 0) {
            return "" + (int) Math.ceil(value);
        }
        String frmt = "0.";
        for (; k <= 0; k++) {
            frmt += "#";
        }
        DecimalFormat myFormatter = new DecimalFormat(frmt);
        String s = myFormatter.format(value).replaceAll(",", ".");
        return s;
    }

    public double doubleValue() {
        if (type.equals("cs") || type.equals("string") || type.equals("cs[]")
                || type.equals("csi")) {
            return 0.0;
        }
        if (type.equals("double") || type.equals("cd") || type.equals("cint")
                || type.equals("int")) {
            return cur_val;
        }
        if ((type.equals("formula") || type.equals("selector")) && is_set) {
            return cur_val;
        }
        if (type.equals("table")) {
            return table.get();
        }
        return 0.0;
    }

    public double doubleValue(double v_n) {
        int k = (int) v_n;
        if (type.equals("cd[]") && k >= 0 && k < array.length) {
            return array[k];
        }
        return 0.0;
    }

    public String toString() {
        if (type.equals("table")) {
            return "" + table.get();
        }
        if (type.equals("cd[]")) {
            return "ARRAY";
        }
        if (type.equals("cs[]")) {
            return "STRINGS";
        }
        if (type.equals("csi")) {
            return strVal;
        }
        return "" + cur_val;
    }

    public String strVal(double v_n) {
        int k = (int) v_n;
        if (type.equals("cs[]") && k >= 0 && k < strings.length) {
            return strings[k];
        }
        return "";
    }

    public boolean compare(double d) {
        if (!is_set) {
            return false;
        }
        return CMath.diffp(d, cur_val, eps);
    }

    public boolean isSet() {
        return is_set;
    }
}
