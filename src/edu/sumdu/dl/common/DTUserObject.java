package edu.sumdu.dl.common;

import java.awt.Container;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * $Log: DTUserObject.java,v $ Revision 1.3 2003/10/19 15:54:45 nnick Added some
 * log dumps
 * 
 * Revision 1.2 2003/10/19 15:02:48 nnick Fixed bugs in userobject , added tags
 * 
 * Revision 1.1 2003/10/19 15:00:30 nnick Binding for visual edit and markup to
 * place user-defined objects.
 * 
 * */
public class DTUserObject extends DTPanel {

    private String varName;

    public DTUserObject() {
        super();
        add(new JLabel("User's object"));
    }
    private Step step;

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        varName = node.getAttr("var");
        try {
            JComponent userObj = (JComponent) pv.getVar(varName);
            if (userObj != null) {
                target.add(userObj);
                elements.add(userObj);
                if (userObj instanceof CheckField) {
                    fields.add(userObj);
                }
            }
        } catch (Exception e) {
            /*
             * Here is a trick. If we fail to get proper object from run-time
             * vars, show a stub
             */
            target.add(this);
            elements.add(this);
        }
        return true;
    }

    public String toXML() {
        return "<userobject var=\"" + Tool.escapeXML("" + varName) + "\" />";
    }
}
