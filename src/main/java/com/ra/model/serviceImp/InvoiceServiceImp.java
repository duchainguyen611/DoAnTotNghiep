package com.ra.model.serviceImp;

import com.ra.model.entity.ENUM.InvoiceStatus;
import com.ra.model.entity.Invoice;
import com.ra.model.entity.InvoiceDetail;
import com.ra.model.entity.Product;
import com.ra.model.entity.User;
import com.ra.model.entity.dto.response.admin.AInvoiceResponseDTO;
import com.ra.model.entity.dto.response.user.UInvoiceResponseDTO;
import com.ra.model.repository.InvoiceDetailRepository;
import com.ra.model.repository.InvoiceRepository;
import com.ra.model.service.InvoiceService;
import com.ra.model.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImp implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final ProductService productService;
    @Override
    public Invoice findById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Invoice> findAllByUser(User user) {
        return invoiceRepository.findAllByUser(user);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public List<AInvoiceResponseDTO> displayAll() {
        return findAll().stream().map(this::AEntityResponse).toList();
    }

    @Override
    public List<UInvoiceResponseDTO> displayAllByUser(User user) {
        return findAllByUser(user).stream().map(this::UEntityResponse).toList().reversed();
    }

    @Override
    public UInvoiceResponseDTO displayById(Long id) {
        return UEntityResponse(findById(id));
    }


    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<AInvoiceResponseDTO> findAllByStatus(InvoiceStatus status) {
        return invoiceRepository.findAllByInvoiceStatus(status).stream().map(this::AEntityResponse).toList();
    }

    @Override
    public List<AInvoiceResponseDTO> findAllExceptStatus(InvoiceStatus invoiceStatus) {
        return invoiceRepository.findAllExceptStatus(invoiceStatus).stream().map(this::AEntityResponse).toList();
    }

    @Override
    public Invoice changeStatus(Long id,String status) {
        Invoice invoice = findById(id);
        switch (status){
            case "WAITING" -> invoice.setInvoiceStatus(InvoiceStatus.WAITING);
            case "CONFIRM" -> invoice.setInvoiceStatus(InvoiceStatus.CONFIRM);
            case "DELIVERY" -> invoice.setInvoiceStatus(InvoiceStatus.DELIVERY);
            case "SUCCESS" -> {
                invoice.setInvoiceStatus(InvoiceStatus.SUCCESS);
                List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findAllByInvoice(invoice);
                for (InvoiceDetail invoiceDetail:invoiceDetails){
                    Product product = productService.findById(invoiceDetail.getProduct().getId());
                    product.setStockQuantity(product.getStockQuantity()-invoiceDetail.getInvoiceQuantity());
                    productService.save(product);
                }
            }
            case "CANCEL" -> invoice.setInvoiceStatus(InvoiceStatus.CANCEL);
            case "DENIED" -> invoice.setInvoiceStatus(InvoiceStatus.DENIED);
        }
        return invoiceRepository.save(invoice);
    }

    @Override
    public AInvoiceResponseDTO AEntityResponse(Invoice invoice) {
        return AInvoiceResponseDTO.builder()
                .id(invoice.getId())
                .username(invoice.getUser().getUsername())
                .serialNumber(invoice.getSerialNumber())
                .totalPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(invoice.getTotalPrice())+ "₫")
                .statusInvoice(invoice.getInvoiceStatus().toString())
                .createdDate(invoice.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
    }

    @Override
    public UInvoiceResponseDTO UEntityResponse(Invoice invoice) {
        return UInvoiceResponseDTO.builder()
                .id(invoice.getId())
                .createdDate(invoice.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .receiveAddress(invoice.getReceiveAddress())
                .receiveName(invoice.getReceiveName())
                .receivePhone(invoice.getReceivePhone())
                .paymentMethods(invoice.getPaymentMethods().toString())
                .note(invoice.getNote())
                .totalPriceHaveShip(NumberFormat.getInstance(new Locale("vi", "VN")).format(invoice.getTotalPrice())+ "₫")
                .totalPriceNotShip(NumberFormat.getInstance(new Locale("vi", "VN")).format(invoice.getTotalPrice()-40000)+ "₫")
                .statusInvoice(invoice.getInvoiceStatus().toString())
                .build();
    }


}
