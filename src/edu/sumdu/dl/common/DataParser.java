package edu.sumdu.dl.common;

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

    static String[] asSArray(Object o) {
        // System.err.println(o.toString());
        String[] ret = o.toString().replaceAll("\\x0a", "\n").split(
                "\\|\\|\\|\\|");
        // System.err.println("Split:"+ret.length);
        return ret;
    }

    static int[] asIArray(Object o) {
        String s[] = asStrArray(o);
        if (s == null) {
            return null;
        }
        int d[] = new int[s.length];
        for (int i = 0; i < d.length; i++) {
            d[i] = Tool.toInt(s[i]);
        }
        return d;
    }

    static boolean[] asBArray(Object o) {
        String s[] = asStrArray(o);
        if (s == null) {
            return null;
        }
        boolean d[] = new boolean[s.length];
        for (int i = 0; i < d.length; i++) {
            d[i] = Tool.toBool(s[i]);
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

    static int[][] asIIArray(Object o) {
        Vector v = new Vector();
        Pattern p = Pattern.compile("\\{(.+?)\\}");
        Matcher m = p.matcher(o.toString());
        int max = 0;
        while (m.find()) {
            int d1[] = asIArray(m.group(0));
            if (d1.length > max) {
                max = d1.length;
            }
            v.addElement(d1);
        }
        if (v.size() == 0 || max == 0) {
            return null;
        }
        int d[][] = new int[v.size()][max];
        for (int i = 0; i < v.size(); i++) {
            d[i] = (int[]) (v.elementAt(i));
        }
        return d;
    }

    public static String makeString(int[] ar) {
        String s = "{";
        for (int i = 0; i < ar.length; i++) {
            s += ar[i];
            if (i < ar.length - 1) {
                s += ',';
            }
        }
        return s + "}";
    }

    public static String makeString(boolean[] ar) {
        String s = "{";
        for (int i = 0; i < ar.length; i++) {
            s += ar[i];
            if (i < ar.length - 1) {
                s += ',';
            }
        }
        return s + "}";
    }

    public static String makeString(String[] ar) {
        String ret = "";
        for (int i = 0; i < ar.length; i++) {
            ret += ar[i].replaceAll("\n", "\\x0A") + "||||";
        }
        return ret;
    }

    public static String makeString(double[] ar) {
        String s = "{";
        for (int i = 0; i < ar.length; i++) {
            s += ar[i];
            if (i < ar.length - 1) {
                s += ',';
            }
        }
        return s + "}";
    }

    public static String makeString(double[][] ar) {
        String s = "{";
        for (int i = 0; i < ar.length; i++) {
            s += makeString(ar[i]);
            if (i < ar.length - 1) {
                s += ',';
            }
        }
        return s + "}";
    }

    public static String makeString(int[][] ar) {
        String s = "{";
        for (int i = 0; i < ar.length; i++) {
            s += makeString(ar[i]);
            if (i < ar.length - 1) {
                s += ',';
            }
        }
        return s + "}";
    }
}
