package com.ra.controller.admin;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.dto.response.admin.AUserResponseDTO;
import com.ra.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AUserManagerController {
    private final UserService userService;

    @GetMapping("/user")
    public String getAllUserActive(Model model) {
        List<AUserResponseDTO> users = userService.findAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("users", users);
        return "admin/users/mainUser";
    }

    @GetMapping("/inactiveUser")
    public String getAllUserInactive(Model model) {
        List<AUserResponseDTO> users = userService.findAllByStatus(ActiveStatus.INACTIVE);
        model.addAttribute("users", users);
        return "admin/users/inactiveUser";
    }

    @ResponseBody
    @GetMapping("/changeStatusUser/{id}")
    public AUserResponseDTO changeStatus(@PathVariable Long id) {
        userService.changeStatus(id);
        return userService.AEntityMapResponse(userService.findById(id));
    }
}
