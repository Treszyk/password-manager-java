package com.treszyk.passwordmanager.util;

import com.treszyk.passwordmanager.crypto.CryptoUtils;

import java.security.SecureRandom;

public class SaltGenerator {
    public static byte[] generateSalt(int length) {
        //add a check to see if salt.dat isn't empty!@!@!
        if(FileUtils.fileExists(Constants.SALT_PATH)) {
            System.out.println("Decoded an already existing salt!");
            return CryptoUtils.DecodeBase64ToByteArray(FileUtils.readBase64FromFile(Constants.SALT_PATH));
        }

        System.out.println("Generating a new salt...");
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[length];
        secureRandom.nextBytes(salt);
        String base64salt = CryptoUtils.EncodeByteArrayToBase64(salt);
        FileUtils.writeBase64ToFile(Constants.SALT_PATH, base64salt);

        return salt;
    }
}
