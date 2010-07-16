package edu.sumdu.dl.common;

import java.util.ArrayList;
import java.awt.*;

public class DTInputMatrix extends DTMatrix {

    public DTInputMatrix() {
        super();
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        setType("" + node.getAttr("type"));
        setSource(node.getAttr("source"));
        try {
            fromIntMatrix((int[][]) pv.getVar(getSource()), fields);
        } catch (Exception exe) {
            // exe.printStackTrace();
            try {
                fromDoubleMatrix((double[][]) pv.getVar(getSource()), fields);
            } catch (Exception exe1) {
                exe1.printStackTrace();
            }
        }
        return true;
    }

    public void fromIntMatrix(int[][] M, ArrayList fields) {
        setRows(M.length);
        setCols(M[0].length);
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                fields.add(this.add(new CFNumberField((double) M[i][j], 1e-3,
                        4, true)));
            }
        }
    }

    public void fromDoubleMatrix(double M[][], ArrayList fields) {
        setRows(M.length);
        setCols(M[0].length);
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                fields.add(this.add(new CFNumberField(M[i][j], 1e-3, 5, true)));
            }
        }
    }
    private String source = null;

    public void setSource(String s) {
        source = s;
    }

    public String getSource() {
        return source;
    }

    public String toXML() {
        return "<inputmatrix type=\"" + getSymbolicType(getType())
                + "\" source=\"" + getSource() + "\"/>";
    }
}
