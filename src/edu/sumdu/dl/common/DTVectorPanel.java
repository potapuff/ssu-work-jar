package edu.sumdu.dl.common;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// панель со стрелкой вверху - вектор
public class DTVectorPanel extends DTPanel {

    public DTVectorPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        g.drawLine(d.width - 4, 0, d.width, 1);
        g.drawLine(0, 1, d.width, 1);
        g.drawLine(d.width - 4, 2, d.width, 1);
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        super.fromXNode(node, target, fields, elements, l, pv);
        return false;
    }

    public String toXML() {
        return "<vectorpanel>" + dump() + "</vectorpanel>";
    }
}
