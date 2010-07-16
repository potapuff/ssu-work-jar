package edu.sumdu.dl.common;

import javax.swing.BoxLayout;
import java.awt.Container;
import java.util.ArrayList;

public class DTStepPanel extends DTPanel implements TLocalized {

    private String title;
    private String titleLoc;

    public DTStepPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    }

    public String getTitle() {
        if (title == null) {
            return "NONAME";
        }
        if (titleLoc == null) {
            return title;
        }
        return titleLoc;
    }

    public void setTitle(String s) {
        title = s;
    }

    public void refreshLang(Localizer l) {
        titleLoc = l.getMessage(title);
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        setTitle(node.getAttr("title"));
        refreshLang(l);
        return false;
    }

    public String toXML() {
        return "<step title=\"" + Tool.escapeXML(title) + "\">" + dump()
                + "</step>";
    }
}
