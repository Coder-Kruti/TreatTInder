package com.treat.tinder.service;

import com.treat.tinder.datasource.CustomerInteractionRepository;
import com.treat.tinder.datasource.CustomerRepository;
import com.treat.tinder.datasource.DogRepository;
import com.treat.tinder.entity.Customer;
import com.treat.tinder.entity.CustomerInteraction;
import com.treat.tinder.entity.CustomerRequest;
import com.treat.tinder.entity.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final CustomerInteractionRepository customerInteractionRepository;

    @Autowired
    private final DogRepository dogRepository;
    CustomerService(CustomerRepository customerRepository, CustomerInteractionRepository customerInteractionRepository, DogRepository dogRepository){
        this.customerRepository = customerRepository;
        this.customerInteractionRepository = customerInteractionRepository;
        this.dogRepository = dogRepository;
    }

    public Customer saveCustomer (CustomerRequest customerRequest){
            Customer customer = new Customer();
            customer.setName(customerRequest.getName());
            customer.setPhoneNumber(customerRequest.getPhoneNumber());

            return customerRepository.save(customer);
    }

    public boolean saveLikeDislike(CustomerInteraction customerInteraction) {
        if (customerInteraction == null) {
            return false;
        }
        //verify te particular Dog id and customer is already present or not
        Optional<Dog> dog = dogRepository.findById(customerInteraction.getDogID());
        Optional<Customer> customer = customerRepository.findById(customerInteraction.getCustomerID());
        if (dog.isEmpty() || customer.isEmpty()) {
            return false;
        }

        customerInteractionRepository.save(customerInteraction);
        return true;
    }

}
