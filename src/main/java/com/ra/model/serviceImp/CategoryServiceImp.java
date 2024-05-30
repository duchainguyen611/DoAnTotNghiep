package com.ra.model.serviceImp;

import com.ra.model.entity.Category;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.dto.request.admin.ACategoryRequestDTO;
import com.ra.model.entity.dto.request.admin.ACategoryUpdateRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.ACategoryResponseDTO;
import com.ra.model.entity.dto.response.user.UCategoryResponseDTO;
import com.ra.model.repository.CategoryRepository;
import com.ra.model.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public List<ACategoryResponseDTO> AFindAllByStatus(ActiveStatus status) {
        return categoryRepository.findAllByStatus(status).stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public List<UCategoryResponseDTO> UFindAllByStatus(ActiveStatus status) {
        return categoryRepository.findAllByStatus(status).stream().map(this::UEntityMapResponse).toList();
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<ACategoryResponseDTO> displayAll() {
        return getAll().stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long Id) {
        categoryRepository.deleteById(Id);
    }


    @Override
    public Category findById(Long Id) {
        return categoryRepository.findById(Id).orElse(null);
    }

    @Override
    public void changeStatus(Long id) {
        Category category  = findById(id);
        switch (category.getStatus()){
            case ACTIVE -> category.setStatus(ActiveStatus.INACTIVE);
            case INACTIVE -> category.setStatus(ActiveStatus.ACTIVE);
        }
        save(category);
    }

    @Override
    public void addCategory(ACategoryRequestDTO aCategoryRequest) {
        Category category = AEntityMapRequest(aCategoryRequest);
        category.setStatus(ActiveStatus.ACTIVE);
        save(category);
    }

    @Override
    public StringError updateCategory(ACategoryUpdateRequestDTO aCategoryUpdateRequest, Long categoryId) {
        Category categoryOld = findById(categoryId);
        Category categoryNew = AEntityMapRequest(aCategoryUpdateRequest);
        categoryNew.setId(categoryOld.getId());
        categoryNew.setCreatedDate(categoryOld.getCreatedDate());
        categoryNew.setStatus(categoryOld.getStatus());
        boolean isCategoryNameExist = categoryRepository.existsByCategoryName(categoryNew.getCategoryName())
                &&!categoryOld.getCategoryName().equals(categoryNew.getCategoryName());
        StringError stringError = new StringError();
        if (isCategoryNameExist){
            stringError.setName("Tên danh mục đã được sử dụng!");
            return stringError;
        }
        save(categoryNew);
        return null;
    }

    @Override
    public Category AEntityMapRequest(ACategoryRequestDTO aCategoryRequest) {
        return Category.builder()
                .categoryName(aCategoryRequest.getCategoryName())
                .description(aCategoryRequest.getDescription())
                .build();
    }

    @Override
    public ACategoryUpdateRequestDTO AEntityMapRequest(Category category) {
        return ACategoryUpdateRequestDTO.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .build();
    }

    @Override
    public Category AEntityMapRequest(ACategoryUpdateRequestDTO aCategoryUpdateRequest) {
        return Category.builder()
                .categoryName(aCategoryUpdateRequest.getCategoryName())
                .description(aCategoryUpdateRequest.getDescription())
                .build();
    }

    @Override
    public ACategoryResponseDTO AEntityMapResponse(Category category) {
        return ACategoryResponseDTO.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .status(category.getStatus().toString())
                .build();
    }

    @Override
    public UCategoryResponseDTO UEntityMapResponse(Category category) {
        return UCategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getCategoryName())
                .description(category.getDescription())
                .build();
    }
}
