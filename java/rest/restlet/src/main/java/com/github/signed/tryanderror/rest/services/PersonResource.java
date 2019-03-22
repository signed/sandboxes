package com.github.signed.tryanderror.rest.services;

import com.github.signed.tryanderror.rest.domain.Person;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface PersonResource {

    @Get
    public Person retrieve();

    @Post
    public Person copy(Person person);

}