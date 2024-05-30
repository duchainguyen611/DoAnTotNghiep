package com.ra.controller.admin;

import com.ra.model.entity.ENUM.InvoiceStatus;
import com.ra.model.entity.Invoice;
import com.ra.model.entity.dto.response.admin.AInvoiceDetailResponseDTO;
import com.ra.model.entity.dto.response.admin.AInvoiceResponseDTO;
import com.ra.model.service.InvoiceDetailService;
import com.ra.model.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AInvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceDetailService invoiceDetailService;

    @GetMapping("/invoice")
    public String getAllInvoiceExceptConfirm(Model model){
        List<AInvoiceResponseDTO> invoices = invoiceService.findAllExceptStatus(InvoiceStatus.SUCCESS);
        model.addAttribute("invoices",invoices);
        return "admin/invoice/mainInvoice";
    }

    @GetMapping("/invoiceSuccess")
    public String getAllInvoiceConfirm(Model model){
        List<AInvoiceResponseDTO> invoices = invoiceService.findAllByStatus(InvoiceStatus.SUCCESS);
        model.addAttribute("invoices",invoices);
        return "admin/invoice/invoiceSuccess";
    }

    @GetMapping("/invoiceDetail/{id}")
    public String invoiceDetail(Model model, @PathVariable Long id){
        Invoice invoice = invoiceService.findById(id);
        AInvoiceResponseDTO aInvoiceResponse = invoiceService.AEntityResponse(invoice);
        model.addAttribute("aInvoiceResponse",aInvoiceResponse);
        List<AInvoiceDetailResponseDTO> invoiceDetails = invoiceDetailService.displayAll(invoice);
        model.addAttribute("invoiceDetails",invoiceDetails);
        String totalPrice = NumberFormat.getInstance(new Locale("vi", "VN")).format(invoiceDetailService.totalPrice(id))+ "₫";
        model.addAttribute("totalPrice", totalPrice);
        int countProduct = invoiceDetailService.countProduct(id);
        model.addAttribute("countProduct",countProduct);
        return "admin/invoice/invoiceDetails";
    }

    @ResponseBody
    @GetMapping("/changeStatusInvoice/{id}")
    public AInvoiceResponseDTO changeStatus(@PathVariable Long id, @RequestParam(name = "status") String status){
        invoiceService.changeStatus(id,status);
        return invoiceService.AEntityResponse(invoiceService.findById(id));
    }

}
