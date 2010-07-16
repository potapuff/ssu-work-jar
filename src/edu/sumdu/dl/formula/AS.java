package edu.sumdu.dl.formula;

import java.awt.*;

public class AS {

    private static Color s = Color.blue;

    public AS() {
        super();
    }

    public static void setCol(Color c) {
        s = c;
    }

    public static Color getCol() {
        return s;
    }
}
