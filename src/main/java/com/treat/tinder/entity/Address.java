package com.treat.tinder.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Address {
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postcode;
    private String country;
}
