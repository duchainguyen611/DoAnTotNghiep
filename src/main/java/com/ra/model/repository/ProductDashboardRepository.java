package com.ra.model.repository;

import com.ra.model.entity.dto.response.admin.dashboard.DashboardProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductDashboardRepository extends JpaRepository<DashboardProductDTO, Long> {
    @Query(value = "SELECT \n" +
            "    p.id AS id, " +
            "    p.product_name AS name_product,\n" +
            "    c.category_name AS name_category,\n" +
            "    b.brand_name AS name_brand,\n" +
            "    COALESCE(SUM(od.quantity), 0) AS input_quantity,\n" +
            "    COALESCE(SUM(id.quantity), 0) AS output_quantity,\n" +
            "    p.stock_quantity AS stock_quantity,\n" +
            "    GREATEST(COALESCE(MAX(od.created_date),  '1970-01-01'), COALESCE(MAX(id.created_date),  '1970-01-01')) AS created_date\n" +
            "FROM \n" +
            "    product p\n" +
            "LEFT JOIN \n" +
            "    category c ON p.category_id = c.id\n" +
            "LEFT JOIN \n" +
            "    brand b ON p.brand_id = b.id\n" +
            "LEFT JOIN \n" +
            "    (\n" +
            "        SELECT \n" +
            "            od.product_id,\n" +
            "            SUM(od.order_quantity) AS quantity,\n" +
            "            od.created_date\n" +
            "        FROM \n" +
            "            order_detail od \n" +
            "        JOIN \n" +
            "            orders o ON od.order_id = o.id\n" +
            "        WHERE\n" +
            "             od.created_date BETWEEN COALESCE(:createdDateStart, od.created_date) AND COALESCE(:createdDateEnd, od.created_date)\n" +
            "            AND o.order_status = 'CONFIRMED'\n" +
            "        GROUP BY \n" +
            "            od.product_id, od.created_date\n" +
            "    ) od ON p.id = od.product_id\n" +
            "LEFT JOIN \n" +
            "    (\n" +
            "        SELECT \n" +
            "            id.product_id,\n" +
            "            SUM(id.invoice_quantity) AS quantity,\n" +
            "            id.created_date\n" +
            "        FROM \n" +
            "            invoice_detail id \n" +
            "        JOIN \n" +
            "            invoice i ON id.invoice_id = i.id\n" +
            "        WHERE\n" +
            "             id.created_date BETWEEN COALESCE(:createdDateStart, id.created_date) AND COALESCE(:createdDateEnd, id.created_date)\n" +
            "            AND i.invoice_status = 'SUCCESS'\n" +
            "        GROUP BY \n" +
            "            id.product_id, id.created_date\n" +
            "    ) id ON p.id = id.product_id\n" +
            "WHERE \n" +
            "    (p.brand_id = COALESCE(:brandId, p.brand_id)) \n" +
            "    AND (p.category_id = COALESCE(:categoryId, p.category_id))\n" +
            "GROUP BY \n" +
            "    p.id, p.product_name, c.category_name, b.brand_name, p.stock_quantity\n" +
            "ORDER BY \n" +
            "    created_date\n", nativeQuery = true)
    List<DashboardProductDTO> dashboardProduct(String createdDateStart, String createdDateEnd, String brandId, String categoryId);
}
