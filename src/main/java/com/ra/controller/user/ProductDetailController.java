package com.ra.controller.user;

import com.ra.model.entity.Product;
import com.ra.model.entity.ShoppingCart;
import com.ra.model.entity.User;
import com.ra.model.entity.WishList;
import com.ra.model.service.ProductService;
import com.ra.model.service.ShoppingCartService;
import com.ra.model.service.WishListService;
import com.ra.security.UserDetail.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class ProductDetailController {
    private final UserLogin userLogin;
    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;
    private final WishListService wishListService;


    @PostMapping("/addProductFromProductDetailToShoppingCart/{productId}")
    public String addProductToShoppingCart(@PathVariable Long productId
            , @RequestParam(name = "quantityProduct") Integer quantityProduct
            , RedirectAttributes redirAttrs) {
        User user = userLogin.userLogin();
        Product product = productService.findById(productId);
        if (product.getStockQuantity()==0){
            redirAttrs.addFlashAttribute("error", "Sản phẩm tạm thời hết hàng!");
            return "redirect:/user/shop/productDetail/" + productId;
        }
        ShoppingCart oldCart = shoppingCartService.findByProductAndUser(product, user);
        if (oldCart != null) {
            oldCart.setCartQuantity(quantityProduct + oldCart.getCartQuantity());
            shoppingCartService.save(oldCart);
        } else {
            ShoppingCart cart = new ShoppingCart();
            cart.setProduct(product);
            cart.setCartQuantity(quantityProduct);
            cart.setUser(user);
            shoppingCartService.save(cart);
        }
        redirAttrs.addFlashAttribute("success", "Đã thêm sản phẩm vào giỏ hàng!");
        return "redirect:/user/shop/productDetail/" + productId;
    }

    @PostMapping("/addProductFromProductDetailToWishList/{productId}")
    public String addProductToWishList(@PathVariable Long productId, RedirectAttributes redirAttrs) {
        User user = userLogin.userLogin();
        Product product = productService.findById(productId);
        WishList wishListOld = wishListService.findByProductAndUser(product, user);
        if (wishListOld == null) {
            WishList wishList = new WishList();
            wishList.setProduct(product);
            wishList.setUser(user);
            wishListService.save(wishList);
            redirAttrs.addFlashAttribute("success", "Đã thêm sản phẩm vào sản phẩm yêu thích!");
        }else{
            redirAttrs.addFlashAttribute("error", "Sản phẩm đã có trong sản phẩm yêu thích!");
        }
        return "redirect:/user/shop/productDetail/" + productId;
    }
}
