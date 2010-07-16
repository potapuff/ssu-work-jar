package edu.sumdu.dl.calc;

import java.util.regex.*;

public class LexAnal {

    public static final char FUNC = 'f';
    public static final char POW = '^';
    public static final char EOL = '\n';
    public static final char MINUS = '-';
    public static final char PLUS = '+';
    public static final char MULT = '*';
    public static final char DIVD = '/';
    public static final char OP = '(';
    public static final char CP = ')';
    public static final char CB = ']';
    public static final char NUM = '0';
    public static final char BAD = '?';
    public static final char VARB = 'v';
    public static final char AVAR = '[';
    public static final char LT = '<';
    public static final char GT = '>';
    public static final char LE = '$';
    public static final char GE = '@';
    public static final char EQ = '=';
    public static final char NE = '#';
    public static final char OR = '|';
    public static final char NOT = '~';
    public static final char AND = '&';
    public static final int FT_SQRT = 1;
    public static final int FT_SIN = 2;
    public static final int FT_COS = 3;
    public static final int FT_ABS = 4;
    public static final int FT_EXP = 5;
    public static final int FT_CRND = 6;
    public static final int FT_NRND = 7;
    public static final int FT_DIFF = 8;
    public static final int FT_DIFFP = 9;
    public static final int FT_SELECT = 10;
    public static final int FT_LN = 11;
    public static final int FT_LG = 12;
    public static final int FT_TG = 13;
    public static final int FT_CTG = 14;
    public static final int FT_ASIN = 15;
    public static final int FT_ACOS = 16;
    public static final int FT_ATG = 17;
    public static final int FT_ACTG = 18;
    public static final int FT_SIN2 = 19;
    public static final int FT_SIN3 = 20;
    public static final int FT_COS2 = 21;
    public static final int FT_COS3 = 22;
    public static final int FT_DX = 23;
    public static final int FT_SEC = 24;
    public static final int FT_COSEC = 25;
    public static final int FT_SINH = 26;
    public static final int FT_COSH = 27;

    class FDesc {

        public String s_name;
        public int tip;

        FDesc(String s, int t) {
            s_name = new String(s);
            tip = t;
        }
    }
    public FDesc FUNCS[] = new FDesc[]{new FDesc("sqrt(", FT_SQRT),
        new FDesc("sin(", FT_SIN), new FDesc("cos(", FT_COS),
        new FDesc("sin2(", FT_SIN2), new FDesc("cos2(", FT_COS2),
        new FDesc("sin3(", FT_SIN3), new FDesc("cos3(", FT_COS3),
        new FDesc("abs(", FT_ABS), new FDesc("exp(", FT_EXP),
        new FDesc("crandom(", FT_CRND), new FDesc("nrandom(", FT_NRND),
        new FDesc("diff(", FT_DIFF), new FDesc("diffp(", FT_DIFFP),
        new FDesc("ln(", FT_LN), new FDesc("lg(", FT_LG),
        new FDesc("tg(", FT_TG), new FDesc("ctg(", FT_CTG),
        new FDesc("arcsin(", FT_ASIN), new FDesc("arccos(", FT_ACOS),
        new FDesc("arctg(", FT_ATG), new FDesc("arcctg(", FT_ACTG),
        new FDesc("d(", FT_DX), new FDesc("select(", FT_SELECT),
        new FDesc("sec(", FT_SEC), new FDesc("cosec(", FT_COSEC),
        new FDesc("sh(", FT_SINH), new FDesc("ch(", FT_COSH)};
    VarTable vars;
    char cur_tok;
    double cur_val;
    String cur_str;
    int cur_fun, cur_pos;
    String buf;

    public String getBuf() {
        return new String(buf);
    }

    public int getPos() {
        return cur_pos;
    }

    // read the name of the function and set proper type
    boolean isAlpha(char ch) {
        return ch <= 'z' && ch >= 'a' || ch <= 'Z' && ch >= 'A' || ch == '_'
                || ch == '\'';
    }

    boolean isAlNum(char ch) {
        return ch <= 'z' && ch >= 'a' || ch <= 'Z' && ch >= 'A' || ch == '_'
                || ch == '\'' || ch >= '0' && ch <= '9';
    }

    char read_func() {
        int i;
        for (i = 0; i < FUNCS.length; i++) {
            String cf = FUNCS[i].s_name;
            if (cur_pos + cf.length() < buf.length()) {
                String s = buf.substring(cur_pos, cur_pos + cf.length());
                if (cf.equals(s)) {
                    cur_pos += cf.length();
                    cur_tok = FUNC;
                    cur_fun = FUNCS[i].tip;
                    return cur_tok;
                }
            }
        }
        String s = "";
        while (cur_pos < buf.length() && isAlNum(buf.charAt(cur_pos))) {
            s = s + buf.charAt(cur_pos++);
        }
        cur_str = s;
        if (cur_pos < buf.length() && buf.charAt(cur_pos) == '[') {
            cur_pos++;
            if (vars != null && vars.getVar(s) != null)// &&
            // vars.getVar(s).isSet())
            {
                cur_tok = AVAR;
                return AVAR;
            } else {
                return BAD;
            }
        }
        if (vars != null && vars.getVar(s) != null) {
            if (vars.getVar(s).isSet()) {
                cur_val = vars.getVal(s);
            }
            cur_tok = VARB;
            return VARB;
        } else {
            cur_val = Math.random() * 10000;
        }
        return BAD;
    }

    // calculate function for given value and type
    void eat() {
        while (cur_pos < buf.length() && buf.charAt(cur_pos) == ' ') {
            cur_pos++;
        }
        if (cur_pos >= buf.length()) {
            cur_tok = EOL;
            return;
        }
        switch (buf.charAt(cur_pos)) {
            case '(':
            case ')':
            case '+':
            case '-':
            case '^':
            case '/':
            case '|':
            case '&':
            case ']':
            case '=':
            case ',':
            case '*':
                cur_tok = buf.charAt(cur_pos);
                cur_pos++;
                return;
            case '<':
                if (buf.charAt(cur_pos + 1) == '=') {
                    cur_tok = LE;
                    cur_pos++;
                } else {
                    cur_tok = LT;
                }
                cur_pos++;
                return;
            case '>':
                if (buf.charAt(cur_pos + 1) == '=') {
                    cur_tok = GE;
                    cur_pos++;
                } else {
                    cur_tok = GT;
                }
                cur_pos++;
                return;
            case '!':
                if (buf.charAt(cur_pos + 1) == '=') {
                    cur_tok = NE;
                    cur_pos++;
                } else {
                    cur_tok = NOT;
                }
                cur_pos++;
                return;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                cur_tok = NUM;
                cur_str = strtod(cur_pos);
                Double dv = new Double(cur_str);
                cur_val = dv.doubleValue();
                cur_pos += cur_str.length();
                return;
            default:
                if (isAlpha(buf.charAt(cur_pos))) {
                    cur_tok = read_func();
                } else {
                    cur_tok = EOL;
                }
        }

    }

    String strtod(int idx) {
        String s = buf.substring(idx);
        // This should match correctly written number
        Pattern p = Pattern.compile("(\\d+\\.?\\d*[eE][-+]?\\d+|\\d+\\.?\\d*)");
        Matcher m = p.matcher(s);
        if (m.find()) {
            return m.group();
        } else {
            return "" + s.charAt(0);
        }
    }

    LexAnal(String formula, VarTable vt) {
        buf = new String(formula);
        vars = vt;
    }

    public void start() {
        cur_pos = 0;
    }
}
