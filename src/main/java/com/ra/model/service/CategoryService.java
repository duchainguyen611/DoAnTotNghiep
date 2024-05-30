package com.ra.model.service;

import com.ra.model.entity.Category;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.dto.request.admin.ACategoryRequestDTO;
import com.ra.model.entity.dto.request.admin.ACategoryUpdateRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.ACategoryResponseDTO;
import com.ra.model.entity.dto.response.user.UCategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    List<ACategoryResponseDTO> AFindAllByStatus(ActiveStatus status);
    List<UCategoryResponseDTO> UFindAllByStatus(ActiveStatus status);
    List<Category> getAll();
    List<ACategoryResponseDTO> displayAll();
    Category save(Category category);
    void delete(Long id);
    Category findById(Long id);
    void changeStatus(Long id);
    void addCategory(ACategoryRequestDTO aCategoryRequest);
    StringError updateCategory(ACategoryUpdateRequestDTO aCategoryUpdateRequest, Long categoryId);
    Category AEntityMapRequest(ACategoryRequestDTO aCategoryRequest);
    ACategoryUpdateRequestDTO AEntityMapRequest(Category category);
    Category AEntityMapRequest(ACategoryUpdateRequestDTO aCategoryUpdateRequest);
    ACategoryResponseDTO AEntityMapResponse(Category category);
    UCategoryResponseDTO UEntityMapResponse(Category category);
}
