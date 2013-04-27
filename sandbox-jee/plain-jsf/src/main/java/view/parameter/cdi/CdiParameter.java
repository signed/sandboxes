package view.parameter.cdi;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

@Named("cdi")
public class CdiParameter {

    @Inject
    private Logger logger;

    @Inject
    @HttpParam("token")
    public String token = "Initial Cdi token assigned at field declaration";

    public void setToken(String newContent) {
        logger.severe("setToken( "+newContent+" )");
        this.token = newContent;
    }

    public String getToken() {
        logger.severe("getToken()");
        return this.token;
    }
}
