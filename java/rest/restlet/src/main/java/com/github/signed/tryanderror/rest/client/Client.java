package com.github.signed.tryanderror.rest.client;

import com.github.signed.tryanderror.rest.domain.Person;
import com.github.signed.tryanderror.rest.services.PersonResource;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws Exception {
        String uri = "http://localhost:8182/firstSteps/hello";
        String customer = "http://localhost:8182/step/customer";
        ClientResource resource = createClientResourceFor(customer);
        writeToSystemOut(resource.post(null));
        
        

//        final ClientResource cr = new ClientResource("http://localhost:8182/restlet/person");
//        final PersonResource resources = cr.wrap(PersonResource.class);
//        final Person person = resources.retrieve();

//        System.out.println(person.name);
    }

    private static ClientResource createClientResourceFor(String uri) {
        return new ClientResource(uri);
    }

    private static void writeToSystemOut(Representation representation) throws IOException {
        representation.write(System.out);
    }
}