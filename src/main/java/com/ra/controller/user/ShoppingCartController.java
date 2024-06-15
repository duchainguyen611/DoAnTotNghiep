package com.ra.controller.user;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.ENUM.PaymentMethods;
import com.ra.model.entity.Product;
import com.ra.model.entity.ShoppingCart;
import com.ra.model.entity.User;
import com.ra.model.entity.dto.response.user.AddressResponseDTO;
import com.ra.model.entity.dto.response.user.CartResponseDTO;
import com.ra.model.entity.dto.response.user.CheckOutInforDTO;
import com.ra.model.entity.dto.response.user.UCategoryResponseDTO;
import com.ra.model.service.AddressService;
import com.ra.model.service.CategoryService;
import com.ra.model.service.ProductService;
import com.ra.model.service.ShoppingCartService;
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
@RequiredArgsConstructor
@RequestMapping("/user")
public class ShoppingCartController {
    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;
    private final UserLogin userLogin;
    private final AddressService addressService;
    private final CategoryService categoryService;

    @GetMapping("/cart")
    public String shoppingCart(Model model) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader=carts.size();
        model.addAttribute("carts", carts);
        double sumPrice = (double) 0;
        for (CartResponseDTO cart:carts){
            sumPrice+=(cart.getUnitPrice()*cart.getCartQuantity());
        }
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        model.addAttribute("sumPrice",sumPrice);
        model.addAttribute("sumPriceMobile",sumPrice);
        model.addAttribute("sumPriceHeader", NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPrice)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);

        return "home/user/shoppingCart/shoppingCart";
    }

    @PostMapping("/addProductToShoppingCart/{productId}")
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
        return "redirect:/user/shop";
    }

    @GetMapping("/cart/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        User user = userLogin.userLogin();
        shoppingCartService.deleteByUserAndId(user, id);
        return "redirect:/user/cart";
    }

    @PostMapping("/cart/updateQuantity")
    public String updateProductInShoppingCart(@RequestParam(name = "idCart") Long idCart,
                                              @RequestParam(name = "quantityProduct") int quantityProduct) {
        shoppingCartService.updateOrderQuantity(idCart,quantityProduct);
        return "redirect:/user/cart";
    }

    @GetMapping("/cart/checkOut")
    public String prepareCheckOut(Model model, RedirectAttributes redirAttrs) {
        User user = userLogin.userLogin();
        model.addAttribute("user", user);
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        if (carts.isEmpty()){
            redirAttrs.addFlashAttribute("error", "Không có sản phẩm trong giỏ hàng!");
            return "redirect:/user/cart";
        }if (addressService.displayAllByUser(user).isEmpty()){
            redirAttrs.addFlashAttribute("error", "Chưa có địa chỉ nhận hàng! Hãy thêm địa chỉ nhận hàng ở trang khách hàng ở chỗ tên khách hàng!");
            return "redirect:/user/cart";
        }else {
            List<AddressResponseDTO> addresses = addressService.displayAllByUser(user);
            model.addAttribute("carts", carts);
            model.addAttribute("addresses", addresses);
            double sumPrice = (double) 0;
            int countProduct = 0;
            for (CartResponseDTO cart:carts){
                if(cart.getCartQuantity()>productService.findById(cart.getProductId()).getStockQuantity()){
                    redirAttrs.addFlashAttribute("error", "Sản phẩm "+cart.getNameProduct()+" tạm thời không đủ với số lượng bạn mua!");
                    return "redirect:/user/cart";
                }
                countProduct++;
                sumPrice+=(cart.getUnitPrice()*cart.getCartQuantity());
            }
            model.addAttribute("sumPrice", sumPrice);
            model.addAttribute("countProduct", countProduct);
            CheckOutInforDTO checkOutInfor = new CheckOutInforDTO();
            model.addAttribute("checkOutInfor",checkOutInfor);
            return "home/user/payment/payment";
        }
    }

    @PostMapping("/checkOut")
    public String checkOut(@ModelAttribute("checkOutInfor") CheckOutInforDTO checkOutInfor, RedirectAttributes redirAttrs) {
        if (checkOutInfor.getReceiveName().isEmpty()){
            redirAttrs.addFlashAttribute("error", "Chọn đỉa chỉ nhận hàng trước khi thanh toán!");
            return "redirect:/user/cart/checkOut";
        }else {
            checkOutInfor.setPaymentMethods(PaymentMethods.COD);
            shoppingCartService.checkOut(checkOutInfor);
            return "redirect:/user/profile/invoice";
        }
    }

}
