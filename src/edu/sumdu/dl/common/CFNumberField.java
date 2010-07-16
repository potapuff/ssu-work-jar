package edu.sumdu.dl.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// текстовое поле ввода заданных числовых данных, проверка с заданной точностью
public class CFNumberField extends JTextField implements CheckField,
        FocusListener, TStorable, NodeStorable {

    private double value, eps;       // требуемое значение, точность
    private boolean perc;             // perc=true - относит. точность false -
    // абс. точность
    private boolean need_check = true; // perc=true - относит. точность false -

    // абс. точность
    public void setValue(double val) {
        value = val;
    }

    public double getValue() {
        return value;
    }

    public void setEps(double val) {
        eps = val;
    }

    public double getEps() {
        return eps;
    }

    public void setPerc(boolean val) {
        perc = val;
    }

    public boolean getPerc() {
        return perc;
    }

    public void setNeedCheck(boolean val) {
        need_check = val;
    }

    public boolean getNeedCheck() {
        return perc;
    }

    public CFNumberField() {
        super();
        addFocusListener(this);        
        setFont(new Font("Calibri", Font.BOLD, 16));
        setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210), 1));
    }

    public CFNumberField(String s, int w) { // начальное содержимое, ширина
        this();
        setText(s);
        setColumns(w);
        setNeedCheck(false);
    }

    public CFNumberField(double v, double eps) { // требуемое значение, точность
        this();
        setColumns(3);
        setData(v, eps);
    }

    // требуемое значение, точность, тип проверки
    public CFNumberField(double v, double eps, boolean p) {
        this(v, eps);
        setPerc(p);
    }

    // требуемое значение, точность, ширина
    public CFNumberField(double v, double eps, int s) {
        this();
        setColumns(s);
        setData(v, eps);
    }

    // требуемое значение, точность, ширина поля, тип проверки
    public CFNumberField(double v, double eps, int s, boolean p) {
        this(v, eps);
        setPerc(p);
    }

    // сохранение данных в объекте, настройка шрифта
    private void setData(double v, double eps) {
        setFont(new Font("Arial", Font.BOLD, 14));
        setValue(v);
        setEps(eps);
        addFocusListener(this);
    }

    // изменение цвета текста в зависимости от правильности при уходе с поля
    public void focusLost(FocusEvent e) {
        if (need_check) {
            if (isCorrect()) {
                setForeground(Color.GREEN);
            } else {
                setForeground(Color.RED);
            }
        }
    }

    // изменение цвета текста при переходе на поле
    public void focusGained(FocusEvent e) {
        setForeground(Color.black);      
    }

    // проверка правильности введенных данных
    public boolean isCorrect() {
        if (valuePropName != null) {
            setValue(Tool.toDouble(myRTV.getVar(valuePropName).toString()));
        }

        if (getText().length() > 0) {

            try {
                double gv = Tool.getFractDouble(getText().replaceAll(",", "."));
                return perc ? CMath.diffp(gv, this.value, this.eps) : CMath.diff(gv, this.value, this.eps);
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getState() {
        return "<ask>" + Tool.escapeXML(getText()) + "</ask>";
    }

    public void setState(XNode node) {
        setText(node.getContent());
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        fields.add(this);
        elements.add(this);
        myRTV = pv;
        setEps(Tool.toDouble(node.getAttr("eps")));
        setPerc(Tool.toBool(node.getAttr("perc")));
        if (node.getAttr("eval") != null && !("".equals(node.getAttr("eval")))) {
            setValue(Tool.toDouble(pv.getVar(node.getAttr("eval")).toString()));
            setValuePropName(node.getAttr("eval"));
        } else {
            setValue(Tool.toDouble(node.getAttr("value")));
        }
        setColumns(Tool.toInt(node.getAttr("width")));
        setNeedCheck(Tool.toBool(node.getAttr("check")));
        return true;
    }
    private String propName;

    public String getPropName() {
        return propName;
    }

    public void setPropName(String s) {
        propName = s;
    }
    private String valuePropName;

    public String getValuePropName() {
        return valuePropName;
    }

    public void setValuePropName(String s) {
        valuePropName = s;
    }
    RuntimeVars myRTV;
    private String contentType = "flow";

    public void setContentType(String s) {
        contentType = s;
    }

    public String getContentType() {
        return contentType;
    }

    String getContent() {
        if (contentType.equals("eval")) {
            return propName;
        } else {
            return getText();
        }
    }

    public String toXML() {
        String valueStr = "";
        if (valuePropName != null) {
            valueStr = " eval=\"" + valuePropName + "\" ";
        } else {
            valueStr = " value=\"" + value + "\"";
        }
        return "<ask type=\"" + contentType + "\" width=\"" + getColumns()
                + "\" " + " check=\"" + need_check + "\" " + " perc=\""
                + getPerc() + "\" " + " eps=\"" + getEps() + "\" " + valueStr
                + ">" + getContent() + "</ask>";
    }
}
