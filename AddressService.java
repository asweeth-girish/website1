package org.example.service;

import org.example.entity.Address;
import org.example.entity.Vendor;
import org.example.repository.AddressRepository;
import org.example.repository.VendorRepository;

import org.example.vo.VendorVO.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService{
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public Address addAddress(Long vendorId,AddressVO addressvo){
        Vendor vendor=vendorRepository.findById(vendorId)
                .orElseThrow(()-> new RuntimeException("vendor not found"));
         Address address = new Address();
         address.setStreetName(addressvo.getStreetName());
         address.setState(addressvo.getState());
         address.setPinCode(addressvo.getPinCode());
         address.setVendor(vendor);

        return addressRepository.save(address);

    }


}
