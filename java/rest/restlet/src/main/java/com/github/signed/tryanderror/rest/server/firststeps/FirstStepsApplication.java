package com.github.signed.tryanderror.rest.server.firststeps;

import com.github.signed.tryanderror.rest.server.PersonServerResource;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class FirstStepsApplication extends Application {

    @Override
    public synchronized Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attach("/hello", HelloWorldResource.class);
        router.attach("/customer", CustomerResource.class);
        router.attach("/person", PersonServerResource.class);
        return router;
    }
}