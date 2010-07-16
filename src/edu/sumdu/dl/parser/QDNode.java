package edu.sumdu.dl.parser;

// class to store XML node -- just a more simplified API than ordinary SAX/DOM
import java.util.*;

public class QDNode {

    private ArrayList children;
    private String name;
    private String content;
    private Hashtable attributes;
    private QDNode parent;

    public QDNode() {
        children = new ArrayList();
        attributes = new Hashtable();
        content = "";
    }

    public QDNode(String nm, Hashtable ht, QDNode par) {
        this();
        name = nm;
        attributes = ht;
        parent = par;
    }

    public QDNode getParent() {
        return parent;
    }

    public static String ridSpace(String s) {
        String t = s.replaceAll("\\s+", " ");
        if (t.equals(" ")) {
            return "";
        } else {
            return t;
        }
    }

    public void addChild(QDNode node) {
        children.add(node);
    }

    public QDNode[] getChildren() {
        if (children.size() == 0) {
            return new QDNode[0];
        }
        QDNode[] ar = new QDNode[children.size()];
        for (int i = 0; i < children.size(); i++) {
            ar[i] = (QDNode) children.get(i);
        }
        return ar;
    }

    public String getContent() {
        return content;
    }

    public void appendContent(String s) {
        content += s;
    }

    public String getAttr(String s) {
        Object o = attributes.get(s);
        if (o == null) {
            return null;
        } else {
            return o.toString();
        }
    }

    public String getName() {
        return new String(name);
    }

    public void setName(String s) {
        name = s;
    }

    public void normalize() {
        content = ridSpace(content);
    }

    public Hashtable getAttributes() {
        return attributes;
    }
}
