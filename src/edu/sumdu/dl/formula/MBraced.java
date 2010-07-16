package edu.sumdu.dl.formula;

import javax.swing.*;
import java.awt.*;

public class MBraced extends MBox {

    private Color col;

    MBraced() {
        super();
        insets = new Insets(0, 8, 0, 8);
        setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        // col=AS.getCol();
    }

    public void paintComponent(Graphics g) {
        int sz;
        if (msize.width <= 40 && msize.height <= 30) {
            sz = 6;
        } else {
            sz = 15;
        }
        // super.paintComponent(g.create(sz,0,msize.width-sz*2,msize.height));
        // g.setColor(col);
        super.paintComponent(g);
        g.drawArc(3, 0, sz - 1, msize.height, 90, 180);
        // g.drawArc(1,0,sz-2,msize.height,90,180);
        g.drawArc(msize.width - sz - 2, 0, sz - 1, msize.height, -90, 180);
        // g.drawArc(msize.width-sz,0,sz-2,msize.height,-90,180);
    }
}
