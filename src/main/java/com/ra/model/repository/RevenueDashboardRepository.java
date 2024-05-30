package com.ra.model.repository;

import com.ra.model.entity.dto.response.admin.dashboard.DashboardRevenueDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenueDashboardRepository extends JpaRepository<DashboardRevenueDTO,Long> {
    @Query(value = "SELECT \n" +
            "    ROW_NUMBER() OVER(ORDER BY createdDate) as id,\n" +
            "    createdDate as created_date,\n" +
            "    SUM(importAmount) AS import_amount,\n" +
            "    SUM(saleAmount) AS sale_amount,\n" +
            "    (SUM(saleAmount) - SUM(importAmount)) AS total_amount\n" +
            "FROM (\n" +
            "    SELECT \n" +
            "        o.created_date AS createdDate,\n" +
            "        SUM(o.total_price) AS importAmount,\n" +
            "        0 AS saleAmount\n" +
            "    FROM \n" +
            "        orders o\n" +
            "    WHERE \n" +
            "        o.created_date BETWEEN COALESCE(:createdDateStart, o.created_date) AND COALESCE(:createdDateEnd, o.created_date)\n" +
            "            AND o.order_status = 'CONFIRMED'\n" +
            "    GROUP BY \n" +
            "        o.created_date\n" +
            "    UNION ALL\n" +
            "    SELECT \n" +
            "        i.created_date AS createdDate,\n" +
            "        0 AS importAmount,\n" +
            "        SUM(i.total_price) AS saleAmount\n" +
            "    FROM \n" +
            "        invoice i\n" +
            "    WHERE \n" +
            "        i.created_date BETWEEN COALESCE(:createdDateStart, i.created_date) AND COALESCE(:createdDateEnd, i.created_date)\n" +
            "            AND i.invoice_status = 'SUCCESS'\n" +
            "    GROUP BY \n" +
            "        i.created_date\n" +
            ") AS combined\n" +
            "GROUP BY \n" +
            "    createdDate\n" +
            "ORDER BY \n" +
            "    createdDate",
            nativeQuery = true)
    List<DashboardRevenueDTO> dashboardRevenue(String createdDateStart, String createdDateEnd);



}
