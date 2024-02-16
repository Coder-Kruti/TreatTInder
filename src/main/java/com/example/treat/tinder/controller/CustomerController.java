package com.example.treat.tinder.controller;

import com.example.treat.tinder.entity.Customer;
import com.example.treat.tinder.entity.CustomerInteraction;
import com.example.treat.tinder.entity.CustomerRequest;
import com.example.treat.tinder.entity.InteractionType;
import com.example.treat.tinder.resource.CustomerResource;
import com.example.treat.tinder.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CustomerController implements CustomerResource {
    @Autowired
    CustomerService customerService;
    CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest customerRequest) {
       if(customerRequest == null || customerRequest.getName() == null || customerRequest.getPhoneNumber() == null || customerRequest.getName().isEmpty() || customerRequest.getPhoneNumber().isEmpty()){
           //invalid request
           return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
       }
       else {
           Customer customer = customerService.saveCustomer(customerRequest);
           return new ResponseEntity<>(customer, HttpStatus.OK);
       }
    }

    @Override
    public ResponseEntity<Boolean> saveLikeDislike(Integer customerId, Integer dogId, Map<String, String> requestBody) {
        String action = requestBody.get("action");
        boolean response;
        if (action == null || action.isEmpty() || !isValidAction(action)) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        CustomerInteraction customerInteraction = new CustomerInteraction();
        customerInteraction.setCustomerID(customerId);
        customerInteraction.setDogID(dogId);
        customerInteraction.setInteractionType(InteractionType.valueOf(action.toUpperCase()));
        response = customerService.saveLikeDislike(customerInteraction);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private boolean isValidAction(String action) {
        return action.equalsIgnoreCase(InteractionType.LIKE.toString()) ||
                action.equalsIgnoreCase(InteractionType.DISLIKE.toString());
    }
}
