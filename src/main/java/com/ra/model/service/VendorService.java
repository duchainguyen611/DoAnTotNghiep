package com.ra.model.service;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Vendor;
import com.ra.model.entity.dto.request.admin.AVendorRequestDTO;
import com.ra.model.entity.dto.request.admin.AVendorUpdateRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.AVendorResponseDTO;

import java.util.List;

public interface VendorService {
    List<Vendor> getAll();
    List<AVendorResponseDTO> displayAll();
    List<AVendorResponseDTO> findAllByStatus(ActiveStatus status);
    Vendor save(Vendor vendor);
    void delete(Long id);
    Vendor findById(Long id);
    void changeStatus(Long id);
    void addVendor(AVendorRequestDTO aVendorRequest);
    StringError updateVendor(AVendorUpdateRequestDTO aVendorUpdateRequest , Long vendorID);
    Vendor AEntityMapRequest(AVendorRequestDTO aVendorRequest);
    AVendorUpdateRequestDTO AEntityMapRequest(Vendor vendor);
    Vendor AEntityMapRequest(AVendorUpdateRequestDTO aVendorUpdateRequest);
    AVendorResponseDTO AEntityMapResponse(Vendor vendor);
}
