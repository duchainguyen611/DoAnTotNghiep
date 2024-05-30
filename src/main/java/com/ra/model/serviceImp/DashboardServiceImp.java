package com.ra.model.serviceImp;

import com.ra.model.entity.dto.response.admin.dashboard.DashboardImportGoodsDTO;
import com.ra.model.entity.dto.response.admin.dashboard.DashboardProductDTO;
import com.ra.model.entity.dto.response.admin.dashboard.DashboardRevenueDTO;
import com.ra.model.entity.dto.response.admin.dashboard.DashboardSellGoodsDTO;
import com.ra.model.repository.ImportGoodsDashboardRepository;
import com.ra.model.repository.ProductDashboardRepository;
import com.ra.model.repository.RevenueDashboardRepository;
import com.ra.model.repository.SellGoodsDashboardRepository;
import com.ra.model.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DashboardServiceImp implements DashboardService {
    private final ProductDashboardRepository productDashboardRepository;
    private final RevenueDashboardRepository revenueDashboardRepository;
    private final ImportGoodsDashboardRepository importGoodsDashboardRepository;
    private final SellGoodsDashboardRepository sellGoodsDashboardRepository;

    @Override
    public List<DashboardProductDTO> dashboardProduct(String createdDateStart, String createdDateEnd, String brandId, String categoryId) {
        return productDashboardRepository.dashboardProduct(createdDateStart,createdDateEnd,brandId,categoryId);
    }

    @Override
    public List<DashboardRevenueDTO> dashboardRevenue(String createdDateStart, String createdDateEnd) {
        return revenueDashboardRepository.dashboardRevenue(createdDateStart,createdDateEnd);
    }

    @Override
    public List<DashboardImportGoodsDTO> dashboardImportGoods(String createdDateStart, String createdDateEnd, String brandId, String categoryId, String vendorId) {
        return importGoodsDashboardRepository.dashboardImportGoods(createdDateStart,createdDateEnd,brandId,categoryId,vendorId);
    }

    @Override
    public List<DashboardSellGoodsDTO> dashboardSellGoods(String createdDateStart, String createdDateEnd, String brandId, String categoryId) {
        return sellGoodsDashboardRepository.dashboardSellGoods(createdDateStart,createdDateEnd,brandId,categoryId);
    }


}
