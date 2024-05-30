package com.ra.model.service;

import com.ra.model.entity.Brand;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.dto.request.admin.ABrandUpdateRequestDTO;
import com.ra.model.entity.dto.request.admin.ABrandRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.ABrandResponseDTO;
import com.ra.model.entity.dto.response.user.UBrandResponseDTO;

import java.util.List;


public interface BrandService{
    List<Brand> getAll();
    List<ABrandResponseDTO> displayAll();
    List<ABrandResponseDTO> AFindAllByStatus(ActiveStatus status);
    List<UBrandResponseDTO> UFindAllByStatus(ActiveStatus status);
    Brand save(Brand brand);
    void delete(Long id);
    Brand findById(Long id);
    void changeStatus(Long id);
    void addBrand(ABrandRequestDTO aBrandRequest);
    StringError updateBrand(ABrandUpdateRequestDTO aBrandUpdateRequest, Long bardId);
    Brand AEntityMapRequest(ABrandRequestDTO aBrandRequest);
    ABrandUpdateRequestDTO AEntityMapRequest(Brand brand);
    Brand AEntityMapRequest(ABrandUpdateRequestDTO aBrandUpdateRequest);
    ABrandResponseDTO AEntityMapResponse(Brand brand);
    UBrandResponseDTO UEntityMapResponse(Brand brand);
}
