package edu.sumdu.dl.common;

import java.awt.Container;
import java.util.ArrayList;

/**
 * $Log: DTUserStep.java,v $ Revision 1.5 2003/10/20 08:18:41 nnick Fixed bug in
 * DTUserStepm - didn't add itself to target. Localizer attempta again to load
 * props from file -- needed by TaskEditor
 * 
 * Revision 1.4 2003/10/19 15:54:45 nnick Added some log dumps
 * 
 * Revision 1.3 2003/10/19 14:53:01 nnick Fixed bugs
 * 
 * Revision 1.1 2003/10/19 14:43:18 nnick DTUserStep is just a visula binding.
 * The goal is to use user-defined steps as basic No need to add UserStep at
 * design time. just edit xml
 * */
public class DTUserStep extends DTStepPanel implements NodeStorable {

    private String varName = null;
    private String design = null;

    public DTUserStep() {
        super();
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        design = node.getAttr("design");
        varName = node.getAttr("var");
        return false;
    }

    public String toXML() {
        return "<userstep var=\"" + Tool.escapeXML("" + varName)
                + "\" design=\"" + Tool.escapeXML("" + design) + "\">" + dump()
                + "</userstep>";
    }
}
