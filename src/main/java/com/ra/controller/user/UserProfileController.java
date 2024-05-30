package com.ra.controller.user;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.User;
import com.ra.model.entity.dto.request.ChangePasswordDTO;
import com.ra.model.entity.dto.request.ChangeProfileDTO;
import com.ra.model.entity.dto.request.user.AddressRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.user.AddressResponseDTO;
import com.ra.model.entity.dto.response.user.CartResponseDTO;
import com.ra.model.entity.dto.response.user.UCategoryResponseDTO;
import com.ra.model.entity.dto.response.user.UInvoiceResponseDTO;
import com.ra.model.service.*;
import com.ra.security.UserDetail.UserLogin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final InvoiceDetailService invoiceDetailService;
    private final InvoiceService invoiceService;
    private final UserService userService;
    private final UserLogin userLogin;
    private final AddressService addressService;
    private final ShoppingCartService shoppingCartService;
    private final CategoryService categoryService;
    @GetMapping("")
    public String profileUser(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader = carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart : carts) {
            sumPriceHeader += (cart.getUnitPrice() * cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader", NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        model.addAttribute("user", user);
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/profile/profile";
    }

    @GetMapping("/invoice")
    public String historyInvoice(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader = carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart : carts) {
            sumPriceHeader += (cart.getUnitPrice() * cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader", NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        List<UInvoiceResponseDTO> invoiceList = invoiceService.displayAllByUser(user);
        model.addAttribute("invoiceList", invoiceList);
        model.addAttribute("user", user);
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/profile/historyInvoice";
    }

    @GetMapping("/updatePassword")
    public String changePassword(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader = carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart : carts) {
            sumPriceHeader += (cart.getUnitPrice() * cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader", NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        ChangePasswordDTO changePassword = new ChangePasswordDTO();
        model.addAttribute("changePassword", changePassword);
        model.addAttribute("user", user);
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/profile/updatePassword";
    }

    @GetMapping("/address")
    public String address(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader = carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart : carts) {
            sumPriceHeader += (cart.getUnitPrice() * cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader", NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        AddressRequestDTO addressRequest = new AddressRequestDTO();
        model.addAttribute("addressRequest", addressRequest);
        List<AddressResponseDTO> addresses = addressService.displayAllByUser(user);
        model.addAttribute("addresses", addresses);
        model.addAttribute("user", user);
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/profile/address";
    }

    @GetMapping("/updateProfile")
    public String editProfileAdmin(Model model,@RequestParam(name = "keyword", required = false) String keyword){
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader = carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart : carts) {
            sumPriceHeader += (cart.getUnitPrice() * cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader", NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        model.addAttribute("user", user);
        model.addAttribute("keyword", keyword);
        ChangeProfileDTO changeProfile = userService.AEntityMapProfileRequest(user);
        model.addAttribute("changeProfile", changeProfile);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/profile/updateProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfileAdmin(@Valid @ModelAttribute("changeProfile") ChangeProfileDTO changeProfile
            , BindingResult bindingResult
            , RedirectAttributes redirAttrs) throws IOException {
        User user = userLogin.userLogin();
        changeProfile.setImage(user.getImage());
        if (bindingResult.hasErrors()) {
            return "home/user/profile/updateProfile";
        }
        StringError stringError = userService.updateProfile(changeProfile, user);
        if (stringError != null) {
            if (stringError.getEmail() != null)
                bindingResult.rejectValue("email", "email exist", stringError.getEmail());
            if (stringError.getPhone() != null)
                bindingResult.rejectValue("phone", "phone exist", stringError.getPhone());
            return "home/user/profile/updateProfile";
        }
        redirAttrs.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/user/profile/updateProfile";
    }

    @PostMapping("/addAddress")
    public String addAddress(@Valid @ModelAttribute("addressRequest") AddressRequestDTO addressRequest) {
        User user = userLogin.userLogin();
        addressRequest.setUser(user);
        addressService.addAddress(addressRequest);
        return "redirect:/user/profile/address";
    }

    @PostMapping("/updateAddress/{id}")
    public String updateAddress(
            @RequestParam("FullNameUpdate") String fullNameUpdate
            , @RequestParam("PhoneUpdate") String phoneUpdate
            , @RequestParam("Address1Update") String address1Update
            , @RequestParam("Address2Update") String address2Update
            , @RequestParam("Address3Update") String address3Update
            , @RequestParam("Address4Update") String address4Update
            , @PathVariable Long id,Model model) {
        AddressRequestDTO addressRequest = new AddressRequestDTO();
        addressRequest.setReceiveName(fullNameUpdate);
        addressRequest.setReceivePhone(phoneUpdate);
        addressRequest.setAddress1(address1Update);
        addressRequest.setAddress2(address2Update);
        addressRequest.setAddress3(address3Update);
        addressRequest.setAddress4(address4Update);
        addressService.updateAddress(addressRequest, id);
        return "redirect:/user/profile/address";
    }


    @GetMapping("/deleteAddress/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressService.delete(id);
        return "redirect:/user/profile/address";
    }


    @PostMapping("/updatePassword")
    public String updatePasswordAdmin(@Valid @ModelAttribute("changePassword") ChangePasswordDTO changePassword
            , BindingResult bindingResult, Model model , RedirectAttributes redirAttrs) {
        User user = userLogin.userLogin();
        model.addAttribute("user", user);
        if (bindingResult.hasErrors()) {
            return "home/user/profile/updatePassword";
        }
        StringError stringError = userService.updatePassword(changePassword, user);
        if (stringError != null) {
            if (stringError.getOldPassword() != null)
                bindingResult.rejectValue("oldPassword", "phone exist", stringError.getOldPassword());
            if (stringError.getNewPassword() != null)
                bindingResult.rejectValue("newPassword", "phone exist", stringError.getNewPassword());
            if (stringError.getConPassword() != null)
                bindingResult.rejectValue("conPassword", "email exist", stringError.getConPassword());
            return "home/user/profile/updatePassword";
        }
        redirAttrs.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect: /user/profile/updatePassword";
    }


}
