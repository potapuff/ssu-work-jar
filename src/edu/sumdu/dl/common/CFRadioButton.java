package edu.sumdu.dl.common;

// Переключатель - правильные и неправильные ответы
import javax.swing.JRadioButton;
import java.util.ArrayList;
import java.awt.Container;

// Переключатель - правильные и неправильные ответы
public class CFRadioButton extends JRadioButton implements CheckField,
        TStorable, NodeStorable {

    protected boolean value;        // = true - правльный вариант
    protected String valuePropName;

    public String getValuePropName() {
        return valuePropName;
    }

    public void setValuePropName(String s) {
        valuePropName = s;
    }

    public CFRadioButton() {
        super();
        valuePropName = null;
    }

    public CFRadioButton(boolean val, String label) { // правильность, текст -
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
        if (xnode != null) {
            String val = xnode.getAttr("value");
            if (!Tool.isBool(val)) {
                setValuePropName(val);
                setValue(Tool.toBool(pv.getVar(val).toString()));
            } else {
                setValue(Tool.toBool(val));
            }
            /* setText is not nessesary for <radio> and doesn't work for <i18nradio> */
//            setText(xnode.getContent());
        }


        boolean t = isSelected();
        boolean wellDone;
        if (t && value || !t && !value) {
            wellDone = true;
        } else {
            wellDone = false;
        }
        if (indicator != null) {
            indicator.setCorrect(wellDone);
        }
        return wellDone;
    }

    public String getState() {
        return "<radio state=\"" + isSelected() + "\" />";
    }

    public void setState(XNode n) {
        setSelected(Tool.toBool(n.getAttr("state")));
    }
    private XNode xnode;
    private RuntimeVars pv;

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        xnode = node;
        this.pv = pv;
        fields.add(this);
        elements.add(this);
        String val = node.getAttr("value");
        if (!Tool.isBool(val)) {
            setValuePropName(val);
            setValue(Tool.toBool(pv.getVar(val).toString()));
        } else {
            setValue(Tool.toBool(val));
        }
        setText(node.getContent());
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
        return "<radio " + valueStr + " >" + getText() + "</radio>";
    }
}
