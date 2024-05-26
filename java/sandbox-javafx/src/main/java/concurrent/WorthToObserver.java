package concurrent;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class WorthToObserver {
    public static EventSource adapt(final Task<?> toAdapt){
        return new EventSource() {
            @Override
            public <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
                toAdapt.addEventHandler(eventType, eventHandler);
            }
        };
    }

    public static EventSource adapt(final Service<?> toAdapt){
        return new EventSource() {
            @Override
            public <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
                toAdapt.addEventHandler(eventType, eventHandler);
            }
        };
    }
}
