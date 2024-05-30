package com.ra.model.repository;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {
    List<Vendor> findAllByStatus(ActiveStatus status);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByVendorName(String vendorName);
}
