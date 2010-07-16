package edu.sumdu.dl.common;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.*;

public class DTButtonGroup extends DTPanel {

    private ButtonGroup bg;
    private boolean made_descent = false;

    public DTButtonGroup() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        bg = new ButtonGroup();
        setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
    }

    void descent(Component c) {
        if (c instanceof AbstractButton) {
            // System.err.println("Button!");
            bg.add((AbstractButton) c);
        } else if (c instanceof Container) {
            // System.err.println("Container!");
            Container co = ((Container) c);
            // System.err.println("Size:"+co.getComponentCount());
            for (int i = 0; i < co.getComponentCount(); i++) {
                descent(co.getComponent(i));
            }
        }// else System.err.println("Other!"+c.getClass().getName());
    }

    public void paintComponent(Graphics g) {
        if (!made_descent) {
            made_descent = true;
            descent(this);
        }
        super.paintComponent(g);
    }

    public String toXML() {
        return "<radiogroup>" + dump() + "</radiogroup>";
    }
}
