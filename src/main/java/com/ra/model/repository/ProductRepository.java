package com.ra.model.repository;

import com.ra.model.entity.Brand;
import com.ra.model.entity.Category;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
        List<Product> findAllByStatus(ActiveStatus status);
        @Query("select p from Product p where p.stockQuantity = :stockQuantity and p.status = 'ACTIVE'")
        List<Product> findAllByStockQuantity(Integer stockQuantity);
        @Query("select p from Product p where p.stockQuantity > :stockQuantity and p.status = 'ACTIVE'")
        List<Product> findAllByStockQuantityNotLike(Integer stockQuantity);
        List<Product> findAllByCategoryAndBrandAndStatus(Category category, Brand brand, ActiveStatus status);
        @Query("select p from Product p where p.category = :category and p.id != :id")
        List<Product> getAllByCategory(Category category, Long id);
        @Query(value = "select p.* from product p join invoice_detail id on p.id= id.product_id\n" +
                "where p.status = 'ACTIVE' group by id.product_id order by count(id.product_id) desc",nativeQuery = true)
        List<Product> getProductBestSellingByInvoice();
        List<Product> getAllByStatusOrderByIdDesc(ActiveStatus status);
        @Query(value = "select * from product where status = 'ACTIVE' order by rand()",nativeQuery = true)
        List<Product> getAllByRandom();
        Page<Product> findAllByStatusOrderByIdDesc(ActiveStatus status,Pageable pageable);
        boolean existsByProductName(String productName);
        @Query(value = "SELECT * FROM product WHERE (category_id = COALESCE(:categoryId, category_id)) \n" +
                "AND (brand_id = COALESCE(:brandId, brand_id)) AND status = 'ACTIVE'",nativeQuery = true)
        List<Product> findAllByCategoryAndBrand(String categoryId,String brandId);

        @Query(value = "SELECT * FROM product WHERE (category_id = COALESCE(:categoryId, category_id)) \n" +
                "AND product_name like CONCAT('%', :keyword, '%') AND status = 'ACTIVE'",nativeQuery = true)
        List<Product> findAllByCategoryAndProductName(String categoryId,String keyword);

        @Query(value = "SELECT * " +
                "FROM product " +
                "WHERE (brand_id = COALESCE(:brandId, brand_id)) " +
                "AND (category_id = COALESCE(:categoryId, category_id)) " +
                "AND (unit_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 100000000)) " +
                "AND status = 'ACTIVE'",
                nativeQuery = true)
        Page<Product> sortPageFilterProduct(String brandId, String categoryId, String startPrice, String endPrice,Pageable pageable);

}
