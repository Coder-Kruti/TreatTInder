package com.treat.tinder.datasource;

import com.treat.tinder.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Integer> {
}