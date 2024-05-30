package com.ra.model.serviceImp;

import com.ra.model.entity.Address;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.ENUM.RoleName;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.model.entity.dto.request.ChangePasswordDTO;
import com.ra.model.entity.dto.request.ChangeProfileDTO;
import com.ra.model.entity.dto.request.user.RegisterDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.AUserResponseDTO;
import com.ra.model.repository.UserRepository;
import com.ra.model.service.AddressService;
import com.ra.model.service.RoleService;
import com.ra.model.service.UserService;
import com.ra.validator.user.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AddressService addressService;

    @Override
    public List<User> getAllUserExceptAdmin() {
        return userRepository.getAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void register(RegisterDTO register) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        User newUser = User.builder()
                .fullName(register.getFullName())
                .email(register.getEmail())
                .phone(register.getPhone())
                .username(register.getUsername())
                .image("img.png")
                .password(passwordEncoder.encode(register.getPassword()))
                .status(ActiveStatus.ACTIVE)
                .roles(roles).
                build();
        userRepository.save(newUser);
        Address newAddress = Address.builder()
                .user(newUser)
                .receiveAddress(newUser.getAddress())
                .receiveName(newUser.getFullName())
                .receivePhone(newUser.getPhone())
                .build();
        addressService.save(newAddress);
    }

    @Override
    public List<AUserResponseDTO> findAllByStatus(ActiveStatus status) {
        return userRepository.findAllByStatus(status.toString()).stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public void changeStatus(Long id) {
        User user = findById(id);
        if (user.getStatus().equals(ActiveStatus.ACTIVE)){
            user.setStatus(ActiveStatus.INACTIVE);
        }else {
            user.setStatus(ActiveStatus.ACTIVE);
        }
        userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public StringError updateProfile(ChangeProfileDTO changeProfile, User userOld) {
        User userNew = AEntityMapProfileRequest(changeProfile,userOld);
        userNew.setCreatedDate(userOld.getCreatedDate());
        userNew.setId(userOld.getId());
        boolean isEmailExist = userRepository.existsByEmail(userNew.getEmail())&&!userOld.getEmail().equals(userNew.getEmail());
        boolean isPhoneExist = userRepository.existsByPhone(userNew.getPhone())&&!userOld.getPhone().equals(userNew.getPhone());
        StringError stringError = new StringError();
        boolean check = true;
        if (isEmailExist){
            stringError.setEmail("Email này đã được sử dụng!");
            check = false;
        }
        if (isPhoneExist){
            stringError.setPhone("Số điện thoại này đã được sử dụng!");
            check = false;
        }
        if (!check){
            return stringError;
        }
        update(userNew);
        return null;
    }

    @Override
    public StringError updatePassword(ChangePasswordDTO changePassword, User user) {
        StringError stringError = new StringError();
        boolean check = true;
        if (!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
            stringError.setOldPassword("Mật khẩu cũ không đúng!");
            check = false;
        }
        if (changePassword.getOldPassword().equals(changePassword.getNewPassword())) {
            stringError.setNewPassword("Mật khẩu mới không được giống mật khẩu cũ!");
            check = false;
        }
        if (!changePassword.getNewPassword().equals(changePassword.getConPassword())) {
            stringError.setConPassword("Xác nhận mật khẩu không khớp!");
            check = false;
        }
        String errorNewPassword = PasswordValidator.passwordValidation(changePassword.getNewPassword());
        if (errorNewPassword != null) {
            stringError.setNewPassword(errorNewPassword);
            check = false;
        }
        if (!check){
            return stringError;
        }
        user.setPassword(passwordEncoder.encode(changePassword.getConPassword()));
        update(user);
        return null;
    }

    @Override
    public AUserResponseDTO AEntityMapResponse(User user) {
        return AUserResponseDTO.builder()
                .id(user.getId())
                .dateOfBirth(user.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .image(user.getImage())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .gender(user.getGender().toString())
                .address(user.getAddress())
                .username(user.getUsername())
                .createdDate(user.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .modifyDate(user.getModifyDate()==null?null:user.getModifyDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .status(user.getStatus().toString())
                .build();
    }

    @Override
    public User AEntityMapProfileRequest(ChangeProfileDTO changeProfile, User user) {
        return User.builder()
                .email(changeProfile.getEmail())
                .image(changeProfile.getImage())
                .fullName(changeProfile.getFullName())
                .phone(changeProfile.getPhone())
                .address(changeProfile.getAddress())
                .dateOfBirth(LocalDate.parse(changeProfile.getDateOfBirth()))
                .gender(changeProfile.getGender())
                .username(user.getUsername())
                .password(user.getPassword())
                .status(user.getStatus())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public ChangeProfileDTO AEntityMapProfileRequest(User user) {
        return ChangeProfileDTO.builder()
                .email(user.getEmail())
                .image(user.getImage())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .dateOfBirth(String.valueOf(user.getDateOfBirth()))
                .gender(user.getGender())
                .build();
    }


}
