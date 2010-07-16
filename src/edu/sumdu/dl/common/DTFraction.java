package edu.sumdu.dl.common;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DTFraction extends DTPanel {

    private DTPanel top_p, bot_p;

    public DTPanel getTopPanel() {
        return top_p;
    }

    public DTPanel getBotPanel() {
        return bot_p;
    }

    public DTFraction() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        top_p = new DTPanel(new FlowLayout());
        top_p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
        bot_p = new DTPanel(new FlowLayout());
        bot_p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
        add(top_p);
        add(bot_p);
    }

    public Component addTop(Component c) {
        return top_p.add(c);
    }

    public Component addBot(Component c) {
        return bot_p.add(c);
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        elements.add(getTopPanel());
        elements.add(getBotPanel());
        XNode childs[] = node.getChildren();
        for (int i = 0; i < childs.length; i++) {
            if (childs[i].getName().equals("top")) {
                XNode childs2[] = childs[i].getChildren();
                for (int j = 0; j < childs2.length; j++) {
                    Tool.addChilds(childs2[j], getTopPanel(), fields, elements,
                            l, pv);
                }
            }
            if (childs[i].getName().equals("bottom")) {
                XNode childs2[] = childs[i].getChildren();
                for (int j = 0; j < childs2.length; j++) {
                    Tool.addChilds(childs2[j], getBotPanel(), fields, elements,
                            l, pv);
                }
            }
        }
        return true;
    }

    public String toXML() {
        return "<fraction><top>" + top_p.dump() + "</top><bottom>"
                + bot_p.dump() + "</bottom></fraction>";
    }
}
