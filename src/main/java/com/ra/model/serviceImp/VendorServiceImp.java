package com.ra.model.serviceImp;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.Vendor;
import com.ra.model.entity.dto.request.admin.AVendorRequestDTO;
import com.ra.model.entity.dto.request.admin.AVendorUpdateRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.AVendorResponseDTO;
import com.ra.model.repository.VendorRepository;
import com.ra.model.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorServiceImp implements VendorService {
    private final VendorRepository vendorRepository;

    @Override
    public List<Vendor> getAll() {
        return vendorRepository.findAll();
    }

    @Override
    public List<AVendorResponseDTO> displayAll() {
        return getAll().stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public List<AVendorResponseDTO> findAllByStatus(ActiveStatus status) {
        return vendorRepository.findAllByStatus(status).stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public Vendor save(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public void delete(Long Id) {
        vendorRepository.deleteById(Id);
    }


    @Override
    public Vendor findById(Long Id) {
        return vendorRepository.findById(Id).orElse(null);
    }

    @Override
    public void changeStatus(Long id) {
        Vendor vendor = findById(id);
        switch (vendor.getStatus()){
            case ACTIVE -> vendor.setStatus(ActiveStatus.INACTIVE);
            case INACTIVE -> vendor.setStatus(ActiveStatus.ACTIVE);
        }
        save(vendor);
    }

    @Override
    public void addVendor(AVendorRequestDTO aVendorRequest) {
        Vendor vendor = AEntityMapRequest(aVendorRequest);
        vendor.setStatus(ActiveStatus.ACTIVE);
        save(vendor);
    }

    @Override
    public StringError updateVendor(AVendorUpdateRequestDTO aVendorUpdateRequest, Long vendorID) {
        Vendor vendorOld = findById(vendorID);
        Vendor vendorNew = AEntityMapRequest(aVendorUpdateRequest);
        vendorNew.setId(vendorOld.getId());
        vendorNew.setStatus(vendorOld.getStatus());
        vendorNew.setCreatedDate(vendorOld.getCreatedDate());
        boolean isPhoneExist = vendorRepository.existsByPhone(vendorNew.getPhone())&&!vendorOld.getPhone().equals(vendorNew.getPhone());
        boolean isEmailExist = vendorRepository.existsByEmail(vendorNew.getEmail())&&!vendorOld.getEmail().equals(vendorNew.getEmail());
        boolean isBrandNameExist = vendorRepository.existsByVendorName(vendorNew.getVendorName())&&!vendorOld.getVendorName().equals(vendorNew.getVendorName());
        StringError stringError = new StringError();
        boolean isCheck = true;
        if (isPhoneExist){
            isCheck = false;
            stringError.setPhone("Số điện thoại này đã được sử dụng!");
        }
        if (isEmailExist) {
            isCheck = false;
            stringError.setEmail("Email này đã được sử dụng!");
        }
        if (isBrandNameExist) {
            isCheck = false;
            stringError.setName("Tên thương hiệu này đã được sử dụng!");
        }
        if (!isCheck){
            return stringError;
        }else {
            save(vendorNew);
            return null;
        }
    }

    @Override
    public Vendor AEntityMapRequest(AVendorRequestDTO aVendorRequest) {
        return Vendor.builder()
                .image(aVendorRequest.getImage())
                .vendorName(aVendorRequest.getVendorName())
                .address(aVendorRequest.getAddress())
                .email(aVendorRequest.getEmail())
                .phone(aVendorRequest.getPhone())
                .build();
    }

    @Override
    public AVendorUpdateRequestDTO AEntityMapRequest(Vendor vendor) {
        return AVendorUpdateRequestDTO.builder()
                .id(vendor.getId())
                .image(vendor.getImage())
                .vendorName(vendor.getVendorName())
                .address(vendor.getAddress())
                .email(vendor.getEmail())
                .phone(vendor.getPhone())
                .build();
    }

    @Override
    public Vendor AEntityMapRequest(AVendorUpdateRequestDTO aVendorUpdateRequest) {
        return Vendor.builder()
                .image(aVendorUpdateRequest.getImage())
                .vendorName(aVendorUpdateRequest.getVendorName())
                .address(aVendorUpdateRequest.getAddress())
                .email(aVendorUpdateRequest.getEmail())
                .phone(aVendorUpdateRequest.getPhone())
                .build();
    }

    @Override
    public AVendorResponseDTO AEntityMapResponse(Vendor vendor) {
        return AVendorResponseDTO.builder()
                .id(vendor.getId())
                .image(vendor.getImage())
                .vendorName(vendor.getVendorName())
                .address(vendor.getAddress())
                .email(vendor.getEmail())
                .phone(vendor.getPhone())
                .status(vendor.getStatus().toString())
                .build();
    }
}
