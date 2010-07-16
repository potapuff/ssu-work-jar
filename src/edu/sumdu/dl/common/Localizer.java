package edu.sumdu.dl.common;

/*$Log: Localizer.java,v $
Revision 1.8.1 2009/07/15
Added Localizer Constructor with language as parameter

/*Revision 1.8  2005/07/25 12:39:35  nnick
/*Fixed langSwitch and added Localizer.getCurrentLanguage()
/*
/*Revision 1.7  2003/11/06 14:38:17  nnick
/*Adding help frame
/*
/*Revision 1.6  2003/11/06 14:12:03  nnick
/*Fixed checks in formulas,
/*added alive lang change
/*Help file is pending
/*
/*Revision 1.5  2003/11/01 11:43:17  nnick
/*Some fixes to use javac from editor
/*
/*Revision 1.4  2003/10/28 12:05:36  nnick
/*fixed bug in loading common props, added from/to fraem feature
/*
/*Revision 1.3  2003/10/20 08:18:41  nnick
/*Fixed bug in DTUserStepm - didn't add itself to target. Localizer attempta again
/*to load props from file -- needed by TaskEditor
/*
/*Revision 1.2  2003/10/18 14:17:32  nnick
/*Now to use Dialogs one should create instance and initBunlde(Localizer).
/*Localizer now uses default commonXXXX.properties, loaded at creation
/**/
import java.util.*;
import java.io.*;

public class Localizer {

    public String getMessage(String S) {
        if (messages == null && props != null) {
            try { // File loaded properties
                String ss = props.getProperty(S);
                if (ss == null) {
                    try { // Default properties
                        String res = messageDefault.getString(S);
                        return res;
                    } catch (Exception e2) {
                        System.out.println("GETMESSAGE:" + S
                                + ":Not found in default!");
                        return S;
                    }
                } else {
                    return ss;
                }
            } catch (Exception e) {
                System.out.println("GetMassage:" + S
                        + ":Not found in properties file!");
                return S;
            }
        } else {
            try { // User properties
                return messages.getString(S);
            } catch (Exception e1) {
                // System.out.println("GETMESSAGE:"+S+":Not found in bundle!");
                try { // Default properties
                    String res = messageDefault.getString(S);
                    return res;
                } catch (Exception e2) {
                    // System.out.println("GETMESSAGE:"+S+":Not found in default!");
                }
                return S;
            }
        }

    }
    private ResourceBundle messageDefault;

    public Localizer() /** Загрузка ресурсов по умолчанию commonXXXX.properties */
    {
        Locale l = new Locale("");
        Locale currentLocale = l.getDefault();
        try {
            messageDefault = ResourceBundle.getBundle(
                    "edu/sumdu/dl/common/common", currentLocale);
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
    }

    public Localizer(String lang) {
        this();
        language = lang;
    }
    private Locale currentLocale;
    private ResourceBundle messages;
    private String language = "ru_RU";
    private Properties props = null;

    /** Загрузка таблицы локализации по имени класса */
    public void loadMessages(Object o) {
        currentLocale = new Locale(language);
        // System.err.println(currentLocale.toString());
        try {
            messages = ResourceBundle.getBundle(o.getClass().getName() + "_"
                    + language);
            props = null;
        } catch (Exception e) {
            e.printStackTrace();
            loadMessages(o.getClass().getName() + "_ru_RU.properties");
        }
        try {
            messageDefault = ResourceBundle.getBundle("edu/sumdu/dl/common/common_" + language);// currentLocale);
            // System.out.println(messageDefault.toString());
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }

    }

    public void loadMessages(String nam) {
        try {
            props = new Properties();
            String s = System.getProperty("TEDworkCP");
            if (s == null) {
                s = "";
            }
            props.load(new FileInputStream(s + nam));
            messages = null;
            // System.out.println("Load messages from file:"+props);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * /** уНЕОЙФШ СЪЩЛ УППВЭЕОЙК
     */
    public void changeLanguage(Object o, String newlang) {
        language = newlang;
        loadMessages(o);
    }

    public String getCurrentLanguage() {
        return language;
    }
}
