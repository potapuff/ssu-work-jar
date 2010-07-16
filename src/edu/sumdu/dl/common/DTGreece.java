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
        
        gsS = s;
        if (s == null || s.length() == 0) {
            super.setText(s);
            return;
        }

        String s1, s2 = null;
        Pattern p = Pattern.compile("([A-Za-z]+)(\\d+)");
        Matcher m = p.matcher(s);
        if (m.find()) {
            s1 = m.group(1);
            s2 = m.group(2);
        } else {
            s1 = s;
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
            super.setText("<html>" + c + "<sub>" + s2 + "</sub></html>");
        } else {
            super.setText("" + c);
        }

        setFont(new Font("default", Font.BOLD, 14));
    }

    public String toXML() {
        return "<greece>" + gsS + "</greece>";
    }
}
