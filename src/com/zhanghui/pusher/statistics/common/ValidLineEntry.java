package com.zhanghui.pusher.statistics.common;

public abstract class ValidLineEntry {
    public LineStat stat=LineStat.VALID;

    public boolean isValid(){
        return stat == LineStat.VALID;
    }

    public boolean isNotSupport(){
        return stat == LineStat.NOT_SUPPORT;
    }

    public boolean isInvalid(){
        return stat == LineStat.INVALID;
    }

    public void setValid(){
        stat = LineStat.VALID;
    }

    public void setInvalid(){
        stat = LineStat.INVALID;
    }

    public void setNotSupport(){
        stat = LineStat.NOT_SUPPORT;
    }

    public static enum LineStat {
        VALID,
        INVALID,
        NOT_SUPPORT
    }
}

