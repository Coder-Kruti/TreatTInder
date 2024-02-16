package com.treat.tinder.controller;

import com.treat.tinder.entity.Dog;
import com.treat.tinder.entity.DogFilterOptions;
import com.treat.tinder.resource.DogResource;
import com.treat.tinder.service.DogService;
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

//    @Override
//    public ResponseEntity<Dog> saveLikeDislike() {
//        List<Dog> dogList = dogService.filterDogs(filterOptions);
//        return new ResponseEntity<>(dogList, HttpStatus.OK);
//    }

    @Override
    public void getDogsFromPetFinder() {
        dogService.getDogsPetFinder();

    }

}
