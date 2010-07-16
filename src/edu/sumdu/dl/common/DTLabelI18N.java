package edu.sumdu.dl.common;

import java.awt.Container;
import java.util.ArrayList;
import java.util.regex.*;

public class DTLabelI18N extends DTLabel implements TLocalized, Updateable {

    private RuntimeVars runtimeVars;
    private XNode node;
    private Localizer localizer;
    private String content;
    private String style;

    public DTLabelI18N() {
        super();
    }

    public DTLabelI18N(String s) {
        super(s);
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer localizer, RuntimeVars rv) {

        target.add(this);
        elements.add(this);
        this.runtimeVars = rv;
        this.localizer = localizer;
        this.node = node;

        content = node.getContent().trim();
        style = node.getAttr("style");
        changeValue();

        return true;
    }

    public void updateValuesToCheck() {
        changeValue();
    }

    public String toXML() {
        return "<i18n style=\"" + style + "\">" + Tool.escapeXML(content) + "</i18n>";
    }

    public void refreshLang(Localizer t) {
        localizer = t;
        changeValue();
    }

    private void changeValue() {

        String s = "<html><p style=\"" + style + "\">" + localizer.getMessage(content) + "</p></html>";
        Pattern p = Pattern.compile("\\[:(.+?):\\]");
        Matcher m = p.matcher(s);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, runtimeVars.getVar(m.group(1)).toString());
        }
        m.appendTail(sb);
        super.setText(sb.toString());
    }
}
