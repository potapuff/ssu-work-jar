package edu.sumdu.dl.common;

import java.util.ArrayList;
import java.awt.Container;

// Переключатель - правильные и неправильные ответы
public class CFCheckBoxI18N extends CFCheckBox implements TLocalized {

    private String propName;

    public String getPropName() {
        return propName;
    }

    public void setPropName(String s) {
        propName = s;
    }

    public CFCheckBoxI18N() {
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
        return true;
    }

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
        return "<i18ncheckbox " + valueStr + " >"
                + Tool.escapeXML(getContent()) + "</i18ncheckbox>";
    }

    public void refreshLang(Localizer t) {
        setText(t.getMessage(getContent()));
    }
}
