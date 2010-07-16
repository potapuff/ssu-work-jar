package edu.sumdu.dl.common;

/*
$Log: XNParser.java,v $
Revision 1.9  2003/11/02 18:40:22  nnick
Some changes in tables,
better edit properties,CSS in docs etc

Revision 1.8  2003/10/31 07:50:05  nnick
FIxed highlight

Revision 1.7  2003/10/29 12:16:09  nnick
Fixed xml parser to work proper in applets!

Revision 1.6  2003/10/29 08:17:27  nnick
Trying to fix XML parsing when loading as applet

Revision 1.5  2003/10/29 08:12:05  nnick
Current step can also be moved to fame

Revision 1.4  2003/10/19 15:54:45  nnick
Added some log dumps

Revision 1.3  2003/10/18 16:56:55  nnick
TODO: create default properties


Mostly fixed. Dialogs and Reporter now use Trainer.dialogs to get localized messages
Now it should send a Dump to server also.
Showing different replies on number of tries is not a good idea.
Maybe count errors? If one needs help - use sendDump.
Anyway i don't send info about every part.
THERE IS NO NEED IN THAT.

 *Revision 1.2  2003/10/18 14:23:11  nnick
 *Now it gets not file but resource
 */
import edu.sumdu.dl.parser.*;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.*;

public class XNParser {

    public static String last_error = "";

    public static XNode parse(Reader r) {
        DocHandler dh = new DocHandler();
        try {

            QDParser.parse(dh, r);
            XNode n1 = new XNode(dh.getDocumentElement());
            return n1;
        } catch (Exception x) {
            System.out.println(x.getMessage());
            last_error = x.getMessage();
        }
        return null;
    }

    public static XNode parse(URL fname) {
        try {
            return parse(new InputStreamReader(fname.openStream(), "UTF8"));
        } catch (Exception e) {
            last_error = e.getMessage();
            return null;
        }
    }

    public static XNode parse(String fname) {
        try {
            return parse(new InputStreamReader(new FileInputStream(fname),
                    "UTF8"));
        } catch (Exception e) {
            last_error = e.getMessage();
            return null;
        }
    }

    public static String indent(String xmlString) {
        return dump(parseString(xmlString), 0);
    }

    public static XNode parseString(String xmlString) {
        try {
            return parse(new InputStreamReader(new ByteArrayInputStream(
                    xmlString.getBytes("UTF8")), "UTF8"));
        } catch (Exception e) {
            last_error = e.getMessage();
            return null;
        }
    }

    public static String dump(XNode node, int indent) {

        String ind = "";
        if (node == null) {
            return ind;
        }
        for (int i = 0; i < indent; i++) {
            ind += " ";
        }
        String s = "";
        s = "\n" + ind + "<" + node.getName();
        if (node.hasAttributes()) {
            for (Enumeration e = node.getAttrs(); e.hasMoreElements();) {
                String key = e.nextElement().toString();
                s += " " + key + "=\"" + Tool.escapeXML("" + node.getAttr(key))
                        + "\"";
            }
        }
        if (node.hasChildNodes()) {
            s += ">" + Tool.escapeXML(node.getContent());
            int lng = node.getChildren().length;
            for (int i = 0; i < lng; i++) {
                s += dump(node.getChild(i), indent + 2);
            }
            if (lng > 0) {
                s += "" + ind;
            }
            s += "</" + node.getName() + ">\n";
        } else {
            s += ">" + Tool.escapeXML(node.getContent()) + "</"
                    + node.getName() + ">\n";
        }
        return s;
    }
}
