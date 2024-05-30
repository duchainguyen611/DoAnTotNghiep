package com.ra.controller.admin;

import com.ra.model.entity.Brand;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.dto.request.UploadFileAdd;
import com.ra.model.entity.dto.request.UploadFileUpdate;
import com.ra.model.entity.dto.request.admin.ABrandUpdateRequestDTO;
import com.ra.model.entity.dto.request.admin.ABrandRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.ABrandResponseDTO;
import com.ra.model.service.BrandService;
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
public class ABrandController {
    private final BrandService brandService;

    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/brand")
    public String getAllByStatusActivated(Model model) {
        List<ABrandResponseDTO> brands = brandService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("brands", brands);
        return "admin/brand/mainBrand";
    }

    @GetMapping("/disabledBrand")
    public String getAllByStatusDisabled(Model model) {
        List<ABrandResponseDTO> brands = brandService.AFindAllByStatus(ActiveStatus.INACTIVE);
        model.addAttribute("brands", brands);
        return "admin/brand/disabledBrand";
    }

    @GetMapping("/addBrand")
    public String add(Model model) {
        ABrandRequestDTO aBrandRequest = new ABrandRequestDTO();
        model.addAttribute("aBrandRequest", aBrandRequest);
        return "admin/brand/addBrand";
    }
    @PostMapping("/addBrand")
    public String save(@Valid @ModelAttribute("aBrandRequest") ABrandRequestDTO aBrandRequest,
                       BindingResult bindingResult,
                       @RequestParam("imageBrand") MultipartFile file,
                       RedirectAttributes redirAttrs) throws IOException {
        if (bindingResult.hasErrors() || !UploadFileAdd.saveImage(file,bindingResult)) {
            return "admin/brand/addBrand";
        }
        String fileName = file.getOriginalFilename();
        aBrandRequest.setImage(fileName);
        brandService.addBrand(aBrandRequest);
        redirAttrs.addFlashAttribute("success", "Thêm thành công!");
        return "redirect:/admin/brand";
    }

    @GetMapping("/updateBrand/{id}")
    public String update(Model model, @PathVariable Long id) {
        Brand brand = brandService.findById(id);
        ABrandUpdateRequestDTO aBrandUpdateRequest = brandService.AEntityMapRequest(brand);
        model.addAttribute("aBrandUpdateRequest", aBrandUpdateRequest);
        return "admin/brand/updateBrand";
    }

    @PostMapping("/updateBrand/{id}")
    public String edit(@Valid @ModelAttribute("aBrandUpdateRequest") ABrandUpdateRequestDTO aBrandUpdateRequest
            , BindingResult bindingResult
            , @RequestParam("imageBrand") MultipartFile file
            , @PathVariable Long id,  RedirectAttributes redirAttrs) throws IOException {
        if (bindingResult.hasErrors() || ! UploadFileUpdate.saveImage(file,bindingResult)) {
            return "admin/brand/updateBrand";
        }
        if (file.isEmpty()){
            aBrandUpdateRequest.setImage(brandService.findById(id).getImage());
        }else {
            String fileName = file.getOriginalFilename();
            aBrandUpdateRequest.setImage(fileName);
        }
        StringError stringError = brandService.updateBrand(aBrandUpdateRequest,id);
        if(stringError!=null){
            if (stringError.getEmail()!=null) bindingResult.rejectValue("email", "email exist",stringError.getEmail());
            if (stringError.getName()!=null) bindingResult.rejectValue("brandName", "name exist",stringError.getName());
            if (stringError.getPhone()!=null) bindingResult.rejectValue("phone", "phone exist",stringError.getPhone());
            return "admin/brand/updateBrand";
        }
        redirAttrs.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/admin/brand";
    }

    @ResponseBody
    @GetMapping("/changeStatusBrand/{id}")
    public ABrandResponseDTO changeStatus(@PathVariable Long id) {
        brandService.changeStatus(id);
        return brandService.AEntityMapResponse(brandService.findById(id));
    }


    @GetMapping("/deleteBrand/{id}")
    public String delete(@PathVariable Long id,  RedirectAttributes redirAttrs) {
        brandService.delete(id);
        redirAttrs.addFlashAttribute("success", "Xóa thành công!");
        return "redirect:/admin/disabledBrand";
    }

}
