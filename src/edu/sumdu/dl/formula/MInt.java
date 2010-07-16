package edu.sumdu.dl.formula;

import java.awt.Font;
import java.awt.Graphics;


/* intg(x^2/sin(x^2);0;2/3)*/
public class MInt extends MBox {

    private MBox bottom, top, mainF;

    MInt(MBox Xformula, MBox Xbottom, MBox Xtop) {
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

        sumWid = (int) Math.max(Math.max(bottom.msize.width, top.msize.width),
                20);
        int width = sumWid + mainF.msize.width + 1;
        msize = new MBounds(width, top.msize.height + bottom.msize.height
                + mainF.msize.height + 4, mainF.msize.top + top.msize.height
                + 2, mainF.msize.bot + bottom.msize.height + 2);
        top.setBounds((int) Math.abs(sumWid - top.msize.width) / 2, 0,
                top.msize.width, top.msize.height);
        bottom.setBounds((int) Math.abs(sumWid - bottom.msize.width) / 2,
                top.msize.height + mainF.msize.height + 4, bottom.msize.width,
                bottom.msize.height);
        mainF.setBounds(sumWid, top.msize.height, mainF.msize.width,
                mainF.msize.height);
        return msize;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // g.setColor(AS.getCol());
        int hei = mainF.msize.height;
        g.setFont(new Font("default", 0, hei));
        int wd = g.getFontMetrics().charWidth('\u222b');
        g.drawString("\u222b", (sumWid - wd) / 3, hei + top.msize.height - hei
                / 6);
    }
}
