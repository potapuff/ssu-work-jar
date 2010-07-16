package edu.sumdu.dl.calc;

import java.util.regex.*;
import java.util.*;

class DataParser {

    static double asDouble(Object o) {
        String s = o.toString();
        try {
            Double d = new Double(s);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    static String[] asStrArray(Object o) {
        if (o == null) {
            return null;
        }
        String s = o.toString();
        Pattern p = Pattern.compile("\\{(.+?)\\}");
        Matcher m = p.matcher(s);
        if (m.find()) {
            return m.group(1).split(",");
        } else {
            return null;
        }
    }

    static double[] asDArray(Object o) {
        String s[] = asStrArray(o);
        if (s == null) {
            return null;
        }
        double d[] = new double[s.length];
        for (int i = 0; i < d.length; i++) {
            d[i] = asDouble(s[i]);
        }
        return d;
    }

    static double[][] asDDArray(Object o) {
        Vector v = new Vector();
        Pattern p = Pattern.compile("\\{(.+?)\\}");
        Matcher m = p.matcher(o.toString());
        int max = 0;
        while (m.find()) {
            double d1[] = asDArray(m.group(0));
            if (d1.length > max) {
                max = d1.length;
            }
            v.addElement(d1);
        }
        if (v.size() == 0 || max == 0) {
            return null;
        }
        double d[][] = new double[v.size()][max];
        for (int i = 0; i < v.size(); i++) {
            d[i] = (double[]) (v.elementAt(i));
        }
        return d;
    }
}
