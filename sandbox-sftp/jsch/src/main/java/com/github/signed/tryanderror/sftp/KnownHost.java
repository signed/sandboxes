package com.github.signed.tryanderror.sftp;

import net.schmizz.sshj.common.KeyType;
import net.schmizz.sshj.transport.verification.OpenSSHKnownHosts;

import java.security.PublicKey;

public class KnownHost {

    public static final OpenSSHKnownHosts.Marker NoMarker = null;
    public static final int DefaultSshPort = 22;
    private String host;
    private int port;
    private PublicKey hostKey;

    public KnownHost(String host, int port, PublicKey hostKey) {
        this.host = host;
        this.port = port;
        this.hostKey = hostKey;
    }

    public String toLine() {
        KeyType keyType = KeyType.fromKey(hostKey);
        String hostname = "[" + host + "]:" + port;
        if(DefaultSshPort == port){
            hostname = host;
        }
        OpenSSHKnownHosts.SimpleEntry simpleEntry = new OpenSSHKnownHosts.SimpleEntry(NoMarker, hostname, keyType, hostKey);
        return simpleEntry.getLine();
    }
}
