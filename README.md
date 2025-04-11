# Password Manager (Java + React(Future Plan))

A secure password management application that stores and retrieves user passwords using strong encryption techniques. The project leverages **PBKDF2** for key derivation and AES in **GCM mode** for encryption. It securely handles passwords by deriving a master key from the user’s password, storing a salt for encryption, and generating random initialization vectors (IVs) for each encryption operation.

## Key Features:
- **Password Encryption**: Uses AES encryption in GCM mode with a unique IV for each password.
- **Key Derivation**: Generates strong keys using PBKDF2 with a salt and high iteration count.
- **Web Interface (Future Plan)**: A React-based frontend will be implemented for easy password management.
- **Account Authentication (Future Plan)**: User account authentication will be integrated to allow login and password management.
