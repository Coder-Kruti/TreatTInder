package com.example.treat.tinder.datasource;

import com.example.treat.tinder.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
