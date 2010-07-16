package edu.sumdu.dl.common;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.*;
import java.util.ArrayList;

public class DTPanel extends JPanel implements NodeStorable {

    private boolean isSet;

    // private JLabel filler=null;
    public DTPanel() {
        super();
        // trick();
    }
    boolean trickwork = true;

    // private void trick(){
    // filler=new JLabel("EMPTY");
    // filler.setFont(new Font("default",Font.BOLD,25));
    // super.add(filler);
    // }
    public Dimension getPreferredSize() {
        if (trickwork) {
            return new Dimension(80, 25);
        } else {
            return super.getPreferredSize();
        }
    }

    public Dimension getMinimumSize() {
        if (trickwork) {
            return getPreferredSize();
        } else {
            return super.getMinimumSize();
        }
    }

    public Component add(Component c) {
        // if(filler!=null){
        // remove(filler);
        // filler=null;
        // }
        trickwork = false;
        return super.add(c);
    }

    public DTPanel(LayoutManager lm) {
        super(lm);
        // trick();
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        setLayoutName(node.getAttr("layout"));
        return false;
    }
    private String layoutName = "";

    public void setLayoutName(String name) {
        if (name == null) {
            return;
        }
        if (name.equals("flow")) {
            setLayout(new FlowLayout());
            layoutName = name;
        }
        if (name.equals("xbox")) {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            layoutName = name;
        }
        if (name.equals("ybox")) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            layoutName = name;
        }
        if (name.equals("lead")) {
            setLayout(new FlowLayout(FlowLayout.LEADING));
            layoutName = name;

        }
        if (name.equals("left")) {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            layoutName = name;

        }
        if (name.equals("right")) {
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            layoutName = name;
        }
        if (name.equals("trail")) {
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            layoutName = name;
        }

    }

    public String getLayoutName() {
        return layoutName;
    }

    public String toXML() {
        String ln = "";
        if (layoutName != "") {
            ln = " layout=\"" + layoutName + "\"";
        }
        return "<p" + ln + ">" + dump() + "</p>";
    }

    protected String dump() {
        String s = "";
        for (int i = 0; i < getComponents().length; i++) {
            Component c = getComponent(i);
            if (c instanceof NodeStorable) {
                s += ((NodeStorable) c).toXML();
            }
        }
        return s;
    }

    public void paintComponent(java.awt.Graphics g) {
        if (g instanceof java.awt.Graphics2D) {
            ((java.awt.Graphics2D) g).setRenderingHint(
                    java.awt.RenderingHints.KEY_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        }
        super.paintComponent(g);
    }
}
