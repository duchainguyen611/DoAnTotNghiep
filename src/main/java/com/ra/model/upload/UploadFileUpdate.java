package com.ra.model.upload;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileUpdate {
    public static Boolean saveImage(MultipartFile[] files, BindingResult bindingResult) {
        MultipartFile image = files[0];
        if (image.isEmpty()) {
            return true;
        }
        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            bindingResult.rejectValue("image", "not a picture", "Đây không phải tệp ảnh!");
            return false;
        }
        return true;
    }
}
