package com.github.signed.tryanderror.sftp;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

import javax.crypto.spec.SecretKeySpec;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Tryout_Test {
    private final String saltBase64 = "m2iPYR2tBjmNnrykWOrdDhSUiQQ=";
    private byte[] salt = Base64.decode(saltBase64);
    private final String host = "localhost";
    private final String messageDigestBase64 = "UmCsNF7C3/VML7m7CizecRacVEA=";
    private final byte[] messageDigest = Base64.decode(messageDigestBase64);
    private  final HMacSha1 macSha1 = new HMacSha1();


    @Test
    public void returnsTheSaltFromTheSecretKey() throws Exception {
        SecretKeySpec hMacSHA1 = new SecretKeySpec(salt, "HMacSHA1");
        assertThat(hMacSHA1.getEncoded(), is(salt));
    }

    @Test
    public void hMacSha1Verification() throws Exception {
        assertThat(macSha1.digest(host, salt), is(messageDigest));
    }

    @Test
    public void hMacSha1VerificationWithIp() throws Exception {
        byte[] salt = Base64.decode("u/QML//eJMg/H0YASOFpZS/mDqc=");
        byte[] messageDigest = Base64.decode("6jjZNFQVleOCekhC9hdI3HMpWxc=");
        assertThat(macSha1.digest("127.0.0.1", salt), is(messageDigest));
    }

    @Test
    public void hMacSha1VerificationWithPort() throws Exception {
        byte[] salt = Base64.decode("0SbajyQO4/FyeCqeQP1WMRAm5ko=");
        byte[] messageDigest = Base64.decode("tt9eyFzqWlTQJeMgfRSGNgbnCqw=");
        assertThat(macSha1.digest("[localhost]:2022", salt), is(messageDigest));
    }

    @Test
    public void creation() throws Exception {
        HMacSha1Result digest = macSha1.digest("localhost");
        assertThat(macSha1.digest("localhost", digest.salt), is(digest.digest));
    }
}
