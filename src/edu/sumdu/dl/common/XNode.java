package edu.sumdu.dl.common;

import java.util.*;
import edu.sumdu.dl.parser.QDNode;

public class XNode {

    private QDNode qdNode;
    private ArrayList children;

    public XNode() {
        qdNode = new QDNode();
        children = new ArrayList();
    }

    public XNode(QDNode n) {
        this();
        qdNode = n;
        QDNode[] list = qdNode.getChildren();
        for (int i = 0; i < list.length; i++) {
            addChild(list[i]);
        }
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
        children.add(new XNode(node));
    }

    public XNode[] getChildren() {
        if (children.size() == 0) {
            return new XNode[0];
        }
        XNode[] ar = new XNode[children.size()];
        for (int i = 0; i < children.size(); i++) {
            ar[i] = (XNode) children.get(i);
        }
        return ar;
    }

    public XNode getChild(int i) {
        return (XNode) children.get(i);
    }

    public Enumeration getAttrs() {
        return qdNode.getAttributes().keys();
    }

    public String getContent() {
        return qdNode.getContent();
    }

    public String getAttr(String s) {
        return qdNode.getAttr(s);
    }

    public String getName() {
        return qdNode.getName();
    }

    public boolean hasAttributes() {
        return qdNode.getAttributes().size() > 0;
    }

    public boolean hasChildNodes() {
        return children.size() > 0;
    }

    public String toString() {
        return XNParser.dump(this, 0);
    }
}
