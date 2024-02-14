package com.example.treat.tinder.resource;

import com.example.treat.tinder.entity.Dog;
import com.example.treat.tinder.entity.DogFilterOptions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface DogResource {

    @RequestMapping(value = "/treat/tinder",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<Dog>> getDogs(@RequestBody DogFilterOptions filterOptions);

    @RequestMapping(value = "/treat/petfinder",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public void getDogsFromPetFinder();

}
