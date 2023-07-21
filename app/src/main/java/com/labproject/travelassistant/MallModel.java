package com.labproject.travelassistant;

public class MallModel {
    String sname,loc,purl;

    MallModel(){


    }
    public MallModel(String sname, String loc, String purl) {
        this.sname = sname;
        this.loc = loc;
        this.purl = purl;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
