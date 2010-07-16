package edu.sumdu.dl.common;

import javax.swing.*;
import java.awt.Container;
import java.util.ArrayList;
import java.awt.Color;

public class DTRadioGroupIndicator extends JLabel implements NodeStorable {

    public DTRadioGroupIndicator() {
        super();
        setPicture(DEFAULT_PICTURE);
        bg = new ButtonGroup();
    }

    public void setCorrect(boolean correct) {
        if (correct) {
            setPicture(TRUE_PICTURE);
        } else {
            setPicture(FALSE_PICTURE);
        }
    }
    ButtonGroup bg;
    private static final int DEFAULT_PICTURE = 0;
    private static final int TRUE_PICTURE = 1;
    private static final int FALSE_PICTURE = 2;
    private String[] imgRes = new String[]{
        "edu/sumdu/dl/common/images/default.gif",
        "edu/sumdu/dl/common/images/true.gif",
        "edu/sumdu/dl/common/images/false.gif"};

    public void add(AbstractButton but) {
        bg.add(but);
    }
    private String[] alias = new String[]{"[?]", "[\u221a]", "[x]"};
    private Color[] colors = new Color[]{new Color(0, 0, 0),
        new Color(0, 120, 0), new Color(120, 0, 0)};

    void setPicture(int num) {
        try {
            setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(
                    imgRes[num])));
        } catch (Exception e) {
            e.printStackTrace();
            setText(alias[num]);
            setForeground(colors[num]);
        }
        validate();
        repaint();
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        setText(node.getContent());
        name = "" + node.getAttr("name");
        // System.out.println("Createt group flag:"+name);
        pv.setVar(name, this);
        return true;
    }
    String name;

    public String toXML() {
        return "<group_flag name=\"" + name + "\" />";
    }
}
