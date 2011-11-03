package edu.sumdu.dl.common;

/** Панель  с калькулятором
 *
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import edu.sumdu.dl.calc.*;

public class Calculator extends JPanel implements ActionListener, KeyListener {

    JTextField inputLine;
    JLabel errorLine;
    JLabel resultLine;
    String buf;
    java.text.NumberFormat formatter = java.text.NumberFormat.getNumberInstance();

    public Calculator() {
        this("2x2=?");
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
    }

    public Calculator(String title) {
        super();
        JPanel topP = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        inputLine = new JTextField("", 20);
        inputLine.addKeyListener(this);
        topP.add(inputLine);
        JButton b = new JButton();

        Object iconFile = Trainer.icons.get("equals");
        if (iconFile != null) {
            try {
                b.setIcon(new ImageIcon(getClass().getResource(
                        Trainer.ICONS_PATH + iconFile)));
                b.setBorder(BorderFactory.createEmptyBorder());
            } catch (Exception e) {
                b.setText("=");
            }
        } else {
            b.setText("=");
        }
        b.setFocusPainted(false);//!
        b.addActionListener(this);
        topP.add(b);
        add(topP);
        JPanel botP = new JPanel(new FlowLayout());
        resultLine = new JLabel();
        resultLine.setFont(new Font("default", Font.BOLD, 14));
        botP.add(resultLine);
        errorLine = new JLabel();
        botP.add(errorLine);
        add(botP);
        setBorder(BorderFactory.createTitledBorder(title));
        formatter.setMinimumFractionDigits(0);
        formatter.setMaximumFractionDigits(7);
        //formatter.setGroupingUsed(false);
    }

    public void setTitle(String title) {
        setBorder(BorderFactory.createTitledBorder(title));
    }

    void DoCalc() {
        buf = inputLine.getText().toLowerCase().replaceAll(",", ".");
        DimCalc dc = new DimCalc(buf, new VarTable());
        errorLine.setText("");
        double vl = dc.eval();
        if (dc.notOK) {
            errorLine.setText(dc.errorLine);
        }

        resultLine.setText("" + formatter.format(vl));
    }

    /** обработка нажатия на кнопку "=" */
    public void actionPerformed(ActionEvent c) {
        DoCalc();
    }

    /** обработка нажатия клавиши ENTER */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            DoCalc();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
