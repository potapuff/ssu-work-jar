package edu.sumdu.dl.common;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Container;
import java.util.ArrayList;
import java.util.regex.*;
import java.net.URL;

public class DTHint extends JButton implements ActionListener, NodeStorable,
        TLocalized {

    public DTHint() {
        super();
        try {
            setIcon(new ImageIcon(getClass().getResource(
                    "/edu/sumdu/dl/common/images/hint.gif")));
            setBorder(BorderFactory.createEmptyBorder());
        } catch (Exception e) {
        }
        addActionListener(this);
        jep = new JEditorPane("text/html", "");
        jep.setEditable(false);
        jif = new JFrame("Hint"); // , true, true, true, true);
        jif.setBounds(200, 25, 400, 400);
        jif.setContentPane(new JScrollPane(jep));
        jif.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setToolTipText("Подсказка");
    }
    private JEditorPane jep;
    private JFrame jif;
    private String text = "";
    private int delay = 0;

    public void setHintText(String text) {
        this.text = text;
        jep.setText(replaceImages(text));
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        // delay=Tool.toInt(node.getAttr("delay"));
        setHintText(node.getContent());
        /*
         * if(pv.getVar("!APPLET!THE_DESKTOP!")!=null){
         * ((Container)pv.getVar("!APPLET!THE_DESKTOP!")).add(jif); }
         */
        setToolTipText(l.getMessage("hint"));
        target.add(this);
        elements.add(this);
        return true;
    }

    String replaceImages(String txt) {

        Pattern p = Pattern.compile("<img src=\"(.+?)\"");
        Matcher m = p.matcher(txt);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            URL url = getClass().getResource(m.group(1));
            System.err.println("URL:" + url + ":" + m.group(1));
            if (url != null) {
                m.appendReplacement(sb, "<img src=\"" + url + "\"");
            } else {
                m.appendReplacement(sb, m.group());
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public String toXML() {
        // return
        // "<hint delay=\""+delay+"\">"+Tool.escapeXML(String.valueOf(text))+"</hint>";
        return "<hint>" + Tool.escapeXML(String.valueOf(text)) + "</hint>";
    }

    public void actionPerformed(ActionEvent evt) {
        jif.setSize(400, 400);
        jif.setVisible(true);
    }

    public void refreshLang(Localizer tv) {
        setToolTipText(tv.getMessage("hint"));
    }
}
