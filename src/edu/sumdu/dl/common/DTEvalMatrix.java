package edu.sumdu.dl.common;

import java.util.ArrayList;
import java.awt.*;

public class DTEvalMatrix extends DTMatrix {

    public DTEvalMatrix() {
        super();
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        setType("" + node.getAttr("type"));
        setSource(node.getAttr("source"));
        try {
            fromIntMatrix((int[][]) pv.getVar(getSource()));
        } catch (Exception exe) {
            // exe.printStackTrace();
            try {
                fromDoubleMatrix((double[][]) pv.getVar(getSource()));
            } catch (Exception exe1) {
                exe1.printStackTrace();
            }
        }
        return true;
    }
    private String source = null;

    public void fromDoubleMatrix(double M[][]) {
        setRows(M.length);
        setCols(M[0].length);
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                add(new javax.swing.JLabel("" + M[i][j]));
            }
        }
    }

    public void fromIntMatrix(int[][] M) {
        setRows(M.length);
        setCols(M[0].length);
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                add(new javax.swing.JLabel("" + M[i][j]));
            }
        }
    }

    public void setSource(String s) {
        source = s;
    }

    public String getSource() {
        return source;
    }

    public String toXML() {
        return "<evalmatrix type=\"" + getSymbolicType(getType())
                + "\" source=\"" + getSource() + "\"/>";
    }
}
