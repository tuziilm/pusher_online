package com.zhanghui.pusher.common;

public enum IconType {
    ORI(Integer.MAX_VALUE),
    M300(300);
    private int minLen;

    private IconType(int minLen){
        this.minLen = minLen;
    }

    public int getMinLen() {
        return minLen;
    }

    public void setMinLen(int minLen) {
        this.minLen = minLen;
    }
}
