package edu.sumdu.dl.common;

import java.util.*;
import java.awt.Container;
import java.awt.Component;
import javax.swing.JApplet;

/*  $Log: Tool.java,v $
/*  Revision 1.20  2006/08/31 12:08:37  nnick
/*  Can't remember :)
/*
/*  Revision 1.19  2004/09/07 09:17:46  nnick
/*  Added <group_flag> tag to group various radios and indicate correctness of checks
/*
/*  Revision 1.18  2004/07/19 09:10:11  nnick
/*  Added <hint> element - popup window with HTML text
/*
/*  Revision 1.17  2003/11/07 11:16:45  nnick
/*  Added <store> tag, docs a bit edited
/*
/*  Revision 1.16  2003/11/06 16:20:28  nnick
/*  Added buttons which can respond to user's action listeners,
/*  added help in trainer
/*  (help text is still missing, but the feture is)
/*
/*  Revision 1.15  2003/11/03 11:29:56  nnick
/*  CFNumberField supports input like '1/3',
/*  but value to check must be set as plain number like 0.333333
/*
/*  Revision 1.14  2003/11/02 11:38:42  nnick
/*  Fixed some bugs and more docs
/*
/*  Revision 1.13  2003/10/31 16:34:35  nnick
/*  Added templates
/*  <template name="tmpl1">
/*  [:sas:]
/*  </template>
/*  and calls
/*  <call template="tmpl1">
/*  <param name="sas" value="134" />
/*  </call>
/*
/*  Revision 1.12  2003/10/31 15:32:45  nnick
/*  Added Escape/Unescape XML fetures in editor,
/*  probably fixed repainting in CFFormula
/*
/*  Revision 1.11  2003/10/31 14:49:04  nnick
/*  Handles ifNot tag
/*
/*  Revision 1.10  2003/10/23 09:20:44  nnick
/*  Added <formula value="x^2" /> , <formula eval="varName" /> objects
/*
/*  Revision 1.9  2003/10/22 17:07:44  nnick
/*  DTIf is a trick to some parts be not displayed when ruuning applet.
/*
/*  Revision 1.8  2003/10/21 18:08:14  nnick
/*  Bug DTTable returned false instead true
/* */
public class Tool {

    public static JApplet findParentApplet(Component c) {
        Component par = c;
        while (par != null && !(par instanceof JApplet)) {
            par = par.getParent();
        }
        return (JApplet) par;
    }

    public static boolean toBool(String o) {
        return "true".equals(o) || "1".equals(o);
    }

    public static int toInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double toDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static double getFractDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            try {
                String dr[] = s.split("/");
                return toDouble(dr[0]) / toDouble(dr[1]);
            } catch (Exception e2) {
                return 0.0;
            }
        }
    }

    public static void addChilds(XNode node, Container target,
            ArrayList fields, ArrayList elements, Localizer loc, RuntimeVars pv) {
        NodeStorable newnode = fromTag(node.getName());
        // System.out.println(newnode.toXML()+node.getContent());
        if (newnode == null) {
            return;
        }

        boolean selfRead = newnode.fromXNode(node, target, fields, elements,
                loc, pv);
        // System.out.println(newnode.toXML());
        if (!selfRead) {
            Container c = (Container) newnode;
            XNode childs[] = node.getChildren();
            for (int i = 0; i < childs.length; i++) {
                addChilds(childs[i], c, fields, elements, loc, pv);
            }

        }
    }

    public static NodeStorable fromTag(String name) {
        String className = "";
        try {
            className = tagClasses.get(name).toString();
            NodeStorable node = (NodeStorable) Class.forName(className).newInstance();
            return node;
        } catch (Exception e) {
            System.err.println("Failed to create tag from " + name + ":"
                    + className);
            e.printStackTrace();
            return null;
        }

    }

    public static void addTagClass(String tagName, String className) {
        tagClasses.put(tagName, className);
    }
    private static Hashtable tagClasses = new Hashtable();

    static {

        addTagClass("calc", "edu.sumdu.dl.common.CFCalculatorField");//Eduard

        addTagClass("ask", "edu.sumdu.dl.common.CFNumberField");//
        addTagClass("asktext", "edu.sumdu.dl.common.CFTextField");//
        addTagClass("askformula", "edu.sumdu.dl.common.CFFormula");//

        addTagClass("checkbox", "edu.sumdu.dl.common.CFCheckBox");//
        addTagClass("i18ncheckbox", "edu.sumdu.dl.common.CFCheckBoxI18N");//

        addTagClass("combobox", "edu.sumdu.dl.common.CFComboBox");//
        addTagClass("i18ncombobox", "edu.sumdu.dl.common.CFComboBoxI18N");//

        addTagClass("radio", "edu.sumdu.dl.common.CFRadioButton");//
        addTagClass("i18nradio", "edu.sumdu.dl.common.CFRadioButtonI18N");//

        addTagClass("label", "edu.sumdu.dl.common.DTLabel");
        addTagClass("i18n", "edu.sumdu.dl.common.DTLabelI18N");
        addTagClass("eval", "edu.sumdu.dl.common.DTEvalLabel");//
        addTagClass("vector", "edu.sumdu.dl.common.DTVectorLabel");
        addTagClass("greece", "edu.sumdu.dl.common.DTGreece");

        addTagClass("fraction", "edu.sumdu.dl.common.DTFraction");

        addTagClass("p", "edu.sumdu.dl.common.DTPanel");

        addTagClass("sqrt", "edu.sumdu.dl.common.DTSqrt");
        addTagClass("vectorpanel", "edu.sumdu.dl.common.DTVectorPanel");
        addTagClass("radiogroup", "edu.sumdu.dl.common.DTButtonGroup");

        addTagClass("matrix", "edu.sumdu.dl.common.DTMatrix");
        addTagClass("evalmatrix", "edu.sumdu.dl.common.DTEvalMatrix");
        addTagClass("inputmatrix", "edu.sumdu.dl.common.DTInputMatrix");

        addTagClass("table", "edu.sumdu.dl.common.DTTable");

        addTagClass("userstep", "edu.sumdu.dl.common.DTUserStep");
        addTagClass("userobject", "edu.sumdu.dl.common.DTUserObject");
        addTagClass("step", "edu.sumdu.dl.common.DTStepPanel");

        addTagClass("img", "edu.sumdu.dl.common.DTImage");
        addTagClass("if", "edu.sumdu.dl.common.DTIf");
        addTagClass("ifNot", "edu.sumdu.dl.common.DTIfNot");
        addTagClass("formula", "edu.sumdu.dl.common.DTFormula");

        addTagClass("template", "edu.sumdu.dl.common.DTTemplate");
        addTagClass("call", "edu.sumdu.dl.common.DTCall");

        addTagClass("button", "edu.sumdu.dl.common.DTButton");
        addTagClass("i18nbutton", "edu.sumdu.dl.common.DTButtonI18N");

        addTagClass("store", "edu.sumdu.dl.common.DTStore");
        addTagClass("hint", "edu.sumdu.dl.common.DTHint");

        addTagClass("conclude", "edu.sumdu.dl.common.CFConclusions");

        addTagClass("group_flag", "edu.sumdu.dl.common.DTRadioGroupIndicator");

    }

    public static boolean isNaN(String s) {
        try {
            Double d = new Double(s);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static String escapeXML(String x) {
        if (x == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < x.length(); i++) {
            switch (x.charAt(i)) {
                case '&':
                    buf.append("&amp;");
                    break;
                case '\'':
                    buf.append("&apos;");
                    break;
                case '"':
                    buf.append("&quot;");
                    break;
                case '>':
                    buf.append("&gt;");
                    break;
                case '<':
                    buf.append("&lt;");
                    break;
                default:
                    if ((int) x.charAt(i) < 128) {
                        buf.append(x.charAt(i));
                    } else {
                        buf.append("&#" + (int) x.charAt(i) + ";");
                    }
            }
        }

        return buf.toString();
    }

    public static boolean isBool(String s) {
        return "true".equals(s) || "false".equals(s);
    }

    public static String unescapeXML(String s) {
        StringBuffer bf = new StringBuffer();
        try {
            String r = s.replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"").replaceAll("&apos;", "'");
            int i = 0;
            while (i < r.length()) {
                if (r.charAt(i) != '&') {
                    bf.append(r.charAt(i));
                } else if (r.charAt(i + 1) == '#') {
                    int k = 0;
                    i += 2;
                    while (i < r.length() && r.charAt(i) <= '9'
                            && r.charAt(i) >= '0') {
                        k *= 10;
                        k += (int) r.charAt(i++) - 48;
                    }
                    bf.append((char) k);
                } else {
                    bf.append(r.charAt(i));
                }
                i++;
            }
        } catch (Exception e) {
            bf = new StringBuffer(s);
        }
        return bf.toString();

    }
}
