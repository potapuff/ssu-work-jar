package edu.sumdu.dl.common;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DTBracedPanel extends DTPanel {

    private int typ = 0;
    public static final int NO_BRACE = 0;
    public static final int LEFT_BRACED = 1;
    public static final int RIGHT_BRACED = 2;
    public static final int BOTH_BRACED = 3;
    public static final int DETERM = 4;
    public static final int FIGURED = 5;
    public static final String[] symbolicTypes = new String[]{"none", "(",
        ")", "()", "||", "{"};

    public int getType() {
        return typ;
    }

    public void setType(int newTip) {
        typ = newTip;
        // System.out.println("Tip:"+typ);
    }

    public String getSymbolicType(int tp) {
        if (tp < 0 || tp >= symbolicTypes.length) {
            return "none";
        } else {
            return symbolicTypes[tp];
        }
    }

    public void setType(String tip) {
        if (tip.equals("{")) {
            setType(FIGURED);
        } else if (tip.equals("||")) {
            setType(DETERM);
        } else if (tip.equals("()")) {
            setType(BOTH_BRACED);
        } else if (tip.equals("(")) {
            setType(LEFT_BRACED);
        } else if (tip.equals(")")) {
            setType(RIGHT_BRACED);
        } else {
            setType(NO_BRACE);
        }
    }

    public DTBracedPanel() {
        super();
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

    }

    public DTBracedPanel(LayoutManager lm) {
        super(lm);
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        if (typ == LEFT_BRACED || typ == BOTH_BRACED) {
            g.drawArc(0, 0, 20, d.height, 90, 180);
            g.drawArc(0, 0, 19, d.height, 90, 180);
        }
        if (typ == RIGHT_BRACED || typ == BOTH_BRACED) {
            g.drawArc(d.width - 20, 0, 18, d.height, -90, 180);
            g.drawArc(d.width - 20, 0, 19, d.height, -90, 180);
        }
        if (typ == DETERM) {
            g.drawLine(1, 0, 1, d.height);
            g.drawLine(2, 0, 2, d.height);
            g.drawLine(d.width - 2, 0, d.width - 2, d.height);
            g.drawLine(d.width - 1, 0, d.width - 1, d.height);
        }
        if (typ == FIGURED) {
            int w = 20;
            int h = d.height;
            g.drawArc(w / 2, 0, w / 2, h, 90, 87);
            g.drawArc(0, h / 2 - 10, w / 2, 10, -90, 90);
            g.drawArc(w / 2 + 1, 0, w / 2, h, 90, 87);
            g.drawArc(1, h / 2 - 10, w / 2, 10, -90, 90);
            g.drawArc(0, h / 2, w / 2, 10, 0, 90);
            g.drawArc(w / 2, 0, w / 2, h, 183, 87);
            g.drawArc(1, h / 2, w / 2, 10, 0, 90);
            g.drawArc(w / 2 + 1, 0, w / 2, h, 183, 87);
        }
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        super.fromXNode(node, target, fields, elements, l, pv);
        setType("" + node.getAttr("type"));
        return false;
    }

    public String toXML() {
        return "<bracedpanel type=\"" + getSymbolicType(typ) + "\">" + dump()
                + "</bracedpanel>";
    }
}
