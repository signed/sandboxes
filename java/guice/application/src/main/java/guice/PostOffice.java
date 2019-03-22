package guice;

import javax.inject.Inject;

public class PostOffice {

    private final Dispatcher<Mail> mailDispatcher;
    private final Dispatcher<Package> packageDispatcher;

    @Inject
    public PostOffice(Dispatcher<Mail> mailDispatcher, Dispatcher<Package> packageDispatcher) {
        this.mailDispatcher = mailDispatcher;
        this.packageDispatcher = packageDispatcher;
    }

    public void deliver() {
        mailDispatcher.dispatch();
        packageDispatcher.dispatch();
    }
}
