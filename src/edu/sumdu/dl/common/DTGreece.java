package edu.sumdu.dl.common;

import java.awt.Font;
import java.util.regex.*;

public class DTGreece extends DTLabel {

    public DTGreece() {
        super();
    }

    public DTGreece(String s) {
        super();
        setText(s);
    }
    String gsS;

    public void setText(String s) {

        String t = this.content;

        gsS = t;
        if (t == null || t.length() == 0) {
            super.setText(t);
            return;
        }

        String s1, s2 = null;
        Pattern p = Pattern.compile("([A-Za-z]+)(\\d+)");
        Matcher m = p.matcher(t);
        if (m.find()) {
            s1 = m.group(1);
            s2 = m.group(2);
        } else {
            s1 = t;
        }

        String[] lets = {
            "alpha", "beta", "gamma", "delta", "eps", "zeta", "eta", "theta",
            "i", "kappa", "lambda", "mu", "nu", "ksi", "o", "pi", "ro", "zu",
            "sigma", "tau", "ips", "phi", "chi", "psi", "omega"
        };

        int k = 945;
        char c = s1.charAt(0);
        boolean bln = s1.equals(s1.toUpperCase());

        if (bln) {
            k -= 32;
        }

        for (int i = 0; i < lets.length; i++) {
            if (lets[i].equals(s1.toLowerCase())) {
                c = (char) (k + i);
            }
        }

        if (s2 != null) {
            super.setText("<html><p style=\"" + style + "\">" + c + "<sub>" + s2 + "</sub></p></html>");
        } else {
            super.setText("<html><p style=\"" + style + "\">" + c + "</p></html>");
        }

        setFont(new Font("default", Font.BOLD, 14));
    }

    public String toXML() {
        return "<greece style=\"" + style + "\">" + gsS + "</greece>";
    }
}
