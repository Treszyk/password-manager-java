package com.treszyk.passwordmanager.crypto;

import java.util.Base64;
//change the names to match the java way to name methods
public class CryptoUtils {
    //maybe this class should hold the master key as well? would it affect the security of the program????
    public static String EncodeByteArrayToBase64(byte[] array) {
        return Base64.getEncoder().encodeToString(array);
    }

    public static byte[] DecodeBase64ToByteArray(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static String DecodeBase64ToReadableString(String base64) {
        return "To be implemented";
    }

    public static String DecodeByteArrayToReadableString(String base64) {
        //probably not needed
        return "To be implemented";
    }

    public static String encryptPassword(String userPassword) {
        String base64Encrypted = userPassword + "encrypted";
        //encrypt pass here
        return base64Encrypted;
    }
}
