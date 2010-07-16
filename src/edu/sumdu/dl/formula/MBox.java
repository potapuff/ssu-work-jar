package edu.sumdu.dl.formula;

import javax.swing.*;
import java.awt.*;

public class MBox extends JPanel {

    public MBounds msize;
    public Insets insets;
    private boolean isRoot;

    MBox() {
        this(false);

    }

    MBox(boolean f) {
        super();
        setLayout(null);
        isRoot = f;
        insets = new Insets(1, 1, 1, 1);
        setForeground(AS.getCol());
    }

    public Dimension getPreferredSize() {
        buildTree();
        return new Dimension(msize.width, msize.height);
    }

    public void paintComponent(Graphics g) {
        // if(isRoot) FormulaRender.printTree(this,0);
        // setB();
        // setBackground(new Color(255,255,255));
        if (g instanceof java.awt.Graphics2D) {
            ((java.awt.Graphics2D) g).setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        super.paintComponent(g);
    }

    public void setB() {
        int coco = getComponentCount();
        int curX = insets.left;
        int curY = 0;
        for (int i = 0; i < coco; i++) {
            Component cur = getComponent(i);
            if (cur instanceof MBox) {
                MBox mb = (MBox) cur;
                mb.setBounds(curX + 1, msize.top - mb.msize.top,
                        mb.msize.width, mb.msize.height);
                curX += mb.msize.width + 2;
            } else if (cur instanceof JLabel) {
                Dimension d = cur.getPreferredSize();
                int th = d.height / 2;
                cur.setBounds(curX + 1, msize.top - th, d.width, d.height);
                curX += d.width + 2;
            }
        }
    }

    public MBounds buildTree() {
        int coco = getComponentCount();
        int maxth = 0, maxbh = 0, width = 0, height = 0;
        for (int i = 0; i < coco; i++) {
            Component cur = getComponent(i);
            if (cur instanceof MBox) {
                MBox mb = (MBox) cur;
                MBounds b = mb.buildTree();
                width += b.width + 2;
                if (b.top > maxth) {
                    maxth = b.top;
                }
                if (b.bot > maxbh) {
                    maxbh = b.bot;
                }
            } else if (cur instanceof JLabel) {
                Dimension d = cur.getPreferredSize();
                int th = d.height / 2;
                int bh = d.height - th;
                if (th > maxth) {
                    maxth = th;
                }
                if (bh > maxbh) {
                    maxbh = bh;
                }
                width += d.width + 2;
            }
        }
        height = maxbh + maxth;
        setSize(width + insets.left + insets.right, height + insets.top
                + insets.bottom);
        msize = new MBounds(width + insets.left + insets.right, height
                + insets.top + insets.bottom, maxth + insets.top, maxbh
                + insets.bottom);
        setB();
        // System.err.println(this.toString());
        return msize;

    }

    public String toString() {
        return this.getClass().getName() + ":(" + getLocation().x + ","
                + getLocation().y + ")[" + msize.width + "x" + msize.height
                + ";" + msize.top + "/" + msize.bot + "]";
    }
}
