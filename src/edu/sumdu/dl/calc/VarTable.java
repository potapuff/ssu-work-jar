package edu.sumdu.dl.calc;

import java.util.*;
import java.util.regex.*;

public class VarTable extends Hashtable {

    public VarTable() {
        super();
    }

    public Variable getVar(String name) {
        Object o = get(name);
        if (o == null || !(o instanceof Variable)) {
            return null;
        } else {
            return (Variable) o;
        }
    }

    public double getVal(String name) {
        Variable v = getVar(name);
        if (v != null) {
            return v.doubleValue();
        } else {
            return Math.random() * 100000;
        }
    }

    public void add(Variable v) {
        if (v == null) {
            return;
        }
        put(v.name, v);
    }

    public void updateDeps(String s) {
        for (Enumeration k = keys(); k.hasMoreElements();) {
            Object o = k.nextElement();
            if (o instanceof String && !o.equals(s)) {
                Variable v = getVar((String) o);
                if (v.type.equals("formula")) {
                    Pattern p = Pattern.compile("(\\w+)");
                    Matcher m = p.matcher(v.formula);
                    while (m.find()) {
                        if (m.group().equals(s)) {
                            v.reset();
                        }
                    }
                }
                if (v.type.equals("selector") && v.formula.equals(s)) {
                    v.reset();
                }
            }
        }
    }

    public String getsa(String vname, double indx) {
        Variable v = getVar(vname);
        if (v == null) {
            return "";
        }
        return v.strVal(indx);
    }

    public double getda(String vname, double indx) {
        Variable v = getVar(vname);
        if (v == null) {
            return 0.0;
        }
        return v.doubleValue(indx);
    }
}
