# 🔐 Password Manager API

A secure, modern backend for a personal password manager built with **Spring Boot**, **JWT authentication**, and **AES-256-GCM encryption**.  
Designed to be lightweight, stateless, and showcase real-world security practices — built for learning and personal use.

---

## ✅ Features Implemented

### 🧾 User Authentication
- ✅ Stateless **JWT-based login** system
- ✅ Users registered with hashed passwords (BCrypt)
- ✅ Endpoints protected via Spring Security
- ✅ Bearer token authorization via `Authorization: Bearer ...` header

---

### 🔐 Vault Access & Master Key Lifecycle
- ✅ Users register with only a login password
- ✅ Master password is set **after login** and is used to activate vault encryption
- ✅ **PBKDF2-HMAC-SHA256** key derivation with unique per-user salt
- ✅ Vault master key is derived from the master password + stored salt
- ✅ Securely cached **AES-256-GCM encryption key** in memory only — **never persisted or saved to disk**
- ✅ Vault can be:
    - 🔓 Unlocked (key cached temporarily)
    - 🔒 Locked manually
    - ⏳ Auto-locked after inactivity timeout (default: 1 minute)

---

### 🧠 Security Model
- ✅ Encrypted `"vault-check"` string to validate master password correctness (zero-knowledge check)
- ✅ Master key held only in memory (`VaultSessionService`) and auto-expires after inactivity
- ✅ Repeated unlocks are disallowed while vault is already unlocked
- ✅ Vault lock state is inferred through key expiration, not stored in database
- ⚠️ **Note:** Short timeout values (e.g. 1-minute vault key cache, 15-minute JWT token) are configured for development/testing purposes only — actual values should be adjusted for production-grade UX/security balance

---

## 🔧 Tech Stack

- **Java 21**
- **Spring Boot 3.4.4**
    - `spring-boot-starter-web`
    - `spring-boot-starter-security`
    - `spring-boot-starter-data-jpa`
- **JWT** with `jjwt-api`, `jjwt-impl`, `jjwt-jackson` (0.11.5)
- **AES-256-GCM** encryption + **PBKDF2** key derivation
- **H2** in-memory database for dev/testing
- **Springdoc OpenAPI** (`springdoc-openapi-starter-webmvc-ui`) for Swagger UI
- **JUnit** (via `spring-boot-starter-test`) for testing

---

## 📂 Current Endpoints

| Method | Endpoint                         | Description                                      |
|--------|----------------------------------|--------------------------------------------------|
| POST   | `/api/users/register`            | Register new user (login password only)          |
| POST   | `/api/users/login`               | Login, returns JWT token                         |
| POST   | `/api/vault/set-master-password` | Set one-time master password after login         |
| POST   | `/api/vault/unlock`              | Derive & cache master key from master password   |
| POST   | `/api/vault/lock`                | Wipe in-memory master key (manual vault lock)    |
| GET    | `/api/users/me/debug`            | Debug endpoint (returns full user details)       |

---

## 🚧 Coming Soon

- Add and retrieve encrypted password entries
- User-friendly frontend in React.js
- Optional encrypted storage of vault contents
- CSRF protection (for future cookie-based auth flows)

---

## 🛑 Disclaimer

This project is intended for educational and personal use only.  
**Do not use it in production** to store real credentials unless it has been audited and hardened accordingly.

---

## 📸 Author

Made by [@treszyk](https://github.com/treszyk) 
