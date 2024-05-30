package com.ra.controller.admin;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.ENUM.OrderStatus;
import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.dto.request.admin.AOrderDetailRequestDTO;
import com.ra.model.entity.dto.request.admin.AOrderRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.*;
import com.ra.model.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AOrderController {
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final VendorService vendorService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    @GetMapping("/order")
    public String getAllOrderUnconfirmed(Model model) {
        List<AOrderResponseDTO> orders = orderService.findAllByStatus(OrderStatus.UNCONFIRMED);
        model.addAttribute("orders", orders);
        return "admin/orders/mainOrder";
    }

    @GetMapping("/orderConfirmed")
    public String getAllOrderConfirmed(Model model) {
        List<AOrderResponseDTO> orders = orderService.findAllByStatus(OrderStatus.CONFIRMED);
        model.addAttribute("orders", orders);
        return "admin/orders/orderConfirmed";
    }


    @GetMapping("/addOrder")
    public String addOrder(Model model) {
        AOrderRequestDTO aOrderRequest = new AOrderRequestDTO();
        model.addAttribute("aOrderRequest", aOrderRequest);
        List<AVendorResponseDTO> vendors = vendorService.findAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("vendors", vendors);
        return "admin/orders/addOrder";
    }

    @PostMapping("/addOrder")
    public String insertOrder(@Valid @ModelAttribute("aOrderRequest") AOrderRequestDTO aOrderRequest
            , BindingResult bindingResult
            , RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()) {
            return "admin/orders/addOrder";
        }
        orderService.addOrder(aOrderRequest);
        redirAttrs.addFlashAttribute("success", "Thêm thành công!");
        return "redirect:/admin/order";
    }

    @GetMapping("/updateOrder/{id}")
    public String updateOrder(Model model, @PathVariable Long id, HttpSession session) {
        session.setAttribute("orderId", id);
        Order order = orderService.findById(id);
        AOrderRequestDTO aOrderRequest = orderService.AEntityMapRequest(order);
        model.addAttribute("aOrderRequest", aOrderRequest);
        List<AVendorResponseDTO> vendors = vendorService.findAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("vendors", vendors);
        return "admin/orders/updateOrder";
    }

    @PostMapping("/updateOrder/{id}")
    public String editOder(@ModelAttribute("aOrderRequest") AOrderRequestDTO aOrderRequest, RedirectAttributes redirAttrs
            , @PathVariable Long id) {
        orderService.updateOrder(aOrderRequest, id);
        redirAttrs.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/admin/order";
    }

    @Transactional
    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable Long id,RedirectAttributes redirAttrs) {
        Order order = orderService.findById(id);
        orderDetailService.deleteByOrder(order);
        orderService.delete(id);
        redirAttrs.addFlashAttribute("success", "Xóa thành công!");
        return "redirect:/admin/order";
    }

    @GetMapping("/confirmOrder/{id}")
    public String confirmOrder(@PathVariable Long id,RedirectAttributes redirAttrs) {
        Boolean confirmOrder = orderService.confirmOrder(id);
        if(!confirmOrder){
            redirAttrs.addFlashAttribute("error", "Phiếu nhập hàng chưa có sản phẩm!");
            return "redirect:/admin/order";
        }else {
            redirAttrs.addFlashAttribute("success", "Duyệt phiếu nhập hàng thành công!");
            return "redirect:/admin/orderConfirmed";
        }
    }

    @GetMapping("/orderDetail/{orderId}")
    public String orderDetail(Model model, @PathVariable Long orderId, HttpSession session) {
        session.setAttribute("orderId", orderId);
        Order order = orderService.findById(orderId);
        AOrderResponseDTO aOrderResponse = orderService.AEntityMapResponse(order);
        model.addAttribute("aOrderResponse", aOrderResponse);
        List<AOrderDetailResponseDTO> orderDetails = orderDetailService.displayAllByOrder(order);
        model.addAttribute("orderDetails", orderDetails);
        String totalPrice = NumberFormat.getInstance(new Locale("vi", "VN")).format(orderDetailService.totalPrice(orderId))+ "₫";
        model.addAttribute("totalPrice", totalPrice);
        int countProduct = orderDetailService.countProduct(orderId);
        model.addAttribute("countProduct",countProduct);
        return "admin/orders/orderDetails";
    }



    @GetMapping("/orderDetailConfirm/{orderId}")
    public String orderDetailConfirm(Model model, @PathVariable Long orderId, HttpSession session) {
        session.setAttribute("orderId", orderId);
        Order order = orderService.findById(orderId);
        AOrderResponseDTO aOrderResponse = orderService.AEntityMapResponse(order);
        model.addAttribute("aOrderResponse", aOrderResponse);
        List<AOrderDetailResponseDTO> orderDetails = orderDetailService.displayAllByOrder(order);
        model.addAttribute("orderDetails", orderDetails);
        String totalPrice = NumberFormat.getInstance(new Locale("vi", "VN")).format(orderDetailService.totalPrice(orderId))+ "₫";
        model.addAttribute("totalPrice", totalPrice);
        int countProduct = orderDetailService.countProduct(orderId);
        model.addAttribute("countProduct",countProduct);
        return "admin/orders/orderDetailsConfirmed";
    }

    @GetMapping("/addOrderDetail")
    public String addOrderDetail(Model model, HttpSession session
            , @RequestParam(name = "brandId",required = false)String brandId
            , @RequestParam(name = "categoryId",required = false)String categoryId) {
        List<ACategoryResponseDTO> categories = categoryService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories", categories);
        List<ABrandResponseDTO> brands = brandService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("brands", brands);
        Long orderId = (Long) session.getAttribute("orderId");
        model.addAttribute("orderId", orderId);
        AOrderDetailRequestDTO aOrderDetailRequest = new AOrderDetailRequestDTO();
        model.addAttribute("aOrderDetailRequest", aOrderDetailRequest);
        List<AProductResponseDTO> products = productService.findAllByCategoryAndBrand(categoryId,brandId);
        model.addAttribute("products", products);
        model.addAttribute("selectedBrandId",brandId);
        model.addAttribute("selectedCategoryId",categoryId);
        return "admin/orders/addOrderDetail";
    }


    @PostMapping("/addOrderDetail")
    public String insertOrderDetail(@Valid @ModelAttribute("aOrderDetailRequest") AOrderDetailRequestDTO aOrderDetailRequest
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes
            , HttpSession session
            , Model model) {
        Long orderId = (Long) session.getAttribute("orderId");
        List<AProductResponseDTO> products = productService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("products", products);
        if (bindingResult.hasErrors()) {
            return "admin/orders/addOrderDetail";
        }
        StringError stringError = orderDetailService.addOrderDetail(aOrderDetailRequest, orderId);
        if (stringError != null) {
            if (stringError.getProduct() != null) {
                bindingResult.rejectValue("productId", "product exist", stringError.getProduct());
                return "admin/orders/addOrderDetail";
            }
        }
        redirectAttributes.addAttribute("orderId", orderId);
        redirectAttributes.addFlashAttribute("success", "Thêm thành công!");
        return "redirect:/admin/addOrderDetail";
    }

    @GetMapping("/updateOrderDetail/{id}")
    public String updateOrderDetail(Model model, @PathVariable Long id, HttpSession session) {
        Long orderId = (Long) session.getAttribute("orderId");
        model.addAttribute("orderId", orderId);
        OrderDetail orderDetail = orderDetailService.findById(id);
        AOrderDetailRequestDTO aOrderDetailRequest = orderDetailService.AEntityMapRequest(orderDetail);
        model.addAttribute("aOrderDetailRequest", aOrderDetailRequest);
        List<AProductResponseDTO> products = productService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("products", products);
        return "admin/orders/updateOrderDetail";
    }

    @PostMapping("/updateOrderDetail/{id}")
    public String editOrderDetail(@Valid @ModelAttribute("aOrderDetailRequest") AOrderDetailRequestDTO aOrderDetailRequest
            , BindingResult bindingResult
            , @PathVariable Long id
            , RedirectAttributes redirectAttributes
            , HttpSession session
            ,Model model) {
        Long orderId = (Long) session.getAttribute("orderId");
        List<AProductResponseDTO> products = productService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("products", products);
        if (bindingResult.hasErrors()) {
            return "admin/orders/updateOrderDetail";
        }
        StringError stringError = orderDetailService.updateOrder(aOrderDetailRequest, id, orderId);
        if (stringError != null) {
            if (stringError.getProduct() != null) {
                bindingResult.rejectValue("productId", "product exist", stringError.getProduct());
                return "admin/orders/addOrderDetail";
            }
        }
        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:/admin/orderDetail/{orderId}";
    }

    @Transactional
    @GetMapping("/deleteOrderDetail/{id}")
    public String deleteOrderDetail(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Long orderId = (Long) session.getAttribute("orderId");
        orderDetailService.deleteById(id);
        redirectAttributes.addAttribute("orderId", orderId); // Thêm orderId vào URL
        return "redirect:/admin/orderDetail/{orderId}";
    }

}
