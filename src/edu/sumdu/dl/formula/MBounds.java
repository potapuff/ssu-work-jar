package edu.sumdu.dl.formula;

public class MBounds {

    public int width, height, top, bot;

    MBounds() {
        this(0, 0, 0, 0);
    }

    MBounds(int w, int h, int t, int b) {
        width = w;
        height = h;
        top = t;
        bot = b;
    }

    MBounds(MBounds m) {
        width = m.width;
        height = m.height;
        top = m.top;
        bot = m.bot;
    }
}
