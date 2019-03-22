package restore;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class RestoreBean implements Serializable{
    private String value = "initial";

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}