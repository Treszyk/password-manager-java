// service/VaultSessionService.java
package com.treszyk.passwordmanager.service;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.treszyk.passwordmanager.session.VaultKeyEntry;
import org.springframework.stereotype.Service;

@Service
public class VaultSessionService {
    private static final long TIMEOUT_MILLIS = 1 * 60 * 1000; // 1 minute extend later
    private final Map<Long, VaultKeyEntry> userKeys = new ConcurrentHashMap<>();

    public void cacheKey(Long userId, SecretKey key) { userKeys.put(userId, new VaultKeyEntry(key)); }
    public void removeKey(Long userId) { userKeys.remove(userId); }
    public boolean hasKey(Long userId) { return userKeys.containsKey(userId); }

    public SecretKey getKey(Long userId) {
        VaultKeyEntry entry = getValidEntryOrRemove(userId);
        return entry != null ? entry.getKey() : null;
    }

    public boolean isVaultUnlocked(Long userId) {
        return getValidEntryOrRemove(userId) != null;
    }

    private VaultKeyEntry getValidEntryOrRemove(Long userId) {
        VaultKeyEntry entry = userKeys.get(userId);
        if (entry == null) return null;

        if (System.currentTimeMillis() - entry.getTimestamp() > TIMEOUT_MILLIS) {
            userKeys.remove(userId);
            return null;
        }

        return entry;
    }



}
