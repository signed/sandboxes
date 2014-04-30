package com.github.signed.tryanderror.rest.server;

import com.github.signed.tryanderror.rest.domain.Person;
import com.github.signed.tryanderror.rest.services.PersonResource;
import org.restlet.resource.ServerResource;

public class PersonServerResource extends ServerResource implements PersonResource {

    private static volatile Person person1 = new Person("Peter", 40);

    @Override
    public Person retrieve() {
        return person1;
    }

    @Override
    public Person copy(final Person p) {
        return p;
    }
}