package com.treszyk.passwordmanager;

import com.treszyk.passwordmanager.core.MasterPasswordHandler;
import com.treszyk.passwordmanager.core.VaultManager;
import com.treszyk.passwordmanager.crypto.KeyDerivation;
import com.treszyk.passwordmanager.util.FileUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileUtils.createDirectoryIfNotExists("vault");
        Scanner sc = new Scanner(System.in);
        VaultManager vm;
        System.out.println("Welcome!");

        MasterPasswordHandler masterPasswordHandler = new MasterPasswordHandler();
        SecretKey masterKey = masterPasswordHandler.getMasterKeyFromUser();
        vm = new VaultManager(masterKey);
        byte[] keyBytes = masterKey.getEncoded();

        System.out.println("Encrypted master key: " + Base64.getEncoder().encodeToString(keyBytes));

        while(true) {
            System.out.println("[0] - exit \n" +
                               "[1] - add a new password\n" +
                               "[2] - decrypt a password\n" +
                               "[3] - clear saved passwords\n" +
                               "[4] - generate a new salt(DESTRUCTIVE ACTION!!!)\n" +
                               "[5] - get all encrypted passwords\n" +
                               "[6] - get all decrypted passwords");
            String choice = sc.nextLine().trim();

            if(choice.equals("0"))
                break;

            switch(choice) {
                case "1": vm.addNewPass(); break;
                default: System.out.println("There is no such option!");
            }
        }

        System.out.println("Byebye");
        //KeyDerivation.Example();
    }
}