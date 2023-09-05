/* Team Delta
 * Authors: Bryce Kellas
 * Class to create a salted hash via "PBKDF2WithHmacSHA1" and validate a user entered password - 
 * Adapted from: https://reflectoring.io/creating-hashes-in-java/ - November 21, 2021 - Author: Pratik Das
 *      Accessed 9/1/2023-Only used code for PBKDF2WithHmacSHA1 segment, renamed class to HashPassword from HashCreator
 */
package lodge;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashPassword {

    public String generateStrongPasswordHash(final String password) 
        throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        int iterations = 1000;
        byte[] salt = createSalt();

        byte[] hash = createPBEHash(password, iterations, salt, 64);

        // prepend iterations and salt to the hash
        return iterations + ":" + convertToHex(salt) + ":" + convertToHex(hash);
    }

    //Create salt
    private byte[] createSalt() throws NoSuchAlgorithmException, NoSuchProviderException {

        //Always use a SecureRandom generator for random salt
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");

        //Create array for salt
        byte[] salt = new byte[16];

        //Get a random salt
        sr.nextBytes(salt);

        //return salt
        return salt;
   }

    //Create hash of password with salt, iterations, and keylength
    private byte[] createPBEHash(final String password, final int iterations, final byte[] salt, final int keyLength) 
        throws NoSuchAlgorithmException, InvalidKeySpecException {

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength * 8);

        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        return skf.generateSecret(spec).getEncoded();
    }

    // Validate a password entered by user against and existing hash
    public boolean validatePassword(final String originalPassword, final String storedPasswordHash) 
        throws NoSuchAlgorithmException, InvalidKeySpecException {

        // Split the string by :
        String[] parts = storedPasswordHash.split(":");

        // Extract iterations, salt, and hash 
        // from the stored password hash
        int iterations = Integer.valueOf(parts[0]); 
        byte[] salt = convertToBytes(parts[1]);
        byte[] hash = convertToBytes(parts[2]);

        byte[] originalPasswordHash = createPBEHash(originalPassword, iterations, salt, hash.length);

        int diff = hash.length ^ originalPasswordHash.length;
        for(int i = 0; i < hash.length && i < originalPasswordHash.length; i++){
            diff |= hash[i] ^ originalPasswordHash[i];
        }
        return diff == 0;
    }

    // Convert a byte String to hex format
    private String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }

    // Convert a hex format string to byte
    private byte[] convertToBytes(final String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        
        for(int i = 0; i < bytes.length; i++){
            bytes[i] = Integer.valueOf(hex.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return bytes;
    }

    // Test driver for testing hash creating and validation
    public static void main(String[] args) 
        throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException, FileNotFoundException, IOException {
            HashPassword hashPassword = new HashPassword();

			String strongPwdHash = hashPassword.generateStrongPasswordHash("password123");
			System.out.println(strongPwdHash);
			
			boolean matchResult = hashPassword.validatePassword("password123", strongPwdHash);
			System.out.println("matchResult "+matchResult);
	}
}