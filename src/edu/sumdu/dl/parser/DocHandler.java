package edu.sumdu.dl.parser;

import java.util.*;

public class DocHandler {

    private QDNode root, curNode;

    public void startElement(String tag, Hashtable h) {
        // System.out.println("Start:"+tag+":"+h);

        QDNode node = new QDNode(tag, h, curNode);
        curNode.addChild(node);
        curNode = node;
    }

    public void endElement(String tag) {
        // System.out.println("End:"+tag);
        curNode.normalize();
        curNode = curNode.getParent();

    }

    public void startDocument() {
        root = new QDNode();
        curNode = root;
    }

    public void endDocument() {
    }

    public void text(String str) {
        // System.out.println("TEXT:"+str);
        curNode.appendContent(str);
    }

    public QDNode getRoot() {
        return root;
    }

    public QDNode getDocumentElement() {
        return root.getChildren()[0];
    }
}
