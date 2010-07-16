package edu.sumdu.dl.common;

import javax.swing.*;
import java.net.URL;
import java.awt.Container;
import java.util.ArrayList;

public class DTImage extends DTPanel {

    public DTImage() {
        super();
    }

    public String toXML() {
        return "<img src=\"" + Tool.escapeXML(getSource()) + "\" title=\""
                + Tool.escapeXML(getTitle()) + "\" />";
    }
    private String src = null, title = null;

    public void setTitle(String s) {
        if (s == null) {
            title = "";
        } else {
            title = s;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setSource(String s) {
        src = s;
    }

    public String getSource() {
        return src;
    }

    private void setImage() {
        try {
            try {
                URL codeBase = Tool.findParentApplet(this).getCodeBase();
                URL url = null;
                url = new URL(codeBase, src);
                System.out.println(codeBase);
                System.out.println(url);
                add(new JLabel(title, new ImageIcon(url), JLabel.CENTER));
            } catch (Exception e) {
                add(new JLabel("", new ImageIcon(getClass().getClassLoader().getResource(src)), JLabel.CENTER));
            }
        } catch (Exception e) {
            add(new JLabel(title));
        }
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        setTitle(node.getAttr("title"));
        setSource(node.getAttr("src"));
        setImage();
        return true;
    }
}
