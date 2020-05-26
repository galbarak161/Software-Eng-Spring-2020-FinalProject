package CommonElements;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import javax.crypto.Cipher;

public class EncryptionGenerator {

	private final String encryptionKey = "RSA";
	private final String cipherArgs = "RSA/ECB/PKCS1Padding";
	private final int keySize = 512;
	private KeyPair pair = null;
	private Cipher cipher = null;

	public EncryptionGenerator() throws Exception {
		// Creating KeyPair generator object
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(encryptionKey);

		// Initializing the key pair generator
		keyPairGen.initialize(keySize);

		// Generate the pair of keys
		pair = keyPairGen.generateKeyPair();

		// Creating a Cipher object
		cipher = Cipher.getInstance(cipherArgs);
	}

	public String encrypt(String input) throws Exception {
		// Initializing a Cipher object
		cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());

		// Add data to the cipher
		cipher.update(input.getBytes());

		// encrypting the data
		byte[] cipherText = cipher.doFinal();

		System.out.println(new String(cipherText, "UTF8"));

		return new String(cipherText, "UTF8");
	}

	public String decrypt(String input) throws Exception {
		// Initializing a Cipher object
		cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());

		// Decrypting the text
		byte[] decipheredText = cipher.doFinal(input.getBytes());

		System.out.println(new String(decipheredText));

		return new String(decipheredText);
	}
}
