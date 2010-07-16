package edu.sumdu.dl.common;

import java.awt.*;
import java.util.*;
import edu.sumdu.dl.formula.*;

public class DTFormula extends DTPanel implements NodeStorable, ISelfDumped,
        ImageDumped {

    private String curFormula;

    public void setFormula(String f) {
        curFormula = f;
        AS.setCol(Color.black);
        repaintFormula();
    }

    public String getFormula() {
        return curFormula;
    }
    private FormulaRender fr;

    public DTFormula() {
        super(new FlowLayout());
        curFormula = "Edit me!";
        AS.setCol(Color.black);
        fr = new FormulaRender(curFormula);
        add(fr);
    }

    protected void repaintFormula() {
        fr.removeAll();
        fr.eval(curFormula);
        fr.repaint();
    }
    private String content = null;
    private boolean evaled = false;

    public void setContent(String s) {
        content = s;
    }

    public String getContent() {
        return content;
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        // System.err.println("Getting:"+node.toString());
        fr.setRTV(pv);
        if (node.getAttr("eval") != null && node.getAttr("eval").length() != 0) {
            // System.err.println("Setting:"+node.getAttr("eval"));
            setFormula(pv.getVar(node.getAttr("eval")).toString());
            setContent(node.getAttr("eval"));
            evaled = true;
        } else {
            setFormula(node.getAttr("value"));
        }
        return true;
    }

    public String toXML() {
        String valueStr;
        if (evaled) {
            valueStr = " eval=\"" + Tool.escapeXML(getContent()) + "\"";
        } else {
            valueStr = " value=\"" + Tool.escapeXML(curFormula) + "\" ";
        }
        System.err.println(valueStr);
        return "<formula " + valueStr + " />";
    }

    public String asHTML() {
        return curFormula;
    }

    public byte[] getImageDump() {
        if (fr == null) {
            return new byte[0];
        } else {
            return fr.getImageDump();
        }
    }
}
