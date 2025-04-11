package com.treszyk.passwordmanager.crypto;

import com.treszyk.passwordmanager.util.Constants;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class KeyDerivation {
    public static void Example() {
        String password = "password123";
        String base64Salt = "zC+kZu6hdyghGTb4O+8+Nw==";
        byte[] salt = Base64.getDecoder().decode(base64Salt);

        int iterations = 100000;
        int keyLength = 256;

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] secretKey;
        try {
            secretKey = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        String base64SecretKey = Base64.getEncoder().encodeToString(secretKey);
        System.out.println("Derived Secret Key: " + base64SecretKey); // should output: [Secret Key: j/VmWNHfNcTS8D0kKKKX4PP0PqKrc+rC8VheXwLD/Rw=]
    }
    public static SecretKey deriveKey(char[] masterPass, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(masterPass, salt, Constants.PBKDF2_ITERATIONS, Constants.PBKDF2_KEY_SIZE);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance(Constants.KEY_DERIVATION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("The derivation algorithm could not be found, check settings: " + e);
            throw new RuntimeException(e);
        }
        SecretKey tmp;
        try {
            tmp = factory.generateSecret(spec);
        } catch (InvalidKeySpecException e) {
            System.out.println("Invalid key specifications: " + e);
            throw new RuntimeException(e);
        }
        Arrays.fill(masterPass, '\0');
        spec.clearPassword();
        return new SecretKeySpec(tmp.getEncoded(), Constants.ENCRYPTION_ALGORITHM);
    }
}
