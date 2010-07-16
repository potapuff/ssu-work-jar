package edu.sumdu.dl.common;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CFConclusions -- тупой компонент для ввода "выводов". Отображается как
 * TextArea.
 * 
 * Тег: conclude
 * 
 * Атрибуты: width - число столбцов height - число строк wordlen - ождаемая
 * средняя длина слова wordcount - ожидаемое число слов.
 * 
 * Проверка - если в тексте есть хотя бы wordcount слов, и в среднем их длина не
 * меньше ожидаемой (wordlen), то с некоторой натяжкой можем предположить, что
 * там написано что-то осмысленное. :) Словом считается последовательность
 * русских/украинских букв.
 */
public class CFConclusions extends JTextArea implements TStorable,
        NodeStorable, FocusListener {

    public CFConclusions() {
        super(4, 40);
        setLineWrap(true);
        addFocusListener(this);
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public String getState() {
        return "<conclude>" + Tool.escapeXML(getText()) + "</conclude>";
    }

    public void setState(XNode n) {
        setText(n.getContent());
    }
    int min_word_count = 5;

    public int getMinWordCount() {
        return min_word_count;
    }

    public void setMinWordCount(int n) {
        min_word_count = n;
    }
    int avg_word_len = 5; // expected average word length

    public int getAvgWordLen() {
        return avg_word_len;
    }

    public void setAvgWordLen(int n) {
        avg_word_len = n;
    }

    int zeroToDefault(int value, int default_value) {
        return value == 0 ? default_value : value;
    }

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv) {
        target.add(this);
        fields.add(this);
        elements.add(this);
        setColumns(zeroToDefault(Tool.toInt(node.getAttr("width")), 4));
        setRows(zeroToDefault(Tool.toInt(node.getAttr("height")), 40));
        setAvgWordLen(zeroToDefault(Tool.toInt(node.getAttr("wordlen")), 5));
        setMinWordCount(zeroToDefault(Tool.toInt(node.getAttr("wordcount")), 10));
        return true;
    }

    public String toXML() {
        return "<conclude width=\"" + getColumns() + "\" height=\"" + getRows()
                + "\" wordlen=\"" + getAvgWordLen() + "\" wordcount=\""
                + getMinWordCount() + "\"/>";
    }

    public boolean isCorrect() {
        Pattern p = Pattern.compile("([а-яА-ЯёїіЇІЁҐґ]+)");
        Matcher m = p.matcher(getText());
        int word_count = 0, word_total_len = 0;
        while (m.find()) {
            word_count += 1;
            word_total_len += m.group(1).length();
        }
        return word_count >= getMinWordCount()
                && (word_total_len * 1.0 / word_count) >= getAvgWordLen();
    }

    public void focusLost(FocusEvent e) {
        if (isCorrect()) {
            this.setForeground(Color.black);
        } else {
            this.setForeground(Color.red);
        }
    }

    public void focusGained(FocusEvent e) {
        this.setForeground(Color.black);
    }
}
