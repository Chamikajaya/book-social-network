package com.chamika.books_project.utils;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${application.file.upload.photos-directory}")
    private String baseUploadDirectory; // Base directory for file uploads

    /**
     * Save the uploaded file to the server under a user-specific directory.
     *
     * @param fileToUpload the file to be uploaded
     * @param userId       the ID of the user uploading the file
     * @return the path where the file was saved
     */
    public String saveFile(@Nonnull MultipartFile fileToUpload, @Nonnull Integer userId) {
        String userSpecificDirectory = "users" + File.separator + userId; // Directory path specific to the user
        return uploadFile(fileToUpload, userSpecificDirectory);
    }

    /**
     * Uploads the file to the specified subdirectory.
     *
     * @param fileToUpload      the file to be uploaded
     * @param subDirectoryPath  the subdirectory path where the file will be saved
     * @return the path where the file was saved
     */
    private String uploadFile(MultipartFile fileToUpload, String subDirectoryPath) {
        // Construct the full directory path
        String fullDirectoryPath = baseUploadDirectory + File.separator + subDirectoryPath;
        File targetFolder = new File(fullDirectoryPath);

        // Create the directory if it doesn't exist
        if (!targetFolder.exists()) {
            boolean isFolderCreated = targetFolder.mkdirs(); // Recursively create directories
            if (!isFolderCreated) {
                log.warn("Upload folder for this user could not be created.");
                return null;
            }
        }

        // Get the file extension
        String fileExtension = getFileExtension(fileToUpload.getOriginalFilename());
        log.info("File extension type is -> {}", fileExtension);

        // Construct the full file path where the file will be saved ultimately
        String fullFilePath = fullDirectoryPath + File.separator + System.currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(fullFilePath);

        try {
            // Save the file to the target path
            Files.write(targetPath, fileToUpload.getBytes());
            log.info("File saved to " + targetPath);
            return fullFilePath;
        } catch (IOException e) {
            log.error("Could not save the cover image on the server. " + e);
        }

        return null;
    }

    /**
     * Get the file extension from the original filename.
     *
     * @param originalFilename the original filename
     * @return the file extension
     */
    private String getFileExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new RuntimeException("File extension could not be captured, since the originalFilename is not provided");
        }

        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex == -1) {
            return "";
        }
        return originalFilename.substring(dotIndex + 1).toLowerCase();
    }
}
