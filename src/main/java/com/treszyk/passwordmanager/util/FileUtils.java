package com.treszyk.passwordmanager.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class FileUtils {
    public static void createDirectoryIfNotExists(String path) {
        Path dirPath = Paths.get(path);
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                System.err.println("Failed to create directory: " + e.getMessage());
            }
        }
    }

    public static boolean fileExists(String path) {
        Path filePath = Paths.get(path);
        return Files.exists(filePath);
    }

    public static void writeByteArrayToFile(String path, byte[] array) {
        Path filePath = Paths.get(path);
        try {
            Files.write(filePath, array);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static byte[] readByteArrayFromFile(String path) {
        Path filePath = Paths.get(path);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeBase64ToFile(String path, String base64) {
        Path filePath = Paths.get(path);
        try {
            Files.writeString(filePath, base64);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static String readBase64FromFile(String path) {
        Path filePath = Paths.get(path);
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
