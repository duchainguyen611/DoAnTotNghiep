package com.ra.model.repository;

import com.ra.model.entity.Brand;
import com.ra.model.entity.ENUM.ActiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    List<Brand> findAllByStatus(ActiveStatus status);
    Brand findByBrandName(String brandName);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByBrandName(String brandName);
    Brand findByPhone(String phone);
}
