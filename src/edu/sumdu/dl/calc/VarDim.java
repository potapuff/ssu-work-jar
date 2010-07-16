package edu.sumdu.dl.calc;

/*DimensionPattern:
L - Length
K - Temperature
W - Weight
T - Time
P - Pressure
class DimensionPattern {
private String store[][];
DimensionPattern(String Pat[][]){
store=new String[Pat.length][2];
for(int i=0;i<Pat.length;i++){
store[i][0]=new String(Pat[i][0]);
store[i][1]=new String(Pat[i][1]);
}
}
}*/
import java.util.*;
import java.util.regex.*;

public class VarDim extends Hashtable {

    VarDim(String Spl) {
        super();
        Pattern p = Pattern.compile("(\\w)(-?\\d+)");
        Matcher m = p.matcher(Spl);
        while (m.find()) {
            this.put(m.group(1), new Integer(m.group(2)));
        }
    }

    public boolean equals(VarDim dm) {
        if (dm.size() != this.size()) {
            return false;
        }
        for (Enumeration e = dm.keys(); e.hasMoreElements();) {
            Object key = e.nextElement();
            Object val1 = this.get(key);
            if (val1 == null) {
                return false;
            }
            Object val2 = dm.get(key);
            if (val1 instanceof Integer && val2 instanceof Integer
                    && val1.equals(val2)); else {
                return false;
            }
        }
        return true;
    }

    public void mult(VarDim dm) {
        sumv(dm, 1);
    }

    public void divide(VarDim dm) {
        sumv(dm, -1);
    }

    void sumv(VarDim dm, int flag) {
        for (Enumeration e = dm.keys(); e.hasMoreElements();) {
            Object key = e.nextElement();
            Object val1 = this.get(key);
            Object val2 = dm.get(key);
            if (val1 == null) {
                if (val2 instanceof Integer) {
                    this.put(key, new Integer(((Integer) val2).intValue()
                            * flag));
                } else {
                    this.put(key, val2);
                }
            } else if (val1 instanceof Integer && val2 instanceof Integer) {
                Integer vl1 = (Integer) val1, vl2 = (Integer) val2;
                int i1 = vl1.intValue() + flag * vl2.intValue();
                this.put(key, new Integer(i1));
            }
        }
    }

    public void power(float powr) {
        for (Enumeration e = this.keys(); e.hasMoreElements();) {
            Object key = e.nextElement();
            Object val1 = this.get(key);
            if (val1 instanceof Integer) {
                Integer vl1 = (Integer) val1;
                int i1 = (int) (vl1.intValue() * powr);
                this.put(key, new Integer(i1));
            }
        }
    }
}
