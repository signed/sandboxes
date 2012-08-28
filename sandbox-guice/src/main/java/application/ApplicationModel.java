package application;

import lang.Announcer;
import lang.Closure;

import java.util.Objects;

public class ApplicationModel {
    private final Announcer<Closure> changeListener = Announcer.to(Closure.class);
    private String message;

    public String message() {
        return message;
    }

    public void message(String message) {
        if (Objects.equals(this.message, message)) return;
        this.message = message;
        changeListener.announce().execute();
    }

    public void onChange(Closure closure) {
        changeListener.addListener(closure);
    }
}
