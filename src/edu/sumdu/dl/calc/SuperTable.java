package edu.sumdu.dl.calc;

import java.util.*;

public class SuperTable {

    Vector parts;
    String rowk, colk, rowv, colv;
    VarTable vat;

    public SuperTable(VarTable vt, String row_c, String col_c, String row_look,
            String col_look) {
        parts = new Vector();
        vat = vt;
        rowk = new String(row_c);
        colk = new String(col_c);
        rowv = new String(row_look);
        colv = new String(col_look);
    }

    /*
     * row_c,col_c - control parameters to get the part row_look,col_look -
     * parameters to get data in the part
     */
    public void add(TabPart tp) {
        parts.addElement(tp);
    }

    public double get() {
        DimCalc cc = new DimCalc(rowk, vat);
        double row_v = cc.eval();
        if (cc.notOK) {
            return 0;
        }
        cc = new DimCalc(colk, vat);
        if (cc.notOK) {
            return 0;
        }
        double col_v = cc.eval();
        for (int i = 0; i < parts.size(); i++) {
            Object o = parts.elementAt(i);
            if (o instanceof TabPart) {
                TabPart tp = (TabPart) o;
                if (tp.check(row_v, col_v)) {
                    cc = new DimCalc(rowv, vat);
                    row_v = cc.eval();
                    if (cc.notOK) {
                        return 0.0;
                    }
                    cc = new DimCalc(colv, vat);
                    col_v = cc.eval();
                    if (cc.notOK) {
                        return 0.0;
                    }
                    return tp.get(row_v, col_v);
                }
            }
        }
        return 0.0;
    }
}
