package com.example.treat.tinder.datasource;

import com.example.treat.tinder.entity.CustomerInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInteractionRepository extends JpaRepository<CustomerInteraction, Integer> {
}
