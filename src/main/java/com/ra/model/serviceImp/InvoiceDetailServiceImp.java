package com.ra.model.serviceImp;

import com.ra.model.entity.Invoice;
import com.ra.model.entity.InvoiceDetail;
import com.ra.model.entity.dto.response.admin.AInvoiceDetailResponseDTO;
import com.ra.model.entity.dto.response.user.UInvoiceDetailResponseDTO;
import com.ra.model.repository.InvoiceDetailRepository;
import com.ra.model.service.InvoiceDetailService;
import com.ra.model.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class InvoiceDetailServiceImp implements InvoiceDetailService {
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final InvoiceService invoiceService;


    @Override
    public List<InvoiceDetail> findAllByInvoice(Invoice invoice) {
        return invoiceDetailRepository.findAllByInvoice(invoice);
    }

    @Override
    public List<AInvoiceDetailResponseDTO> displayAll(Invoice invoice) {
        return findAllByInvoice(invoice).stream().map(this::AEntityInvoice).toList();
    }

    @Override
    public AInvoiceDetailResponseDTO AEntityInvoice(InvoiceDetail invoiceDetail) {
        return AInvoiceDetailResponseDTO.builder()
                .id(invoiceDetail.getId())
                .productImage(invoiceDetail.getProduct().getImage())
                .productName(invoiceDetail.getProduct().getProductName())
                .invoiceQuantity(invoiceDetail.getInvoiceQuantity())
                .productUnitPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(invoiceDetail.getProduct().getUnitPrice())+ "₫")
                .totalAmount(NumberFormat.getInstance(new Locale("vi", "VN")).format(invoiceDetail.getProduct().getUnitPrice()*invoiceDetail.getInvoiceQuantity())+ "₫")
                .build();
    }

    @Override
    public List<UInvoiceDetailResponseDTO> displayInvoiceDetailByInvoice(Long idInvoice) {
        Invoice invoice = invoiceService.findById(idInvoice);
        return invoiceDetailRepository.findAllByInvoice(invoice).stream().map(this::UEntityResponse).toList();
    }

    @Override
    public Double totalPrice(Long invoiceId) {
        Invoice invoice = invoiceService.findById(invoiceId);
        double totalPrice = 0;
        for (InvoiceDetail invoiceDetail:invoice.getInvoiceDetails()){
            totalPrice += invoiceDetail.getProduct().getUnitPrice()*invoiceDetail.getInvoiceQuantity();
        }
        invoice.setTotalPrice(totalPrice);
        invoiceService.save(invoice);
        return totalPrice;
    }

    @Override
    public Integer countProduct(Long invoiceId) {
        Invoice invoice = invoiceService.findById(invoiceId);
        return invoice.getInvoiceDetails().size();
    }

    @Override
    public UInvoiceDetailResponseDTO UEntityResponse(InvoiceDetail invoiceDetail) {
        return UInvoiceDetailResponseDTO.builder()
                .id(invoiceDetail.getId())
                .idProduct(invoiceDetail.getProduct().getId())
                .nameProduct(invoiceDetail.getProduct().getProductName())
                .imageProduct(invoiceDetail.getProduct().getImage())
                .unitPriceProduct(NumberFormat.getInstance(new Locale("vi", "VN")).format(invoiceDetail.getProduct().getUnitPrice())+ "₫")
                .invoiceQuantity(invoiceDetail.getInvoiceQuantity())
                .totalPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(invoiceDetail.getProduct().getUnitPrice()*invoiceDetail.getInvoiceQuantity())+ "₫")
                .build();
    }
}
