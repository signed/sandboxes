package view.parameter.cdi;

import javax.inject.Named;

@Named("cdi")
public class CdiParameter {
    public String content = "Initial Cdi content assigned at field declaration";

    public void setContent(String newContent) {
        this.content = newContent;
    }

    public String getContent() {
        return this.content;
    }
}
