package com.ra.model.service;

import com.ra.model.entity.ENUM.InvoiceStatus;
import com.ra.model.entity.Invoice;
import com.ra.model.entity.User;
import com.ra.model.entity.dto.response.admin.AInvoiceResponseDTO;
import com.ra.model.entity.dto.response.user.UInvoiceResponseDTO;

import java.util.List;

public interface InvoiceService {
    Invoice findById(Long id);
    List<Invoice> findAllByUser(User user);
    List<Invoice> findAll();
    List<AInvoiceResponseDTO> displayAll();
    List<UInvoiceResponseDTO> displayAllByUser(User user);
    UInvoiceResponseDTO displayById(Long id);
    Invoice save(Invoice invoice);
    List<AInvoiceResponseDTO> findAllByStatus(InvoiceStatus status);
    List<AInvoiceResponseDTO> findAllExceptStatus(InvoiceStatus invoiceStatus);
    Invoice changeStatus(Long id,String status);
    AInvoiceResponseDTO AEntityResponse(Invoice invoice);
    UInvoiceResponseDTO UEntityResponse(Invoice invoice);
}
