package com.treszyk.passwordmanager;

import com.treszyk.passwordmanager.core.MasterPasswordHandler;
import com.treszyk.passwordmanager.util.FileUtils;

import javax.crypto.SecretKey;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        FileUtils.createDirectoryIfNotExists("vault");
        System.out.println("Welcome!");
        MasterPasswordHandler masterPasswordHandler = new MasterPasswordHandler();
        SecretKey masterKey = masterPasswordHandler.getMasterKeyFromUser();
        byte[] keyBytes = masterKey.getEncoded();
        System.out.print(Base64.getEncoder().encodeToString(keyBytes));
        //KeyDerivation.Example();
    }
}