package edu.sumdu.dl.common;

/*$Log: Trainer.java,v $

/* Revision 1.8  2009/10/01 
/* added importance level support for all steps
/*added sending done value of trainer after clicking "next" button
/* automatically setting up stepAmount value. no necessary to write setStepAmount(arg) in code. 
/* added automatically frames sizing (if user don't use setMainSize(), setTaskSize(). )
/* fixed some bags    
 */
/* Revision 1.7  2009/7/20 
/* cur_lang modified to public
/*removed new instance of Localizer in initFrames()
/*put math icons to icon's hashtable
/*removed some code to opportunity to change language if needL1ON == false
/*added wait dialog until information sends on server

/*Revision 1.65  2006/10/10 09:44:41  nnick
/*create XMLEncoder dump before sending (multi-threads problems)
/*
/*Revision 1.64  2006/10/07 07:19:02  nnick
/*Fixes to work with old ohrana truda tasks
/*
/*Revision 1.63  2006/09/19 09:59:36  nnick
/*Fixed standAloneMode (was set on wrong place)
/*
/*Revision 1.62  2006/09/11 12:51:57  nnick
/*Async sendWhatDone()
/*
/*Revision 1.61  2006/09/11 10:51:15  nnick
/*Wrapped one exception in Dialogs, a bit refactored next step
/*
/*Revision 1.60  2006/09/09 10:54:04  nnick
/*Added dependency data loading
/*
/*Revision 1.59  2006/09/09 09:35:25  nnick
/*Added Save/Load stateMap for standalones.
/*
/*Revision 1.58  2006/08/31 12:08:37  nnick
/*Can't remember :)
/*
/*Revision 1.57  2006/02/17 12:31:47  nnick
/*Removed dl.bool package - not used for 2.5 years
/*
/*Revision 1.56  2005/12/02 11:55:32  nnick
/*Added not(expr) to FormulaRender. No eval yet.
/*
/*Revision 1.55  2005/07/25 12:39:35  nnick
/*Fixed langSwitch and added Localizer.getCurrentLanguage()
/*
/*Revision 1.54  2005/07/22 07:56:18  nnick
/*Added sec, cosec, sh, ch functions, removed .au
/*
/*Revision 1.53  2004/09/29 07:40:05  nnick
/*Added templating in <i18n> element,
/*use [:varName:] to replace by it
/*
/*Revision 1.52  2004/09/09 11:49:58  nnick
/*Removed another legacy code
/*
/*Revision 1.51  2004/09/08 16:11:32  nnick
/*Added:
/*  Trainer.setNeedL10N(boolean) - that sets visibility of change language button
/*  Trainer, DumpSwing - removed chunk of legacy code, as idea changed
/*
/*Revision 1.50  2004/09/03 08:30:39  nnick
/*Added scrolling for task panel and dynamic eval of vars for formulas and <ask> fields
/*
/*Revision 1.49  2004/07/19 09:10:11  nnick
/*Added <hint> element - popup window with HTML text
/*
/*Revision 1.48  2004/07/02 12:56:33  nnick
/*added layouts to <if> and <ifNot>
/*
/*Revision 1.47  2004/06/06 09:28:46  nnick
/*Added joke sound on asking help
/*Added such a math render:
/* 2 | x2=1
/*x  |
/*   | x1=0
/*
/*to be wrriten as points(x^2;x1=0;x2=1)
/*
/*Revision 1.46  2004/05/29 08:53:44  nnick
/*Some small fixes
/*
/*Revision 1.45  2004/05/11 08:14:36  nnick
/*Some fixes in integral rendering
/*
/*Revision 1.44  2004/03/12 12:57:18  nnick
/*Fixed typos in LexAnal for arc functions,
/*added showing != not equal, ~= approx equal, <= less eq, >= greater or eq
/*in FormulaRender
/*
/*Revision 1.43  2004/03/04 09:27:09  nnick
/*Improving state store/restoring vis network
/*
/*Revision 1.42  2004/03/03 09:25:41  nnick
/*Small bug (KS>=0) fixed
/*
/*Revision 1.41  2004/03/03 09:04:18  nnick
/*No more saving dump pn user's HDD
/*Testing save/restore status feature
/*
/*Revision 1.40  2004/03/03 08:03:08  nnick
/*Added eval attribute for template call
/*
/*Revision 1.39  2004/03/01 13:28:37  nnick
/*Fixed minor bug in sending temp store
/*
/*Revision 1.38  2004/02/29 13:55:44  nnick
/*Added inc try_num before task run
/*
/*Revision 1.37  2004/02/29 12:59:22  nnick
/*Sending only task an d last step during temporaryr reports
/*
/*Revision 1.36  2004/02/22 16:00:48  nnick
/*Added detailed report about progress in steps
/*Applet uses "temp_store" parameter to send CGI request.
/*Report is sent after a step is done
/*
/*Revision 1.35  2003/12/14 16:57:47  nnick
/*Fixed steps title change after lang change
/*
/*Revision 1.34  2003/11/17 17:37:12  nnick
/*Moved ru/ua button,
/*checkformula now appears in center of screen
/*
/*Revision 1.33  2003/11/17 11:40:42  nnick
/*Added ':' to show \cdot in formulas,
/*fixed some bugs in Trainer
/*Fixed editor/help/index.html
/*
/*Revision 1.32  2003/11/14 07:29:58  nnick
/*Seems to be fixed the moment when adding frame back to pane
/*
/*Revision 1.31  2003/11/12 18:32:04  nnick
/*Tabs are displayed at start all,but disabled.
/*Fixed from/to pane behaviour.
/*Added minimal help.
/*Fixed some bugs in FormulaRender.
/*DumpSwing makes some incorrect text (when JLabel text contains <, > chars)
/*
/*
/*More bugs to come :)
/*
/*Revision 1.30  2003/11/12 08:33:10  nnick
/*Added ignorecase option in askformula
/*
/*Revision 1.29  2003/11/09 12:29:21  nnick
/*Some fixes in formula rendering - braces and input
/*
/*Revision 1.28  2003/11/06 16:20:28  nnick
/*Added buttons which can respond to user's action listeners,
/*added help in trainer
/*(help text is still missing, but the feture is)
/*
/*Revision 1.27  2003/11/06 14:12:03  nnick
/*Fixed checks in formulas,
/*added alive lang change
/*Help file is pending
/*
/*Revision 1.26  2003/11/03 09:19:37  nnick
/*Added stepDone call, fiexd some docs
/*
/*Revision 1.25  2003/11/02 18:40:22  nnick
/*Some changes in tables,
/*better edit properties,CSS in docs etc
/*
/*Revision 1.24  2003/11/02 11:38:42  nnick
/*Fixed some bugs and more docs
/*
/*Revision 1.23  2003/11/01 11:43:17  nnick
/*Some fixes to use javac from editor
/*
/*Revision 1.22  2003/10/29 17:26:06  nnick
/*Fixed bug in send dump
/*
/*Revision 1.21  2003/10/29 16:24:01  nnick
/*added alert listener to call from steps
/*
/*Revision 1.20  2003/10/29 15:33:52  nnick
/*Typos fixed
/*
/*Revision 1.18  2003/10/29 08:12:05  nnick
/*Current step can also be moved to fame
/*
/*Revision 1.17  2003/10/28 13:13:03  nnick
/*Calculatore design fixed
/*
/*Revision 1.16  2003/10/28 12:37:18  nnick
/*Fixed props.
/*
/*Revision 1.15  2003/10/28 12:05:36  nnick
/*fixed bug in loading common props, added from/to fraem feature
/*
/*Revision 1.14  2003/10/27 19:58:17  nnick
/*Fixed bug in setting sizes
/*
/*Revision 1.13  2003/10/27 17:17:25  nnick
/*Tabs are scrolled now
/*
/*Revision 1.12  2003/10/23 09:51:16  nnick
/*Bug fixed
/*
/*Revision 1.11  2003/10/23 09:46:53  nnick
/*Bug in adding next step
/*
/*Revision 1.10  2003/10/23 09:25:33  nnick
/*fixed some bugs in type cast
/*
/*Revision 1.9  2003/10/23 09:14:11  nnick
/*Steps' content is placed now on JScrollPane
/*
/*Revision 1.8  2003/10/23 08:28:59  nnick
/*Changed some API
/*
/*Revision 1.7  2003/10/22 17:10:37  nnick
/*
/*Now setting var the+_applet_is_running to true to enable DTif work at start time
/*
/*Revision 1.6  2003/10/21 17:19:07  nnick
/*Added methods setVar, generateTask for more abstraction
/*
/*Revision 1.5  2003/10/21 15:02:13  nnick
/*Some fixes due to user-defined steps
/*
/*Revision 1.4  2003/10/20 08:18:41  nnick
/*Fixed bug in DTUserStepm - didn't add itself to target. Localizer attempta again
/*to load props from file -- needed by TaskEditor
/*
/*Revision 1.3  2003/10/18 16:56:55  nnick
/*TODO: create default properties
/*
/*
/*Mostly fixed. Dialogs and Reporter now use Trainer.dialogs to get localized messages
/*Now it should send a Dump to server also.
/*Showing different replies on number of tries is not a good idea.
/*Maybe count errors? If one needs help - use sendDump.
/*Anyway i don't send info about every part.
/*THERE IS NO NEED IN THAT.
/*
/*Revision 1.2  2003/10/18 14:33:00  nnick
/*Added Dialogs dialogs object,
/*removed some buttons of restore state, change language
/*changed default resource keys
/*added getDump to use in storeState(), sendState()
/**/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.plaf.*;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

/** Базовый класс для апплетов интерактивных тренажеров */
public class Trainer extends JApplet implements ActionListener,
        InternalFrameListener, FailListener, AlertListener {

    /**
     *
     */
    private static final long serialVersionUID = -3935967112011633878L;
    public RuntimeVars fullTask;
    /** текущий шаг, */
    public int curStep;
    /** шаги */
    public Step step[];
    /** кол-во шагов */
    public int stepAmount;
    /** панели для отображения шагов */
    public JComponent step_panels[];
    public Step uslovie;
    /** панель содержимого */
    public JTabbedPane content;
    /** фрейм условия */
    public JInternalFrame taskFrame;
    /** фрейм решения */
    public JInternalFrame solveFrame;
    /** фрейм решения */
    public JInternalFrame helpFrame;
    /** фрейм тренажера */
    public JFrame frame;
    /** панель с закладками для шагов */
    public JPanel tabbedContent;
    /** панель условия */
    public JPanel taskPanel;
    /** основная панель */
    public JDesktopPane desktopPane;
    /** панель командных кпопок - "Дальше", "Указания" etc и калькулятора */
    public JPanel commandPanel;
    /** высота и ширина фрейма решения */
    int a_width, a_height;
    /** высота и ширина фрейма условия */
    int u_width, u_height;
    /** необходимость калькулятора */
    public boolean needCalc;
    public boolean taskShown;
    /** Calculator field */
    public Calculator calculator;
    public String appId, digest;
    public int try_num = 0;
    /** сообщение для отправки на сервер */
    public String messageToSend,
            /** название задания */
            TaskName;
    /** задание решено и отправлено */
    public boolean is_done_sent = false;
    String testPassId = "";
    boolean needL10N = true;
    /*Размер фрейма решения задан пользователем*/
    private boolean solveFrameSizedByUser = false;
    /*Размер фрейма задания задан пользователем*/
    private boolean taskFrameSizedByUser = false;
    private int trainerDoneValue = 0;/* 0-100 */

    private int importanceLevelSum = -1;

    /** установка начальных параметров */
    public void setSizes() {
        // Rewrite it in your code
        setDefaultSize();
    }

    public void setStepAmount(int newSize) {
        stepAmount = newSize;
        if (step == null) {
            step = new Step[stepAmount];
            for (int i = 0; i < stepAmount; i++) {
                step[i] = new Step();
            }
        }
    }

    public void setTaskSize(int wid, int hei) {
        u_width = wid;
        u_height = hei + 20;
        taskFrameSizedByUser = true;
    }

    public void setMainSize(int wid, int hei) {
        a_width = wid;
        a_height = hei + 20;
        solveFrameSizedByUser = true;
    }

    public void setNeedL10N(boolean need) {
        needL10N = need;
    }

    public void setNeedCalc(boolean need) {
        needCalc = need;
    }

    /**
     * финт для случая запуска не как апплета, а как приложения /* - требуется
     * аплет разместить на фрейме
     */
    public static void getApp(Trainer app) {
        app.appId = app.getClass().getName();
        app.standAloneMode = true;
        app.init();
        JFrame frame = new JFrame(app.TaskName);
        frame.setSize(app.a_width + app.u_width + 10, Math.max(app.u_height,
                app.a_height) + 30);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            if (System.getProperty("TEDworkCP") != null) {
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        } catch (Exception e) {
        }
        frame.setVisible(true);
        frame.setContentPane(new JPanel(new BorderLayout()));
        frame.setContentPane(app.getContentPane());

        app.frame = frame;
        app.start();
    }
    protected boolean standAloneMode = false;

    /**
     * Инициализация фреймов тренажера /* taskFrame - диалоговое окно
     * отображения условия и перехода к новому варианту /* в центре - условие,
     * внизу кнопка "Новый вариант"
     */
    private void setDefaultSize() {
        a_width = 600;
        a_height = 350;
        u_width = 200;
        u_height = 200;
        setNeedCalc(false);
    }

    JButton saveButton ;
    JButton loadButton 
    
    public void initFrames() {
        TaskName = localizer.getMessage("task.name");
        /** основная панель */
        desktopPane = new JDesktopPane();
        // вот на нее и будем добавлять все остальное
        setContentPane(desktopPane);
        fullTask.setVar("!APPLET!THE_DESKTOP!", desktopPane);
        dialogs.desktop = this.getContentPane();
        taskFrame = new JInternalFrame(localizer.getMessage("task.task"),
                true, //resizable
                false, //closable
                true, //maximizable
                true //iconifiable
                );
        desktopPane.add(taskFrame);

        JPanel p = new JPanel(new BorderLayout());
        taskFrame.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        taskFrame.setContentPane(p);
        taskPanel = new JPanel(new GridLayout(1, 1));

        // сообщение об устаревшей версии java
        final String javaVersion = System.getProperty("java.version");
        if (!(javaVersion.startsWith("1.6") || javaVersion.startsWith("1.7"))) {
            String versionMsg = localizer.getMessage("invalid.java.version") + javaVersion;
            JPanel pn = new JPanel(new FlowLayout());
            pn.setBackground(new Color(255, 204, 204));
            javax.swing.border.Border br = BorderFactory.createLineBorder(new Color(204, 102, 102), 1);

            pn.setBorder(br);

            JLabel msg = new JLabel();
            msg.setForeground(new Color(51, 51, 51));

            pn.add(msg);
            msg.setText(versionMsg);
            p.add("North", pn);
        }


        // в центре - условие, внизу кнопка "Новый вариант"
        p.add("Center", new JScrollPane(taskPanel));
        JButton but = createButton("task.new");
        newButton = but;
        but.setActionCommand("Restart");
        JPanel newPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        p.add("South", newPane);
        but.addActionListener(this);

        // solveFrame - фрейм решения
        solveFrame = new JInternalFrame(localizer.getMessage("task.solve"),
                true, //resizable
                false, //closable
                false, //maximizable
                false //iconifiable
                );
        desktopPane.add(solveFrame);

        solveButton = createButton("task.solve.it");
        solveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                solveFrame.setVisible(true);
                solveButton.setEnabled(false);
                try_num++;
                sendWhatDone(); // make a record that work has begun
            }
        });
        newPane.add(newButton);
        newPane.add(solveButton);

        if (needSaveLoad) {
            saveButton = createButton("task.save");
            saveButton.setActionCommand("save");
            saveButton.addActionListener(this);
            newPane.add(saveButton);
            loadButton = createButton("task.load");
            loadButton.setActionCommand("load");
            loadButton.addActionListener(this);
            newPane.add(loadButton);
        }

        solveFrame.setSize(a_width, a_height);
        solveFrame.setLocation(u_width + 5, 0);
        solveFrame.setVisible(false);
        solveFrame.addInternalFrameListener(this);

        helpFrame = new MetalworksHelp(helpUrl);
        desktopPane.add(helpFrame);
        helpFrame.setTitle(localizer.getMessage("help.help.help"));

        // создаем панель для фрейма решения
        tabbedContent = new JPanel(new BorderLayout());
        tabbedContent.setLayout(new BorderLayout());
        solveFrame.setContentPane(tabbedContent);
        commandPanel = new JPanel();

        but = createButton("task.help");
        helpButton = but;
        but.addActionListener(this);
        but.setActionCommand("HelpReq");
        commandPanel.add(but);
        but = createButton("task.to.frame");
        ptfButton = but;
        but.addActionListener(this);
        but.setActionCommand("toFrame");
        commandPanel.add(but);

        // добавляем кнопку "Далi!" и калькулятор, если необходимо
        but = createButton("task.next");
        nextButton = but;
        but.addActionListener(this);
        but.setActionCommand("Next");
        //but.setBackground(new Color(0xF7, 0xEf, 0xF0));
        commandPanel.add(but);

        if (needL10N) { // if we need localization
            if (cur_lang.equals(RUSSIAN_LANG)) {
                but = createButton("task.switch.lang_ru");
            } else {
                but = createButton("task.switch.lang_uk");
            }
            but.setToolTipText(cur_lang);
            langButton = but;
            but.addActionListener(this);
            but.setActionCommand("RU/UA");
            langButton.setToolTipText(cur_lang);
            newPane.add(langButton);
        }

        but = createButton("task.help.user");
        userHelpButton = but;
        but.addActionListener(this);
        but.setActionCommand("Help");
        commandPanel.add(but);

        if (needCalc) {
            JPanel pl = new JPanel(new BorderLayout());
            pl.add(commandPanel);
            Calculator calculator = new Calculator();
            calculator.setTitle(localizer.getMessage("calculator.title"));
            pl.add(calculator, "South");
            commandPanel = pl;
        }
    }

    public void setImportanceLevel(int stepindex, ImportanceLevel il) {

        if (stepindex < 1 || this.step.length < stepindex) {
            throw new WrongParameterException("there are no step with index: "
                    + stepindex + ". There are " + this.step.length + " steps");
        }
        this.step[stepindex - 1].setImportanceLevel(il);
    }
    public static Hashtable<String, String> icons = new Hashtable<String, String>();

    static {
        icons.put("task.help", "help_tutor.png");
        icons.put("task.next", "nextstep.png");
        icons.put("task.new", "new.png");
        icons.put("task.solve.it", "next.png");
        icons.put("task.help.user", "help.png");
        icons.put("task.to.frame", "window_list.png");
        icons.put("task.switch.lang", "ru.png");
        icons.put("task.switch.lang_ru", "ru.png");
        icons.put("task.switch.lang_uk", "ua.png");
        icons.put("task.save", "filesave.gif");
        icons.put("math.abs", "abs.gif");
        icons.put("math.sqrt", "sqrt.gif");
        icons.put("math.sqr", "sqr.gif");
        icons.put("math.ln", "ln.gif");
        icons.put("math.exp", "exp.gif");
        icons.put("equals", "equals.png");
    }
    public static final String ICONS_PATH = "/edu/sumdu/dl/common/images/";

    private JButton createButton(String key) {
        JButton bt = new JButton();
        String s = localizer.getMessage(key);
        bt.setToolTipText(s);
        Object iconFile = icons.get(key);
        if (iconFile != null) {
            try {
                bt.setIcon(new ImageIcon(getClass().getResource(
                        ICONS_PATH + iconFile)));
                bt.setBorder(BorderFactory.createEmptyBorder());
            } catch (Exception e) {
                bt.setText(s);
            }
        } else {
            bt.setText(s);
        }
        bt.setPreferredSize(new Dimension(43, 43));
        bt.setFocusPainted(false);//!
        //bt.setBorderPainted(true);
        bt.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
        return bt;
    }
    // инициализация аплета
    public JButton nextButton, newButton, helpButton, userHelpButton,
            langButton, ptfButton, solveButton;

    public void init() {
        setDefaultSize();
        if (stepAmount <= 0) {
            parseDesign();
            setStepAmount(rootNode.getChildren().length - 1);
        }
        setSizes();
        messageToSend = "";
        taskShown = true;
        localizer = new Localizer(cur_lang);
        localizer.loadMessages(this);
        dialogs = new Dialogs(this);
        dialogs.initBundle(localizer);
        fullTask = new RuntimeVars();

        fullTask.setVar("the_applet_is_running", new Boolean(true));
        initFrames();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) taskFrame.getUI()).setNorthPane(null);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) solveFrame.getUI()).setNorthPane(null);
        taskFrame.setBorder(BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        solveFrame.setBorder(BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        NewVariant();        
    }

    /*
     * вся нижеследующая лабуда для корректного отображения условия во время
     * решения
     */
    public void internalFrameClosing(InternalFrameEvent e) {
    }

    public void internalFrameClosed(InternalFrameEvent e) {
    }

    public void internalFrameOpened(InternalFrameEvent e) {
    }

    public void internalFrameIconified(InternalFrameEvent e) {
    }

    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
    }

    // если активировали фрейм решения, то показать и фрейм условия
    public void internalFrameActivated(InternalFrameEvent e) {
        taskShown = false;
        Umova();
    }

    // реакция на кнопки
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Next")) // попытка пройти к cледующему шагу
        {
            if (importanceLevelSum < 0) {
                for (Step st : step) {
                    importanceLevelSum += st.getImportanceLevel().getLevel();
                }
            }
            int points = 0;
            if (curStep < step.length) {
                points = (int) Math.round(step[curStep].getDoneRatio()
                        * step[curStep].getImportanceLevel().getLevel() * 10000
                        / importanceLevelSum) / 100;

            }
            if (Math.abs(100 - trainerDoneValue - points) <= 2) {
                points = 100 - trainerDoneValue;
            }
            trainerDoneValue += points;
            if (curStep >= stepAmount || step[curStep].isDone()) {
                // если уже все сделано или сделан текщий шаг
                stepDone(curStep);
                curStep++;
                updateElements();
                sendWhatDone(-1);
                if (curStep < stepAmount) {

                    // переход к следующую закладку
                    content.setEnabledAt(content.indexOfComponent(step_panels[curStep]), true);
                    content.setSelectedIndex(content.indexOfComponent(step_panels[curStep]));
                } else {
                    // все шаги выполнены! 
                    GameOver();
                }
            } else {
                sendWhatDone();
                trainerDoneValue -= points;
            }
        } else if (s.equals("Task")) { // Показать условие
            Umova();
        } else if (s.equals("Restart")) { // Выбрать другой вариант
            NewVariant();
        } else if (s.equals("RU/UA")) {
            switchLang();
        } else if (s.equals("HelpReq")) {
            sendHelpRequest();
        } else if (s.equals("toFrame")) {
            paneToFrame();
        } else if (s.equals("Help")) {
            showHelp();
        } else if (s.equals("save")) {
            saveStateMap();
        } else if (s.equals("load")) {
            loadStateMap();
        }

    }

    public void updateElements() {
        //обновить условие и все шаги до st
        uslovie.updateElements();
        for (int i = 0; i < step.length; i++) {
            step[i].updateElements();
        }
    }

    public void showHelp() {
        // show a help sreen
        helpFrame.setSize(600, 600);
        helpFrame.setVisible(true);
    }
    private String cur_lang = RUSSIAN_LANG;
    public final static String RUSSIAN_LANG = "ru_RU";
    public final static String UKR_LANG = "uk_UA";

    public void switchLang() {
        // if(!needL10N) return;
        if (cur_lang.equals(UKR_LANG)) {
            cur_lang = RUSSIAN_LANG;
        } else {
            cur_lang = UKR_LANG;
        }
        try {
            if (cur_lang.equals(UKR_LANG)) {
                if (langButton != null) {
                    langButton.setIcon(new ImageIcon(getClass().getResource(
                            ICONS_PATH + icons.get("task.switch.lang_uk"))));
                }
            } else {
                if (langButton != null) {
                    langButton.setIcon(new ImageIcon(getClass().getResource(
                            ICONS_PATH + icons.get("task.switch.lang_ru"))));
                }
            }
        } catch (Exception e) {
        }
        if (langButton != null) {
            langButton.setToolTipText(cur_lang);
        }
        if (calculator != null) {
            calculator.setTitle(localizer.getMessage("calculator.title"));
        }
        changeLanguage(cur_lang);
    }

    class FrameToPaneButton extends JButton implements ActionListener {

        JInternalFrame which;
        int position;
        int stepPos;

        FrameToPaneButton(JInternalFrame t, int step_pos) {
            super(localizer.getMessage("task.to.pane"));
            which = t;
            addActionListener(this);
            stepPos = step_pos;
        }

        public void actionPerformed(ActionEvent e) {
            which.remove(this);
            int newPos = 0;
            for (int i = 0; i < stepPos; i++) {
                if (content.indexOfComponent(step_panels[i]) >= 0) {
                    newPos++;
                }
            }
            content.insertTab(step[stepPos].getTitle(), null,
                    step_panels[stepPos], "", newPos);
            which.setVisible(false);
            which.dispose();
            int k;
            for (k = 0; k < stepAmount; k++) {
                if (step_panels[curStep] == content.getComponentAt(k)) {
                    break;
                }
            }
            try {
                content.setSelectedIndex(k);
            } catch (Exception e1) {
            }
        }
    }

    public void paneToFrame() {
        int i = content.getSelectedIndex();
        if (i < 0 || !content.isEnabledAt(i)) {
            return;
        }
        int k;
        if (i >= curStep) {
            return;
        }
        for (k = 0; k < stepAmount; k++) {
            if (step_panels[k] == content.getComponentAt(i)) {
                break;
            }
        }
        if (k > curStep) {
            return;
        }
        Rectangle dim = ((JScrollPane) step_panels[k]).getViewportBorderBounds();
        JInternalFrame f = new JInternalFrame(step[k].getTitle(),
                /** resizable */
                true,
                /** closable */
                false,
                /** maximizable */
                true,
                /** iconifiable */
                false);
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(BorderLayout.CENTER, step_panels[k]);
        JButton bt = new FrameToPaneButton(f, k);
        JPanel p = new JPanel();
        p.add(bt);
        f.getContentPane().add(BorderLayout.SOUTH, p);
        Dimension d1 = p.getPreferredSize();
        f.setSize(dim.width + 20, dim.height + d1.height + 20);
        desktopPane.add(f);
        f.setVisible(true);
    }
    boolean helpUsed = false;
    String last_help_message;

    public void sendHelpRequest() {
        try {
            String subj = dialogs.SubjDialog(dialogs.msg("answer.help"),
                    dialogs.msg("answer.subj"));
            if (subj != null) {
                helpUsed = true;
                last_help_message = subj;
                sendWhatDone();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String getDump() {
        StringBuffer buf = new StringBuffer();
        DumpSwing.clearImageList();
        buf.append(DumpSwing.dump(uslovie.getContent()));
        for (int i = 0; i <= curStep && i < stepAmount; i++) {
            buf.append(DumpSwing.dump(step[i].getContent()));
            buf.append("<hr>");
        }
        return buf.toString();
    }

    public void sendWhatDone() {
        sendWhatDone(0);
    }
    JLabel textLab;

    public void sendWhatDone(final int offByOne) {

        if (isRunningAsSCO() || standAloneMode) {
            return;
        }
        String tmpDump;

        if (curStep >= stepAmount)// show wait dialog in last step
        {
            dialogs.showWaitDialog();
        }

        try {
            tmpDump = xmlEncode(getStateMap());
        } catch (Exception ex) {
            ex.printStackTrace();
            tmpDump = "";
        }

        final String xmlDump = tmpDump;

        SwingUtilities.invokeLater(new Runnable() {

            int cur = curStep;

            public void run() {

                URL next_try_url, next_step_url, url3;
                try {
                    next_try_url = new URL(getCodeBase(),
                            getParameter("next_try_url"));
                    next_step_url = new URL(getCodeBase(),
                            getParameter("next_step_url"));
                } catch (Exception e) {
                    dialogs.stopWaitDialog();
                    return;
                }
                try {

                    int KS = cur >= stepAmount ? stepAmount - 1 : cur + offByOne;
                    if (new_trial) {
                        url3 = next_try_url;
                    } else {
                        url3 = next_step_url;
                    }
                    new_trial = false;
                    final StringBuffer bf = new StringBuffer();

                    bf.append("task_image=");
                    bf.append(URLEncoder.encode(new String(Base64.encode(com.keypoint.PngEncoderB.dumpComponentImage(uslovie.getContent()))), "UTF8"));
                    if (KS >= 0) {
                        bf.append("&step_image=");
                        bf.append(URLEncoder.encode(new String(Base64.encode(com.keypoint.PngEncoderB.dumpComponentImage(step[KS].getContent()))), "UTF8"));
                    }
                    String usedHelpString = "";
                    if (helpUsed) {
                        bf.append("&had_help_request=true");
                        bf.append("&help_request="
                                + URLEncoder.encode(last_help_message, "UTF8"));
                        last_help_message = "";
                        helpUsed = false;
                    }
                    if (cur >= stepAmount) {
                        bf.append("&correct=1");
                        bf.append("&message="
                                + URLEncoder.encode("Задача решена полностью"
                                + usedHelpString, "UTF8"));
                    } else {
                        bf.append("&message="
                                + URLEncoder.encode("Выполнено " + cur + " из "
                                + stepAmount + " шагов"
                                + usedHelpString, "UTF8"));
                    }
                    bf.append("&step=" + cur);
                    bf.append("&total=" + stepAmount);
                    bf.append("&try_num=" + try_num);
                    bf.append("&report_html="
                            + URLEncoder.encode(getReportHtml(), "UTF8"));
                    bf.append("&data_map="
                            + URLEncoder.encode(xmlDump, "UTF8"));
                    bf.append("&done=" + trainerDoneValue);

                    URLConnection connection = url3.openConnection();
                    connection.setDoOutput(true);

                    OutputStream out = connection.getOutputStream();
                    out.write(bf.toString().getBytes("UTF8"));
                    InputStream in = connection.getInputStream();



                    dialogs.stopWaitDialog();

                    String s = "";
                    int k;
                    byte buf[] = new byte[1024];
                    while ((k = in.read(buf)) > 0) {
                        s += new String(buf, 0, k);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        });

    }
    boolean new_trial = false;

    // Выбор нового варианта
    public void NewVariant() {
        tune();
        generateTask();
        step = new Step[stepAmount];
        solveButton.setEnabled(true);
        solveFrame.setVisible(false);
        testPassId = "";
        try_num++;
        helpUsed = false;
        new_trial = true;
        last_help_message = "";
        loadTask();
    }

    public void loadTask() {
        for (int i = 0; i < stepAmount; i++) {
            step[i] = new Step();
        }
        content = new JTabbedPane(JTabbedPane.TOP,
                JTabbedPane.SCROLL_TAB_LAYOUT);
        is_done_sent = false;
        tabbedContent.removeAll();
        tabbedContent.add("Center", content);
        tabbedContent.add("South", commandPanel);
        step_panels = new JComponent[stepAmount];
        taskShown = true;
        parseDesign();
        XNode childs[] = rootNode.getChildren();
        uslovie = new Step();
        uslovie.readDesign(childs[0], localizer, fullTask);
        taskPanel.removeAll();
        taskPanel.add(uslovie.getContent());
        Umova();
        for (int i = 1; i <= stepAmount; i++) {
            if (childs[i].getName().equals("userstep")) {
                step[i - 1] = (Step) fullTask.getVar(childs[i].getAttr("var"));
            }
            step[i - 1].readDesign(childs[i], localizer, fullTask);
        }
        for (int i = 0; i < stepAmount; i++) {
            step[i].setFailListener(this);
            step[i].setAlertListener(this);
            JScrollPane jp = new JScrollPane(step[i].getContent());
            step_panels[i] = jp;
            content.addTab(step[i].getTitle(), null, jp, "");
            if (i > 0) {
                content.setEnabledAt(i, false);
            }
        }

        fixSizes();
        content.setSelectedIndex(0);
        curStep = 0;
    }

    public void fixSizes() {
        if (!taskFrameSizedByUser) {
            taskFrameSizedByUser = !taskFrameSizedByUser;
            u_width = taskPanel.getPreferredSize().width + 20;
            u_height = taskPanel.getPreferredSize().height + 150;

        }
        Umova();
        if (!solveFrameSizedByUser) {
            solveFrameSizedByUser = !solveFrameSizedByUser;
            a_width = tabbedContent.getPreferredSize().width + 20;
            a_height = tabbedContent.getPreferredSize().height + 50;
            solveFrame.setSize(a_width, a_height);
            solveFrame.setLocation(taskPanel.getPreferredSize().width + 20 + 5, 0);
        }
        if (solveFrameSizedByUser) {
            solveFrame.setSize(a_width, a_height);
            solveFrame.setLocation(u_width + 5, 0);
        }
    }
    private XNode rootNode;

    void parseDesign() {
        if (rootNode != null) {
            return;
        }
        String str;
        URL url = this.getClass().getClassLoader().getResource(
                str = this.getClass().getName().replaceAll("\\.", "/")
                + ".xml");
        System.err.println(str + "" + url);
        rootNode = XNParser.parse(url);
    }

    /** Окончить тест */
    public void GameOver() {
        // JOptionPane.showMessageDialog (new Frame (),
        // localizer.getMessage("task.done"));
        if (isRunningAsSCO()) {
            completeSCO();
        }
        dialogs.ShowReply(this, true, 0, 0);
    }

    /** фрейм условия на передний план! */
    private void Umova() {
        try {
            taskFrame.setSize(u_width, u_height);
            taskFrame.setLocation(0, 0);
            taskFrame.setVisible(true);
            taskFrame.setIcon(false);
            taskFrame.moveToFront();
        } catch (Exception e) {
        }
    }

    public String getCurLang() {
        return cur_lang;
    }

    public void setCurLang(String curLang) {
        if (!(curLang.equals(UKR_LANG) || curLang.equals(RUSSIAN_LANG))) {
            throw new WrongParameterException(curLang + " is not "
                    + RUSSIAN_LANG + " or " + UKR_LANG);
        }
        cur_lang = curLang;
    }
    public Localizer localizer;
    Dialogs dialogs;

    public Dialogs getDialogs() {
        return dialogs;
    }

    private void changeLanguage(String newlang) {
        localizer.changeLanguage(this, newlang);
        newButton.setToolTipText(localizer.getMessage("task.new"));
        nextButton.setToolTipText(localizer.getMessage("task.next"));
        helpButton.setToolTipText(localizer.getMessage("task.help"));
        userHelpButton.setToolTipText(localizer.getMessage("task.help.user"));
        solveButton.setToolTipText(localizer.getMessage("task.solve.it"));
        taskFrame.setTitle(localizer.getMessage("task.task"));
        solveFrame.setTitle(localizer.getMessage("task.solve"));
        ptfButton.setToolTipText(localizer.getMessage("task.to.frame"));
        helpFrame.setTitle(localizer.getMessage("help.help.help"));
        uslovie.refreshLanguage(localizer);
        dialogs.initBundle(localizer);
        //frame.setTitle(localizer.getMessage("taskname"));
        for (int i = 0; i < stepAmount; i++) {
            step[i].refreshLanguage(localizer);
            int k = content.indexOfComponent(step_panels[i]);
            if (k >= 0) {
                content.setTitleAt(k, step[i].getTitle());
            }
        }
    }

    /** Установить переменную времени выполнения */
    public void setVar(String name, Object data) {
        fullTask.setVar(name, data);
    }

    /** Получить значение переменной времени выполнения */
    public Object getVar(String name) {
        return fullTask.getVar(name);
    }

    /** Создать задание */
    public void generateTask() {       
    }

    /** Провести дополнительные действия после восстановления состояния */
    public void afterRestore() {        
    }

    /** Действия по выполнению шага */
    public void stepDone(int stepNumber) {     
    }
    public int WARN_FAIL_LEVEL = 5;

    public void checkFailed(FailEvent evt) {
        String message = null;
        boolean oldJump = evt.jump;
        evt.jump = evt.stepOrigin.getFailCount() >= WARN_FAIL_LEVEL;
        if (evt.message == null || evt.message.equals("")) {
            evt.i18n = true;
            evt.message = evt.jump ? "error.jump" : "error.default";
        }
        message = evt.i18n ? localizer.getMessage(evt.message) : evt.message;
        if (!oldJump && evt.jump) {
            message = "<html>" + message + "<p>"
                    + localizer.getMessage("error.jump2") + "</html>";
        }
        if (!evt.jump) {
            // Просто сообщение об ошибке
            dialogs.MessageDialog(message, JOptionPane.ERROR_MESSAGE);
        } else {// Предложение почитать теорию
            if (dialogs.YesNoDialog(message, JOptionPane.ERROR_MESSAGE)) {
                try {
                    getAppletContext().showDocument(
                            new URL(getCodeBase(), getParameter("url_theory")),
                            "_blank");
                } catch (Exception e) {
                }
            }
        }

    }

    public void doAlert(String msg, boolean i18n) {
        String message = i18n ? localizer.getMessage(msg) : msg;
        dialogs.MessageDialog(message);
    }

    static {
        tune();
    }

    static void tune() {
        String[] toFix = new String[]{"Button", "CheckBox",
            "CheckBoxMenuItem", "ComboBox", "Desktop", "DesktopIcon",
            "EditorPane", "FormattedTextField", "Label", "List", "Menu",
            "MenuBar", "MenuItem", "OptionPane", "Panel", "PasswordField",
            "PopupMenu", "ProgressBar", "RadioButton",
            "RadioButtonMenuItem", "ScrollBar", "ScrollPane", "Separator",
            "Slider", "Spinner", "SplitPane", "TabbedPane", "Table",
            "TableHeader", "TextArea", "TextField", "TextPane",
            "TitledBorder", "ToggleButton", "ToolBar", "ToolTip", "Tree",
            "Viewport"};
        Font defont;
        defont = new Font("default", Font.PLAIN, 12);

        UIDefaults def = UIManager.getDefaults();
        FontUIResource defF = new FontUIResource(defont);
        ColorUIResource defFor = new ColorUIResource(0, 0, 0);
        //ColorUIResource defBack = new ColorUIResource(0xee, 0xee, 0xee);
        ColorUIResource defBack = new ColorUIResource(250, 250, 250);

        for (int i = 0; i < toFix.length; i++) {
            String key = toFix[i];
            def.put(key + ".font", defF);
            def.put(key + ".foreground", defFor);
            if (!key.matches("^(.+)(Field|torPane|ext)")) {
                def.put(key + ".background", defBack);
            }
        }
    }
    String reportHtml = "";

    public void setReportHtml(String reportStr) {
        reportHtml = reportStr;
    }

    public String getReportHtml() {
        return reportHtml == null ? "" : reportHtml;
    }
    // saving Map for standalone
    boolean needSaveLoad = false;

    public void setNeedSaveLoad(boolean flag) {
        needSaveLoad = flag;
    }

    // couple of mocks
    /**
     * overload in concrete trainer
     */
    public Map getStateMap() {
        return new java.util.HashMap();
    }

    /**
     * overload in concrete trainer
     */
    public void applyStateMap(Map map) {
        // ABSTRACT
    }
    JFileChooser saveLoadChooser = null;

    public void saveStateMap() {
        if (saveLoadChooser == null) {
            saveLoadChooser = new JFileChooser(".");
        }
        Map map = getStateMap(); // call to abstract
        try {
            int returnVal = saveLoadChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File outFile = saveLoadChooser.getSelectedFile();
                XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
                        new FileOutputStream(outFile)));
                e.writeObject(map);
                e.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadStateMap() {
        if (saveLoadChooser == null) {
            saveLoadChooser = new JFileChooser(".");
        }
        try {
            int returnVal = saveLoadChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File inFile = saveLoadChooser.getSelectedFile();
                XMLDecoder d = new XMLDecoder(new BufferedInputStream(
                        new FileInputStream(inFile)));
                Object result = d.readObject();
                d.close();
                if (result instanceof Map) {
                    applyStateMap((Map) result);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    String xmlEncode(Object obj) throws UnsupportedEncodingException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLEncoder e = new XMLEncoder(bos);
        e.writeObject(obj);
        e.close();
        return bos.toString("UTF8");
    }

    Object xmlDecode(InputStream stream) throws UnsupportedEncodingException {
        XMLDecoder d = new XMLDecoder(stream);
        Object result = d.readObject();
        d.close();
        return result;
    }

    public Map getDependencyDataMap(String depId) {
        try {
            URL dep_url = new URL(getCodeBase(), getParameter("dependency_url")
                    + "?depend=" + depId);
            Object result = xmlDecode(dep_url.openStream());
            System.err.println(result);
            if (result instanceof Map) {
                return (Map) result;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // SCO support functions
    // <param name="run_mode" value="sco" />
    public boolean isRunningAsSCO() {
        try {
            return "sco".equals(getParameter("run_mode"));
        } catch (Exception ex) {
            return false;
        }
    }

    public void completeSCO() {
        if (!isRunningAsSCO()) {
            return;
        }
        try {
            netscape.javascript.JSObject pageWindow = netscape.javascript.JSObject.getWindow(this);
            pageWindow.call("TrainerReportAndComplete",
                    new String[]{getReportHtml()});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private String helpUrl = "/edu/sumdu/dl/common/help/index.html";

    public void setHelpUrl(String url) {
        helpUrl = url;
    }

    public String getHelpUrl() {
        return helpUrl;
    }
}
