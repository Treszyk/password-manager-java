package com.treszyk.passwordmanager.controller;

import com.treszyk.passwordmanager.crypto.CryptoUtils;
import com.treszyk.passwordmanager.crypto.KeyDerivation;
import com.treszyk.passwordmanager.dto.SetMasterPasswordRequest;
import com.treszyk.passwordmanager.dto.UnlockVaultRequest;
import com.treszyk.passwordmanager.model.User;
import com.treszyk.passwordmanager.service.UserService;
import com.treszyk.passwordmanager.service.VaultSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;

import static com.treszyk.passwordmanager.crypto.CryptoUtils.decryptPassword;

@RestController
@RequestMapping("/api/vault")
public class VaultController {

    private final VaultSessionService vaultSessionService;
    private final UserService userService;

    public VaultController(VaultSessionService vaultSessionService, UserService userService) {
        this.vaultSessionService = vaultSessionService;
        this.userService = userService;
    }

    @PostMapping("/unlock")
    public ResponseEntity<?> unlockVault(@RequestBody UnlockVaultRequest request,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = user.getId();

        if (!user.isMasterPasswordSet()) {
            return ResponseEntity.status(400).body("Master password has not been set.");
        }

        if (vaultSessionService.isVaultUnlocked(userId)) {
            return ResponseEntity.status(400).body("Vault is already unlocked.");
        }

        byte[] salt = Base64.getDecoder().decode(user.getMasterSalt());
        SecretKey derivedKey  = KeyDerivation.deriveKey(request.masterPassword().toCharArray(), salt);

        String encryptedCheck = user.getVaultCheckEncrypted();
        if (!CryptoUtils.isCorrectVaultKey(encryptedCheck, derivedKey)) {
            return ResponseEntity.status(403).body("Invalid master password");
        }

        vaultSessionService.cacheKey(userId, derivedKey );

        return ResponseEntity.ok("Vault unlocked for user: " + user.getUsername() + ".");
    }

    @PostMapping("/lock")
    public ResponseEntity<?> lockVault(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!vaultSessionService.hasKey(user.getId())) {
            return ResponseEntity.status(403).body("Vault is already locked.");
        }

        vaultSessionService.removeKey(user.getId());

        return ResponseEntity.ok("Vault locked.");
    }

    @PostMapping("/set-master-password")
    public ResponseEntity<?> setMasterPassword(@RequestBody SetMasterPasswordRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = user.getId();

        if (user.isMasterPasswordSet()) {
            return ResponseEntity.status(400).body("Master password already set");
        }

        byte[] salt = Base64.getDecoder().decode(user.getMasterSalt());

        SecretKey masterKey = KeyDerivation.deriveKey(request.masterPassword().toCharArray(), salt);

        vaultSessionService.cacheKey(userId, masterKey);

        String verificationText = "vault-check";
        String encryptedCheck = CryptoUtils.encryptPassword(verificationText, masterKey);
        user.setVaultCheckEncrypted(encryptedCheck);

        user.setMasterPasswordSet(true);
        userService.save(user);

        return ResponseEntity.ok("Master password set and cached.");
    }
}
