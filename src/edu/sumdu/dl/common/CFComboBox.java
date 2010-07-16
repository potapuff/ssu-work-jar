package edu.sumdu.dl.common;

import java.util.*;
import java.awt.Container;
import javax.swing.*;

// Выпадающий список с одним правильным ответом
public class CFComboBox extends JComboBox implements CheckField, TStorable,
        NodeStorable {

    private int ridx; // номер правильного варианта
    private ArrayList<String> itemsList = new ArrayList<String>();
    private boolean random = false;
    //private Object rightAns = null;
    private String[] orig = null;

    public CFComboBox() {
        super();
    }

    public CFComboBox(String s[], int idx) {
        super(s);
        ridx = idx;
    }

    public List getItemsList() {
        return itemsList;
    }

    public void setXNode(XNode n) {
        node = n;
    }

    public boolean isCorrect() { // проверка на правильность выбранного элемента
        System.out.println(pv.getVar(node.getAttr("value")).toString());
        if (node == null) {
            return getSelectedIndex() == ridx;
        }
        if (Tool.isNaN(node.getAttr("value"))) {
            setPropName(node.getAttr("value"));
            setRightIdx(Tool.toInt(pv.getVar(node.getAttr("value")).toString()));
        } else {
            setRightIdx(Tool.toInt(node.getAttr("value")));
        }

        int idx = 0;
        for (String s : itemsList) {
            if (s.equals(orig[ridx])) {
                break;
            }
            idx++;
        }

        return getSelectedIndex() == idx;
    }

    public void setRightIdx(int i) {
        ridx = i;
        //rightAns = itemsList.get(i) ;
    }

    public String getState() {
        return "<combobox value=\"" + getSelectedIndex() + "\" />\n";
    }

    public void setState(XNode node) {
        setSelectedIndex(Tool.toInt(node.getAttr("value")));
    }

    public void addItemToList(String s) {
        itemsList.add(s);
    }

    public void addAllItemsToComponent() {
        this.removeAllItems();
        orig = new String[itemsList.size()];
        orig = (String[]) itemsList.toArray(orig);
        //System.out.println(Arrays.toString(orig));//
        if (random) {
            Collections.shuffle(itemsList);
            //setRightIdx(itemsList.indexOf(rightAns));
            random = false;
        }
        for (String o : itemsList) {
            addItem(o);
        }
    }

    public void clearList() {
        itemsList.clear();
    }

    public void setRuntimeVars(RuntimeVars pv) {
        this.pv = pv;
    }
    private XNode node;
    private RuntimeVars pv;

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        fields.add(this);
        this.node = node;
        this.pv = pv;
        XNode items[] = node.getChildren();
        for (int i = 0; i < items.length; i++) {
            //addItem(items[i].getContent());
            addItemToList(items[i].getContent());
            //System.out.println(items[i].getContent().getClass().getName());
        }
        if (Tool.isNaN(node.getAttr("value"))) {
            setPropName(node.getAttr("value"));
            setRightIdx(Tool.toInt(pv.getVar(node.getAttr("value")).toString()));
        } else {
            setRightIdx(Tool.toInt(node.getAttr("value")));
        }

        random = Tool.toBool(node.getAttr("random"));
        addAllItemsToComponent();
        return true;
    }

    public String toXML() {
        String itemlist = "";
        for (int i = 0; i < getItemCount(); i++) {
            itemlist += "<item>" + Tool.escapeXML(getItemAt(i).toString())
                    + "</item>";
        }
        return "<combobox value=\"" + makeValueStr() + "random=\"" + new Boolean(random).toString() + "\">" + itemlist
                + "</combobox>";
    }
    private String evalue = null;

    protected String makeValueStr() {
        if (evalue != null) {
            return evalue;
        } else {
            return "" + ridx;
        }
    }

    public void setPropName(String s) {
        evalue = s;
    }

    public String getPropName() {
        return evalue;
    }

    public void setNeedRandom(boolean b) {
        random = b;
    }
}
