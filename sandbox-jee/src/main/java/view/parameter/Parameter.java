package view.parameter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class Parameter {

    @ManagedProperty(value="#{param.token}")
    private String token = "default";
    private String tokenPostConstruct;
    private String another = "another default";
    private String anotherPostConstruct;
    private String byHand = "by hand default";
    private String byHandPostConstruct;

    @PostConstruct
    public void construct() {
        tokenPostConstruct = token;
        anotherPostConstruct = another;
        byHandPostConstruct = byHand;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String newToken) {
        this.token = newToken;
    }

    public String getTokenPostConstruct() {
        return tokenPostConstruct;
    }

    public void setAnother(String newAnother) {
        this.another = newAnother;
    }

    public String getAnother() {
        return another;
    }

    public String getAnotherPostConstruct() {
        return anotherPostConstruct;
    }

    public String getByHandPostConstruct() {
        return byHandPostConstruct;
    }

    public String getByHand() {
        return byHand;
    }

    public void setByHand(String byHand) {
        this.byHand = byHand;
    }
}