package com.mbcoder.officeeyes.model;

public class BitBarResponse {

    private boolean isFree;

    public BitBarResponse() {
    }

    public BitBarResponse(boolean isFree) {
        this.isFree = isFree;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
