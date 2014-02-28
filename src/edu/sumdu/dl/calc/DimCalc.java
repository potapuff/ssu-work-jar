package edu.sumdu.dl.calc;

import edu.sumdu.dl.common.CMath;
import edu.sumdu.dl.common.Localizer;
import edu.sumdu.dl.common.TLocalized;
import java.util.Map;
import java.util.TreeMap;

/*
 Simple calculator
 e: -p | p | e+p | e-p
 p: t | p*t | p/t
 t: f | t^f
 f: NUM | (e) | func(e) 
 */
public class DimCalc {

    public String errorLine;
    public boolean notOK;
    private Localizer localizer;
    private Map<String, String> calcMessages = new TreeMap<String, String>();

    private void initMessagesMap() {
        calcMessages.put("calc.message.1", "Нет параметров для функции");
        calcMessages.put("calc.message.2", "кв. корень из отрицательного");
        calcMessages.put("calc.message.3", "Вызов select() для неизвестной величины");
        calcMessages.put("calc.message.4", "Недостаточно параметров вызова ф-и");
        calcMessages.put("calc.message.5", "Логарифм от отрицательного!");
        calcMessages.put("calc.message.6", "Неизвестная функция");
        calcMessages.put("calc.message.7", "нет выражения");
        calcMessages.put("calc.message.8", "Деление на ноль");
        calcMessages.put("calc.message.9", "Использована неопределенная величина ");
        calcMessages.put("calc.message.10", "] пропущена");
        calcMessages.put("calc.message.11", "')' пропущена");
        calcMessages.put("calc.message.12", "недопустимый символ");
    }

    DD error(String s) {
        if (errorLine.length() > 0) {
            errorLine = errorLine + "," + s;
        } else {
            errorLine = errorLine + s;
        }
        errorLine += " '" + la.getBuf().substring(0, la.getPos()) + "'\n";
        notOK = true;
        return new DD(1.0);
    }

    DD calc_func(int tip, DD[] x) {
        if (x[0] == null && tip != LexAnal.FT_SELECT) {
            return error(getMessage("calc.message.1"));
        }
        switch (tip) {
            case LexAnal.FT_SQRT:
                if (x[0].value > 0) {
                    x[0].pow(0.5);
                    return x[0];
                } else {
                    return error(getMessage("calc.message.2"));
                }
            case LexAnal.FT_SELECT:
                la.eat();
                DD ret;
                if (la.cur_tok != LexAnal.VARB || vt.getVar(la.cur_str) == null) {
                    ret = error(getMessage("calc.message.3"));
                } else {
                    ret = new DD(vt.getVar(la.cur_str).select(vt));
                }
                la.eat();
                return ret;
            case LexAnal.FT_COS:
                return new DD(Math.cos(x[0].value));
            case LexAnal.FT_SIN:
                return new DD(Math.sin(x[0].value));
            case LexAnal.FT_SEC:
                return new DD(1 / Math.cos(x[0].value));
            case LexAnal.FT_COSEC:
                return new DD(1 / Math.sin(x[0].value));
            case LexAnal.FT_SIN2:
                return new DD(Math.sin(x[0].value) * Math.sin(x[0].value));
            case LexAnal.FT_SIN3:
                return new DD(Math.sin(x[0].value) * Math.sin(x[0].value)
                        * Math.sin(x[0].value));
            case LexAnal.FT_COS2:
                return new DD(Math.cos(x[0].value) * Math.cos(x[0].value));
            case LexAnal.FT_COS3:
                return new DD(Math.cos(x[0].value) * Math.cos(x[0].value)
                        * Math.cos(x[0].value));
            case LexAnal.FT_COSH:
                return new DD(
                        (Math.exp(x[0].value) + Math.exp(-x[0].value)) / 2);
            case LexAnal.FT_SINH:
                return new DD(
                        (Math.exp(x[0].value) - Math.exp(-x[0].value)) / 2);
            case LexAnal.FT_TG:
                return new DD(Math.tan(x[0].value));
            case LexAnal.FT_CTG:
                return new DD(1 / Math.tan(x[0].value));
            case LexAnal.FT_ATG:
                return new DD(Math.atan(x[0].value));
            case LexAnal.FT_ACTG:
                return new DD(Math.atan(1 / x[0].value));
            case LexAnal.FT_ACOS:
                return new DD(Math.acos(x[0].value));
            case LexAnal.FT_ASIN:
                return new DD(Math.asin(x[0].value));
            case LexAnal.FT_ABS:
                return new DD(Math.abs(x[0].value));
            case LexAnal.FT_EXP:
                return new DD(Math.exp(x[0].value));
            case LexAnal.FT_DX:
                return new DD(x[0].value * 0.005);

            case LexAnal.FT_CRND:
                if (x[1] == null) {
                    return error(getMessage("calc.message.4"));
                }
                return new DD(CMath.crandom(x[0].value, x[1].value));
            case LexAnal.FT_NRND:
                if (x[1] == null || x[2] == null) {
                    return error(getMessage("calc.message.4"));
                }
                return new DD(CMath.nrandom(x[0].value, x[1].value, x[2].value));
            case LexAnal.FT_DIFF:
                if (x[1] == null || x[2] == null) {
                    return error(getMessage("calc.message.4"));
                }
                return dLogic(CMath.diff(x[0].value, x[1].value, x[2].value));
            case LexAnal.FT_DIFFP:
                if (x[1] == null || x[2] == null) {
                    return error(getMessage("calc.message.4"));
                }
                return dLogic(CMath.diffp(x[0].value, x[1].value, x[2].value));
            case LexAnal.FT_LN:
                if (x[0].value < 0) {
                    return error(getMessage("calc.message.5"));
                } else {
                    return new DD(Math.log(x[0].value));
                }
            case LexAnal.FT_LG:
                if (x[0].value < 0) {
                    return error(getMessage("calc.message.5"));
                } else {
                    return new DD(Math.log(x[0].value) / Math.log(10.0));
                }

            default:
                return error(getMessage("calc.message.6"));
        }
    }

    DD dLogic(boolean cond) {
        if (cond) {
            return new DD(1.0);
        } else {
            return new DD(0.0);
        }
    }

    // TERM: FACT ([*/&&] FACT)
    // FACT: ! FACT | NUM | var | var[ SIMPLE ] | func( e_list ) | ( EXPR )
    // e_list : simple ( ',' simple)*
    static boolean isTrue(DD d) {
        return isTrue(d.value);
    }

    static public boolean isTrue(double d) {
        return !CMath.diff(d, 0, 0.0001);
    }

    DD expr() {
        DD sim = simple();
        switch (la.cur_tok) {
            case LexAnal.LT:
                la.eat();
                return dLogic(sim.value < simple().value);
            case LexAnal.GT:
                la.eat();
                return dLogic(sim.value > simple().value);
            case LexAnal.NE:
                la.eat();
                return dLogic(!CMath.diffp(sim.value, simple().value, 0.001));
            case LexAnal.LE:
                la.eat();
                return dLogic(sim.value <= simple().value);
            case LexAnal.GE:
                la.eat();
                return dLogic(sim.value >= simple().value);
            case LexAnal.EQ:
                la.eat();
                return dLogic(CMath.diffp(sim.value, simple().value, 0.001));
            default:
                return sim;
        }
    }

    DD simple() {
        DD vl;
        if (la.cur_tok == LexAnal.EOL) {
            return error(getMessage("calc.message.7"));
        }
        if (la.cur_tok == LexAnal.MINUS) {
            la.eat();
            vl = p();
            vl.value = -vl.value;
        } else {
            vl = p();
        }
        for (;;) {
            switch (la.cur_tok) {
                case LexAnal.MINUS:
                    la.eat();
                    vl.sub(p());
                    break;
                case LexAnal.PLUS:
                    la.eat();
                    vl.add(p());
                    break;
                case LexAnal.OR:
                    la.eat();
                    vl = dLogic(isTrue(p()) || isTrue(vl));
                    break;
                default:
                    return vl;
            }
        }
    }

    DD p() {
        DD left, lp;
        left = t();
        for (;;) {
            switch (la.cur_tok) {
                case LexAnal.MULT:
                    la.eat();
                    left.mul(t());
                    break;
                case LexAnal.DIVD:
                    la.eat();
                    lp = t();
                    if (lp.value != 0) {
                        left.div(lp);
                    } else {
                        return error(getMessage("calc.message.8"));
                    }
                    break;
                case LexAnal.AND:
                    la.eat();
                    left = dLogic(isTrue(t()) && isTrue(left));
                    break;
                default:
                    return left;
            }
        }
    }

    DD t() {
        DD vl, p;
        vl = f();
        while (la.cur_tok == LexAnal.POW) {
            la.eat();
            p = f();
            vl.pow(p.value);
        }
        return vl;
    }

    DD f() {
        DD vl;
        int cf;
        switch (la.cur_tok) {
            case LexAnal.NOT:
                la.eat();
                return dLogic(!isTrue(f()));

            case LexAnal.VARB:
                if (!vt.getVar(la.cur_str).isSet()) {
                    error(getMessage("calc.message.9" + la.cur_str));
                }
                vl = new DD(vt.getVar(la.cur_str));
                la.eat();
                return vl;
            case LexAnal.AVAR:// var[simple]
                String cv = la.cur_str;
                if (!vt.getVar(la.cur_str).isSet()) {
                    error(getMessage("calc.message.9" + la.cur_str));
                }
                la.eat();
                DD d = simple();
                if (la.cur_tok == LexAnal.CB) {
                    la.eat();
                } else {
                    return error(getMessage("calc.message.10"));
                }
                return new DD(vt.getda(cv, d.value), vt.getVar(cv).varDim);
            case LexAnal.NUM:
                vl = new DD(la.cur_val);
                la.eat();
                return vl;
            case LexAnal.OP:
                la.eat();
                vl = expr();
                if (la.cur_tok != LexAnal.CP) {
                    return error(getMessage("calc.message.11"));
                }
                la.eat();
                return vl;
            case LexAnal.FUNC:
                int cuf = la.cur_fun;
                int co = 0;
                DD params[] = new DD[10];
                if (cuf != LexAnal.FT_SELECT) {
                    do {
                        la.eat();
                        params[co++] = expr();
                    } while (la.cur_tok == ',');
                }
                DD ret = calc_func(cuf, params);
                if (la.cur_tok != LexAnal.CP) {
                    return error(getMessage("calc.message.11"));
                }
                la.eat();
                return ret;
            default:
                // return new DD(1.0);
                return error(getMessage("calc.message.12"));
        }
    }
    VarTable vt;
    LexAnal la;
    DD last_value;
    
    private DimCalc(){
        initMessagesMap();
    }
    
    public DimCalc(VarTable vart) {
        this();
        vt = vart;
    }

    public DimCalc(String formula, VarTable vart) {
        this(vart);
        la = new LexAnal(formula, vart);
    }
    
    public void setFormula(String formula){
        la = new LexAnal(formula, vt);
    }

    public double eval() {
        errorLine = "";
        notOK = false;
        la.start();
        la.eat();
        last_value = expr();

        return last_value.value;
    }
    
    private String getMessage(String key) {
        if (localizer != null) {
            return localizer.getMessage(key);
        } else {
            return calcMessages.get(key);
        }
    }

    public void refreshLang(Localizer t) {
        localizer = t;
    }

    class DD {

        public VarDim dim;
        public double value;

        DD() {
            value = 0;
            dim = new VarDim("");
        }

        DD(Variable v) {
            value = v.doubleValue();
            dim = new VarDim(v.varDim);
        }

        DD(double v, String dm) {
            value = v;
            dim = new VarDim(dm);
        }

        DD(double v) {
            value = v;
            dim = new VarDim("");
        }

        public void add(DD o) {
            value += o.value;
            if (!dim.equals(o.dim)) {
                dim = new VarDim("X1");
            }
        }

        public void sub(DD o) {
            value -= o.value;
            if (!dim.equals(o.dim)) {
                dim = new VarDim("X1");
            }
        }

        public void mul(DD o) {
            value *= o.value;
            dim.mult(o.dim);
        }

        public void div(DD o) {
            value /= o.value;
            dim.divide(o.dim);
        }

        public void pow(DD o) {
            this.pow((o.value));
        }

        public void pow(double ov) {
            dim.power((float) ov);
            value = Math.pow(value, ov);
        }
    }
}
