package edu.sumdu.dl.formula;

import java.awt.*;

public class MRoot extends MBox {

    private Color col;
    String pow;
    int fontSize;

    MRoot(String pow) {
        super();
        fontSize = 8;
        this.pow = pow;
        setForeground(AS.getCol());
    }

    public MBounds buildTree() {
        insets = new Insets(2, pow.length() * fontSize + 5, 0, 2);
        msize = super.buildTree();

        return msize;
    }

    public void paintComponent(Graphics g) {
        int left = Math.max(msize.width / 12 - 2, insets.left);
        super.paintComponent(g.create(insets.left, insets.top, msize.width
                - insets.left - insets.right, msize.height - insets.top
                - insets.bottom));
        g.drawLine(left, 0, msize.width, 0);
        g.drawLine(left, 0, 3, msize.height);
        g.drawLine(0, msize.height / 2, 3, msize.height / 2);
        g.drawLine(3, msize.height / 2, 3, msize.height);
        g.drawLine(msize.width - 1, 0, msize.width - 1, 5);
        g.drawString(pow, 0, Math.max(fontSize + 2, msize.height / 2 - 3));
        // g.drawLine(msize.width-2,0,msize.width-2,5);

        /*
         * g.drawLine(left,1,msize.width+1,1);
         * g.drawLine(left+1,0,left+1,msize.height);
         * g.drawLine(0+1,msize.height/2,left+1,msize.height);
         * g.drawLine(msize.width-1+1,0,msize.width-1+1,5);
         * g.drawLine(msize.width-2+1,0,msize.width-2+1,5);
         */
    }
}
