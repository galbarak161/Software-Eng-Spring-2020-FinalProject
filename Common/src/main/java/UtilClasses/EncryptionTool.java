package UtilClasses;

import java.util.Base64;

public class EncryptionTool {
	
	public static String encrypt(String inputToEncrypt) throws Exception {
		byte[] encodedBytes = Base64.getEncoder().encode(inputToEncrypt.getBytes());	
		String encoderString = new String(encodedBytes);
		
		//System.out.println("encoded: " + inputToEncrypt + " To: " + encoderString);
		
		return encoderString;
	}

	public static String decrypt(String inputToDecrypt) throws Exception {
		byte[] decodedBytes = Base64.getDecoder().decode(inputToDecrypt.getBytes());
		String decodedString =  new String(decodedBytes);
		
		//System.out.println("decoded: " + inputToDecrypt + " To: " + decodedString);
		
		return decodedString;
	}
}
