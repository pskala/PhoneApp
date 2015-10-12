package com.ixperta.phoneapp.domain;

public class Station {

    private String id;
    private String number;
    private String redirectNumber;
    private String ownerID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRedirectNumber() {
        return redirectNumber;
    }

    public void setRedirectNumber(String redirectNumber) {
        this.redirectNumber = redirectNumber;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
}
