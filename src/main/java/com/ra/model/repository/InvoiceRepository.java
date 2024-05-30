package com.ra.model.repository;

import com.ra.model.entity.ENUM.InvoiceStatus;
import com.ra.model.entity.Invoice;
import com.ra.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> findAllByInvoiceStatus(InvoiceStatus invoiceStatus);
    @Query("SELECT i FROM Invoice i WHERE i.invoiceStatus <> :invoiceStatus")
    List<Invoice> findAllExceptStatus(InvoiceStatus invoiceStatus);
    List<Invoice> findAllByUser(User user);
}
