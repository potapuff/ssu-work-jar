package edu.sumdu.dl.formula;

import java.awt.*;

public class MFract extends MBox {

    public MBox top_p, bot_p;

    // private Color col;
    MFract(MBox t, MBox b) {
        super();
        top_p = t;
        bot_p = b;
        add(t);
        add(b);
        // col=AS.getCol();
    }

    public MBounds buildTree() {
        MBounds l = top_p.buildTree();
        MBounds r = bot_p.buildTree();
        msize = new MBounds(Math.max(l.width, r.width),
                l.height + r.height + 4, l.height + 2, r.height + 2);
        top_p.setBounds((msize.width - top_p.msize.width) / 2, 0,
                top_p.msize.width, top_p.msize.height);
        bot_p.setBounds((msize.width - bot_p.msize.width) / 2,
                top_p.msize.height + 4, bot_p.msize.width, bot_p.msize.height);

        return msize;
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        // g.setColor(col);
        g.drawLine(0, top_p.msize.height + 2, msize.width,
                top_p.msize.height + 2);
        g.drawLine(0, top_p.msize.height + 3, msize.width,
                top_p.msize.height + 3);
    }
}
