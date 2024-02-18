package com.treat.tinder.resource;

import com.treat.tinder.entity.Customer;
import com.treat.tinder.entity.CustomerAction;
import com.treat.tinder.entity.CustomerRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface CustomerResource {

    @Tag(name = "Customer", description = "Save customer and like/dislike")
    @RequestMapping(value = "/treat/tinder/customer",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @Operation(summary = "Create Customer data.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer creation successful."),
            @ApiResponse(responseCode = "400", description = "Bad Request.",content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.",content = @Content)
    })
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest customerRequest);

    @Tag(name = "Customer", description = "Save customer and like/dislike")
    @RequestMapping(value = "/treat/tinder/customer/{customerId}/dogs/{dogId}/reaction",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @Operation(summary = "Save Like/Dislike of dog.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request.",content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.",content = @Content)
    })
    public ResponseEntity<String> saveLikeDislike(@PathVariable Integer customerId, @PathVariable Integer dogId, @RequestBody CustomerAction customerAction);

}
