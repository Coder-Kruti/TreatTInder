package com.example.treat.tinder.controller;

import com.example.treat.tinder.entity.Dog;
import com.example.treat.tinder.entity.DogFilterOptions;
import com.example.treat.tinder.resource.DogResource;
import com.example.treat.tinder.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DogController implements DogResource {
    @Autowired
    DogService dogService;

    DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @Override
    public ResponseEntity<List<Dog>> getDogs(DogFilterOptions filterOptions) {
        List<Dog> dogList = dogService.filterDogs(filterOptions);
        return new ResponseEntity<>(dogList, HttpStatus.OK);
    }

    @Override
    public void getDogsFromPetFinder() {
        dogService.getDogsPetFinder();

    }

}
