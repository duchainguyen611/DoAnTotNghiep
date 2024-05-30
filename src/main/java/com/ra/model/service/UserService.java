package com.ra.model.service;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.User;
import com.ra.model.entity.dto.request.ChangePasswordDTO;
import com.ra.model.entity.dto.request.ChangeProfileDTO;
import com.ra.model.entity.dto.request.user.RegisterDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.AUserResponseDTO;

import java.util.List;

public interface UserService {
    List<User> getAllUserExceptAdmin();
    User findById(Long id);
    void register(RegisterDTO register);
    List<AUserResponseDTO> findAllByStatus(ActiveStatus status);
    void changeStatus(Long id);
    User update(User user);
    StringError updateProfile(ChangeProfileDTO changeProfile, User user);
    StringError updatePassword(ChangePasswordDTO changePassword, User user);
    AUserResponseDTO AEntityMapResponse(User user);
    User AEntityMapProfileRequest(ChangeProfileDTO changeProfile, User user);
    ChangeProfileDTO AEntityMapProfileRequest(User user);
}
