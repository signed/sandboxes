package com.github.signed.tryanderror.sftp;

public class HMacSha1Result {
    public final byte[] digest;
    public final byte[] salt;


    public HMacSha1Result(byte[] salt, byte[] digest) {
        this.salt = salt;
        this.digest = digest;
    }
}
