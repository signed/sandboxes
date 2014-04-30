package tryanderror.encryption;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isNotNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Collection;
import java.util.Enumeration;

import javax.crypto.Cipher;

import org.junit.Test;

public class Rsa {
	
    @Test
	public void generateAPublicAnAPrivateRsaKey() throws Exception {

		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.genKeyPair();
		Key publicKey = kp.getPublic();
		Key privateKey = kp.getPrivate();

		KeyFactory fact = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(),
				RSAPublicKeySpec.class);
		RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(),
				RSAPrivateKeySpec.class);
	}

	@Test
	public void retrieveKeysFromAPkcs12File() throws Exception {
		KeyStore keystore = java.security.KeyStore.getInstance("PKCS12");

		File file = getFileFor("bundle.p12");

		keystore.load(new FileInputStream(file), "secret".toCharArray());

		for (Enumeration enums = keystore.aliases(); enums.hasMoreElements();) {
			String alias = (String) enums.nextElement();
			if (keystore.isKeyEntry(alias)) {
				PrivateKey key = (PrivateKey) keystore.getKey(alias, "secret".toCharArray());
			}
		}
	}

	@Test
	public void retriveKeyFromAPkcs7File() throws Exception {
				File publicKeyFile = getFileFor("server.p7b"); 
		        FileInputStream fileInputStream = new FileInputStream(publicKeyFile);
		        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		        Collection collection = certificateFactory.generateCertificates(fileInputStream);
		        Certificate certificate = (Certificate) collection.iterator().next();
		        PublicKey publicKey = certificate.getPublicKey();
		        System.out.println(publicKey);
		        assertThat(publicKey, is(notNullValue()));
	}

	public void saveToFile(String fileName, BigInteger mod, BigInteger exp) throws IOException {
		FileOutputStream out = new FileOutputStream(fileName);
		BufferedOutputStream out2 = new BufferedOutputStream(out);
		ObjectOutputStream oout = new ObjectOutputStream(out2);
		try {
			oout.writeObject(mod);
			oout.writeObject(exp);
		} catch (Exception e) {
			throw new IOException("Unexpected error", e);
		} finally {
			oout.close();
		}
	}

	@Test
	public void testname() throws Exception {
		KeyPair keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, keys.getPublic());
		byte[] rawData = "top secret".getBytes();
		byte[] encrypted = cipher.doFinal(rawData);

		cipher.init(Cipher.DECRYPT_MODE, keys.getPrivate());
		byte[] decrypted = cipher.doFinal(encrypted);
		String notASecretAnyMore = new String(decrypted);
	}
	
	private File getFileFor(String filename) {
		URL url = getClass().getResource("/rsa_keys/server/"+filename);
		File file = new File(url.getFile());
		return file;
	}
}