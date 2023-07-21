package com.labproject.travelassistant;

public class SpotModel {
    String tname,loc,purl;

    SpotModel(){


    }
    public SpotModel(String tname, String loc, String purl) {
        this.tname = tname;
        this.loc = loc;
        this.purl = purl;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
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
