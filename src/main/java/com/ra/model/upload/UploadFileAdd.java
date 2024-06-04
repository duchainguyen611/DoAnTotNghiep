package com.ra.model.upload;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;


public class UploadFileAdd {

    public static Boolean saveImage(MultipartFile[] files, BindingResult bindingResult) {
        if (files.length == 0) {
            bindingResult.rejectValue("image", "picture null", "Hình ảnh không được trống!");
            return false;
        } else {
            MultipartFile image = files[0];
            if (image.isEmpty()) {
                bindingResult.rejectValue("image", "empty file", "File ảnh rỗng!");
                return false;
            }
            String contentType = image.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                bindingResult.rejectValue("image", "not a picture", "Đây không phải tệp ảnh!");
                return false;
            }
        }
        return true;
    }

}
