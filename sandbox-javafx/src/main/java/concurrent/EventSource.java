package concurrent;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public interface EventSource {
    <T extends Event> void addEventHandler(final EventType<T> eventType, final EventHandler<? super T> eventHandler);
}
