package concurrent;

import javafx.concurrent.Service;

public class ServiceStarter implements Starter {
    private Service<?> service;

    public ServiceStarter(Service<?> service) {
        this.service = service;
    }

    @Override
    public void start() {

        service.reset();
        service.start();
    }
}
