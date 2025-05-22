package com.treszyk.passwordmanager.session;

import javax.crypto.SecretKey;

public class VaultKeyEntry {
    private final SecretKey key;
    private final long timestamp;

    public VaultKeyEntry(SecretKey key) {
        this.key = key;
        this.timestamp = System.currentTimeMillis();
    }

    public SecretKey getKey() {
        return key;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
