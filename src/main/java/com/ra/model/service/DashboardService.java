package com.ra.model.service;

import com.ra.model.entity.dto.response.admin.dashboard.DashboardImportGoodsDTO;
import com.ra.model.entity.dto.response.admin.dashboard.DashboardProductDTO;
import com.ra.model.entity.dto.response.admin.dashboard.DashboardRevenueDTO;
import com.ra.model.entity.dto.response.admin.dashboard.DashboardSellGoodsDTO;

import java.util.List;

public interface DashboardService {
    List<DashboardProductDTO> dashboardProduct(String createdDateStart, String createdDateEnd, String brandId, String categoryId);
    List<DashboardRevenueDTO> dashboardRevenue(String createdDateStart, String createdDateEnd);
    List<DashboardImportGoodsDTO> dashboardImportGoods(String createdDateStart, String createdDateEnd, String brandId, String categoryId,String vendorId);
    List<DashboardSellGoodsDTO> dashboardSellGoods(String createdDateStart, String createdDateEnd, String brandId, String categoryId);


}
