package edu.sumdu.dl.common;

/*
$Log: Reporter.java,v $
Revision 1.5  2004/09/08 16:11:32  nnick
Added:
Trainer.setNeedL10N(boolean) - that sets visibility of change language button
Trainer, DumpSwing - removed chunk of legacy code, as idea changed

Revision 1.4  2003/11/12 08:33:10  nnick
Added ignorecase option in askformula

Revision 1.3  2003/10/18 16:56:55  nnick
TODO: create default properties


Mostly fixed. Dialogs and Reporter now use Trainer.dialogs to get localized messages
Now it should send a Dump to server also.
Showing different replies on number of tries is not a good idea.
Maybe count errors? If one needs help - use sendDump.
Anyway i don't send info about every part.
THERE IS NO NEED IN THAT.

Revision 1.2  2003/10/18 14:58:37  nnick
Removed report function, as it is defined in Trainer

Revision 1.1.1.1  2003/10/18 13:08:21  nnick
Common classes for trainer

Revision 1.3  2003/10/12 15:18:37  nnick
Fixed bug in packege name for dialogs call in reporter

Revision 1.2  2003/10/12 14:38:53  nnick
dialogs, reporter and DumpSwing are now in packages

Revision 1.6  2003/10/11 12:21:14  nnick
GridBag layout now also dumps better!
TODO: colors and borders ? :)

Revision 1.5  2003/10/10 11:36:20  nnick
Fixed bug Cp1251 ! Fucking JRE 1.4.0 :-(

Revision 1.4  2003/10/10 08:34:17  nnick
Added some comments for javadoc

Revision 1.3  2003/10/09 16:26:22  sezal
Some changes in Dialogs.ShowReply, Dialogs.msg, adding Dialogs.initBundle

 */
import java.net.*;
import java.io.*;
import java.util.zip.*;

/**
 * Отправка отчетов на сервер
 */
public class Reporter {

    final static int BUF_SIZE = 40000;

    /**
     * Отправка дампа текущего сотояния решения задачи на сервер, просьба
     * студента о помощи
     * 
     * @param cont
     *            массив компонентов, внешний вид которых следует передать
     * @param app_id
     *            ID аплета, например 'sdnf'
     * @param subj
     *            пояснение студента о возникшей проблеме
     * @param uri
     *            URL скрипта, принимающего запросы о помощи
     */
    public static void DumpState(Trainer applet, String app_id, String subj,
            String uri) throws ReportException {
        System.out.println("Sending to:" + uri);
        try {
            URL url3 = new URL(applet.getCodeBase(), uri);
            URLConnection connection = url3.openConnection();
            connection.setDoOutput(true);
            OutputStream out = connection.getOutputStream();
            byte buf[] = new byte[BUF_SIZE];
            int k;
            try {
                String s = "id=" + app_id + "&subj="
                        + URLEncoder.encode(subj, "Cp1251") + "&text="
                        + URLEncoder.encode(applet.getDump(), "Cp1251");
                StringBuffer bf = new StringBuffer(s);
                for (int i = 0; i < DumpSwing.images.size(); i++) {
                    bf.append("&img" + i + "=");
                    bf.append(URLEncoder.encode(new String(Base64.encode((byte[]) DumpSwing.images.get(i))),
                            "Cp1251"));
                }
                ByteArrayInputStream in = new ByteArrayInputStream(bf.toString().getBytes("Cp1251"));
                GZIPOutputStream zip = new GZIPOutputStream(out);
                while ((k = in.read(buf, 0, BUF_SIZE)) > 0) {
                    zip.write(buf, 0, k);
                }
                zip.finish();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ":"
                        + e.getMessage());
            }
            out.close();

            InputStream in = connection.getInputStream();
            String s = "";
            while ((k = in.read(buf)) > 0) {
                s += new String(buf, 0, k);
            }
            DumpSwing.clearImageList();
            // System.out.println(s);
            applet.dialogs.MessageDialog(applet.dialogs.msg("help.ok"));

        } catch (Exception e2) {
            System.out.println("Error");
            throw new ReportException(applet.dialogs.msg("report.error"));
        }

    }
}

class ReportException extends Exception {

    public ReportException() {
        super();
    }

    public ReportException(String s) {
        super(s);
    }
}
