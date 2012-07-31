package concurrent;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class BackgroundExecution implements EventSource{
    private final EventSource eventSource;
    private final Starter starter;
    private Worker<?> worker;

    public BackgroundExecution(EventSource eventSource, Starter starter, Worker<?> worker) {
        this.eventSource = eventSource;
        this.starter = starter;
        this.worker = worker;
    }

    @Override
    public <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        eventSource.addEventHandler(eventType, eventHandler);
    }

    public ReadOnlyStringProperty messageProperty() {
        return worker.messageProperty();
    }

    public void start(){
        starter.start();
    }

    public void cancel() {
        worker.cancel();
    }
}
