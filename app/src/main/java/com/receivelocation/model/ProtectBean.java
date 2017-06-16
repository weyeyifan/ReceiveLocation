package com.receivelocation.model;

/**
 * @createAuthor zfb
 * @createTime 2017/5/4${Time}
 * @describe ${TODO}
 */

public class ProtectBean {
    String earfcn;
    String pci;
    String tai;
    String cgi;
    float  sinr;
    float  rsrp;
    boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(String earfcn) {
        this.earfcn = earfcn;
    }

    public String getPci() {
        return pci;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public String getTai() {
        return tai;
    }

    public void setTai(String tai) {
        this.tai = tai;
    }

    public String getCgi() {
        return cgi;
    }

    public void setCgi(String cgi) {
        this.cgi = cgi;
    }

    public float getSinr() {
        return sinr;
    }

    public void setSinr(float sinr) {
        this.sinr = sinr;
    }

    public float getRsrp() {
        return rsrp;
    }

    public void setRsrp(float rsrp) {
        this.rsrp = rsrp;
    }
}
