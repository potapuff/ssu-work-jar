package edu.sumdu.dl.common;

/*
 * $Log: MetalworksHelp.java,v $
 * Revision 1.2  2003/11/06 16:20:28  nnick
 * Added buttons which can respond to user's action listeners,
 * added help in trainer
 * (help text is still missing, but the feture is)
 *
 * Revision 1.1  2003/11/06 14:38:17  nnick
 * Adding help frame
 *
 * Revision 1.2  2003/10/28 10:07:23  nnick
 * Fixed some docs and images for editor
 *
 * Well, I've borrowed this code, so what? :)
 */
/*
 * Copyright (c) 2002 Sun Microsystems, Inc. All  Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * -Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduct the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT
 * BE LIABLE FOR ANY DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT
 * OF OR RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN
 * IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended for
 * use in the design, construction, operation or maintenance of any nuclear
 * facility.
 */

/*
 * @(#)MetalworksHelp.java	1.9 02/06/13
 */
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

/*
 * @version 1.9 06/13/02
 * @author Steve Wilson
 */

public class MetalworksHelp extends JInternalFrame implements TLocalized {
    
    private HtmlPane html;

    public MetalworksHelp(Localizer localizer) {

        super("Help", true, true, true, true);
        setFrameIcon((Icon) UIManager.get("Tree.openIcon"));
        setBounds(200, 25, 400, 400);
        setTitle(localizer.getMessage("help.help.help"));
        html = new HtmlPane(localizer.getMessage(HtmlPane.indexResourceKey));
        setContentPane(html);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    @Override
    public void refreshLang(Localizer t) {
        setTitle(t.getMessage("help.help.help"));
        try {
            html.setPage(t.getMessage(HtmlPane.indexResourceKey));
        } catch (IOException ex) {
            System.err.println("Can't refresh help page");
            ex.printStackTrace();
        }
    }
}

class HtmlPane extends JScrollPane implements HyperlinkListener {

    private JEditorPane jpane;
    protected static String indexResourceKey = "help.url.key";

    public HtmlPane(String page) {
        URL url = null;
        try {

            url = getClass().getResource(page);
            System.out.println("INIT: url: " + url + "  page: " + page);
            jpane = new JEditorPane();

            //для уборки багов с версией 1.6.0_22 и выше
            jpane.setEditorKit(new HTMLEditorKit() {

                protected Parser getParser() {
                    try {
                        Class c = Class.forName("javax.swing.text.html.parser.ParserDelegator");
                        Parser defaultParser = (Parser) c.newInstance();
                        return defaultParser;
                    } catch (Throwable e) {
                    }
                    return null;
                }
            });

            jpane.setPage(url);
            jpane.setEditable(false);
            jpane.addHyperlinkListener(this);

            JViewport vp = getViewport();
            vp.add(jpane);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e + ":" + url);
        } catch (IOException e) {
            System.out.println("IOException: " + e + ":" + url);
        }
        
    }
    
    public JEditorPane getEditorPane() {
        return jpane;
    }
    
    public void setPage(String page) throws IOException {
        URL url = getClass().getResource(page);
        System.out.println("refreshLang: url: " + url + "  page: " + page);
        getEditorPane().setPage(url);
    }

    /**
     * Notification of a change relative to a hyperlink.
     */
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            linkActivated(e.getURL());
        }
    }

    /**
     * Follows the reference in an link. The given url is the requested
     * reference. By default this calls <a href="#setPage">setPage</a>, and if
     * an exception is thrown the original previous document is restored and a
     * beep sounded. If an attempt was made to follow a link, but it represented
     * a malformed url, this method will be called with a null argument.
     * 
     * @param u
     *            the URL to follow
     */
    protected void linkActivated(URL u) {
        Cursor c = jpane.getCursor();
        Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        jpane.setCursor(waitCursor);
        SwingUtilities.invokeLater(new PageLoader(u, c));
    }

    /**
     * temporary class that loads synchronously (although later than the request
     * so that a cursor change can be done).
     */
    class PageLoader implements Runnable {

        PageLoader(URL u, Cursor c) {
            url = u;
            cursor = c;
        }

        public void run() {
            if (url == null) {
                // restore the original cursor
                jpane.setCursor(cursor);

                // PENDING(prinz) remove this hack when
                // automatic validation is activated.
                Container parent = jpane.getParent();
                parent.repaint();
            } else {
                Document doc = jpane.getDocument();
                try {
                    jpane.setPage(url);
                } catch (IOException ioe) {
                    jpane.setDocument(doc);
                    getToolkit().beep();
                } finally {
                    // schedule the cursor to revert after
                    // the paint has happended.
                    url = null;
                    SwingUtilities.invokeLater(this);
                }
            }
        }
        URL url;
        Cursor cursor;
    }
}
