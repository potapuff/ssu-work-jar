package edu.sumdu.dl.formula;

import java.awt.Graphics;


/* sum(i=0;n;x^2/sin(x^2))*/
public class MSum extends MBox {

    private MBox bottom, top, mainF;

    MSum(MBox Xbottom, MBox Xtop, MBox Xformula) {
        super();
        setLayout(null);
        add(bottom = Xbottom);
        add(top = Xtop);
        add(mainF = Xformula);
        setForeground(AS.getCol());
    }
    int sumWid;

    public MBounds buildTree() {
        mainF.buildTree();
        bottom.buildTree();
        top.buildTree();

        sumWid = (int) Math.max(bottom.msize.width, top.msize.width);
        int width = sumWid + mainF.msize.width + 10;
        msize = new MBounds(width, top.msize.height + bottom.msize.height
                + mainF.msize.height + 4, mainF.msize.top + top.msize.height
                + 2, mainF.msize.bot + bottom.msize.height + 2);
        top.setBounds((sumWid - top.msize.width) / 2, 0, top.msize.width,
                top.msize.height);
        bottom.setBounds((sumWid - bottom.msize.width) / 2, top.msize.height
                + mainF.msize.height + 4, bottom.msize.width,
                bottom.msize.height);
        mainF.setBounds(sumWid + 5, top.msize.height, mainF.msize.width,
                mainF.msize.height);
        return msize;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // g.setColor(AS.getCol());
        int hei = mainF.msize.height;
        g.translate(2, top.msize.height + 2);
        g.drawLine(sumWid, 4, sumWid, 0);
        g.drawLine(sumWid, 0, 0, 0);
        g.drawLine(0, 0, sumWid / 2, hei / 2);
        g.drawLine(0, hei, sumWid / 2, hei / 2);
        g.drawLine(sumWid, hei, 0, hei);
        g.drawLine(sumWid, hei - 4, sumWid, hei);

        g.drawLine(sumWid + 1, 4, sumWid + 1, 0);
        g.drawLine(sumWid + 1, 0, 0 + 1, 0);
        g.drawLine(0 + 1, 0, sumWid / 2 + 1, hei / 2);
        g.drawLine(0 + 1, hei, sumWid / 2 + 1, hei / 2);
        g.drawLine(sumWid, 1 + hei, 0, 1 + hei);
        g.drawLine(sumWid + 1, hei - 4, sumWid + 1, hei);
        g.translate(-2, -top.msize.height - 2);

    }
}
