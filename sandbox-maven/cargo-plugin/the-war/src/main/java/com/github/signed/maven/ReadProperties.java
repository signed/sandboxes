package com.github.signed.maven;

import javax.inject.Named;

@Named("integrationtest")
public class ReadProperties {

    private String available = "catch me if you can";

    public String getAvailable(){
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
