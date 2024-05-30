package com.ra.model.service;

import com.ra.model.entity.Address;
import com.ra.model.entity.User;
import com.ra.model.entity.dto.request.user.AddressRequestDTO;
import com.ra.model.entity.dto.response.user.AddressResponseDTO;


import java.util.List;

public interface AddressService {
    List<Address> getAllByUser(User user);
    List<AddressResponseDTO> displayAllByUser(User user);
    Address save(Address address);
    void delete(Long id);
    Address findById(Long id);
    void addAddress(AddressRequestDTO addressRequest);
    void updateAddress(AddressRequestDTO addressRequest, Long addressId);
    AddressResponseDTO UEntityMap(Address address);
    Address UEntityMap(AddressRequestDTO addressRequest);
}
