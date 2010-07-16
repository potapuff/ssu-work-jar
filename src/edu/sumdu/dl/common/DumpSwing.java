/*
$Log: DumpSwing.java,v $
Revision 1.3  2004/09/08 16:11:32  nnick
Added:
Trainer.setNeedL10N(boolean) - that sets visibility of change language button
Trainer, DumpSwing - removed chunk of legacy code, as idea changed

Revision 1.2  2003/11/12 08:33:10  nnick
Added ignorecase option in askformula

Revision 1.1.1.1  2003/10/18 13:08:21  nnick
Common classes for trainer

Revision 1.2  2003/10/12 14:38:53  nnick
dialogs, reporter and DumpSwing are now in packages

Revision 1.6  2003/10/11 12:21:14  nnick
GridBag layout now also dumps better!
TODO: colors and borders ? :)

Revision 1.5  2003/10/11 11:09:19  nnick
Unicode chars
TODO: GridBag dump

Revision 1.4  2003/10/11 06:21:54  nnick
Added interface ISelfDumped to dump formulas also

Revision 1.3  2003/10/10 08:34:17  nnick
Added some comments for javadoc

Revision 1.2  2003/10/09 16:26:22  sezal
Some changes in Dialogs.ShowReply, Dialogs.msg, adding Dialogs.initBundle

 */
package edu.sumdu.dl.common;

import javax.swing.*;

/**
 * Дамп Swing компонентов в виде HTML
 */
public abstract class DumpSwing {

    static java.util.ArrayList images = new java.util.ArrayList();
    static int imagecount = 0;

    public static String dump(JComponent co) {
        return dump(co, 0);
    }

    public static String dump(JComponent[] co) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < co.length; i++) {
            buf.append("<hr>" + dump(co[i], 0));
        }
        return buf.toString();
    }

    public static void clearImageList() {
        images = new java.util.ArrayList();
        imagecount = 0;
    }

    public static String dump(JComponent co, int indent) {
        StringBuffer buf = new StringBuffer();
        if (co == null) {
            return "";
        }
        byte[] img = com.keypoint.PngEncoderB.dumpComponentImage(co);
        images.add(img);
        return "<img src=\"" + images.size() + ".png\" />";
    }
}
