package view.parameter.cdi;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

@Named("cdi")
public class CdiParameter {

    @Inject
    private Logger logger;

    public String content = "Initial Cdi content assigned at field declaration";

    public void setContent(String newContent) {
        logger.severe("setContent( "+newContent+" )");
        this.content = newContent;
    }

    public String getContent() {
        logger.severe("getContent()");
        return this.content;
    }
}
