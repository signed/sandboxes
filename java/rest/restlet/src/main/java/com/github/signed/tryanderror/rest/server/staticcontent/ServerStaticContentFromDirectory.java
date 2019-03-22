package com.github.signed.tryanderror.rest.server.staticcontent;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.Directory;

public class ServerStaticContentFromDirectory extends Application {
    @Override
    public Restlet createInboundRoot() {
        Directory directory = new Directory(getContext(), "file:///tmp/nodes/");
        directory.setListingAllowed(true);
        return directory;
    }
}
