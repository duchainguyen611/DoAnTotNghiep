package com.ra.model.upload;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UploadFileAdd {
    public static Boolean saveImage(MultipartFile file, BindingResult bindingResult) throws IOException {
        if (file.isEmpty()) {
            bindingResult.rejectValue("image", "picture null", "Hình ảnh không được trống!");
            return false;
        } else {
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                bindingResult.rejectValue("image", "Not is Picture", "Đây không phải tệp ảnh!");
                return false;
            }
        }

        String fileName = file.getOriginalFilename();
        // Ensure the static/uploads directory exists
        Path directoryPath = Paths.get("src/main/resources/static/uploads");
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        Path filePath = directoryPath.resolve(fileName);
        System.out.println(filePath);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return true;
    }
}
