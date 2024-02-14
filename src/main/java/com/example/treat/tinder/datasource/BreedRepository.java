package com.example.treat.tinder.datasource;

import com.example.treat.tinder.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Integer> {
}