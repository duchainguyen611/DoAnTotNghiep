package com.ra.controller.admin;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.ENUM.InvoiceStatus;
import com.ra.model.entity.ENUM.OrderStatus;
import com.ra.model.entity.dto.response.admin.dashboard.DashboardRevenueDTO;
import com.ra.model.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ProductService productService;
    private final UserService userService;
    private final InvoiceService invoiceService;
    private final OrderService orderService;
    private final DashboardService dashboardService;
    @GetMapping("")
    public String index(Model model){
        int sumUser = userService.getAllUserExceptAdmin().size();
        int sumUserActive = userService.findAllByStatus(ActiveStatus.ACTIVE).size();
        int sumUserInActive= userService.findAllByStatus(ActiveStatus.INACTIVE).size();
        int sumProduct = productService.findAll().size();
        int sumProductOutStock = productService.findAllByStockQuantity(0).size();
        int sumProductInStock = productService.findAllByStockQuantityNotLike(0).size();
        int sumInvoice = invoiceService.findAll().size();
        int sumInvoiceSuccess = invoiceService.findAllByStatus(InvoiceStatus.SUCCESS).size();
        int sumInvoiceCancel = invoiceService.findAllByStatus(InvoiceStatus.CANCEL).size();
        int sumOrder = orderService.getAll().size();
        int sumOrderConfirm = orderService.findAllByStatus(OrderStatus.CONFIRMED).size();
        int sumOrderExceptConfirm = orderService.findAllByStatus(OrderStatus.UNCONFIRMED).size();
        model.addAttribute("sumUser",sumUser);
        model.addAttribute("sumUserActive",sumUserActive);
        model.addAttribute("sumUserInActive",sumUserInActive);
        model.addAttribute("sumProduct",sumProduct);
        model.addAttribute("sumProductOutStock",sumProductOutStock);
        model.addAttribute("sumProductInStock",sumProductInStock);
        model.addAttribute("sumInvoice",sumInvoice);
        model.addAttribute("sumInvoiceSuccess",sumInvoiceSuccess);
        model.addAttribute("sumInvoiceCancel",sumInvoiceCancel);
        model.addAttribute("sumOrder",sumOrder);
        model.addAttribute("sumOrderConfirm",sumOrderConfirm);
        model.addAttribute("sumOrderExceptConfirm",sumOrderExceptConfirm);


        String today = LocalDate.now().toString();
        String sevenDaysAgo =  LocalDate.now().minusDays(7).toString();
        model.addAttribute("today",today);
        model.addAttribute("sevenDaysAgo",sevenDaysAgo);

        List<DashboardRevenueDTO> revenueDTOS = dashboardService.dashboardRevenue(sevenDaysAgo,today);
        List<Double> importAmountList = revenueDTOS.stream().map(DashboardRevenueDTO::getImportAmount).toList();
        List<Double> saleAmountList = revenueDTOS.stream().map(DashboardRevenueDTO::getSaleAmount).toList();
        List<Double> totalAmountList = revenueDTOS.stream().map(DashboardRevenueDTO::getTotalAmount).toList();
        model.addAttribute("importAmountList",importAmountList);
        model.addAttribute("saleAmountList",saleAmountList);
        model.addAttribute("totalAmountList",totalAmountList);


        return "admin/index";
    }

}
