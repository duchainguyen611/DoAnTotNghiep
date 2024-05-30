package com.ra.model.repository;

import com.ra.model.entity.Invoice;
import com.ra.model.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail,Long> {
    List<InvoiceDetail> findAllByInvoice(Invoice invoice);
}
