package com.example.treat.tinder.service;

import com.example.treat.tinder.datasource.CustomerRepository;
import com.example.treat.tinder.entity.Customer;
import com.example.treat.tinder.entity.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer (CustomerRequest customerRequest){
            Customer customer = new Customer();
            customer.setName(customerRequest.getName());
            customer.setPhoneNumber(customerRequest.getPhoneNumber());

            return customerRepository.save(customer);
    }

}
