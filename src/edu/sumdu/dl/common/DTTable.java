package edu.sumdu.dl.common;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.awt.Container;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import java.awt.Graphics;

public class DTTable extends DTPanel {

    private GridBagLayout gridbag;
    private GridBagConstraints c;

    public DTTable() {
        super();
        gridbag = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(gridbag);
        TR = new ArrayList();
    }
    // For each component to be added to this container:
    // ...Create the component...
    // ...Set instance variables in the GridBagConstraints instance...
    // gridbag.setConstraints(theComponent, c);
    // pane.add(theComponent);
    boolean[][] gridUsed;
    boolean border = false;
    int curRow, curCol, rows = 1, cols = 1;

    void setupGrid() {
        gridUsed = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gridUsed[i][j] = false;
            }
        }
        curRow = 0;
        curCol = 0;
    }
    ArrayList TR;

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer loc, RuntimeVars pv) {
        target.add(this);
        elements.add(this);
        rows = (Tool.toInt(node.getAttr("rows")));
        cols = (Tool.toInt(node.getAttr("cols")));
        border = (Tool.toBool(node.getAttr("border")));
        setupGrid();
        XNode[] kids = node.getChildren();
        for (int i = 0; i < kids.length; i++) {
            if ("tr".equals(kids[i].getName())) {
                ArrayList CELLS = new ArrayList();
                findRow();
                XNode cells[] = kids[i].getChildren();
                for (int j = 0; j < cells.length; j++) {
                    if ("td".equals(cells[j].getName())) {
                        findCol();
                        DTCell td = new DTCell();
                        int colspan = Tool.toInt(cells[j].getAttr("colspan"));
                        int rowspan = Tool.toInt(cells[j].getAttr("rowspan"));
                        int align = getConstr(cells[j].getAttr("align")); // System.out.println("TABLE:colspan:"+colspan+":rowspan:"+rowspan);
                        if (colspan <= 0) {
                            colspan = 1;
                        }
                        if (rowspan <= 0) {
                            rowspan = 1;
                        }
                        td.setColspan(colspan);
                        td.setRowspan(rowspan);
                        td.setLayoutName(cells[j].getAttr("layout"));
                        td.setAlign(cells[j].getAttr("align"));
                        XNode content[] = cells[j].getChildren();
                        for (int k = 0; k < content.length; k++) {
                            Tool.addChilds(content[k], td, fields, elements,
                                    loc, pv);
                        }
                        CELLS.add(td);
                        placeCell(td, colspan, rowspan, align);
                    }
                }
                TR.add(CELLS);
                for (int k = 0; k < cols; k++) {
                    gridUsed[curRow][k] = true;
                }
            }
        }
        if (border) {
            setBorder(BorderFactory.createLineBorder(Color.black));
        }
        return true;
    }

    void placeCell(JComponent data, int w, int h, int al) {
        c.gridx = curCol;
        c.gridy = curRow;
        c.gridwidth = w;
        c.gridheight = h;
        // if(al==GridBagConstraints.CENTER) c.fill=GridBagConstraints.BOTH;
        // else
        c.fill = GridBagConstraints.NONE;

        c.ipadx = c.ipady = 0;
        c.insets = new java.awt.Insets(1, 1, 1, 1);
        c.weightx = c.weighty = 1.0;
        c.anchor = al;

        gridbag.setConstraints(data, c);
        // if(border)
        // data.setBorder(BorderFactory.createLineBorder(Color.black));
        add(data);
        for (int i = 0; i < h && i + curRow < rows; i++) {
            for (int j = 0; j < w && j + curCol < cols; j++) {
                gridUsed[i + curRow][j + curCol] = true;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!border) {
            return;
        }
        GridBagLayout gbl = (GridBagLayout) getLayout();
        int[][] dims = gbl.getLayoutDimensions();
        int[] colWidth = dims[0];
        int[] rowHeigh = dims[1];
        for (int i = 0; i < getComponentCount(); i++) {
            GridBagConstraints gbc = gbl.getConstraints(getComponent(i));
            int topx = 0, topy = 0, widthx = 0, widthy = 0;
            for (int k = 0; k < gbc.gridx; k++) {
                topx += colWidth[k];
            }
            for (int k = 0; k < gbc.gridy; k++) {
                topy += rowHeigh[k];
            }
            for (int k = gbc.gridx; k < gbc.gridx + gbc.gridwidth; k++) {
                widthx += colWidth[k];
            }
            for (int k = gbc.gridy; k < gbc.gridy + gbc.gridheight; k++) {
                widthy += rowHeigh[k];
            }
            g.drawRect(topx, topy, widthx, widthy);
            // System.out.println("Rect:"+topx+":"+topy+":"+widthx+"x"+widthy);
        }

    }

    public String toXML() {
        StringBuffer buf = new StringBuffer();
        buf.append("<table rows=\"" + rows + "\" cols=\"" + cols
                + "\" border=\"" + border + "\" >");
        for (int i = 0; i < TR.size(); i++) {
            buf.append("<tr>");
            ArrayList cells = (ArrayList) TR.get(i);
            for (int j = 0; j < cells.size(); j++) {
                buf.append(((DTCell) cells.get(j)).toXML());
            }
            buf.append("</tr>");
        }
        buf.append("</table>");
        return buf.toString();
    }

    void findRow() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!gridUsed[i][j]) {
                    curRow = i;
                    return;
                }
            }
        }
    }

    void findCol() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!gridUsed[i][j]) {
                    curCol = j;
                    return;
                }
            }
        }
    }

    int getConstr(String s) {
        if (s == null) {
            return GridBagConstraints.CENTER;
        }
        if (s.toUpperCase().equals("CENTER")) {
            return GridBagConstraints.CENTER;
        }
        if (s.toUpperCase().equals("NORTH")) {
            return GridBagConstraints.NORTH;
        }
        if (s.toUpperCase().equals("NORTHEAST")) {
            return GridBagConstraints.NORTHEAST;
        }
        if (s.toUpperCase().equals("EAST")) {
            return GridBagConstraints.EAST;
        }
        if (s.toUpperCase().equals("SOUTHEAST")) {
            return GridBagConstraints.SOUTHEAST;
        }
        if (s.toUpperCase().equals("SOUTH")) {
            return GridBagConstraints.SOUTH;
        }
        if (s.toUpperCase().equals("SOUTHWEST")) {
            return GridBagConstraints.SOUTHWEST;
        }
        if (s.toUpperCase().equals("WEST")) {
            return GridBagConstraints.WEST;
        }
        if (s.toUpperCase().equals("NORTHWEST")) {
            return GridBagConstraints.NORTHWEST;
        }
        return GridBagConstraints.CENTER;
    }
}
