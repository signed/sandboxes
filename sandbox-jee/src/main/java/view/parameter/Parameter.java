package view.parameter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class Parameter {

    @ManagedProperty(value="#{param.token}")
    private String token = "default";
    private String tokenPostConstruct;

    @PostConstruct
    public void construct() {
        tokenPostConstruct = token;
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
}