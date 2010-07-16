package edu.sumdu.dl.common;

import java.awt.*;
import java.util.ArrayList;

/*  $Log: DTStore.java,v $
/*  Revision 1.1  2003/11/07 11:16:45  nnick
/*  Added <store> tag, docs a bit edited
/*
/*  Revision 1.2  2003/10/22 17:22:42  nnick
/*  Fixed design in Calculator
/*
/*  Revision 1.1  2003/10/22 17:07:44  nnick
/*  DTIf is a trick to some parts be not displayed when ruuning applet.
/* */
public class DTStore extends DTPanel {

    public DTStore() {
        super();
    }
    private String testVar = null;

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        testVar = node.getAttr("var");
        if (Tool.toBool(pv.getVar("the_applet_is_running").toString())) {
            pv.setVar(testVar, this);
        }
        target.add(this);
        elements.add(this);
        return false;
    }

    public String toXML() {
        return "<store var=\"" + Tool.escapeXML(String.valueOf(testVar))
                + "\" >" + dump() + "</store>";
    }
}
