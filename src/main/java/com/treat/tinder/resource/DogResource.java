package com.treat.tinder.resource;

import com.treat.tinder.entity.Dog;
import com.treat.tinder.entity.DogFilterOptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface DogResource {
    @Tag(name = "Dogs", description = "Fetch, store and filter dogs data")
    @Operation(summary = "Filter Dogs data as per request.")
    @RequestMapping(value = "/treat/tinder",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dog data is filtered."),
            @ApiResponse(responseCode = "204", description = "No data present as per the given filter request.",content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request. Please provide valid input.",content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.",content = @Content)
    })
    ResponseEntity<List<Dog>> getDogs(@RequestBody DogFilterOptions filterOptions);

    @Tag(name = "Dogs", description = "Fetch, store and filter dogs data")
    @Operation(summary = "Fetch dogs from Pet Finder")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dog data is fetched from Pet Finder service"),
            @ApiResponse(responseCode = "500", description = "Internal server error.",content = @Content)
    })
    @RequestMapping(value = "/treat/petfinder",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public void getDogsFromPetFinder();

}
