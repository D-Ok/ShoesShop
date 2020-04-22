package shoesShop.Tools;

import java.io.UnsupportedEncodingException; 
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Security {
		
		private static final String algoritm = "AES";
		private static Key key;
		private static Cipher cipher;
		

		public static String encrypt(String s, String pass) {
			String pinStr = String.valueOf(pass);
			
			try {
				 if(cipher==null) initializeCipher();
				  key = getKey(s);
				  cipher.init(Cipher.ENCRYPT_MODE, key);

				  byte[] plainText  = pinStr.getBytes("UTF-8");
				  byte[] cipherText = cipher.doFinal(plainText);
				  return Base64.getEncoder().encodeToString(cipherText);
			      
			} catch (InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
			  return null;
		  }
		
		public static short decrypt(String s, String pass) {
			  try {
				if(cipher==null) initializeCipher();
				key = getKey(s);
				cipher.init(Cipher.DECRYPT_MODE, key);
				
				byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(pass));
				return Short.valueOf(new String(cipherText, "UTF-8"));
				
			} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
				return 0;
			}
		      
		}
		
		private static void initializeCipher() {
			if(cipher==null) {
				try {
					 cipher = Cipher.getInstance(algoritm);
				} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
					e.printStackTrace();
				}     
				
			}
		}

		
		private static Key getKey(String card) {
			byte[] key;
			try {
				key = ("salt"+card).getBytes("UTF-8");
				MessageDigest sha = MessageDigest.getInstance("SHA-1");
			    key = sha.digest(key);
			    key = Arrays.copyOf(key, 16);
			    SecretKeySpec secretKeySpec = new SecretKeySpec(key, algoritm);
			    return secretKeySpec;
			} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
			return null;
		}

}
