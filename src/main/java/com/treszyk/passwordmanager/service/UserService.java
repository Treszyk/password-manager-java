package com.treszyk.passwordmanager.service;

import com.treszyk.passwordmanager.model.User;
import com.treszyk.passwordmanager.repository.UserRepository;
import com.treszyk.passwordmanager.util.Constants;
import com.treszyk.passwordmanager.util.SaltGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public User registerUser(String username, String rawPassword) {
        String hashedPassword = passwordEncoder.encode(rawPassword);

        byte[] salt = SaltGenerator.generateSalt(Constants.PBKDF2_SALT_SIZE);
        String base64Salt = Base64.getEncoder().encodeToString(salt);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setMasterSalt(base64Salt);
        user.setMasterPasswordSet(false);

        return userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}