package edu.sumdu.dl.common;

import javax.swing.JLabel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;

/*  $Log: DTCall.java,v $
/*  Revision 1.4  2004/03/03 08:03:08  nnick
/*  Added eval attribute for template call
/*
/*  Revision 1.3  2003/11/02 11:38:42  nnick
/*  Fixed some bugs and more docs
/*
/*  Revision 1.2  2003/10/31 19:43:43  nnick
/*  Fixed color in Formulas, better repaint atfter change, templates now work
/*
/*  Revision 1.1  2003/10/31 16:34:35  nnick
/*  Added templates
/*  <template name="tmpl1">
/*  [:sas:]
/*  </template>
/*  and calls
/*  <call template="tmpl1">
/*  <param name="sas" value="134" />
/*  </call>
/*
/* */
public class DTCall extends DTPanel {

    public DTCall() {
        super();
        params = new Hashtable();
    }
    private String tmplName = null;
    private Hashtable params;

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        tmplName = node.getAttr("template");
        XNode childs[] = node.getChildren();
        // setting parameters
        for (int i = 0; i < childs.length; i++) {
            if (childs[i].getName().equals("param")) {
                if (childs[i].getAttr("eval") != null) {
                    params.put(childs[i].getAttr("name"), pv.getVar(childs[i].getAttr("eval")));
                } else {
                    params.put(childs[i].getAttr("name"), childs[i].getAttr("value"));
                }

            }
        }

        if (!Tool.toBool(pv.getVar("the_applet_is_running").toString())
                || pv.getVar("TEMPLATE:" + tmplName) == null) {
            add(new JLabel("call template " + tmplName));
            target.add(this);
            elements.add(this);
            return true;
        } else {
            try {
                // getting template
                DTTemplate tmpl = (DTTemplate) pv.getVar("TEMPLATE:" + tmplName);
                // subst them in template
                childs = tmpl.setParams(params).getChildren();
                // add template's content to target
                for (int i = 0; i < childs.length; i++) {
                    Tool.addChilds(childs[i], target, fields, elements, l, pv);
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Calling template:"
                        + tmplName
                        + ":not found in RuntimeVars! (May be not defined yet?)");
            }

            return true;
        }
    }

    public String toXML() {
        StringBuffer ps = new StringBuffer();
        for (Enumeration e = params.keys(); e.hasMoreElements();) {
            String key = e.nextElement().toString();
            String value = String.valueOf(params.get(key));
            if (value != null) {
                ps.append("<param name=\"" + Tool.escapeXML(key)
                        + "\" value=\"" + Tool.escapeXML(value) + "\"/>");
            }
        }
        return "<call template=\"" + Tool.escapeXML(String.valueOf(tmplName))
                + "\" >" + ps + "</call>";
    }
}
