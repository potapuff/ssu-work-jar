package edu.sumdu.dl.common;

import java.awt.*;
import java.util.ArrayList;

/*  $Log: DTIfNot.java,v $
/*  Revision 1.2  2004/07/02 12:56:33  nnick
/*  added layouts to <if> and <ifNot>
/*
/*  Revision 1.1  2003/10/31 14:50:32  nnick
/*  <ifNot> is like  <if> but condition must be false.
/*  TODO ???:
/*  <choose>
/*   <if test="">
/*   </if>
/*   <default>
/*   </default>
/*  </choose>
/*
/*  Revision 1.2  2003/10/22 17:22:42  nnick
/*  Fixed design in Calculator
/*
/*  Revision 1.1  2003/10/22 17:07:44  nnick
/*  DTIf is a trick to some parts be not displayed when ruuning applet.
/* */
public class DTIfNot extends DTPanel {

    public DTIfNot() {
        super();
    }
    private String testVar = null;

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        testVar = node.getAttr("test");
        setLayoutName(node.getAttr("layout"));
        if (!Tool.toBool(pv.getVar(testVar).toString())
                || !Tool.toBool(pv.getVar("the_applet_is_running").toString())) {
            target.add(this);
            elements.add(this);
            return false;
        } else {
            return true;
        }
    }

    public String toXML() {
        String ln = "";
        if (getLayoutName() != "") { // говнокод
            ln = " layout=\"" + getLayoutName() + "\"";
        }
        return "<ifNot " + ln + " test=\""
                + Tool.escapeXML(String.valueOf(testVar)) + "\" >" + dump()
                + "</ifNot>";
    }
}
