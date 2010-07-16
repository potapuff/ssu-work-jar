package edu.sumdu.dl.common;

import java.util.ArrayList;
import java.awt.Container;

public interface NodeStorable {

    public boolean fromXNode(XNode node, Container target, ArrayList fields,
            ArrayList elements, Localizer l, RuntimeVars pv);

    public String toXML();
}
