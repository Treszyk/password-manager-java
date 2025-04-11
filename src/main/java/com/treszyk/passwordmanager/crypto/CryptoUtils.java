package com.treszyk.passwordmanager.crypto;

import com.treszyk.passwordmanager.util.Constants;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

//change the names to match the java way to name methods
public class CryptoUtils {
    //maybe this class should hold the master key as well? would it affect the security of the program????
    public static String encodeByteArrayToBase64(byte[] array) {
        return Base64.getEncoder().encodeToString(array);
    }

    public static byte[] decodeBase64ToByteArray(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static String decodeBase64ToReadableString(String base64) {
        return "To be implemented";
    }

    public static String decodeByteArrayToReadableString(byte[] byteArray) {
        //this method is kinda worthless since it's so easy to convert bytearray to string
        return new String(byteArray, StandardCharsets.UTF_8);
    }

    //returns the encrypted password with the unique IV
    public static String encryptPassword(String userPassword, SecretKey masterKey) {
        byte[] iv = generateIV();

        Cipher cipher;
        try {
            cipher = Cipher.getInstance(Constants.CIPHER_TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Error initializing cipher: " + e.getMessage(), e);
        }

        GCMParameterSpec gcmSpec = new GCMParameterSpec(Constants.GCM_TAG_LENGTH, iv);

        try {
            cipher.init(Cipher.ENCRYPT_MODE, masterKey, gcmSpec);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Invalid key provided for encryption: " + e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("Invalid algorithm parameter for GCM mode: " + e.getMessage(), e);
        }

        byte[] byteArrayEncrypted;
        try {
            byteArrayEncrypted = cipher.doFinal(userPassword.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("Error during encryption, invalid block size: " + e.getMessage(), e);
        } catch (BadPaddingException e) {
            throw new RuntimeException("Encryption failed due to bad padding: " + e.getMessage(), e);
        }

        String base64Encrypted = encodeByteArrayToBase64(byteArrayEncrypted);
        String base64IV = encodeByteArrayToBase64(iv);

        return base64Encrypted + "," + base64IV;
    }

    public static String decryptPassword(String encryptedPass, SecretKey masterKey, String passIV) {
        try {
            byte[] encryptedPassword = Base64.getDecoder().decode(encryptedPass);
            byte[] iv = Base64.getDecoder().decode(passIV);

            Cipher cipher = Cipher.getInstance(Constants.CIPHER_TRANSFORMATION);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(Constants.GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, masterKey, gcmSpec);

            byte[] decryptedPasswordBytes = cipher.doFinal(encryptedPassword);

            return decodeByteArrayToReadableString(decryptedPasswordBytes);
        } catch (AEADBadTagException e) {
            System.err.println("Decryption failed: Tag mismatch. Likely an incorrect password.");
            return "Error: Incorrect password or corrupted data";
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            System.err.println("Decryption failed due to crypto-related error: " + e.getMessage());
            e.printStackTrace();
            return "Error: Crypto error during decryption";
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            System.err.println("Decryption failed: Block size or padding issue.");
            return "Error: Decryption block/padding error";
        } catch (Exception e) {
            System.err.println("Decryption failed: " + e.getMessage());
            e.printStackTrace();
            return "Error: Unexpected error during decryption";
        }
    }

    public static byte[] generateIV() {
        byte[] iv = new byte[Constants.AES_IV_SIZE];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return iv;
    }
}
