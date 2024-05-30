package com.ra.model.serviceImp;

import com.ra.model.entity.Category;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Product;
import com.ra.model.entity.dto.request.admin.AProductRequestDTO;
import com.ra.model.entity.dto.request.admin.AProductUpdateRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.AProductDetailResponseDTO;
import com.ra.model.entity.dto.response.admin.AProductResponseDTO;
import com.ra.model.entity.dto.response.user.UProductResponseDTO;
import com.ra.model.repository.ProductRepository;
import com.ra.model.service.BrandService;
import com.ra.model.service.CategoryService;
import com.ra.model.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllByStockQuantity(Integer stockQuantity) {
        return productRepository.findAllByStockQuantity(stockQuantity);
    }

    @Override
    public List<Product> findAllByStockQuantityNotLike(Integer stockQuantity) {
        return productRepository.findAllByStockQuantityNotLike(stockQuantity);
    }

    @Override
    public List<AProductResponseDTO> findAllByCategoryAndBrand(String categoryId, String brandId) {
        return productRepository.findAllByCategoryAndBrand(categoryId,brandId).stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public List<AProductResponseDTO> displayAll() {
        return findAll().stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public List<AProductResponseDTO> AFindAllByStatus(ActiveStatus status) {
        return productRepository.findAllByStatus(status).stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public Page<UProductResponseDTO> UFindAllByStatusActive(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,12);
        return productRepository.findAllByStatusOrderByIdDesc(ActiveStatus.ACTIVE,pageable).map(this::UEntityMapResponse);
    }

    @Override
    public List<UProductResponseDTO> getProductBestSellingByInvoice(Integer size) {
        List<UProductResponseDTO> products = productRepository.getProductBestSellingByInvoice()
                .stream()
                .map(this::UEntityMapResponse)
                .toList();
        return products.subList(0, Math.min(size, products.size()));
    }

    @Override
    public List<UProductResponseDTO> getAllByStatusOrderByIdDesc(Integer size, ActiveStatus status) {
        List<UProductResponseDTO> products = productRepository.getAllByStatusOrderByIdDesc(status)
                .stream()
                .map(this::UEntityMapResponse)
                .toList();
        return products.subList(0, Math.min(size, products.size()));
    }

    @Override
    public List<UProductResponseDTO> getRandomProduct(Integer start, Integer size) {
        List<UProductResponseDTO> products = productRepository.getAllByRandom()
                .stream()
                .map(this::UEntityMapResponse)
                .toList();
        return products.subList(start, Math.min(size, products.size()));
    }

    @Override
    public List<UProductResponseDTO> findAllByCategoryAndProductName(String keyword, String categoryId) {
        return productRepository.findAllByCategoryAndProductName(categoryId,keyword).stream().map(this::UEntityMapResponse).toList();
    }


    @Override
    public Page<UProductResponseDTO> findAllByCategoryAndProductNamePage(String keyword, Integer pageNo, String categoryId) {
        Pageable pageable = PageRequest.of(pageNo-1,12);
        List<UProductResponseDTO> products = findAllByCategoryAndProductName(keyword,categoryId);
        int start = (int) pageable.getOffset();
        int end =(int) ((pageable.getOffset()+pageable.getPageSize())> products.size()?products.size(): pageable.getOffset() + pageable.getPageSize());
        products = products.subList(start,end);
        return new PageImpl<>(products,pageable,findAllByCategoryAndProductName(keyword,categoryId).size());
    }

    @Override
    public Page<UProductResponseDTO> sortPageFilterProduct(String brandId, String categoryId, String startPrice, String endPrice, String field, String sort, Integer pageNo) {
        Pageable pageable;
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(pageNo - 1, 12, Sort.by(field).descending());
        } else {
            pageable = PageRequest.of(pageNo - 1, 12, Sort.by(field));
        }
        return productRepository.sortPageFilterProduct(brandId, categoryId, startPrice, endPrice,pageable).map(this::UEntityMapResponse);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long Id) {
        productRepository.deleteById(Id);
    }


    @Override
    public Product findById(Long Id) {
        return productRepository.findById(Id).orElse(null);
    }

    @Override
    public List<UProductResponseDTO> findByCategory(Category category, Long id) {
        return productRepository.getAllByCategory(category,id).stream().map(this::UEntityMapResponse).toList();
    }

    @Override
    public void changeStatus(Long id) {
        Product product = findById(id);
        switch (product.getStatus()){
            case ACTIVE -> product.setStatus(ActiveStatus.INACTIVE);
            case INACTIVE -> product.setStatus(ActiveStatus.ACTIVE);
        }
        save(product);
    }

    @Override
    public void addProduct(AProductRequestDTO aProductRequest) {
        Product product = AEntityMapRequest(aProductRequest);
        product.setStatus(ActiveStatus.ACTIVE);
        save(product);
    }

    @Override
    public StringError updateProduct(AProductUpdateRequestDTO aProductUpdateRequest, Long productId) {
        Product productOld = findById(productId);
        Product productNew = AEntityMapRequest(aProductUpdateRequest);
        productNew.setStatus(productOld.getStatus());
        productNew.setId(productOld.getId());
        productNew.setCreatedDate(productOld.getCreatedDate());
        boolean isProductNameExist = productRepository.existsByProductName(productNew.getProductName())
                &&!productOld.getProductName().equals(productNew.getProductName());
        StringError stringError = new StringError();
        if (isProductNameExist){
            stringError.setName("Tên sản phẩm đã được sử dụng!");
            return stringError;
        }
        save(productNew);
        return null;
    }

    @Override
    public Product AEntityMapRequest(AProductRequestDTO aProductRequest) {
        return Product.builder()
                .productName(aProductRequest.getProductName())
                .description(aProductRequest.getDescription())
                .unitPrice(aProductRequest.getUnitPrice())
                .stockQuantity(aProductRequest.getStockQuantity())
                .image(aProductRequest.getImage())
                .category(categoryService.findById(aProductRequest.getCategoryId()))
                .brand(brandService.findById(aProductRequest.getBrandId()))
                .build();
    }


    @Override
    public AProductUpdateRequestDTO AEntityMapRequest(Product product) {
        return AProductUpdateRequestDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .stockQuantity(product.getStockQuantity())
                .image(product.getImage())
                .categoryId(product.getCategory().getId())
                .brandId(product.getBrand().getId())
                .build();
    }

    @Override
    public Product AEntityMapRequest(AProductUpdateRequestDTO aProductUpdateRequest) {
        return Product.builder()
                .productName(aProductUpdateRequest.getProductName())
                .description(aProductUpdateRequest.getDescription())
                .unitPrice(aProductUpdateRequest.getUnitPrice())
                .stockQuantity(aProductUpdateRequest.getStockQuantity())
                .image(aProductUpdateRequest.getImage())
                .category(categoryService.findById(aProductUpdateRequest.getCategoryId()))
                .brand(brandService.findById(aProductUpdateRequest.getBrandId()))
                .build();
    }

    @Override
    public AProductResponseDTO AEntityMapResponse(Product product) {
        return AProductResponseDTO.builder()
                .id(product.getId())
                .image(product.getImage())
                .categoryName(product.getCategory().getCategoryName())
                .productName(product.getProductName())
                .unitPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(product.getUnitPrice())+ "₫")
                .productStatus(product.getStatus().toString())
                .stockQuantity(product.getStockQuantity())
                .build();
    }

    @Override
    public AProductDetailResponseDTO AEntityMapDetailResponse(Product product) {
        return AProductDetailResponseDTO.builder()
                .id(product.getId())
                .brandName(product.getBrand().getBrandName())
                .categoryName(product.getCategory().getCategoryName())
                .productName(product.getProductName())
                .description(product.getDescription())
                .unitPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(product.getUnitPrice())+ "₫")
                .stockQuantity(product.getStockQuantity())
                .image(product.getImage())
                .status(product.getStatus().toString())
                .createdDate(product.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .modifyDate(product.getModifyDate()==null?null:product.getModifyDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
    }


    @Override
    public UProductResponseDTO UEntityMapResponse(Product product) {
        return UProductResponseDTO.builder()
                .productName(product.getProductName())
                .id(product.getId())
                .brand(product.getBrand())
                .category(product.getCategory())
                .image(product.getImage())
                .description(product.getDescription())
                .unitPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(product.getUnitPrice())+ "₫")
                .stockQuantity(product.getStockQuantity())
                .build();
    }
}
