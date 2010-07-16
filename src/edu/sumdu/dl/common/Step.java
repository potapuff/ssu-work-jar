package edu.sumdu.dl.common;

// вБЪПЧЩК ЛМБУУ ДМС ЫБЗБ ТЕЫЕОЙС
// 23-09-2003 9:50 nnick
/* $Log: Step.java,v $
/* Revision 1.16  2003/12/14 16:57:47  nnick
/* Fixed steps title change after lang change
/*
/* Revision 1.15  2003/11/02 11:38:42  nnick
/* Fixed some bugs and more docs
/*
/* Revision 1.14  2003/10/29 17:26:06  nnick
/* Fixed bug in send dump
/*
/* Revision 1.13  2003/10/29 16:26:25  nnick
/* one more typo
/*
/* Revision 1.11  2003/10/29 16:24:01  nnick
/* added alert listener to call from steps
/*
/* Revision 1.10  2003/10/29 15:33:52  nnick
/* Typos fixed
/*
/* Revision 1.9  2003/10/29 15:30:38  nnick
/* added FailEvent and FailListener
/*
/* Revision 1.8  2003/10/29 08:12:05  nnick
/* Current step can also be moved to fame
/*
/* Revision 1.7  2003/10/27 19:45:11  nnick
/* Added some comments
/*
/* Revision 1.5  2003/10/23 08:29:17  nnick
/* Changed access to fields
/*
/* Revision 1.4  2003/10/21 15:02:13  nnick
/* Some fixes due to user-defined steps
/*
/* Revision 1.3  2003/10/19 15:54:45  nnick
/* Added some log dumps
/*
/* Revision 1.2  2003/10/19 14:43:19  nnick
/* DTUserStep is just a visula binding.
/* The goal is to use user-defined steps as basic
/* */
import java.util.ArrayList;
import javax.swing.JPanel;

public class Step implements TStorable {

    /** Панель этого шага */
    public DTStepPanel content;
    /** Список самопроверяемых объектов на панели этого шага */
    private ArrayList fields;
    private int fail_count = 0;
    private boolean stepDone = false;
    private int checkedFieldsAmount = -1;
    private ImportanceLevel importanceLevel = ImportanceLevel.MEDIUM_IMPORTANCE;

    public Step() {
        fields = new ArrayList();
        elements = new ArrayList();
    }

    public double getDoneRatio() {
        int t1 = getDoneFieldsAmount();
        int t2 = getCheckedFieldsAmount();
        if (t2 == 0) {
            return 1.0;
        }
        return (double) t1 / t2;
    }

    public CheckField getFieldAt(int i) {
        Object o = fields.get(i);
        if (o instanceof CheckField) {
            return (CheckField) o;
        } else {
            return null;
        }
    }

    public ArrayList getFields() {
        return fields;
    }

    public int getFieldsCount() {
        return fields.size();
    }

    public int getDoneFieldsAmount() {
        if (stepDone && checkedFieldsAmount > 0) {
            return checkedFieldsAmount;
        }
        int count = 0;
        checkedFieldsAmount = 0;
        for (Object o : fields) {
            if (o instanceof CheckField) {
                checkedFieldsAmount += 1;
                if (((CheckField) o).isCorrect()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getFieldsAmount() {
        return fields.size();
    }

    public int getCheckedFieldsAmount() {
        int count = 0;
        if (checkedFieldsAmount < 0) {
            for (Object o : fields) {
                if ((o instanceof CheckField)) {
                    count++;
                }
            }
            checkedFieldsAmount = count;
        }
        return checkedFieldsAmount;
    }

    /** Проверка шага на правильноть для перехода к следующему */
    public boolean isDone() {
        if (stepDone) {
            return true;
        }
        boolean allOK = true;
        for (Object o : fields) {
            if (o instanceof CheckField) {
                allOK = allOK && ((CheckField) o).isCorrect();
            }
        }
        if (!allOK) {
            fireFailEvent();
        } else {
            stepDone = true;
        }
        return allOK;
    }

    public ImportanceLevel getImportanceLevel() {
        return importanceLevel;
    }

    public void setImportanceLevel(ImportanceLevel importanceLevel) {
        this.importanceLevel = importanceLevel;
    }

    /** Создание информации (XML) для сохранения состояния */
    public String getState() {
        String out = "<stepstate><name>" + getTitle() + "</name><fields>";
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i) instanceof TStorable) {
                out += ((TStorable) fields.get(i)).getState();
            }
        }
        out += "</fields></stepstate>";
        return out;
    }

    /** Восстановление из XML сохраненного состояния */
    public void setState(XNode node) {
        try {
            XNode childs[] = node.getChildren()[1].getChildren();
            for (int i = 0; i < childs.length; i++) {
                ((TStorable) fields.get(i)).setState(childs[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ArrayList elements;

    public Object getElementAt(int i) {
        return elements.get(i);
    }

    /** Список объектов на панели этого шага */
    public Object[] getElements() {
        return elements.toArray();
    }

    public ArrayList getElementList() {
        return elements;
    }

    /** Обговление языка "на лету" */
    public void refreshLanguage(Localizer l) {
        for (int i = 0; i < elements.size(); i++) {
            Object o = elements.get(i);
            if (o instanceof TLocalized) {
                ((TLocalized) o).refreshLang(l);
            }
        }
        content.refreshLang(l);

    }

    /** Создание дизайна из XML */
    public void readDesign(XNode node, Localizer loc, RuntimeVars pv) {
        JPanel p1 = new JPanel();
        Tool.addChilds(node, p1, fields, elements, loc, pv);
        content = (DTStepPanel) p1.getComponent(0);
    }

    /** Ф-и для отображения на закладках */
    public DTStepPanel getContent() {
        return content;
    }

    public String getTitle() {
        return content.getTitle();
    }

    public void Draw(java.awt.Container target) {
        target.add(content);
    }
    /** Обработка неправильных ответов */
    private FailListener failListener = null;

    public int getFailCount() {
        return fail_count;
    }

    public void incFailCount() {
        fail_count++;
    }

    public void resetFailCount() {
        fail_count = 0;
    }

    public void setFailListener(FailListener fl) {
        failListener = fl;
    }

    public void fireFailEvent() {
        fireFailEvent(new FailEvent(this));
    }

    public void fireFailEvent(FailEvent fe) {
        incFailCount();
        if (failListener != null) {
            failListener.checkFailed(fe);
        }
    }

    public void updateElements() {
        for (Object o : elements) {
            if (o instanceof Updateable) {
                ((Updateable) o).updateValuesToCheck();
            }
        }
    }
    private AlertListener alertListener = null;

    public void setAlertListener(AlertListener fl) {
        alertListener = fl;
    }

    public void Alert(String str, boolean f) {
        if (alertListener != null) {
            alertListener.doAlert(str, f);
        } else {
            javax.swing.JOptionPane.showInternalMessageDialog(getContent(),
                    new javax.swing.JLabel(str));
        }
    }

    public void Alert(String str) {
        Alert(str, false);
    }

    @Override
    public String toString() {
        return "Step [checkedFieldsAmount=" + checkedFieldsAmount
                + ", importanceLevel=" + importanceLevel.getLevel() + ", stepDone="
                + stepDone + "]";
    }
}
