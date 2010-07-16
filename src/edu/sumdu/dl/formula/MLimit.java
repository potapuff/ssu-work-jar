package edu.sumdu.dl.formula;

import java.awt.Font;
import java.awt.Graphics;

/* limit(x,0,x^2/sin(x^2))*/
public class MLimit extends MBox {

    private MJLabel limStr;
    private MBox var;
    private MBox goesTo;
    private MBox mainF;
    int arrowPos;
    static int arrowLen = 10;

    MLimit(MBox Xvar, MBox XgoesTo, MBox Xformula) {
        super();
        setLayout(null);
        add(limStr = new MJLabel("lim"));
        limStr.setForeground(AS.getCol());
        limStr.setFont(new Font("dialog", Font.BOLD, 16));
        add(var = Xvar);
        add(goesTo = XgoesTo);
        add(mainF = Xformula);
    }

    public MBounds buildTree() {
        mainF.buildTree();
        var.buildTree();
        goesTo.buildTree();
        int lh = 9;
        int h1 = mainF.msize.top;
        int b1 = mainF.msize.bot;

        int h2 = var.msize.top;
        int b2 = var.msize.bot;

        int h3 = goesTo.msize.top;
        int b3 = goesTo.msize.bot;
        int w1 = lh * 5;
        int width = var.msize.width + arrowLen + 4 + goesTo.msize.width
                + mainF.msize.width;
        int botH = h2 > h3 ? h2 : h3;
        int botB = b2 > b3 ? b2 : b3;

        int fullBot = lh + botH + botB > b1 ? lh + botH + botB : b1;
        int fullTop = h1;
        msize = new MBounds(width, fullBot + fullTop, fullTop, fullBot);
        var.setBounds(0, fullTop + lh + botH - h2, var.msize.width,
                var.msize.height);
        goesTo.setBounds(var.msize.width + arrowLen, fullTop + lh + botH - h3,
                goesTo.msize.width, goesTo.msize.height);
        mainF.setBounds(var.msize.width + arrowLen + goesTo.msize.width + 4, 0,
                mainF.msize.width, mainF.msize.height);
        arrowPos = fullTop + lh + botH;
        limStr.setBounds(lh, fullTop - lh, lh * 4 + lh / 2, lh * 2);
        // System.out.println(limStr.toString());
        // System.out.println(var.toString());
        // System.out.println(goesTo.toString());
        // System.out.println(mainF.toString());
        // System.out.println(this.toString());
        limStr.validate();
        return msize;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // g.setColor(AS.getCol());
        g.drawLine(var.msize.width + arrowLen - 3, arrowPos - 1,
                var.msize.width + arrowLen, arrowPos);
        g.drawLine(var.msize.width + 2, arrowPos, var.msize.width + arrowLen,
                arrowPos);
        g.drawLine(var.msize.width + arrowLen - 3, arrowPos + 1,
                var.msize.width + arrowLen, arrowPos);
    }
}
