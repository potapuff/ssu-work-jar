package edu.sumdu.dl.calc;

import java.util.*;
import java.util.regex.*;
import edu.sumdu.dl.common.CMath;

public class Formula {

    Formula() {
    }

    /* equals(A+B,B+A,5+4,vt) */
    static public boolean equals(String one, String two, String three,
            VarTable vt) {
        boolean flag = NumericDimEqv(one, two, vt).equals("");
        flag = flag && VarSame(one, two).equals("");
        flag = flag && FormulaEqvNumeric(two, three, vt).equals("");
        return flag;
    }

    static public boolean equals(String one, String two, VarTable vt) {
        boolean flag = NumericDimEqv(one, two, vt).equals("");
        flag = flag && VarSame(one, two).equals("");
        return flag;
    }

    static public boolean equals(String one, String two, VarTable vt,
            String vars[]) {
        boolean flag = true;
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < vars.length; i++) {
                Variable v = vt.getVar(vars[i]);
                if (v != null) {
                    v.set(CMath.random(v.min_val, v.max_val));
                }
            }
            String s = NumericDimEqv(one, two, vt);
            // System.err.println("Checking :"+one+":vs:"+two+":>"+s);
            flag = s.equals("") && flag;
        }
        return flag;
    }

    static public String errors(String one, String two, String three,
            VarTable vt) {
        String s = NumericDimEqv(one, two, vt) + VarSame(one, two)
                + FormulaEqvNumeric(two, three, vt);
        return s + "\n";
    }

    /*
     * Model: x=A+B=5+4 => (x,A+B,5+4) Stored: x=B+A (x,B+A) p1_2(A+B,B+A);
     * p4(A+B,5+4) p3({A,B},{B,A})
     * 
     * две формулы эквивалентны если: 1.размерности равны 2.значения при
     * заданных переменных равны
     */
    static public String NumericDimEqv(String one, String two, VarTable vt) {
        String error1 = "";
        DimCalc c1 = new DimCalc(one, vt);
        DimCalc c2 = new DimCalc(two, vt);
        double d1 = c1.eval();
        double d2 = c2.eval();
        if (c1.notOK) {
            error1 += "\nОшибка в эталонной формуле ";
        }
        if (c1.notOK && c2.notOK) {
            // System.err.println("Both fail:"+c1.errorLine+":"+c2.errorLine);
            return "Both fail:" + c1.errorLine + ":" + c2.errorLine;
        }
        if (c2.notOK) {
            error1 += "\nОшибка во введенной формуле " + c2.errorLine;
        }
        if (!CMath.diffp(d1, d2, 0.001)) {
            error1 += "\nНесоответсвие результата";
        }
        if (!c1.last_value.dim.equals(c2.last_value.dim)) {
            error1 += "\nнесоответсвие размерностей";
        }
        // System.err.println(error1);
        return error1;
    }

    /* 3.использованы те же переменные */
    static public String VarSame(String one, String two) {
        String error1 = "";
        Hashtable h1 = new Hashtable();
        Hashtable h2 = new Hashtable();
        Pattern p = Pattern.compile("([:alpha:]\\w+)");
        Matcher m1 = p.matcher(one);
        Matcher m2 = p.matcher(two);
        while (m1.find()) {
            h1.put(m1.group(), m1.group());
        }
        while (m2.find()) {
            h2.put(m2.group(), m2.group());
        }
        if (!h1.equals(h2)) {
            error1 = "\nнесоответствие параметров";
        }
        return error1;
    }

    /*----------------------------------------
    /*  4.в правой части Числам соотв значения переменных из VarTable*/
    static public String FormulaEqvNumeric(String leftPart, String rightPart,
            VarTable varTable) {
        String error1 = "";
        LexAnal l1 = new LexAnal(leftPart, varTable);
        LexAnal l2 = new LexAnal(rightPart, varTable);
        l1.start();
        l2.start();
        boolean flag = true;
        do {
            l1.eat();
            l2.eat();
            if (l1.cur_tok == l2.cur_tok && l1.cur_tok != LexAnal.VARB) {
                if (l1.cur_tok == LexAnal.NUM) {
                    flag = flag && CMath.diffp(l1.cur_val, l2.cur_val, 0.01);
                }
                if (l1.cur_tok == LexAnal.FUNC) {
                    flag = flag && l1.cur_fun == l2.cur_fun;
                }
            } else if (l1.cur_tok == LexAnal.VARB && l2.cur_tok == LexAnal.NUM) {
                flag = flag
                        && CMath.diffp(varTable.getVal(l1.cur_str), l2.cur_val,
                        0.01);

            } else {
                flag = false;
            }

        } while (flag && l1.cur_tok != LexAnal.EOL);
        if (l1.cur_tok == LexAnal.EOL && l2.cur_tok != LexAnal.EOL) {
            flag = false;
        }
        if (!flag) {
            error1 = "\nнесоответсвие записи или числовых значений";
        }
        return error1;
    }
    /*
     * => Вывод: калькулятор должен использовать VarTable (там хранить имена
     * значения и размерности) по ходу численных вычислений проводить децствия с
     * размерностями
     */
}
