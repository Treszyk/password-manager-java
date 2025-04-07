package com.treszyk.passwordmanager.crypto;

import java.util.Base64;

public class CryptoUtils {
    public static String EncodeByteArrayToBase64(byte[] array) {
        return Base64.getEncoder().encodeToString(array);
    }

    public static byte[] DecodeBase64ToByteArray(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
