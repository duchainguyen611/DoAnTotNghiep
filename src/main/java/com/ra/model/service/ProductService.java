package com.ra.model.service;

import com.ra.model.entity.Category;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Product;
import com.ra.model.entity.dto.request.admin.AProductRequestDTO;
import com.ra.model.entity.dto.request.admin.AProductUpdateRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.AProductDetailResponseDTO;
import com.ra.model.entity.dto.response.admin.AProductResponseDTO;
import com.ra.model.entity.dto.response.user.UProductResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<Product> findAllByStockQuantity(Integer stockQuantity);
    List<Product> findAllByStockQuantityNotLike(Integer stockQuantity);
    List<AProductResponseDTO> findAllByCategoryAndBrand(String categoryId,String brandId);

    List<AProductResponseDTO> displayAll();

    List<AProductResponseDTO> AFindAllByStatus(ActiveStatus status);

    Page<UProductResponseDTO> UFindAllByStatusActive(Integer pageNo);

    List<UProductResponseDTO> getProductBestSellingByInvoice(Integer size);

    List<UProductResponseDTO> getAllByStatusOrderByIdDesc(Integer size, ActiveStatus status);

    List<UProductResponseDTO> getRandomProduct(Integer start, Integer size);

    List<UProductResponseDTO> findAllByCategoryAndProductName(String keyword, String categoryId);

    Page<UProductResponseDTO> findAllByCategoryAndProductNamePage(String keyword, Integer pageNo, String categoryId);

    Page<UProductResponseDTO> sortPageFilterProduct(String brandId, String categoryId, String startPrice, String endPrice, String field, String sort, Integer pageNo);

    Product save(Product product);

    void delete(Long id);

    Product findById(Long id);

    List<UProductResponseDTO> findByCategory(Category category, Long id);

    void changeStatus(Long id);

    void addProduct(AProductRequestDTO aProductRequest);

    StringError updateProduct(AProductUpdateRequestDTO aProductUpdateRequest, Long productId);

    Product AEntityMapRequest(AProductRequestDTO aProductRequest);

    AProductUpdateRequestDTO AEntityMapRequest(Product product);

    Product AEntityMapRequest(AProductUpdateRequestDTO aProductUpdateRequest);

    AProductResponseDTO AEntityMapResponse(Product product);

    AProductDetailResponseDTO AEntityMapDetailResponse(Product product);


    UProductResponseDTO UEntityMapResponse(Product product);
}
