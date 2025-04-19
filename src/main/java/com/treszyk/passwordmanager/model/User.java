package com.treszyk.passwordmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String masterSalt;

    @Column(nullable = false)
    private boolean masterPasswordSet = false;

    @Column(nullable = true)
    private String vaultCheckEncrypted;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        System.out.println("JUST A CHECK:" + password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMasterSalt() {
        return masterSalt;
    }

    public void setMasterSalt(String masterSalt) {
        this.masterSalt = masterSalt;
    }

    public boolean isMasterPasswordSet() {
        return masterPasswordSet;
    }

    public void setMasterPasswordSet(boolean masterPasswordSet) {
        this.masterPasswordSet = masterPasswordSet;
    }

    public String getVaultCheckEncrypted() {
        return vaultCheckEncrypted;
    }

    public void setVaultCheckEncrypted(String vaultCheckEncrypted) {
        this.vaultCheckEncrypted = vaultCheckEncrypted;
    }
}