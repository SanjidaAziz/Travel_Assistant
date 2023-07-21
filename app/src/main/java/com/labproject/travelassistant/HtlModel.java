package com.labproject.travelassistant;

public class HtlModel
{
     String hname,loc,rat,purl;
     HtlModel()
     {

     }
    public HtlModel(String hname, String loc, String rat,String purl) {
        this.hname = hname;
        this.loc = loc;
        this.rat = rat;
        this.purl=purl;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getRat() {
        return rat;
    }

    public void setRat(String rat) {
        this.rat = rat;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
