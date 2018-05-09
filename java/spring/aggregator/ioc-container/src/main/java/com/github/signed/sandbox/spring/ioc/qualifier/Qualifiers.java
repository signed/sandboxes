package com.github.signed.sandbox.spring.ioc.qualifier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Qualifiers {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(Qualifiers.class);
    }

    @Autowired
    @Platform(Platform.OperatingSystems.ANDROID)
    private MarketPlace android;

    @Autowired
    @Platform(Platform.OperatingSystems.IOS)
    private MarketPlace ios;

    @PostConstruct
    public void qualifyTheTweets() {
        System.out.println("ios:" + this.ios);
        System.out.println("android:" + this.android);
    }
}



