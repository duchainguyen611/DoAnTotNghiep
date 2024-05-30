package com.ra.controller.user;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Invoice;
import com.ra.model.entity.InvoiceDetail;
import com.ra.model.entity.ShoppingCart;
import com.ra.model.entity.User;
import com.ra.model.entity.dto.response.user.*;
import com.ra.model.service.*;
import com.ra.security.UserDetail.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserInvoiceController {
    private final CategoryService categoryService;
    private final ShoppingCartService shoppingCartService;
    private final UserLogin userLogin;
    private final InvoiceService invoiceService;
    private final InvoiceDetailService invoiceDetailService;
    private final ProductService productService;
    @GetMapping("/invoiceDetail/{idInvoice}")
    public String invoiceDetail(Model model, @PathVariable Long idInvoice) {
        User user = userLogin.userLogin();
        List<CartResponseDTO> carts = shoppingCartService.displayCartByUser(user);
        int countProductHeader = carts.size();
        double sumPriceHeader = (double) 0;
        for (CartResponseDTO cart : carts) {
            sumPriceHeader += (cart.getUnitPrice() * cart.getCartQuantity());
        }
        model.addAttribute("sumPriceHeader", NumberFormat.getInstance(new Locale("vi", "VN")).format(sumPriceHeader)+ "₫");
        model.addAttribute("countProductHeader", countProductHeader);
        UInvoiceResponseDTO invoice = invoiceService.displayById(idInvoice);
        List<UInvoiceDetailResponseDTO> invoiceDetails = invoiceDetailService.displayInvoiceDetailByInvoice(idInvoice);
        model.addAttribute("user", user);
        model.addAttribute("invoice", invoice);
        model.addAttribute("invoiceDetails", invoiceDetails);
        List<UCategoryResponseDTO> categories = categoryService.UFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories",categories);
        return "home/user/profile/invoiceDetail";
    }

    @GetMapping("/invoice/cancelInvoice/{id}")
    public String cancelInvoice(@PathVariable Long id,RedirectAttributes redirAttrs){
        invoiceService.changeStatus(id, "CANCEL");
        redirAttrs.addFlashAttribute("success", "Hủy thành công!");
        return "redirect:/user/profile/invoice";
    }

    @GetMapping("/invoice/buyAgain/{id}")
    public String buyAgain(@PathVariable Long id, RedirectAttributes redirAttrs){
        User user = userLogin.userLogin();
        Invoice invoice = invoiceService.findById(id);
        List<InvoiceDetail> invoiceDetails = invoiceDetailService.findAllByInvoice(invoice);
        for (InvoiceDetail invoiceDetail:invoiceDetails){
            ShoppingCart oldCart = shoppingCartService.findByProductAndUser(invoiceDetail.getProduct(), user);
            if (oldCart != null) {
                oldCart.setCartQuantity(1 + oldCart.getCartQuantity());
                shoppingCartService.save(oldCart);
            }else {
                ShoppingCart cart = new ShoppingCart();
                cart.setProduct(productService.findById(invoiceDetail.getProduct().getId()));
                cart.setCartQuantity(invoiceDetail.getInvoiceQuantity());
                cart.setUser(user);
                shoppingCartService.save(cart);
            }
        }
        redirAttrs.addFlashAttribute("success", "Đã thêm sản phẩm vào giỏ hàng!");
        return "redirect:/user/profile/invoice";
    }
}
