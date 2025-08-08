package org.example.controller;

import org.example.service.CustomerService;
import org.example.vo.CustomerResponseVO;
import org.example.vo.CustomerVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/application")
    public String getApplication(@RequestParam(name="status", required = false)String status){
        return "Application status "+(status!=null?status:"none");
    }

    @PostMapping("/apply")
    public CustomerResponseVO apply(@RequestBody CustomerVO data){
        return customerService.apply(data);
    }
}

