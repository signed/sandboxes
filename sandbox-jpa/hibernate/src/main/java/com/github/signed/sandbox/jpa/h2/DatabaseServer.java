package com.github.signed.sandbox.jpa.h2;

import java.sql.SQLException;

import org.h2.tools.Server;

public class DatabaseServer {

    private final Server server;
    private int port;

    public DatabaseServer(int port) {
        this.port = port;
        try {
            server = Server.createTcpServer("-tcpPort", portAsString(), "-tcpAllowOthers");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        try {
            server.start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            Server.shutdownTcpServer("tcp://localhost:" + portAsString(), "", true, false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        server.stop();
    }

    private String portAsString() {
        return Integer.toString(this.port);
    }
}
