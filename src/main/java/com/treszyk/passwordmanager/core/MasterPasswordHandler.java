package com.treszyk.passwordmanager.core;

import com.treszyk.passwordmanager.crypto.KeyDerivation;
import com.treszyk.passwordmanager.util.Constants;
import com.treszyk.passwordmanager.util.SaltGenerator;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Scanner;

public class MasterPasswordHandler {
    public SecretKey getMasterKeyFromUser() {
        System.out.println("Please enter your master password:");
        Scanner sc = new Scanner(System.in);

        char[] masterPass = sc.nextLine().trim().toCharArray();
        byte[] salt = SaltGenerator.generateSalt(Constants.PBKDF2_SALT_SIZE);

        SecretKey secretKey = KeyDerivation.deriveKey(masterPass, salt);

        Arrays.fill(masterPass, '\0');

        return secretKey;
    }
}
