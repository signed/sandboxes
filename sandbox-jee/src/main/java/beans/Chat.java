package beans;

import org.icefaces.application.PushRenderer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named
public class Chat {
    private String message;
    private List<String> messages = new ArrayList<String>();

    public void addMessage() {
        messages.add(message);
        message = "";
        PushRenderer.render("messages");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMessages() {
        PushRenderer.addCurrentSession("messages");
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
