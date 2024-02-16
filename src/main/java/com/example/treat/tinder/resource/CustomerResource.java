package com.example.treat.tinder.resource;

import com.example.treat.tinder.entity.Customer;
import com.example.treat.tinder.entity.CustomerRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface CustomerResource {
    @RequestMapping(value = "/treat/tinder/customer",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest customerRequest);

}
