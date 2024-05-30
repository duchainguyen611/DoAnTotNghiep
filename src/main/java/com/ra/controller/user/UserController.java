package com.ra.controller.user;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Product;
import com.ra.model.entity.User;
import com.ra.model.entity.dto.response.user.CartResponseDTO;
import com.ra.model.entity.dto.response.user.UBrandResponseDTO;
import com.ra.model.entity.dto.response.user.UCategoryResponseDTO;
import com.ra.model.entity.dto.response.user.UProductResponseDTO;
import com.ra.model.service.*;
import com.ra.security.UserDetail.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ShoppingCartService shoppingCartService;
    private final UserLogin userLogin;

    @GetMapping("/home")
    public String home(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader=carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart:carts){
            sumPriceHeader+=(cart.getUnitPrice()*cart.getCartQuantity());
        }
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
        model.addAttribute("sumPriceHeader", NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        model.addAttribute("keyword", keyword);
        return "home/user/home";
    }

    @GetMapping("/news")
    public String news(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader=carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart:carts){
            sumPriceHeader+=(cart.getUnitPrice()*cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader",NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/news/news";
    }

    @GetMapping("/news/news1")
    public String news1(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader=carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart:carts){
            sumPriceHeader+=(cart.getUnitPrice()*cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader",NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/news/news1";
    }

    @GetMapping("/news/news2")
    public String news2(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader=carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart:carts){
            sumPriceHeader+=(cart.getUnitPrice()*cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader",NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/news/news2";
    }

    @GetMapping("/news/news3")
    public String news3(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader=carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart:carts){
            sumPriceHeader+=(cart.getUnitPrice()*cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader",NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/news/news3";
    }

    @GetMapping("/contact")
    public String contact(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader=carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart:carts){
            sumPriceHeader+=(cart.getUnitPrice()*cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader",NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        model.addAttribute("keyword", keyword);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/contact/contact";
    }


    @GetMapping("/shop/productDetail/{id}")
    public String productDetail(Model model, @PathVariable Long id, @RequestParam(name = "keyword", required = false) String keyword) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader=carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart:carts){
            sumPriceHeader+=(cart.getUnitPrice()*cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader",NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        model.addAttribute("keyword", keyword);
        Product product = productService.findById(id);
        UProductResponseDTO uProductResponse = productService.UEntityMapResponse(product);
        model.addAttribute("uProductResponse", uProductResponse);
        List<UProductResponseDTO> products = productService.findByCategory(uProductResponse.getCategory(),uProductResponse.getId());
        model.addAttribute("products",products);
        List<UProductResponseDTO> productsByRandom1 = productService.getRandomProduct(0,3);
        List<UProductResponseDTO> productsByRandom2 = productService.getRandomProduct(3,6);
        model.addAttribute("productsByRandom1",productsByRandom1);
        model.addAttribute("productsByRandom2",productsByRandom2);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/product/productDetailUser";
    }

    @GetMapping("/search")
    public String searchProduct(Model model
            , @RequestParam(name = "keyword", required = false) String keyword
            , @RequestParam(defaultValue = "1", name = "pageNo") Integer pageNo
            , @RequestParam(name = "categoryId",required = false) String categoryId){
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader=carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart:carts){
            sumPriceHeader+=(cart.getUnitPrice()*cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader",NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        Page<UProductResponseDTO> products = productService.findAllByCategoryAndProductNamePage(keyword,pageNo,categoryId);
        model.addAttribute("products", products);
        model.addAttribute("keyword",keyword);
        model.addAttribute("sumProduct",productService.findAllByCategoryAndProductName(keyword,categoryId).size());
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/product/searchProduct";
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
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader=carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart:carts){
            sumPriceHeader+=(cart.getUnitPrice()*cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader",NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
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
        return "home/user/product/shopProduct";
    }
}
