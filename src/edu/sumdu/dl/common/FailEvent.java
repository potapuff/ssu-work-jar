package edu.sumdu.dl.common;

public class FailEvent {

    public String message = null;
    public boolean i18n = false;
    public boolean jump = false;
    public Step stepOrigin = null;

    public FailEvent() {
    }

    public FailEvent(Step where) {
        this(where, null, false, false);
    }

    public FailEvent(Step where, String msg) {
        this(where, msg, false, false);
    }

    public FailEvent(Step where, String msg, boolean i18n, boolean needJump) {
        message = msg;
        this.i18n = i18n;
        jump = needJump;
        stepOrigin = where;
    }

    public FailEvent(Step where, String msg, boolean i18n) {
        this(where, msg, i18n, false);
    }
}
