package org.example.service;

import org.example.vo.CustomerResponseVO;
import org.example.vo.CustomerVO;

public interface CustomerService {
    String Save(String name,String code);
    CustomerResponseVO apply(CustomerVO data);
}
