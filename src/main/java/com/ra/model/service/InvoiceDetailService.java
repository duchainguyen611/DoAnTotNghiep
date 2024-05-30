package com.ra.model.service;

import com.ra.model.entity.Invoice;
import com.ra.model.entity.InvoiceDetail;
import com.ra.model.entity.dto.response.admin.AInvoiceDetailResponseDTO;
import com.ra.model.entity.dto.response.user.UInvoiceDetailResponseDTO;

import java.util.List;

public interface InvoiceDetailService {
    List<InvoiceDetail> findAllByInvoice(Invoice invoice);
    List<AInvoiceDetailResponseDTO> displayAll(Invoice invoice);
    AInvoiceDetailResponseDTO AEntityInvoice(InvoiceDetail invoiceDetail);
    List<UInvoiceDetailResponseDTO> displayInvoiceDetailByInvoice(Long idInvoice);
    Double totalPrice(Long invoiceId);
    Integer countProduct(Long invoiceId);
    UInvoiceDetailResponseDTO UEntityResponse(InvoiceDetail invoiceDetail);

}
