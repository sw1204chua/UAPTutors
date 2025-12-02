/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.model;

/**
 *
 * @author Sean
 */
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final int HASH_LENGTH = 256; // Hash length in bits
    private static final int ITERATIONS = 10000; // Number of iterations

    // Hash the password using PBKDF2 with SHA-256
    public static String hashPassword(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), new byte[0], ITERATIONS, HASH_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    // Verify the password
    public static boolean verifyPassword(String inputPassword, String storedHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String computedHash = hashPassword(inputPassword);
        return storedHash.equals(computedHash);
    }
}
