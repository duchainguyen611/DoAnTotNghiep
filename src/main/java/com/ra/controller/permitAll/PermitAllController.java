package com.ra.controller.permitAll;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Product;
import com.ra.model.entity.dto.request.user.RegisterDTO;
import com.ra.model.entity.dto.response.user.UBrandResponseDTO;
import com.ra.model.entity.dto.response.user.UCategoryResponseDTO;
import com.ra.model.entity.dto.response.user.UProductResponseDTO;
import com.ra.model.service.BrandService;
import com.ra.model.service.CategoryService;
import com.ra.model.service.ProductService;
import com.ra.model.service.UserService;
import com.ra.validator.user.PasswordValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PermitAllController {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/home")
    public String home(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        List<UBrandResponseDTO> brands = brandService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("brands",brands);
        List<UProductResponseDTO> productsByBestSelling = productService.getProductBestSellingByInvoice(6);
        List<UProductResponseDTO> productsByNewest = productService.getAllByStatusOrderByIdDesc(4,ActiveStatus.ACTIVE);
        List<UProductResponseDTO> productsByRandom1 = productService.getRandomProduct(0,3);
        List<UProductResponseDTO> productsByRandom2 = productService.getRandomProduct(3,6);
        model.addAttribute("productsByBestSelling",productsByBestSelling);
        model.addAttribute("productsByNewest",productsByNewest);
        model.addAttribute("productsByRandom1",productsByRandom1);
        model.addAttribute("productsByRandom2",productsByRandom2);
        return "home/permitAll/home";
    }

    @GetMapping("/news")
    public String news(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/permitAll/news/news";
    }

    @GetMapping("/news/news1")
    public String news1(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/permitAll/news/news1";
    }

    @GetMapping("/news/news2")
    public String news2(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/permitAll/news/news2";
    }

    @GetMapping("/news/news3")
    public String news3(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/permitAll/news/news3";
    }

    @GetMapping("/contact")
    public String contact(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/permitAll/contact/contact";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(name = "keyword", required = false) String keyword){
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/permitAll/authentication/login";
    }
    @GetMapping("/login?error")
    public String setErrorMessageLogin(Model model) {
        return "home/permitAll/authentication/login";
    }

    @GetMapping("/register")
    public String register(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        RegisterDTO register = new RegisterDTO();
        model.addAttribute("register", register);
        return "home/permitAll/authentication/register";
    }

    @GetMapping("/shop/productDetail/{id}")
    public String productDetail(Model model, @PathVariable Long id,@RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        Product product = productService.findById(id);
        UProductResponseDTO uProductResponse = productService.UEntityMapResponse(product);
        model.addAttribute("uProductResponse", uProductResponse);
        List<UProductResponseDTO> products = productService.findByCategory(uProductResponse.getCategory(),uProductResponse.getId());
        model.addAttribute("products",products);
        List<UProductResponseDTO> productsByRandom1 = productService.getRandomProduct(0,3);
        List<UProductResponseDTO> productsByRandom2 = productService.getRandomProduct(3,6);
        model.addAttribute("productsByRandom1",productsByRandom1);
        model.addAttribute("productsByRandom2",productsByRandom2);
        return "home/permitAll/product/productDetailUser";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute(name = "register") RegisterDTO register
            , BindingResult bindingResult, RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()) {
            return "home/permitAll/authentication/register";
        }
        String errorPassword = PasswordValidator.passwordValidation(register.getPassword());
        if (errorPassword != null) {
            bindingResult.rejectValue("password", "validator password", errorPassword);
            return "home/permitAll/authentication/register";
        }
        userService.register(register);
        redirAttrs.addFlashAttribute("success", "Đăng ký thành công!");
        return "redirect:/login";
    }

    @GetMapping("/search")
    public String searchProduct(Model model
            , @RequestParam(name = "keyword", required = false) String keyword
            , @RequestParam(defaultValue = "1", name = "pageNo") Integer pageNo
            , @RequestParam(name = "categoryId",required = false) String categoryId){
        Page<UProductResponseDTO> products = productService.findAllByCategoryAndProductNamePage(keyword,pageNo,categoryId);
        model.addAttribute("products", products);
        model.addAttribute("keyword",keyword);
        model.addAttribute("sumProduct",productService.findAllByCategoryAndProductName(keyword,categoryId).size());
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/permitAll/product/searchProduct";
    }

    @GetMapping("/shop")
    public String shopProduct(Model model
            , @RequestParam(name = "keyword", required = false) String keyword
            , @RequestParam(defaultValue = "1", name = "pageNo") Integer pageNo
            , @RequestParam(name = "categoryId",required = false) String categoryId
            , @RequestParam(name = "brandId",required = false) String brandId
            , @RequestParam(name = "startPrice",required = false) String startPriceString
            , @RequestParam(name = "endPrice",required = false) String endPriceString
            , @RequestParam(name = "filter",required = false) String filter) {
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        List<UBrandResponseDTO> brands = brandService.UFindAllByStatus(ActiveStatus.ACTIVE);
        String field = "id",sort="desc";
        Page<UProductResponseDTO> products = productService.UFindAllByStatusActive(pageNo);
        boolean isCheck = false;
        if (startPriceString != null){
            startPriceString = startPriceString.replace("₫", "").replaceAll("\\.", "");
            model.addAttribute("startPrice", startPriceString);
            isCheck = true;
        }
        if (endPriceString != null){
            endPriceString = endPriceString.replace("₫", "").replaceAll("\\.", "");
            model.addAttribute("endPrice", endPriceString);
            isCheck = true;
        }
        if (categoryId != null) {
            model.addAttribute("categoryId", categoryId);
            isCheck = true;
        }
        if (brandId != null) {
            model.addAttribute("brandId", brandId);
            isCheck = true;
        }
        if (filter!=null){
            String[] sortArray = filter.split("-");
            field = sortArray[0];
            sort = sortArray[1];
            model.addAttribute("filter", filter);
            isCheck = true;
        }
        if (isCheck) {
            products = productService.sortPageFilterProduct(brandId, categoryId, startPriceString, endPriceString, field, sort, pageNo);
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("products", products);
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "home/permitAll/product/shopProduct";
    }


}
