package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class Parameter {

    @ManagedProperty(value="#{param.token}")
    private String token = "default";

    public String getToken() {
        return token;
    }

    public void setToken(String newToken) {
        this.token = newToken;
    }
}