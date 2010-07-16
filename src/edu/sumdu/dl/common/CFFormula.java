/*
Revision 1.1 2009/07/15
Added buttons for some math operations in DialogFormula

 */
package edu.sumdu.dl.common;

import java.awt.event.*;
import javax.swing.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import edu.sumdu.dl.formula.*;
import edu.sumdu.dl.calc.*;
import java.net.URL;

/** Интерактивное поле ввода вычисляемой формулы */
public class CFFormula extends JPanel implements MouseListener, CheckField,
        TStorable, NodeStorable {

    private String curFormula;        // текущая формула
    boolean ignoreCase = false;

    public void setFormula(String f) {
        curFormula = f;
        AS.setCol(Color.blue);
        repaintFormula();
    }

    public String getFormula() {
        return curFormula;
    }
    private FormulaRender fr;
    private VarTable vt;          // таблица переменных
    private String checkFormula; // требуемая формула

    public String getCheckFormula() {
        return checkFormula;
    }

    public void setCheckFormula(String newcheckFormula) {
        checkFormula = newcheckFormula;
    }
    private String vars[]; // массив имен переменных, кот. используются в
    // формулах
    private Variable tvary[];

    public void addVar(XNode node) {
        double min, max, cur;
        if (node.getAttr("min") != null) {
            min = Tool.toDouble(node.getAttr("min"));
        } else {
            min = -10;
        }
        if (node.getAttr("max") != null) {
            max = Tool.toDouble(node.getAttr("max"));
        } else {
            max = 10;
        }
        cur = (min + max) / 2;
        String newvars[] = new String[vars.length + 1];
        System.arraycopy(vars, 0, newvars, 0, vars.length);
        Variable newtvar[] = new Variable[vars.length + 1];
        System.arraycopy(tvary, 0, newtvar, 0, vars.length);
        vars = newvars;
        String varName = node.getContent();
        if (ignoreCase) {
            varName = varName.toLowerCase();
        }
        vars[vars.length - 1] = varName;
        tvary = newtvar;
        tvary[vars.length - 1] = new Variable(varName, "double", min, max, cur,
                1e-4, "", true, true, "");

        vt.add(tvary[vars.length - 1]);

    }

    /**
     * inis - начальная формула для отображения checkS - требуемая формула vat -
     * таблица переменных vrs - список используемых переменных
     */
    public CFFormula() {
        super(new FlowLayout());
        setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        curFormula = "x^2";
        vt = new VarTable();
        Color cool = AS.getCol();
        AS.setCol(Color.blue);
        fr = new FormulaRender(curFormula); // прорисовка формулы
        vars = new String[0];
        tvary = new Variable[0];

        add(fr);
        this.addMouseListener(this);
        AS.setCol(cool);
        // this.setFocusable(true);
        // addFocusListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        changeFormula();
        setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

    }

    // Invoked when the mouse button has been clicked (pressed and released) on
    // a component.
    public void mouseEntered(MouseEvent e) {
    }

    // Invoked when the mouse enters a component.
    public void mouseExited(MouseEvent e) {
    }

    // Invoked when the mouse exits a component.
    public void mousePressed(MouseEvent e) {
    }

    // Invoked when a mouse button has been pressed on a component.
    // при щелчке по формуле - ввод новой формулы
    public void mouseReleased(MouseEvent e) {
    }

    // private boolean gained=false;
    // public void focusGained(FocusEvent ev){
    // if(!gained){
    // fr.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.blue));
    // changeFormula();
    // gained=true;
    // }
    // }
    // public void focusLost(FocusEvent ev){
    // gained=false;
    // fr.setBorder(BorderFactory.createEmptyBorder());
    // }
    private void changeFormula() {
        /*
         * String curFormula1 =
         * JOptionPane.showInputDialog("New formula",curFormula);
         */
        DialogFormula df = new DialogFormula((Frame) SwingUtilities.getAncestorOfClass(java.awt.Frame.class, this), curFormula);
        df.setVisible(true);
        String curFormula1 = df.getText();
        df.dispose();
        df = null;
        if (curFormula1 == null || curFormula1.equals("")) {
            curFormula1 = "Wrong";
        } else {
            curFormula = curFormula1;
        }
        if (isCorrect()) {
            AS.setCol(new Color(0, 200, 0));
        } else {
            AS.setCol(Color.red);
        }
        repaintFormula();
    }

    protected void repaintFormula() {
        fr.removeAll();
        fr.eval(curFormula);
        fr.revalidate();
        fr.repaint();
        // fr.getParent().validate();
    }

    public boolean isCorrect() {
        try {
            if (evaled) {
                setCheckFormula(myRTV.getVar(getContent()).toString());
            }

            if (ignoreCase) {
                return Formula.equals(checkFormula.toLowerCase(), curFormula.toLowerCase(), vt, vars);
            } else {
                return Formula.equals(checkFormula, curFormula, vt, vars);
            }
        } catch (Exception e) {
            return false;
        }
    }

    public String getState() {
        return "<formula>" + Tool.escapeXML(curFormula) + "</formula>";
    }

    public void setState(XNode node) {
        setFormula(node.getContent());
        fr.repaint();
    }
    private String content = null;
    private boolean evaled = false;

    public void setContent(String s) {
        content = s;
    }

    public String getContent() {
        return content;
    }
    RuntimeVars myRTV;

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        fields.add(this);
        elements.add(this);
        myRTV = pv;
        if (node.getAttr("eval") != null && node.getAttr("eval").length() != 0) {
            setCheckFormula(pv.getVar(node.getAttr("eval")).toString());
            setContent(node.getAttr("eval"));
            evaled = true;

        } else {
            setCheckFormula(node.getAttr("value"));
        }
        setFormula(node.getContent());
        vars = new String[0];
        ignoreCase = Tool.toBool(node.getAttr("ignorecase"));
        XNode[] items = node.getChildren();
        for (int i = 0; i < items.length; i++) {
            if (items[i].getName().equals("var")) {
                addVar(items[i]);
            }
        }
        return true;
    }

    public String toXML() {
        String valueStr;
        if (evaled) {
            valueStr = " eval=\"" + Tool.escapeXML(getContent()) + "\"";
        } else {
            valueStr = " value=\"" + Tool.escapeXML(checkFormula) + "\" ";
        }
        String dumpS = "";
        if (ignoreCase) {
            valueStr += "ignorecase=\"true\" ";
        }
        for (int i = 0; i < vars.length; i++) // dumpS+="<var>"+vars[i]+"</var>";
        {
            dumpS += tvary[i].getTag();
        }
        return "<askformula " + valueStr + " >" + Tool.escapeXML(curFormula)
                + dumpS + "</askformula>";
    }
}

class DialogFormula extends JDialog implements ActionListener, DocumentListener {

    JFrame jframe;
    JTextArea zoneTexte;
    String texteEquation;
    JPanel epane;
    FormulaRender mathcomp;

    public DialogFormula(Frame fr, String texteEquation) {
        super(fr, "Equation editor", true);
        this.texteEquation = texteEquation;
        JPanel cpane = new JPanel(new BorderLayout());
        setContentPane(cpane);
        setMaximumSize(new Dimension(600, 600));
        setMinimumSize(new Dimension(200, 200));

        epane = new JPanel(new BorderLayout());
        mathcomp = new FormulaRender(texteEquation);
        epane.add(mathcomp, BorderLayout.CENTER);
        zoneTexte = new JTextArea(texteEquation, 2, 40);
        zoneTexte.setLineWrap(true);
        zoneTexte.setWrapStyleWord(true);
        zoneTexte.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        zoneTexte.getDocument().addDocumentListener(this);
        epane.add(zoneTexte, BorderLayout.SOUTH);
        cpane.add(epane, BorderLayout.CENTER);

        JPanel bpane = new JPanel(new GridLayout(0, 1, 2, 2));
        JButton boutonAnnuler = new JButton("Cancel");
        boutonAnnuler.addActionListener(this);
        boutonAnnuler.setActionCommand("Cancel");
        JPanel bpane2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel bpane1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        bpane2.add(boutonAnnuler);
        JButton boutonOK = new JButton("OK");
        boutonOK.addActionListener(this);
        boutonOK.setActionCommand("OK");
        bpane2.add(boutonOK);

        bpane.add(bpane1);
        bpane.add(bpane2);

        JButton absBut = new IconfiedJButton(getClass().getResource(
                Trainer.ICONS_PATH + Trainer.icons.get("math.abs")), "absolute");
        absBut.addActionListener(this);
        absBut.setActionCommand("abs");
        JButton sqrtBut = new IconfiedJButton(getClass().getResource(
                Trainer.ICONS_PATH + Trainer.icons.get("math.sqrt")), "sqrt");
        sqrtBut.addActionListener(this);
        sqrtBut.setActionCommand("sqrt");
        JButton sqrBut = new IconfiedJButton(getClass().getResource(
                Trainer.ICONS_PATH + Trainer.icons.get("math.sqr")), "sqr");
        sqrBut.addActionListener(this);
        sqrBut.setActionCommand("sqr");
        JButton lnBut = new IconfiedJButton(getClass().getResource(
                Trainer.ICONS_PATH + Trainer.icons.get("math.ln")), "ln");
        lnBut.addActionListener(this);
        lnBut.setActionCommand("ln");
        JButton expBut = new IconfiedJButton(getClass().getResource(
                Trainer.ICONS_PATH + Trainer.icons.get("math.exp")), "exp");
        expBut.addActionListener(this);
        expBut.setActionCommand("exp");

        bpane1.add(absBut);
        bpane1.add(sqrtBut);
        bpane1.add(sqrBut);
        bpane1.add(lnBut);
        bpane1.add(expBut);

        cpane.add(bpane, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(boutonOK);
        setSize(new Dimension(300, 200));
        zoneTexte.requestFocus();
        addWindowListener(new WindowAdapter() {

            public void windowActivated(WindowEvent we) {
                zoneTexte.requestFocus();
            }
        });
        setLocationRelativeTo(null);
    }

    public String getText() {
        return texteEquation;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if ("OK".equals(cmd)) {
            actionOK();
        } else if ("Cancel".equals(cmd)) {
            texteEquation = null;
            setVisible(false);
        } else if ("abs".equals(cmd)) {
            insertEquantion("abs", "(", ")");
        } else if ("sqrt".equals(cmd)) {
            insertEquantion("sqrt", "(", ")");
        } else if ("sqr".equals(cmd)) {
            insertEquantion("", "(", ")^2");
        } else if ("ln".equals(cmd)) {
            insertEquantion("ln", "(", ")");
        } else if ("exp".equals(cmd)) {
            insertEquantion("exp", "(", ")");

        }
    }

    protected void insertEquantion(String eqv, String end1, String end2) {
        String selected = zoneTexte.getSelectedText();
        if (selected == null) {
            int pos = zoneTexte.getCaretPosition();
            if (pos < 0) {
                zoneTexte.append(eqv + end1 + end2);
                zoneTexte.setCaretPosition(eqv.length() + 1);
            } else {
                zoneTexte.insert(eqv + end1 + end2, pos);
                zoneTexte.setCaretPosition(eqv.length() + pos + 1);
            }
            zoneTexte.requestFocus();
        } else {
            int start = zoneTexte.getSelectionStart();
            int end = zoneTexte.getSelectionEnd();
            // zoneTexte.replaceRange(eqv+"("+selected+")", start, end);
            zoneTexte.requestFocus();
            zoneTexte.replaceSelection(eqv + end1 + selected + end2);
            zoneTexte.setCaretPosition(start + (eqv + end1).length());
            zoneTexte.moveCaretPosition(end + (eqv + end1).length());
            changementTexte();
        }
    }

    protected void actionOK() {
        setVisible(false);
    }

    protected void changementTexte() {
        if (zoneTexte.getText().indexOf('\n') != -1) {
            actionOK();
        } else {
            texteEquation = zoneTexte.getText();
            majAffichage();
        }
    }

    public void insertUpdate(DocumentEvent e) {
        changementTexte();
    }

    public void removeUpdate(DocumentEvent e) {
        changementTexte();
    }

    public void changedUpdate(DocumentEvent e) {
        changementTexte();
    }

    protected void majAffichage() {
        mathcomp.setFormula(texteEquation);
        mathcomp.repaint();
    }
}

class IconfiedJButton extends JButton {

    public IconfiedJButton(URL iconPath) {
        this(iconPath, "");
    }

    public IconfiedJButton(URL iconPath, String hint) {
        super(new ImageIcon(iconPath));
        // setPreferredSize(new Dimension(getIcon().getIconWidth()+3
        // ,getIcon().getIconHeight()+3));
        setPreferredSize(new Dimension(30, 27));
        setToolTipText(hint);
    }
}
