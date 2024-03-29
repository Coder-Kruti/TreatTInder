package com.treat.tinder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DogFilterOptions {
    private String organizationID;
    private Gender gender;
    private Breed breed;
    private Address address;
}
