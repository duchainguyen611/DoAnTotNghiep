package com.ra.controller.admin.dashboard;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.dto.response.admin.ABrandResponseDTO;
import com.ra.model.entity.dto.response.admin.ACategoryResponseDTO;
import com.ra.model.entity.dto.response.admin.AVendorResponseDTO;
import com.ra.model.entity.dto.response.admin.dashboard.*;
import com.ra.model.service.*;
import com.ra.model.serviceImp.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DashboardController{
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final DashboardService dashboardService;
    private final VendorService vendorService;
    private final ExcelService excelService;
    @GetMapping("/dashboardProduct")
    public String dashboardProduct(Model model
            , @RequestParam(name = "createdDateStart",required = false)String createdDateStart
            , @RequestParam(name = "createdDateEnd",required = false)String createdDateEnd
            , @RequestParam(name = "brandId",required = false)String brandId
            , @RequestParam(name = "categoryId",required = false)String categoryId){
        List<ACategoryResponseDTO> categories = categoryService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories", categories);
        List<ABrandResponseDTO> brands = brandService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("brands", brands);
        List<DashboardProductDTO> products = dashboardService.dashboardProduct(createdDateStart,createdDateEnd,brandId,categoryId);
        model.addAttribute("products",products);
        model.addAttribute("selectedBrandId",brandId);
        model.addAttribute("selectedCategoryId",categoryId);
        model.addAttribute("createdDateStart",createdDateStart);
        model.addAttribute("createdDateEnd",createdDateEnd);
        return "admin/dashbroad/dashboardProduct";
    }

    @GetMapping("/dashboardRevenue")
    public String dashboardRevenue(Model model
            , @RequestParam(name = "createdDateStart",required = false)String createdDateStart
            , @RequestParam(name = "createdDateEnd",required = false)String createdDateEnd){
        List<DashboardRevenueDTO> revenues = dashboardService.dashboardRevenue(createdDateStart,createdDateEnd);
        model.addAttribute("revenues",revenues);
        model.addAttribute("createdDateStart",createdDateStart);
        model.addAttribute("createdDateEnd",createdDateEnd);
        return "admin/dashbroad/dashboardRevenues";
    }

    @GetMapping("/dashboardImportGoods")
    public String dashboardImportGoods(Model model
            , @RequestParam(name = "createdDateStart",required = false)String createdDateStart
            , @RequestParam(name = "createdDateEnd",required = false)String createdDateEnd
            , @RequestParam(name = "brandId",required = false)String brandId
            , @RequestParam(name = "categoryId",required = false)String categoryId
            , @RequestParam(name = "vendorId",required = false)String vendorId){
        List<ACategoryResponseDTO> categories = categoryService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories", categories);
        List<ABrandResponseDTO> brands = brandService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("brands", brands);
        List<AVendorResponseDTO> vendors = vendorService.findAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("vendors", vendors);
        List<DashboardImportGoodsDTO> importGoods = dashboardService.dashboardImportGoods(createdDateStart,createdDateEnd,brandId,categoryId,vendorId);
        model.addAttribute("importGoods",importGoods);
        model.addAttribute("createdDateStart",createdDateStart);
        model.addAttribute("createdDateEnd",createdDateEnd);
        model.addAttribute("selectedBrandId",brandId);
        model.addAttribute("selectedCategoryId",categoryId);
        model.addAttribute("selectedVendorId",vendorId);
        return "admin/dashbroad/dashboardImportGoods";
    }

    @GetMapping("/dashboardSellGoods")
    public String dashboardSellGoods(Model model
            , @RequestParam(name = "createdDateStart",required = false)String createdDateStart
            , @RequestParam(name = "createdDateEnd",required = false)String createdDateEnd
            , @RequestParam(name = "brandId",required = false)String brandId
            , @RequestParam(name = "categoryId",required = false)String categoryId){
        List<ACategoryResponseDTO> categories = categoryService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("categories", categories);
        List<ABrandResponseDTO> brands = brandService.AFindAllByStatus(ActiveStatus.ACTIVE);
        model.addAttribute("brands", brands);
        List<DashboardSellGoodsDTO> sellGoods = dashboardService.dashboardSellGoods(createdDateStart,createdDateEnd,brandId,categoryId);
        model.addAttribute("sellGoods",sellGoods);
        model.addAttribute("createdDateStart",createdDateStart);
        model.addAttribute("createdDateEnd",createdDateEnd);
        model.addAttribute("selectedBrandId",brandId);
        model.addAttribute("selectedCategoryId",categoryId);
        return "admin/dashbroad/dashboardSellGoods";
    }

    @GetMapping("/exportExcelDashboardProduct")
    public ResponseEntity<byte[]> exportExcelDashboardProduct(
            @RequestParam(name = "createdDateStart",required = false)String createdDateStart
            , @RequestParam(name = "createdDateEnd",required = false)String createdDateEnd
            , @RequestParam(name = "brandId",required = false)String brandId
            , @RequestParam(name = "categoryId",required = false)String categoryId){
        try {
            List<DashboardProductDTO> products = dashboardService.dashboardProduct(createdDateStart,createdDateEnd,brandId,categoryId);
            int id = 1;
            for (DashboardProductDTO productDTO : products){
                productDTO.setId((long) id++);
            }
            ByteArrayInputStream in = excelService.exportToExcel(products);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            headers.setContentDispositionFormData("attachment", "data.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(in.readAllBytes(), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/exportExcelDashboardRevenue")
    public ResponseEntity<byte[]> exportExcelDashboardRevenue(
            @RequestParam(name = "createdDateStart",required = false)String createdDateStart
            , @RequestParam(name = "createdDateEnd",required = false)String createdDateEnd){
        try {
            List<DashboardRevenueDTO> revenues = dashboardService.dashboardRevenue(createdDateStart,createdDateEnd);
            ByteArrayInputStream in = excelService.exportToExcel(revenues);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            headers.setContentDispositionFormData("attachment", "data.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(in.readAllBytes(), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/exportExcelDashboardImportGoods")
    public ResponseEntity<byte[]> exportToExcel(
            @RequestParam(name = "createdDateStart",required = false)String createdDateStart
            , @RequestParam(name = "createdDateEnd",required = false)String createdDateEnd
            , @RequestParam(name = "brandId",required = false)String brandId
            , @RequestParam(name = "categoryId",required = false)String categoryId
            , @RequestParam(name = "vendorId",required = false)String vendorId){
        try {
            List<DashboardImportGoodsDTO> importGoods = dashboardService.dashboardImportGoods(createdDateStart,createdDateEnd,brandId,categoryId,vendorId);
            int id = 1;
            for (DashboardImportGoodsDTO importGoodsDTO:importGoods){
                importGoodsDTO.setId((long) id++);
            }
            ByteArrayInputStream in = excelService.exportToExcel(importGoods);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            headers.setContentDispositionFormData("attachment", "data.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(in.readAllBytes(), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/exportExcelDashboardSellGoods")
    public ResponseEntity<byte[]> exportExcelDashboardSellGoods(
            @RequestParam(name = "createdDateStart",required = false)String createdDateStart
            , @RequestParam(name = "createdDateEnd",required = false)String createdDateEnd
            , @RequestParam(name = "brandId",required = false)String brandId
            , @RequestParam(name = "categoryId",required = false)String categoryId){
        try {
            List<DashboardSellGoodsDTO> sellGoods = dashboardService.dashboardSellGoods(createdDateStart,createdDateEnd,brandId,categoryId);
            int id = 1;
            for (DashboardSellGoodsDTO sellGoodsDTO:sellGoods){
                sellGoodsDTO.setId((long) id++);
            }
            ByteArrayInputStream in = excelService.exportToExcel(sellGoods);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            headers.setContentDispositionFormData("attachment", "data.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(in.readAllBytes(), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
