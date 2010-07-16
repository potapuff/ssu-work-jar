package edu.sumdu.dl.formula;

import javax.swing.*;
import java.awt.*;

public class MSQRT extends MBox {

    private Color col;

    MSQRT() {
        super();
        insets = new Insets(4, 6, 0, 5);
        setBorder(BorderFactory.createEmptyBorder(4, 6, 0, 5));
        setForeground(AS.getCol());
        // col=AS.getCol();
    }

    public void paintComponent(Graphics g) {
        int left = Math.max(msize.width / 12 - 2, 6);
        super.paintComponent(g.create(insets.left, insets.top, msize.width
                - insets.left - insets.right, msize.height - insets.top
                - insets.bottom));
        // g.setColor(col);
        g.drawLine(left, 0, msize.width, 0);
        g.drawLine(left, 0, 3, msize.height);
        g.drawLine(0, msize.height / 2, 3, msize.height / 2);
        g.drawLine(3, msize.height / 2, 3, msize.height);
        g.drawLine(msize.width - 1, 0, msize.width - 1, 5);
        // g.drawLine(msize.width-2,0,msize.width-2,5);

        /*
         * g.drawLine(left,1,msize.width+1,1);
         * g.drawLine(left+1,0,left+1,msize.height);
         * g.drawLine(0+1,msize.height/2,left+1,msize.height);
         * g.drawLine(msize.width-1+1,0,msize.width-1+1,5);
         * g.drawLine(msize.width-2+1,0,msize.width-2+1,5);
         */
    }

    public MBounds buildTree() {
        msize = super.buildTree();
        insets = new Insets(4, Math.max(msize.width / 12 - 2, 6) + 2, 0, 5);
        super.buildTree();
        return msize;
    }
}
