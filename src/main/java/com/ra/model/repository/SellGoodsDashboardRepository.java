package com.ra.model.repository;

import com.ra.model.entity.dto.response.admin.dashboard.DashboardSellGoodsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellGoodsDashboardRepository extends JpaRepository<DashboardSellGoodsDTO,Long> {
    @Query(value = "SELECT \n" +
            "    p.id AS id,\n " +
            "    p.product_name AS product_name,\n" +
            "    c.category_name AS category_name,\n" +
            "    b.brand_name AS brand_name,\n" +
            "    COALESCE(SUM(id.quantity), 0) AS output_quantity,\n" +
            "    COALESCE(SUM(id.amount), 0) AS total_amount,\n" +
            "    COALESCE(MAX(id.created_date),  '1970-01-01') AS created_date\n" +
            "FROM \n" +
            "    product p\n" +
            "LEFT JOIN \n" +
            "    category c ON p.category_id = c.id\n" +
            "LEFT JOIN \n" +
            "    brand b ON p.brand_id = b.id\n" +
            "JOIN \n" +
            "    (\n" +
            "        SELECT \n" +
            "            id.product_id,\n" +
            "            SUM(id.invoice_quantity) AS quantity,\n" +
            "            SUM(id.invoice_quantity*p.unit_price) as amount,\n" +
            "            id.created_date\n" +
            "        FROM \n" +
            "            invoice_detail id \n" +
            "        JOIN \n" +
            "            invoice i ON id.invoice_id = i.id\n" +
            "        JOIN \n" +
            "            product p ON id.product_id = p.id\n" +
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
            "    created_date;",nativeQuery = true)
    List<DashboardSellGoodsDTO> dashboardSellGoods(String createdDateStart, String createdDateEnd, String brandId, String categoryId);

}
