package com.treat.tinder.controller;

import com.treat.tinder.entity.Customer;
import com.treat.tinder.entity.CustomerAction;
import com.treat.tinder.entity.CustomerInteraction;
import com.treat.tinder.entity.CustomerRequest;
import com.treat.tinder.entity.InteractionType;
import com.treat.tinder.resource.CustomerResource;
import com.treat.tinder.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController implements CustomerResource {
    @Autowired
    CustomerService customerService;
    CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest customerRequest) {
       if(customerRequest == null || customerRequest.getName() == null || customerRequest.getPhoneNumber() == null ||
               customerRequest.getName().isEmpty() || customerRequest.getPhoneNumber().isEmpty()) {
           //invalid request
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
       else {
           Customer customer = customerService.saveCustomer(customerRequest);
           return new ResponseEntity<>(customer, HttpStatus.CREATED);
       }
    }

    @Override
    public ResponseEntity<String> saveLikeDislike(Integer customerId, Integer dogId, CustomerAction action) {

        boolean response;
        if (action == null ) {
            return new ResponseEntity<>("Request is invalid, kindly check the input.", HttpStatus.BAD_REQUEST);
        }

        CustomerInteraction customerInteraction = new CustomerInteraction();
        customerInteraction.setCustomerID(customerId);
        customerInteraction.setDogID(dogId);
        customerInteraction.setInteractionType(action.getInteractionType());
        response = customerService.saveLikeDislike(customerInteraction);
        if (response){
            return new ResponseEntity<>("Successfully saved like/dislike interaction for the specified customer and dog.", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Failed in processing the request", HttpStatus.BAD_REQUEST);
        }

    }

    private boolean isValidAction(String action) {
        return action.equalsIgnoreCase(InteractionType.LIKE.toString()) ||
                action.equalsIgnoreCase(InteractionType.DISLIKE.toString());
    }
}
