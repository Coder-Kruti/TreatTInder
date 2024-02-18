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

    /***
     * Filter the dog from the database as per given criteria.
     * @param filterOptions Takes the filter options which includes organisation , breed and address.
     * @return List pf dogs which satisfy the given filter
     */
    @Override
    public ResponseEntity<List<Dog>> getDogs(DogFilterOptions filterOptions) {
        if (filterOptions == null || filterOptions.getAddress() == null || filterOptions.getBreed() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Dog> dogList = dogService.filterDogs(filterOptions);
        if (dogList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dogList, HttpStatus.OK);
    }


    /**
     * Fetch the details from the pet finder service. Ideally this should be scheduled with a cron job to fetch every 4 or 5 hrs
     */
    @Override
    public void getDogsFromPetFinder() {
        dogService.getDogsPetFinder();
    }

}
