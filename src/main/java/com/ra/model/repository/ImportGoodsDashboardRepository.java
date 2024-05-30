package com.ra.model.repository;

import com.ra.model.entity.dto.response.admin.dashboard.DashboardImportGoodsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportGoodsDashboardRepository extends JpaRepository<DashboardImportGoodsDTO,Long> {
    @Query(value = "SELECT \n" +
            "    p.id AS id,\n " +
            "    p.product_name AS product_name,\n" +
            "    od.vendor_name AS vendor_name,\n" +
            "    c.category_name AS category_name,\n" +
            "    b.brand_name AS brand_name,\n" +
            "    COALESCE(SUM(od.quantity), 0) AS input_quantity,\n" +
            "    COALESCE(SUM(od.amount), 0) AS total_amount,\n" +
            "    COALESCE(MAX(od.created_date),  '1970-01-01') AS created_date\n" +
            "FROM \n" +
            "    product p\n" +
            "LEFT JOIN \n" +
            "    category c ON p.category_id = c.id\n" +
            "LEFT JOIN \n" +
            "    brand b ON p.brand_id = b.id\n" +
            "JOIN \n" +
            "    (\n" +
            "        SELECT \n" +
            "            od.product_id,\n" +
            "            SUM(od.order_quantity) AS quantity,\n" +
            "            SUM(od.order_quantity*od.unit_price_order) as amount,\n" +
            "            od.created_date,\n" +
            "            v.vendor_name,\n" +
            "            v.id as vendor_id\n" +
            "        FROM \n" +
            "            order_detail od \n" +
            "        JOIN \n" +
            "            orders o ON od.order_id = o.id\n" +
            "        JOIN\n" +
            "            vendor v on v.id = o.vendor_id\n" +
            "        WHERE\n" +
            "             od.created_date BETWEEN COALESCE(:createdDateStart, od.created_date) AND COALESCE(:createdDateEnd, od.created_date)\n" +
            "            AND o.order_status = 'CONFIRMED'\n" +
            "        GROUP BY \n" +
            "             od.product_id, od.created_date, v.id\n" +
            "    ) od ON p.id = od.product_id\n" +
            "WHERE \n" +
            "    (p.brand_id = COALESCE(:brandId, p.brand_id)) \n" +
            "    AND (p.category_id = COALESCE(:categoryId, p.category_id))\n" +
            "    AND (od.vendor_id = COALESCE(:vendorId, od.vendor_id))\n" +
            "GROUP BY \n" +
            "    p.id, p.product_name, c.category_name, b.brand_name, p.stock_quantity,od.vendor_id\n" +
            "ORDER BY \n" +
            "    created_date",nativeQuery = true)
    List<DashboardImportGoodsDTO> dashboardImportGoods(String createdDateStart, String createdDateEnd, String brandId, String categoryId,String vendorId);
}
