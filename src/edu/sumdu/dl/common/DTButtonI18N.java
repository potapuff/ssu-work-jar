package edu.sumdu.dl.common;

import java.awt.Container;
import java.util.ArrayList;

public class DTButtonI18N extends DTButton {

    public DTButtonI18N() {
        super();
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        super.fromXNode(node, target, fields, elements, l, pv);
        setText(l.getMessage(node.getContent()));
        i18nKey = node.getContent();
        return true;
    }
    String i18nKey = null;

    public String toXML() {
        return "<i18nbutton action=\"" + Tool.escapeXML(getActionName())
                + "\" listener=\"" + Tool.escapeXML(getListenerName()) + "\">"
                + Tool.escapeXML(i18nKey) + "</i18nbutton>";

    }
}
