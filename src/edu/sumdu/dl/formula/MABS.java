package edu.sumdu.dl.formula;

import javax.swing.*;
import java.awt.*;

public class MABS extends MBox {

    MABS() {
        super();
        insets = new Insets(0, 4, 0, 4);
        setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, getForeground()));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g.create(4, 0, msize.width - 8, msize.height));
    }
}
