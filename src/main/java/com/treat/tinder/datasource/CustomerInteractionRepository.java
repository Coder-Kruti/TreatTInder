package com.treat.tinder.datasource;

import com.treat.tinder.entity.CustomerInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInteractionRepository extends JpaRepository<CustomerInteraction, Integer> {
}
