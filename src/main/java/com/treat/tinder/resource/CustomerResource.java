package com.treat.tinder.resource;

import com.treat.tinder.entity.Customer;
import com.treat.tinder.entity.CustomerRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

public interface CustomerResource {
    @RequestMapping(value = "/treat/tinder/customer",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest customerRequest);

    @RequestMapping(value = "/treat/tinder/customer/{customerId}/dogs/{dogId}/reaction",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<Boolean> saveLikeDislike(@PathVariable Integer customerId, @PathVariable Integer dogId, @RequestBody Map<String, String> requestBody);

}
