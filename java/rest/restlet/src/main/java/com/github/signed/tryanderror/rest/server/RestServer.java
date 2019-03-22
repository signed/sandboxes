package com.github.signed.tryanderror.rest.server;

import com.github.signed.tryanderror.rest.server.firststeps.FirstStepsApplication;
import com.github.signed.tryanderror.rest.server.staticcontent.ServerStaticContentFromDirectory;
import com.github.signed.utils.FilesystemUtils;
import org.restlet.Component;
import org.restlet.data.Protocol;

import java.io.File;

public class RestServer {

    public static void main(String[] args) throws Exception {
        final File nodesDirectory = new File(FilesystemUtils.getRootOfTmpDirectory(), "nodes");
        nodesDirectory.mkdirs();

        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8182);
        component.getClients().add(Protocol.FILE);
        component.getDefaultHost().attach("/step", new FirstStepsApplication());
        component.getDefaultHost().attach("/nodes", new ServerStaticContentFromDirectory());
        component.start();
    }
}