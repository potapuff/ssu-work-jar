package edu.sumdu.dl.formula;

import java.awt.*;

public class MPower extends MBox {

    public MBox left_p, top_p;

    MPower(MBox pow, MBox val) {
        super();
        left_p = pow;
        top_p = val;
        add(left_p);
        add(top_p);
        insets = new Insets(0, 0, 0, 0);
    }

    public MBounds buildTree() {
        MBounds l = left_p.buildTree();
        MBounds r = top_p.buildTree();
        int width = left_p.msize.width + top_p.msize.width;
        msize = new MBounds(width,
                left_p.msize.height + top_p.msize.height - 8,
                top_p.msize.height + left_p.msize.top - 9, left_p.msize.bot + 1);
        setB();
        return msize;
    }

    public void setB() {
        left_p.setBounds(0, msize.height - left_p.msize.height,
                left_p.msize.width, left_p.msize.height);
        top_p.setBounds(left_p.msize.width, 1, top_p.msize.width,
                top_p.msize.height);
    }
}
