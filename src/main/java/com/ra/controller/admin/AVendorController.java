package com.ra.controller.admin;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Vendor;
import com.ra.model.entity.dto.request.UploadFileAdd;
import com.ra.model.entity.dto.request.UploadFileUpdate;
import com.ra.model.entity.dto.request.admin.AVendorRequestDTO;
import com.ra.model.entity.dto.request.admin.AVendorUpdateRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.AVendorResponseDTO;
import com.ra.model.service.VendorService;
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
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AVendorController {
    private final VendorService vendorService;

    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/vendor")
    public String getAllByActivated(Model model) {
        List<AVendorResponseDTO> vendors = vendorService.findAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("vendors", vendors);
        return "admin/vendor/mainVendor";
    }

    @GetMapping("/disabledVendor")
    public String getAllByDisabled(Model model) {
        List<AVendorResponseDTO> vendors = vendorService.findAllByStatus(ActiveStatus.INACTIVE);
        model.addAttribute("vendors", vendors);
        return "admin/vendor/disabledVendor";
    }

    @GetMapping(value = "/addVendor")
    public String add(Model model) {
        AVendorRequestDTO aVendorRequest = new AVendorRequestDTO();
        model.addAttribute("aVendorRequest", aVendorRequest);
        return "admin/vendor/addVendor";
    }

    @PostMapping(value = "/addVendor")
    public String save(@Valid @ModelAttribute("aVendorRequest") AVendorRequestDTO aVendorRequest
            , BindingResult bindingResult
            ,  RedirectAttributes redirAttrs
            , @RequestParam("imageVendor") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors() || !UploadFileAdd.saveImage(file, bindingResult)) {
            return "admin/vendor/addVendor";
        }
        String fileName = file.getOriginalFilename();
        aVendorRequest.setImage(fileName);
        vendorService.addVendor(aVendorRequest);
        redirAttrs.addFlashAttribute("success", "Thêm thành công!");
        return "redirect:/admin/vendor";
    }

    @GetMapping(value = "/updateVendor/{id}")
    public String update(Model model, @PathVariable Long id) {
        Vendor vendor = vendorService.findById(id);
        AVendorUpdateRequestDTO aVendorUpdateRequest = vendorService.AEntityMapRequest(vendor);
        model.addAttribute("aVendorUpdateRequest", aVendorUpdateRequest);
        return "admin/vendor/updateVendor";
    }

    @PostMapping(value = "/updateVendor/{id}")
    public String edit(@Valid @ModelAttribute("aVendorUpdateRequest") AVendorUpdateRequestDTO aVendorUpdateRequest
            , BindingResult bindingResult
            , @RequestParam("imageVendor") MultipartFile file
            , @PathVariable Long id
            ,  RedirectAttributes redirAttrs) throws IOException {
        if (bindingResult.hasErrors() || !UploadFileUpdate.saveImage(file, bindingResult)) {
            return "admin/vendor/updateVendor";
        }
        if (file.isEmpty()) {
            aVendorUpdateRequest.setImage(vendorService.findById(id).getImage());
        } else {
            String fileName = file.getOriginalFilename();
            aVendorUpdateRequest.setImage(fileName);
        }
        StringError stringError = vendorService.updateVendor(aVendorUpdateRequest,id);
        if(stringError!=null){
            if (stringError.getEmail()!=null) bindingResult.rejectValue("email", "email exist",stringError.getEmail());
            if (stringError.getName()!=null) bindingResult.rejectValue("vendorName", "name exist",stringError.getName());
            if (stringError.getPhone()!=null) bindingResult.rejectValue("phone", "phone exist",stringError.getPhone());
            return "admin/vendor/updateVendor";
        }
        redirAttrs.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/admin/vendor";
    }

    @ResponseBody
    @GetMapping("changeStatusVendor/{id}")
    public AVendorResponseDTO changeStatus(@PathVariable Long id) {
        vendorService.changeStatus(id);
        return vendorService.AEntityMapResponse(vendorService.findById(id));
    }

    @GetMapping("/deleteVendor/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes redirAttrs) {
        vendorService.delete(id);
        redirAttrs.addFlashAttribute("success", "Xóa thành công!");
        return "redirect:/admin/disabledVendor";
    }
}
