package edu.sumdu.dl.common;

import javax.swing.BoxLayout;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;

/*
 * <pre>
 *$Log: DTTemplate.java,v $
 *Revision 1.4  2003/11/02 18:40:22  nnick
 *Some changes in tables,
 *better edit properties,CSS in docs etc
 *
 *Revision 1.3  2003/11/02 11:38:42  nnick
 *Fixed some bugs and more docs
 *
 *Revision 1.2  2003/10/31 19:43:43  nnick
 *Fixed color in Formulas, better repaint atfter change, templates now work
 *
 *Revision 1.1  2003/10/31 16:34:35  nnick
 *Added templates
 *<template name="tmpl1">
 *[:sas:]
 *</template>
 *and calls
 *<call template="tmpl1">
 *<param name="sas" value="134" />
 *</call>
 *
 * </pre>
 * */
public class DTTemplate extends DTPanel {

    public DTTemplate() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    private String tmplName = null;
    private XNode content;

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        tmplName = node.getAttr("name");
        content = node;
        pv.setVar("TEMPLATE:" + tmplName, this);
        // System.err.println("Created template "+tmplName);
        if (!Tool.toBool(pv.getVar("the_applet_is_running").toString())) {
            setBorder(javax.swing.BorderFactory.createTitledBorder("Template:"
                    + tmplName));
            target.add(this);
            elements.add(this);
            return false;
        } else {
            return true;
        }
    }

    public String toXML() {
        return "<template name=\"" + Tool.escapeXML(String.valueOf(tmplName))
                + "\" >" + dump() + "</template>";
    }

    public XNode setParams(Hashtable params) {
        String s = content.toString();
        // System.err.println("Template :"+tmplName+":setting params");
        for (Enumeration e = params.keys(); e.hasMoreElements();) {
            String key = e.nextElement().toString();
            String value = String.valueOf(params.get(key));
            if (value != null) {
                s = s.replaceAll("\\[:" + key + ":\\]", value);
            }
        }
        // System.err.println("Template :"+tmplName+":"+s);
        return XNParser.parseString(s);
    }
}
