package guice;

import javax.inject.Inject;

public class Dispatcher<T extends Deliverable> {

    private final T deliverable;

    @Inject
    public Dispatcher(T deliverable){
        this.deliverable = deliverable;
    }

    public void dispatch() {
        System.out.println("dispatching "+deliverable.name());
    }
}
