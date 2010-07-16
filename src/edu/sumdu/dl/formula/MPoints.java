package edu.sumdu.dl.formula;

import java.awt.*;

//points(expr[;bot[;top]])
public class MPoints extends MBox {

    public MBox mainF, topF, botF;

    // private Color col;
    MPoints(MBox m, MBox b, MBox a) {
        super();
        mainF = m;
        topF = a;
        botF = b;
        add(m);
        add(a);
        add(b);
        // col=AS.getCol();
    }

    public MBounds buildTree() {
        MBounds t = topF.buildTree();
        MBounds b = botF.buildTree();
        MBounds m = mainF.buildTree();
        msize = new MBounds(Math.max(t.width, b.width) + 4 + m.width, m.height
                + t.top + b.bot, m.top + t.top, m.bot + b.bot);
        topF.setBounds(m.width + 4, 0, t.width, t.height);
        botF.setBounds(m.width + 4, t.top + m.height - b.top, b.width,
                b.height);
        mainF.setBounds(0, t.top, m.width, m.height);
        return msize;
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        // g.setColor(col);
        g.drawLine(mainF.msize.width + 2, 0, mainF.msize.width + 2,
                msize.height);
    }
}
