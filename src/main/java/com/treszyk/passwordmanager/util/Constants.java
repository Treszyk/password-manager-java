package com.treszyk.passwordmanager.util;

public class Constants {
    public static final String VAULT_PATH = "vault/pass.dat";
    public static final String SALT_PATH = "vault/salt.dat";

    public static final String ENCRYPTION_ALGORITHM = "AES";
    public static final String KEY_DERIVATION_ALGORITHM = "PBKDF2WithHmacSHA256";
    public static final String HASHING_ALGORITHM = "SHA-256";

    public static final int GCM_TAG_LENGTH = 128;

    public static final int PBKDF2_ITERATIONS = 100000;
    public static final int PBKDF2_SALT_SIZE = 16;
    public static final int PBKDF2_KEY_SIZE = 256;

    public static final String CIPHER_TRANSFORMATION = "AES/GCM/NoPadding";

    public static final int AES_IV_SIZE = 16;
}
