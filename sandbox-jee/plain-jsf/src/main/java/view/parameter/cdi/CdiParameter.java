package view.parameter.cdi;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

@Named("cdi")
public class CdiParameter {

    @Inject
    private Logger logger;

    @Inject
    @HttpParam("token")
    private String token = "Initial Cdi token assigned at field declaration";
    private String tokenPostConstruct;

    @Inject
    @HttpParam("another")
    private String bogus = "Initial value assigned to bogus at field declaration";
    private String bogusPostConstruct;

    @PostConstruct
    private void postConstruct() {
        tokenPostConstruct = token;
        bogusPostConstruct = bogus;
    }

    public void setToken(String newContent) {
        logger.severe("setToken( "+newContent+" )");
        this.token = newContent;
    }

    public String getToken() {
        logger.severe("getToken()");
        return this.token;
    }

    public String getTokenPostConstruct() {
        return tokenPostConstruct;
    }

    public void setTokenPostConstruct(String tokenPostConstruct) {
        this.tokenPostConstruct = tokenPostConstruct;
    }

    public String getBogus() {
        return bogus;
    }

    public void setBogus(String bogus) {
        this.bogus = bogus;
    }

    public String getBogusPostConstruct() {
        return bogusPostConstruct;
    }

    public void setBogusPostConstruct(String bogusPostConstruct) {
        this.bogusPostConstruct = bogusPostConstruct;
    }
}