package com.github.signed.maven;

import javax.inject.Named;
import java.net.URL;

@Named("integrationtest")
public class ReadProperties {

    private String available = "catch me if you can";

    public String getAvailable(){
        URL configuration = ReadProperties.class.getResource("/configuration.properties");
        if(configuration == null){
            return "not available";
        }else {
            return configuration.toExternalForm();
        }
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
