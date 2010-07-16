package edu.sumdu.dl.common;

import java.awt.*;

// метка со стрелкой вверху - вектор
public class DTVectorLabel extends DTLabel {

    public DTVectorLabel() {
        super();
    }

    public DTVectorLabel(String s) {
        super(s);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        g.drawLine(d.width - 4, 0, d.width, 1);
        g.drawLine(0, 1, d.width, 1);
        g.drawLine(d.width - 4, 2, d.width, 1);
    }

    public String toXML() {
        return "<vector>" + Tool.escapeXML(getText()) + "</vector>";
    }
}
