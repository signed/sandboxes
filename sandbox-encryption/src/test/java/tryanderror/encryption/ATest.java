package tryanderror.encryption;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.security.Provider.Service;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.hibernate.type.EncryptedBinaryType;
import org.jasypt.util.binary.BasicBinaryEncryptor;
import org.jasypt.util.binary.BinaryEncryptor;
import org.jasypt.util.binary.StrongBinaryEncryptor;
import org.jasypt.util.digest.Digester;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.junit.Test;

public class ATest {
	
	@Test
	public void testname() throws Exception {
		Digester digester = new Digester();
		digester.setAlgorithm("SHA-1");
		byte[] message = "doom".getBytes();
		byte[] digest = digester.digest(message);
		System.out.println(digest);
	}
	
	@Test
	public void another() throws Exception {
		PasswordEncryptor encryptor = new BasicPasswordEncryptor();
		encryptor = new StrongPasswordEncryptor();
		String encryptPassword = encryptor.encryptPassword("doom");
		assertThat(encryptor.checkPassword("doom", encryptPassword), is(true));
	}
	
	@Test
	public void binaryDefault() throws Exception {
		BasicBinaryEncryptor encryptor = new BasicBinaryEncryptor(); 
		
		String myEncryptionPassword = "das ist mein passwort und es sollte auch ein key gehen";
		encryptor.setPassword(myEncryptionPassword);
		
		byte[] myBinary = "Das ist super geheim".getBytes();
		byte[] encryptedStuff = encryptor.encrypt(myBinary);
		
		 byte[] decryptedStuff = encryptor.decrypt(encryptedStuff);
		 String plainStringAgain = new String(decryptedStuff);

		 System.out.println("encrypted stuff: " +new String(encryptedStuff));
		 System.out.println("plain string   : "+ plainStringAgain);
	}
	
	@Test
	public void bouncyCastleAlgos() throws Exception {
		BouncyCastleProvider provider = new BouncyCastleProvider();
		System.out.println(provider.getInfo());
		for( Service service : provider.getServices()) {
			System.out.println(service.getAlgorithm());
		}
		
	}
	
	@Test
	public void binary() throws Exception {
		StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
		//encryptor.setProvider(new BouncyCastleProvider());
		encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		
		String myEncryptionPassword = "das ist mein passwort und es sollte auch ein key gehen";
		encryptor.setPassword(myEncryptionPassword);
		
		byte[] myBinary = "Das ist super geheim".getBytes();
		byte[] encryptedStuff = encryptor.encrypt(myBinary);
		
		 byte[] decryptedStuff = encryptor.decrypt(encryptedStuff);
		 String plainStringAgain = new String(decryptedStuff);

		 System.out.println("encrypted stuff: " +new String(decryptedStuff));
		 System.out.println("plain string   : "+ plainStringAgain);
	}
}