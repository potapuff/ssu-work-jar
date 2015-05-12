package edu.sumdu.dl.formula;

import javax.swing.*;
import java.awt.*;
import java.util.regex.*;
import java.util.*;
import com.keypoint.*;
import java.awt.image.BufferedImage;


/*
Simple calculator
e: -p | p | e+p | e-p
p: t | p*t | p/t
t: f | t^f
f: NUM | (e) | func(e)
 */
public class FormulaRender extends JPanel implements
        edu.sumdu.dl.common.ISelfDumped, edu.sumdu.dl.common.ImageDumped {

    public String errorLine;
    public boolean notOK;
    static final char FUNC = 'f';
    static final char POW = '^';
    static final char EOL = '\n';
    static final char MINUS = '-';
    static final char PLUS = '+';
    static final char MULT = '*';
    static final char DIVD = '/';
    static final char OP = '(';
    static final char CP = ')';
    static final char NUM = '0';
    static final char BAD = '?';
    static final char VARB = 'v';
    static final char OBJ = '@';
    static final char ASYMP = '\u2248'; // ~=
    static final char NEQ = '\u2260'; // !=
    static final char LEQ = '\u2264'; // <=
    static final char GEQ = '\u2265'; // >=
    static final char PLUSMINUS = '\u00B1'; // .+-.

    /*
     * <!ENTITY asymp CDATA "&#8776; ?" -- почти равно = асимптотически равно,
     * U+2248 ISOamsr --> <!ENTITY ne CDATA "&#8800; ?" -- не равно, U+2260
     * ISOtech --> <!ENTITY equiv CDATA "&#8801; ?" -- совпадает с, U+2261
     * ISOtech --> <!ENTITY le CDATA "&#8804; ?" -- меньше или равно, U+2264
     * ISOtech --> <!ENTITY ge CDATA "&#8805; ?" -- больше или равно, U+2265
     * ISOtech -->
     */
    abstract class FT {

        static final int SQRT = 1;
        static final int SIN = 2;
        static final int COS = 3;
        static final int ABS = 4;
        static final int EXP = 5;
        static final int LIMIT = 6;
        static final int SUMMA = 7;
        static final int ROOT = 8;
        static final int TAN = 9;
        static final int COTAN = 10;
        static final int DIFFER = 11;
        static final int PARTIAL = 12;
        static final int DIFF = 13;
        static final int INTG = 14;
        static final int POINTS = 15;
        static final int COSEC = 16;
        static final int NOT = 17;
    }

    class FDesc {

        public String s_name;
        public int tip;
        public boolean canHavePow;

        FDesc(String s, int t) {
            s_name = new String(s);
            tip = t;
        }

        FDesc(String s, int t, boolean f) {
            s_name = new String(s);
            tip = t;
            canHavePow = f;
        }
    }
    FDesc FUNCS[] = new FDesc[]{new FDesc("sqrt", FT.SQRT),
        new FDesc("cosec", FT.COSEC), new FDesc("sin", FT.SIN, true),
        new FDesc("cos", FT.COS, true), new FDesc("tg", FT.TAN, true),
        new FDesc("ctg", FT.COTAN, true), new FDesc("abs", FT.ABS),
        new FDesc("exp", FT.EXP),
        new FDesc("part", FT.PARTIAL, true), new FDesc("not", FT.NOT),
        new FDesc("points", FT.POINTS), // points(f(x);a;b)
        new FDesc("int", FT.INTG), // int(x^3;2;10)
        new FDesc("limit", FT.LIMIT), // limit(x;0;x^2/sin(x))
        new FDesc("sum", FT.SUMMA), // sum(x;0;x^2/sin(x))
        new FDesc("root", FT.ROOT), // sum(x;0;x^2/sin(x))
        new FDesc("diff", FT.DIFF), // diff(f(x)) diff(f(x);3) diff(f(x);3;x)
        new FDesc("d", FT.DIFFER, true)
    };
    String lastfun;
    char cur_tok;
    String cur_val;
    int cur_fun;
    String buf;
    int cur;

    // read the name of the function and set proper type
    boolean isAlpha(char ch) {
        return ch <= 'z' && ch >= 'a' || ch <= 'Z' && ch >= 'A' || ch == '_'
                || ch == '\'';
    }

    boolean isAlNum(char ch) {
        return ch <= 'z' && ch >= 'a' || ch <= 'Z' && ch >= 'A' || ch == '_'
                || ch >= '0' && ch <= '9' || ch == '\'';
    }

    boolean isNum(char ch) {
        return ch <= '9' && ch >= '0';
    }

    boolean hasPow(String where, String suspect, int ofset) {
        // System.err.println("Is?"+suspect+":"+suspect.length()+":"+where);
        // if(suspect.indexOf("(")>=0)
        // suspect=suspect.substring(0,suspect.length()-2);
        if (where.length() < suspect.length()) {
            return false;
        }
        // System.err.println("Check:"+where.substring(0,suspect.length()));
        if (where.indexOf(suspect) == 0) {

            String s = "";
            int lng = suspect.length();
            if (lng < where.length()) {
                s = where.substring(suspect.length(), where.length());
            }
            if (edu.sumdu.dl.common.Tool.toInt(s) == 0) {
                last_read_func = new FuncInfo(suspect, false, "");
            } else {
                last_read_func = new FuncInfo(suspect, true, s);
            }
            // System.err.println("Yes!"+suspect+":"+s);
            return true;
        }
        // System.err.println("No!"+suspect+":"+where);
        return false;
    }

    class FuncInfo {

        String func_name, func_power;
        boolean has_power;

        FuncInfo(String nm, boolean f, String pw) {
            func_power = pw;
            has_power = f;
            func_name = nm;
        }
    }
    FuncInfo last_read_func;

    char read_func() {
        int i;
        String s = "";
        while (cur < buf.length() && isAlNum(buf.charAt(cur))) {
            s = s + buf.charAt(cur++);
        }
        cur_val = s;
        cur_tok = VARB;
        if (cur < buf.length() && buf.charAt(cur) == '(') {
            cur_fun = -1;
            for (i = 0; i < FUNCS.length; i++) {
                String cf = FUNCS[i].s_name;
                boolean itHasPower = FUNCS[i].canHavePow
                        && hasPow(cur_val, cf, 0);
                if (cf.equals(cur_val) || itHasPower) {
                    cur_tok = FUNC;
                    cur_fun = FUNCS[i].tip;
                    if (!itHasPower) {
                        last_read_func = new FuncInfo(cur_val, false, "");
                    }
                    cur++;
                    return cur_tok;
                }
            }
            last_read_func = new FuncInfo(cur_val, false, "");
            cur_tok = FUNC;
            cur++;

        }
        return cur_tok;
    }

    // calculate function for given value and type
    void calc_func(int tip, MBox x, MBox target, FuncInfo last_read_func) {
        switch (tip) {
            case FT.SQRT:
                MSQRT p = new MSQRT();
                p.add(x);
                target.add(p);
                break;
            case FT.NOT:
                MNot not_p = new MNot();
                not_p.add(x);
                target.add(not_p);
                break;

            case FT.ROOT:
                eat();
                MRoot rot = new MRoot(cur_val);
                rot.add(x);
                eat();
                // System.out.println(cur_val);
                target.add(rot);
                break;
            case FT.DIFF:
                // if(cur_tok==CP){
                MBraced br22 = new MBraced();
                br22.add(x);
                eat();
                String stp;
                int po;
                try {
                    po = (new Integer(cur_val)).intValue();
                } catch (Exception exep) {
                    po = 1;
                }
                eat();
                if (po < 1 || po > 10) {
                    po = 1;
                }
                stp = (new String[]{"'", "''", "'''", "IV", "V", "VI", "VII",
                            "VIII", "IX", "X"})[po - 1];
                MJLabel stepn = new MJLabel(stp);
                if (po < 4) {
                    stepn.setFont(new Font("default", Font.BOLD | Font.ITALIC,
                            MJLabel.fontSize + 2));
                } else {
                    stepn.setFont(new Font("default", Font.ITALIC,
                            MJLabel.fontSize - 4));
                }

                MBox stepen = new MBox();
                stepen.add(stepn);
                target.add(new MPower(br22, stepen));
                // }
                break;
            case FT.DIFFER:
                String powr = "";
                if (last_read_func.has_power) {
                    powr = "<sup>" + last_read_func.func_power + "</sup>";
                }

                if (x.getComponentCount() == 1
                        && x.getComponent(0) instanceof MJLabel) {
                    String txt = ((MJLabel) x.getComponent(0)).getText();
                    if (txt.indexOf("<html") == 0) {
                        txt = txt.replaceFirst("<html>", "<html><b>d</b>"
                                + powr);
                    } else {
                        txt = "<html>d" + powr + "<i>" + txt + "</i></html>";
                    }
                    target.add(new MJLabel(txt, Font.BOLD));
                } else {
                    target.add(new MJLabel("<html><b>d</b>" + powr + "</html>",
                            Font.BOLD));
                    MBraced br1 = new MBraced();
                    br1.add(x);
                    target.add(br1);
                }
                break;
            case FT.SIN:
            case FT.COS:
            case FT.TAN:
            case FT.COTAN:
            case FT.PARTIAL:
            case FT.COSEC:
                MBraced br = new MBraced();
                br.add(x);
                if (tip == FT.PARTIAL) {
                    last_read_func.func_name = "\u2202";
                }
                String lastfun = last_read_func.func_name;
                if (last_read_func.has_power) {
                    // lastfun="<html>"+last_read_func.func_name+"<sup>"+last_read_func.func_power+"</sup></html>";
                    MJLabel.decFont();
                    MJLabel.decFont();
                    MJLabel powlab = new MJLabel(last_read_func.func_power);
                    MJLabel.incFont();
                    MJLabel.incFont();
                    MBox b1 = new MBox();
                    b1.add(new MJLabel(lastfun, Font.BOLD));
                    MBox b2 = new MBox();
                    b2.add(powlab);
                    target.add(new MPower(b1, b2));
                } else {
                    target.add(new MJLabel(lastfun, Font.BOLD));
                }

                target.add(ridBrace2(br));
                break;
            case FT.ABS:
                MABS a = new MABS();
                target.add(a);
                a.add(x);
                break;
            case FT.EXP:
                MBox mb = new MBox();
                x = ridBrace2(x);
                mb.add(new MJLabel("e", Font.BOLD));
                MPower mp = new MPower(mb, x);
                target.add(mp);
                break;
            case FT.INTG:
                MBox exp = x;
                MBox low = new MBox();
                MBox hig = new MBox();
                // System.err.println("Warn:"+cur_tok);
                if (cur_tok == ';') {
                    eat();
                    MJLabel.decFont();
                    e(low); // System.err.println("Warn:"+cur_tok);

                    if (cur_tok != CP) {
                        eat();
                        e(hig);
                    }
                    // System.err.println("Warn:"+cur_tok);

                    MJLabel.incFont();
                }
                target.add(new MInt(exp, low, hig));
                break;
            case FT.POINTS:
                MBox expr = x;
                MBox low1 = new MBox();
                MBox hig1 = new MBox();
                // System.err.println("Warn:"+cur_tok);
                if (cur_tok == ';') {
                    eat();
                    MJLabel.decFont();
                    e(low1); // System.err.println("Warn:"+cur_tok);

                    if (cur_tok != CP) {
                        eat();
                        e(hig1);
                    }
                    // System.err.println("Warn:"+cur_tok);

                    MJLabel.incFont();
                }
                target.add(new MPoints(expr, low1, hig1));
                break;

            case FT.LIMIT:
                MBox var = x;
                MBox goesTo = new MBox();
                MJLabel.decFont();
                eat();
                e(goesTo);
                MJLabel.incFont();
                eat(); // ';'
                MBox formula = new MBox();
                if (cur_tok != CP) {
                    e(formula);
                } else {
                    formula = new MStub();
                }
                target.add(new MLimit(var, goesTo, formula));
                break;
            case FT.SUMMA:
                MBox sum_bot = x;
                MBox sum_top = new MBox();
                eat();
                e(sum_top);
                eat(); // ';'
                MBox sum_form = new MBox();
                e(sum_form);
                target.add(new MSum(sum_bot, sum_top, sum_form));
                break;
            default:
                MBraced br2 = new MBraced();
                br2.add(x);
                target.add(new MJLabel(last_read_func.func_name, Font.BOLD));
                target.add(br2);
                break;
        }
    }

    void eat() {
        while (cur < buf.length() && buf.charAt(cur) == ' ') {
            cur++;
        }
        if (cur >= buf.length()) {
            cur_tok = EOL;
            return;
        }
        switch (buf.charAt(cur)) {
            case 0:
                cur_tok = EOL;
                break;
            case '=':
            case '<':
            case '!':
            case '>':
            case NEQ:
            case LEQ:
            case GEQ:
            case ASYMP:
            case PLUSMINUS:
            case '(':
            case ')':
            case '+':
            case '-':
            case '^':
            case '/':
            case '[':
            case ']':
            case ',':
            case ';':
            case '{':
            case '}':
            case ':':
            case '*':
                cur_tok = buf.charAt(cur);
                cur++;
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
                String st_v = strtod(cur);
                cur_val = st_v;
                cur += st_v.length();
                return;
            case '@':
                cur_tok = OBJ;
                cur++;
                cur_val = "";
                while (cur < buf.length() && buf.charAt(cur) != '@') {
                    cur_val += buf.charAt(cur);
                    cur += 1;
                }
                if (buf.charAt(cur) == '@') {
                    cur += 1;
                }
                // System.err.println("GOT:@"+cur_val+":"+buf.charAt(cur));
                return;
            default:
                if (isAlpha(buf.charAt(cur))) {
                    cur_tok = read_func();
                } else {
                    cur_tok = EOL;
                }
        }
    }

    void e(MBox target) {
        if (cur_tok == EOL) {
            return;
        }
        if (cur_tok == MINUS) {
            eat();
            target.add(new MJLabel(" - ", Font.BOLD));
            p(target);
        } else {
            p(target);
        }
        for (;;) {
            // System.out.print(":"+cur_tok);
            switch (cur_tok) {
                case ',':
                case '<':
                case '!':
                case '>':
                case NEQ:
                case LEQ:
                case GEQ:
                case ASYMP:
                case PLUSMINUS:

                    target.add(new MJLabel(" " + cur_tok + " ", Font.BOLD));
                    eat();
                    e(target);
                    break;
                case '=':
                    target.add(new MJLabel(" " + cur_tok + " ", Font.BOLD));
                    eat();
                    p(target);
                    break;
                case MINUS:
                    eat();
                    target.add(new MJLabel(" - ", Font.BOLD));
                    p(target);
                    break;
                case PLUS:
                    eat();
                    target.add(new MJLabel(" + ", Font.BOLD));
                    p(target);
                    break;
                default:
                    return;
            }
        }
    }

    void p(MBox target) {
        MBox tp, bt, left;
        int tpc = 0, btc = 0;
        tp = new MBox();
        bt = new MBox();
        t(tp);
        while (cur_tok == MULT || cur_tok == DIVD || cur_tok == ':') {
            if (cur_tok == MULT) {
                eat();
                if (cur_tok == NUM) {
                    tp.add(new MJLabel("*"));
                }
                t(tp);
            }
            if (cur_tok == ':') {
                eat();
                MJLabel.incFont();
                tp.add(new MJLabel(
                        "<html><sup style=\"font-size:16pt\">.</sup></html>",
                        Font.BOLD));
                MJLabel.decFont();

                t(tp);
            }
            if (cur_tok == DIVD) {
                eat();
                // if(btc>0)bt.add(new MJLabel("*"));
                btc++;
                t(bt);
            }
        }
        if (btc > 0) {
            tp = ridBrace(tp);
            bt = ridBrace(bt);
            target.add(new MFract(tp, bt));
        } else {
            Component cl[] = tp.getComponents();
            for (int i = 0; i < cl.length; i++) {
                target.add(cl[i]);
            }
        }

    }

    MBox ridBrace(MBox cont) {
        MBox bx;
        if (cont.getComponentCount() == 1
                && cont.getComponent(0) instanceof MBraced) {
            bx = new MBox();
            Component cl[] = ((MBox) (cont.getComponent(0))).getComponents();
            for (int i = 0; i < cl.length; i++) {
                bx.add(cl[i]);
            }

        } else {
            bx = cont;
        }
        return bx;
    }

    MBox ridBrace2(MBox cont) {
        MBox bx = new MBox();
        // System.out.println(cont.getClass().getName());
        if (!(cont instanceof MBraced)) {
            return cont;
        }
        Component cl[];
        if (cont.getComponentCount() == 1
                && cont.getComponent(0).getClass().getName().equals(
                "edu.sumdu.dl.formula.MBox")) {
            cl = ((MBox) cont.getComponent(0)).getComponents();
        } else {
            cl = cont.getComponents();
        }
        int opers = 0;
        for (int i = 0; i < cl.length; i++) {
            // System.out.println(cl[i].getClass().getName());
            if (cl[i] instanceof JLabel) {
                String t = ((JLabel) cl[i]).getText();
                if (t.matches("\\s*[-+]\\s*")) {
                    ++opers;
                }
            }
        }
        if (opers == 0) {
            cl = cont.getComponents();
            for (int i = 0; i < cl.length; i++) {
                bx.add(cl[i]);
            }
            return bx;
        } else {
            return cont;
        }
    }

    MBox downTrigoPower(MBox vl) {
        return vl;
    }

    void t(MBox target) {
        MBox vl, p;
        boolean powd = false;
        vl = new MBox();
        f(vl);
        int pows = 0;
        while (cur_tok == POW) {
            eat();
            powd = true;
            p = new MBox();
            MJLabel.decFont();
            f(p);
            pows++;
            vl = new MPower(vl, ridBrace2(p));
        }
        while (0 != pows--) {
            MJLabel.incFont();
        }
        vl = downTrigoPower(vl);
        if (powd) {
            target.add(vl);
        } else {
            Component cl[] = vl.getComponents();
            vl.removeAll();
            for (int i = 0; i < cl.length; i++) {
                target.add(cl[i]);
            }
        }
    }

    void f(MBox target) {
        MBox vl;
        int cf;
        switch (cur_tok) {
            case OBJ:
                vl = new MStub();
                JComponent c = getByName(cur_val);
                if (c == null) {
                    target.add(new MJLabel(cur_val));
                } else {
                    ((MStub) vl).setUserObject(c);
                    target.add(vl);
                }
                eat();
                break;
            case VARB:
                eat();
                if (cur_tok == '[') {
                    MJLabel.decFont();
                }
                target.add(new MJLabel(Greece.make(cur_val)));
                break;
            case NUM:
                eat();
                target.add(new MJLabel(cur_val));
                break;
            case OP:
                vl = new MBraced();
                eat();
                e(vl);
                if (cur_tok != CP) {
                    break;
                }
                eat();
                target.add(vl);
                break;
            case '{':
                vl = new MFBraced();
                eat();
                e(vl);
                if (cur_tok != '}') {
                    break;
                }
                eat();
                target.add(vl);
                break;

            case '[':
                vl = new MBox();
                eat();
                if (cur_tok == ']') {
                    vl.add(new MJLabel(" "));
                    break;
                }
                e(vl);
                if (cur_tok != ']') {
                    break;
                }
                eat();
                target.add(vl);
                break;
            case FUNC:
                cf = cur_fun;
                FuncInfo lr = last_read_func;
                // System.err.println(String.valueOf(lr));
                vl = new MBox();
                eat();
                if (cur_tok == FT.LIMIT || cur_tok == FT.EXP) {
                    MJLabel.decFont();
                }
                e(vl);
                if (cur_tok == FT.LIMIT || cur_tok == FT.EXP) {
                    MJLabel.incFont();
                }
                if (cur_tok != CP && cf != FT.LIMIT && cf != FT.SUMMA
                        && cf != FT.ROOT && cf != FT.DIFF && cf != FT.INTG
                        && cf != FT.POINTS) {
                    break;
                }
                calc_func(cf, vl, target, lr);
                eat();
                break;
            default:
                break;
        }
    }
    String theFormula;

    public FormulaRender() {
        super();
    }
    edu.sumdu.dl.common.RuntimeVars rtv = null;
    java.util.ArrayList usedUserObjects = new ArrayList();
    java.util.ArrayList usedUserNames = new ArrayList();

    public void setRTV(edu.sumdu.dl.common.RuntimeVars rtv) {
        this.rtv = rtv;
    }

    JComponent getByName(String name) {
        if (rtv == null) {
            return null;
        }
        Object ob = rtv.getVar(name);
        if (!(ob instanceof JComponent)) {
            return null;
        }
        usedUserObjects.add(ob);
        usedUserNames.add(name);
        return (JComponent) ob;
    }

    public FormulaRender(String s) {
        super();
        AS.setCol(Color.black);
        setFormula(s);
    }

    public String asHTML() {
        StringBuffer s = new StringBuffer(theFormula);
        if (usedUserObjects.size() > 0) {
            s.append("<br>");
            for (int i = 0; i < usedUserObjects.size(); i++) {
                s.append("\n<br>@" + usedUserNames.get(i) + "@:<br>\n");
                s.append(edu.sumdu.dl.common.DumpSwing.dump((JComponent) usedUserObjects.get(i)));
            }
        }
        return s.toString();
    }

    public void setFormula(String s) {
        eval(s);
    }
    MBox eqBox;

    public void eval(String s) {
        // String frm[]=s.split("=");
        theFormula = s.replaceAll("\\.\\+-\\.", "" + PLUSMINUS).replaceAll(
                "--", "+").replaceAll("\\+-", "-").replaceAll("\\+\\+", "+").replaceAll("-\\+", "-").replaceAll("!=", "" + NEQ).replaceAll(
                "<=", "" + LEQ).replaceAll(">=", "" + GEQ).replaceAll(
                "~=", "" + ASYMP);
        MJLabel.fontSize = 12;
        MBox vl = new MBox(true);
        removeAll();
        /*
         * for(int i=0;i<frm.length;i++){ buf=frm[i]; cur=0; notOK=false; eat();
         * e(vl); if(frm.length>1 && i<frm.length-1) vl.add(new MJLabel(" = "));
         * }
         */
        cur = 0;
        notOK = false;
        buf = theFormula;
        eat();
        e(vl);
        vl.buildTree();
        add(vl);
        eqBox = vl;
    }

    String strtod(int idx) {
        String s = buf.substring(idx);
        // This should match correctly written number
        Pattern p = Pattern.compile("(\\d+\\.?\\d*[eE][-+]?\\d+|\\d+\\.?\\d*)");
        // ([+-]?\d+\.?\d*|[+-]?\d+\.?\d*[eE]?[+-]?\d+)
        Matcher m = p.matcher(s);
        if (m.find()) {
            return m.group();
        } else {
            return "" + s.charAt(0);
        }
    }

    public byte[] getImageDump() {
        Dimension dim = eqBox.getPreferredSize();
        // dim=new Dimension(400,400);
        /*
         * BufferedImage img2 = (BufferedImage)eqBox.createImage(dim.width,
         * dim.height); System.err.println(img2);
         */
        BufferedImage img2 = new BufferedImage(dim.width, dim.height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img2.createGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, dim.width, dim.height);
        g2.setColor(Color.black);
        eqBox.setForeground(Color.black);
        eqBox.paint(g2);

        BufferedImage img3 = new BufferedImage(dim.width, dim.height,
                BufferedImage.TYPE_INT_ARGB);
        int[] rgbArray = new int[dim.width * dim.height];
        img2.getRGB(0, 0, dim.width, dim.height, rgbArray, 0, dim.width);
        for (int i = 0; i < rgbArray.length; i++) {
            // System.err.print(":"+rgbArray[i]);
            rgbArray[i] = (255 - (rgbArray[i] & 0x000000FF)) << 24;
        }
        img3.setRGB(0, 0, dim.width, dim.height, rgbArray, 0, dim.width);

        PngEncoderB png = new PngEncoderB(img2, PngEncoderB.ENCODE_ALPHA, true,
                PngEncoderB.FILTER_NONE, 9);
        byte[] pngbytes = png.pngEncode();
        return pngbytes;
    }
}

class MJLabel extends JLabel {

    static int fontSize = 12;

    public static void decFont() {
        fontSize -= 2;
        if (fontSize < 6) {
            fontSize = 6;
        }
    }

    public static void incFont() {
        fontSize += 2;
    }

    MJLabel(String s) {
        super(s);
        setForeground(AS.getCol());
        setFont(new Font("dialog", Font.BOLD | Font.ITALIC, fontSize));
    }

    MJLabel(String s, int style) {
        super(s);
        setForeground(AS.getCol());
        setFont(new Font("dialog", style, fontSize));
    }

    public void paintComponent(Graphics g) {
        // if(isRoot) FormulaRender.printTree(this,0);
        // setB();
        // setBackground(new Color(255,255,255));
        if (g instanceof java.awt.Graphics2D) {
            ((java.awt.Graphics2D) g).setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        super.paintComponent(g);
    }
}

class Greece {

    static String lets[] = new String[]{"alpha", "beta", "gamma", "delta",
        "eps", "zeta", "eta", "theta", "iota", "kappa", "lambda", "mu",
        "nu", "ksi", "o", "pi", "ro", "zu", "sigma", "tau", "ips", "phi",
        "chi", "psi", "omega"};

    public static String make(String s) {
        if (s.equals("infty")) {
            return String.valueOf((char) 0x221e);
        }
        String s1, s2 = null, s3 = "", s4 = "";
        Pattern p = Pattern.compile("([A-Za-z]+)(\\d*)('*)(\\w*)");
        Matcher m = p.matcher(s);
        if (m.find()) {
            s1 = m.group(1);
            s2 = m.group(2);
            s3 = m.group(3);
            s4 = m.group(4);
        } else {
            s1 = s;
        }
        int k = 945;
        char c = s1.charAt(0);
        boolean bln = s1.equals(s1.toUpperCase());
        if (bln) {
            k -= 32;
        }
        for (int i = 0; i < lets.length; i++) {
            if (lets[i].equals(s1.toLowerCase())) {
                c = (char) (k + i);
            }
        }
        if ((int) c >= 913) {
            if (s2 != null || s3 != null) {
                return "<html>" + c + "<sub>" + s2 + s4 + "</sub>" + s3
                        + "</html>";
            } else {
                return "" + c;
            }
        } else {
            return s.replaceAll("(.)([^']+)('*)(\\w*)",
                    "<html>$1<sub>$2$4</sub>$3</html>");
        }
    }
}
