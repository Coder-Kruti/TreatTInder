package com.treat.tinder.controller;

import com.treat.tinder.entity.Customer;
import com.treat.tinder.entity.CustomerAction;
import com.treat.tinder.entity.CustomerInteraction;
import com.treat.tinder.entity.CustomerLikeDislikeResponse;
import com.treat.tinder.entity.CustomerRequest;
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

    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Create a customer record
     *
     * @param customerRequest Takes Customer phone number and name . Stores in the database
     * @return Customer record created.
     */
    @Override
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest customerRequest) {
        if (customerRequest == null || customerRequest.getName() == null || customerRequest.getPhoneNumber() == null ||
                customerRequest.getName().isEmpty() || customerRequest.getPhoneNumber().isEmpty()) {
            //invalid request
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Customer customer = customerService.saveCustomer(customerRequest);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        }
    }

    /**
     * Manages the customer like/dislike of a given dog
     *
     * @param customerId ID of the customer who is liking the dog
     * @param dogId      ID of the dog being liked.
     * @param action     Like / Dislike
     * @return Message weather the interaction is handled or not.
     */

    @Override
    public ResponseEntity<CustomerLikeDislikeResponse> saveLikeDislike(Integer customerId, Integer dogId, CustomerAction action) {

        if (action == null) {
            return ResponseEntity.badRequest().build();
        }

        CustomerInteraction customerInteraction = new CustomerInteraction();
        customerInteraction.setCustomerID(customerId);
        customerInteraction.setDogID(dogId);
        customerInteraction.setInteractionType(action.getInteractionType());

        CustomerLikeDislikeResponse response = customerService.saveLikeDislike(customerInteraction);

        return ResponseEntity.status(response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
