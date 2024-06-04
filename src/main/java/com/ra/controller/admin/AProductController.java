package com.ra.controller.admin;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Product;
import com.ra.model.firebase.IImageService;
import com.ra.model.upload.UploadFileAdd;
import com.ra.model.upload.UploadFileUpdate;
import com.ra.model.entity.dto.request.admin.AProductRequestDTO;
import com.ra.model.entity.dto.request.admin.AProductUpdateRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.ABrandResponseDTO;
import com.ra.model.entity.dto.response.admin.ACategoryResponseDTO;
import com.ra.model.entity.dto.response.admin.AProductDetailResponseDTO;
import com.ra.model.entity.dto.response.admin.AProductResponseDTO;
import com.ra.model.service.BrandService;
import com.ra.model.service.CategoryService;
import com.ra.model.service.ProductService;
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
public class AProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final IImageService imageService;

    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/product")
    public String getAllProductActivated(Model model) {
        List<AProductResponseDTO> products = productService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("products", products);
        return "admin/product/mainProduct";
    }

    @GetMapping("/disabledProduct")
    public String getAllProductDisabled(Model model) {
        List<AProductResponseDTO> products = productService.AFindAllByStatus(ActiveStatus.INACTIVE);
        model.addAttribute("products", products);
        return "admin/product/disabledProduct";
    }

    @GetMapping("/addProduct")
    public String add(Model model) {
        List<ACategoryResponseDTO> categories = categoryService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories", categories);
        List<ABrandResponseDTO> brands = brandService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("brands", brands);
        AProductRequestDTO aProductRequest = new AProductRequestDTO();
        model.addAttribute("aProductRequest", aProductRequest);
        return "admin/product/addProduct";
    }

    @PostMapping("/addProduct")
    public String save(@Valid @ModelAttribute("aProductRequest") AProductRequestDTO aProductRequest
            , BindingResult bindingResult
            , @RequestParam("imageProduct") MultipartFile[] files
                       ,  RedirectAttributes redirAttrs
            , Model model) throws IOException {
        List<ACategoryResponseDTO> categories = categoryService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories", categories);
        List<ABrandResponseDTO> brands = brandService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("brands", brands);
        if (bindingResult.hasErrors() || !UploadFileAdd.saveImage(files, bindingResult)) {
            return "admin/product/addProduct";
        }
        for (MultipartFile file : files) {
            try {
                aProductRequest.setImage(imageService.save(file));
                productService.addProduct(aProductRequest);
            } catch (Exception e) {
                bindingResult.rejectValue("image","uploadError", "Lỗi khi lưu ảnh hoặc thêm sản phẩm!");
                return "admin/product/addProduct";
            }
        }
        redirAttrs.addFlashAttribute("success", "Thêm thành công!");
        return "redirect:/admin/product";

    }

    @GetMapping("/updateProduct/{id}")
    public String update(Model model, @PathVariable Long id) {
        List<ACategoryResponseDTO> categories = categoryService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories", categories);
        List<ABrandResponseDTO> brands = brandService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("brands", brands);
        Product product = productService.findById(id);
        AProductUpdateRequestDTO aProductUpdateRequest = productService.AEntityMapRequest(product);
        model.addAttribute("aProductUpdateRequest", aProductUpdateRequest);
        return "admin/product/updateProduct";
    }

    @PostMapping("/updateProduct/{id}")
    public String edit(@Valid @ModelAttribute("aProductUpdateRequest") AProductUpdateRequestDTO aProductUpdateRequest
            , BindingResult bindingResult
            , @RequestParam("imageProduct") MultipartFile[] files
            , @PathVariable Long id
            ,  RedirectAttributes redirAttrs
            , Model model) throws IOException {
        List<ACategoryResponseDTO> categories = categoryService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories", categories);
        List<ABrandResponseDTO> brands = brandService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("brands", brands);
        if (bindingResult.hasErrors() || !UploadFileUpdate.saveImage(files, bindingResult)) {
            return "admin/product/updateProduct";
        }
        MultipartFile image = files[0];
        if (image.isEmpty()) {
            aProductUpdateRequest.setImage(productService.findById(id).getImage());
        }else {
            for (MultipartFile file : files) {
                try {
                    aProductUpdateRequest.setImage(imageService.save(file));
                } catch (Exception e) {
                    bindingResult.rejectValue("image","uploadError", "Lỗi khi lưu ảnh hoặc thêm thương hiệu!");
                    return "admin/product/updateProduct";
                }
            }
        }
        StringError stringError = productService.updateProduct(aProductUpdateRequest, id);
        if (stringError != null) {
            if (stringError.getName() != null) {
                bindingResult.rejectValue("productName", "name exist", stringError.getName());
                return "admin/product/updateProduct";
            }
        }
        redirAttrs.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/admin/product";
    }

    @GetMapping("/productDetail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        AProductDetailResponseDTO aProductDetailResponse = productService.AEntityMapDetailResponse(product);
        model.addAttribute("aProductDetailResponse", aProductDetailResponse);
        return "admin/product/productDetail";
    }

    @ResponseBody
    @GetMapping("/changeStatusProduct/{id}")
    public AProductResponseDTO changeStatus(@PathVariable Long id) {
        productService.changeStatus(id);
        return productService.AEntityMapResponse(productService.findById(id));
    }

    @GetMapping("/deleteProduct/{id}")
    public String delete(@PathVariable Long id,  RedirectAttributes redirAttrs) {
        productService.delete(id);
        redirAttrs.addFlashAttribute("success", "Xóa thành công!");
        return "redirect:/admin/disabledProduct";
    }
}
