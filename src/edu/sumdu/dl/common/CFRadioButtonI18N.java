package edu.sumdu.dl.common;

import java.util.ArrayList;
import java.awt.Container;

// Переключатель - правильные и неправильные ответы
public class CFRadioButtonI18N extends CFRadioButton implements TLocalized {

    private String propName;

    public String getPropName() {
        return propName;
    }

    public void setPropName(String s) {
        propName = s;
    }

    public CFRadioButtonI18N() {
        super();
        propName = null;
        valuePropName = null;
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        super.fromXNode(node, target, fields, elements, l, pv);
        setPropName(node.getContent());
        // if(node.getAttr("type").equals("i18n"))
        setText(l.getMessage(node.getContent()));

        groupName = node.getAttr("group");
        if (groupName != null) {
            indicator = (DTRadioGroupIndicator) pv.getVar(groupName);
            // System.err.println("INDICATE:"+indicator);
            if (indicator != null) {
                indicator.add(this);
            }
        }
        return true;
    }
    DTRadioGroupIndicator indicator = null;
    String groupName = null;

    String getContent() {
        return propName;
    }

    public String toXML() {
        String valueStr = "";
        if (valuePropName != null) {
            valueStr = " value=\"" + valuePropName + "\" ";
        } else {
            valueStr = " value=\"" + value + "\" ";
        }
        if (groupName != null) {
            valueStr += " group=\"" + groupName + "\"";
        }

        return "<i18nradio " + valueStr + " >" + Tool.escapeXML(getContent())
                + "</i18nradio>";
    }

    public void refreshLang(Localizer t) {
        setText(t.getMessage(getContent()));
    }
}
