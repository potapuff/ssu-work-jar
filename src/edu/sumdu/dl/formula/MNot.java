package edu.sumdu.dl.formula;

import java.awt.*;

public class MNot extends MBox {

    private Color col;

    MNot() {
        super();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        g.drawLine(0, 0, d.width, 0);
    }
}
