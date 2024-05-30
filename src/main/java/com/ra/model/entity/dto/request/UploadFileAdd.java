package com.ra.model.entity.dto.request;

import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
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
            assert contentType != null;
            if (!contentType.startsWith("image/")) {
                bindingResult.rejectValue("image", "Not is Picture", "Đây không phải tệp ảnh!");
                return false;
            }
        }
        String fileName = file.getOriginalFilename();
        File saveFile = new ClassPathResource("static/uploads").getFile();
        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
        System.out.println(path);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return true;
    }
}
