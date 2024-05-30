package com.ra.controller.admin;

import com.ra.model.entity.User;
import com.ra.model.entity.dto.request.ChangePasswordDTO;
import com.ra.model.entity.dto.request.ChangeProfileDTO;
import com.ra.model.entity.dto.request.UploadFileUpdate;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.AUserResponseDTO;
import com.ra.model.service.UserService;
import com.ra.security.UserDetail.UserLogin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class InforAdminController {
    private final UserLogin userLogin;
    private final UserService userService;

    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/profile")
    public String profileAdmin(Model model) {
        User user = userLogin.userLogin();
        AUserResponseDTO aUserResponse = userService.AEntityMapResponse(user);
        model.addAttribute("aUserResponse", aUserResponse);
        return "admin/inforAdmin/adminProfile";
    }

    @GetMapping("/updateProfile")
    public String editProfileAdmin(Model model){
        User user = userLogin.userLogin();
        ChangeProfileDTO changeProfile = userService.AEntityMapProfileRequest(user);
        model.addAttribute("changeProfile", changeProfile);
        return "admin/inforAdmin/updateProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfileAdmin(@Valid @ModelAttribute("changeProfile") ChangeProfileDTO changeProfile
            , BindingResult bindingResult
            , @RequestParam("imageAdmin") MultipartFile file
            , RedirectAttributes redirAttrs) throws IOException {
        User user = userLogin.userLogin();
        if (bindingResult.hasErrors() || !UploadFileUpdate.saveImage(file, bindingResult)) {
            return "admin/inforAdmin/updateProfile";
        }
        if (file.isEmpty()) {
            changeProfile.setImage(user.getImage());
        } else {
            String fileName = file.getOriginalFilename();
            changeProfile.setImage(fileName);
        }
        StringError stringError = userService.updateProfile(changeProfile, user);
        if (stringError != null) {
            if (stringError.getEmail() != null)
                bindingResult.rejectValue("email", "email exist", stringError.getEmail());
            if (stringError.getPhone() != null)
                bindingResult.rejectValue("phone", "phone exist", stringError.getPhone());
            return "admin/inforAdmin/updateProfile";
        }
        redirAttrs.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/admin/updateProfile";
    }

    @GetMapping("/updatePassword")
    public String editPasswordAdmin(Model model){
        ChangePasswordDTO changePassword = new ChangePasswordDTO();
        model.addAttribute("changePassword", changePassword);
        return "admin/inforAdmin/updatePassword";
    }

    @PostMapping("/updatePassword")
    public String updatePasswordAdmin(@Valid @ModelAttribute("changePassword") ChangePasswordDTO changePassword
            , BindingResult bindingResult, RedirectAttributes redirAttrs) {
        User user = userLogin.userLogin();
        if (bindingResult.hasErrors()) {
            return "admin/inforAdmin/updatePassword";
        }
        StringError stringError = userService.updatePassword(changePassword, user);
        if (stringError != null) {
            if (stringError.getOldPassword() != null)
                bindingResult.rejectValue("oldPassword", "phone exist", stringError.getOldPassword());
            if (stringError.getNewPassword() != null)
                bindingResult.rejectValue("newPassword", "phone exist", stringError.getNewPassword());
            if (stringError.getConPassword() != null)
                bindingResult.rejectValue("conPassword", "email exist", stringError.getConPassword());
            return "admin/inforAdmin/updatePassword";
        }
        redirAttrs.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/admin/updatePassword";
    }
}
