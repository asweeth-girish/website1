package org.example.service;



import org.example.entity.Address;
import org.example.entity.Vendor;
import org.example.repository.VendorRepository;

import org.example.vo.VendorVO;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }

    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("vendor not found n"));
    }

    public Vendor createVendorWithAddresses(VendorVO vendorvo){
        Vendor vendor = new Vendor();
        vendor.setFirstname(vendorvo.getFirstName());
        vendor.setEmail(vendorvo.getEmail());

        if(vendorvo.getAddresses() !=null){
            for(VendorVO.AddressVO addressvo : vendorvo.getAddresses()){
                Address address = new Address();
                address.setStreetName(addressvo.getStreetName());
                address.setState(addressvo.getState());
                address.setPinCode(addressvo.getPinCode());
                vendor.addAddress(address);
            }
        }

        return vendorRepository.save(vendor);
    }


}









