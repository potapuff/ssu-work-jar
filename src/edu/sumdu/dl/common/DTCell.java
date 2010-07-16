package edu.sumdu.dl.common;

public class DTCell extends DTPanel {

    public DTCell() {
        super();
    }
    int rows, cols;

    public void setColspan(int n) {
        cols = n;
    }

    public void setRowspan(int n) {
        rows = n;
    }
    String align = null;

    public void setAlign(String s) {
        align = s;
    }

    public String toXML() {
        String rowStr = "", colStr = "", alStr = "";
        String ln = "";
        if (!"".equals(getLayoutName())) { // говнокод
            ln = " layout=\"" + getLayoutName() + "\"";
        }
        if (rows > 1) {
            rowStr = " rowspan=\"" + rows + "\" ";
        }
        if (cols > 1) {
            colStr = " colspan=\"" + cols + "\" ";
        }
        if (align != null) {
            alStr = " align=\"" + Tool.escapeXML(align) + "\"";
        }
        // System.out.println("colspan:"+cols+":rowspan:"+rows);

        return "<td" + colStr + rowStr + alStr + ln + ">" + super.dump()
                + "</td>";
    }
}
