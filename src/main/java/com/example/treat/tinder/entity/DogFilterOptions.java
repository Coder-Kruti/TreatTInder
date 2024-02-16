package com.example.treat.tinder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DogFilterOptions {
    private String organizationName;
    private Gender gender;
    private Breed breed;
    private Contact contact;
}
