package com.treszyk.passwordmanager.core;

import com.treszyk.passwordmanager.crypto.CryptoUtils;
import com.treszyk.passwordmanager.util.Constants;
import com.treszyk.passwordmanager.util.FileUtils;

import javax.crypto.SecretKey;
import java.util.Scanner;

public class VaultManager {
    private final SecretKey masterKey;

    public VaultManager(SecretKey masterKey) {
        this.masterKey = masterKey;
    }

    public void saveEncryptedPassword(String forWhat, String passwordInput) {
        String base64Encrypted = CryptoUtils.encryptPassword(passwordInput, masterKey);
        //it needs to be appended here not overwritten
        //can [-] be used here? or is it usable by Base64
        //it should probably just be written as .json
        FileUtils.writeBase64ToFile(Constants.VAULT_PATH, forWhat + "," + base64Encrypted);
        System.out.println("Password for [" + forWhat + "] got saved.");
    }

    public void readDecryptedPasswords() {
        String pass = FileUtils.readBase64FromFile(Constants.VAULT_PATH);
        String[] splitLine = pass.split(",");

        String forWhat = splitLine[0];
        String encryptedPass = splitLine[1];
        String passIV = splitLine[2];

        String decryptedPass = CryptoUtils.decryptPassword(encryptedPass, masterKey, passIV);
        System.out.println("Decrypted password for [" + forWhat + "]: " + decryptedPass);
    }


    public void addNewPass() {
        Scanner sc = new Scanner(System.in);
        System.out.println("What service would you like a password for?:");
        String whatFor = sc.nextLine().trim();

        System.out.println("Enter the password for [" + whatFor + "]:");
        String pass = sc.nextLine().trim();

        this.saveEncryptedPassword(whatFor, pass);
    }
}
