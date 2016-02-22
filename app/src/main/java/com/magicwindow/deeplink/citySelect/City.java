package com.magicwindow.deeplink.citySelect;

public class City {
    public String name;
    public String pinyi;

    public String postcode;

    public City(String name, String pinyi) {
        super();
        this.name = name;
        this.pinyi = pinyi;
    }
    public City(String name, String pinyi, String postcode) {
        super();
        this.name = name;
        this.pinyi = pinyi;
        this.postcode = postcode;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public City() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyi() {
        return pinyi;
    }

    public void setPinyi(String pinyi) {
        this.pinyi = pinyi;
    }

}
