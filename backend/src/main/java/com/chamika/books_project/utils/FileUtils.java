package com.chamika.books_project.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {

    /**
     * Reads the file from the specified location and returns its contents as a byte array.
     *
     * @param filePath the path to the file to be read
     * @return the contents of the file as a byte array, or null if the filePath is null/empty or an error occurs
     */
    public static byte[] readFileFromLocation(String filePath) {
        // Check if the file path is valid
        if (filePath == null || filePath.isEmpty()) {
            log.warn("Invalid file path: null or empty");
            return null;
        }

        try {
            // Convert the file path string to a Path object
            Path path = new File(filePath).toPath();
            // Read the file's contents as a byte array
            return Files.readAllBytes(path);
        } catch (IOException e) {
            // Log a warning message if an error occurs while reading the file
            log.warn("Error occurred while reading the file from the given location: " + filePath, e);
        }

        return null;
    }
}
