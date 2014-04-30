package com.github.signed.tryanderror.rest.server.firststeps;

import com.github.signed.tryanderror.rest.domain.Customer;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

public class CustomerResource extends ServerResource {

    private static volatile Customer myCustomer = Customer.createSample();

    @Get
    public Customer retrieve() {
        return myCustomer;
    }

    @Put
    public void store(Customer customer) {
        myCustomer = customer;
    }

    @Post
    public String handlePost() {
        getResponse().setStatus(Status.SUCCESS_CREATED);
        getResponse().redirectSeeOther("http://www.idos.de");
        return "HTTP POST.";
    }
}