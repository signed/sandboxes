package com.github.signed.tryanderror.sftp;

import net.schmizz.sshj.common.Buffer;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

import java.security.PublicKey;

import static com.github.signed.tryanderror.sftp.ServerDsaKeyFactory.createTheServerkey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class KnownHost_Test {

    private final String publicKeyBase64Encoded = "AAAAB3NzaC1kc3MAAACBAPGilhrKurL/VMDUga1yTZPlh4QX/ydFiWWS3u8wNkN2xuTIvGQCUXI2oM/OjPpPXKQvmrETvbmJ4o+Yg9zOXaEFu5Oh9dFzXVSKWHmZ+ooZQDxNMhc/OWlrP3o0B3Vq/j7VQv4KvjWkU1QgUbbxryCwanBrCofwuABBm53pWpg9AAAAFQDnk9uVyDAjcx+Ci06G2W8Yx4vjSwAAAIBV3sjEhhzduD/0ZNKYEmUpLmPO3Q1QF79W9qM1WYk4xA0YmIziLkArT7vhbhImMHM+v6uG0PpGC14pfvNKvw0v2Oa/MBAZCaj9Zf3lip9quwPQNZxhP/uG04PAKL4FTIXwzisklghMN+34TPmYIr4xPOuqmkxdpyWPzlYWMs6i0AAAAIAZ9KhcSOrfwgAAVhR+h8KbQtloW2euUy5dcg84lh+lavpImPpUG3JB9gKCKW6nWFYnsGtgwiLcspr0uZEB7SMZgadBhsrf7cbL5DfNyRutzxiyyr+R9y70bNqbknwF6i2X9eP++UHVxJwIPLjy3TfRQPBKH6AuSXUO6aG6+Rg80g==";
    private final PublicKey serverKey = createTheServerkey();

    @Test
    public void ensureBothPublicKeysAreTheSame() throws Exception {
        byte[] decode = Base64.decode(publicKeyBase64Encoded);
        PublicKey publicKey = new Buffer.PlainBuffer(decode).readPublicKey();
        boolean equals = publicKey.equals(serverKey);
        assertThat(equals, is(true));
    }


    @Test
    public void allInOnePackage() throws Exception {
        KnownHost knownHost = new KnownHost("localhost", 22, serverKey);
        assertThat(knownHost.toLine(), is("localhost ssh-dss AAAAB3NzaC1kc3MAAACBAPGilhrKurL/VMDUga1yTZPlh4QX/ydFiWWS3u8wNkN2xuTIvGQCUXI2oM/OjPpPXKQvmrETvbmJ4o+Yg9zOXaEFu5Oh9dFzXVSKWHmZ+ooZQDxNMhc/OWlrP3o0B3Vq/j7VQv4KvjWkU1QgUbbxryCwanBrCofwuABBm53pWpg9AAAAFQDnk9uVyDAjcx+Ci06G2W8Yx4vjSwAAAIBV3sjEhhzduD/0ZNKYEmUpLmPO3Q1QF79W9qM1WYk4xA0YmIziLkArT7vhbhImMHM+v6uG0PpGC14pfvNKvw0v2Oa/MBAZCaj9Zf3lip9quwPQNZxhP/uG04PAKL4FTIXwzisklghMN+34TPmYIr4xPOuqmkxdpyWPzlYWMs6i0AAAAIAZ9KhcSOrfwgAAVhR+h8KbQtloW2euUy5dcg84lh+lavpImPpUG3JB9gKCKW6nWFYnsGtgwiLcspr0uZEB7SMZgadBhsrf7cbL5DfNyRutzxiyyr+R9y70bNqbknwF6i2X9eP++UHVxJwIPLjy3TfRQPBKH6AuSXUO6aG6+Rg80g=="));
    }

    @Test
    public void allInOnePackageWithPort() throws Exception {
        KnownHost knownHost = new KnownHost("localhost", 2022, serverKey);
        assertThat(knownHost.toLine(), is("[localhost]:2022 ssh-dss AAAAB3NzaC1kc3MAAACBAPGilhrKurL/VMDUga1yTZPlh4QX/ydFiWWS3u8wNkN2xuTIvGQCUXI2oM/OjPpPXKQvmrETvbmJ4o+Yg9zOXaEFu5Oh9dFzXVSKWHmZ+ooZQDxNMhc/OWlrP3o0B3Vq/j7VQv4KvjWkU1QgUbbxryCwanBrCofwuABBm53pWpg9AAAAFQDnk9uVyDAjcx+Ci06G2W8Yx4vjSwAAAIBV3sjEhhzduD/0ZNKYEmUpLmPO3Q1QF79W9qM1WYk4xA0YmIziLkArT7vhbhImMHM+v6uG0PpGC14pfvNKvw0v2Oa/MBAZCaj9Zf3lip9quwPQNZxhP/uG04PAKL4FTIXwzisklghMN+34TPmYIr4xPOuqmkxdpyWPzlYWMs6i0AAAAIAZ9KhcSOrfwgAAVhR+h8KbQtloW2euUy5dcg84lh+lavpImPpUG3JB9gKCKW6nWFYnsGtgwiLcspr0uZEB7SMZgadBhsrf7cbL5DfNyRutzxiyyr+R9y70bNqbknwF6i2X9eP++UHVxJwIPLjy3TfRQPBKH6AuSXUO6aG6+Rg80g=="));
    }


}
