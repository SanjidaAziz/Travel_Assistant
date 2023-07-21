package com.labproject.travelassistant;

public class ResModel {
    String rname,rtype,location,rating,purl;

    ResModel(){


    }

    public ResModel(String rname, String rtype, String location, String rating, String purl) {
        this.rname = rname;
        this.rtype = rtype;
        this.location = location;

        this.rating = rating;
        this.purl = purl;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
