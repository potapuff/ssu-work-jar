package edu.sumdu.dl.common;

import java.awt.Container;
import java.util.ArrayList;

public class CFComboBoxI18N extends CFComboBox implements TLocalized {

    private Localizer localizer;

    public CFComboBoxI18N() {
        super();
    }

//    public CFComboBoxI18N(String s[], int idx) {
//        this();
//        for (int i = 0; i < s.length; i++) {
//            addItem(s[i]);
//        }
//        setRightIdx(idx);
//    }
    public String getState() {
        return "<i18ncombobox value=\"" + getSelectedIndex() + "\" />\n";
    }

    public void refreshLang(Localizer t) {
        int idx = getSelectedIndex();
        removeAllItems();
        for (ComboItem ci : getItemsList()) {
            addItem("<html><p style=\"" + ci.getStyle() + "\">" + t.getMessage(ci.getContent()) + "</p></html>");
        }
        setSelectedIndex(idx);
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {

        target.add(this);
        elements.add(this);
        fields.add(this);
        setXNode(node);
        setRuntimeVars(pv);
        localizer = l;

        XNode items[] = node.getChildren();
        for (int i = 0; i < items.length; i++) {
            String style = items[i].getAttr("style");
            addItemToList(items[i].getContent(), (style == null ? "" : style));
        }
        if (Tool.isNaN(node.getAttr("value"))) {
            setRightIdx(Tool.toInt(pv.getVar(node.getAttr("value")).toString()));
        } else {
            setRightIdx(Tool.toInt(node.getAttr("value")));
        }

        setRandom(Tool.toBool(node.getAttr("random")));
        addAllItemsToComponent();
        refreshLang(l);

        return true;
    }

    public String toXML() {
        StringBuilder itemlist = new StringBuilder("<i18ncombobox value=\"");
        itemlist.append(getRightIdx()).append("\" random=\"").append(getRandom()).append("\">");
        for (ComboItem ci : getItemsList()) {
            itemlist.append("<item style=\"").append(ci.getStyle()).append("\">")
                    .append(Tool.escapeXML(localizer.getMessage(ci.getContent()))).append("</item>");
        }
        return itemlist.append("</i18ncombobox>").toString();
    }
}
