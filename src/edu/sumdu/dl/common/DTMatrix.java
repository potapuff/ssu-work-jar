package edu.sumdu.dl.common;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// матрица W строк H столбцов
public class DTMatrix extends DTBracedPanel {

    public DTMatrix() {
        super();
        GridLayout gl = new GridLayout(1, 1);
        setLayout(gl);
        setType(0);
        setRows(1);
        setCols(1);
        gl.setVgap(3);
        gl.setHgap(3);
    }
    int rowCount = 0, colCount = 0;

    public void setRows(int H) {
        LayoutManager lm = this.getLayout();
        rowCount = H;
        if (lm instanceof GridLayout) {
            ((GridLayout) lm).setRows(H);
        }
    }

    public void setCols(int H) {
        LayoutManager lm = this.getLayout();
        colCount = H;
        if (lm instanceof GridLayout) {
            ((GridLayout) lm).setColumns(H);
        }
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        super.fromXNode(node, target, fields, elements, l, pv);
        setRows(Tool.toInt(node.getAttr("rows")));
        setCols(Tool.toInt(node.getAttr("cols")));
        return false;
    }

    public String getSType() {
        return getSymbolicType(getType());
    }

    public String toXML() {
        return "<matrix type=\"" + getSType() + "\" rows=\"" + rowCount
                + "\" cols=\"" + colCount + "\">" + dump() + "</matrix>";
    }

    public Component add(Component comp) {
        if (comp instanceof JLabel) {
            ((JLabel) comp).setHorizontalAlignment(JLabel.RIGHT);
        }
        return super.add(comp);
    }
}
