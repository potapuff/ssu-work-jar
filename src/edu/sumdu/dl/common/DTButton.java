package edu.sumdu.dl.common;

import javax.swing.JButton;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DTButton extends JButton implements NodeStorable {

    public DTButton() {
        super();
    }

    public DTButton(String s) {
        super(s);
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        setText(node.getContent());
        setActionName(node.getAttr("action"));
        setListenerName(node.getAttr("listener"));
        Object listner = pv.getVar(node.getAttr("listener"));
        if (listner != null && listner instanceof ActionListener) {
            addActionListener((ActionListener) listner);
        }
        return true;
    }
    String actionName, listenerName;

    public void setActionName(String s) {
        setActionCommand(s);
        actionName = s;
    }

    public String getActionName() {
        return actionName;
    }

    public void setListenerName(String s) {
        listenerName = s;
    }

    public String getListenerName() {
        return listenerName;
    }

    public String toXML() {
        return "<button action=\"" + Tool.escapeXML(getActionName())
                + "\" listener=\"" + Tool.escapeXML(getListenerName()) + "\">"
                + Tool.escapeXML(getText()) + "</button>";
    }
}
