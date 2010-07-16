package edu.sumdu.dl.common;

import javax.swing.BorderFactory;
import java.awt.*;
import java.util.ArrayList;

// Панель - квадратый корень
public class DTSqrt extends DTPanel {

    public DTSqrt() {
        super(new FlowLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void paintComponent(Graphics g) {
        Dimension d = getSize();
        super.paintComponent(g);
        g.drawLine(4, 0, d.width, 0);
        g.drawLine(4, 0, 4, d.height);
        g.drawLine(0, d.height / 2, 4, d.height);
        g.drawLine(d.width - 1, 0, d.width - 1, 5);
        g.drawLine(d.width - 2, 0, d.width - 2, 5);
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        super.fromXNode(node, target, fields, elements, l, pv);
        return false;
    }

    public String toXML() {
        return "<sqrt>" + dump() + "</sqrt>";
    }
}
