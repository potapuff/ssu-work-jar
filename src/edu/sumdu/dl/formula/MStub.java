package edu.sumdu.dl.formula;

public class MStub extends MBox {

    MStub() {
        super();

    }
    javax.swing.JComponent userObject = null;

    public void setUserObject(javax.swing.JComponent uo) {
        userObject = uo;
        add(uo);
        setLayout(new java.awt.FlowLayout());
    }

    public MBounds buildTree() {
        if (userObject == null) {
            msize = new MBounds(5, 10, 5, 5);
        } else {
            java.awt.Dimension d = userObject.getPreferredSize();
            msize = new MBounds(d.width, d.height, d.height / 2, d.height / 2);
        }
        return msize;
    }
}
