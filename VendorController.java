package org.example.controller;

import org.example.entity.Address;
import org.example.entity.Vendor;
import org.example.service.AddressService;
import org.example.service.VendorService;
import org.example.vo.VendorVO.AddressVO;
import org.example.vo.VendorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @Autowired
    private AddressService addressService;


    @PostMapping
    public  Vendor createVendor(@RequestBody VendorVO vendorvo){
        return vendorService.createVendorWithAddresses(vendorvo);
    }


    @GetMapping("/{id}")
    public Vendor getVendor(@PathVariable Long id){

        return vendorService.getVendorById(id);
    }

    @PostMapping("/address/{vendorId}")
    public Address addAddress(@PathVariable Long vendorId, @RequestBody AddressVO addressvo){
        return addressService.addAddress(vendorId,addressvo);

    }



}
