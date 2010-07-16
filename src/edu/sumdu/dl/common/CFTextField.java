package edu.sumdu.dl.common;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;

public class CFTextField extends JTextField implements TStorable, NodeStorable,
        FocusListener, CheckField {

    public CFTextField() {
        super();
        addFocusListener(this);
    }

    public String getState() {
        return "<asktext>" + Tool.escapeXML(getText()) + "</asktext>";
    }

    public void setState(XNode n) {
        setText(n.getContent());
    }
    private XNode node;
    private RuntimeVars pv;

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        this.node = node;
        this.pv = pv;
        fields.add(this);
        elements.add(this);
        setColumns(Tool.toInt(node.getAttr("width")));
        String val = node.getAttr("eval");
        if ("eval".equals(val)) {
            setEval(val);
            setValue(pv.getVar(node.getAttr("value")).toString());
        } else {
            setValue(node.getAttr("value"));
        }
        return true;
    }
    private String eval = null;
    private String value;

    void setValue(String s) {
        value = s;
    }

    String getValue() {
        return value;
    }

    void setEval(String s) {
        eval = s;
    }

    public String toXML() {
        String valueStr = "";
        if (eval != null) {
            valueStr = " eval=\"eval\" value=\"" + Tool.escapeXML(eval) + "\" ";
        } else {
            valueStr = "value=\"" + Tool.escapeXML(value) + "\" ";
        }
        return "<asktext width=\"" + getColumns() + "\" " + valueStr + " />";
    }

    public boolean isCorrect() {
        if (node != null) {
            String val = node.getAttr("eval");
            if ("eval".equals(val)) {
                setEval(val);
                setValue(pv.getVar(node.getAttr("value")).toString());
            } else {
                setValue(node.getAttr("value"));
            }
        }

        String t = getText().replaceAll("\\s+", " ").replaceAll("^\\s+", "").replaceAll("\\s+$", "").toUpperCase();
        String t1 = value.replaceAll("\\s+", " ").replaceAll("^\\s+", "").replaceAll("\\s+$", "").toUpperCase();
        return t.equals(t1);
    }

    public void focusLost(FocusEvent e) {
        if (isCorrect()) {
            this.setForeground(new Color(20, 200, 20));
        } else {
            this.setForeground(Color.red);
        }
    }

    public void focusGained(FocusEvent e) {
        this.setForeground(Color.black);
    }
}
