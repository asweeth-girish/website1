package org.example.service;

import org.example.vo.CustomerResponseVO;
import org.example.vo.CustomerVO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    public CustomerResponseVO apply(CustomerVO data){
        CustomerResponseVO response=new CustomerResponseVO();
        String uniqueID= UUID.randomUUID().toString();
        response.setId(uniqueID);
        response.setName(data.getName());
        response.setEmail(data.getEmail());

        return response;
    }

    @Override
    public String Save(String name, String code) {
        return "";
    }
}
