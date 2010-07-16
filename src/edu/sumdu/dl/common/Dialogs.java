/*
$Log: Dialogs.java,v $

Revision 1.5
Added Wait dialog window
modified constructor for dialog window location

Revision 1.4  2006/09/11 10:51:15  nnick
Wrapped one exception in Dialogs, a bit refactored next step

Revision 1.3  2003/10/18 16:56:55  nnick
TODO: create default properties


Mostly fixed. Dialogs and Reporter now use Trainer.dialogs to get localized messages
Now it should send a Dump to server also.
Showing different replies on number of tries is not a good idea.
Maybe count errors? If one needs help - use sendDump.
Anyway i don't send info about every part.
THERE IS NO NEED IN THAT.

Revision 1.2  2003/10/18 14:17:32  nnick
Now to use Dialogs one should create instance and initBunlde(Localizer).
Localizer now uses default commonXXXX.properties, loaded at creation

Revision 1.1.1.1  2003/10/18 13:08:21  nnick
Common classes for trainer

Revision 1.3  2003/10/12 15:18:37  nnick
Fixed bug in packege name for dialogs call in reporter

Revision 1.2  2003/10/12 14:38:53  nnick
dialogs, reporter and DumpSwing are now in packages

Revision 1.7  2003/10/10 08:34:17  nnick
Added some comments for javadoc

Revision 1.6  2003/10/09 18:36:52  nnick
added comments

Revision 1.3  2003/10/09 16:26:22  sezal
Some changes in Dialogs.ShowReply, Dialogs.msg, adding Dialogs.initBundle

 */
package edu.sumdu.dl.common;

import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * Все типы диалоговых сообщений - локализация
 */
public class Dialogs {

    /** DesktopPane апплета, нужно для центрирования диалогов */
    public Container desktop;
    Object yesno[];
    Object ok[] = {"OK"};
    /** Набор ресурсов по умолчанию */
    Localizer bundle;
    Component applet;

    public Dialogs(Component applet) {
        this.applet = applet;
    }

    public String msg(String s) {
        return bundle.getMessage(s);
    }

    /** Загрузка ресурсов данного аплета */
    public void initBundle(Localizer l) {
        bundle = l;
        yesno = new Object[]{msg("Yes"), msg("No")};
    }

    public boolean YesNoDialog(String str) {
        return YesNoDialog(str, JOptionPane.PLAIN_MESSAGE);
    }

    public boolean YesNoDialog(String str, int type) {
        return (JOptionPane.showInternalOptionDialog(desktop, new JLabel(str),
                "", JOptionPane.YES_NO_OPTION, type, null, // don't use a custom
                // Icon
                yesno, // the titles of buttons
                yesno[0]) // default button title
                ) == 0 ? true : false;
    }

    public void MessageDialog(String str) {
        MessageDialog(str, JOptionPane.INFORMATION_MESSAGE);
    }

    public void MessageDialog(String str, int type) {
        JOptionPane.showInternalOptionDialog(desktop, new JLabel(str), "",
                JOptionPane.OK_OPTION, type, new ImageIcon(), // don't use a
                // custom Icon
                ok, // the titles of buttons
                ok[0]); // default button title
    }

    /**
     * Ввод пояснения для запроса о помощи
     */
    public String SubjDialog(String message, String initial) {
        JPanel p = new JPanel(new BorderLayout());
        String buts[] = new String[]{msg("help.send"), msg("cancel")};
        p.add(new JLabel(message), BorderLayout.NORTH);
        JTextArea ta = new JTextArea(initial, 6, 50);
        JScrollPane jsp = new JScrollPane(ta);
        ta.setLineWrap(true);
        p.add(jsp, BorderLayout.CENTER);
        boolean result = (JOptionPane.showInternalOptionDialog(desktop, p,
                msg("help.title"), JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, // don't use a custom Icon
                buts, // the titles of buttons
                buts[0]) // default button title
                ) == 0;
        if (result) {
            if (ta.getText().length() > 30) {
                return ta.getText();
            } else {
                MessageDialog(msg("help.tooshort"));
            }
        }
        return null;
    }

    /**
     *Сообщение о правильности решения
     * 
     * @param right
     *            - правильность ответов
     * @param res
     *            - ответ сервера не было ли ранее попыток
     * @param count
     *            - номер попытки
     */
    public int ShowReply(JApplet applet, boolean right, int res, int count) {
        try {
            if (right) {
                if (res == 0) // Ответил правильно с первого раза
                {
                    MessageDialog(msg("answer.ok"),
                            JOptionPane.QUESTION_MESSAGE);
                } else if (res == 1) // Ответил правильно, но не с первого раза
                {
                    MessageDialog(msg("answer.ok2"),
                            JOptionPane.QUESTION_MESSAGE);
                }
                try {
                    applet.getAppletContext().showDocument(
                            new URL(applet.getParameter("url_ok")));
                } catch (Exception ex) {
                }
            } else {
                if (++count > 2) { // Ошибся более 2 раз
                    if (YesNoDialog(msg("answer.error2"),
                            JOptionPane.ERROR_MESSAGE)) {
                        applet.getAppletContext().showDocument(
                                new URL(applet.getParameter("url_theory")),
                                "_blank");
                        count = 0; // После прочтения "теории" начинаем отсчёт
                        // заново.
                    }
                } else // Ошибся
                {
                    MessageDialog(msg("answer.error"),
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (MalformedURLException e) {
        }
        return count;
    }

    /**
     * Сообщение о правильности решения если нет возможности соединения с
     * сервером
     */
    public void ReportErrorMessage(boolean right) {
        if (right) {
            MessageDialog(msg("answer.report.ok"), JOptionPane.ERROR_MESSAGE);
        } else {
            MessageDialog(msg("answer.report.error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showWaitDialog() {
        if (waitDialog == null) {
            waitDialog = new WaitDialog();
        }
    }

    public void stopWaitDialog() {
        if (waitDialog != null) {
            waitDialog.dispose();
            waitDialog = null;
        }
    }
    private WaitDialog waitDialog = null;

    class WaitDialog extends JWindow {

        WaitDialog() {
            run();
        }

        public void run() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            setContentPane(panel);
            this.setVisible(true);
            JLabel textLab = new JLabel(msg("loading"));
            textLab.setFont(new Font("default", Font.ITALIC, 22));
            textLab.setForeground(Color.GREEN);
            JPanel tlabP = new JPanel();
            tlabP.add(textLab, SwingConstants.CENTER);
            panel.add(tlabP, BorderLayout.SOUTH);
            setSize(new Dimension((textLab.getPreferredSize().width) + 15,
                    (textLab.getPreferredSize().height) + 15));

            setLocationRelativeTo(applet);
            setVisible(true);
            panel.revalidate();
            repaint();
        }
    }
}
