package edu.sumdu.dl.common;

import javax.swing.JCheckBox; // Переключатель - правильные и неправильные ответы
import java.util.ArrayList;
import java.awt.Container;

// Переключатель - правильные и неправильные ответы
public class CFCheckBox extends JCheckBox implements CheckField, TStorable,
        NodeStorable {

    boolean value;        // = true - правльный вариант
    String valuePropName;

    public String getValuePropName() {
        return valuePropName;
    }

    public void setValuePropName(String s) {
        valuePropName = s;
    }

    public CFCheckBox() {
        super();
        valuePropName = null;
    }

    public CFCheckBox(boolean val, String label) { // правильность, текст -
        // подсказка
        super(label);
        value = val;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean v) {
        value = v;
    }

    public boolean isCorrect() {
        if (node != null) {
            String val = node.getAttr("value");
            if (!Tool.isBool(val)) {
                setValuePropName(val);
                setValue(Tool.toBool(pv.getVar(val).toString()));
            } else {
                setValue(Tool.toBool(val));
            }
        }

        boolean t = isSelected();
        if (t && value || !t && !value) {
            return true;
        } else {
            return false;
        }
    }

    public String getState() {
        return "<checkbox state=\"" + isSelected() + "\" />";
    }

    public void setState(XNode n) {
        setSelected(Tool.toBool(n.getAttr("state")));
    }
    private XNode node;
    private RuntimeVars pv;

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        fields.add(this);
        this.pv = pv;
        this.node = node;
        elements.add(this);
        String val = node.getAttr("value");
        if (!Tool.isBool(val)) {
            setValuePropName(val);
            setValue(Tool.toBool(pv.getVar(val).toString()));
        } else {
            setValue(Tool.toBool(val));
        }
        setText(node.getContent());
        return true;
    }

    public String toXML() {
        String valueStr = "";
        if (valuePropName != null) {
            valueStr = " value=\"" + valuePropName + "\" ";
        } else {
            valueStr = " value=\"" + value + "\" ";
        }
        return "<checkbox " + valueStr + " >" + getText() + "</checkbox>";
    }
}
