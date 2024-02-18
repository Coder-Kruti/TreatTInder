package com.treat.tinder.service;

import com.treat.tinder.datasource.CustomerInteractionRepository;
import com.treat.tinder.datasource.CustomerRepository;
import com.treat.tinder.datasource.DogRepository;
import com.treat.tinder.entity.Customer;
import com.treat.tinder.entity.CustomerInteraction;
import com.treat.tinder.entity.CustomerLikeDislikeResponse;
import com.treat.tinder.entity.CustomerRequest;
import com.treat.tinder.entity.Dog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final CustomerInteractionRepository customerInteractionRepository;

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private final DogRepository dogRepository;
    CustomerService(CustomerRepository customerRepository, CustomerInteractionRepository customerInteractionRepository, DogRepository dogRepository){
        this.customerRepository = customerRepository;
        this.customerInteractionRepository = customerInteractionRepository;
        this.dogRepository = dogRepository;
    }

    /**
     * Save the customer data
     * @param customerRequest Name and phone number of the customer
     * @return Customer that is created.
     */
    public Customer saveCustomer (CustomerRequest customerRequest){
            Customer customer = new Customer();
            customer.setName(customerRequest.getName());
            customer.setPhoneNumber(customerRequest.getPhoneNumber());

            return customerRepository.save(customer);
    }

    /**
     *  Save the like/ dislike of a dog
     * @param customerInteraction Like/Dislike
     * @return Weather its successful
     */
    public CustomerLikeDislikeResponse saveLikeDislike(CustomerInteraction customerInteraction) {
        logger.info("Save Like/Dislike of a dog.");

        if (customerInteraction == null) {
            return new CustomerLikeDislikeResponse(false, "The input given is null.");
        }

        // Verify if the particular Dog id and customer are already present
        Optional<Dog> dog = dogRepository.findById(customerInteraction.getDogID());
        Optional<Customer> customer = customerRepository.findById(customerInteraction.getCustomerID());
        if (dog.isEmpty() || customer.isEmpty()) {
            return new CustomerLikeDislikeResponse(false, "The dog or customer is not present in the database.");
        }

        customerInteractionRepository.save(customerInteraction);

        return new CustomerLikeDislikeResponse(true, "Successfully saved the customer interaction.");
    }
}
