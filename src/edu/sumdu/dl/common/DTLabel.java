package edu.sumdu.dl.common;

import javax.swing.*;
import java.awt.Container;
import java.util.ArrayList;

public class DTLabel extends JLabel implements NodeStorable {

    String content;
    String style;

    public DTLabel() {
        super();
    }

    public DTLabel(String s) {
        super(s);
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        
        content = node.getContent().trim();
        style = node.getAttr("style");

        setText("<html><p style=\"" + style + "\">" + content + "</p></html>");

        return true;
    }

    public String toXML() {
        return "<label style=\"" + style + "\">" + Tool.escapeXML(content) + "</label>";
    }
}
