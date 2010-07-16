package edu.sumdu.dl.formula;

import javax.swing.*;
import java.awt.*;

public class MFBraced extends MBox {

    private Color col;

    MFBraced() {
        super();
        insets = new Insets(0, 10, 0, 10);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        // col=AS.getCol();
    }

    public void paintComponent(Graphics g) {
        int w;
        if (msize.width <= 40 && msize.height <= 30) {
            w = 8;
        } else {
            w = 16;
        }
        super.paintComponent(g);
        int sz = msize.width;
        int h = msize.height;
        g.drawArc(w / 2, 0, w / 2, h, 87, 90);
        g.drawArc(0, h / 2 - 10, w / 2, 10, -90, 90);
        g.drawArc(0, h / 2, w / 2, 10, 0, 90);
        g.drawArc(w / 2, 0, w / 2, h, 183, 90);

        g.drawArc(sz - w + 1, 0, w / 2, h, 3, 87);
        g.drawArc(sz - w / 2, h / 2 - 10, w / 2, 10, 180, 90);
        g.drawArc(sz - w / 2, h / 2, w / 2, 10, 90, 90);
        g.drawArc(sz - w + 1, 0, w / 2, h, -93, 90);

    }
}
