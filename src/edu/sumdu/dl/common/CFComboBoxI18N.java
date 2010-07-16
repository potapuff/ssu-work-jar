package edu.sumdu.dl.common;

import java.util.*;
import java.awt.Container;

public class CFComboBoxI18N extends CFComboBox implements TLocalized {

    public CFComboBoxI18N() {
        super();

    }

    public CFComboBoxI18N(String s[], int idx) {
        this();
        for (int i = 0; i < s.length; i++) {
            addItem(s[i]);
        }
        setRightIdx(idx);

    }

    public String getState() {
        return "<i18ncombobox value=\"" + getSelectedIndex() + "\" />\n";
    }

    public void refreshLang(Localizer t) {
        int idx = getSelectedIndex();
        removeAllItems();
        List list = getItemsList();
        for (Object s : list) {
            addItem(t.getMessage(s.toString()));
        }
        try {
            setSelectedIndex(idx);
        } catch (Exception e) {
        }
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        fields.add(this);
        setXNode(node);
        setRuntimeVars(pv);
        XNode items[] = node.getChildren();
        for (int i = 0; i < items.length; i++) {
            addItemToList((items[i].getContent()));
        }
        if (Tool.isNaN(node.getAttr("value"))) {
            setPropName(node.getAttr("value"));
            setRightIdx(Tool.toInt(pv.getVar(node.getAttr("value")).toString()));
        } else {
            setRightIdx(Tool.toInt(node.getAttr("value")));
        }
        setNeedRandom(Tool.toBool(node.getAttr("random")));
        addAllItemsToComponent();
        refreshLang(l);

        return true;
    }

    public String toXML() {
        String itemlist = "";
        List list = getItemsList();
        for (int i = 0; i < list.size(); i++) {
            itemlist += "<item>" + Tool.escapeXML(list.get(i).toString())
                    + "</item>";
        }
        return "<i18ncombobox value=\"" + makeValueStr() + "\">" + itemlist
                + "</i18ncombobox>";
    }
}
