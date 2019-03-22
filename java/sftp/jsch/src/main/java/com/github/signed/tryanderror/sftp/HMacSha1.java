package com.github.signed.tryanderror.sftp;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HMacSha1 {
    public static final BouncyCastleProvider Provider = new BouncyCastleProvider();
    public static final String HMacName = "HMacSHA1";

    public HMacSha1Result digest(String message) throws NoSuchAlgorithmException, InvalidKeyException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(HMacName, Provider);
        SecretKey salt = keyGenerator.generateKey();
        byte[] digest = digest(message, salt);
        return new HMacSha1Result(salt.getEncoded(), digest);
    }

    public byte[] digest(String message, byte[] salt) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(salt, HMacName);
        return digest(message, secretKeySpec);
    }

    private byte[] digest(String message, SecretKey salt) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMacName, new BouncyCastleProvider());
        mac.init(salt);
        mac.reset();
        mac.update(message.getBytes());
        return mac.doFinal();
    }
}
