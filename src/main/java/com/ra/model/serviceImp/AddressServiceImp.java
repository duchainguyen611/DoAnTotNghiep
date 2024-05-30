package com.ra.model.serviceImp;

import com.ra.model.entity.Address;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.User;
import com.ra.model.entity.dto.request.user.AddressRequestDTO;
import com.ra.model.entity.dto.response.user.AddressResponseDTO;
import com.ra.model.repository.AddressRepository;
import com.ra.model.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AddressServiceImp implements AddressService {
    private final AddressRepository addressRepository;
    @Override
    public List<Address> getAllByUser(User user) {
        return addressRepository.findAllByUser(user);    }

    @Override
    public List<AddressResponseDTO> displayAllByUser(User user) {
        return getAllByUser(user).stream().map(this::UEntityMap).toList();
    }
    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void delete(Long Id) {
        addressRepository.deleteById(Id);
    }

    @Override
    public Address findById(Long Id) {
        return addressRepository.findById(Id).orElse(null);
    }

    @Override
    public void addAddress(AddressRequestDTO addressRequest) {
        if (addressRequest.getStatus()==null){
            addressRequest.setStatus(ActiveStatus.INACTIVE);
        }
        Address address = UEntityMap(addressRequest);
        save(address);
    }

    @Override
    public void updateAddress(AddressRequestDTO addressRequest, Long addressId) {
        Address addressOld = findById(addressId);
        Address addressNew = UEntityMap(addressRequest);
        addressNew.setId(addressOld.getId());
        addressNew.setUser(addressOld.getUser());
        save(addressNew);
    }

    @Override
    public AddressResponseDTO UEntityMap(Address address) {
        String[] listAddressReceive = address.getReceiveAddress().split(", ");
        return AddressResponseDTO.builder()
                .id(address.getId())
                .receiveAddress(address.getReceiveAddress())
                .receiveName(address.getReceiveName())
                .receivePhone(address.getReceivePhone())
                .address1(listAddressReceive[0])
                .address2(listAddressReceive[1])
                .address3(listAddressReceive[2])
                .address4(listAddressReceive[3])
                .build();
    }

    @Override
    public Address UEntityMap(AddressRequestDTO addressRequest) {
        String addressReceive = addressRequest.getAddress1() +", "+ addressRequest.getAddress2()
                +", "+ addressRequest.getAddress3()+", "+ addressRequest.getAddress4()+", Việt Nam";
        return Address.builder()
                .user(addressRequest.getUser())
                .receiveName(addressRequest.getReceiveName())
                .receivePhone(addressRequest.getReceivePhone())
                    .receiveAddress(addressReceive)
                .build();
    }
}
