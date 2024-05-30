package com.ra.model.repository;

import com.ra.model.entity.Category;
import com.ra.model.entity.ENUM.ActiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAllByStatus(ActiveStatus status);
    boolean existsByCategoryName(String categoryName);
}
