package form;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("formData")
@SessionScoped
public class FormBean implements Serializable {

    private String name;
    private String secret;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String newSecret) {
        this.secret = newSecret;
    }
}