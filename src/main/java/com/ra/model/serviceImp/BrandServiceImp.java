package com.ra.model.serviceImp;

import com.ra.model.entity.Brand;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.dto.request.admin.ABrandUpdateRequestDTO;
import com.ra.model.entity.dto.request.admin.ABrandRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.ABrandResponseDTO;
import com.ra.model.entity.dto.response.user.UBrandResponseDTO;
import com.ra.model.repository.BrandRepository;
import com.ra.model.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImp implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public List<ABrandResponseDTO> displayAll() {
        return getAll().stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public List<ABrandResponseDTO> AFindAllByStatus(ActiveStatus status) {
        return brandRepository.findAllByStatus(status).stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public List<UBrandResponseDTO> UFindAllByStatus(ActiveStatus status) {
        return brandRepository.findAllByStatus(status).stream().map(this::UEntityMapResponse).toList();
    }


    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public void delete(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public Brand findById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }


    @Override
    public void changeStatus(Long id) {
        Brand brand = findById(id);
        switch (brand.getStatus()) {
            case ACTIVE -> brand.setStatus(ActiveStatus.INACTIVE);
            case INACTIVE -> brand.setStatus(ActiveStatus.ACTIVE);
        }
        save(brand);
    }

    @Override
    public void addBrand(ABrandRequestDTO aBrandRequest) {
        Brand brand = AEntityMapRequest(aBrandRequest);
        brand.setStatus(ActiveStatus.ACTIVE);
        save(brand);
    }

    @Override
    public StringError updateBrand(ABrandUpdateRequestDTO aBrandUpdateRequest, Long bardId) {
        Brand brandOld = findById(bardId);
        Brand brandNew = AEntityMapRequest(aBrandUpdateRequest);
        brandNew.setId(brandOld.getId());
        brandNew.setStatus(brandOld.getStatus());
        brandNew.setCreatedDate(brandOld.getCreatedDate());
        boolean isPhoneExist = brandRepository.existsByPhone(brandNew.getPhone())&&!brandOld.getPhone().equals(brandNew.getPhone());
        boolean isEmailExist = brandRepository.existsByEmail(brandNew.getEmail())&&!brandOld.getEmail().equals(brandNew.getEmail());
        boolean isBrandNameExist = brandRepository.existsByBrandName(brandNew.getBrandName())&&!brandOld.getBrandName().equals(brandNew.getBrandName());
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
            save(brandNew);
            return null;
        }
    }

    @Override
    public Brand AEntityMapRequest(ABrandRequestDTO aBrandRequest) {
        return Brand.builder()
                .brandName(aBrandRequest.getBrandName())
                .address(aBrandRequest.getAddress())
                .image(aBrandRequest.getImage())
                .phone(aBrandRequest.getPhone())
                .email(aBrandRequest.getEmail())
                .build();
    }

    @Override
    public ABrandUpdateRequestDTO AEntityMapRequest(Brand brand) {
        return ABrandUpdateRequestDTO.builder()
                .id(brand.getId())
                .brandName(brand.getBrandName())
                .address(brand.getAddress())
                .image(brand.getImage())
                .phone(brand.getPhone())
                .email(brand.getEmail())
                .build();
    }

    @Override
    public Brand AEntityMapRequest(ABrandUpdateRequestDTO aBrandUpdateRequest) {
        return Brand.builder()
                .brandName(aBrandUpdateRequest.getBrandName())
                .address(aBrandUpdateRequest.getAddress())
                .image(aBrandUpdateRequest.getImage())
                .phone(aBrandUpdateRequest.getPhone())
                .email(aBrandUpdateRequest.getEmail())
                .build();
    }

    @Override
    public ABrandResponseDTO AEntityMapResponse(Brand brand) {
        return ABrandResponseDTO.builder()
                .id(brand.getId())
                .image(brand.getImage())
                .brandName(brand.getBrandName())
                .address(brand.getAddress())
                .email(brand.getEmail())
                .phone(brand.getPhone())
                .status(brand.getStatus().toString())
                .build();
    }

    @Override
    public UBrandResponseDTO UEntityMapResponse(Brand brand) {
        return UBrandResponseDTO.builder()
                .id(brand.getId())
                .brandName(brand.getBrandName())
                .image(brand.getImage())
                .build();
    }
}
