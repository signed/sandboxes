package com.github.signed.tryanderror.sftp;

import net.schmizz.sshj.common.KeyType;
import net.schmizz.sshj.transport.verification.OpenSSHKnownHosts;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LoadPublicKeyFromKnownHosts_Test {

    private final BigInteger y = new BigInteger("18226708153648715711288874856052985268113303959121736442781051674059176370996402024446715347011127200085029534002879241134794751792794374977362557950732424250028476206807864978099939804631704654672032856670483093723026555234069198194514575599154150815422312588086704804758323913258088757583867862120211692754");
    private final BigInteger p = new BigInteger("169681939708027790944661846904714852379361462648127669771054435709970244636023330464075771363639741799176245656362610906393475051074915187029090777848884285108353074118793606929636867806219447783707900195022671939556774784852768934498078014217821638033168613366966535532498666539726280024877551527822112757821");
    private final BigInteger q = new BigInteger("1322074206163278206825028169808530589813184389963");
    private final BigInteger g = new BigInteger("60300140882990912410847678092832826153739741326146732167996083167044227787908017715655778544971212345361787278649161124050108491159412866658592147238369664583814007701576363536594080820414541946989857244242273262755638195274761146731910475363368314302848413403090018971179671466673150272503410454506851181264");


    @Test
    public void someTest() throws Exception {
        String lineFromKnownHosts = "|1|0SbajyQO4/FyeCqeQP1WMRAm5ko=|tt9eyFzqWlTQJeMgfRSGNgbnCqw= ssh-dss AAAAB3NzaC1kc3MAAACBAPGilhrKurL/VMDUga1yTZPlh4QX/ydFiWWS3u8wNkN2xuTIvGQCUXI2oM/OjPpPXKQvmrETvbmJ4o+Yg9zOXaEFu5Oh9dFzXVSKWHmZ+ooZQDxNMhc/OWlrP3o0B3Vq/j7VQv4KvjWkU1QgUbbxryCwanBrCofwuABBm53pWpg9AAAAFQDnk9uVyDAjcx+Ci06G2W8Yx4vjSwAAAIBV3sjEhhzduD/0ZNKYEmUpLmPO3Q1QF79W9qM1WYk4xA0YmIziLkArT7vhbhImMHM+v6uG0PpGC14pfvNKvw0v2Oa/MBAZCaj9Zf3lip9quwPQNZxhP/uG04PAKL4FTIXwzisklghMN+34TPmYIr4xPOuqmkxdpyWPzlYWMs6i0AAAAIAZ9KhcSOrfwgAAVhR+h8KbQtloW2euUy5dcg84lh+lavpImPpUG3JB9gKCKW6nWFYnsGtgwiLcspr0uZEB7SMZgadBhsrf7cbL5DfNyRutzxiyyr+R9y70bNqbknwF6i2X9eP++UHVxJwIPLjy3TfRQPBKH6AuSXUO6aG6+Rg80g==";
        doStuff(lineFromKnownHosts);
    }

    @Test
    public void someOtherTest() throws Exception {
        String lineFromKnownHosts = "[localhost]:2022 ssh-dss AAAAB3NzaC1kc3MAAACBAPGilhrKurL/VMDUga1yTZPlh4QX/ydFiWWS3u8wNkN2xuTIvGQCUXI2oM/OjPpPXKQvmrETvbmJ4o+Yg9zOXaEFu5Oh9dFzXVSKWHmZ+ooZQDxNMhc/OWlrP3o0B3Vq/j7VQv4KvjWkU1QgUbbxryCwanBrCofwuABBm53pWpg9AAAAFQDnk9uVyDAjcx+Ci06G2W8Yx4vjSwAAAIBV3sjEhhzduD/0ZNKYEmUpLmPO3Q1QF79W9qM1WYk4xA0YmIziLkArT7vhbhImMHM+v6uG0PpGC14pfvNKvw0v2Oa/MBAZCaj9Zf3lip9quwPQNZxhP/uG04PAKL4FTIXwzisklghMN+34TPmYIr4xPOuqmkxdpyWPzlYWMs6i0AAAAIAZ9KhcSOrfwgAAVhR+h8KbQtloW2euUy5dcg84lh+lavpImPpUG3JB9gKCKW6nWFYnsGtgwiLcspr0uZEB7SMZgadBhsrf7cbL5DfNyRutzxiyyr+R9y70bNqbknwF6i2X9eP++UHVxJwIPLjy3TfRQPBKH6AuSXUO6aG6+Rg80g==";
        doStuff(lineFromKnownHosts);
    }

    private void doStuff(String lineFromKnownHosts) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        OpenSSHKnownHosts.HostEntry hostEntry = OpenSSHKnownHosts.EntryFactory.parseEntry(lineFromKnownHosts);

        KeyFactory dsa = KeyFactory.getInstance("DSA", new BouncyCastleProvider());
        KeySpec spec = new DSAPublicKeySpec(y, p, q, g);
        PublicKey publicKey = dsa.generatePublic(spec);

        System.out.println(hostEntry.getLine());
        assertThat(hostEntry.verify(publicKey), is(true));
        assertThat(hostEntry.appliesTo(KeyType.DSA, "[localhost]:2022"), is(true));
    }
}
