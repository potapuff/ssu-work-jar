package edu.sumdu.dl.common;

/*
Rev 1.1 26.07.2009
Added support of n-dim arrays in get method
 */
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.*;

public class RuntimeVars {

    private Hashtable ht;
    private Pattern p;

    public RuntimeVars() {
        ht = new Hashtable();
    }

    public void setVar(String key, Object value) {
        ht.put(key, value);
    }

    public Object getVar(String key) {

        Object o = ht.get(key);

        if (o != null) {
            return o;
        }
        if (p == null) {
            p = Pattern.compile("^\\w+(\\[\\d+?\\])+?$");
        }
        Matcher m = p.matcher(key);
        String arrName = null;
        Object array = null;
        if (m.matches()) {// key matches as array
            arrName = key.substring(0, key.indexOf('['));
            array = ht.get(arrName);
            if (array != null) {
                int keyDim = 0, arrDim = 0;
                StringTokenizer st = new StringTokenizer(key, "[");
                keyDim = st.countTokens() - 1;
                int deeping[] = new int[keyDim];
                st.nextToken();
                int t = 0;
                while (st.hasMoreTokens()) {
                    deeping[t++] = Integer.parseInt(st.nextToken().replace(']',
                            ' ').replace('[', ' ').trim());
                }
                try {
                    for (int i = 0; i < deeping.length; i++) {
                        array = Array.get(array, deeping[i]);
                    }
                } catch (IllegalArgumentException ie) {
                    System.err.println("Error while getting a variable " + key
                            + " :" + ie.getMessage());
                    return key;
                } catch (ArrayIndexOutOfBoundsException ae) {
                    System.err.println("Error while getting a variable " + key
                            + " :" + ae.getMessage());
                    return key;
                }
                return array;
            }
        }

        System.err.println("Can't get '" + key
                + "' , giving it itself. Check your code");
        return key;
    }
}
