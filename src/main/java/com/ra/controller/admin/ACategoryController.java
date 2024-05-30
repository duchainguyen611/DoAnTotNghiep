package com.ra.controller.admin;

import com.ra.model.entity.Category;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.dto.request.admin.ACategoryRequestDTO;
import com.ra.model.entity.dto.request.admin.ACategoryUpdateRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.ACategoryResponseDTO;
import com.ra.model.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ACategoryController {
    private final CategoryService categoryService;

    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/category")
    public String getAllByStatusActivated(Model model) {
        List<ACategoryResponseDTO> categories = categoryService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories", categories);
        return "admin/category/mainCategory";
    }

    @GetMapping("/disabledCategory")
    public String getAllByStatusDisabled(Model model) {
        List<ACategoryResponseDTO> categories = categoryService.AFindAllByStatus(ActiveStatus.INACTIVE);
        model.addAttribute("categories", categories);
        return "admin/category/disabledCategory";
    }


    @GetMapping("/addCategory")
    public String add(Model model) {
        ACategoryRequestDTO aCategoryRequest = new ACategoryRequestDTO();
        model.addAttribute("aCategoryRequest", aCategoryRequest);
        return "admin/category/addCategory";
    }

    @PostMapping("/addCategory")
    public String save(@Valid @ModelAttribute("aCategoryRequest") ACategoryRequestDTO aCategoryRequest
            , BindingResult bindingResult, RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()){
            return "admin/category/addCategory";
        }
        categoryService.addCategory(aCategoryRequest);
        redirAttrs.addFlashAttribute("success", "Thêm thành công!");
        return "redirect:/admin/category";
    }

    @GetMapping("/updateCategory/{id}")
    public String update(Model model, @PathVariable Long id) {
        Category category = categoryService.findById(id);
        ACategoryUpdateRequestDTO aCategoryUpdateRequest = categoryService.AEntityMapRequest(category);
        model.addAttribute("aCategoryUpdateRequest", aCategoryUpdateRequest);
        return "admin/category/updateCategory";
    }

    @PostMapping("/updateCategory/{id}")
    public String edit(@Valid @ModelAttribute("aCategoryUpdateRequest") ACategoryUpdateRequestDTO aCategoryUpdateRequest
            , BindingResult bindingResult,@PathVariable Long id,  RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()){
            return "admin/category/updateCategory";
        }
        StringError stringError = categoryService.updateCategory(aCategoryUpdateRequest,id);
        if (stringError!=null){
            if (stringError.getName()!=null){
                bindingResult.rejectValue("categoryName", "name exist",stringError.getName());
                return "admin/category/updateCategory";
            }
        }
        redirAttrs.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/admin/category";
    }
    @ResponseBody
    @GetMapping("/changeStatusCategory/{id}")
    public ACategoryResponseDTO changeStatus(@PathVariable Long id){
        categoryService.changeStatus(id);
        return categoryService.AEntityMapResponse(categoryService.findById(id));
    }

    @GetMapping("/deleteCategory/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirAttrs) {
        categoryService.delete(id);
        redirAttrs.addFlashAttribute("success", "Xóa thành công!");
        return "redirect:/admin/disabledCategory";
    }
}
