package edu.sumdu.dl.common;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

// Выпадающий список с одним правильным ответом
public class CFComboBox extends JComboBox implements CheckField, TStorable,
        NodeStorable {

    private int rightIndex; // номер правильного варианта
    private boolean random = false;
    private ArrayList<ComboItem> myItemsList = new ArrayList<ComboItem>();
    private XNode node;
    private RuntimeVars pv;

    public CFComboBox() {
        super();
    }

//    public CFComboBox(String s[], int idx) {
//        super(s);
//        rightIndex = idx;
//    }

    protected List<ComboItem> getItemsList() {
        return myItemsList;
    }

    public void setXNode(XNode n) {
        node = n;
    }

    public boolean isCorrect() { // проверка на правильность выбранного элемента
        if (node == null) {
            return getSelectedIndex() == rightIndex;
        }
        if (Tool.isNaN(node.getAttr("value"))) {
            setRightIdx(Tool.toInt(pv.getVar(node.getAttr("value")).toString()));
        } else {
            setRightIdx(Tool.toInt(node.getAttr("value")));
        }
        int idx = 0;
        for (ComboItem ci : myItemsList) {
            if (ci.getIndex() == rightIndex) {
                break;
            }
            idx++;
        }
        return getSelectedIndex() == idx;
    }

    protected void setRightIdx(int i) {
        rightIndex = i;
    }
    
    protected int getRightIdx() {
        return rightIndex;
    }

    public String getState() {
        return "<combobox value=\"" + getSelectedIndex() + "\" />\n";
    }

    public void setState(XNode node) {
        setSelectedIndex(Tool.toInt(node.getAttr("value")));
    }

    protected void addItemToList(String cnt, String stl) {
        myItemsList.add(new ComboItem(cnt, stl, myItemsList.size()));
    }

    protected void addAllItemsToComponent() {
        this.removeAllItems();

        if (getRandom()) {
            Collections.shuffle(myItemsList);
        }
        for (ComboItem ci : myItemsList) {
            addItem("<html><p style=\"" + ci.getStyle() + "\">" + ci.getContent() + "</p></html>");
        }
    }

    protected void setRuntimeVars(RuntimeVars pv) {
        this.pv = pv;
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

        return true;
    }

    public String toXML() {
        StringBuilder itemlist = new StringBuilder("<combobox value=\"");
        itemlist.append(getRightIdx()).append("\" random=\"").append(getRandom()).append("\">");
        for (ComboItem ci : getItemsList()) {
            itemlist.append("<item style=\"").append(ci.getStyle()).append("\">")
                    .append(Tool.escapeXML(ci.getContent())).append("</item>");
        }
        return itemlist.append("</combobox>").toString();
    }

    protected void setRandom(boolean r) {
        random = r;
    }
    
    protected boolean getRandom() {
        return random;
    }

    class ComboItem {

        private String content;
        private String style;
        private int index;

        protected ComboItem(String content, String style, int isCorrect) {
            this.content = content;
            this.style = style;
            this.index = isCorrect;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
