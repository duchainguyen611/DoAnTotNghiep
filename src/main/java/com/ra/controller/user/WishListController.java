package com.ra.controller.user;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Product;
import com.ra.model.entity.ShoppingCart;
import com.ra.model.entity.User;
import com.ra.model.entity.WishList;
import com.ra.model.entity.dto.response.user.CartResponseDTO;
import com.ra.model.entity.dto.response.user.UCategoryResponseDTO;
import com.ra.model.entity.dto.response.user.WishListResponseDTO;
import com.ra.model.service.CategoryService;
import com.ra.model.service.ProductService;
import com.ra.model.service.ShoppingCartService;
import com.ra.model.service.WishListService;
import com.ra.security.UserDetail.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;
    private final UserLogin userLogin;
    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;
    private final CategoryService categoryService;
    @GetMapping("/wishList")
    public String shoppingCart(Model model,@RequestParam(name = "keyword", required = false) String keyword){
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader=carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart:carts){
            sumPriceHeader+=(cart.getUnitPrice()*cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader", NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        model.addAttribute("keyword",keyword);
        List<WishListResponseDTO> wishLists = wishListService.displayWishListByUser(user);
        model.addAttribute("wishLists",wishLists);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/wishList/wishlist";
    }

    @GetMapping("/wishList/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id){
        User user = userLogin.userLogin();
        wishListService.deleteByUserAndId(user,id);
        return "redirect:/user/wishList";
    }

    @PostMapping("/addProductToWishList/{productId}")
    public String addProductToWishList(@PathVariable Long productId, RedirectAttributes redirAttrs){
        User user = userLogin.userLogin();
        Product product = productService.findById(productId);
        WishList wishListOld = wishListService.findByProductAndUser(product, user);
        if (wishListOld == null) {
            WishList wishList = new WishList();
            wishList.setProduct(product);
            wishList.setUser(user);
            wishListService.save(wishList);
            redirAttrs.addFlashAttribute("success", "Đã thêm sản phẩm vào sản phẩm yêu thích!");
        }else {
            redirAttrs.addFlashAttribute("error", "Sản phẩm đã có trong sản phẩm yêu thích!");
        }
        return "redirect:/user/shop";
    }

    @PostMapping("/addProductFromWishListToShoppingCart/{productId}")
    public String addProductToShoppingCart(@PathVariable Long productId, RedirectAttributes redirAttrs) {
        User user = userLogin.userLogin();
        Product product = productService.findById(productId);
        ShoppingCart oldCart = shoppingCartService.findByProductAndUser(product, user);
        if (product.getStockQuantity()==0){
            redirAttrs.addFlashAttribute("error", "Sản phẩm tạm thời hết hàng!");
            return "redirect:/user/shop";
        }
        if (oldCart != null) {
            oldCart.setCartQuantity(1 + oldCart.getCartQuantity());
            shoppingCartService.save(oldCart);
        }else {
            ShoppingCart cart = new ShoppingCart();
            cart.setProduct(productService.findById(productId));
            cart.setCartQuantity(1);
            cart.setUser(user);
            shoppingCartService.save(cart);
        }
        redirAttrs.addFlashAttribute("success", "Đã thêm sản phẩm vào giỏ hàng!");
        return "redirect:/user/wishList";
    }
}
