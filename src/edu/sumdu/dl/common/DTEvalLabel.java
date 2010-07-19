package edu.sumdu.dl.common;

import java.awt.Container;
import java.util.ArrayList;

public class DTEvalLabel extends DTLabel implements Updateable {

    private XNode node;
    private RuntimeVars runtimeVars;
    

    public DTEvalLabel() {
        super();
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars rv) {

        target.add(this);
        elements.add(this);
        this.node = node;
        this.runtimeVars = rv;

        content = node.getContent().trim();
        style = node.getAttr("style");
        change();
        return true;
    }

    public void updateValuesToCheck() {
        change();
    }


    private void change() {
        setText("<html><p style=\"" + style + "\">" + runtimeVars.getVar(content).toString() + "</p></html>");
    }

    

    public String toXML() {
        return "<eval style=\"" + style + "\">" + Tool.escapeXML(content) + "</eval>";
    }
}
