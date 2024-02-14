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
    private String gender;
    private String breed;
    private String location;
}
